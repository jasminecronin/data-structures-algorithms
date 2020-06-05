/**
 * Performs a bubble sort in place on a singly linked list of a generic type.
 * 
 * @author Jasmine Roebuck 30037334
 *
 */
public class SLLBubbleSort {

	/**
	 * Given a generic SLL, sorts in ascending order from the head node using a
	 * bubble sort. Data must be comparable with a compareTo() method.
	 * 
	 * @param list - the SLL to be sorted
	 */
	public static <T extends Comparable<? super T>> void BubbleSort(SLL<T> list) {
		int swapCount; // To know when to stop the sort
		SLLNode<T> current;
		SLLNode<T> next;
		SLLNode<T> prev;
		int end = list.getLength(); // Keep track of the sorted portion

		do { // Bubble sort
			current = list.head;
			next = current.next;
			prev = null;
			swapCount = 0; // Reset after every traversal
			for (int i = 1; i < end; i++) {
				if (current.info.compareTo(next.info) > 0) {
					// Swap current node with next node
					if (prev != null)
						prev.next = next;
					else // Current node is the head of the list
						list.head = next;
					current.next = next.next;
					next.next = current;
					swapCount++;

					prev = next;
					next = current.next;
					// No need to move current, it shifted forward one step with the swap
				} else { // No need to swap nodes
					prev = current;
					current = next;
					next = current.next;
				}
			}
			if (end == list.getLength()) {
				list.tail = current;
			}
			end--; // Prevents from continuing to check the sorted end of the list
		} while (swapCount != 0); // Stop when we've traversed the list and made no swaps
	}

}
