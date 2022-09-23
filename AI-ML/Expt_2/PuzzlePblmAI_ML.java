import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class PuzzlePblmAI_ML {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // input for the puzzle
        int puzzle[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15 };
        Game intitialNode = new Game(puzzle);

        BFS bfs = new BFS();
        DFS dfs = new DFS();
        List<Game> path;
        System.out.print("\n1. BFS\n2. DFS\nEnter your choice: ");
        int choice = sc.nextInt();
        if (choice == 2) {

            System.out.print("\nSolving using DFS...");
            path = dfs.dfsMethod(intitialNode);
        } else {

            System.out.print("\nSolving using BFS...");
            path = bfs.bfsMethod(intitialNode);
        }
        if (path.size() == 0) {
            System.out.println("No solution found");
            return;
        } else {
            System.out.println("Solution found");
        }
        for (int i = path.size() - 1; i >= 0; i--) {
            if (path.get(i) != null)
                Game.printPuzzle(path.get(i).puzzle);
        }

    }
}

class Game {
    static int puzzleSize = 4;
    public List<Game> children = new ArrayList<Game>(); // children of the current node
    public Game parent; // to keep track of the path
    public int[] puzzle;
    public int blank = 0;

    public Game(int[] puzzle) {
        this.puzzle = new int[puzzleSize * puzzleSize];
        this.puzzle = puzzle;
        this.blank = findBlank();
    }

    // Find the blank space in the puzzle
    public int findBlank() {
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // to check if the puzzle is in the goal state
    public boolean isGoal() {

        int[] goal = new int[puzzle.length];
        for (int i = 0; i < puzzle.length - 1; i++) {
            goal[i] = (i + 1);
        }
        if (Arrays.equals(puzzle, goal)) {
            return true;
        }
        return false;

    }

    // make moves for the puzzle
    public void makeMoves() {
        // loop through the puzzle and find the blank space
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0)
                blank = i;
        }

        moveRight(puzzle, blank);
        moveLeft(puzzle, blank);
        moveUp(puzzle, blank);
        moveDown(puzzle, blank);

    }

    // method to move the blank tile left
    public void moveLeft(int[] puzzle, int i) {
        if (i % puzzleSize > 0) {
            int[] newPuzzle = Arrays.copyOf(puzzle, puzzle.length);

            int temp = newPuzzle[i - 1];
            newPuzzle[i - 1] = newPuzzle[i];
            newPuzzle[i] = temp; // swap the blank tile with the tile on the left
            Game child = new Game(newPuzzle); // create a new node
            children.add(child);// add the child to the list of children
            child.parent = this; // set the parent of the child
        }
    }

    // method to move the blank tile right
    public void moveRight(int[] puzzle, int i) {
        if (i % puzzleSize < puzzleSize - 1) {
            int[] newPuzzle = Arrays.copyOf(puzzle, puzzle.length);
            int temp = newPuzzle[i + 1];
            newPuzzle[i + 1] = newPuzzle[i];
            newPuzzle[i] = temp;
            Game child = new Game(newPuzzle);
            children.add(child);
            child.parent = this;
        }
    }

    // method to move the blank tile up
    public void moveUp(int[] puzzle, int i) {
        if (i - puzzleSize >= 0) {
            int[] newPuzzle = Arrays.copyOf(puzzle, puzzle.length);
            int temp = newPuzzle[i - puzzleSize];
            newPuzzle[i - puzzleSize] = newPuzzle[i];
            newPuzzle[i] = temp;
            Game child = new Game(newPuzzle);
            children.add(child);
            child.parent = this;
        }
    }

    // method to move the blank tile down
    public void moveDown(int[] puzzle, int i) {
        if (i + puzzleSize < puzzle.length) {
            int[] newPuzzle = Arrays.copyOf(puzzle, puzzle.length);
            int temp = newPuzzle[i + puzzleSize];
            newPuzzle[i + puzzleSize] = newPuzzle[i];
            newPuzzle[i] = temp;
            Game child = new Game(newPuzzle);
            children.add(child);
            child.parent = this;
        }

    }

    // method to print the puzzle
    static public void printPuzzle(int[] puzzle) {
        System.out.print("\n-----------------------------\n");
        for (int i = 0; i < puzzle.length; i++) {
            System.out.printf("|  %2d  ", puzzle[i]);
            if (i % puzzleSize == puzzleSize - 1) {
                System.out.print("|\n-----------------------------\n");

            }
        }
    }

    // method to check if two puzzles are equal
    boolean isVisited(int[] puzzle) {
        boolean samePuzzle = true;
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != this.puzzle[i]) {
                samePuzzle = false;
            }
        }
        return samePuzzle; // return true if the two puzzles are equal
    }

}

