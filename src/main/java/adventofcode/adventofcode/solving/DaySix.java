package adventofcode.adventofcode.solving;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class DaySix {

    private final List<Race> races;

    public DaySix(List<String> input) {
        races = initializeRaces(input);

    }

    private List<Race> initializeRaces(List<String> input) {
        List<Race> initializedRaces = new ArrayList<>();
        String[] times = input.get(0).split("\\s+");
        String[] distances = input.get(1).split("\\s+");
        for (int i =1; i<5; i++) {
            initializedRaces.add(new Race(Integer.parseInt(times[i].trim()), Integer.parseInt(distances[i].trim())));
        }
        System.out.println("Races initialized : "+initializedRaces.size());

        return initializedRaces;
    }

    public Long puzzle1() {
        Long result=1L;
        for (Race race : races) {
            System.out.println("adding winning value : "+ winTheRace(race));
            result*= winTheRace(race);
        }

        return result;
    }
    public int puzzle2() {
        Race race = new Race(45977295L, 305106211101695L);
        return winTheRace(race);
    }

    public int winTheRace(Race race) {
        int result=0;
        //System.out.println("working on race "+race.time+" / "+race.distanceMin);
        for (Long i =1L; i <= race.time; i++) {
            if ((i*(race.time-i)) > race.distanceMin) {
                result++;
            }
        }
        //System.out.println("returning victory values : "+result.size());
        return result;
    }


    @AllArgsConstructor
    public static class Race {
        long time;
        long distanceMin;
    }
}
