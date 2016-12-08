package ie.gmit.sw;

/*
 * Algorithm not working or implemented
 * http://www.mathcs.emory.edu/~cheung/Courses/323/Syllabus/DynProg/Progs/LCS/Hirschberg.java
 */

public class HirschbergAlgorithm {
	static int K[][];

	/*
	 * ==================================================================
	 * findLCS_String(x, y): find the LCS STRING in string x and y
	 * ==================================================================
	 */
	public static String distance(String x, String y) {
		int k, i, j;
		int m, n;
		String y1, y2;
		String x1, x2;
		String C = "";

		m = x.length(); // m = length of x
		n = y.length(); // n = length of y

		/*
		 * ===================================================== Base case 1: ""
		 * =====================================================
		 */
		if (m == 0) {
			return ""; // LCS = ""
		}

		/*
		 * ===================================================== Base case 2: x
		 * = "?" =====================================================
		 */
		if (m == 1) {
			/*
			 * ===================================== The input x consists of 1
			 * character Find the single common character in y
			 * =====================================
			 */
			for (i = 0; i < n; i++)
				if (y.charAt(i) == x.charAt(0))
					return (x); // Found: LCS = x

			return ""; // Not found: LCS = ""
		}

		/*
		 * ===================================================== General case: x
		 * has 2 or more characters
		 * =====================================================
		 */
		int c = solveLCS(x, y); // This is the sum of the correct split
		int c1 = 0, c2 = 0;

		/*
		 * System.out.println("LCS( " + x + "," + y + ") = " + c );
		 */

		x1 = x.substring(0, m / 2); // First half of x
		x2 = x.substring(m / 2, m); // Second half of x

		/*
		 * -------------------------------------------------- Find a correct
		 * split of y --------------------------------------------------
		 */
		for (k = 0; k < n; k++) {

			c1 = solveLCS(x1, y.substring(0, k)); // LCS of first half
			c2 = solveLCS(x2, y.substring(k, n)); // LCS of second half
			/*
			 * System.out.println("Trying: "); System.out.println(" " + x1 +
			 * "<->" + y.substring(0, k) + " ==> " + c1); System.out.println(" "
			 * + x2 + "<->" + y.substring(j, k) + " ==> " + c2);
			 */
			if (c1 + c2 == c)
				break; // Found a correct split of y !!!
		}
		/*
		 * if ( c1 + c2 != c ) {
		 * System.out.println("x1 + x2 == z NOT FOUND ???"); }
		 */
		/*
		 * -------------------------------------------------- Here: k = a
		 * correct split of y ....
		 * 
		 * Solve smaller problems
		 * --------------------------------------------------
		 */

		y1 = y.substring(0, k);
		y2 = y.substring(k, n);

		String sol1 = distance(x1, y1);
		String sol2 = distance(x2, y2);

		/*
		 * ------------------------------------------------------------ Use
		 * solution of smaller problems to solve original problem
		 * ------------------------------------------------------------
		 */
		return (sol1 + sol2);
	}

	/*
	 * ==============================================================
	 * solveLCS(x,y): find the number of characters in the Longest Common
	 * Substring of x and y
	 * 
	 * This is the linear space algorithm to find length of LCS Except: I ADDED
	 * a statement to return K[1][n] at the end
	 * ==============================================================
	 */
	public static int solveLCS(String x, String y) {
		int i, j;

		if (x.length() == 0 || y.length() == 0)
			return 0;

		for (j = 0; j < y.length() + 1; j++)
			K[1][j] = 0; // x = "" ===> LCS = 0

		for (i = 1; i < x.length() + 1; i++) {
			/*
			 * ===================================================== Recycle
			 * phase: copy row K[1][...] to row K[0][...]
			 * =====================================================
			 */
			for (j = 0; j < y.length() + 1; j++)
				K[0][j] = K[1][j];

			K[1][0] = 0; // y = "" ===> LCS = 0

			for (j = 1; j < y.length() + 1; j++) {
				if (x.charAt(i - 1) == y.charAt(j - 1)) {
					K[1][j] = K[0][j - 1] + 1;
				} else {
					K[1][j] = Math.max(K[0][j], K[1][j - 1]);
				}
			}
		}

		return K[1][y.length()]; // ***** I added this
	}

	public static void main(String[] args) {
		String s1 = "testing algorithm";
		String s2 = "tst algorithm";
		K = new int[2][s1.length() + 1]; // Linear space !!!


		System.out.println(distance(s1, s2));
	}

}
