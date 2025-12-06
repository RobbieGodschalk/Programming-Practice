import java.io.File;
import java.util.Scanner;


public class Day1 {

        // Global state
        static int pointing = 50;
        static int matchesExact = 0;
        static int matchesAny = 0;

    private static int rotate(char direction, int value) {
        return switch (direction) {
            case 'L' -> Math.floorMod((pointing - value), 100);
            case 'R' -> Math.floorMod((pointing + value), 100);
            default -> pointing;
        };
    }

    private static int rotateCount(char direction, int value) {
        // We get number of full rotations and the remainder
        int divisions = value / 100;
        int remainder = value % 100;

        // Each full rotation crosses 0 once, and remainder may cross it once more
        int crosses = divisions;

        // Check if remainder crosses 0
        int start = pointing;
        int end = rotate(direction, remainder);

        if (direction == 'L') {
            if (end > start) {
                crosses += 1;
            }
        } else if (direction == 'R') {
            if (end < start) {
                crosses += 1;
            }
        }

        return crosses;
    }
    public static void main(String[] args) {

        System.out.println("wtf");

        // Get input file
        String inputFile = args.length > 0 ? args[0] : "AoC/2025/Day1/day1.txt";



        try (Scanner sc = new Scanner(new File(inputFile)))
        {
            while (sc.hasNextLine()) 
            {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                // First character signals left or right
                char direction = line.charAt(0);

                // Remainder is the distance to rotate
                int value = Integer.parseInt(line.substring(1).trim());

                // Part 1
                // if (rotate(direction, value) == 0) {
                //     matchesExact++;
                // }

                // Part 2
                matchesAny += rotateCount(direction, value);
            }

            // System.out.println("Times pointing at 0: " + matchesExact);
            System.out.println("Times crossing 0: " + matchesAny);
        }
        catch (Exception e) 
        {
            System.out.println("Error reading file: " + e.getMessage());
        }

    }
}