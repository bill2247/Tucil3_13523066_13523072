import java.util.*;
// import java.util.function.*;

public class Solver {
    public int algorithm; // 1. UCS, 2.GBFS, 3.A*

    public Tree solve(Tree t){
        PriorityQueue<Tree> pq = new PriorityQueue<>();
        Set<BoardState> visited = new HashSet<>();

        pq.add(t);
        // Scanner scanner = new Scanner(System.in);  // debug
        while(pq.isEmpty()==false){
            Tree currentNode = pq.poll();
            if(currentNode.isGoal()){
                System.out.println("Node  : " + currentNode);
                System.out.println("Parent: " + currentNode.getParent());
                currentNode.getState().printGameState(); // debug
                return currentNode;
            }
            System.out.println("Node  : " + currentNode);
            System.out.println("Parent: " + currentNode.getParent());
            currentNode.getState().printGameState(); // debug
            // String name = scanner.nextLine(); // debug

            visited.add(currentNode.getState());
            currentNode.generateChildren();
            for(Tree childNode : currentNode.getChildren()){
                if(visited.contains(childNode.getState())==false){
                    pq.add(childNode);
                }
            }
        }

        return null;
    }

    public static Tree generatePath(Tree goalNode){
        System.out.println("kusanagi2");
        System.out.println("Node  : " + goalNode);
        System.out.println("Parent: " + goalNode.getParent());
        goalNode.getState().printGameState(); // debug


        ArrayList<Tree> path = new ArrayList<>();
        Tree current = goalNode;
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
        return path.get(0);
    }
}
