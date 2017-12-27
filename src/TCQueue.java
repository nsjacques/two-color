/*
 * An int-holding FIFO container that tracks its head, tail, and size
 * 
 * enqueue, dequeue, getter for head, isEmpty, printQueue
 */
public class TCQueue{
	private Node head;
	private Node tail;
	private int size;
	
	public TCQueue(){
	}
	
	public void enqueue(int i){
		Node n = new Node(i);
		if (size == 0){
			tail = head = n;
		}
		else{
			tail.setNext(n);
			tail = n;
		}
		size++;
	}
	public int dequeue(){
		if (head == null) return -1;
		int i = head.getContents();
		head = head.getNext();
		size--;
		if (size < 2) tail = head;
		return i;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	public Node getHead(){
		return head;
	}

	public void printQueue(){
		Node n = head;
		System.out.println("\nprinting queue: ");
		while(n != null){
			System.out.print(n.getContents() + ", ");
			n = n.getNext();
		}
		System.out.println("\n");
	}
}
