import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.JFrame;

/**
 * This program builds a maze, with weighted or unweighted paths as specified,
 * and displays the shortest paths as described from a query file.
 * 
 * @author Jasmine Roebuck 30037334
 */
public class HW4 {

	private static boolean weighted;

	public static void main(String[] args) throws NumberFormatException, IOException {

		if (args[0].equals("--weighted")) {
			weighted = true;
		} else if (args[0].equals("--unweighted")) {
			weighted = false;
		} else {
			System.out.println("Error: invalid weighted option entered.");
		}

		File maze = new File(args[1]);
		File query = new File(args[2]);

		// Build the maze from maze file
		// Graph will be represented as an adjacency matrix
		BufferedReader br = new BufferedReader(new FileReader(maze));
		int N = Integer.parseInt(br.readLine());
		int size = (int) Math.pow(N, 2);
		int[][] mazeMatrix = createMaze(size);
		// Build maze edges from file
		while (br.ready()) {
			String[] line = br.readLine().split("\\s+");
			int src = Integer.parseInt(line[0]);
			int dest = Integer.parseInt(line[1]);
			int weight = Integer.parseInt(line[2]);
			addEdge(mazeMatrix, src, dest, weight);
		}
		br.close();

		// Start the visualizer
		MazeVisualizer applet = new MazeVisualizer(N);
		JFrame f = new JFrame("MazeVisualizer");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Add edges to the visualizer
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if (mazeMatrix[i][j] != Integer.MAX_VALUE) {
					applet.addEdge(i, j);
				}
			}
		}

		// Calculate shortest paths from query file
		br = new BufferedReader(new FileReader(query));
		while (br.ready()) {
			String[] line = br.readLine().split("\\s+");
			int src = Integer.parseInt(line[0]);
			int dest = Integer.parseInt(line[1]);
			shortestPath(mazeMatrix, src);
			applet.addPath(path(mazeMatrix, dest));
		}
		br.close();

		// Display the visualizer
		f.getContentPane().add("Center", applet);
		applet.init();
		f.pack();
		f.setBackground(Color.WHITE);
		f.setSize(new Dimension(512, 512));
		f.setVisible(true);
	}

	/**
	 * Given the number of vertices in the graph, initializes an adjacency matrix.
	 * Entries in the matrix represent path weights and are initilized to
	 * Integer.MAX_VALUE for a weighted graph or 1 for an unweighted graph. The
	 * first column in the matrix is reserved to track parent nodes in the shortest
	 * path algorithm, and the first row in the matrix is reserved to track
	 * distances in the shortest path algorithm. Entry matrix[0][0] is unused.
	 * 
	 * @param size - number of vertices in the graph
	 * @return - the initialized matrix with all values filled
	 */
	public static int[][] createMaze(int size) {
		int[][] mazeMatrix = new int[size + 1][size + 1];
		for (int i = 0; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				mazeMatrix[i][j] = Integer.MAX_VALUE;
			}
		}
		resetParents(mazeMatrix);
		return mazeMatrix;
	}

	/**
	 * Adds an edge with the given weight to the graph. Uses a weight of 1 if the
	 * graph is unweighted.
	 * 
	 * @param mazeMatrix - the current maze representation
	 * @param src - source vertex
	 * @param dest - destination vertex
	 * @param weight - weight of the edge
	 */
	public static void addEdge(int[][] mazeMatrix, int src, int dest, int weight) {
		if (weighted) {
			mazeMatrix[src][dest] = weight;
		} else {
			mazeMatrix[src][dest] = 1;
		}
	}

	/**
	 * Calculates the shortest path using Dijkstra's algorithm. Finds the shortest
	 * path, if it exists, to every vertex from the given source vertex.
	 * 
	 * @param mazeMatrix - the current maze representation
	 * @param src - the source vertex
	 */
	public static void shortestPath(int[][] mazeMatrix, int src) {
		HashSet<Integer> visited = new HashSet<>();
		Comparator<Pair<Integer, Integer>> comparator = new PairComparator();
		PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<Pair<Integer, Integer>>(mazeMatrix.length,
				comparator);
		resetParents(mazeMatrix);
		resetDistances(mazeMatrix);
		mazeMatrix[0][src] = 0; // Set distance to 0 for source vertex
		int current = src;
		for (int i = 1; i < mazeMatrix.length; i++) {
			calcDistances(mazeMatrix, current, pq, visited);
			if (pq.peek() == null) { // No more nodes to visit, algorithm complete
				break;
			}
			visited.add(current);
			current = pq.poll().getX();
		}
	}

	/**
	 * Re-initializes the parents to -1 before starting a new shortest path
	 * calculation
	 * 
	 * @param mazeMatrix - the current maze representation
	 */
	public static void resetParents(int[][] mazeMatrix) {
		for (int i = 0; i < mazeMatrix.length; i++) {
			mazeMatrix[i][0] = -1;
		}
	}

	/**
	 * Re-initialized the distances to Integer.MAX_VALUE before starting a new
	 * shortest path calculation
	 * 
	 * @param mazeMatrix - the current maze representation
	 */
	public static void resetDistances(int[][] mazeMatrix) {
		for (int i = 0; i < mazeMatrix.length; i++) {
			mazeMatrix[0][i] = Integer.MAX_VALUE;
		}
	}

	/**
	 * Given a current vertex, calculates tentative distances to its neighbors.
	 * Updates distance and parent if a shorter distance is found.
	 *
	 * @param mazeMatrix - the current maze representation
	 * @param current - the source vertex
	 * @param pq - the priority queue holding the unvisited vertices
	 * @param visited - the set holding the visited vertices
	 */
	public static void calcDistances(int[][] mazeMatrix, int current, PriorityQueue<Pair<Integer, Integer>> pq,
			HashSet visited) {
		for (int i = 1; i < mazeMatrix[current].length; i++) {
			int dist = mazeMatrix[0][current];
			if (mazeMatrix[current][i] < Integer.MAX_VALUE && !visited.contains(i)) { // i is an unvisited neighbor
				dist += mazeMatrix[current][i];
				if (dist < mazeMatrix[0][i]) {
					mazeMatrix[0][i] = dist; // Update distance
					mazeMatrix[i][0] = current; // Update parent
				}
				update(pq, i, dist);
			}
		}
	}

	/**
	 * Adds or updates an inspected node to the priority queue. If the node was
	 * previously in the queue, it is removed and re-inserted with the same vertex
	 * and an updated distance.
	 * 
	 * @param pq - the queue holding the vertices yet to be visited
	 * @param vertex - the vertex we want to find or add
	 * @param dist - the current shortest distance to that vertex
	 */
	public static void update(PriorityQueue<Pair<Integer, Integer>> pq, int vertex, int dist) {
		for (Pair<Integer, Integer> p : pq) {
			if (p.getX() == vertex) {
				pq.remove(p);
				pq.add(new Pair<Integer, Integer>(vertex, dist));
				return;
			}
		}
		pq.add(new Pair<Integer, Integer>(vertex, dist));
	}

	/**
	 * Given a destination vertex, builds the shortest path from the current maze
	 * matrix.
	 * 
	 * @param mazeMatrix - the current maze representation
	 * @param dest - destination vertex
	 * @return - a LinkedList containing the shortest path to the destination vertex
	 */
	public static LinkedList<Integer> path(int[][] mazeMatrix, int dest) {
		LinkedList<Integer> path = new LinkedList<>();
		int current = dest;
		path.addFirst(current);
		while (mazeMatrix[current][0] != -1) { // Starting node will have no parent
			current = mazeMatrix[current][0];
			path.addFirst(current);
		}
		return path;
	}

}
