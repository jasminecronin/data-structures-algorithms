package Exercise1;

public class Exercise1 {

	public static void main(String[] args) {
		long[] result = new long[25];
		for (int k = 5; k <= 30; k++) {
			int N = (int) Math.pow(2, k);
			int[] arr = new int[N];
			for (int i = 0; i < N; i++) {
				arr[i] = i;
			}
			long total = 0;
			for (int j = 0; j <= N; j++) {
				total += binarySearch(arr, j);
			}
			long ave = total / (N+1);
			System.out.println(N + ", " + ave);
			//result[k-5] = ave;
		}
//		for (long ave : result) {
//			System.out.println(ave + " ");
//		}
	}

	static int binarySearch(int[] arr, int key) {
		int lo = 0, mid, hi = arr.length - 1;
		int cmp = 0;
		while (lo <= hi) {
			cmp++;
			mid = (lo + hi) / 2;
			if (key < arr[mid]) {
				cmp++;
				hi = mid - 1;
			} else if (arr[mid] < key) {
				lo = mid + 1;
				cmp += 2;
			} else {
				cmp += 2;
				return cmp;
			}
		}
		cmp++;
		return cmp; // key not found
	}
}
