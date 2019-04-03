

// Class for representing the sudoku board (and cells)
public class Sudoku {
    private int size;       // The dimensions of square grid, must be a perfect square
    private int[][] grid;       // Representing cells' values
    private boolean[][] set;    // Determining the status of cells

    // Constructor
    public Sudoku(int size) {
        this.size = size;
        // Initialising the grid
        grid = new int[size][size];
        set = new boolean[size][size];
    }

    // Get size
    public int size() {
        return this.size;
    }

    // Get grids
    public int[][] grid() {
        return this.grid;
    }

    // Get the set values
    public boolean[][] set() {
        return this.set;
    }

    // Draw sudoku on the terminal
    public void CLI_Draw() {
        System.out.print("\033[H\033[J");
        for(int[] a : grid) {
            for(int b : a)
                if(b == 0)
                    System.out.print(". ");
                else
                    System.out.print(b+" ");
            System.out.println();
        }
    }
}
