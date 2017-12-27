/*
 * An adjacency list construct that also keeps track of what vertices have not been visited by a search
 * 
 * Unvisited is a node array where all the nodes are initially connected.
 * It's like an array with a linked list working in and about it.
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class AdjacencyList {
	
	private Vert[] adjList;
	Node unvisitedHead;
	Node[] unvisited;

	public AdjacencyList(String filename) throws FileNotFoundException{
		buildList(filename);
	}	
	
	/*
	 * Builds the adjacency list and the unvisited list. Called by the constructor.
	 * 
	 * Fills adjList[] with properly numbered Verts.
	 * Then it uses the Vert method 'addNeighbor' to add edges to each, building the proper adjacency list.
	 * 
	 * Fills unvisited[] with properly numbered and connected Nodes.
	 */
	private void buildList(String filename) throws FileNotFoundException{
		
		Scanner scan = new Scanner(new FileReader(filename));
		
		int size = Integer.parseInt(scan.next());
		
		//length = size+1 for consistency because vertex numberings start at 1
		adjList = new Vert[size + 1];
		unvisited = new Node[size+1];
		
		//prepares for initialization of the arrays
		Node prevNode;
		unvisitedHead = prevNode = unvisited[1] = new Node(1);
		
		//initializes both arrays
		for (int i = 2; i <= size; i++){
			unvisited[i] = new Node(i);
			prevNode.setNext(unvisited[i]);
			unvisited[i].setPrev(prevNode);
			prevNode = unvisited[i];
			
			adjList[i] = new Vert(i);
		}
		adjList[1] = new Vert(1);
		
		//fills in the adjacency list by adding edges for neighbors
		while(scan.hasNext()){
			int i = Integer.parseInt(scan.next());
			int j = Integer.parseInt(scan.next());
			
			adjList[i].addNeighbor(j);
			adjList[j].addNeighbor(i);
		}
		
		scan.close();
	}

	/*
	 * Called after a Vertex is visited by the BFS. 
	 * Removes a node from the Unvisited list.
	 * 
	 * First if: not last node
	 * Second if: not first node
	 * Else: final node in the list (first and last)
	 */
	public void removeFromUnvisited(int i){
	
		if (unvisited[i].getNext() != null)
			unvisited[i].getNext().setPrev(unvisited[i].getPrev());
		if (unvisited[i].getPrev() != null)
			unvisited[i].getPrev().setNext(unvisited[i].getNext());
		else
			unvisitedHead = unvisitedHead.getNext();

		unvisited[i] = null;
	}

	public Vert[] getAdjList(){
		return adjList;
	}
	public Node getUnvisitedHead(){
		return unvisitedHead;
	}
	public Node[] getUnvisited(){
		return unvisited;
	}
	public Vert getVert(int i){
		return adjList[i];
	}
	
	public void printListColored(){
		for(int i = 1; i < adjList.length; i++){
			System.out.println(i + " " + 
					((adjList[i].getColor() == 0) ? "-" : ((adjList[i].getColor() == 1) ? "red" : "blue")));
		}
	}
	
	
	
	
	//used for testing:
	
	public void printUnused(Node vertList){
		Node n = vertList;
		System.out.println("\nPrinting Unused: ");
		while (n != null){
			System.out.println(n.getContents());
			n = n.getNext();
		}
	}
	public void printList(){
		System.out.println("printing list: ");
		for(int i = 0; i < adjList.length; i++){
			if (adjList[i] != null){
				Node n = adjList[i].getHead();
				if(adjList[i].getHead() == null){
					System.out.println(i + " : ... : " + ((adjList[i].getColor() == 0) ? "-" : 
						((adjList[i].getColor() == 1) ? "red" : "blue")));
				}
				while (n != null){
					System.out.println(i + " : " + n.getContents() + " : " + 
							((adjList[i].getColor() == 0) ? "-" : 
								((adjList[i].getColor() == 1) ? "red" : "blue")));
					n = n.getNext();
				}
			}
		}
	}

}
