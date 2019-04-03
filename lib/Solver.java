import java.util.Vector;

// Class with static functions for solving the sudoku (after designing)
public class Solver {
    private static int x, y; // present coordinates
    private static int size, sq; // sudoku parameters
    private static boolean pl = false, solved = false;
    private static Sudoku s; // sudoku to solve

    private static int tag; // Tells which parameter to pick for placing from allowed values : Important for backtracking without recursion

    // Outer solver function
    public static void solveSudoku(Sudoku toSolve){
        x = 0;
        y = 0;
        tag = 0;
        s = toSolve;
        size = s.size();
        sq = (int)Math.sqrt(size);
        while(!solved) {
            if(y >= size)
                break;
            step();
            Designer.GUI_draw(s, x, y);
        }
    }

    // Inner solver function (private)
    private static void step() {
        // Permenent value, get back
        if(s.grid()[y][x] != 0) {
            x = (x+1)%size;
            if(x == 0)
                y++;
            return;
        }
        Vector<Integer> v = getPossible();
        // Not possible to place, backtrack
        if(v.size() == 0) {
            resetLast();
            return;
        }
        // Not possible to place
        int pos = v.indexOf(tag);
        if(pos == v.size()-1) {
            resetLast();
            return;
        }
        // Possible to place
        if(pos == -1)
            Designer.addPlace(s, x, y, v.elementAt(0), pl);
        else
            Designer.addPlace(s, x, y, v.elementAt(pos+1), pl);
        // Setting tag 0 again as next can take any value
        tag = 0;
        // Increasing position
        x = (x+1)%size;
        if(x == 0)
            y++;
        return;
    }

    // Function for backtracking - to reset last possible setting to allow changes in present step
    private static void resetLast() {
        // Getting the last set position
        while(true) {
            x--;
            if(x == -1) {
                x = size-1;
                y--;
            }
            if(s.set()[y][x] == false)
                break;
        }
        tag = s.grid()[y][x];
        s.grid()[y][x] = 0;
        return;
    }

    // Helper function to get the possible postions placable on a cell
    private static Vector<Integer> getPossible() {
        Vector<Integer> v = new Vector<>();
        for(int i=0; i<size; i++)
            v.addElement(i+1);
        for(int i=0; i<size; i++) {
            int v1 = s.grid()[i][x];
            int v2 = s.grid()[y][i];
            if(v1 != 0)
                if(v.indexOf(v1) != -1) {
                    v.removeElement(v1);
                }
            if(v2 != 0)
                if(v.indexOf(v2) != -1) {
                    v.removeElement(v2);
                }
        }
        for(int i=x-x%sq; i<x-(x%sq)+sq; i++)
            for(int j=y-y%sq; j<y-(y%sq)+sq; j++) {
                int v1 = s.grid()[j][i];
                if(v1 != 0)
                    if(v.indexOf(v1) != -1) {
                        v.removeElement(v1);
                    }
            }
        return v;
    }

}
