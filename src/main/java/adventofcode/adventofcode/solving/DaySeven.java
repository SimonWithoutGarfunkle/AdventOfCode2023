package adventofcode.adventofcode.solving;

import lombok.AllArgsConstructor;

import java.util.*;

public class DaySeven {

    private List<Hand> hands;

    public DaySeven(List<String> input) {
        hands = initializeHands(input);

    }

    private List<Hand> initializeHands(List<String> input) {
        List<Hand> initializedHands = new ArrayList<>();
        for (String line : input) {
            String[] lineInArray = line.split(" ");
            Hand hand = new Hand(lineInArray[0], Integer.parseInt(lineInArray[1]), 0L);
            initializedHands.add(hand);
        }

        /*
        List<Hand> initializedHands = input.stream().map(line -> {
            String[] lineInArray = line.split(" ");
            return new Hand(lineInArray[0], Integer.parseInt(lineInArray[1]), 0L);
        }).toList();

         */
        System.out.println("Hands initialized : "+initializedHands.size());
        return initializedHands;
    }

    @AllArgsConstructor
    public static class Hand {
        String cards;
        int bid;
        long score;

    }

    private void printResult() {
        hands.sort(Comparator.comparingLong(hand -> hand.score));
        long result =0L;

        for (int i=1; i<= hands.size(); i++) {
            result += i*hands.get(i-1).bid;
        }
        System.out.println("And the answer is ...  ==> "+result);
    }

    public void puzzle1() {
        for (Hand hand : hands) {
            hand.score = scoreHand(hand);
            System.out.println("hand : "+hand.cards+" has score of : "+hand.score);
        }
        printResult();

    }


    // We use a 11 digit scoring system to evaluate a hand :
    // the first digit represents the type of hand, from 7 - the strongest, to 1 - the lowest
    // every 2 following number represents each of the 5 cards in hand so we can compare 2 hands with the same type
    public long scoreHand (Hand hand) {
        long[] count = new long[5];
        long score =0;
        for (int i = 0; i < hand.cards.length(); i++) {
            int finalI = i;
            count[i] = hand.cards.chars().filter(ch -> ch == hand.cards.charAt(finalI)).count();
        }

        score =+ scoreFromCardsValues(hand.cards);

        //If we have a five of a kind
        if (Arrays.stream(count).max().orElse(0L)==(5L)) {
            score += 70000000000L;
        //If we have a four of a kind
        } else if (Arrays.stream(count).max().orElse(0L)==(4L)) {
            score += 60000000000L;
        } else if (contains2and3(count)) {
            score += 50000000000L;
        //If we have 3 copies of a card but nor 2 of the other card because full is filtered with the test before
        } else if (Arrays.stream(count).max().orElse(0L)==(3L)) {
            score += 40000000000L;
        } else if (contains2pairs(count)) {
            score += 30000000000L;
        } else if (Arrays.stream(count).max().orElse(0L)==(2L)) {
            score += 20000000000L;
        } else {
            score += 10000000000L;
        }

        return score;
    }

    //Score the 5 cards of the hand on a 10 digit system with 2 digit for each card
    public long scoreFromCardsValues(String hand) {
        long result =0;
        Map<String, String> cartesValeurs = new HashMap<>();

        // Génération des valeurs 02 a 09 pour les cartes de 2 au 9
        for (int i = 2; i < 10; i++) {
            cartesValeurs.put(String.valueOf(i), "0"+String.valueOf(i));
        }
        cartesValeurs.put("T", "10");
        //J value moved from 11 to 01 for question 2
        cartesValeurs.put("J", "01");
        cartesValeurs.put("Q", "12");
        cartesValeurs.put("K", "13");
        cartesValeurs.put("A", "14");

        String stringResult ="";
        for (int j = 0; j < hand.length(); j++) {
            stringResult += cartesValeurs.get(""+hand.charAt(j));
        }

        result = Long.parseLong(stringResult);
        return result;
    }

    //Return true means we have a full
    public boolean contains2and3(long[] numbers) {
        return Arrays.stream(numbers)
                .anyMatch(value -> value == 2L) && Arrays.stream(numbers)
                .anyMatch(value -> value == 3L);
    }

    //Return true means we have 2 pairs
    public boolean contains2pairs(long[] numbers) {
        return Arrays.stream(numbers)
                .filter(value -> value == 2L)
                .count()==4L;
    }


    public void puzzle2() {
        for (Hand hand : hands) {
            hand.score = scoreHandForPuzzle2(hand);
            System.out.println("hand : "+hand.cards+" has score of : "+hand.score);
        }
        printResult();

    }

    // Je garde le code dupliqué pour mettre en avant les differences et mieux voir les differences
    public long scoreHandForPuzzle2 (Hand hand) {
        // Je rajoute une 6e case pour contenir le nombre de J
        long[] count = new long[6];
        long score =0;
        for (int i = 0; i < hand.cards.length(); i++) {
            int finalI = i;
            //Le joker est traité differement on saute ce cas
            if (hand.cards.charAt(finalI)=='J') {continue;}
            count[i] = hand.cards.chars().filter(ch -> ch == hand.cards.charAt(finalI)).count();
        }

        count[5] = hand.cards.chars().filter(ch -> ch == 'J').count();

        //System.out.println(Arrays.toString(count));

        score =+ scoreFromCardsValues(hand.cards);

        long maxOfCount = Arrays.stream(Arrays.copyOf(count, 5)).max().orElse(0L)+count[5];

        //If we have a five of a kind
        if (maxOfCount >= 5L) {
            score += 70000000000L;
            //If we have a four of a kind
        } else if (maxOfCount == 4L) {
            score += 60000000000L;
        } else if (fullPuzzle2(count)) {
            score += 50000000000L;
            //If we have 3 copies of a card but nor 2 of the other card because full is filtered with the test before
        } else if (maxOfCount == 3L) {
            score += 40000000000L;
        } else if (twoPairsPuzzle2(count)) {
            score += 30000000000L;
        } else if (maxOfCount == 2L) {
            score += 20000000000L;
        } else {
            score += 10000000000L;
        }

        return score;
    }

    // les seules combinaisons qui donnent un full sont :
    // 2 paires + J
    // full classique avec 0 J
    public boolean fullPuzzle2(long[] numbers) {
        boolean result = (contains2pairs(Arrays.copyOf(numbers, 5)) && numbers[5]==1L) ||
                (contains2and3(Arrays.copyOf(numbers, 5)) && numbers[5]==0L);
        //System.out.println("test full : "+result);
        return result;
    }

    // la seule combinaison qui donne 2 paires est :
    // 2 paires avec 0 J
    // Si j'ai 1 paire et au moins 1J j'aurai au moins un Brelan
    public boolean twoPairsPuzzle2(long[] numbers) {
        boolean result =contains2pairs(Arrays.copyOf(numbers, 5)) && numbers[5]==0L;
        //System.out.println("test 2 paires : "+result);
        return result;
    }

}
