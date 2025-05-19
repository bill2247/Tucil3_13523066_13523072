import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of InputFile
        // input user input file
        System.out.print("Please enter the input file path: ");
        Scanner scanner = new Scanner(System.in);
        String filePath ;
        while(true) {
            filePath = scanner.nextLine();
            if (filePath.equals("exit")) {
                System.out.println("Exiting...");
                return;
            }
            if (filePath.endsWith(".txt")) {
                break;
            } else {
                System.out.print("Invalid file path. Please enter a valid .txt file path: ");
            }
        }
        scanner.close();
        InputFile inputFile = new InputFile("test/" + filePath);
        
        // Get the board state from the input file
        BoardState boardState = inputFile.getBoardState();
        
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
    }
}
