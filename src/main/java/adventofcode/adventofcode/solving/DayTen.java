package adventofcode.adventofcode.solving;

import java.util.List;

public class DayTen {
    char[][] matrix;
    int[] start;

    public DayTen(List<String> input) {
        matrix = generateMatrix(input);
        start = getStart(matrix);
    }

    private char[][] generateMatrix(List<String> input) {
        matrix = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = input.get(i).toCharArray();
        }
        System.out.println("Matrix size is "+matrix.length+"/"+matrix[0].length);
        return matrix;

    }

    private int[] getStart(char[][] matrix) {
        int[] result = new int[2];
        for (int i=0; i < matrix.length; i++) {
            for (int j=0; j < matrix[i].length; j++) {
                if (matrix[i][j]=='S') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    //On travaille avec un int[4] qui contient la position x/y précédente dans les 2 premieres cases
    // et la position x/y actuelle dans les 2 cases suivantes pour déterminer dans quel sens prendre le tuyau
    // chaque tuyau n'a que 2 coté, dont celui d'où l'on vient
    public void puzzle1() {
        int count=1;
        int[] coordinates = new int[]{start[0], start[1], start[0] + 1, start[1]};

        while (!(matrix[coordinates[2]][coordinates[3]] == 'S')) {
            System.out.println(coordinates[0]+"/"+coordinates[1]);
            //System.out.println("moving from : "+matrix[coordinates[0]][coordinates[1]]+" --> to : "+matrix[coordinates[2]][coordinates[3]]);
            switch (matrix[coordinates[2]][coordinates[3]]) {
                case '|' :
                    if (coordinates[2] > coordinates[0]) {
                        coordinates = down(coordinates);
                    } else {
                        coordinates = up(coordinates);
                    }
                    break;
                case 'L' :
                    if (coordinates[2] > coordinates[0]) {
                        coordinates = right(coordinates);
                    } else {
                        coordinates = up(coordinates);
                    }
                    break;
                case '-' :
                    if (coordinates[3] > coordinates[1]) {
                        coordinates = right(coordinates);
                    } else {
                        coordinates = left(coordinates);
                    }
                    break;
                case 'J' :
                    if (coordinates[2] > coordinates[0]) {
                        coordinates = left(coordinates);
                    } else {
                        coordinates = up(coordinates);
                    }
                    break;
                case '7' :
                    if (coordinates[3] > coordinates[1]) {
                        coordinates = down(coordinates);
                    } else {
                        coordinates = left(coordinates);
                    }
                    break;
                case 'F' :
                    if (coordinates[0] > coordinates[2]) {
                        coordinates = right(coordinates);
                    } else {
                        coordinates = down(coordinates);
                    }
                    break;
            }
            count++;

        }
        System.out.println("And the answer is ... ==> "+count);

    }

    private int[] up(int[] previous) {
        int[] afterMoving = new int[4];
        afterMoving[0] = previous[2];
        afterMoving[1] = previous[3];
        afterMoving[2] = previous[2]-1;
        afterMoving[3] = previous[3];
        //System.out.println("Going up !");
        return afterMoving;
    }

    private int[] right(int[] previous) {
        int[] afterMoving = new int[4];
        afterMoving[0] = previous[2];
        afterMoving[1] = previous[3];
        afterMoving[2] = previous[2];
        afterMoving[3] = previous[3]+1;
        //System.out.println("Going right !");
        return afterMoving;
    }

    private int[] down(int[] previous) {
        int[] afterMoving = new int[4];
        afterMoving[0] = previous[2];
        afterMoving[1] = previous[3];
        afterMoving[2] = previous[2]+1;
        afterMoving[3] = previous[3];
        //System.out.println("Going down !");
        return afterMoving;
    }

    private int[] left(int[] previous) {
        int[] afterMoving = new int[4];
        afterMoving[0] = previous[2];
        afterMoving[1] = previous[3];
        afterMoving[2] = previous[2];
        afterMoving[3] = previous[3]-1;
        //System.out.println("Going left !");
        return afterMoving;
    }




}
