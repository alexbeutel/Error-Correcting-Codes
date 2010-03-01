import java.util.*;

public class ErrorCodesMain {
	public static void main(String[] args) {
		GF28.init();
		GF257.init();
		
		Random rnd = new Random();
		ArrayList<Integer> gens28 = GF28.findGenerators();
		System.out.println("# of Generators of GF(2^8): " + gens28.size());
		HashSet<Integer> gens257 = GF257.findGenerators();
		System.out.println("# of Generators of GF(257): " + gens257.size());
		char gen = 255;
		while(gen == 255) {
			int index = rnd.nextInt(gens28.size());
			int temp = gens28.get(index);
			if(gens257.contains(temp)) gen = (char)temp;
		}
		
		String input = "Hello, my name is Alex Beutel."; //The message to send
		//gen = 3; //If you'd prefer to hardcode the  generator (just make sure its in both GF(2^8) and GF(257)
		int s = 5;
		
		Encoder e = new Encoder(input, s, gen);
		System.out.println("Generator: " + (int)gen);
		
		char[] c28 = e.slow(); // O(nk) with GF(2^8)
		int[] cFFT = e.fast(); // O(nk) with GF(257)
		int[] c257 = e.slow257(); // FFT O(nlogn) with GF(257)
		
		Decoder d = new Decoder(gen, input.length());
		int[] bads = {}; //{0,1,2,3,4,5,6,7,8,9}; //{35, 27, 8, 3, 15, 23, 37, 18}; //{0,1,2,5, 9, 13, 19};
		
		HashSet<Integer> bad = createSet(bads);
		System.out.print("Erasures: ");
		for(int i = 0; i < 2*s; i++) {
			int b = rnd.nextInt(input.length() + 2*s);
			bad.add(b);
			System.out.print(b + ", ");
		}
		System.out.println("\n");
		
		
		char[] c2 = d.decode(c28, bad);
		String s2 = new String(c2);
		System.out.println("OUTPUT FROM O(nk) IN GF(2^8): " + s2);
		
		
		int[] c4 = d.decode257(cFFT, bad);
		char[] c5 = new char[c4.length];
		for(int i = 0; i < c4.length; i++) {
			c5[i] = (char)(c4[i]);
		}
		String s3 = new String(c5);
		System.out.println("FFT OUTPUT DECODED: " + s3);
		
		c4 = d.decode257(c257, bad);
		c5 = new char[c4.length];
		for(int i = 0; i < c4.length; i++) {
			c5[i] = (char)(c4[i]);
		}
		String s4 = new String(c5);
		System.out.println("OUTPUT FROM O(nk) IN GF(257): " + s4);
		
	}
	public static HashSet<Integer> createSet(int[] a) {
		HashSet<Integer> h = new HashSet<Integer>();
		for(int a1 : a) h.add(a1);
		return h;
	}
}
