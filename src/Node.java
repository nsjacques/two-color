/*
 * A int-holding Node object
 * 
 * Getters and setters for its next node and previous node
 * A getter for its contents
 */
public class Node{
	private int contents;
	private Node next;
	private Node prev;

	public Node(int i){
		contents = i;
	}
	
	public int getContents(){
		return contents;
	}
	public void setNext(Node n){
		next = n;
	}
	public Node getNext(){
		return next;
	}
	public void setPrev(Node n){
		prev = n;
	}
	public Node getPrev(){
		return prev;
	}
}