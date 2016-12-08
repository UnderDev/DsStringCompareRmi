package ie.gmit.sw;

/*
 * Algorithm Implemented but not working correctly
 */

public class SmithWatermanAlgorithm  implements Algorithm{

	public static final int startgapscore = -12;
	public static final int continuegapscore = -1;
	public static final int matchscore = 7;
	public static final int mismatchscore = -3;

	public static final int NOTDEF = 0;
	public static final int FROMNW = 1;
	public static final int FROMN = 2;
	public static final int FROMW = 3;
	public static final int resPerLine = 60;

	private String x; // First string
	private String y; // Second string
	private int xlen, ylen; // their lengths
	private int[][] scoreArray;
	private int[][] fromWhere;

	private String xalig, yalig; // for the alignments

	private int bestrow, bestcol; // for location of best entry
	private int bestval; // for best entry
	private int startrow, startcol; // for location of start of alignment

	public void setStrings(String a, String b) {
		x = a;
		y = b;
		xlen = x.length();
		ylen = y.length();
		scoreArray = new int[ylen + 1][xlen + 1];
		fromWhere = new int[ylen + 1][xlen + 1]; // could be smaller, but ...
	}

	public void fillScoreArray() {
		int col, row; // for indexing through array
		int northwest, north, west; // (row,col) entry will be max of these
		int best; // will be the max
		int from; // and will be from here
		// Fill the top row and left column:
		for (col = 0; col <= xlen; col++) {
			scoreArray[0][col] = 0;
			fromWhere[0][col] = NOTDEF;
		}
		for (row = 0; row <= ylen; row++) {
			scoreArray[row][0] = 0;
			fromWhere[row][0] = NOTDEF;
		}
		// Now fill in the rest of the array:
		bestcol = 0;
		bestrow = 0;
		bestval = 0;
		for (row = 1; row <= ylen; row++) {
			for (col = 1; col <= xlen; col++) {
				if (x.charAt(col - 1) == y.charAt(row - 1))
					northwest = scoreArray[row - 1][col - 1] + matchscore;
				else
					northwest = scoreArray[row - 1][col - 1] + mismatchscore;
				if (fromWhere[row][col - 1] == FROMW)
					west = scoreArray[row][col - 1] + continuegapscore;
				else
					west = scoreArray[row][col - 1] + startgapscore;
				if (fromWhere[row - 1][col] == FROMN)
					north = scoreArray[row - 1][col] + continuegapscore;
				else
					north = scoreArray[row - 1][col] + startgapscore;
				best = northwest;
				from = FROMNW;
				if (north > best) {
					best = north;
					from = FROMN;
				}
				if (west > best) {
					best = west;
					from = FROMW;
				}
				if (best <= 0) {
					best = 0;
					from = NOTDEF;
				}
				scoreArray[row][col] = best;
				fromWhere[row][col] = from;
				if (best > bestval) {
					bestval = best;
					bestrow = row;
					bestcol = col;
				}
			}
		}
	}

	public void setAlignment() {
		int row = bestrow;
		int col = bestcol;
		int val = scoreArray[row][col];
		xalig = "";
		yalig = "";
		while (val > 0) {
			if (fromWhere[row][col] == FROMN) {
				// came from north
				xalig = "-" + xalig;
				yalig = y.charAt(row - 1) + yalig;
				row--;
			} else if (fromWhere[row][col] == FROMW) {
				// came from west
				xalig = x.charAt(col - 1) + xalig;
				yalig = "-" + yalig;
				col--;
			} else {
				if (fromWhere[row][col] != FROMNW)
					System.out.println("Goofy row col " + row + " " + col);
				xalig = x.charAt(col - 1) + xalig;
				yalig = y.charAt(row - 1) + yalig;
				col--;
				row--;
			}
			val = scoreArray[row][col];
		}
		startcol = col;
		startrow = row;
	}

	/*public static void main(String[] args) {
		SmithWatermanAlgorithm sw = new SmithWatermanAlgorithm();
		sw.setStrings("ACACACTA", "AGCACACA");
		sw.fillScoreArray();
		sw.setAlignment();
		System.out.println(sw.bestval);
	}*/

	public int distance(String s, String t) {
		SmithWatermanAlgorithm sw = new SmithWatermanAlgorithm();
		sw.setStrings(s, t);
		sw.fillScoreArray();
		sw.setAlignment();
		return sw.bestval;
	}
}
