package model;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created 12/1/15
 *
 * @author Niels Norberg
 */
public class PrimeListFileHandler {

    /**
     * Loads the given file and parses it for a list of integers to be loaded.
     * The list can either be separated by commas or by newlines, but not by both.
     * <br></br>
     * <i>Valid example:</i> <br></br>
     * <i color="green">2,3,5,7,11</i>
     * <br></br>
     * <i>Invalid example:</i> <br></br>
     * <i color="red">2,3<br></br>
     * 5,7</i>
     *
     * @param file The file to read the data from
     * @return a list of integers loaded from the file
     * @throws FileNotFoundException is thrown if the file was not found
     */
    public ArrayList<Integer> load(File file) throws FileNotFoundException {
        ArrayList<Integer> ints = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file)); // Load the specified file
        try {
            String line = reader.readLine(); // Read the first line of the file

            if (line != null && !line.contains(",")) { // If the file contains no commas, read it as a line separated file
                do {
                    ints.add(Integer.parseInt(line));
                } while ((line = reader.readLine()) != null);
            } else if(line != null && line.contains(",")) { // If the file contains any commas, read it as comma separated
                for (String object: line.split(",")) {
                    ints.add(Integer.parseInt(object));
                }
            }

            reader.close(); // Close the reader again
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ints;
    }

    public void save(int prime, File file) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // Create a write for the file
            writer.println(prime); // Write the new number to the file
            writer.close(); // Close the writer again to avoid it taking up memory and avoid memory leaks
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
