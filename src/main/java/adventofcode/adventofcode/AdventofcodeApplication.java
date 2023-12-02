package adventofcode.adventofcode;

import adventofcode.adventofcode.service.InputExtractor;
import adventofcode.adventofcode.solving.DayOne;
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
		List<String> input = extractor.extractFileToList("src/main/resources/inputs/day2input1.txt");

		System.out.println("Liste de éléments : "+input.size());
		System.out.println("premier mot :"+input.get(0));

		DayTwo victory= new DayTwo();
		System.out.println(victory.solution1(input));

		System.out.println(victory.solution2(input));




	}





}
