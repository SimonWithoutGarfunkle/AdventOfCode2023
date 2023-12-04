package adventofcode.adventofcode.solving;

import java.util.ArrayList;
import java.util.List;

public class DayFour {

    public class Game {
        public int id;
        public List<Integer> winningNumbers;
        public List<Integer> card;
        public int quantity=1;
        public int score;
    }

    public int solvingPuzzle2 (List<String> input) {
        List<Game> puzzle2 = new ArrayList<>();
        for (String item : input) {
            Game game = convertInputToGame(item);
            scoreGame(game);
            puzzle2.add(game);
        }

        for (int i = 0; i < puzzle2.size(); i++) {
            Game currentGame = puzzle2.get(i);
            int matches = currentGame.score;
            System.out.println("La game n°"+currentGame.id+ " a un score de : "+currentGame.score+" et une quantité de "+currentGame.quantity);

            // on actualise les quantité des X games suivantes, où X = score de la currentGame
            for (int j = i + 1; j < i+1+currentGame.score; j++) {
                    // on augmente la quantité de la quantité de currentGame pour prendre en compte le coté "démultiplié"
                    puzzle2.get(j).quantity+= puzzle2.get(i).quantity;

            }
        }

        // on additionne la quantité de chaque Game
        return puzzle2.stream().mapToInt(game -> game.quantity).sum();

    }

    public int solvingPuzzle1 (List<String> input) {
        int result =0;
        for (String item : input) {
            Game game = convertInputToGame(item);
            scoreGame(game);
            if (game.score>0) {
                System.out.println("La game n°"+game.id+ " a un score de : "+game.score);
                result+= (int) Math.pow(2D, game.score-1);

            }
        }
        return result;
    }

    // Compte le nombre de numéro gagnant apparaissant dans la game et ajoute le score a la game
    public Game scoreGame(Game game) {
        int result=0;
        for (Integer i : game.card) {
            if (game.winningNumbers.contains(i)) {
                result++;
            }
        }
        game.score=result;
        return game;
    }


    // Extrait les données de la ligne pour créé un objet Game avec
    public Game convertInputToGame(String line) {
        //System.out.println("working on line : "+line);
        List<Integer> winningNumbers = new ArrayList<>();
        List<Integer> card = new ArrayList<>();
        Game game = new Game();

        String[] parts = line.split("[:|]");
        //System.out.println("line has parts : "+parts.length);
        game.id = getDigits(parts[0]);

        String[] winningParts = parts[1].split(" ");
        for (int i =0; i < winningParts.length; i++ ) {
            if (!winningParts[i].isBlank()) {
                winningNumbers.add(getDigits(winningParts[i]));
            }
        }

        String[] cardParts = parts[2].split(" ");
        for (int j =0; j < cardParts.length; j++ ) {
            if (!cardParts[j].isBlank()) {
                card.add(getDigits(cardParts[j]));
            }
        }
        game.winningNumbers=winningNumbers;
        game.card=card;

        return game;
    }

    // On recupere la valeur numérique du numero ou de l'id de la game
    public int getDigits(String string) {
        String result = string.replaceAll("[^0-9]", "");
        return Integer.parseInt(result);
    }


}
