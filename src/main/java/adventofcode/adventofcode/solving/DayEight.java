package adventofcode.adventofcode.solving;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DayEight {

    private final String instructions;
    private final Map<String, String> map;

    private static final Logger logger = LoggerFactory.getLogger(DayEight.class);

    public DayEight(List<String> input) {
        instructions = input.get(0);
        map = initializemap(input);

    }

    // Ce n'est pas la réponse mais ca fourni les logs pour identifier la frequence de chaque fantome
    public void puzzle2() {
        List<String> starter = Arrays.asList("BXA", "KBA", "VTA", "AAA", "HMA", "HLA");
        //List<String> starter = Arrays.asList("11A", "22A");
        int compteur =0;
        long result = 1L;
        String regex = "[A-Z]{2}Z";

        while (!destinationReached(starter)) {
            final int currentCompteur = compteur;
            starter = starter.stream()
                    .map(string -> {
                        if (instructions.charAt(currentCompteur) == 'L') {
                            //System.out.println("left means from : "+string+" to ==> "+map.get(string).substring(0, 3));
                            return map.get(string).substring(0, 3);
                        } else {
                            //System.out.println("right means from : "+string+" to ==> "+map.get(string).substring(3, 6));
                            return map.get(string).substring(3, 6);
                        }
                    })
                    .collect(Collectors.toList());
            //System.out.println(starter.get(0)+ " et on a bouclé : "+result);

            if (starter.get(0).charAt(2)=='Z') {
                System.out.println("ghost n°1 reach Z at "+result);
            }
            if (starter.get(1).charAt(2)=='Z') {
                System.out.println("ghost n°2 reach Z at "+result);
            }
            if (starter.get(2).charAt(2)=='Z') {
                System.out.println("ghost n°3 reach Z at "+result);
            }
            if (starter.get(3).charAt(2)=='Z') {
                System.out.println("ghost n°4 reach Z at "+result);
            }
            if (starter.get(4).charAt(2)=='Z') {
                System.out.println("ghost n°5 reach Z at "+result);
            }
            if (starter.get(5).charAt(2)=='Z') {
                System.out.println("ghost n°6 reach Z at "+result);
            }
            compteur++;
            result++;
            if (compteur >= instructions.length()) {
                compteur = 0;
            }
            if (result > 200000) {
                break;
            }
        }
        System.out.println("And the answer is ... "+result);
    }

    public long lcm_of_array_elements(int[] element_array)
    {
        long lcm_of_array_elements = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < element_array.length; i++) {

                // lcm_of_array_elements (n1, n2, ... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm_of_array_elements.

                if (element_array[i] == 0) {
                    return 0;
                }
                else if (element_array[i] < 0) {
                    element_array[i] = element_array[i] * (-1);
                }
                if (element_array[i] == 1) {
                    counter++;
                }

                // Divide element_array by devisor if complete
                // division i.e. without remainder then replace
                // number with quotient; used for find next factor
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }

            // If divisor able to completely divide any number
            // from array multiply with lcm_of_array_elements
            // and store into lcm_of_array_elements and continue
            // to same divisor for next factor finding.
            // else increment divisor
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            }
            else {
                divisor++;
            }

            // Check if all element_array is 1 indicate
            // we found all factors and terminate while loop.
            if (counter == element_array.length) {
                return lcm_of_array_elements;
            }
        }
    }


    private boolean destinationReached(List<String> input) {
        return input.stream().allMatch(string -> string.matches("[A-Z]{2}Z"));
    }

    public void puzzle1() {
        String travel = "AAA";
        int compteur =0;
        int result = 0;
        while (!travel.equals("ZZZ")) {
            logger.info("working on : "+instructions.charAt(compteur)+" travelling from : "+travel);
            if (instructions.charAt(compteur)=='L') {
                travel = map.get(travel).substring(0,3);
            } else {
                travel = map.get(travel).substring(3,6);
            }
            logger.info(" ==> "+travel);
            compteur++;
            result++;
            if (compteur >= instructions.length()) {
                compteur = 0;
            }
        }
        System.out.println("And the answer is ... "+result);
    }

    // Je fais une map avec les 6 lettres en value et j'utiliserai un if pour choisir quelles 3 lettres j'utilise
    private Map<String, String> initializemap(List<String> input) {
        Map<String, String> initialized = new HashMap<>();
        for (String line : input.subList(2, input.size())) {
            String lineValue = line.substring(line.length()-9 , line.length()-1).replace(", ", "");
            initialized.put(line.substring(0, 3), lineValue);
            //System.out.println("map key="+line.substring(0, 3)+"  and value="+lineValue);
        }
        return initialized;
    }

}
