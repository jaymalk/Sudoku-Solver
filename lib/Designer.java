import java.awt.Font;
import java.awt.Color;

// Class with static functions for designing the Sudoku (before solving)
public class Designer {
    /* General profile of functions
        - Take in a Sudoku
        - Take in the coordinates of the cell
        - Perform the desired function
    */
    public static boolean cli = true;

    /* Function for placing a value on the board (before/during solve)
        - The permanent parameter checks whether before solver or not.
    */
    public static void addPlace(Sudoku s, int x, int y, int new_val, boolean permanent) {
        try {
            if(s.set()[y][x] == true && !permanent)
                throw new IllegalArgumentException("The value is already set. Cannot reset. [Designer.addPlace]");
            assert(new_val > 0 && new_val <= s.size());
            s.grid()[y][x] = new_val;
            s.set()[y][x] = permanent;
            if(cli)
                s.CLI_Draw();
            else
                GUI_draw(s, x, y);
        }
        catch(IllegalArgumentException e) {
            System.out.println(e.getMessage() + "[Designer.addPlace]");
        }
        catch(AssertionError e) {
            System.out.println("Value placed in the sudoku not in bounds ("+1+"-"+s.size()+"). [Designer.addPlace]");
        }
        catch(Exception e) {
            System.out.println("Index out of bounds. [Designer.addPlace]");
        }
    }

    // GUI Related Functions

    /* Starting the GUI interface for the Sudoku */
    public static void setScreen(Sudoku s) {
        int size = s.size();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(900, 900);
        StdDraw.setXscale(-1, size+1);
        StdDraw.setYscale(-1, size+1);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setFont(new Font("ARIAL", Font.PLAIN, 35));
        StdDraw.setPenRadius(0.001);
        GUI_draw(s);
    }

    /* Drawing on the screen */
    public static void GUI_draw(Sudoku s) {
        int size = s.size();
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++) {
                if(s.set()[j][i])
                    StdDraw.setPenColor(new Color(150, 0, 75));
                else
                    StdDraw.setPenColor(new Color(75, 0, 150));
                StdDraw.filledCircle(i+0.5, j+0.5, 0.5);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.circle(i+0.5, j+0.5, 0.5);
                StdDraw.setPenColor(StdDraw.WHITE);
                if(s.grid()[j][i] != 0)
                    StdDraw.text(i+0.5, j+0.5, String.valueOf(s.grid()[j][i]));
            }
        StdDraw.show();
    }
    public static void GUI_draw(Sudoku s, int i, int j) {
        StdDraw.disableDoubleBuffering();
        if(s.set()[j][i])
            StdDraw.setPenColor(new Color(150, 0, 75));
        else
            StdDraw.setPenColor(new Color(75, 0, 150));
        StdDraw.filledCircle(i+0.5, j+0.5, 0.5);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(i+0.5, j+0.5, 0.5);
        StdDraw.setPenColor(StdDraw.WHITE);
        if(s.grid()[j][i] != 0)
            StdDraw.text(i+0.5, j+0.5, String.valueOf(s.grid()[j][i]));
        StdDraw.enableDoubleBuffering();
    }

    /* Printing the instructions for Simulation (GUI) */
    public static void printInstructions() {
        String s = "\n  Instructions for simulation.\n";
        s += "\n    1. Click on the blocks/cells and enter the desired value (allowed) for pre-design.\n";
        s += "\n    2. Press 0 to set focused-block out of focus or to reset a value. (overwriting is not allowed)\n";
        s += "\n    3. After having completely designed the sudoku, press ENTER and the simulation shall start.\n";
        s += "\nPress Enter to continue...";
        System.out.print(s);
    }

}
