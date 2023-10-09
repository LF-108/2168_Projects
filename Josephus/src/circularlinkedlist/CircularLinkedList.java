//Lara Fernandes
//9/28/23

package circularlinkedlist;
import java.util.Iterator;
import java.util.Scanner;

public class CircularLinkedList<E> implements Iterable<E> {
	Node<E> head;
	Node<E> tail;
	int size;

	// Implement a constructor
	public CircularLinkedList() {
		
		this.head = null;
		this.tail = null;
		size = 0;
	}

	// Return Node<E> found at the specified index
	// Be sure to check for out of bounds cases
	private Node<E> getNode(int index ){
	Node<E>node_at_index = head;
	//Catches faulty index inputs
	if(index < 0 ||index >= size ) {
    	throw new IndexOutOfBoundsException("Invalid Index");
    }
    
    //loops through the references starting from head, adjusting node_at_index accordingly until 
	//the desired node is found
    
    	for(int i = 0; i<index; i++) {
    		node_at_index = node_at_index.next;
    	}
    
		return node_at_index;
	}

	// Add a node to the end of the list
	// HINT: Use the overloaded add method as a helper method
	public boolean add(E item) {
    
		//Uses the overloaded add method to add an element to the end of the list
		this.add(size,item);	
    
	return false;
	}

	
	// Cases to handle:
	//      Out of bounds
	//      Adding an element to an empty list
	//      Adding an element to the front
	//      Adding an element to the end
	//      Adding an element in the middle
	// HINT: Remember to keep track of the list's size
	public void add(int index, E item) {
    Node<E>new_Node = new Node<>(item);	
	
    //Catches invalid index input
    if(index <0 || index > size) {
    	throw new IndexOutOfBoundsException("Invalid Index");	
    }
	
    //Sets head and tail equal to each other and sets their references equal to each other
	//as only one element is present
    if(size == 0) {
    	
    	this.head = new_Node;
    	this.tail = new_Node;
	
	}
    
    //Adjusts tail and head's references, and sets head to new_Node, the new head
	else if(index == 0) {
		
		tail.next = new_Node;
		new_Node.next = head;
		head = new_Node;
	}
    
    //Adjusts tail's reference, and sets tail to new_Node, the new tail
	else if(index == size) {
		
		tail.next = new_Node;
		new_Node.next = head;
		tail = new_Node;
		
	}
    
    //Finds the node before the desired node, sets new_Node's reference to that node's next reference
    //and sets the prior node's reference to new_Node to insert new_Node into the list
	else {
		
		Node<E>temp = this.getNode(index-1);
		new_Node.next = temp.next;
		temp.next = new_Node;
    
    }
    
    //Increments the size
    size++;
	}

	// Cases to handle:
	//      Out of bounds
	//      Removing the last element in the list
	//      Removing the first element in the list
	//      Removing the last element
	//      Removing an element from the middle
	// HINT: Remember to keep track of the list's size
	public E remove(int index) {
		
		//The value that will be removed from the list
		E to_remove = null;
		
		//Catches out of bounds indexes, and throws an exception accordingly
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid Index");
        }
		
		//Sets head and tail to null since the list is now empty
		if (size == 1) {
			
			to_remove = head.item;
			head = null;
			tail = null;
		}
		
		//Adjusts the connections between head and tail if the head is removed
		else if(index == 0) {
			
			to_remove = head.item;

			tail.next = head.next;
			head = tail.next;
        
        }
        
      //Since we do not have connections to previous nodes like a double linked list
      //start at 1 node before the desired and set the references accordingly so a 
      //connection is not lost
		else if(index == size-1) {
        	
			to_remove = tail.item;
        	
        	Node<E>temp = getNode(index-1);
        	temp.next = tail.next;
        	tail = temp;
        	
        }
        
        //Finds the node before the one to be removed and sets its next reference
        //to the node after the one to be removed
        else {
        	
        	Node<E>temp = this.getNode(index-1);
        	
        	to_remove = this.getNode(index).item;
        	temp.next = this.getNode(index).next;
        	
        }
        
