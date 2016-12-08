package ie.gmit.sw;

import java.util.Random;


/*
 * Class not used, wrong implementation of EuclideanDistanceAlgorithm
 */
public class EuclideanDistanceAlgorithm {
	public static void main(String[] args) {
		EuclideanDistanceAlgorithm euc = new EuclideanDistanceAlgorithm();
		Random rnd = new Random();

		//int N = Integer.parseInt(args[0]);

		double[] a = new double[10];
		double[] b = new double[25];

		euc.print(euc.init(a, rnd));
		euc.print(euc.init(b, rnd));
		System.out.println(euc.distance(a, b));
	}

	private double[] init(double[] src, Random rnd) {
		for (int i = 0; i < src.length; i++) {
			src[i] = rnd.nextDouble();
		}
		return src;
	}

	private double distance(double[] a, double[] b) {
		double diff_square_sum = 0.0;
		for (int i = 0; i < a.length; i++) {
			diff_square_sum += (a[i] - b[i]) * (a[i] - b[i]);
		}
		return Math.sqrt(diff_square_sum);
	}

	private void print(double[] x) {
		for (int j = 0; j < x.length; j++) {
			System.out.print(" " + x[j] + " ");
		}
		System.out.println();
	}
}
