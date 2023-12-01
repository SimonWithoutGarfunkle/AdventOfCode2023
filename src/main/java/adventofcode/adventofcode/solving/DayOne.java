package adventofcode.adventofcode.solving;

import java.util.*;

public class DayOne {


    public int firstTest(List<String> list) {
        int result=0;
        for (String word : list) {
            System.out.println("On s'attaque au mot : "+word+" ==> "+convertLettertoDigit(word));
            result += getFirstLastDigit(convertLettertoDigit(word));
            System.out.println("le résultat est désormais : "+result);
        }
        return result;
    }

    public String convertLettertoDigit(String mot) {
        //on converti ainsi pour gerer les mots qui se superposent comme eightwo avec 8 et 2
        Map<String, String> convertisseur = new HashMap<>();
        convertisseur.put("one", "one1one");
        convertisseur.put("two", "two2two");
        convertisseur.put("three", "three3three");
        convertisseur.put("four", "four4four");
        convertisseur.put("five", "five5five");
        convertisseur.put("six", "six6six");
        convertisseur.put("seven", "seven7seven");
        convertisseur.put("eight", "eight8eight");
        convertisseur.put("nine", "nine9nine");
        String result=mot;

        for (String clef : convertisseur.keySet()) {
            result = result.replaceAll(clef, convertisseur.get(clef));
        }

        return result;

    }

    /*
    private String selectBestWord(String mot1, String mot2) {

        int[] word1Indexes = findIndexesForDigit(mot1);
        int[] word2Indexes = findIndexesForDigit(mot2);
        String result="";
        if (word1Indexes[0] > word2Indexes[0]) {
            result+=mot2.charAt(word2Indexes[0]);
        } else {
            result+=mot1.charAt(word1Indexes[0]);
        }

        if (word1Indexes[1] > word2Indexes[1]) {
            result+=mot1.charAt(word2Indexes[1]);
        } else {
            result+=mot2.charAt(word1Indexes[1]);
        }


        return result;

    }

    private int[] findIndexesForDigit(String string) {
        int[] indices = {-1, -1};

        for (int i = 0; i < string.length(); i++) {
            char caractere = string.charAt(i);
            if (Character.isDigit(caractere)) {
                if (indices[0] == -1) {
                    indices[0] = i;
                }
                indices[1] = i;
            }
        }

        return indices;

    }
     */


    public int getFirstLastDigit(String string) {
        String onlyDigit = "";

        for (int i=0; i<string.length(); i++) {
            if (isDigit(String.valueOf(string.charAt(i)))) {
                onlyDigit += string.charAt(i);
            }
        }


        int firstDigit = Character.getNumericValue(onlyDigit.charAt(0));
        int lastDigit = Character.getNumericValue(onlyDigit.charAt(onlyDigit.length()-1));
        System.out.println("on additionne :"+firstDigit*10+" et "+lastDigit);

        return firstDigit*10+lastDigit;

    }

    public boolean isDigit(String lettre) {
        String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        return Arrays.stream(digits).toList().contains(lettre);
    }

}
