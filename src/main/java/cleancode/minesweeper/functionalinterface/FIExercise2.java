package cleancode.minesweeper.functionalinterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FIExercise2 {

    public static void main(String[] args) {
        practiceOptionalPipeline();
        practiceDynamicPredicate();
        practiceCommandTable();
        practiceLazySupplier();
        practiceComparatorChain();
        practiceCustomCollect();
    }

    private static void practiceOptionalPipeline() {
        Function<String, String> trimLower = s -> s.trim().toLowerCase(Locale.ROOT);

        String result = Optional.of("  Mine  ")
                .map(trimLower)
                .map(s -> "[" + s + "]")
                .orElse("[empty]");

        System.out.println("[optional] " + result);
    }

    private static void practiceDynamicPredicate() {
        List<String> inputs = List.of("mine", "sweeper", "code", "clean");

        Predicate<String> startsWithC = s -> s.startsWith("c");
        Predicate<String> lengthAtLeast5 = s -> s.length() >= 5;
        Predicate<String> combined = startsWithC.or(lengthAtLeast5);

        List<String> filtered = inputs.stream()
                .filter(combined)
                .toList();

        System.out.println("[predicate] " + filtered);
    }

    private static void practiceCommandTable() {
        Map<String, Function<String, String>> commands = Map.of(
                "upper", s -> s.toUpperCase(Locale.ROOT),
                "lower", s -> s.toLowerCase(Locale.ROOT),
                "bracket", s -> "[" + s + "]"
        );

        String result = commands.get("upper").apply("mine");
        System.out.println("[command] " + result);
    }

    private static void practiceLazySupplier() {
        Supplier<String> expensive = () -> {
            System.out.println("[supplier] building value");
            return "lazy";
        };

        Optional<String> empty = Optional.empty();
        String value = empty.orElseGet(expensive);
        System.out.println("[supplier] value=" + value);
    }

    private static void practiceComparatorChain() {
        List<String> inputs = new ArrayList<>(List.of("mine", "sweeper", "code", "clean"));

        inputs.sort(Comparator.comparingInt(String::length)
                .thenComparing(Function.identity()));

        System.out.println("[comparator] " + inputs);
    }

    private static void practiceCustomCollect() {
        List<String> inputs = List.of("mine", "sweeper", "code", "clean");

        Map<Integer, String> result = inputs.stream()
                .collect(Collectors.toMap(String::length, Function.identity(), (a, b) -> a));

        System.out.println("[collect] " + result);
    }
}
