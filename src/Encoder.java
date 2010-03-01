import java.util.*;

public class Encoder {
	int k, n, s;
	String input;
	char gen;
	public Encoder(String m, int s, char gen) {
		input = m;
		k = m.length();
		this.s = s;
		n = 2*s + k;
		this.gen = gen;
	}
	public char[] slow() {
		char[] cin = input.toCharArray();
		char[] m = new char[n];
		for(int i = 0; i < n; i++) {
			m[i] = 0;
			if(i < cin.length) m[i] = cin[i];
		}
		char[] c = new char[n];
		
		// i is row of T and index of c, j is col and thus index of m
		GF28 wn = new GF28((char)1);
		char[][] A = new char[n][n];
		for(int i = 0; i < n; i++) {
			
			GF28 cur = new GF28((char)1);
			
			GF28 tot = new GF28((char)0);
			
			for(int j = 0; j < n; j++) {
				A[i][j] = cur.val;
				tot = cur.mult(m[j]).add(tot);
				cur = cur.mult(wn);
			}
			wn = wn.mult(gen);
			c[i] = tot.val;
		}
		return c;
	}
	
	public int[] slow257() {
		//int n = 256; //If you want the encoded message from here to be the same as that from FFT
		char[] cin = input.toCharArray();
		int[] m = new int[n];
		for(int i = 0; i < n; i++) {
			m[i] = 0;
			if(i < cin.length) m[i] = cin[i];
		}
		int[] c = new int[n];
		
		// i is row of T and index of c, j is col and thus index of m
		GF257 wn = new GF257(1);
		int[][] A = new int[n][n];
		for(int i = 0; i < n; i++) {
			
			GF257 cur = new GF257(1);
			
			GF257 tot = new GF257(0);
			
			for(int j = 0; j < n; j++) {
				A[i][j] = cur.val;
				tot = cur.mult(m[j]).add(tot);
				cur = cur.mult(wn);
			}
			wn = wn.mult(gen);
			c[i] = tot.val;
		}
		return c;
	}
	
	public int[] fast() {
		char[] cin = input.toCharArray();
		int[] intIn = new int[cin.length];
		for(int i = 0; i < cin.length; i++) intIn[i] = (int)cin[i];
		return FFT(intIn);
	}
	
	public int[] FFT(int[] a) {
		int size = n;
		size = 256;
		int[] b = new int[size];
		for(int i = 0; i < b.length; i++) b[i] = 0;
		for(int i = 0; i < a.length; i++) b[i] = a[i];
		return doFFT(b, gen);
	}
	
	public String toString(int[] a) {
		String s = "";
		for(int a1 : a) s = s + a1 + ", ";
		return s;
	}
	
	public int[] doFFT(int[] a, int gen) {
		if(a.length == 1) return a;
		int wn = gen;
		GF257 w = new GF257(1);
		
		int[] U = new int[a.length/2];
		int[] V = new int[a.length/2];
		for(int i = 0; i < a.length; i++) {
			if(i % 2 == 0) U[i/2] = a[i];
			else V[i/2] = a[i];
		}
		
		int[] y0 = doFFT(U, (new GF257(gen)).mult(gen).val);
		int[] y1 = doFFT(V, (new GF257(gen)).mult(gen).val);
		int[] y = new int[a.length];
		
		for(int i = 0; i < a.length; i++ ) {
			y[i] = w.mult(y1[i % (a.length/2)]).add(y0[i % (a.length/2)]).val;
			w = w.mult(gen);
		}
		return y;
	}

}
