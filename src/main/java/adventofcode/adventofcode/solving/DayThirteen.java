package adventofcode.adventofcode.solving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayThirteen {

    //Contient le fichier input
    private final List<char[][]> maps;


    //Parametre d'ajustement entre la question 1 et la question 2
    //Pour la question 1, on cherche une symetrie parfaite donc avec 0 difference
    //Pour la question 2, on cherche une symetrie à 1 case pret donc difference =1
    private int difference = 0;

    public DayThirteen(List<String> input) {
        maps = readInput(input);
    }

    public List<char[][]> readInput(List<String> input) {
        List<char[]> onBuildingMap = new ArrayList<>();
        List<char[][]> finalMap = new ArrayList<>();
        for (String line : input) {
            if (!line.isBlank()) {
                onBuildingMap.add(line.toCharArray());
            }
            else {
                // Créer une copie du tableau actuel pour éviter les modifications de onBuildingMap n'impactent finalMap
                char[][] copyOfMap = new char[onBuildingMap.size()][onBuildingMap.get(0).length];
                for (int i = 0; i < onBuildingMap.size(); i++) {
                    copyOfMap[i] = onBuildingMap.get(i).clone();
                }
                finalMap.add(copyOfMap);
                onBuildingMap.clear();
            }
        }
        if (!onBuildingMap.isEmpty()) {
            char[][] copyOfMap = new char[onBuildingMap.size()][onBuildingMap.get(0).length];
            for (int i = 0; i < onBuildingMap.size(); i++) {
                copyOfMap[i] = onBuildingMap.get(i).clone();
            }
            finalMap.add(copyOfMap);
        }
        return finalMap;
    }


    //Test chaque axe de symetrie pour les lignes d'une map
    public int checkHorizontalMatrix(char[][] matrix) {
        int result;
        for (int i = 1; i < matrix[0].length; i++) {
            result = 0;
            for (char[] line : matrix) {
                result += checkHorizontalLine(line, i);
            }
            if (result == difference) {
                System.out.println("symetrie horizontale trouvee : "+i);
                return i;
            }
        }
        System.out.println("pas de symetrie horizontale");
        return -1;
    }

    //Test la symetrie de l'axe en parametre pour la ligne en parametre et retourne le nombre de difference
    //Symetrie parfaite = 0 difference
    public int checkHorizontalLine(char[] line, int column) {
        int differences = 0;
        if ( column <= (line.length/2)) {
            for (int j = 0; j < column; j++) {
                if (!(line[j] == line[2 * column - j - 1])) {
                    differences++;
                }
                //On cherche 0 difference pour la question 1 et 1 pour la question 2
                // donc dès qu'on en a plus que 1 on sait que la valeur ne sera pas valide
                if (differences > 1) {
                    return differences;
                }
            }
        } else {
            for (int j = column ; j < line.length ; j++) {
                if (!(line[j] == line[2 * column - j - 1])) {
                    differences++;
                }
                if (differences > 1) {
                    return differences;
                }
            }
        }
        return differences;
    }

    public int checkVerticalLine(char[][] matrix, int line) {
        int result =0;
        if (line <= matrix.length/2) {
            for (int i = 0; i < matrix[0].length; i++) {
                for (int j = 0; j < line; j++) {
                    if (!(matrix[j][i] == matrix[2*line - j - 1][i])) {
                        result++;
                    }
                }
            }
        } else {
            for (int i = 0; i < matrix[0].length; i++) {
                for (int j = line; j < matrix.length; j++) {
                    if (!(matrix[j][i] == matrix[2*line - j - 1][i])) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    private int checkVerticalMatrix(char[][] map) {
        int result = 0 ;
        for (int i = 1; i < map.length; i++) {
            result = checkVerticalLine(map, i);
            if (result == difference) {
                System.out.println("symetrie verticale trouvee : "+i);
                return i;
            }
        }
        System.out.println("pas de symetrie verticale ");
        return -1;
    }

    public void puzzle2() {
        difference = 1;
        puzzle1();
    }

    public void puzzle1() {
        int i = 1;
        int answer=0;
        int horizontale = 0;
        int vertical = 0;
        for (char[][] map : maps) {
            System.out.println("working on map n°"+i);
            horizontale = checkHorizontalMatrix(map);
            vertical = checkVerticalMatrix(map);
            if ( horizontale > 0) {
                answer += horizontale;
            } else if(vertical > 0) {
                answer+= (100 * vertical);
            } else {
                System.out.println("erreur avec horizontal="+horizontale+"  et vertical="+vertical);
            }
            i++;
        }

        System.out.println("And the answer is ... --> "+answer);


    }

    public void printMaps() {
        for (char[][] map : maps) {
            for (char[] row : map) {
                for (char c : row) {
                    System.out.print(c + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void printMatrix(char[][] matrice) {
        for (char[] row : matrice) {
            for (char element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

}
