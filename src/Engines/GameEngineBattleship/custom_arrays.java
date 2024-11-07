package src.Engines.GameEngineBattleship;
import java.util.Arrays;



// custom_arrays.
public class custom_arrays {
    public static int[] add_to_array(int[] array, int element){
        int[] new_array = new int[array.length+1];
        for (int i = 0; i < array.length; i++) {
            new_array[i] = array[i];
        }
        new_array[new_array.length -1] = element;
        return new_array;
    }

    public static int[] remove_array_from_array(int[] array1, int[] array2) {
        int result_length = array1.length - array2.length;
        int[] result = new int[result_length];
        Arrays.sort(array1); 
        Arrays.sort(array2); 
        int j = 0;
        for(int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[j]) {
                result[i - j] = array1[i];
            } else {
                j++;
            }
            
        }
        return result;
    }

    public static int sumSlice(int[] array, int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        return sum;
    }

}