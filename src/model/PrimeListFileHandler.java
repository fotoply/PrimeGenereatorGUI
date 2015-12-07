package model;

import javax.management.InstanceNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 12/1/15
 *
 * @author Niels Norberg
 */
public class PrimeListFileHandler {

    private File file;

    public void setFile(File file) {
        if (file == null || file.isDirectory() || !file.canRead() || !file.canWrite()) {
            throw new IllegalArgumentException("The file is not valid");
        }

        this.file = file;
    }

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
     * @return a list of integers loaded from the file
     * @throws FileNotFoundException is thrown if the file was not found
     */
    public ArrayList<Integer> load() throws InstanceNotFoundException {
        if (file == null) {
            throw new InstanceNotFoundException("Please set the file first.");
        }

        ArrayList<Integer> ints = new ArrayList<>();
        BufferedReader reader = null; // Load the specified file
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line = reader.readLine(); // Read the first line of the file

            if (line != null && !line.contains(",")) { // If the file contains no commas, read it as a line separated file
                do {
                    ints.add(Integer.parseInt(line));
                } while ((line = reader.readLine()) != null);
            } else if (line != null && line.contains(",")) { // If the file contains any commas, read it as comma separated
                for (String object : line.split(",")) {
                    ints.add(Integer.parseInt(object));
                }
            }

            reader.close(); // Close the reader again
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ints;
    }

    /**
     * Saves a given integer on a new line in the file given.
     *
     * @param prime the integer to save
     */
    public void save(int prime) throws InstanceNotFoundException {
        if (file == null) {
            throw new InstanceNotFoundException("Please set the file first.");
        }
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // Create a write for the file
            writer.println(prime); // Write the new number to the buffer
            writer.flush(); // Flush the buffer to disk
            writer.close(); // Close the writer again to avoid it taking up memory and avoid memory leaks
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a list of integers to the disk in the given file. Will append
     *
     * @param numbers a list of integers to write to disk
     */
    public void save(List<Integer> numbers) throws InstanceNotFoundException {
        try {
            if (file == null) {
                throw new InstanceNotFoundException("Please set the file first.");
            }
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // Create a write for the file
            numbers.forEach(writer::println); // For each number in the list of numbers, add it to the buffer.
            writer.flush(); // Make sure that it is actually written to disk by flushing the buffer
            writer.close(); // Close the writer again to avoid it taking up memory and avoid memory leaks
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
