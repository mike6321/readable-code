package cleancode.minesweeper.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamExercise {

    public static void main(String[] args) {
        basicFilterMapReduce();
        flattenAndDistinct();
        groupAndPartition();
        summarizeAndJoin();
    }

    private static void basicFilterMapReduce() {
        List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 8, 1);
        int evenSquareSum = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n) // 4 + 16 + 64
                .sum();

        List<Integer> sortedTop3 = numbers.stream()
                .sorted()
                .skip(Math.max(0, numbers.size() - 3))
                .toList();

        System.out.println("[basic] evenSquareSum=" + evenSquareSum);
        System.out.println("[basic] sortedTop3=" + sortedTop3);
    }

    private static void flattenAndDistinct() {
        List<List<String>> words = Arrays.asList(
                Arrays.asList("mine", "sweep", "mine"),
                Arrays.asList("clear", "code"),
                Arrays.asList("stream", "code")
        );

        Set<String> distinct = words.stream()
                .flatMap(List::stream)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<String> sortedDistinct = distinct.stream()
                .sorted()
                .toList();

        System.out.println("[flatMap] sortedDistinct=" + sortedDistinct);
    }

    private static void groupAndPartition() {
        List<String> names = Arrays.asList("Anna", "Bob", "Alex", "Brian", "Amy", "Bella");

        Map<Character, List<String>> groupedByFirstChar = names.stream()
                .collect(Collectors.groupingBy(name -> name.charAt(0)));

        Map<Boolean, List<String>> partitionedByLength = names.stream()
                .collect(Collectors.partitioningBy(name -> name.length() >= 4));

        System.out.println("[group] groupedByFirstChar=" + groupedByFirstChar);
        System.out.println("[partition] length>=4=" + partitionedByLength);
    }

    private static void summarizeAndJoin() {
        IntSummaryStatistics stats = IntStream.rangeClosed(1, 10)
                .filter(n -> n % 2 == 1)
                .summaryStatistics();

        String joined = IntStream.rangeClosed(1, 5)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));

        System.out.println("[summary] stats=" + stats);
        System.out.println("[join] joined=" + joined);
    }
}
