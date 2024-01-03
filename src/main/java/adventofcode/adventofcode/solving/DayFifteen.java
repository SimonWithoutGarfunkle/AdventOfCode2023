package adventofcode.adventofcode.solving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DayFifteen {

    private final List<String> inputs;

    public DayFifteen(List<String> input) {
        inputs = readInput(input);
    }

    private List<String> readInput(List<String> input) {
        return Arrays.stream(input.get(0).split(",")).toList();
    }

    public void puzzle1() {
        System.out.println("And the answer is ... -->  "+inputs.stream().mapToInt(this::hashString).sum());
    }
    
    //Je stock les box dans une HashMap avec en clé le n° de la box et en valeur son contenu
    public void puzzle2() {
        HashMap<Integer, List<String>> map = new HashMap<>();
        for (int i = 0; i <= 255; i++) {
            map.put(i, new ArrayList<>());
        }

        for (String input : inputs) {
            String label;
            int box;
            int index;
            char ch;
            if (input.contains("-")) {
                ch='-';
                label = input.substring(0, input.length()-1);
            } else {
                ch='=';
                //Si c'est un cas '=', le dernier caractere est numérique
                label = input.substring(0, input.length()-2);
            }
            box = hashString(label);
            index = containsLabel(map.get(box), label);
            System.out.println("travail sur input = "+input+" // label="+label+" // box="+box+" // index="+index+" // ch='"+ch+"'");
            if (ch == '-') {
                //Si l'élément est trouvé il faut l'enlever de la liste
                if (index > -1) {
                    map.get(box).remove(index);
                }
            } else {
                //Si l'élément est trouvé il faut mettre a jour la valeur numerique
                if ( index > -1) {
                    map.get(box).set(index, label+input.charAt(input.length()-1) );
                } else {
                    //Sinon il faut l'ajouter a la liste
                    map.get(box).add(label+input.charAt(input.length()-1));
                }
            }
        }
        int result=0;
        for (Integer key : map.keySet()) {
            result += scoreBox(key, map.get(key));
        }
        System.out.println("And the answer is ... -->  "+result);

    }


    // Calcule le score d'une box selon les regles de la question 2
    public int scoreBox(Integer key, List<String> contents ) {
        int result = 0;
        int compteur = 0;
        for (String item : contents) {
            compteur++;
            result += (key+1)*compteur*Character.getNumericValue(item.charAt(item.length()-1)) ;
        }
        return result;
    }

    // La HashMap contient des labels associés à des chiffres comme par exemple rn8
    // On ne peut pas chercher directement le label
    public int containsLabel(List<String> list, String label) {
        for (int i = 0; i < list.size(); i++) {
            String current = list.get(i);
            int cut = 2;
            if (current.length() >= label.length()) {
                cut = label.length();
            }
            if (current.substring(0, cut).equals(label)) {
                return i;
            }
        }
        return -1;
    }

    public int hashString(String string) {
        int value =0;
        for (int i =0; i < string.length(); i++) {
            value += (int) string.charAt(i);
            value *= 17;
            value = value % 256;
        }
        return value;
    }
}
