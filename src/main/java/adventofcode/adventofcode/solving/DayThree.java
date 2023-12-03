package adventofcode.adventofcode.solving;

import java.util.ArrayList;
import java.util.List;

public class DayThree {

    public char[][] generateMatrix(List<String> input) {
        char[][] result = new char[140][140];
        int x=0;
        int y=0;
        int i=0;
        for (String line : input) {
            result[i] = line.toCharArray();
            i++;
        }
        return result;
    }

    public void showMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            // Nouvelle ligne pour chaque ligne de la matrice
            System.out.println();
        }
    }

    // Premiere etape On additionne tous les nombres de la matrice sans prendre en compte les symboles
    // Juste pour voir si on arrive a parcourir et lire la matrice
    public int checkMatrixWithoutSymbol(char[][] matrix) {
        int result=0;
        String partResult="";

        for (int i =0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (Character.isDigit(matrix[i][j])) {
                    while (j < matrix[i].length && Character.isDigit(matrix[i][j])) {
                        partResult += matrix[i][j];
                        j++;
                    }
                }
                if (!partResult.isEmpty()) {
                    System.out.println("on est en "+i+"/"+j+" et on ajoute : "+Integer.parseInt(partResult));
                    result += Integer.parseInt(partResult);
                    partResult = "";
                }

            }
        }
        return result;
    }

    // Solution du puzzle 1
    public int checkMatrix(char[][] matrix) {
        int result=0;
        String partResult="";
        int check=0;

        for (int i =0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (Character.isDigit(matrix[i][j])) {
                    while (j < matrix[i].length && Character.isDigit(matrix[i][j])) {
                        // On vérifie la validité de chaque chiffre qui compose le nombre
                        check += checkCase(matrix, i, j);
                        partResult += matrix[i][j];
                        j++;
                    }
                }
                // Si un seul chiffre est valide tout le nombre est valide
                if (check>0) {
                    result += Integer.parseInt(partResult);

                }
                // On réinitialise les compteurs avant de passer a la boucle suivante
                check=0;
                partResult="";
            }
        }
        return result;
    }

    // On test si la case est valide
    public int checkCase (char[][] matrix, int i, int j) {
        int compteur=0;
        for (int xpart=-1; xpart<2 ; xpart++) {
            for (int ypart=-1; ypart<2; ypart++) {
                try {
                    //Si c'est ni un chiffre, ni un point c'est un symbole
                    if (!Character.isDigit(matrix[i + xpart][j + ypart]) && matrix[i + xpart][j + ypart] != '.') {
                        System.out.println("on test : "+matrix[i + xpart][j + ypart]+" en "+(i + xpart)+"/"+(j + ypart));
                        compteur++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {}
            }
        }
        return compteur;
    }



    public void controleData(List<String> input) {
        System.out.println("nombre de ligne : "+input.size());
        input.forEach(string -> System.out.println(string.length()));

    }
}
