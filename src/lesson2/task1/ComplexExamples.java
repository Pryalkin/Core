package lesson2.task1;

import java.util.*;
import java.util.stream.Collectors;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };

        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args){
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            if (person == null) {
                System.out.println("null");
                continue;
            }
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        System.out.println("Processed data:");
        System.out.println();

        long time = System.nanoTime();
        try {
            Arrays.stream(Optional.ofNullable(RAW_DATA)
                    .orElseThrow(MyException::new))
                    .filter(person -> Objects.nonNull(person))
                    .distinct()
                    .sorted((p1, p2) -> {
                        if (p1.getId() == p2.getId()){
                            return p1.getName().compareTo(p2.getName());
                        } else return p1.getId() - p2.getId();
                    })
                    .collect(Collectors.groupingBy(Person::getName, Collectors.counting()))
                    .entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
                    .forEach(data -> {
                        System.out.println("Key: "+data.getKey());
                        System.out.println("Value:"+data.getValue());
                    });
        } catch (MyException e) {
            e.printStackTrace();
        }
        System.out.println("Время выполнения со stream: " + (System.nanoTime() - time) + " наносекунд");

        System.out.println("--------------------------------------");

        System.out.println("Processed data:");
        System.out.println();

        long time2 = System.nanoTime();
        try {
            Person[] dataAfterCheckingForNull = checkForNull(RAW_DATA);
            Person[] dataAfterFilter = filterFromNull(dataAfterCheckingForNull);
            Person[] dataAfterSortingByIdAndName = sortByIdAndName(dataAfterFilter);
            Person[] dataAfterRemovingDuplicates = distinct(dataAfterSortingByIdAndName);
            Person[] dataAfterSortingByName = sortByName(dataAfterRemovingDuplicates);
            String[][] dataAfterGrouping = groupingBy(dataAfterSortingByName);
            printProcessedData(dataAfterGrouping);
        } catch (MyException e) {
            e.printStackTrace();
        }
        System.out.println("Время выполнения на низком уровне: " + (System.nanoTime() - time2) + " наносекунд");

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться
                Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

    }

    private static Person[] checkForNull(Person[] array) throws MyException {
        if (array == null) throw new MyException();
        return array;
    }

    private static Person[] filterFromNull(Person[] array) {
        int valueForArraySize = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null) continue;
            valueForArraySize++;
        }
        Person[] dataAfterFilter = new Person[valueForArraySize];
        int indexInArray = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                dataAfterFilter[indexInArray] = array[i];
                indexInArray++;
            }
        }
        return dataAfterFilter;
    }

    private static Person[] sortByIdAndName(Person[] array) {
        for (int i = 1; i < array.length; i++) {
            if (i < (array.length-1)){
                if (array[i].getId() == array[i+1].getId()){
                    if (array[i].getName().compareTo(array[i+1].getName()) > 0){
                        i = helperMethodForSorting(array, i);
                    }
                } else if (array[i].getId() > array[i+1].getId()){
                    i = helperMethodForSorting(array, i);
                }
            }
        }
        return array;
    }

    private static Person[] distinct(Person[] array) {
        int valueForArraySize = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i].getId() == array[i-1].getId()) continue;
            valueForArraySize++;
        }
        Person[] distinctData = new Person[valueForArraySize];
        distinctData[0] = array[0];
        int indexInArray = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i].getId() == array[i-1].getId()) continue;
            else {
                distinctData[indexInArray] = array[i];
                indexInArray++;
            }
        }
        return distinctData;
    }

    private static Person[] sortByName(Person[] array) {
        for (int i = 0; i < array.length; i++) {
            if ((i < (array.length-1) && array[i].getName().compareTo(array[i+1].getName()) > 0) ){
                Person person = array[i];
                array[i] = array[i+1];
                array[i+1] = person;
                for (int j = 0; j < i; j++) {
                    if (array[i].getName().compareTo(array[j].getName()) < 0) {
                        i = j-1;
                        break;
                    }
                }
            }
        }
        return array;
    }

    private static String[][] groupingBy(Person[] array) {
        int valueForArraySize = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i].getName().equals(array[i-1].getName())) continue;
            valueForArraySize++;
        }
        String[][] dataAfterGrouping = new String[valueForArraySize][2];
        int numberOfObjects = 0, indexInArray = 0;
        for (int i = 0; i < dataAfterGrouping.length; i++) {
            dataAfterGrouping[i][0] = array[indexInArray].getName();
            dataAfterGrouping[i][1] = Integer.toString(numberOfObjects);
            for (int j = indexInArray; j < array.length; j++) {
                if ( dataAfterGrouping[i][0].equals(array[j].getName())){
                    numberOfObjects++;
                    dataAfterGrouping[i][1] = Integer.toString(numberOfObjects);
                } else {
                    numberOfObjects = 0;
                    indexInArray = j;
                    break;
                }
            }

        }
        return dataAfterGrouping;
    }

    private static void printProcessedData(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (j == 0) System.out.println("Key: "+array[i][j]);
                if (j == 1) System.out.println("Value:"+array[i][j]);
            }
        }
    }

    private static int helperMethodForSorting(Person[] array, int i) {
        Person person = array[i];
        array[i] = array[i+1];
        array[i+1] = person;
        for (int j = 0; j < i; j++) {
            if (array[i].getId() == array[j].getId()){
                if (array[i].getName().compareTo(array[j].getName()) < 0) {
                    i = j-1;
                    break;
                }
            } else if (array[i].getId() < array[j].getId()) {
                i = j-1;
                break;
            }
        }
        return i;
    }
}

class MyException extends Exception{
    public MyException() {
        super("Массив пустой");
    }
}

