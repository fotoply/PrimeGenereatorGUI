package model;

import java.util.ArrayList;

/**
 * @author Niels Norberg
 */
public class OldPrimeGenerator {

    /*public static void main(String[] args) {
        SmarterPrimes smarterPrimes = new SmarterPrimes();
        long startTime = System.nanoTime();
        ArrayList<Integer> list = smarterPrimes.prime(10000000);
        long endTime = System.nanoTime();
        File output = new File("output.txt");
        try (FileOutputStream printer = new FileOutputStream(output)){ // Try catch the creating of the printer and autoclose it when done
            BufferedWriter stream = new BufferedWriter(new OutputStreamWriter(printer));
            for (int i = 0; i < list.size()-1; i++) {
                stream.write(list.get(i)+"");
                stream.write(','); // Write all the primes to file, seperated by comma
            }
            stream.write(list.get(list.size() - 1) + ""); // Write the last prime in the list
            stream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(list.get(list.size() - 1));
        System.out.println("Total count: " + list.size());
        System.out.println("Total time: " + (endTime-startTime) + " nanoseconds");
    }*/

    /**
     * Function for calculating primes between 0 and max.
     *
     * @param max The maximum number to search up to
     * @return Returns a list of integers which are between 0 and max and which are primes
     */
    public ArrayList<Integer> prime(int max) {
        ArrayList<Integer> primes = new ArrayList<Integer>();
        primes.add(2); // Add 2 as our base prime
        for (int i = 3; i < max; i += 2) { // Iterate through all odd numbers from 3 to max
            boolean maybePrime = true;
            int sqrt = (int) (0.5 * (600.0 + (i / 600.0))); // Rough estimate of the sqrt of the number i
            for (int j = 0; j < primes.size(); j++) { // Iterate through the list of known primes
                int listElement = primes.get(j); // Current element in the list of known primes
                if (listElement > sqrt) { // IF the current element is larger than the sqrt of it, then it cannot be a primefactor for it
                    break;
                }
                if (i % listElement == 0) { // IF i modulus with the current prime is equal to 0, AKA it equally divides, then it is not a prime
                    maybePrime = false;
                    break;
                }
            }
            if (maybePrime) { // IF it was never found to be not a prime, add it to the list of known primes
                primes.add(i);
            }
        }
        return primes;
    }
}
