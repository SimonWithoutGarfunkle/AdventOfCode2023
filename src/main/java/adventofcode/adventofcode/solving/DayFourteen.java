package adventofcode.adventofcode.solving;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayFourteen {

    private final char[][] matrix;


    public DayFourteen(List<String> input) {
        matrix = readInput(input);
    }

    public char[][] readInput(List<String> input) {
        char[][] map = new char[input.size()][input.get(0).length()];
        int compteur =0;
        for (String line : input) {
            map[compteur] = line.toCharArray();
            compteur++;
        }
        return map;
    }

    public void puzzle1() {
        rollNorth();
        long result = scoreMatrice();
        System.out.println("And the answer is ... --> "+result);
    }

    // J'ai identifié une boucle a partir du cycle 119 de 13 cycles (le 119 = 106 )
    // 106 + (1.000.000.000 - 106)%13  = 116
    public void puzzle2() {
        Map<String, Integer> states = new HashMap<>();
        int cycles = 0;
        while (cycles  < 116) {
            String currentState = matrixToString(matrix);
            if (states.containsKey(currentState)) {
                System.out.println("--->  Loop found! Cycle n°" + cycles + ". Loop to : " + states.get(currentState));
            } else {
                states.put(currentState, cycles);
            }

            // pour simuler les mouvements Est, Sud et Ouest, je fais tourner la matrice de 90° et je fais un mouvement Nord
            for (int i =0; i < 4; i++) {
                rollNorth();
                rotateMatrixClockwise();
            }
            cycles++;
        }
        System.out.println("And the answer is ... --> "+scoreMatrice());

    }

    public long scoreMatrice() {
        long score =0;
        for (int i =0; i < matrix.length; i++) {
            score +=  (new String(matrix[i]).chars().filter(ch -> ch=='O').count() * (matrix.length - i));
        }
        return score;
    }

    // Pour comparer les Arrays multi dimensionnels je les convertis en String
    private String matrixToString(char[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : matrix) {
            sb.append(Arrays.toString(row));
        }
        return sb.toString();
    }


    //Décalle les O vers le haut
    //Le cas de plusieurs O superposé n'est pas bien géré d'ou l'appel en boucle
    public void rollNorth() {
        int movement;

        do {
            movement=0;
            for (int i = matrix.length - 1; i >0; i--) {
                for (int j = matrix[0].length - 1 ; j >= 0; j--) {
                    if (matrix[i][j] == 'O' && matrix[i-1][j] == '.') {
                        matrix[i][j] = '.';
                        matrix[i-1][j] = 'O';
                        movement++;
                    }
                }
            }

        } while (movement > 0);

    }

    public void rotateMatrixClockwise() {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] rotatedMatrix = new char[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[j][rows - 1 - i] = matrix[i][j];
            }
        }

        // Mettre à jour la matrice originale avec la matrice pivotée
        for (int i = 0; i < cols; i++) {
            System.arraycopy(rotatedMatrix[i], 0, matrix[i], 0, rows);
        }
    }

    public char[][] cloneMatrix(char[][] originalMatrix) {
        int rows = originalMatrix.length;
        char[][] clonedMatrix = new char[rows][];

        for (int i = 0; i < rows; i++) {
            clonedMatrix[i] = Arrays.copyOf(originalMatrix[i], originalMatrix[i].length);
        }

        return clonedMatrix;
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
