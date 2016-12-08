package ie.gmit.sw;

import java.util.Arrays;

/*
 * Adapted from:
 * 	https://github.com/tdebatty/java-string-similarity/blob/master/src/main/java/info/debatty/java/stringsimilarity/JaroWinkler.java
 * 
 * And confirmed answer with: 
 *  https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance#Example_.232
 */

public class JaroWinklerAlgorithm implements Algorithm {

	private static final double DEFAULT_THRESHOLD = 0.7;
	private static final int THREE = 3;
	private static final double JW_COEF = 0.1;
	private final double threshold;

	public JaroWinklerAlgorithm() {
		this.threshold = DEFAULT_THRESHOLD;
	}

	public final double getThreshold() {
		return threshold;
	}

	public final double similarity(final String s1, final String s2) {
		int[] mtp = matches(s1, s2);
		float m = mtp[0];
		if (m == 0) {
			return 0f;
		}
		double j = ((m / s1.length() + m / s2.length() + (m - mtp[1]) / m)) / THREE;
		double jw = j;

		if (j > getThreshold()) {
			jw = j + Math.min(JW_COEF, 1.0 / mtp[THREE]) * mtp[2] * (1 - j);
		}
		return jw;
	}

	public int distance(String s1, String s2) {
		return (int) Math.round(setDistance(s1, s2) * 100);
	}

	public final double setDistance(final String s1, final String s2) {
		return 1.0 - similarity(s1, s2);
	}

	private int[] matches(final String s1, final String s2) {
		String max, min;
		if (s1.length() > s2.length()) {
			max = s1;
			min = s2;
		} else {
			max = s2;
			min = s1;
		}
		int range = Math.max(max.length() / 2 - 1, 0);
		int[] matchIndexes = new int[min.length()];
		Arrays.fill(matchIndexes, -1);
		boolean[] matchFlags = new boolean[max.length()];
		int matches = 0;
		for (int mi = 0; mi < min.length(); mi++) {
			char c1 = min.charAt(mi);
			for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
				if (!matchFlags[xi] && c1 == max.charAt(xi)) {
					matchIndexes[mi] = xi;
					matchFlags[xi] = true;
					matches++;
					break;
				}
			}
		}
		char[] ms1 = new char[matches];
		char[] ms2 = new char[matches];
		for (int i = 0, si = 0; i < min.length(); i++) {
			if (matchIndexes[i] != -1) {
				ms1[si] = min.charAt(i);
				si++;
			}
		}
		for (int i = 0, si = 0; i < max.length(); i++) {
			if (matchFlags[i]) {
				ms2[si] = max.charAt(i);
				si++;
			}
		}
		int transpositions = 0;
		for (int mi = 0; mi < ms1.length; mi++) {
			if (ms1[mi] != ms2[mi]) {
				transpositions++;
			}
		}
		int prefix = 0;
		for (int mi = 0; mi < min.length(); mi++) {
			if (s1.charAt(mi) == s2.charAt(mi)) {
				prefix++;
			} else {
				break;
			}
		}
		return new int[] { matches, transpositions / 2, prefix, max.length() };
	}

	/*
	 * public static void main(String[] args) { Algorithm jaro = new
	 * JaroWinklerAlgorithm();
	 * 
	 * System.out.println(jaro.distance("DIXON", "DICKSONX")); }
	 */
}
