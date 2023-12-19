package adventofcode.adventofcode.solving;

import java.util.List;

public class DaySixteen {

    char[][] matrix;

    int[] up = new int[]{-1, 0};
    int[] right = new int[]{0, 1};
    int[] down = new int[]{1, 0};
    int[] left = new int[]{0, -1};

    public DaySixteen(List<String> input) {
        matrix = generateMatrix(input);
    }

    private char[][] generateMatrix(List<String> input) {
        matrix = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = input.get(i).toCharArray();
        }
        System.out.println("Matrix size is "+matrix.length+"/"+matrix[0].length);
        return matrix;
    }

    public void puzzle1() {
        int[] start = new int[]{0, 0, 0, 1};
    }

    public int[] lastMove(int[] input) {
        if (input[2] == input[0]) {
            if (input[3] > input[1]) {
                return right;
            } else {
                return left;
            }
        } else if (input[0] > input[2]) {
            return down;
        }
        return up;
    }
}
