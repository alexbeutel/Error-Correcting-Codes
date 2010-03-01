import java.util.*;

public class Decoder {
	char gen;
	int k;
	public Decoder(char gen, int k) {
		this.gen = gen;
		this.k = k;
	}
	
	public static void printMatrix(char[][] A) {
		for(int i = 0; i < A.length; i++) {
			for(int j = 0; j < A[i].length; j++) {
				System.out.print((int)A[i][j] + " ");
			}
			System.out.println("");
		}
	}
	public static void printMatrix(int[][] A) {
		for(int i = 0; i < A.length; i++) {
			for(int j = 0; j < A[i].length; j++) {
				System.out.print((int)A[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public char[] gaussianElimination(char[][] A, char[] c) {
		char[] m = new char[k];
		for(int i = 0; i < k; i++) {
			GF28 a = new GF28(A[i][i]);
			GF28 inv = a.getInverse();
			for(int j = i+1; j < k; j++) {
				GF28 b = new GF28(A[j][i]);
				b = b.mult(inv);
				for(int k = 0; k < this.k; k++) {
					GF28 v = new GF28(A[i][k]);
					A[j][k] = v.mult(b).add(A[j][k]).val;
				}
				GF28 v = new GF28(c[i]);
				c[j] = v.mult(b).add(c[j]).val;
			}
		}
		
		// A is now in row echelon form
		
		for(int i = k-1; i >= 0; i--) {
			
			GF28 sum = new GF28((char)c[i]);
			for(int j = k-1; j > i; j--) {
				GF28 v = new GF28(A[i][j]);
				sum =  v.mult(m[j]).add(sum);
			}
			
			GF28 a = new GF28(A[i][i]);
			GF28 inv = a.getInverse();
			m[i] = sum.mult(inv).val;
		}
		
		
		return m;
	}
	
	public char[] decode(char[] allC, HashSet<Integer> bad) {
		GF28 wn = new GF28((char)1);
		char[][] A = new char[k][k];
		char[] c = new char[k];
		char[] m = new char[k];
		int cnt = 0;
		
		for(int i = 0; i < allC.length; i++) {
			if(i != 0) wn = wn.mult(gen);
			if(bad.contains(i)) continue;
			
			GF28 cur = new GF28((char)1);
			for(int j = 0; j < k; j++) {
				A[cnt][j] = cur.val; 
				cur = cur.mult(wn);
			}
			c[cnt] = allC[i];
			
			cnt++;
			if(cnt >= k) break;
		}
		
		m = gaussianElimination(A,c);
		
		return m;
	}
	
	
	
	public int[] gaussianElimination257(int[][] A, int[] c) {
		int[] m = new int[k];		
		for(int i = 0; i < k; i++) {
			GF257 a = new GF257(A[i][i]);
			GF257 inv = a.getInverse();
			for(int j = i+1; j < k; j++) {
				
				GF257 b = new GF257(A[j][i]);
				if(A[j][i] != 0) b = new GF257(257 - A[j][i]);
				b = b.mult(inv);
				for(int k = 0; k < this.k; k++) {
					GF257 v = new GF257(A[i][k]);
					A[j][k] = v.mult(b).add(A[j][k]).val;
				}
				GF257 v = new GF257(c[i]);
				c[j] = v.mult(b).add(c[j]).val;
				 
			}
		}
		
		// A is now in row-echelon form
		
		for(int i = k-1; i >= 0; i--) {
			
			GF257 sum = new GF257(0);
			for(int j = k-1; j > i; j--) {
				GF257 v = new GF257(A[i][j]);
				sum =  v.mult(m[j]).add(sum);
			}
			
			GF257 a = new GF257(A[i][i]);
			GF257 inv = a.getInverse();
			GF257 right = (new GF257(c[i])).minus(sum); //new GF257(GF257.minus(c[i], sum.val));
			m[i] = right.mult(inv).val;
		}
		
		
		return m;
	}
	
	public int[] decode257(int[] allC, HashSet<Integer> bad) {
		GF257 wn = new GF257(1);
		int[][] A = new int[k][k];
		int[] c = new int[k];
		int[] m = new int[k];
		int cnt = 0;
		
		for(int i = 0; i < allC.length; i++) {
			if(i != 0) wn = wn.mult(gen);
			if(bad.contains(i)) continue;
			
			GF257 cur = new GF257(1);
			for(int j = 0; j < k; j++) {
				A[cnt][j] = cur.val; 
				cur = cur.mult(wn);
			}
			c[cnt] = allC[i];
			
			cnt++;
			if(cnt >= k) break;
		}
		
		m = gaussianElimination257(A,c);
		
		return m;
	}
	
}
