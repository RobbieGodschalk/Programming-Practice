package Day4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {

    static int[][] directions = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0},          {1, 0},
            {-1, 1},  {0, 1},  {1, 1}
    };

    public static void main(String[] args) {

        // Get input file
        String inputFile = args.length > 0 ? args[0] : "AoC/2025/Day4/day4.txt";

        // Create our map as a 2D list
        List<char[]> map = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(inputFile)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                // Process each line of input here
                map.add(line.toCharArray());



            }

            System.out.println("Map size: " + map.size() + " rows.");
            System.out.println("Accessible spots: " + countAccessible(map.toArray(new char[0][]), 5));

            System.out.println("Removable rolls: " + countRemovableRolls(map.toArray(new char[0][]), 5));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param map 2D char array representing the map
     */
    public static int countAccessible(char[][] map, int reqEmpty) {
        int accessibleCount = 0;
        List<int[]> accessibleSpots = new ArrayList<>();

        int lines = map.length;
        int cols = map[0].length;

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < lines; y++) {

                if (map[y][x] == '@' && checkAccessible(map, x, y, reqEmpty)) {
                    accessibleCount++;
                    accessibleSpots.add(new int[]{x, y});
                }

            }
        }

        return accessibleCount;
    }

    public static int countRemovableRolls(char[][] map, int reqEmpty) {
        int accessibleCount = 0;
        int removableTotal = 0;
        List<int[]> accessibleSpots = new ArrayList<>();

        int lines = map.length;
        int cols = map[0].length;

        do {
            accessibleCount = 0;
            accessibleSpots.clear();
            for (int x = 0; x < cols; x++) {
                for (int y = 0; y < lines; y++) {

                    if (map[y][x] == '@' && checkAccessible(map, x, y, reqEmpty)) {
                        accessibleCount++;
                        accessibleSpots.add(new int[]{x, y});
                    }

                }
            }
            removableTotal += accessibleCount;

            removeAccessibleRolls(map, accessibleSpots);

        } while (accessibleCount > 0);


        return removableTotal;
    }

    public static void removeAccessibleRolls(char[][] map, List<int[]> accessibleSpots) {
        int lines = map.length;
        int cols = map[0].length;

        for (int[] spot : accessibleSpots) {
            int x = spot[0];
            int y = spot[1];

            // Mark accessible spots with '.' --> now empty spot
            map[y][x] = '.';
        }
    }

    public static boolean checkAccessible(char[][] map, int x, int y, int reqEmpty) {
        // We need to check in all 8 directions from (x, y), for reqEmpty empty spots
        // only ONE step in each direction
        // Out of bounds means that spot is empty, so we count this as well

        int[] yBounds = {0, map.length - 1};
        int[] xBounds = {0, map[0].length - 1};
        int emptyCount = 0;

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            int currX = x + dx;
            int currY = y + dy;

            if (currX >= xBounds[0] && currX <= xBounds[1] &&
                    currY >= yBounds[0] && currY <= yBounds[1]) {

                if (map[currY][currX] == '.') {
                    emptyCount++;

                    // Early return
                    if (emptyCount >= reqEmpty) {
                        return true;
                    }
                }
            } else {
                // Out of bounds counts as empty
                emptyCount++;

                // Early return
                if (emptyCount >= reqEmpty) {
                    return true;
                }
            }
        }

        return emptyCount >= reqEmpty;

    }
}
