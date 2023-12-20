package adventofcode.adventofcode.solving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
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
        //System.out.println("Iteration : "+line);
        if(line.length() < 3) {
            return 0;
        }

        //S'il n'y a plus de ? on arrete de diviser le String et on retourne 1 (il faudra implémenter ici la validation de la ligne)
        if (!line.contains("?")) {
            //System.out.println("ici ok !");
            if (validateLine(line, argument)) {
                //System.out.println("toujours ok  !");
                return 1;
            } else {
                return 0;
            }
        }

        //Cas particulier la ligne se termine par ?
        if (line.indexOf('?') == line.length() -1 ) {
            //System.out.println("on termine par ?");
            return combination(line.substring(0, line.length()-1)+".", argument) + combination(line.substring(0, line.length()-1)+"#", argument);
        } else if (line.indexOf('?') == 0) {
            //Cas particulier la ligne commence par ?
            //System.out.println("on commence par ?");
            return combination("."+line.substring(1), argument) + combination("#"+line.substring(1), argument);
        }

        String before = line.substring(0, line.indexOf('?'));
        String after = line.substring(line.indexOf('?')+1);
        return combination(before+"."+after, argument) + combination(before+"#"+after, argument);
    }

    private boolean validateLine(String line, int[] argument) {
        Matcher matcher = PATTERN.matcher(line);
        List<Integer> listOfSharp = new ArrayList<>();
        while (matcher.find()) {
            listOfSharp.add(matcher.group().length());
        }
        return Arrays.equals(listOfSharp.stream().mapToInt(Integer::intValue).toArray(), argument);
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
