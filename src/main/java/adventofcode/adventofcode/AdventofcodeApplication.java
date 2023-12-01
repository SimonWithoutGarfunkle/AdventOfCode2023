package adventofcode.adventofcode;

import adventofcode.adventofcode.service.InputExtractor;
import adventofcode.adventofcode.solving.DayOne;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AdventofcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdventofcodeApplication.class, args);

		InputExtractor extractor = new InputExtractor();
		List<String> input = extractor.extractFileToList("src/main/resources/inputs/day1input2.txt");

		System.out.println("Liste de éléments : "+input.size());
		System.out.println("premier mot :"+input.get(0));



		DayOne victory = new DayOne();
		System.out.println("la réponse est "+victory.firstTest(input));


	}





}
