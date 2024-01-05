package adventofcode.adventofcode.solving;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DaySixteen {

    char[][] matrix;

    List<Beam> beams;

    int compteur;


    public DaySixteen(List<String> input) {
        matrix = generateMatrix(input);
        beams = new ArrayList<>();
        compteur =0;
    }

    private char[][] generateMatrix(List<String> input) {
        matrix = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = input.get(i).toCharArray();
        }
        System.out.println("Matrix size is "+matrix.length+"/"+matrix[0].length);
        return matrix;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public class Beam {
        //Column
        int c;

        //Row
        int r;

        // Direction du dernier mouvement
        Dir dir;

    }

    public enum Dir {
        GAUCHE,
        BAS,
        DROITE,
        HAUT
    }

    public void puzzle1() {
        move(new Beam(0, 0, Dir.DROITE));
        System.out.println("And the answer is ... --> "+energised(beams));
    }

    // Bruteforce et ca passe
    public void puzzle2() {
        List<Integer> energisedScore = new ArrayList<>();
        for (int i =0; i < matrix.length; i++) {
            beams.clear();
            move(new Beam(i, 0, Dir.DROITE));
            energisedScore.add(energised(beams));
            beams.clear();
            move(new Beam(i, matrix[0].length -1, Dir.GAUCHE));
            energisedScore.add(energised(beams));
            beams.clear();
            move(new Beam(0, i, Dir.BAS));
            energisedScore.add(energised(beams));
            beams.clear();
            move(new Beam(matrix.length -1, i, Dir.HAUT));
            energisedScore.add(energised(beams));
        }
        System.out.println("And the answer is ... --> "+ Collections.max(energisedScore));

    }

    // Genere la matrice avec les cases energisees pour les compter
    // C'est pas tres performant pour avoir le resultat mais ca permet d'avoir le visuel
    public int energised(List<Beam> list) {
        char[][] energized = new char[matrix.length][matrix[0].length];
        for (int i = 0; i < energized.length; i++) {
            for (int j = 0; j < energized[0].length; j++) {
                energized[i][j] = '-'; // Remplir chaque case avec le caractère '.'
            }
        }
        for (Beam beam : list) {
            energized[beam.c][beam.r] = '#';
        }
        return StringUtils.countOccurrencesOf(Arrays.deepToString(energized), "#");
    }

    public void move(Beam starter) {
        compteur++;
        //System.out.println("Beams size:"+beams.size()+" and starter="+starter);
        if (!(starter == null)) {
            if (containBeam(starter)) {
            } else {
                beams.add(new Beam(starter.c, starter.r, starter.dir));
                //System.out.println("Beam -->  c:"+starter.c+" r:"+starter.r+" dir:"+starter.dir+" // char:"+matrix[starter.c][starter.r]);
                switch (matrix[starter.c][starter.r]) {
                    case '.':
                        if (starter.dir == Dir.BAS) { move(down(starter));
                        } else if (starter.dir == Dir.DROITE) { move(right(starter));
                        } else if (starter.dir == Dir.HAUT) { move(up(starter));
                        } else { move(left(starter)); } // dir = 'GAUCHE'
                        break;
                    case '-':
                        if (starter.dir == Dir.BAS) {
                            move(left(starter));
                            move(right(starter));
                        } else if (starter.dir == Dir.DROITE) { move(right(starter));
                        } else if (starter.dir == Dir.HAUT) {
                            move(left(starter));
                            move(right(starter));
                        } else { move(left(starter)); } // dir = 'GAUCHE'
                        break;
                    case '|':
                        if (starter.dir == Dir.BAS) { move(down(starter));
                        } else if (starter.dir == Dir.DROITE) {
                            move(up(starter));
                            move(down(starter));
                        } else if (starter.dir == Dir.HAUT) { move(up(starter));
                        } else { // dir = 'GAUCHE'
                            move(up(starter));
                            move(down(starter)); }
                        break;
                    case '/':
                        if (starter.dir == Dir.BAS) { move(left(starter));
                        } else if (starter.dir == Dir.DROITE) { move(up(starter));
                        } else if (starter.dir == Dir.HAUT) { move(right(starter));
                        } else { move(down(starter)); } // dir = 'GAUCHE'
                        break;
                    case '\\':
                        if (starter.dir == Dir.BAS) { move(right(starter));
                        } else if (starter.dir == Dir.DROITE) { move(down(starter));
                        } else if (starter.dir == Dir.HAUT) { move(left(starter));
                        } else { move(up(starter)); } // dir = 'GAUCHE'
                        break;
                }

            }

        }
    }

    public Beam up(Beam beam) {
        if (beam.c == 0) {
            //System.out.println("on est deja tout en haut");
            return null;
        }
        return new Beam(beam.c-1, beam.r, Dir.HAUT);
    }

    public Beam right(Beam beam) {
        if (beam.r == matrix[0].length - 1) {
            //System.out.println("on est deja tout a droite");
            return null;
        }
        return new Beam(beam.c, beam.r+1, Dir.DROITE);
    }

    public Beam down(Beam beam) {
        if (beam.c == matrix.length -1 ) {
            //System.out.println("on est deja tout en bas");
            return null;
        }
        return new Beam(beam.c +1, beam.r, Dir.BAS);
    }

    public Beam left(Beam beam) {
        if (beam.r == 0) {
            //System.out.println("on est deja tout a gauche");
            return null;
        }
        return new Beam(beam.c, beam.r-1, Dir.GAUCHE);
    }

    // Pour eviter les boucles
    public boolean containBeam(Beam beam) {
        for (Beam beamEnergised : beams) {
            if (beamEnergised.c == beam.c && beamEnergised.r == beam.r &&beamEnergised.dir == beam.dir ) {
                //System.out.println("deja parcouru !");
                return true;
            }
        }
        return false;
    }

}
