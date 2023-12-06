package adventofcode.adventofcode.solving;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DayFive {

    private final List<Long> seeds;
    private final List<Map> maps;
    private final ExecutorService executorService;

    public DayFive (List<String> input) {
        seeds = parseSeeds(input.get(0));
        maps = initializeMaps(input.subList(3, input.size()));
        executorService = Executors.newFixedThreadPool(3000);

        int mapId=0;
        for (String line : input.subList(3, input.size())) {
            //System.out.println("working on line : " +line );
            if (line.isBlank()) {
                mapId++;
                //System.out.println(" case blank");
            } else if (Character.isDigit(line.charAt(0))) {
                maps.get(mapId).addRange(convertLineToRange(line));
                //System.out.println(" case digit with id = "+mapId);
            }

        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void shutDowExecutorService() {
        executorService.shutdown();
    }

    private List<Long> parseSeeds(String seedsString) {
        return Arrays.stream(seedsString.replaceFirst("seeds: ", "").split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private List<Map> initializeMaps(List<String> lines) {
        List<Map> initializedMaps = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            initializedMaps.add(new Map(i, new ArrayList<>()));
        }
        int mapId = 0;
        for (String line : lines) {
            //System.out.println("working on line : " +line );
            if (line.isBlank()) {
                mapId++;
                //System.out.println(" case blank");
            } else if (Character.isDigit(line.charAt(0))) {
                initializedMaps.get(mapId).addRange(convertLineToRange(line));
                //System.out.println(" case digit with id = "+mapId);
            }


        }
        System.out.println("Maps initialized");
        return initializedMaps;
    }

    public Long solvingPuzzle2() {
        List<List<Long>> pairs = IntStream.range(0, seeds.size() / 2)
                .mapToObj(i -> List.of(seeds.get(i * 2), seeds.get(i * 2 + 1)))
                .toList();
        //pairs.forEach(System.out::println);


        /*
        Long resultSeed=0L;
        List<Long> finalResult = new ArrayList<>();s

        for (List<Long> pair : pairs) {
            for (Long count = pair.get(0); count < (pair.get(0)+pair.get(1)); count++ ) {
                //System.out.println("working on seed : "+seed);
                resultSeed = count;
                for (int j=0; j<7; j++) {
                    final int mapIndex = j; // Création d'une copie immuable pour l'index de la map
                    resultSeed = crossTheMap(resultSeed, mapIndex);
                    //System.out.println("new value is : "+resultSeed);
                }
                finalResult.add(resultSeed);

            }
        }
        return Collections.min(finalResult);
        */



        List<CompletableFuture<Long>> finalResults = new ArrayList<>();


        for (List<Long> pair : pairs) {
            for (Long count = pair.get(0); count < (pair.get(0) + pair.get(1)); count++) {
                final Long[] resultSeed = {count}; // Copie immuable de count pour chaque itération
                CompletableFuture<Long> futureResult = CompletableFuture.supplyAsync(() -> {
                    for (int j = 0; j < 7; j++) {
                        resultSeed[0] = crossTheMap(resultSeed[0], j);
                    }
                    return resultSeed[0];
                }, executorService);

                finalResults.add(futureResult);
            }
        }

        List<Long> results = finalResults.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return Collections.min(results);


    }

    public Long solvingPuzzle1() {
                Long resultSeed=0L;
        List<Long> finalResult = new ArrayList<>();
        int i=1;

        for (Long seed : seeds) {
            System.out.println("working on seed : "+seed);
            resultSeed = seed;
            for (int j=0; j<7; j++) {
                System.out.println("crossing map n°"+i);
                i++;
                resultSeed = crossTheMap(resultSeed, j);
                System.out.println("new value is : "+resultSeed);
            }
            i=1;
            finalResult.add(resultSeed);

        }
        return Collections.min(finalResult);
    }

    private Range convertLineToRange(String line) {
        String[] data = line.split(" ");
        return new Range(Long.parseLong(data[0]), Long.parseLong(data[1]), Long.parseLong(data[2]));
    }

    private Long crossTheMap(Long longValue, int mapIndex) {
        Map map = maps.get(mapIndex);
        for (Range range : map.ranges) {
            if (range.source <= longValue && longValue < range.source + range.length) {
                return range.destination + (longValue - range.source);
            }
        }
        return longValue;
    }


    @AllArgsConstructor
    private static class Map {
        int id;
        List<Range> ranges;

        public void addRange(Range range) {
            this.ranges.add(range);
        }

    }

    @AllArgsConstructor
    public static class Range {
        Long destination;
        Long source;
        Long length;
    }

}
