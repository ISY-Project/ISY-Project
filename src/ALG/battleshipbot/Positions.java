package battleshipbot;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Positions {
  public static class PlacementRules {
    public static final int NONE = 0 << 0;
    public static final int ALONGSIDE = 1 << 0;
    public static final int DIAGONAL = 1 << 1;

    public static final int ALL = ALONGSIDE | DIAGONAL;

    private int value = 0;

    public PlacementRules(int ...rules) {
      for (int i : rules) {
        this.value |= i;
      }
    }

    public boolean has(int predicate) {
      return (this.value & predicate) != 0;
    }
  }

  private PlacementRules placementRules;
  private long width;
  private long height;

  private Map<Long, List<Long[]>> longCache = new HashMap<>();
  private Map<Long, List<BigInteger[]>> bigintCache = new HashMap<>();
  
  public Positions(long width, long height, PlacementRules placementRules) {
    this.width = width;
    this.height = height;
    this.placementRules = placementRules;
  }

  public List<Long[]> getLong(long length) {
    if (width * height > 64) throw new IndexOutOfBoundsException(String.format("A grid of %dx%d can not be contained within long integers.", width, height));
    if (Math.max(width, height) < length) throw new IndexOutOfBoundsException(String.format("Ship of length %d does not fit within a %dx%d grid.", length, width, height));

    if (longCache.containsKey(length)) return longCache.get(length);

    List<Long[]> positions = new ArrayList<>();
    
    if (length <= width) {
      long horizontalMask = (1 << width) - 1;
      long horizontal = horizontalMask >>> (width - length);
  
      for (long a = 0; a < height; a++) {
        for (long b = 0; b < width + 1 - length; b++) {
          long mask = horizontal;
          
          if (placementRules.has(PlacementRules.ALONGSIDE)) mask
            |= (((horizontal << 1L) | (horizontal >>> 1L)) & (horizontalMask << (a * width)))
            | (a == 0 ? 0L : (horizontal >>> width))
            | (a == height-1 ? 0L : (horizontal << width));
          
          // if (placementRules.has(PlacementRules.DIAGONAL)) mask |= ;

          positions.add(new Long[] { horizontal, mask });
          
          if (b < width - length) horizontal <<= 1L;
        }
        horizontal = horizontal << length;
      }
    }
    
    if (length <= height) {
      long verticalMask = Long.parseLong(("0".repeat((int)width-1)+"1").repeat((int)height), 2);
      long vertical = verticalMask >>> width * (height - length);
      
      for (long a = 0; a < width; a++) {
        for (long b = 0; b < height + 1 - length; b++) {
          long mask = vertical;

          if (placementRules.has(PlacementRules.ALONGSIDE)) mask
            |= (((vertical << width) | (vertical >>> width)) & (verticalMask << a))
            | (a == 0 ? 0L : (vertical >>> 1L))
            | (a == width-1 ? 0L : (vertical << 1L));
          
          // if (placementRules.has(PlacementRules.DIAGONAL)) mask |= ;
          
          positions.add(new Long[] { vertical, mask });
          
          if (b < height - length) vertical <<= width;
        }
        vertical = vertical >>> ((height - length) * width - 1L);
      }
    }

    longCache.put(length, positions);
    return positions;
  }

  public List<BigInteger[]> getBigint() {
    List<BigInteger[]> positions = new ArrayList<>();

    //

    return positions;
  }
}