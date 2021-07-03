import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Poduri {
	// input
	public static int N;
	public static int M;
	public static int X;
	public static int Y;
	public static String[][] poduri;
	public static Boolean[][] vis;

	private static void readInput() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("poduri.in"));
			String line = br.readLine();
			String[] ints = line.split(" ");
			N = Integer.parseInt(ints[0]);
			M = Integer.parseInt(ints[1]);
			line = br.readLine();
			ints = line.split(" ");
			poduri = new String[N + 2][M + 2];
			vis = new Boolean[N + 2][M + 2];
			X = Integer.parseInt(ints[0]);
			Y = Integer.parseInt(ints[1]);
			int i, j;
			for (i = 0; i <= N + 1; i++) {
				if (! (i == 0 || i == N + 1)) {
					line = br.readLine();
				}
				for (j = 0; j <= M + 1; j++) {
					if (i == 0 || j == 0 || i == N + 1 || j == M + 1) {
						poduri[i][j] = "P";
					} else {
						poduri[i][j] = Character.toString(line.charAt(j - 1));
					}
					vis[i][j] = false;
				}
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
	}

	public static int bfs() {
		Queue<Pair> q = new LinkedList<>();
		Pair first = new Pair(X, Y, 0);
		q.add(first);
		vis[X][Y] = true;
		int x, y;

		while (!q.isEmpty()) {
			Pair cell = q.peek();
			x = cell.first;
			y = cell.second;
			q.remove();
			if (poduri[x][y].equals("P")) {
				return cell.dist;
			}
			switch (poduri[x][y]) {
				case "O": {
					if (isValid(x, y - 1)) {
						vis[x][y - 1] = true;
						q.add(new Pair(x, y - 1, cell.dist + 1));
					}
					if (isValid(x, y + 1)) {
						vis[x][y + 1] = true;
						q.add(new Pair(x, y + 1, cell.dist + 1));
					}
					break;
				}

				case "V": {
					if (isValid(x - 1, y)) {
						vis[x - 1][y] = true;
						q.add(new Pair(x - 1, y, cell.dist + 1));
					}
					if (isValid(x + 1, y)) {
						vis[x + 1][y] = true;
						q.add(new Pair(x + 1, y, cell.dist + 1));
					}
					break;
				}

				case "D": {
					if (isValid(x - 1, y)) {
						vis[x - 1][y] = true;
						q.add(new Pair(x - 1, y, cell.dist + 1));
					}
					if (isValid(x + 1, y)) {
						vis[x + 1][y] = true;
						q.add(new Pair(x + 1, y, cell.dist + 1));
					}
					if (isValid(x, y - 1)) {
						vis[x][y - 1] = true;
						q.add(new Pair(x, y - 1, cell.dist + 1));
					}
					if (isValid(x, y + 1)) {
						vis[x][y + 1] = true;
						q.add(new Pair(x, y + 1, cell.dist + 1));
					}
					break;
				}

				default: break;
			}
		}
		return -1;
	}

	public static boolean isValid(int x, int y) {
		if (x > N + 1 || y > M + 1 || x < 0 || y < 0) {
			return false;
		}
		if (vis[x][y]) {
			return false;
		}
		return !poduri[x][y].equals(".");
	}

	public static void main(String[] args) throws IOException {
		readInput();
		BufferedWriter writer = new BufferedWriter(new FileWriter("poduri.out"));
		writer.write(String.valueOf(bfs()));
		writer.close();
	}

	static class Pair {
		int first, second, dist;
		public Pair(int first, int second, int dist) {
			this.first = first;
			this.second = second;
			this.dist = dist;
		}
	}
}
