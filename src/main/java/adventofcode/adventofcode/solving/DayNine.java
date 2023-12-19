package adventofcode.adventofcode.solving;

import java.util.*;

public class DayNine {
    List<List<Integer>> oasis;

    public DayNine(List<String> input) {
        oasis = new ArrayList<>();
        for (String string : input) {
            List<Integer> history = Arrays.stream(string.split(" "))
                    .map(Integer::parseInt)
                    .toList();
            oasis.add(history);
        }
    }

    public void puzzle1() {
        long result = oasis.stream().map(this::downHistory).mapToInt(this::predict).sum();
        System.out.println("And the answer is ... ==> "+result);
    }

    public void puzzle2() {
        long result = oasis.stream().map(this::downHistory).mapToInt(this::predictPrevious).sum();
        System.out.println("And the answer is ... ==> "+result);
    }

    // Génère l'historique complet de la ligne en descendant ligne par ligne jusqua avoir une ligne de 0
    public List<List<Integer>> downHistory(List<Integer> history) {
        int result = 0;
        List<List<Integer>> fullHistory = new ArrayList<>();
        fullHistory.add(history);

        while (!history.stream().allMatch(element -> element == 0)) {
            history = goDown(history);
            fullHistory.add(history);
        }
        return fullHistory;
    }

    // Calcule la ligne inférieur à partir de la ligne fournie
    public List<Integer> goDown(List<Integer> line) {
        List<Integer> down = new ArrayList<>();
        for (int i = 0; i < line.size() - 1; i++) {
            down.add(line.get(i + 1) - line.get(i));
        }
        return down;
    }

    //Prédit la valeur résultat à partir d'un historique complet
    //On remonte l'historique ligne par ligne à l'envers (on part de la ligne des 0)
    public int predict(List<List<Integer>> input) {
        Collections.reverse(input);
        int result = 0;
        for (int i = 1; i < input.size() ; i++) {
            result += input.get(i ).get(input.get(i).size() - 1);
        }
        return result;
    }

    //Prédit la valeur précédente pour le puzzle 2
    public int predictPrevious(List<List<Integer>> input) {
        Collections.reverse(input);
        int result = 0;
        for (int i = 1; i < input.size() ; i++) {
            result = input.get(i).get(0) - result;
        }
        return result;
    }
}
