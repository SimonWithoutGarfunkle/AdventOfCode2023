package adventofcode.adventofcode.solving;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DayEleven {

    public char[][] universe;

    public char[][] sourceUniverse;

    // Ajouter pour la question 2
    public int expansion = 1000000;

    List<Integer> extendLine = new ArrayList<>();

    List<Integer> extendColumn = new ArrayList<>();

    public DayEleven(List<String> input) {
        universe = createAndExpenseUniverse(input);
        sourceUniverse = getSourceUniverse(input);
    }

    public char[][] getSourceUniverse(List<String> input) {
        char[][] original = new char[input.size()][input.get(0).length()];
        for (int i =0; i< original.length; i++) {
            for (int j = 0; j < original[0].length; j++) {
                original[i][j] = input.get(i).charAt(j);
            }
        }

        return original;
    }

    public char[][] createAndExpenseUniverse(List<String> input) {
        //On créé dabord le nouvel input avec les lignes qui ne contiennent que des . dédoublés
        List<String> universeLineExtended = new ArrayList<>();
        for (int i = 0; i <input.size(); i++) {
            universeLineExtended.add(input.get(i));
            if (!input.get(i).contains("#")) {
                universeLineExtended.add(input.get(i));
                //System.out.println("Pour i="+i+"  ==>  "+input.get(i));
                extendLine.add(i);
                //System.out.println("ExtendLine size = "+extendLine.size());
            }
        }

        //On liste les colonnes qu'il faudra dupliquer
        for (int j =0; j < input.get(0).length(); j++) {
            boolean extende = true;
            for (String word : input) {
                if (word.charAt(j) == '#') {
                    extende = false;
                }
            }
            if (extende) {
                extendColumn.add(j);
            }
        }

        String points =".";

        //Pour chaque String de input on rajoute un . a chaque colonne identifiée a l'etape précédente
        List<String> finalInput = new ArrayList<>();
        for (String word : universeLineExtended) {
            String extendedWord = word.substring(0, extendColumn.get(0))+points;
            for (int i =0; i < extendColumn.size()-1; i++) {
                extendedWord+=word.substring(extendColumn.get(i), extendColumn.get(i+1))+points;
            }
            extendedWord+=word.substring(extendColumn.get(extendColumn.size()-1));
            finalInput.add(extendedWord);
        }


        //On créé enfin l'univers a partir de notre input étendue
        char[][] finalUniverse = new char[finalInput.size()][finalInput.get(0).length()];
        for (int i = 0; i < finalUniverse.length; i++) {
            for (int j = 0; j < finalUniverse[0].length; j++) {
               finalUniverse[i][j] = finalInput.get(i).charAt(j);
            }
        }
        //printUniverse(finalUniverse);
        return finalUniverse;
    }

    /*
    Je comptais partir sur la meme approche pour la question 2 que pour la 1 en générant un nouvel
    univers etendu mais les valeurs sont trop grandes pour etre calculées, je change d'approche
     */
    public void puzzle2() {
        List<int[]> sharps = new ArrayList<>();
        for (int i = 0; i < sourceUniverse.length; i++) {
            for (int j = 0; j < sourceUniverse[0].length; j++) {
                if (sourceUniverse[i][j] == '#') {
                    sharps.add(new int[]{i, j});
                }
            }
        }
        System.out.println(sharps.size());
        BigInteger result = new BigInteger("0");

        for (int i = 0; i < sharps.size(); i++) {
            for (int j=i; j < sharps.size()-1; j++) {
                result = result.add(BigInteger.valueOf(countStepsPuzzle2(sharps.get(i), sharps.get(j+1))));
            }
        }
        System.out.println("And the answer is ... ==> "+result);

    }

    public void puzzle1() {
        //On récupere toutes les coordonnées des #
        List<int[]> sharps = new ArrayList<>();
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe[0].length; j++) {
                if (universe[i][j] == '#') {
                    sharps.add(new int[]{i, j});
                }
            }
        }
        System.out.println(sharps.size());

        int result=0;
        //On utilise countSteps sur toutes les combinaisons possible de 2 sharp et on additionne le résultat
        for (int i = 0; i < sharps.size(); i++) {
            for (int j=i; j < sharps.size()-1; j++) {
                result += countSteps(sharps.get(i), sharps.get(j+1));
            }
        }
        System.out.println("And the answer is ... ==> "+result);

    }

    // Compte le nombre de pas a faire pour passer d'un point A de la matrice à un point B
    public int countSteps(int[]sharp1, int[]sharp2) {
        return Math.abs(sharp2[0]-sharp1[0])+Math.abs(sharp2[1]-sharp1[1]);
    }

    public long countStepsPuzzle2(int[]sharp1, int[]sharp2) {
        //On commence par calculer comme pour la question 1
        int basicResult = countSteps(sharp1, sharp2);

        //On compte le nombre de ligne à étendre à traverser pour se rendre du point A au point B
        int count =0;
        for (Integer i = Integer.min(sharp1[0], sharp2[0]); i < Integer.max(sharp1[0], sharp2[0]); i++) {
            if (extendLine.contains(i)) {
                count++;
            }
        }
        //Maintenant on compte les colonnes à étendre à traverser pour se rendre du point A au point B
        for (Integer j = Integer.min(sharp1[1], sharp2[1]); j < Integer.max(sharp1[1], sharp2[1]); j++) {
            if (extendColumn.contains(j)) {
                count++;
            }
        }
        // on part du calcul de base et on ajoute le nombre de ligne à étendre X le coefficient d'expansion
        // L'expansion 1 est dans l'univers original donc elle est déja comptée d'où le -1
        return  count*(expansion-1) + basicResult;
    }

    //Pour faire quelques controles
    public void printUniverse(char[][] universToPrint) {
        System.out.println("Universe X="+universToPrint.length+" and Y="+universToPrint[0].length);
        for (int i =0; i< universToPrint.length; i++) {
            for (int j = 0; j < universToPrint[0].length; j++) {
                System.out.print(" "+universToPrint[i][j]);
            }
            System.out.println();
        }

    }
}
