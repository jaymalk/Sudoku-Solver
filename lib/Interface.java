import java.util.Scanner;

// Main interface for using all other classes to solve sudoku
public class Interface {
    // Scanner for taking user input
    private static Scanner in = new Scanner(System.in);

    // Main function for running
    public static void main(String[] args) {
        // Initialising sudoku
        System.out.print("Enter the sudoku size... ");
            // Asserting that puzzle be square
        int size = 0;
        while(true) {
            size = in.nextInt();
            if(size <= 0) {
                System.out.println("Invalid size. Exiting. [Interface.main]");
                System.exit(0);
            }
            int sq = (int)Math.sqrt(size);
            if(sq*sq == size)
                break;
            System.out.print("Please enter a perfect-square for size... ");
        }
        Sudoku s = new Sudoku(size);
        // Starting Designing Sequence
        startDesign(s);
        // Verifying the present design of Sudoku (with rules)
        verifyDesign(s);
    }

    // DESIGNING STAGE
    // Function for designing the sudoku
    private static void startDesign(Sudoku s) {
        boolean pl = true;   // Signifying the permanent nature of placement
        String l = "-------------------------------------------------------";
        System.out.println(l+"\nEnter the coordinates of blocks and their set value : [x y n_val]\n"+l+"\nEnter (-1) to exit.\n"+l+"\n");
        in.nextLine();
        // Loop for getting values
        while(true) {
            try {
                // Getting the information
                System.out.print("Enter.. ");
                String line = in.nextLine();
                // Processing the information
                String[] param = line.split(" ");
                // Exit string
                if(param[0].equals("-1"))
                    break;
                // Setting the value
                Designer.addPlace(s, Integer.valueOf(param[0]), Integer.valueOf(param[1]), Integer.valueOf(param[2]), pl);
            }
            catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Please enter complete arguments of the form : [x y n_val] [Interface.startDesign]");
            }
            catch(Exception e) {
                System.out.println(e.getMessage() + " [Interface.startDesign]");
            }
        }
    }

    // VERIFICATION STAGE
    // Function for verifying the design of Sudoku
    private static void verifyDesign(Sudoku s) {
        verifyRows(s, 1); // Rows
        verifyRows(s, 0);   // Columns
        int val = (int)Math.sqrt(s.size());
        for(int i=0; i<val; i++)
            for(int j=0; j<val; j++)
                verifyBlock(s, i, j);
    }
    // Function for veryfying the validity of rows
    private static void verifyRows(Sudoku s, int HorV) {
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
                        throw new Exception("Multiple values in a column/row ("+i+", "+j+"). Invalid Puzzle. Exiting. [Interface.verifyRows]");
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    // Function for verification of Blocks
    private static void verifyBlock(Sudoku s, int x, int y) {
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
                        throw new Exception("Multiple values in a block ("+i+", "+j+"). Invalid Puzzle. Exiting. [Interface.verifyRows]");
                }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
