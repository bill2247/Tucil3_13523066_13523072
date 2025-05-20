import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Selamat datang di program Rush Hour Puzzle Solver!");

        Scanner scanner = new Scanner(System.in);
        while(true){
            String filePath;
            boolean isValidatingFile = true;
            BoardState boardState = new BoardState(1,1);
            boardState.resetStatic();
            while(isValidatingFile){
                System.out.print("Nama File Input: ");
                filePath = scanner.nextLine();
                if (filePath.equals("exit")) {
                    System.out.println("Keluar dari program...");
                    return;
                }
                if (filePath.endsWith(".txt")) {
                    try {
                        InputFile inputFile = new InputFile("test/" + filePath);
                        boardState = inputFile.getBoardState();
                        Validation val = new Validation(boardState);
                        val.validation();
                    } catch (Exception e) {
                        System.out.println("^Ditemukan kesalahan pada file: " + e.getMessage());
                        continue;
                    }
                    isValidatingFile = false;
                } else {
                    System.out.println("File tidak valid. Silakan masukan kembali file *.txt");
                }
            }
            System.out.println("-----ALGORITMA PENCARIAN-----");
            System.out.println("1. Uniform Cost Search (UCS)");
            System.out.println("2. Greedy Best First Search");
            System.out.println("3. A*");
            System.out.println("-----------------------------");

            int algorithmCode = 0;
            boolean isValidatingCode = true;
            while(isValidatingCode){
                System.out.print("Algoritma Pencarian (1-3): ");
                String input = scanner.nextLine();
                try{
                    algorithmCode = Integer.parseInt(input);
                    if(algorithmCode<1 || algorithmCode>3){
                       System.out.println("Harap masukkan angka 1-3");
                       continue;
                    }
                } catch(NumberFormatException e){
                    System.out.println("Harap masukkan angka 1-3");
                    continue;
                }
                isValidatingCode = false;
            }

            // Pencarian Solusi
            // Root node, kondisi awal
            Tree tree = new Tree(boardState);
            Cost cost = new Cost(algorithmCode, tree.getState());
            Solver solver = new Solver();
        
            System.out.println("Mencari solusi...");
            long startTimeSolve = System.currentTimeMillis();
            Tree goal = solver.solve(tree);
            long endTimeSolve = System.currentTimeMillis();
            long solveTime = endTimeSolve - startTimeSolve;

            // output
            Tree path = Solver.generatePath(goal);
            Output output = new Output(path);
            
            System.out.println("-------------RUTE MENUJU SOLUSI-------------");
            output.printBoard();
            System.out.println();
            System.out.println("--------------------------------------------");
            System.out.println("Waktu pencarian (ms) : " + solveTime);
            System.out.println("Banyak node dikunjungi: " + solver.count);
            System.out.println("--------------------------------------------");

            System.out.print("Keluar dari program? (y): ");
            String confirm = scanner.nextLine();
            if(confirm.equals("y") || confirm.equals("Y")){
                System.out.println("Sampai jumpa kembali!");
                System.exit(0);
            }
        }
    }
}
