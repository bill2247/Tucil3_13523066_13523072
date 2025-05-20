import java.util.*;

public class Tree implements Comparable<Tree>{
    private BoardState state;
    private ArrayList<Tree> children;
    private char moveType; // u r d l
    private int fn;
    private Tree parent;
    private int steps;
    private char idMoved;

    // konstruktor
    Tree(BoardState state){
        this.state = state;
        this.children = new ArrayList<Tree>();
        this.moveType = ' ';
        this.fn = Cost.f(state);
        this.parent = null;
    }

    //getter
    public BoardState getState(){return this.state;}
    public ArrayList<Tree> getChildren(){return this.children;}
    public Tree getParent(){return this.parent;}
    public int getFn(){return this.fn;}
    public char getMoveType(){return this.moveType;}

    // setter
    public void updateChildren(BoardState state, int fn, char mt){
        Tree child = new Tree(state);
        child.fn = fn;
        child.moveType = mt;
        child.parent = this;
        this.children.add(child);
    }

    @Override
    public int compareTo(Tree other){
        return Integer.compare(this.fn, other.fn);
    }

    public boolean isGoal(){
        return (state.getPiecesLocation().get('P').r == -1 && state.getPiecesLocation().get('P').c == -1);
    }

    public void generateChildren(){
        Map<Character, ArrayList<Coordinate>> legalMoves = state.generateLegalMoves();
        // debug
        System.out.println("--------------");
        for (Map.Entry<Character, ArrayList<Coordinate>> entry : legalMoves.entrySet()) {
            char key = entry.getKey();
            ArrayList<Coordinate> coords = entry.getValue();

            System.out.print(key + ": ");
            for (Coordinate coord : coords) {
                System.out.print("(" + coord.r + ", " + coord.c + ") ");
            }
            System.out.println(); // new line after each entry
        }
        System.out.println("--------------");
        // System.exit(0); // debug
        int debug=0; // debug
        for(Map.Entry<Character, ArrayList<Coordinate>> entry : legalMoves.entrySet()){
            char currId = entry.getKey();            
            ArrayList<Coordinate> currCoordinates = entry.getValue();
            System.out.println(debug); // debug
            debug++; // debug

            for(int i=0;i<currCoordinates.size();i++){
                int r = currCoordinates.get(i).r;
                int c = currCoordinates.get(i).c;
                BoardState newState = state.cloneBoardState();

                // cek tipe gerakan
                char mt = ' ';
                int oldR = state.getPiecesLocation().get(currId).r;
                int oldC = state.getPiecesLocation().get(currId).c;
                if(r>oldR){mt = 'd';}
                else if(r<oldR){mt = 'u';}
                else if(c<oldC){mt = 'l';}
                else if(c>oldC){mt = 'r';}

                // movement
                newState.deletePiece(currId);
                System.out.println("id: " + currId);
                System.out.println("r : " + r);
                System.out.println("c : " + c);
                newState.addPiece(currId, r, c);

                int fn = Cost.f(newState);
                System.out.println("fn: " + fn);
                newState.printGameState();
                updateChildren(newState, fn, mt);
            }
        }
        System.exit(0); // debug
    }
    // getter idMoved
    public char getIdMoved(){
        return this.idMoved;
    }
    // setter idMoved
    public void setIdMoved(char idMoved){
        this.idMoved = idMoved;
    }
    // getter steps
    public int getSteps(){
        return this.steps;
    }
    // setter steps
    public void setSteps(int steps){
        this.steps = steps;
    }
}
