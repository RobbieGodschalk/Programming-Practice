package Day3;

import java.io.File;
import java.util.Scanner;

public class Day3 {

    public static void main(String[] args) {

        // Get input file
        String inputFile = args.length > 0 ? args[0] : "AoC/Day3/day3.txt";


        long totalOutput = 0;

        try (Scanner sc = new Scanner(new File(inputFile)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                // Process each line of input here
                totalOutput += findMaxJoltage(line, 12);
            }

            System.out.println("Total output: " + totalOutput);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int processBank(String bank) {
        // String to array of digits
        int[] digits = bank.chars().toArray();

        int d1 = 0;
        int d2 = 0;

        for (int i = 0; i < digits.length - 1; i++) {
            if (digits[i] > d1) {
                d1 = digits[i];
                d2 = digits[digits.length - 1];
            } else if (digits[i] > d2) {
                d2 = digits[i];
            }
        }

        // Concatenate d1 and d2 to one integer as d1d2
        return Integer.parseInt("" + (char)d1 + (char)d2);
    }

    public static long findMaxJoltage(String bank, int batteries) {
        long totalJoltage = 0;
        int[] bankArr = bank.chars().toArray();

        // We will go from left to right, pick the highest available digit in the sub-string that will
        //      still allow us to make a selection of exactly 'batteries' digits in total.
        int lastIndex = -1;
        int bankSize = bank.length();

        for (int remaining = batteries; remaining > 0; remaining--) {
            int lower = lastIndex + 1;
            int upper = bankSize - remaining; // Ensures we do not consider the last (remaining - 1) batteries here.

            int max = -1;
            for (int i = lower; i <= upper; i++) {
                if (bankArr[i] > max) { // We only consider > to maximize the remaining range!
                    max = bankArr[i];
                    lastIndex = i;
                }
            }

            // Total joltage from appending max as digit left to right, compute using 10^X
            totalJoltage += (long) ((max - '0') * Math.pow(10, remaining - 1));
        }

        return totalJoltage;
    }
}
