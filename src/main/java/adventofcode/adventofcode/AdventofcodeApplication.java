package adventofcode.adventofcode;

import adventofcode.adventofcode.service.InputExtractor;
import adventofcode.adventofcode.solving.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AdventofcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdventofcodeApplication.class, args);

		InputExtractor extractor = new InputExtractor();
		List<String> input = extractor.extractFileToList("src/main/resources/inputs/day9input1.txt");

		System.out.println("La liste contient : "+input.size()+" éléments");
		System.out.println("premiere ligne :"+input.get(0));

		/****************************************************
		 ****************************************************
		 ****************  Lets get to it   *****************
		 ****************************************************
		 ****************************************************/

		DayNine victory = new DayNine(input);
		victory.puzzle2();

	}

}
