import java.util.Scanner;
import java.util.Vector;

// Main interface for using all other classes to solve sudoku
public class Interface {
    // Scanner for taking user input
    private static Scanner in = new Scanner(System.in);

    // Main function for running
    public static void main(String[] args) {
        // Clearing the screen
        System.out.print("\033[H\033[J");

        // Initialising sudoku
        System.out.print("Enter the sudoku size... ");
        // Asserting that puzzle be square
        int size = 0;
        while (true) {
            size = in.nextInt();
            if (size <= 0) {
                System.out.println("Invalid size. Exiting. [Interface.main]");
                System.exit(0);
            }
            int sq = (int) Math.sqrt(size);
            if (sq * sq == size)
                break;
            System.out.print("Please enter a perfect-square for size... ");
        }
        Sudoku s = new Sudoku(size);

        // Clearing the screen
        System.out.print("\033[H\033[J");

        // Starting Designing Sequence
        startDesign(s);

        // Verifying the present design of Sudoku (with rules)
        verifyDesign(s);

        // After verification is complete, try solving the puzzle
        // try {
        Solver.solveSudoku(s);
        // }
        // catch(Exception e) {

        // }
    }

    // DESIGNING STAGE
    // Function for designing the sudoku
    private static void startDesign(Sudoku s) {
        boolean pl = true; // Signifying the permanent nature of placement
        String l = "-------------------------------------------------------";
        System.out.println(l + "\nEnter the coordinates of blocks and their set value : [x] [y] [val]\n" + l
                + "\nEnter (-1) to exit.\n" + l + "\n");
        in.nextLine(); // Redundant line to capture 'Enter' key from above
        // Loop for getting values
        while (true) {
            try {
                // Getting the information
                System.out.print("Enter.. ");
                String line = in.nextLine();
                // Processing the information
                String[] param = line.split(" ");
                // Exit string
                if (param[0].equals("-1"))
                    break;
                // Setting the value
                Designer.addPlace(s, Integer.valueOf(param[0]) - 1, Integer.valueOf(param[1]) - 1,
                        Integer.valueOf(param[2]), pl);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out
                        .println("Please enter complete arguments of the form : [x] [y] [val] [Interface.startDesign]");
            } catch (Exception e) {
                System.out.println(e.getMessage() + " [Interface.startDesign]");
            }
        }
    }

    // VERIFICATION STAGE
    // Function for verifying the design of Sudoku
    private static void verifyDesign(Sudoku s) {
        // New verification design focused on individual objects
        for (int i = 0; i < s.size(); i++) {
            for (int j = 0; j < s.size(); j++) {
                verfifyCell(s, i, j);
            }
        }
    }

    // Function for verifying a cell's validity
    private static void verfifyCell(Sudoku s, int i, int j) {
        try {
            // Temporarily assigning the value 0
            int temp = s.grid()[i][j];
            s.grid()[i][j] = 0;
            // Creating a vector of all possible values
            Vector<Integer> v = new Vector<>();
            for (int k = 0; k < s.size(); k++)
                v.addElement(k + 1);
            // Removing common values from row and column
            for (int k = 0; k < s.size(); k++) {
                int v1 = s.grid()[k][j];
                int v2 = s.grid()[i][k];
                if (v1 != 0)
                    if (v.indexOf(v1) != -1) {
                        v.removeElement(v1);
                    }
                if (v2 != 0)
                    if (v.indexOf(v2) != -1) {
                        v.removeElement(v2);
                    }
            }
            // Removing common values in the block
            int sq = (int) Math.sqrt(s.size());
            int x = (int) i / sq;
            int y = (int) j / sq;
            for (int k = x * sq; k < (x + 1) * sq; k++)
                for (int l = y * sq; l < (y + 1) * sq; l++) {
                    int vn = s.grid()[k][l];
                    if (vn != 0)
                        if (v.indexOf(vn) != -1)
                            v.removeElement(vn);
                }
            // Reassigning old value
            s.grid()[i][j] = temp;
            // Checking if any value is possible to be set
            if (v.size() == 0)
                throw new Exception("No possible value for block (" + j + ", " + i + "). Invalid Puzzle. Exiting.");
            // Checking if assigned value is allowed
            if (temp != 0)
                if (v.indexOf(temp) == -1)
                    throw new Exception("Current value already used (" + j + ", " + i + "). Invalid Puzzle. Exiting.");
        } catch (Exception e) {
            // throw e;
            System.out.println(e.getMessage() + " [InterfaceGUI.verifyCell]");
            s.CLI_Draw();
            System.exit(0);
        }
    }
}
