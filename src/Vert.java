/*
 * A vertex node to represent the vertexes in the adjacency list of the graph
 * 
 * head: a node that holds a list to all of this vertex's neighbors
 * visited & cycleSearch: booleans to track whether or not the BFS or search for an odd cycle has seen this vertex yet
 * color: the vertex's color: 0 for none, 1 for 1, 2 for 2
 * ogParent: an int for its BFS parent: -1 if it was the first of a disconnected section to be searched
 */
public class Vert{
	private Node head;//list of neighbors
	private boolean visited;
	private boolean cycleSearch;
	public short color;// 0,1,2
	private int ogParent;//-1,other

	public Vert(int i){
		visited = false;
		color = 0;
		ogParent = -1;
		cycleSearch = false;
	}

	public void addNeighbor(int v){
		Node n = new Node(v);
		n.setNext(head);
		head = n;
	}
	
	public int getParent(){
		return ogParent;
	}
	public void setParent(int x){
		ogParent = x;
	}
	public short getColor(){
		return color;
	}
	public void setColor(short i){
		color = i;
	}
	public Node getHead(){
		return head;
	}
	public boolean visited(){
		return visited;
	}
	public void setVisited(boolean i){
		visited = i;
	}
	public boolean getCycleSearch() {
		return cycleSearch;
	}
	public void setCycleSearch(boolean cycleSearch) {
		this.cycleSearch = cycleSearch;
	}
}