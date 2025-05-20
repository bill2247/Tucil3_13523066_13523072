import java.util.Scanner;
import java.util.*;

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
        System.out.println("location of pieces: " + boardState.getPiecesLocation());
        System.out.println("exit point: " + BoardState.exitRow + ", " + BoardState.exitCol);
        System.out.println("pieces: " + BoardState.pieces);
        System.out.println("rows: " + BoardState.rows);
        System.out.println("cols: " + BoardState.cols);
        System.out.println("primary piece: " + BoardState.primaryPiece.upLeft.r + ", " + BoardState.primaryPiece.upLeft.c);

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
        char[][] board = boardState.getBoard();
        for(int i=0;i<BoardState.rows;i++){
            for(int j=0;j<BoardState.cols;j++){
                System.out.print(board[i][j]);
            }    
            System.out.println();
        }

        Cost cost = new Cost(2, tree.getState());
        Solver solver = new Solver();
        
        Tree goal = solver.solve(tree);
        System.out.println("kusanagi");
        System.out.println("Node  : " + goal);
        System.out.println("Parent: " + goal.getParent());
        goal.getState().printGameState(); // debug

        // Tree path = solver.generatePath(goal);

        // ------------------------ res ------------------------
        ArrayList<Tree> path = new ArrayList<>();
        Tree current = goal;
        while(current != null){
            path.add(current);
            current=current.getParent();
        }
        Collections.reverse(path);

        for(int i=0;i<path.size()-1;i++){
            ArrayList<Tree> child = new ArrayList<>();
            child.add(path.get(i+1));
            path.get(i).setChildren(child);
        }
        ArrayList<Tree> nochild = new ArrayList<>();
        path.get(path.size()-1).setChildren(nochild);
        
        Tree res = path.get(0);

        System.out.println("kusanagi3");
        System.out.println("Node  : " + res);
        System.out.println("Parent: " + res.getParent());
        res.getState().printGameState(); // debug

        Tree currentNode = res;
        Scanner scanner = new Scanner(System.in);
        while(currentNode.getChildren().size()>0){
            System.out.println("---------------------");
            currentNode.getState().printGameState();
            System.out.println("^^^^^^^^^^^^^^^^^^^^^");
            String name = scanner.nextLine(); // debug
            currentNode = currentNode.getChildren().get(0);
        }
        System.out.println("---------------------");
        currentNode.getState().printGameState();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^");
       
        // System.exit(0);
        // // output
        System.out.println("--------------------------");
        Output output = new Output(res);
        output.printBoard();
    }
}
