package src.ALG.battleshipbot;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class Heatmap {
  private static class LongHeatmap extends Heatmap {
    private long width;
    private long height;
    private int[] ships;

    private Positions positions;
    private Random random = new Random();

    private LinkedNode<Long> stack;
    private Set<Long> hits = new HashSet<>();
    private Set<Long> misses = new HashSet<>();
    private long lastHit;

    public LongHeatmap(int width, int height, int[] ships, int placementRules) {
      this.width = width;
      this.height = height;
      this.ships = ships;
      this.positions = new Positions(width, height, new Positions.PlacementRules(placementRules));
      this.heatmap = new long[width * height];
    }

    @Override
    public void generate() {
      stack = LinkedNode.fromList(iterate(0, ships.length - 1).toList());
      
      heatmap = new long[(int)(width * height)];
      for (long iteration : stack) {
        for (long i = 0; i < width * height; i++) {
          heatmap[(int)i] += iteration >>> i & 1;
        }
      }
    }

    private Stream<Long> iterate(long board, int i) {
      Stream<Long[]> filtered = positions.getLong(ships[i]).stream().filter(iteration -> (board & iteration[0]) == 0);
      return i > 0
        ? filtered.flatMap(iteration -> iterate(board | iteration[1], i - 1).map(it -> it | iteration[0]))
        : filtered.map(iteration -> iteration[0]);
    }

    public LinkedNode<Long> getStack() {
      return stack;
    }

    @Override
    public long getOptimalShot() {
      long max = LongStream
              .range(0, heatmap.length)
              .filter(i -> !hits.contains(i))
              .map(i -> heatmap[(int)i])
              .max().orElse(0);

      List<Long> bestPositions = new ArrayList<>();
      for (int i = 0; i < width * height; i++) {
        if (heatmap[i] == max && !hits.contains((long)i) && !misses.contains((long)i)) bestPositions.add((long)i);
      }

      return bestPositions.get(random.nextInt(bestPositions.size()));
    }

    @Override
    public void hit(long position) {
      LinkedNode<Long> current = stack;
      LinkedNode<Long> previous = null;

      hits.add(position);
      lastHit = position;

      long mask = 1L << position;
      while (current != null) {
        long value = current.getValue();
        if ((value & mask) == 0L) {
          for (long i = 0; i < width * height; i++) {
            heatmap[(int)i] -= value >>> i & 1L;
          }
          if (previous == null) {
            stack = current.getNext();
          } else {
            previous.remove();
          }
        } else {
          previous = current;
        }
        current = current.getNext();
      }
    }

    @Override
    public void miss(long position) {
      LinkedNode<Long> current = stack;
      LinkedNode<Long> previous = null;

      misses.add(position);

      long mask = 1L << position;
      while (current != null) {
        long value = current.getValue();
        if ((~value & mask) == 0) {
          for (long i = 0; i < width * height; i++) {
            heatmap[(int)i] -= value >>> i & 1L;
          }
          if (previous == null) {
            stack = current;
          } else {
            previous.remove();
          }
        } else {
          previous = current;
        }
        current = current.getNext();
      }
    }

    @Override
    public void sink(long length) {
      long ship = 1L << lastHit;
      boolean isHorizontal = lastHit % width > 0 && hits.contains(lastHit - 1) || lastHit % width < (width - 1) && hits.contains(lastHit + 1);
      
      long cursor = lastHit;
      if (isHorizontal) {
        do {
          ship |= 1L << cursor--;
        } while (hits.contains(cursor) && cursor % width != (width - 1));
        cursor = lastHit;
        do {
          ship |= 1L << cursor++;
        } while (hits.contains(cursor) && cursor % width != 0);
      } else {
        do {
          ship |= 1L << cursor;
          cursor -= width;
        } while (hits.contains(cursor));
        cursor = lastHit;
        do {
          ship |= 1L << cursor;
          cursor += width;
        } while (hits.contains(cursor));
      }

      long misses = 0;

      List<Long[]> shipPositions = positions.getLong(length);
      for (Long[] position : shipPositions) {
        if (position[0] == ship) misses = position[1] & ~ship;
      }

      LinkedNode<Long> current = stack;
      LinkedNode<Long> previous = null;

      while (current != null) {
        long value = current.getValue();
        if (((misses & ~value) | (ship & value)) != (ship | misses)) {
          for (long i = 0; i < width * height; i++) {
            heatmap[(int)i] -= value >>> i & 1L;
          }
          if (previous == null) {
            stack = current;
          } else {
            previous.remove();
          }
        } else {
          previous = current;
        }
        current = current.getNext();
      }
    }
  }


  private static class BigintHeatmap extends Heatmap {
    @Override
    public void generate() {
      //
    }

    @Override
    public LinkedNode<Long> getStack() {
      throw new UnsupportedOperationException("Unimplemented method 'getStack'");
    }

    @Override
    public void hit(long position) {}

    @Override
    public void miss(long position) {}

    @Override
    public void sink(long length) {}

    @Override
    public long getOptimalShot() {
      throw new UnsupportedOperationException("Unimplemented method 'getOptimalShot'");
    }
  }

  protected long[] heatmap;

  public long[] getHeatmap() {
    return heatmap;
  }

  public abstract void generate();

  public abstract LinkedNode<Long> getStack();

  public abstract void hit(long position);
  public abstract void miss(long position);
  public abstract void sink(long length);
  public abstract long getOptimalShot();

  public static Heatmap createHeatmap(int width, int height, int[] ships, int placementRules) {
    return width * height <= 64 ? new LongHeatmap(width, height, ships, placementRules) : new BigintHeatmap();
  }
}