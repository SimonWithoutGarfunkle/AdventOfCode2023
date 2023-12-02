package adventofcode.adventofcode.solving;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Je découpe en plein de petites methodes utilitaires, meme ce n'est pas réutilisé pour les exercices des prochains jours
 */
public class DayTwo {


    public class Game {
        public int id;
        public List<Cube> cubes;
    }

    public class Cube {
        public int quantity;
        public String color;
    }


    // On additionne l'id de toutes les games valides
    public int solution1(List<String> input) {
        int result = 0;
        for (String item : input) {
            Game game = convertArrayToGame(readLine(item));
            if (isGamePossibleforPart1(getMaxOfGame(game))) {
                result+=game.id;
            }
        }

        return result;
    }

    public int solution2(List<String> input) {
        int result = 0;
        for (String item : input) {
            int[] maxOfGame = getMaxOfGame(convertArrayToGame(readLine(item)));
            result += (maxOfGame[0]*maxOfGame[1]*maxOfGame[2]);
        }
        return result;

    }

    public String[] readLine(String string) {
        String[] parts = string.split("[:,;]");
        return parts;
    }

    public Game convertArrayToGame(String[] array) {
        Game game = new Game();
        game.cubes = new ArrayList<>();
        game.id = getDigits(array[0]);
        for (int i=1; i<array.length; i++) {
            Cube cube = new Cube();
            cube.quantity=getDigits(array[i]);
            cube.color=getCharacters(array[i]);
            game.cubes.add(cube);
        }
        return game;

    }

    public int[] getMaxOfGame(Game game) {
        int[] result = new int[3];
        int maxBlue=0;
        int maxRed=0;
        int maxGreen=0;

        for (Cube cube : game.cubes) {
            switch (cube.color) {
                case "blue":
                    if (cube.quantity > maxBlue) {
                        maxBlue = cube.quantity;
                    }
                    break;
                case "green":
                    if (cube.quantity > maxGreen) {
                        maxGreen = cube.quantity;
                    }
                    break;
                case "red":
                    if (cube.quantity > maxRed) {
                        maxRed = cube.quantity;
                    }
                    break;
                default :
                    System.out.println("ERROR : unknown color !");
            }
        }

        result[0]=maxRed;
        result[1]=maxGreen;
        result[2]=maxBlue;

        return result;
    }

    public boolean isGamePossibleforPart1(int[] max) {
        int maxRed=max[0];
        int maxGreen=max[1];
        int maxBlue=max[2];

        return (maxRed <= 12 && maxGreen <= 13 && maxBlue <= 14);
    }


    // Vérification des données en entrée mais toutes les données sont propres
    public boolean checkDigitsRule(String input) {
        //On vérifie qu'il y a bien au moins un chiffre dans le string
        Pattern pattern = Pattern.compile(".*\\d+.*");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            //On vérifie qu'il n'y a que chiffres consécutifs sans autre caractère entre eux
            Pattern patternDigits = Pattern.compile("\\D*(\\d+)\\D*");
            Matcher matcherDigits = patternDigits.matcher(input);

            return matcherDigits.matches();
        }
        return false;
    }

    // On recupere la valeur numérique de la couleur ou de l'id de la game
    public int getDigits(String string) {
        String result = string.replaceAll("[^0-9]", "");
        return Integer.parseInt(result);
    }

    // On recupere la couleur
    public String getCharacters(String string) {
        String result = string.replaceAll("[0-9]", "");
        return result.trim();
    }

}
