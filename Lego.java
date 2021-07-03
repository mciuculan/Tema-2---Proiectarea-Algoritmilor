import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Lego {
	public static int K, N, T; // input
	private static int ans = 0; // output
	private static int[] sequence = new int[1000]; // output 2

	private static void readInput() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("lego.in"));
			String line = br.readLine();
			String[] ints = line.split(" ");
			K = Integer.parseInt(ints[0]);
			N = Integer.parseInt(ints[1]);
			T = Integer.parseInt(ints[2]);

		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	private static void backtracking(int step, int stop,
								ArrayList<Integer> domain, ArrayList<Integer> solution) {

		// conditia de oprire
		if (step == stop) {
			check(solution, domain);
			return;
		}

		// pentru a nu porni mereu de la 1
		int i;
		if (step > 1) {
			i = solution.get(step - 1) + 1;
		} else {
			i = 1;
		}

		// pentru fiecare element din domeniu
		while (i < domain.size()) {
			solution.set(step, i);
			backtracking(step + 1, stop, domain, solution);
			i++;
		}
	}

	private static void check(ArrayList<Integer> solution,
								ArrayList<Integer> domain) {

		// lungimea celei mai lungi secvente crescatoare
		int sequenceLen = 0;
		ArrayList<Integer> finalSol = new ArrayList<>();

		// mereu solutia incepe cu 1
		finalSol.add(1);
		int i, j;
		for (i = 1; i < solution.size(); i++) {
			finalSol.add(domain.get(solution.get(i)));
		}

		for (i = 1; i <= finalSol.get(solution.size() - 1) * T; i++) {
			if (min_numbers(finalSol, i) <= T) {
				sequenceLen++;
				if (ans < sequenceLen) {
					ans = sequenceLen;
					for (j = 0; j < finalSol.size(); j++) {
						sequence[j] = finalSol.get(j);
					}
				}
			} else {
				if (ans < sequenceLen) {
					ans = sequenceLen;
					for (j = 0; j < finalSol.size(); j++) {
						sequence[j] = finalSol.get(j);
					}
				}
				sequenceLen = 0;
			}
		}
	}

	private static int min_numbers(ArrayList<Integer> solution, int max) {
		// in dp se va afla numarul minim de numere de suma i
		int[] dp = new int[max + 1];
		int i, j;

		// pentru a crea 0 ai nevoie de suma a 0 numere
		dp[0] = 0;
		// initializare
		for (i = 1; i <= max; i++) {
			dp[i] = Integer.MAX_VALUE;
		}

		for (i = 1; i <= max; i++) {
			for (j = 0; j < solution.size(); j++) {
				// am nevoie de numere crescatoare in secventa
				if (i - solution.get(j) < 0) {
					break;
				}
				if (dp[i - solution.get(j)] != Integer.MAX_VALUE) {
					dp[i] = Math.min(dp[i], dp[i - solution.get(j)] + 1);
				}
			}
		}
		return dp[max];
	}

	public static void main(String[] args) throws IOException {
		int i = 0;
		readInput();

		// initilizari
		ArrayList<Integer> domain = new ArrayList<>();
		ArrayList<Integer> solution = new ArrayList<>();
		for (i = 0; i < K - 1; i++) {
			domain.add(i, i + 2);
		}
		// mereu primul element din solutie este 1
		solution.add(0, 1);
		for (i = 1; i < N; i++) {
			solution.add(i, 0);
		}
		for (i = 0; i < 1000; i++) {
			sequence[i] = 0;
		}
		// final initializari

		// incep generarea de solutii
		backtracking(1, N, domain, solution);

		// afisari
		i = 0;
		BufferedWriter writer = new BufferedWriter(new FileWriter("lego.out"));
		writer.write(String.valueOf(ans));
		writer.write("\n");
		while (sequence[i] != 0) {
			writer.write(String.valueOf(sequence[i]));
			writer.write(" ");
			i++;
		}
		writer.close();
	}
}
