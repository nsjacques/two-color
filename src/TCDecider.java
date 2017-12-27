/*
 * A 'decider' object that keeps an adjacency list of the graph and two nodes.
 * If not two-colorable, the nodes store the two vertexes in the odd-cycle that initially prove the property.
 * 
 * It uses an iterative breadth-first-search that utilizes a TCQueue object.
 * The BFS method uses another method (queueNeighbors) to queue all the neighbors of the current vertex in the BFS.
 * 
 * Queuing the neighbors is where a potential break in the TC property can be discovered:
 * 		Two neighboring vertexes having the same color
 * 
 * If that happens then the BFS breaks and the object searches for the odd cycle.
 * If it does not happen then the BFS finishes and the object prints out the colored graph.
 * 
 */
import java.io.*;

public class TCDecider {
	
	AdjacencyList AL;
	Node oddCycleNode1;
	Node oddCycleNode2;
	Boolean twoColorable;
	Node cycleHead = null;
	
	public TCDecider(){
	}
	
	public void checkTC(String filename) throws FileNotFoundException{
		
		//create an adjacency list for the file
		AL = new AdjacencyList(filename);
		
		//BFS it and receive a -1 if and only if the graph is NOT two-colorable
		int result = BFS();
		
		
		if (result == -1){
			twoColorable = false;
			System.out.println("No");
			Node cycleHead = findCycle();//get the odd-cycle's head
		}
		else{
			twoColorable = true;
			System.out.println("Yes");
		}
	}

	/*
	 * Enqueues the first node and then loops while the queue is nonempty.
	 * In the loop, it dequeues and stores the result. 
	 * 
	 * It checks if this result is the first of a disconected
	 * portion of the graph to be processed. If so, we give it an arbitrary color (1).
	 * 
	 * It then enqueues the neighbors. If this results in the discovery that the graph is not TC,
	 * 	then BFS returns a -1 immediately.
	 * 
	 * If that wasn't the case, we mark the node visited and remove it from the unvisited list.
	 * 
	 * If the queue is empty by the end of this but there are still unvisited nodes, then we enqueue one of
	 * those nodes.
	 */
	private int BFS(){
		//an integer queue for the BFS
		TCQueue q = new TCQueue();
		
		//add the first unvisited node to the queue
		q.enqueue(AL.getUnvisitedHead().getContents());
		
		//until the queue is empty
		while(!q.isEmpty()){
			
			int currVertex = q.dequeue();
			
			//first node of a separated cut, set arbitrary color (1)
			if (AL.getVert(currVertex).getParent() == -1){
				AL.getVert(currVertex).setColor((short)1);
			}
			
			//if queuing the neighbors found an odd-cycle (signaled by a -1), return a -1 to checkTC
			if (queueNeighbors(q, currVertex) == -1)
				return -1;
			
			//set this vertex's visited field to true, already queued/dequeued
			AL.getVert(currVertex).setVisited(true);
			
			//remove this vertex from the unvisited list
			AL.removeFromUnvisited(currVertex);
			
			//if the queue is now empty and the unvisited list is not,
			//add the next unvisited vertex to the queue
			if (q.isEmpty() && AL.getUnvisitedHead() != null){
				int i = AL.getUnvisitedHead().getContents();
				q.enqueue(i);
			}
		}
		return 0;//not -1 and thus the graph is two-colorable
	}
	
	/*
	 * Runs through the current Vertex's list of neighbors and processes them:
	 * 
	 * if the neighbor has not been enqueued (color == 0) then we give it the proper color
	 * 		and give it the parent of the current node in BFS.
	 * else if it has the correct color already, we move on without enqueuing it
	 * else if it has the wrong color, we return -1 immediately 
	 * 
	 * if processing goes well, we just return a 1.
	 */
	private short queueNeighbors(TCQueue q, int currVertex){
		
		//grab the head of the list of neighbors
		Node n = AL.getVert(currVertex).getHead();
		
		//loop through the whole list until you reach the end
		while (n != null){
			
			//neighbor's number stored in i
			int i = n.getContents();
			
			//unvisited
			if(AL.getVert(i).getColor() == 0){
				AL.getVert(i).setColor((short) ((AL.getVert(currVertex).getColor() == 1) ? 2 : 1));
				AL.getVert(i).setParent(currVertex);
			}
			//already visited
			else{
				//Two same-level nodes have the same color, odd cycle, broken
				//Store the two nodes globally and return a -1 to signal the issue
				if (AL.getVert(i).getColor() == AL.getVert(currVertex).getColor()){
					oddCycleNode1 = new Node(i);
					oddCycleNode2 = new Node(currVertex);
					return -1;
				}
				//has correct color
				else{
					n = n.getNext();
					continue;
				}
			}
			//enqueue this number and move on to the next neighbor
			q.enqueue(i);
			n = n.getNext();
		}
		return 1;
	}
	
	/*
	 * Finds the cycle using the two broken nodes from the BFS
	 * It creates two paths.
	 * a and b are connected so it traces their parents back until there's an intersection (already processed parent)
	 * then it attaches the two paths to represent the final cycle
	 * Path a adds new nodes to the end and b adds new nodes to the front
	 */
	private Node findCycle(){
		boolean condition = true;
		
		Node a = oddCycleNode1;//head of path a
		Node at = oddCycleNode1;//tail of path a
		Node b = oddCycleNode2;//head of path b
		
		AL.getAdjList()[at.getContents()].setCycleSearch(true);//already seen in the cycle search
		AL.getAdjList()[b.getContents()].setCycleSearch(true);//already seen in the cycle search
		
		while (condition){
			//if at has a parent
			if (AL.getAdjList()[at.getContents()].getParent() != -1){
				//if the parent isn't used add it to {at}
				if (AL.getAdjList()[AL.getAdjList()[at.getContents()].getParent()].getCycleSearch() == false){
					Node n = new Node(AL.getAdjList()[at.getContents()].getParent());
					at.setNext(n);
					at = n;
					AL.getAdjList()[at.getContents()].setCycleSearch(true);
				}
				else{
					condition = false;
				}
			}
			//if b has a parent
			if (AL.getAdjList()[b.getContents()].getParent() != -1){
				if(AL.getAdjList()[AL.getAdjList()[b.getContents()].getParent()].getCycleSearch() == false){
					Node n = new Node(AL.getAdjList()[b.getContents()].getParent());
					n.setNext(b);
					b = n;
					AL.getAdjList()[b.getContents()].setCycleSearch(true);
				}
				else{
					condition = false;
				}
			}
		}
		//attach the paths
		at.setNext(b);
		return a;
	}
	private void printCycle(Node a){
		while (a != null){
			System.out.print(a.getContents() + " ");
			a = a.getNext();
		}
	}
	
	// Called iff it is two-colorable
	private void TCToString(){
		AL.printListColored();
	}

	public void printResult(){
		if(twoColorable)
			TCToString();
		else
			printCycle(cycleHead);
	}
}