package src.GUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Heatmap {
  private final long[] ships;
  private List<List<Long[]>> iterationsByShip;

  public Heatmap(long[] ships) {
    this.ships = ships;
  }

  private List<Long[]> getShipIterations(long ship) {
    List<Long[]> iterations = new ArrayList<>();

    long horizontal = 255L >>> (8L - ship);
    long vertical = 72340172838076673L >>> 8L * (8L - ship);
    for (long a = 0; a < 8; a++) {
      for (long b = 0; b < 9 - ship; b++) {
        iterations.add(new Long[] { horizontal, horizontal | (((horizontal << 1L) | (horizontal >>> 1L)) & (255L << (a * 8L))) | (horizontal >>> 8L) | (horizontal << 8L) });
        iterations.add(new Long[] { vertical, vertical | (((vertical << 8L) | (vertical >>> 8L)) & (72340172838076673L << a)) | (vertical >>> 1L) | (vertical << 1L) });
        if (b < 8 - ship) {
          horizontal <<= 1L;
          vertical <<= 8L;
        }
      }
      horizontal = horizontal << ship;
      vertical = vertical >>> ((8L - ship) * 8L - 1L);
    }

    return iterations;
  }

  public long[] genHeatmap() {
    iterationsByShip = Arrays.stream(ships).mapToObj(ship -> getShipIterations(ship)).toList();

    Stream<Long> stack = iterate(0L, ships.length - 1);

    long[] heatmap = new long[64];

    stack.forEach(iteration -> {
      for (long i = 0; i < 64; i++) {
        heatmap[(int)i] += iteration >>> i & 1;
      }
    });

    return heatmap;
  }

  private Stream<Long> iterate(long board, int i) {
    Stream<Long[]> filtered = iterationsByShip.get(i).stream().filter(iteration -> (board & iteration[0]) == 0);
    return i > 0
      ? filtered.flatMap(iteration -> iterate(board | iteration[1], i - 1).map(it -> it | iteration[0]))
      : filtered.map(iteration -> iteration[0]);
  }
  
  public static void main(String[] args) {
    Heatmap heatmap = new Heatmap(new long[] { 2, 3, 4, 6 });
    long startTime = System.nanoTime();
    long[] thing = heatmap.genHeatmap();
    long estimatedTime = System.nanoTime() - startTime;
    
    for (int i = 0; i < 64; i++) {
      System.out.print(Long.toString(thing[i]) + " ");
      if ((i % 8) == 7) System.out.println("");
    }

    System.out.println((double) estimatedTime / 1000000);

    // List<Long[]> iterations = heatmap.getShipIterations(2);
    // for (Long[] iteration : iterations) {
    //   heatmap.printBoard(iteration[1]);
    // }
  }
}