import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/*****************************************************************
 * Name				: DijkstraAlgorithmEhlert
 * Author			: Tony Ehlert
 * Created			: Mar 9, 2023
 * Course			: CIS152 Data Structures
 * Version			: 1.0
 * OS				: Windows 11
 * Copyright		: This is my own original work based on
 *         	  	  	  specifications issued by our instructor
 * Description		: This program creates a graph and stores it as an adjacencyList and then
 * 					  calls a function that implements Dijkstra's algorithm to find the shortest path tree to each node
 * 					  in the graph and print it to the console
 *					 Input: Vertices labeled/named with a single letter and edges connecting 
 *							connecting the vertices with the weight of the edge
 *					 Output: The calculated SPT using Dijkstra's algorithm
 * Academic Honesty	: I attest that this is my original work.
 * I have not used unauthorized source code, either modified or 
 * unmodified. I have not given other fellow student(s) access to
 * my program.         
 *****************************************************************/
public class Graph {

	// ArrayList contains LinkedLists of the head node at the front of LinkedList
	ArrayList<LinkedList<Node>> nodeLists;

	/**
	 * Creates an empty Array
	 */
	Graph() {
		nodeLists = new ArrayList<>(); // empty ArrayList creation
	}

	/**
	 * This method creates a Node with a weight of 0, then creates an empty
	 * LinkedList with the newly created node being placed in the first position of
	 * the list
	 * 
	 * @param node - Node with a weight of zero
	 */
	public void addNode(Node node) {
		LinkedList<Node> currentList = new LinkedList<>();
		currentList.add(node);
		nodeLists.add(currentList);
	}

	/**
	 * This method creates a node that gets added to the corresponding LinkedList
	 * for both the starting vertex and ending vertex
	 * 
	 * @param src    - Starting vertex
	 * @param dst    - ending vertex
	 * @param weight = weight of traversal path
	 */
	public void addEdge(char src, char dst, int weight) {
		Iterator<LinkedList<Node>> nodeListsIter = nodeLists.iterator();

		// get LinkedList from arrayList and store as current list
		// alist.get(src) grabs the linkedList contained in the ArrayList index of src
		// value
		LinkedList<Node> currentList = nodeListsIter.next();

		Node currentNode = currentList.getFirst();

		while (Character.toLowerCase(currentNode.data) != Character.toLowerCase(src)) {
			currentList = nodeListsIter.next();
			currentNode = currentList.getFirst();
		}

		// dstNode = address of node to link to = alist.get(dst).get(0)
		// .get(0) grabs the first node contained in LinkedList (aka head node)
		Node dstNode = new Node(dst, weight);

		// adds node to tail of current LinkedList with src value/index == head node
		currentList.add(dstNode);

		// reset currentList iterator
		nodeListsIter = nodeLists.iterator();

		// grab first node of current list
		currentNode = currentList.get(0);

		// while loop to find matching node.data for dst node
		while (Character.toLowerCase(currentNode.data) != Character.toLowerCase(dst)) {
			currentList = nodeListsIter.next();
			currentNode = currentList.get(0);
		}

		// adds node to tail of current LinkedList
		Node srcNode = new Node(src, weight);

		currentList.add(srcNode);

	}

