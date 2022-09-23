// import java.util.ArrayList;
// import java.util.LinkedList;
// import java.util.List;

// public class BFS {
// static int visitedNodes = 0;

// public List<Game> bfsMethod(Game start) {
// List<Game> pathToSolution = new ArrayList<Game>(); // to keep track of the
// path
// List<Game> queue = new LinkedList<Game>(); // to keep track of the nodes to
// be visited
// List<Game> visited = new ArrayList<Game>(); // to keep track of the visited
// nodes

// queue.add(start);
// boolean goalFound = false;
// while (!queue.isEmpty() && !goalFound) {
// Game current = queue.get(0);
// visited.add(current);
// queue.remove(0);
// ++visitedNodes;
// current.makeMoves();
// // current.printPuzzle(current.puzzle);
// for (int i = 0; i < current.children.size(); i++) {
// Game currentGame = current.children.get(i);
// // checks if the current game is in the goal state
// if (currentGame.isGoal()) {
// goalFound = true;
// pathToSolution.add(currentGame);
// System.out.print("\nTotal Visited Nodes: " + (visitedNodes));
// printPath(pathToSolution, currentGame);
// }
// // checks if the current game is already visited
// if (!containGame(visited, currentGame)) {
// queue.add(currentGame);
// }
// }
// }
// return pathToSolution; // return the path to the solution
// }

// public void printPath(List<Game> path, Game game) {
// System.out.print("\nPath to solution: ");
// Game current = game;
// path.add(current);
// while (current != null) {
// current = current.parent;
// path.add(current);
// }
// }

// // checks if the current game is already visited
// public static boolean containGame(List<Game> allGames, Game thisGame) {
// boolean contains = false;

// for (Game game : allGames) {
// if (game.isVisited(thisGame.puzzle)) {
// contains = true;
// }

// }
// return contains;
// }

// }
