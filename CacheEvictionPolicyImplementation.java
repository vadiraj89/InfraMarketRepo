import java.util.HashMap; 

class Node { 
	int key; 
	int value; 
	Node pre; 
	Node next; 

	public Node(int key, int value) 
	{ 
		this.key = key; 
		this.value = value; 
	} 
} 

abstract class CacheEvictionPolicy { 
    HashMap<Integer, Node> map; 
    int capicity, count; 
	Node head, tail; 

	public CacheEvictionPolicy(int capacity) 
	{ 
		this.capicity = capacity; 
		map = new HashMap<>(); 
		head = new Node(0, 0); 
		tail = new Node(0, 0); 
		head.next = tail; 
		tail.pre = head; 
		head.pre = null; 
		tail.next = null; 
		count = 0; 
	} 

	public void deleteNode(Node node) 
	{ 
		node.pre.next = node.next; 
		node.next.pre = node.pre; 
	} 

	public void addToHead(Node node) 
	{ 
		node.next = head.next; 
		node.next.pre = node; 
		node.pre = head; 
		head.next = node; 
	} 

	// This method works in O(1) 
	public int get(int key) 
	{ 
		if (map.get(key) != null) { 
			Node node = map.get(key); 
			int result = node.value; 
			deleteNode(node); 
			addToHead(node); 
			System.out.println("Got the value : " + 
				result + " for the key: " + key); 
			return result; 
		} 
		System.out.println("Did not get any value" + 
							" for the key: " + key); 
		return -1; 
	} 

	// This method works in O(1) 
	abstract void set(int key, int value);
	
} 

class MRUCache extends CacheEvictionPolicy { 
    public MRUCache(int capacity) {
        super(capacity);
    }
    
     void set(int key, int value) 
	{ 
		 
		if (map.get(key) != null) { 
			Node node = map.get(key); 
			node.value = value; 
			deleteNode(node); 
			addToHead(node); 
		} 
		else { 
			Node node = new Node(key, value); 
			map.put(key, node); 
			if (count < capicity) { 
				count++; 
				addToHead(node); 
			} 
			else { 
				map.remove(head.next.key); 
				deleteNode(head.next); 
				addToHead(node); 
			} 
		} 
	} 
    
}
class LRUCache extends CacheEvictionPolicy { 
    public LRUCache(int capacity) {
        super(capacity);
    }
    
     void set(int key, int value) 
	{ 
	
		if (map.get(key) != null) { 
			Node node = map.get(key); 
			node.value = value; 
			deleteNode(node); 
			addToHead(node); 
		} 
		else { 
			Node node = new Node(key, value); 
			map.put(key, node); 
			if (count < capicity) { 
				count++; 
				addToHead(node); 
			} 
			else { 
				map.remove(tail.pre.key); 
				deleteNode(tail.pre); 
				addToHead(node); 
			} 
		} 
	} 
    
}
public class TestCacheEvictionPolicy { 
	public static void main(String[] args) 
	{ 
		System.out.println("Going to test the MRU "+ 
						" Cache Implementation"); 
		CacheEvictionPolicy mruCache = new MRUCache(2); 

		mruCache.set(10, 10); 
System.out.println("head next "+mruCache.head.next.value+"  Prev  "+mruCache.tail.pre.value);
		
		mruCache.set(20, 20); 
      System.out.println("head next "+mruCache.head.next.value+"  Prev  "+mruCache.tail.pre.value);

		

		
		mruCache.set(30, 30); 
System.out.println("head next "+mruCache.head.next.value+"  Prev  "+mruCache.tail.pre.value);


		mruCache.set(40, 40); 
      System.out.println("head next "+mruCache.head.next.value+"  Prev  "+mruCache.tail.pre.value);


System.out.println("Going to test the LRU "+ 
						" Cache Implementation"); 
		CacheEvictionPolicy lruCache = new LRUCache(2); 

		lruCache.set(10, 10); 
System.out.println("head next "+lruCache.head.next.value+"  Prev  "+lruCache.tail.pre.value);
		
		lruCache.set(20, 20); 
      System.out.println("head next "+lruCache.head.next.value+"  Prev  "+lruCache.tail.pre.value);

		

		
		lruCache.set(30, 30); 
System.out.println("head next "+lruCache.head.next.value+"  Prev  "+lruCache.tail.pre.value);


		lruCache.set(40, 40); 
      System.out.println("head next "+lruCache.head.next.value+"  Prev  "+lruCache.tail.pre.value);

	} 
} 
