import java.util.*;

public class Tree {
    private BoardState state;
    private ArrayList<Tree> children;
    private char moveType; // u r d l
    private int fn;
    private Tree parent;

    // konstruktor
    Tree(BoardState state){
        this.state = state;
        this.children = new ArrayList<Tree>();
        this.moveType = ' ';
        this.fn = 0;
        this.parent = null;
    }

    //getter
    public BoardState getState(){return this.state;}
    public ArrayList<Tree> getChildren(){return this.children;}
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

    public void generateChildren(){
        Map<Character, ArrayList<Coordinate>> legalMoves = state.generateLegalMoves();
        for(Map.Entry<Character, ArrayList<Coordinate>> entry : legalMoves.entrySet()){
            char currId = entry.getKey();            
            ArrayList<Coordinate> currCoordinates = entry.getValue();

            for(int i=0;i<currCoordinates.size();i++){
                int r = currCoordinates.get(i).y;
                int c = currCoordinates.get(i).x;
                BoardState newState = state.cloneBoardState();

                // cek tipe gerakan
                char mt = ' ';
                int oldR = state.piecesLocation.get(currId).y;
                int oldC = state.piecesLocation.get(currId).x;
                if(r>oldR){mt = 'd';}
                else if(r<oldR){mt = 'u';}
                else if(c<oldC){mt = 'l';}
                else if(c>oldC){mt = 'r';}

                // movement
                newState.deletePiece(currId);
                newState.addPiece(currId, r, c);
                int fn = Cost.f(newState);
                updateChildren(newState, fn, mt);
            }
        }
    }
}
