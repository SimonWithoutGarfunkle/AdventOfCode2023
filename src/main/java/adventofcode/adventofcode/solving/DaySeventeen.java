package adventofcode.adventofcode.solving;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.*;

public class DaySeventeen {

    int[][] matrix;

    int[][] minSum;

    public DaySeventeen(List<String> input) {
        matrix = generateMatrix(input);
        minSum = findMinPath(matrix);
    }

    private int[][] generateMatrix(List<String> input) {
        matrix = new int[input.size()][input.get(0).length()];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = input.get(i).chars().map(Character::getNumericValue).toArray();
        }
        System.out.println("Matrix size is "+matrix.length+"/"+matrix[0].length);
        return matrix;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public class Move {
        //Column
        int c;

        //Row
        int r;

        // Direction du dernier mouvement
        Dir dir;

        //nombre de mouvement consecutif dans la meme direction
        int consecutif;
    }

    public enum Dir {
        GAUCHE,
        BAS,
        DROITE,
        HAUT
    }

    public void puzzle1() {
        List<Move> moves = new ArrayList<>();

        Move starter = new Move(0, 0, Dir.DROITE, 1);
        moves.add(starter);
        while (!(starter.c == matrix.length - 1 && starter.r == matrix[0].length - 1)) {
            System.out.println("Case value is : "+matrix[starter.c][starter.r]+" position is : "+starter.c+"/"+starter.r+" and moving : "+starter.dir);
            starter = movement(starter);
            moves.add(starter);
        }
        int result =0;
        for (Move movment : moves) {
            result+=matrix[movment.c][movment.r];
        }
        System.out.println("Le score apres "+moves.size()+" mouvements est de : "+result);

        printMatrix(minSum);

    }

    public Move movement(Move move) {
        List<Dir> autorizedMoves = possibleMove(move);
        Map<Dir, Integer> scoredDirection = new HashMap<>();
        for (Dir direction : autorizedMoves) {
            scoredDirection.put(direction, scoreDirection(move, direction));
        }
        Dir minKey = scoredDirection.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(Dir.BAS);

        Move nextMove = new Move(move.c, move.r, minKey, 1);
        if (minKey == move.dir) {
            nextMove.consecutif = move.consecutif + 1;
        }
        switch (minKey) {
            case HAUT -> nextMove.c--;
            case DROITE -> nextMove.r++;
            case BAS -> nextMove.c++;
            case GAUCHE -> nextMove.r--;
        }
        return nextMove;
    }

    public List<Dir> possibleMove(Move move) {
        List<Dir> autorizedMoves = new ArrayList<>(List.of(Dir.GAUCHE, Dir.BAS, Dir.DROITE, Dir.HAUT));
        if (move.consecutif == 3) {
            autorizedMoves.remove(move.dir);
        }
        if (move.c == 0) {
            autorizedMoves.remove(Dir.HAUT);
        }
        if (move.c == matrix.length - 1) {
            autorizedMoves.remove(Dir.BAS);
        }
        if (move.r == 0) {
            autorizedMoves.remove(Dir.GAUCHE);
        }
        if (move.r == matrix[0].length - 1) {
            autorizedMoves.remove(Dir.DROITE);
        }

        //Pas le droit de faire demi tour
        switch (move.dir) {
            case HAUT -> autorizedMoves.remove(Dir.BAS);
            case DROITE -> autorizedMoves.remove(Dir.GAUCHE);
            case BAS -> autorizedMoves.remove(Dir.HAUT);
            case GAUCHE -> autorizedMoves.remove(Dir.DROITE);
        }
        System.out.print(" Authorized : ");
        for (int i = 0; i < autorizedMoves.size(); i++) {
            System.out.print(autorizedMoves.get(i)+"/ ");
        }

        return autorizedMoves;
    }

    public int scoreDirection(Move move, Dir option) {
        switch (option) {
            case HAUT -> { return minSum[move.c - 1][move.r];}
            case DROITE -> { return minSum[move.c][move.r + 1];}
            case BAS -> { return minSum[move.c + 1][move.r];}
            default -> { return minSum[move.c][move.r - 1];}
        }
    }



    public static void printMatrix(int[][] array) {
        for (int i = 0; i < array.length; i++ ) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(" "+array[i][j]);
            }
            System.out.println();
        }
    }

    public static int[][] findMinPath(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] minSum = new int[rows][cols];
        minSum[rows - 1][cols - 1] = grid[rows - 1][cols - 1];

        // Remplissage de la dernière colonne avec les sommes minimales
        for (int i = rows - 2; i >= 0; i--) {
            minSum[i][cols - 1] = minSum[i + 1][cols - 1] + grid[i][cols - 1];
        }

        // Remplissage de la dernière ligne avec les sommes minimales
        for (int j = cols - 2; j >= 0; j--) {
            minSum[rows - 1][j] = minSum[rows - 1][j + 1] + grid[rows - 1][j];
        }

        // Calcul des sommes minimales pour les autres cases
        for (int i = rows - 2; i >= 0; i--) {
            for (int j = cols - 2; j >= 0; j--) {
                minSum[i][j] = grid[i][j] + Math.min(minSum[i + 1][j], minSum[i][j + 1]);
            }
        }
        return minSum;
    }
}
