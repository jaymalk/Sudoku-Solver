import java.util.Scanner;
import edu.princeton.cs.algs4.StdDraw;
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
        while(true) {
            size = in.nextInt();
            if(size <= 0) {
                System.out.println("Invalid size. Exiting. [InterfaceGUI.main]");
                System.exit(0);
            }
            int sq = (int)Math.sqrt(size);
            if(sq*sq == size)
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
        boolean pl = true;   // Signifying the permanent nature of placement
        int xm = -1, ym = -1, vl = 0; // Values from the user
        // Loop for getting values
        while(true) {
            try {
                if(StdDraw.isMousePressed()) {
                    Designer.GUI_draw(s);
                    xm = (int)StdDraw.mouseX();
                    ym = (int)StdDraw.mouseY();
                    try {
                        vl = s.grid()[ym][xm];
                        darken(xm, ym);
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Index out of Bounds (Mouse). [InterfaceGUI.startDesign]");
                    }
                    vl = 0;
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                    break;
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                    sleep(100);
                    vl = Math.min(vl+1, s.size());
                    Designer.addPlace(s, xm, ym, vl, pl);
                    darken(xm, ym);
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                    sleep(100);
                    vl = Math.max(vl-1, 0);
                    if(vl == 0) {
                        try {
                            s.grid()[ym][xm] = 0;
                            s.set()[ym][xm] = false;
                            Designer.GUI_draw(s);
                        }
                        catch(Exception e) {
                            System.out.println("Minor Exception : [InterfaceGUI.startDesign]");
                        }
                    }
                    else
                        Designer.addPlace(s, xm, ym, vl, pl);
                    darken(xm, ym);
                }
            }
            catch(Exception e) {
                System.out.println(e.getMessage() + " [InterfaceGUI.startDesign]");
            }
        }
    }

    // Darkening the box in focus
    public static void darken(int xm, int ym) {
        StdDraw.disableDoubleBuffering();
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(xm+0.5, ym+0.5, 0.5);
        StdDraw.setPenRadius(0.001);
        StdDraw.enableDoubleBuffering();
    }

    // VERIFICATION STAGE
    // Function for verifying the design of Sudoku
    private static void verifyDesign(Sudoku s) throws Exception {
        verifyRows(s, 1); // Rows
        verifyRows(s, 0);   // Columns
        int val = (int)Math.sqrt(s.size());
        for(int i=0; i<val; i++)
            for(int j=0; j<val; j++)
                verifyBlock(s, i, j);
    }
    // Function for veryfying the validity of rows
    private static void verifyRows(Sudoku s, int HorV) throws Exception {
        try {
            for(int i=0; i<s.size(); i++) {
                int[] set = new int[s.size()];
                for(int j=0; j<s.size(); j++) {
                    int val = 0;
                    if(HorV != 0)
                        val = s.grid()[j][i];
                    else
                        val = s.grid()[i][j];
                    if(val == 0)
                        continue;
                    else if (set[val-1] == 0)
                        set[val-1] = 1;
                    else if (set[val-1] == 1)
                        throw new Exception("Multiple values in a column/row ("+i+", "+j+"). Invalid Puzzle. Exiting.");
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage() + " [InterfaceGUI.verifyRows]");
            s.CLI_Draw();
            System.exit(0);
        }
    }
    // Function for verification of Blocks
    private static void verifyBlock(Sudoku s, int x, int y) throws Exception {
        try {
            int sq = (int)Math.sqrt(s.size());
            int[] set = new int[s.size()];
            for(int i=x*sq; i<(x+1)*sq; i++)
                for(int j=y*sq; j<(y+1)*sq; j++) {
                    int val = s.grid()[j][i];
                    if(val == 0)
                        continue;
                    else if (set[val-1] == 0)
                        set[val-1] = 1;
                    else if (set[val-1] == 1)
                        throw new Exception("Multiple values in a block ("+i+", "+j+"). Invalid Puzzle. Exiting.");
                }
        }
        catch(Exception e) {
            System.out.println(e.getMessage() + " [InterfaceGUI.verifyColumns]");
            s.CLI_Draw();
            System.exit(0);
        }
    }

    // Function for voluntary sleeping
    public static void sleep(int t) {
        try {
            Thread.sleep(t);
        }
        catch(Exception e) {}
    }
}
