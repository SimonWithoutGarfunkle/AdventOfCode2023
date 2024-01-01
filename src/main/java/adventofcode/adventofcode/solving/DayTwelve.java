package adventofcode.adventofcode.solving;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayTwelve {

    List<String> field;

    List<int[]> arguments;

    private final Pattern PATTERN = Pattern.compile("#+");

    private Map<String, Integer> memo = new HashMap<>();

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

    public void puzzle2() {
        int result=0;
        for (int i =0; i < field.size(); i++) {
            String newLine="";

            for (int j =0; j < 4; j++) {
                newLine += field.get(i)+"?";
            }

            int[] newArgument = new int[arguments.get(i).length*5];
            for (int j=0; j < newArgument.length; j++) {
                newArgument[j] = arguments.get(i)[j % arguments.get(i).length];
            }
            newLine+=field.get(i);
            System.out.println("newLIne is : "+newLine);
            System.out.println("newArguments is : "+ Arrays.toString(newArgument));

            result+= combination(newLine, newArgument);
        }

        System.out.println("And the answer is ... ==> "+result);

    }

    private int combination(String line, int[] argument) {
        int result;
        //System.out.println("memo size is : "+memo.size());

        if(line.length() < 2) {
            return 0;
        }
        String key = line+"-"+argumentsToString(argument);

        if (memo.containsKey(key)) {
            System.out.println("deja dans la map !");
            return memo.get(key);
        }

        //S'il n'y a plus de ? on arrete la recursivité et on vérifie si la ligne est valide
        if (!line.contains("?")) {
            if (validateLine(line, argument)) {
                result = 1;
            } else {
                result = 0;
            }

        }else if (line.indexOf('?') == line.length() -1 ) {
            result = combination(line.substring(0, line.length()-1)+".", argument) + combination(line.substring(0, line.length()-1)+"#", argument);
        } else if (line.indexOf('?') == 0) {
            //Cas particulier la ligne commence par ?
            result = combination("."+line.substring(1), argument) + combination("#"+line.substring(1), argument);
        } else {
            result = combination(line.substring(0, line.indexOf('?'))+"."+line.substring(line.indexOf('?')+1), argument)
                    + combination(line.substring(0, line.indexOf('?'))+"#"+line.substring(line.indexOf('?')+1), argument);
        }
        System.out.println("Adding to memo : "+key+"    with value:"+result);

        memo.put(key, result);
        return result;
    }

    private String argumentsToString(int[] argument) {
        StringBuilder sb = new StringBuilder();
        for (int value : argument) {
            sb.append(value);
            sb.append("-");
        }
        return sb.toString();
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