// int puzzle[] = new int[Game.puzzleSize * Game.puzzleSize];
// System.out.print(
// "\nEnter the initial state: ");
// String inputStr = sc.nextLine();
// String[] str = inputStr.split(" ");
// for (int i = 0; i < str.length && i < Game.puzzleSize * Game.puzzleSize; i++)
// {
// puzzle[i] = Integer.parseInt(str[i]);
// }

// class for the BFS algorithm
class BFS {
    static int visitedNodes = 0;

    public List<Game> bfsMethod(Game start) {
        List<Game> pathToSolution = new ArrayList<Game>(); // to keep track of the path
        List<Game> queue = new LinkedList<Game>(); // to keep track of the nodes to be visited
        List<Game> visited = new ArrayList<Game>(); // to keep track of the visited nodes

        queue.add(start);
        boolean goalFound = false;
        while (!queue.isEmpty() && !goalFound) {
            Game current = queue.get(0);
            visited.add(current);
            queue.remove(0);
            ++visitedNodes;
            current.makeMoves();
            // current.printPuzzle(current.puzzle);
            for (int i = 0; i < current.children.size(); i++) {
                Game currentGame = current.children.get(i);
                // checks if the current game is in the goal state
                if (currentGame.isGoal()) {
                    goalFound = true;
                    pathToSolution.add(currentGame);
                    System.out.print("\nTotal Visited Nodes: " + (visitedNodes));
                    printPath(pathToSolution, currentGame);
                }
                // checks if the current game is already visited
                if (!containGame(visited, currentGame)) {
                    queue.add(currentGame);
                }
            }
        }
        return pathToSolution; // return the path to the solution
    }

    public void printPath(List<Game> path, Game game) {
        System.out.print("\nPath to solution: ");
        Game current = game;
        path.add(current);
        while (current != null) {
            current = current.parent;
            path.add(current);
        }
    }

    // checks if the current game is already visited
    public static boolean containGame(List<Game> allGames, Game thisGame) {
        boolean contains = false;

        for (Game game : allGames) {
            if (game.isVisited(thisGame.puzzle)) {
                contains = true;
            }

        }
        return contains;
    }

}

// class for the DFS algorithm
class DFS {

    public List<Game> dfsMethod(Game start) {

        List<Game> pathToSolution = new ArrayList<Game>();
        Stack<Game> stack = new Stack<Game>();
        List<Game> visited = new ArrayList<Game>();
        stack.push(start);
        boolean goalFound = false;
        // while the stack is not empty and the goal is not found
        while (!stack.isEmpty() && !goalFound) {
            Game current = stack.peek();
            visited.add(current);
            stack.pop();
            current.makeMoves();
            // loops through the children of the current game
            for (int i = 0; i < current.children.size(); i++) {
                Game currentGame = current.children.get(i);
                if (currentGame.isGoal()) {
                    goalFound = true;
                    pathToSolution.add(currentGame);
                    System.out.print("\nTotal Visited Nodes: " + (visited.size()));
                    printPath(pathToSolution, currentGame);
                } else if (!containGame(visited, currentGame)) {
                    stack.push(currentGame);
                }
            }
        }
        return pathToSolution;
    }

    public void printPath(List<Game> path, Game game) {
        System.out.print("\nPath to solution: ");
        Game current = game;
        path.add(current);
        while (current != null) {
            current = current.parent;
            path.add(current);
        }
    }

    public static boolean containGame(List<Game> allGames, Game thisGame) {
        boolean contains = false;

        for (Game game : allGames) {
            if (game.isVisited(thisGame.puzzle)) {
                contains = true;
            }
        }
        return contains;
    }

}
