import java.util.Scanner;
import java.util.Vector;
import java.awt.event.KeyEvent;

// Main interface for using all other classes to solve sudoku
public class InterfaceGUI {
    // Scanner for taking user input
    private static Scanner in = new Scanner(System.in);

    // Main function for running
    public static void main(String[] args) throws Exception {
        // Turning GUI on
        Designer.cli = false;

        // Clearing the screen
        System.out.print("\033[H\033[J");

        // Initialising sudoku
        System.out.print("Enter the sudoku size... ");
        // Asserting that puzzle be square
        int size = 0;
        while (true) {
            size = in.nextInt();
            if (size <= 0) {
                System.out.println("Invalid size. Exiting. [InterfaceGUI.main]");
                System.exit(0);
            }
            int sq = (int) Math.sqrt(size);
            if (sq * sq == size)
                break;
            System.out.print("Please enter a perfect-square for size... ");
        }
        Sudoku s = new Sudoku(size);

        // Printing the instructions for Simulation
        Designer.printInstructions();
        in.nextLine();
        in.nextLine();

        // Clearing the screen
        Designer.setScreen(s);

        // Starting Designing Sequence
        startDesign(s);

        // Verifying the present design of Sudoku (with rules)
        verifyDesign(s);

        // After verification is complete, try solving the puzzle
        Solver.solveSudoku(s);

        // The puzzle is solved
        System.out.println("Solved!");
    }

    // DESIGNING STAGE
    // Function for designing the sudoku
    private static void startDesign(Sudoku s) {
        boolean pl = true; // Signifying the permanent nature of placement
        int xm = -1, ym = -1, vl = 0; // Values from the user
        // Loop for getting values
        while (true) {
            try {
                if (StdDraw.isMousePressed()) {
                    Designer.GUI_draw(s);
                    xm = (int) StdDraw.mouseX();
                    ym = (int) StdDraw.mouseY();
                    try {
                        vl = s.grid()[ym][xm];
                        darken(xm, ym);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Index out of Bounds (Mouse). [InterfaceGUI.startDesign]");
                    }
                    vl = 0;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                    break;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                    sleep(100);
                    vl = Math.min(vl + 1, s.size());
                    Designer.addPlace(s, xm, ym, vl, pl);
                    darken(xm, ym);
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                    sleep(100);
                    vl = Math.max(vl - 1, 0);
                    if (vl == 0) {
                        try {
                            s.grid()[ym][xm] = 0;
                            s.set()[ym][xm] = false;
                            Designer.GUI_draw(s, xm, ym);
                        } catch (Exception e) {
                            System.out.println("Minor Exception : [InterfaceGUI.startDesign]");
                        }
                    } else
                        Designer.addPlace(s, xm, ym, vl, pl);
                    darken(xm, ym);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " [InterfaceGUI.startDesign]");
            }
        }
        Designer.GUI_draw(s);
    }

    // Darkening the box in focus
    public static void darken(int xm, int ym) {
        StdDraw.disableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.circle(xm + 0.5, ym + 0.5, 0.5);
        StdDraw.enableDoubleBuffering();
    }

    // VERIFICATION STAGE
    // Function for verifying the design of Sudoku
    private static void verifyDesign(Sudoku s) throws Exception {
        // New verification design focused on individual objects
        for (int i = 0; i < s.size(); i++) {
            for (int j = 0; j < s.size(); j++) {
                verfifyCell(s, i, j);
            }
        }
    }

    // Function for verifying a cell's validity
    private static void verfifyCell(Sudoku s, int i, int j) throws Exception {
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

    // Function for voluntary sleeping
    public static void sleep(int t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {
        }
    }
}
