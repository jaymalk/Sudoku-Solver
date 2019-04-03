

// Class with static functions for designing the Sudoku (before solving)
public class Designer {
    /* General profile of functions
        - Take in a Sudoku
        - Take in the coordinates of the cell
        - Perform the desired function
    */

    /* Function for placing a value on the board (before/during solve)
        - The permanent parameter checks whether before solver or not.
    */
    public static void addPlace(Sudoku s, int x, int y, int new_val, boolean permanent) {
        try {
            if(s.set()[y-1][x-1] == true)
                throw new IllegalArgumentException("The value is already set. Cannot reset. [Designer.addPlace]");
            assert(new_val > 0 && new_val <= s.size());
            s.grid()[y-1][x-1] = new_val;
            s.set()[y-1][x-1] = permanent;
            s.CLI_Draw();
        }
        catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        catch(AssertionError e) {
            System.out.println("Value placed in the sudoku not in bounds ("+1+"-"+s.size()+"). [Designer.addPlace]");
        }
        catch(Exception e) {
            System.out.println("Index out of bounds. [Designer.addPlace]");
        }
    }
}
