package ie.gmit.sw;

/*
 * Adapted From:
 * https://gist.github.com/k15z/16e28db4ccc3a6e33a5b
 */
public class NeedlemanWunsch  implements Algorithm{
	private static volatile int match = 1, mismatch = -1, gap =-1;

	public int distance(String a, String b) {
		int[][] dp = new int[a.length() + 1][b.length() + 1];

		// initialize dp table with empty string alignment values
		// - align("","") = 0;
		dp[0][0] = 0;
		// - align("","...") = "...".length() * gap
		for (int i = 1; i < dp.length; i++)
			dp[i][0] = dp[i - 1][0] + gap;
		// - align("...","") = "...".length() * gap
		for (int j = 1; j < dp[0].length; j++)
			dp[0][j] = dp[0][j - 1] + gap;

		// compute all the alignments
		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[i].length; j++) {
				// initialize best_score
				int best_score = Integer.MIN_VALUE;
				// perfect match?
				if (a.charAt(i - 1) == b.charAt(j - 1))
					if (best_score < dp[i - 1][j - 1] + match)
						best_score = dp[i - 1][j - 1] + match;
				// force match?
				if (a.charAt(i - 1) != b.charAt(j - 1))
					if (best_score < dp[i - 1][j - 1] + mismatch)
						best_score = dp[i - 1][j - 1] + mismatch;
				// insert gap in `a`?
				if (best_score < dp[i - 1][j] + gap)
					best_score = dp[i - 1][j] + gap;
				// insert gap in `b`?
				if (best_score < dp[i][j - 1] + gap)
					best_score = dp[i][j - 1] + gap;
				// assign best_score
				dp[i][j] = best_score;
			}
		}

		// return the bottom-right corner of the dp table
		return dp[dp.length - 1][dp[0].length - 1];
	}

	/*public static void main(String[] args) {
		NeedlemanWunsch algo = new NeedlemanWunsch();
		       
        int score=algo.distance("Distributed Systems", "Distressed Sausages");
        System.out.println("Distance: " + score);
	}*/
}
