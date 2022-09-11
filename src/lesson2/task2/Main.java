package lesson2.task2;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[] arrayOfNumbers = createAnArrayOfNumbers();
//        int[] arrayOfNumbers = {3, 4, 2, 7};

        System.out.print("Введите значение для суммы чисел: ");
        Scanner scForSumOfTwoNumbers = new Scanner(System.in);
        int theSumOfTwoNumbers = scForSumOfTwoNumbers.nextInt();

        String value = findTwoValues(arrayOfNumbers, theSumOfTwoNumbers).orElse("Сумма не найдена!");
        System.out.println(value);

    }

    private static int[] createAnArrayOfNumbers() {
        boolean flag = true;
        int[] arrayOfNumbers = null;
        while (flag) {
            System.out.print("Введите размер массива: ");
            Scanner scForArraySize = new Scanner(System.in);
            String arraySize = scForArraySize.next();
            if (arraySize.matches("[0-9]+")) {
                arrayOfNumbers = new int[Integer.valueOf(arraySize)];
                flag = false;
            } else System.out.println("Не верный формат!");
        }
        System.out.println("Заполните массив: ");
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            System.out.print(i + ": ");
            Scanner scForArrayValues = new Scanner(System.in);
            String valueForArray = scForArrayValues.next();
            if (valueForArray.matches("[0-9]+")){
                arrayOfNumbers[i] = Integer.valueOf(valueForArray);
            } else {
                System.out.println("Не верный формат!");
                i--;
            };
        }
        System.out.println("Массив успешно создан!");
        System.out.println(Arrays.toString(arrayOfNumbers));
        return arrayOfNumbers;
    }

    private static Optional<String> findTwoValues(int[] array, int theSumOfTwoNumbers) {
        int[] arrayOfTwoNumbers = new int[2];
        for (int i = 0; i < array.length; i++) {
            for (int j = i+1; j < array.length; j++) {
                if (array[i]+array[j] == theSumOfTwoNumbers) {
                    arrayOfTwoNumbers[0] = array[i];
                    arrayOfTwoNumbers[1] = array[j];
                    return Optional.ofNullable(Arrays.toString(arrayOfTwoNumbers));
                }
            }
        }
        return Optional.empty();
    }

}

