package task1;
public class Main {

    public static void main(String[] args) {
        int[][] twoDimensionalArray;
        try {
            twoDimensionalArray = createAnArray(0, 10000, 5, 11);
        } catch (WrongSizeException e) {
            System.out.println(e.getMessage());
            return;
        }
        printArray(twoDimensionalArray);
        System.out.println("----------------------------------");
        int maxValue = findTheMaxValue(twoDimensionalArray);
        System.out.println("Maximum value: " + maxValue);
        int minValue = findTheMinValue(twoDimensionalArray);
        System.out.println("Minimum value: " + minValue);
        int meanValue = findTheMeanValue(twoDimensionalArray);
        System.out.println("Mean value: " + meanValue);
    }

    private static int findTheMeanValue(int[][] twoDimensionalArray) {
        assert (twoDimensionalArray.length > 0);
        int sumOfArrayNumbers = 0;
        int numberOfArrayNumbers = 0;
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            for (int j = 0; j < twoDimensionalArray[i].length; j++) {
                sumOfArrayNumbers += twoDimensionalArray[i][j];
                numberOfArrayNumbers++;
            }
        }
        return sumOfArrayNumbers/numberOfArrayNumbers;
    }

    private static int findTheMinValue(int[][] twoDimensionalArray) {
        assert (twoDimensionalArray.length > 0);
        int minValue = twoDimensionalArray[0][0];
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            for (int j = 0; j < twoDimensionalArray[i].length; j++) {
                if (twoDimensionalArray[i][j] < minValue) minValue = twoDimensionalArray[i][j];
            }
        }
        return minValue;
    }

    private static int findTheMaxValue(int[][] twoDimensionalArray) {
        assert (twoDimensionalArray.length > 0);
        int maxValue = twoDimensionalArray[0][0];
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            for (int j = 0; j < twoDimensionalArray[i].length; j++) {
                if (twoDimensionalArray[i][j] > maxValue) maxValue = twoDimensionalArray[i][j];
            }
        }
        return maxValue;
    }

    private static void printArray(int[][] twoDimensionalArray) {
        assert (twoDimensionalArray.length > 0);
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            for (int j = 0; j < twoDimensionalArray[i].length; j++) {
                System.out.print(twoDimensionalArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[][] createAnArray(int randomNumberForArrayFrom, int randomNumberForArrayBefore, int arraySizeValueFrom, int arraySizeValueBefore) throws WrongSizeException {
        if (randomNumberForArrayFrom >= randomNumberForArrayBefore ||
                arraySizeValueFrom < 1 || arraySizeValueBefore < 1 ||
                arraySizeValueFrom >= arraySizeValueBefore)
            throw new WrongSizeException("Size 'from' does not match size 'before'!");
        assert (randomNumberForArrayFrom < randomNumberForArrayBefore);
        assert (arraySizeValueFrom > 0 || arraySizeValueBefore > 0);
        assert (arraySizeValueFrom < arraySizeValueBefore);
        int numberForArraySize = random(arraySizeValueFrom, arraySizeValueBefore);
        int[][] twoDimensionalArray = new int[numberForArraySize][numberForArraySize];
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            for (int j = 0; j < twoDimensionalArray[i].length; j++) {
                twoDimensionalArray[i][j] = random(randomNumberForArrayFrom, randomNumberForArrayBefore);
            }
        }
        return twoDimensionalArray;
    }

    private static int random(long from, long before) throws WrongSizeException {
        int[] arrayOfRandomDigits = new int[10];
        for (int i = 0; i < arrayOfRandomDigits.length; i++) {
            int randomNumber =  (int) (System.nanoTime() / 100 % before);
            if (randomNumber < from){
                i--;
                continue;
            } else arrayOfRandomDigits[i] = randomNumber;
            try {
                if (i % 2 == 0) Thread.sleep(1);
                else Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int digitIndex = (int) (System.nanoTime() / 100 % 10);
        return arrayOfRandomDigits[digitIndex];
    }
}

class WrongSizeException extends Exception{
    public WrongSizeException(String message) {
        super(message);
    }
}
