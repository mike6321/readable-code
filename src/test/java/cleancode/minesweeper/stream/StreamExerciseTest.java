package cleancode.minesweeper.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class StreamExerciseTest {

    @Test
    void example_solution_even_square_sum() {
        List<Integer> numbers = Arrays.asList(3, 7, 2, 9, 4, 8, 1);

        int evenSquareSum = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n)
                .sum();

        assertThat(evenSquareSum).isEqualTo(84);
    }

//    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_distinct_sorted_words() {
        List<List<String>> words = Arrays.asList(
                Arrays.asList("Mine", "sweep", "mine"),
                Arrays.asList("clear", "code"),
                Arrays.asList("stream", "code")
        );

        // TODO: Use streams to flatten, lowercase, distinct, and sort.
        List<String> result = words.stream()
                .flatMap(List::stream)
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .toList();

        assertThat(result).containsExactly("clear", "code", "mine", "stream", "sweep");
    }

//    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_group_by_first_char() {
        List<String> names = Arrays.asList("Anna", "Bob", "Alex", "Brian", "Amy", "Bella");

        // TODO: Use groupingBy to group names by first character.
        Map<Character, List<String>> grouped = names.stream()
                        .collect(Collectors.groupingBy(name -> name.charAt(0)));

        assertThat(grouped.get('A')).containsExactly("Anna", "Alex", "Amy");
        assertThat(grouped.get('B')).containsExactly("Bob", "Brian", "Bella");
    }

//    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_partition_by_length() {
        List<String> names = Arrays.asList("Anna", "Bob", "Alex", "Brian", "Amy", "Bella");

        // TODO: Partition into length >= 4 and length < 4.
        Map<Boolean, List<String>> partitioned = names.stream()
                .collect(Collectors.partitioningBy(name -> name.length() >= 4));

        assertThat(partitioned.get(true)).containsExactly("Anna", "Alex", "Brian", "Bella");
        assertThat(partitioned.get(false)).containsExactly("Bob", "Amy");
    }

//    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_int_stream_sum_of_odds() {
        // TODO: Sum odd numbers from 1 to 10 inclusive.
        int sum = IntStream.rangeClosed(0, 10)
                .filter(num -> num % 2 == 1)
                .sum();

        assertThat(sum).isEqualTo(25);
    }

//    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_set_of_even_strings() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // TODO: Convert even numbers to String and collect into a Set.
        Set<String> result = numbers.stream()
                .filter(num -> num % 2 == 0)
                .map(number -> String.valueOf(number))
                .collect(Collectors.toSet());

        assertThat(result).containsExactlyInAnyOrder("2", "4", "6");
    }
}
