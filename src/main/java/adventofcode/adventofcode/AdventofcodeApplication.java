package adventofcode.adventofcode;

import adventofcode.adventofcode.service.InputExtractor;
import adventofcode.adventofcode.solving.DayOne;
import adventofcode.adventofcode.solving.DayThree;
import adventofcode.adventofcode.solving.DayTwo;
import adventofcode.adventofcode.solving.Sample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AdventofcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdventofcodeApplication.class, args);

		InputExtractor extractor = new InputExtractor();
		List<String> input = extractor.extractFileToList("src/main/resources/inputs/day3input1.txt");

		System.out.println("La liste contient : "+input.size()+" éléments");
		System.out.println("premiere ligne :"+input.get(0));


		/****************************************************
		 ****************************************************
		 ****************  Lets get to it   *****************
		 ****************************************************
		 ****************************************************/

		DayThree victory = new DayThree();
		victory.showMatrix(victory.generateMatrix(input));
		System.out.println("la somme des nombres est :"+victory.checkMatrix(victory.generateMatrix(input)));






	}





}
