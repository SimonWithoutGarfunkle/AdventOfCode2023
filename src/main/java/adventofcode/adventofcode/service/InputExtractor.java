package adventofcode.adventofcode.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputExtractor {

    public List<String> extractFileToList(String fileName) {

        List<String> result = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return result;

    }

}
