package adventofcode.adventofcode.solving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DayTwelve {

    List<String> field;

    List<int[]> arguments;

    private final Pattern PATTERN = Pattern.compile("#+");

    public DayTwelve(List<String> input) {
        field = generateField(input);
        arguments = generateArguments(input);

    }

    public void puzzle1() {
        int result=0;
        for (int i =0; i < field.size(); i++) {
            result+= combination(field.get(i), arguments.get(i));
        }

        System.out.println("And the answer is ... ==> "+result);
    }

    private int combination(String line, int[] argument) {
        if (!line.contains("?")) {
            System.out.println("ici ok !");
            if (Arrays.stream(argument).sum() == line.chars().filter(ch -> ch == '#').count()) {
                System.out.println("toujours ok  !");
                return 1;
            }
        }
        String before = line.substring(0, line.indexOf('?'));
        String after = line.substring(line.indexOf('?'));

        return combination(before+"."+after, argument) + combination(before+"#"+after, argument);
    }

    private List<int[]> generateArguments(List<String> input) {
        List<int[]> arguments = new ArrayList<>();
        for (String line : input) {
            int[] nums = Arrays.stream(line.split(" ")[1].split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            arguments.add(nums);
        }
        return arguments;
    }

    private List<String> generateField(List<String> input) {
        field = new ArrayList<>();
        for (String word : input) {
            field.add(word.split(" ")[0]);
        }
        return field;
    }

    public void printField() {
        for (String item : field) {
            System.out.println(item);
        }
    }


    public void printList() {
        for (int[] item : arguments) {
            System.out.println(Arrays.toString(item));
        }
    }


}
