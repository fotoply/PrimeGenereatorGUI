package model;

import javafx.collections.ObservableList;

import javax.management.InstanceNotFoundException;

/**
 * Created 12/2/15
 *
 * @author Niels Norberg
 */
public class PrimeGenerator {
    public int currentNumber = 1;
    public volatile boolean shouldRun = true;
    public ObservableList<Integer> primes;
    public PrimeListFileHandler fileHandler;

    public void run() throws InstanceNotFoundException {
        while (shouldRun) {
            currentNumber++;
            if(isPrime(currentNumber)) {
                primes.add(currentNumber);
                fileHandler.save(currentNumber);
            }
        }
    }

    public boolean isPrime(int number) {
        if(number == 2 || number == 3) {
            return true;
        }
        boolean maybePrime = true;
        int sqrt = (int)(0.5*(600.0+(number/600.0))); // Rough estimate of the sqrt of the number i
        for (Integer prime : primes) { // Iterate through the list of known primes
            if (prime > sqrt) { // IF the current element is larger than the sqrt of it, then it cannot be a primefactor for it
                break;
            }
            if (number % prime == 0) { // IF number modulus with the current prime is equal to 0, AKA it equally divides, then it is not a prime
                maybePrime = false;
                break;
            }
        }
        return maybePrime;
    }

}
