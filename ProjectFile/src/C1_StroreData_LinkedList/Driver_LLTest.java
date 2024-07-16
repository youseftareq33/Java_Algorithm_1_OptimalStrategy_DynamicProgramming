package C1_StroreData_LinkedList;

public class Driver_LLTest {

	public static void main(String[] args) {
		LinkedList ll=new LinkedList();
		
		ll.insertLast("A");
		ll.insertLast("B");
		ll.insertLast("C");
		ll.insertLast("D");
		ll.insertLast("E");
		ll.insertLast("F");
		
		ll.printData();
		
		System.out.println("----------------------");
		
		ll.delete("B");
		
		ll.printData();
		
		System.out.println("----------------------");
		
		System.out.println(ll.search("B"));
		
		System.out.println("----------------------");
		
//		ll.clear();
//		
//		ll.printData();
		
		//System.out.println(ll.getData());
		

	}

}