        //Decrements the size of the list
        size--;
		return to_remove;
	}
	
	// Stringify your list
	// Cases to handle:
	//      Empty list
	//      Single element list
	//      Multi-element list
	// Use "==>" to delimit each node
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		//Returns blank if list is empty
        if(size == 0) {
        	return "";
        }
        
        //Appends the first node in string form to the StringBuilder if only one element
        //in the list
        else if(size == 1) {
        	
            result.append(head.item + " ==> ");
         
        }
        
        //Appends each node value and an arrow to the string builder
        else {
        	for(int i = 0;i<size; i++) {
        	result.append(getNode(i).item + " ==> " );
        
        	}
        	
        }
        
		return result.toString();
	}

	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}
	
	// This class is not static because it needs to reference its parent class
	private class ListIterator<E> implements Iterator<E> {
		Node<E> nextItem;
		Node<E> prev;
		int index;
		
		@SuppressWarnings("unchecked")
		// Creates a new iterator that starts at the head of the list
		public ListIterator() {
			nextItem = (Node<E>) head;
			index = 0;
		}

		// Returns true if there is a next node
		public boolean hasNext() {	
			
		    boolean hasNext = true;
			/*Since the list is circular, there will always be a next node as long as the list is not empty
			since the list will either have another element or circle back to the beginning, even if there
			is only one element, in which case head = tail*/
            if(size == 0) {
            	return false;
            }
            
            return hasNext;
		}
		
		// Advances the iterator to the next item
		// Should wrap back around to the head
		public E next() {

            if(hasNext()== false) {
			return null;
            }
            
            //Advances prev and nexItem, and adjusts the index accordingly, to 0 if at the end of the list
            //or index + 1 for any other position where we do not need to loop back
            else {
            	
            	prev = nextItem;
            	nextItem = nextItem.next;
            	
            	if(index == size-1) {
            		index = 0;
            	}
            	
            	else {
            		index = index + 1;
            	}
            	
            }
            return prev.item;
	
		}
		
		// Remove the last node visted by the .next() call
		// For example, if we had just created an iterator,
		// the following calls would remove the item at index 1 (the second person in the ring)
		// next() next() remove()
		// Use CircularLinkedList.this to reference the CircularLinkedList parent class
		public void remove() {
             
             int to_remove = 0;
             
             //As nextItem, prev, and index get adjusted by next, we can simply use the fact that if
             //the nextItem is the head, we must remove the tail at size-1, or remove whatever value is
             //at the current index-1, since that would be the value last visited by next
             if(nextItem == head) {
            	 to_remove = size-1;
             }
             
             else {
            	 to_remove = index - 1;
            	 index--;
             }
			 
             //Calls the CircularLinkedList parent class's remove method, and uses to_remove as an index to remove
             //an element from the list
			 CircularLinkedList.this.remove(to_remove);
		}
		 
	}
	
	// The Node class
	private static class Node<E>{
		E item;
		Node<E> next;
		
		public Node(E item) {
			this.item = item;
		}
		
	}
	
	public static void main(String[] args){
	
	
	Scanner scanner = new Scanner(System.in);
	
	//Hold the amount of soliders and the number of counts respectively
	int soldier_count = 0;
	int kill_count;
	
	//Gets user input for solider amount and count
	System.out.println("Please enter the number of soldiers:");
	soldier_count = scanner.nextInt();
	
	System.out.println("Please enter the number to count");
	kill_count = scanner.nextInt();
	
	//Creates the CircularLinkedList of integers and adds soldier_count elements into the list
	CircularLinkedList<Integer> soldiers = new CircularLinkedList<Integer>();
	
	for(int i = 0; i<soldier_count; i++) {
		soldiers.add(i,i+1);
	}
	
	//Sets up the iterator and prints out the original list
	Iterator<Integer> iter = soldiers.iterator();
	
	System.out.println(soldiers);
	
	//Loops through the list, removing elements using the iterator and list methods until only 2
	//soliders remain
	while(soldiers.size > 2) {
		for(int i = 0; i<kill_count; i++) {
		iter.next();
		}
		
		iter.remove();
		System.out.println(soldiers);
		
	}
	
	}
}
