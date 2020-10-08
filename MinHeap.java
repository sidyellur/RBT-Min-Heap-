// Implementation of a min heap
//Usage of a public class called MinHeap
public class MinHeap { 
	private Building[] heap; 
	private int size; 
	private int maxisize; 

	private static final int front = 1; 

	public MinHeap(int maxisize) 
	{ 
		this.maxisize = maxisize; 
		this.size = 0; 
		heap = new Building[this.maxisize + 1]; 
		heap[0] = new Building(-1,-1,-1);
	} 

	// Function used to insert a node into the heap 
	public void insert(Building element) 
	{ 
		if (size >= maxisize) { 
			return; 
		} 
		heap[++size] = element; 
		int current = size; 
        //System.out.println("new size after insert " + size);
        while (heap[current].executedTime < heap[parent(current)].executedTime
			|| (heap[current].executedTime == heap[parent(current)].executedTime && heap[current].buildingNumber < heap[parent(current)].buildingNumber)) 
		{ 
			swap(current, parent(current)); 
			current = parent(current); 
		} 
    }
	// Function returns the position of the parent 
	//for the node which is currently at pos
	private int parent(int pos) 
	{ 
		return pos / 2; 
	} 

	// Function returns the position of 
	// the right child for the node which is currently at pos 
	private int rightChild(int pos) 
	{ 
		return (2 * pos) + 1; 
	} 

	// Function  returns the position of the 
	// left child for the node which is currently at pos 
	private int leftChild(int pos) 
	{ 
		return (2 * pos); 
	} 

	
	// Function swaps the two nodes in the heap
	private void swap(int fpos, int spos) 
	{ 
		Building tempr; 
		tempr = heap[fpos]; 
		heap[fpos] = heap[spos]; 
		heap[spos] = tempr; 
	} 

    
    public Building getMin() {
        return heap[front];
    }


    // Returns true if the passed node is a leaf node
	private boolean isLeaf(int pos) 
	{ 
		if (leftChild(pos) > size && rightChild(pos) > size) { 
            //System.out.println("LEAFNODE! " + pos + "  " + size);
			return true; 
        }
        //System.out.println("isnotleaf " + size); 
		return false; 
    } 
    
    public boolean isEmpty() {
        if(size == 0) return true;
        return false;
    }

     	// Function is used to heapify the node at pos 
	private void minHeapify(int pos) 
	{ 

	// Checks if the node is a non-leaf node and greater  than any of its child 
	if (!isLeaf(pos)) { 
		if (heap[pos].executedTime > heap[leftChild(pos)].executedTime  
                || heap[pos].executedTime > heap[rightChild(pos)].executedTime
                || (heap[pos].executedTime == heap[leftChild(pos)].executedTime && heap[pos].buildingNumber > heap[leftChild(pos)].buildingNumber)
                || (heap[pos].executedTime == heap[rightChild(pos)].executedTime && heap[pos].buildingNumber > heap[rightChild(pos)].buildingNumber)) { 
                //System.out.println("PARTIAL SUCCESS!!!");
				
	// Swap with the left child and heapify the left child 
                if (heap[leftChild(pos)].executedTime < heap[rightChild(pos)].executedTime
                    || (heap[leftChild(pos)].executedTime == heap[rightChild(pos)].executedTime && heap[leftChild(pos)].buildingNumber < heap[rightChild(pos)].buildingNumber)) { 
                    //System.out.println("SUCCESS!!!!");    
					swap(pos, leftChild(pos)); 
					minHeapify(leftChild(pos)); 
				} 

	// Swap with the right child and heapify the right child 
				else { 
					swap(pos, rightChild(pos)); 
					minHeapify(rightChild(pos)); 
				} 
			} 
		} 
	} 

	

	// Function used to build the min heap using minHeapify 
	public void minHeap() 
	{ 
		for (int pos = (size / 2); pos >= 1; pos--) { 
			minHeapify(pos); 
		} 
	} 

	// Function used to print out the contents of the heap 
	public void print() 
	{ 
		for (int i = 1; i <= size / 2; i++) { 
			System.out.print(" PARENT : " + heap[i] 
							+ " LEFT CHILD : " + heap[2 * i] 
							+ " RIGHT CHILD :" + heap[2 * i + 1]); 
			System.out.println(); 
		} 
	} 

	// Function used to remove and return the minimum element from the heap 
	public Building remove() 
	{ 
		Building popped = heap[front]; 
        heap[front] = heap[size--]; 
        //System.out.println("new size after delete " + size);
        if(size != 0)
		    minHeapify(front); 
		return popped; 
	} 

} 
