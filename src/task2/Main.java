package task2;

public class Main {
    public static void main(String[] args) {
        int[] array = {5, 6, 3, 2, 5, 1, 4, 9};
        System.out.print("Print unsorted array: ");
        printArray(array);
        System.out.println();
        System.out.print("Print sorted array: ");
        printArray(sort(array));
    }

    private static void printArray(int[] arr) {
        assert (arr.length > 0);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static int[] sort(int[] arr) {
        assert (arr != null && arr.length > 0);
        for (int i = 0; i < arr.length; i++){
            if (i < (arr.length-1) && arr[i] > arr[i+1]) {
                int value = arr[i];
                arr[i] = arr[i+1];
                arr[i+1] = value;
                for (int j = 0; j < arr.length; j++) {
                    if (arr[i] < arr[j]) {
                        i = j-1;
                        break;
                    }
                }
            }
        }
        return arr;
    }
}