	/**
	 * This method checks if an edge is present between two vertices
	 * 
	 * @param src - starting vertex
	 * @param dst - ending vertex
	 * @return
	 */
	public boolean checkEdge(char src, char dst) {

		if (nodeLists.size() > 0) {
			ListIterator<LinkedList<Node>> nodeListsIter = nodeLists.listIterator();

			// get LinkedList from arrayList and store as current list
			// alist.get(src) grabs the linkedList contained in the ArrayList index of src
			// value
			LinkedList<Node> currentList = nodeListsIter.next();

			Node currentNode = currentList.getFirst();

			while ((Character.toLowerCase(currentNode.data) != Character.toLowerCase(src)) && nodeListsIter.hasNext()) {
				currentList = nodeListsIter.next();
				currentNode = currentList.getFirst();
			}

			// iterate over current linkedList and check for a node that matches the
			// destination node data
			for (Node node : currentList) {
				if (node.data == dst) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method prints the adjacencyList representing the graph
	 */
	public void print() {
		for (LinkedList<Node> currentList : nodeLists) {
			for (Node node : currentList) {
				System.out.print(node.data + "(" + node.weight + ") -> ");
			}
			System.out.println();
		}
	}

	// START OF DIJKSTRA"S ALGORITM NEEDED METHODS

	public void dijkstraSPT(char srcNode) {

		// create array to hold node data values that have been visited/calculated
		LinkedList<Character> sptSet = new LinkedList<Character>();

		// create array to hold nodes with updated dist values
		Node nodeDistArray[] = new Node[nodeLists.size()];

		// add all nodes to nodeDist array
		for (int i = 0; i < nodeLists.size(); i++) {
			nodeDistArray[i] = nodeLists.get(i).get(0);
		}

		// set dist value for starting node/srcNode to 0
		for (int i = 0; i < nodeDistArray.length; i++) {
			if (Character.toLowerCase(srcNode) == Character.toLowerCase(nodeDistArray[i].data)) {
				nodeDistArray[i].setDist(0);
			} else {
				System.out.println();
				System.out.println("\"" + Character.toUpperCase(srcNode) + "\" is not a node in this graph!!!");
				return;
			}
		}

		while (sptSet.size() < nodeLists.size()) {

			// pick node with smallest dist
			Node minDistNode = nodeDistArray[minDistIndex(nodeDistArray)];

			// add node to sptSet and set visited to true
			sptSet.add(minDistNode.data);
			nodeDistArray[minDistIndex(nodeDistArray)].setVisited(true);

			// find adjacenyNode list for matching minDistNode.data
			LinkedList<Node> adjNodeList = null;
			for (int i = 0; i < nodeLists.size(); i++) {
				if (Character.toLowerCase(minDistNode.data) == Character.toLowerCase(nodeLists.get(i).get(0).data)) {
					adjNodeList = nodeLists.get(i);
					break;
				}
			}

			// calculate dist for adjNodes and update if smaller than current dist
			for (int i = 0; i < adjNodeList.size(); i++) {
				for (int j = 0; j < nodeDistArray.length; j++) {
					if (Character.toLowerCase(adjNodeList.get(i).data) == Character
							.toLowerCase(nodeDistArray[j].data)) {
						int calcDist = minDistNode.dist + adjNodeList.get(i).weight;
						if (calcDist < nodeDistArray[j].dist) {
							nodeDistArray[j].setDist(calcDist);
						}
					}
				}
			}

		}

		System.out.println("\nShortest Distances from Node \"" + Character.toUpperCase(srcNode) + "\": ");
		for (int i = 0; i < nodeDistArray.length; i++) {
			System.out.println(
					Character.toUpperCase(srcNode) + "-->" + nodeDistArray[i].data + " = " + nodeDistArray[i].dist);
		}

	}

	private int minDistIndex(Node distNode[]) {

		// create a minimum value variable and make it equal to max
		int minDist = Integer.MAX_VALUE;

		// create variable for minDistIndex and set equal to -1
		int minDistIndex = -1;

		for (int i = 0; i < distNode.length; i++) {
			if (distNode[i].visited == false && distNode[i].dist <= minDist) {
				minDist = distNode[i].dist;
				minDistIndex = i;
			}
		}

		return minDistIndex;

	}

	/**
	 * Node class used to store the vertex name/data and the weight of the traversal
	 * path( if not first node in list)
	 */
	class Node {

		char data;
		int weight;
		int dist;
		boolean visited;

		/**
		 * Creates a Node with a default weight of 0,a max value dist, and visited=
		 * false. Only called when adding vertices to graph
		 * 
		 * @param data - Node data letter/name
		 */
		Node(char data) {
			this.data = data;
			this.weight = 0;
			this.dist = Integer.MAX_VALUE;
			this.visited = false;
		}

		/**
		 * Creates an adjacency node with the weight of the edge a max value dist, and
		 * visited= false. that gets added to a LinkedList via the addEdge method
		 * 
		 * @param data
		 * @param weight - Node data letter/name
		 */
		Node(char data, int weight) {
			this.data = data;
			this.weight = weight;
			this.dist = Integer.MAX_VALUE;
			this.visited = false;
		}

		/**
		 * this method is used to set the dist value to the value that gets passed in
		 * 
		 * @param node - node object to be set as parent
		 */
		public void setDist(int dist) {
			this.dist = dist;
		}

		/**
		 * this method is used to set the visited variable
		 * 
		 * @param visited - boolean value representing if the node has been visited
		 */
		public void setVisited(Boolean visited) {
			this.visited = visited;
		}

	}

}
