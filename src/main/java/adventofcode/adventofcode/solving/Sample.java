package adventofcode.adventofcode.solving;

public class Sample {

    public int floorForSanta(String mot) {
        int result=0;
        int compteur=0;
        System.out.println("le mot est long de : "+mot.length());
            for (int i=0; i<mot.length(); i++) {
                if (result ==-1) {
                    break;
                }
                compteur=i;
                System.out.println("on est a l'etage : "+result);
                if (mot.charAt(i) == '(') {
                    result++;
                } else {
                    result--;
                }
            }


        return compteur+1;
    }
}
