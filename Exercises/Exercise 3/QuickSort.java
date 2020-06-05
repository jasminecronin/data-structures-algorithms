import java.util.ArrayList;
import java.util.Random;

/**
 * CPSC 331, Spring 2018
 *
 * Quicksort code adapted from:
 * Data Structures and Algorithms in Java, by Adam Drozdek
 * Available online at:
 * http://www.mathcs.duq.edu/drozdek/DSinJava/
 */

public class QuickSort {
	
	public static void main(String[] args) {
		Random r = new Random();
		for (int k = 10; k <= 25; k++) {
			ArrayList<Integer> result = new ArrayList<>();
			N = (int) Math.pow(2, k);
			Integer[] arr = new Integer[N];
			for (int i = 0; i < N; i++) {
				arr[i] = r.nextInt();
			}
			quicksort(arr);
			//System.out.println( N + " | " + comparisons + " | " + swaps + " | " + aveRatio);
			System.out.println(comparisons);
		}

	}
	
	private static int comparisons;
	private static int swaps;
	private static int count;
	private static int N;
	private static double ratioSum;
	private static double aveRatio;
	
	public static double ratio(int upper, int first, int last) {
		int p1 = upper - first;
		int p2 = last - upper;
		return (double) Math.max(p1, p2) / ((last - first) + 1);
	}
    
    public static void swap(Object[] a, int e1, int e2) {
    	swaps++;
        Object tmp = a[e1];
        a[e1] = a[e2];
        a[e2] = tmp;
    }
    
    private static <T extends Comparable<? super T>> void quicksort(T[] data, int first, int last) {
    	int lower = first + 1, upper = last;
        swap(data,first,(first+last)/2);
        T bound = data[first];
        while (lower <= upper) {
            while (bound.compareTo(data[lower]) > 0) {
                lower++;
                comparisons++;
            }
            while (bound.compareTo(data[upper]) < 0) {
                upper--;
                comparisons++;
            }
            if (lower < upper) {
            	comparisons++;
                swap(data,lower++,upper--);
            }
            else {
            	lower++;
            	comparisons++;
            }
        }
        swap(data,upper,first);
        ratioSum += ratio(upper, first, last);
        count++;
        if (first < upper-1) {
        	comparisons++;
            quicksort(data,first,upper-1);
        }
        if (upper+1 < last) {
        	comparisons++;
            quicksort(data,upper+1,last);
        }
    }
    
    public static <T extends Comparable<? super T>> void quicksort(T[] data) {
    	comparisons = 0;
    	swaps = 0;
        if (data.length < 2)
            return;
        int max = 0;
        // find the largest element and put it at the end of data;
        for (int i = 1; i < data.length; i++)
            if (data[max].compareTo(data[i]) < 0) {
                max = i;
                comparisons++;
            }
            else comparisons++;
        swap(data,data.length-1,max);    // largest el is now in its
        quicksort(data,0,data.length-2); // final position;
        
        aveRatio = (double) ratioSum / count;
    }
}
