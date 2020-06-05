import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Exercise2 {

	public static void main(String[] args) throws IOException {
		int N = 11344; // Taken from words.txt
		N = (int) Math.floor(N / 0.65);
		int tableSize = nearestPrime(N);
		
		int[] customHash = new int[tableSize];
		int[] defaultHash = new int[tableSize];
		for (int i = 0; i < tableSize; i++) {
			customHash[i] = 0;
			defaultHash[i] = 0;
		}
		File fin = new File("words.txt");
		BufferedReader br = new BufferedReader(new FileReader(fin));
		 
		String line = null;
		int index1;
		int index2;
		while ((line = br.readLine()) != null) {
			index1 = getCustomIndex(line, tableSize);
			customHash[index1]++;
			index2 = getDefaultIndex(line, tableSize);
			defaultHash[index2]++;
		}
	 
		br.close();
		
		determineGoodness(customHash);
		determineGoodness(defaultHash);
	}
	
	public static int nearestPrime(int n) {
		if (isPrime(n)) return n;
		//int m = n;
		boolean higher;
		//boolean lower;
		do {
			higher = isPrime(++n);
			//lower = isPrime(--m);
		} while (!higher);
		//if (higher) 
		return n;
		//else return m;
	}
	
	public static boolean isPrime(int n) {
		if (n % 2 == 0) return false;
		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			if (n % i == 0) return false;
		}
		return true;
	}
	
	public static int getCustomIndex(String word, int size) {
		MyString s = new MyString(word);
		int index = s.hashCode();
		index = index % size;
		if (index < 0) index += size;
		return index;
	}
	
	public static int getDefaultIndex(String word, int size) {
		int index = word.hashCode();
		index = index % size;
		if (index < 0) index += size;
		return index;
	}
	
	public static void determineGoodness(int table[]) {
		int size = table.length;
		int count = 0; // count occupied slots
		int max = 0; // highest number of collisions
		int sum = 0; // total collisions sum
		for (int i : table) {
			if (i > 0) count++;
			if (i > max) max = i;
			if (i > 1) sum += (i - 1);
		}
		
		System.out.println("Table Size: " + size);
		System.out.println(String.format("Percentage Empty: %.2f",  (((float) size - count) / size) * 100));
		System.out.println("Maximum Collisions: " + max);
		System.out.println(String.format("Average Collisions: %1$.2f", (float) sum / count));
		System.out.println();
	}

}
