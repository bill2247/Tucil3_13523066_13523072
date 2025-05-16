import java.util.*;

public class Tree {
    private BoardState state;
    private ArrayList<Tree> children;
    private int fn;

    // konstruktor
    Tree(BoardState state){
        this.state = state;
        this.children = new ArrayList<Tree>();
        this.fn = 0;
    }

    //getter
    public BoardState getState(){return this.state;}
    public ArrayList<Tree> getChildren(){return this.children;}
    public int getFn(){return this.fn;}

    // setter
    public void updateChildren(BoardState state, Integer fn){
        Tree child = new Tree(state);
        this.children.add(child);
    }
}
