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
public class DijkstrasSPTDriver {

	public static void main(String[] args) {

		// create an empty AdjacencyListGraph
		Graph adjGraph = new Graph();

		// addition of all nodes
		adjGraph.addNode(adjGraph.new Node('A'));
		adjGraph.addNode(adjGraph.new Node('Q'));
		adjGraph.addNode(adjGraph.new Node('H'));
		adjGraph.addNode(adjGraph.new Node('T'));
		adjGraph.addNode(adjGraph.new Node('F'));
		adjGraph.addNode(adjGraph.new Node('G'));
		adjGraph.addNode(adjGraph.new Node('P'));
		adjGraph.addNode(adjGraph.new Node('C'));
		adjGraph.addNode(adjGraph.new Node('R'));
		adjGraph.addNode(adjGraph.new Node('N'));

		// addition of all edges
		adjGraph.addEdge('F', 'T', 4);
		adjGraph.addEdge('T', 'C', 7);
		adjGraph.addEdge('T', 'Q', 3);
		adjGraph.addEdge('C', 'N', 2);
		adjGraph.addEdge('N', 'P', 4);
		adjGraph.addEdge('N', 'R', 2);
		adjGraph.addEdge('R', 'H', 6);
		adjGraph.addEdge('H', 'P', 3);
		adjGraph.addEdge('H', 'A', 3);
		adjGraph.addEdge('H', 'Q', 3);
		adjGraph.addEdge('A', 'Q', 2);
		adjGraph.addEdge('Q', 'G', 2);
		adjGraph.addEdge('G', 'P', 2);

		// test edge used to verify MST calculation is correct commented out
		// adjGraph.addEdge('C', 'P', 1);

		// test of checkEdge function
		System.out.println("checkEdge Function Test (\'C\',\'A\'): " + adjGraph.checkEdge('C', 'A'));
		System.out.println("checkEdge Function Test (\'C\',\'T\'): " + adjGraph.checkEdge('C', 'T'));
		System.out.println("checkEdge Function Test (\'T\',\'C\'): " + adjGraph.checkEdge('T', 'C'));

		// print of graph
		adjGraph.print();

		// assign starting node data to variable
		char startNode = 'y';

		// call dijkstraSPT()
		adjGraph.dijkstraSPT(startNode);
	}

}
