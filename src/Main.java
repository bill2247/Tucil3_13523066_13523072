import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of InputFile
        // input user input file
        // System.out.print("Please enter the input file path: ");
        // Scanner scanner = new Scanner(System.in);
        // String filePath ;
        // while(true) {
        //     filePath = scanner.nextLine();
        //     if (filePath.equals("exit")) {
        //         System.out.println("Exiting...");
        //         return;
        //     }
        //     if (filePath.endsWith(".txt")) {
        //         break;
        //     } else {
        //         System.out.print("Invalid file path. Please enter a valid .txt file path: ");
        //     }
        // }
        // scanner.close();
        String filePath = "input.txt";
        InputFile inputFile = new InputFile("test/" + filePath);
        
        // Get the board state from the input file
        BoardState boardState = inputFile.getBoardState();
        System.out.println("(main) exit point: " + BoardState.exitRow + ", " + BoardState.exitCol);
        System.out.println("(main) primary piece: " + BoardState.primaryPiece.upLeft.r + ", " + BoardState.primaryPiece.upLeft.c);

        // Validation
        // hitung waktu unutk menjalankan validasi
        long startTime = System.currentTimeMillis();
        Validation val = new Validation(boardState);
        try {
            val.validation();
        } catch (IllegalStateException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Validation Time: " + (endTime - startTime) + " ms");
        
        // Print the board state
        System.out.println("Board State:");
        for (char[] row : boardState.getBoard()) {
            System.out.println(row);
        }

        // Print the pieces
        // System.out.println("Pieces:");
        // boardState.printAllPieces();
        
        // Create a tree with the initial board state
        Tree tree = new Tree(boardState);
        
        // Print the initial state of the tree
        System.out.println("Initial Tree State:");
        System.out.println(tree.getState());
        // output
        Output output = new Output(tree);
        output.printBoard();
    }
}
