package C1_StroreData_LinkedList;

public class LinkedList<T extends Comparable<T> >{
	
	//=== Attributes --------------------------------------------------------
    private Node<T> head;
    
    
    
    
    
    
    //=== Methods -----------------------------------------------------------
    
    //// insert at first
    public void insertathead(T data) {
        Node<T> newnode = new Node<>(data);
        if(head== null) {
            head = newnode;
        } else {
 
            newnode.setNext(head);
            head = newnode;
        }

    }
    
    //// insert at last
    public void insertLast(T data) {
        Node<T> newnode = new Node<T>(data);
        
        if(head==null) {
        	head = newnode;
        }
        else {
            Node<T> curr = head;
            while (curr.getNext() != null) {
                curr = curr.getNext();
            }
            curr.setNext(newnode);
        }
    }

    
    //// Delete
    public void delete(T data) {
		Node<T> curr = head;
		Node<T> prev = null;
		while (curr != null) {
			if (curr.getData().compareTo(data) == 0) { // found
				if (prev == null) { // case at head
					head = curr.getNext();
				} else { // case at middle or last
					prev.setNext(curr.getNext());
				}
				break;
			} // not found
			prev = curr;
			curr = curr.getNext();
		}
	}
    
    
    //// search
    public boolean search(T data) {
        Node<T> curr = head;

        while (curr != null) {
            if (curr.getData().compareTo(data) == 0) {
                return true; // found
            } else {
                curr = curr.getNext();
            }
        }
        return false; // not found
    }

    
    //// Is Empty
    public boolean isEmpty() {
    	if(head==null) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
    //// Clear
    public void clear() {
    	if(head!=null) {
    		head=null;
    	}
    	
    }

    
    //// toString
    @Override
    public String toString() {
    	String res="";
    	Node<T> curr=head;
    	
    	while(curr!=null) {
    		if (curr.getNext()!=null) {
    			res+= curr.getData()+" --> ";
            } else {
            	res+= curr.getData();
            }
    		curr=curr.getNext();
    	}
    	
    	return res;
    }
    
    
    //// Print
    public void printData() {
        Node<T> curr=head;
        
        while (curr!=null) {
            if (curr.getNext()!=null) {
                System.out.print(curr.getData()+" --> ");
            } else {
                System.out.print(curr.getData()+"\n");
            }
            curr = curr.getNext();
        }
    }


}
