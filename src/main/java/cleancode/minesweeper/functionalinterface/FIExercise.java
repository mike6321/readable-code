package cleancode.minesweeper.functionalinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FIExercise {

    public static void main(String[] args) {
        practiceCustomFunctionalInterface();
        practiceBuiltInInterfaces();
        practiceComposeAndChain();
        practiceCallbacks();
        practiceUnaryBinaryOperator();
        practiceBiPredicateBiConsumer();
        practiceComposeVsAndThen();
    }

    private static void practiceCustomFunctionalInterface() {
        StringTransformer shout = s -> s.toUpperCase(Locale.ROOT) + "!";
        StringTransformer trim = String::trim;

        String raw = "  minesweeper  ";
        // trim -> shout 순서로 합성: 앞 공백 제거 후 대문자+느낌표 처리
        String transformed = trim.andThen(shout).transform(raw);

        System.out.println("[custom] transformed=" + transformed);
        // 기본 메서드(withPrefix)로 변환 결과에 접두사를 덧붙임
        System.out.println("[custom] defaultPrefix=" + shout.withPrefix("Hi").transform("team"));
    }

    private static void practiceBuiltInInterfaces() {
        Supplier<String> wordSupplier = () -> "mine";
        Consumer<String> printer = value -> System.out.println("[consumer] " + value);
        Function<String, Integer> lengthFn = String::length;
        Predicate<String> hasE = value -> value.contains("e");
        BiFunction<Integer, Integer, Integer> add = Integer::sum;

        String word = wordSupplier.get();
        printer.accept(word);
        System.out.println("[function] length=" + lengthFn.apply(word));
        System.out.println("[predicate] hasE=" + hasE.test(word));
        System.out.println("[bifunction] add=" + add.apply(3, 7));
    }

    private static void practiceComposeAndChain() {
        Function<String, String> trim = String::trim;
        Function<String, String> lower = s -> s.toLowerCase(Locale.ROOT);
        Function<String, String> wrap = s -> "[" + s + "]";

        String composed = trim.andThen(lower).andThen(wrap).apply("  Clean CODE  ");
        System.out.println("[compose] " + composed);

        Predicate<String> nonNull = Objects::nonNull;
        Predicate<String> longerThan3 = s -> s.length() > 3;
        Predicate<String> notBlank = s -> !s.isBlank();

        Predicate<String> valid = nonNull.and(longerThan3).and(notBlank);
        System.out.println("[predicate] ok=" + valid.test(" mine "));
    }

    private static void practiceCallbacks() {
        List<String> results = new ArrayList<>();
        saveIfValid("field", value -> results.add("saved:" + value), value -> value.length() >= 4);
        saveIfValid("x", value -> results.add("saved:" + value), value -> value.length() >= 4);

        System.out.println("[callback] results=" + results);
    }

    private static void practiceUnaryBinaryOperator() {
        UnaryOperator<String> upper = s -> s.toUpperCase(Locale.ROOT);
        BinaryOperator<String> longer = (a, b) -> a.length() >= b.length() ? a : b;

        System.out.println("[unary] " + upper.apply("mine"));
        System.out.println("[binary] " + longer.apply("code", "clean"));
    }

    private static void practiceBiPredicateBiConsumer() {
        BiPredicate<String, Integer> lengthAtLeast = (value, min) -> value.length() >= min;
        BiConsumer<String, Boolean> appendWithFlag = (value, flag) ->
                System.out.println("[biconsumer] " + value + ":" + flag);

        System.out.println("[bipredicate] " + lengthAtLeast.test("mine", 4));
        appendWithFlag.accept("mine", true);
    }

    private static void practiceComposeVsAndThen() {
        Function<String, String> trim = String::trim;
        Function<String, String> wrap = s -> "[" + s + "]";

        String composed = trim.compose(wrap).apply(" code ");
        String chained = trim.andThen(wrap).apply(" code ");

        System.out.println("[compose-vs] composed=" + composed);
        System.out.println("[compose-vs] andThen=" + chained);
    }

    private static void saveIfValid(String value, Consumer<String> onSave, Predicate<String> validator) {
        if (validator.test(value)) {
            onSave.accept(value);
        }
    }

    @FunctionalInterface
    private interface StringTransformer {
        String transform(String value);

        default StringTransformer andThen(StringTransformer after) {
            // 현재 변환을 먼저 실행하고, 그 결과를 다음 변환에 전달
            return value -> after.transform(transform(value));
        }

        default StringTransformer withPrefix(String prefix) {
            // 변환 결과 앞에 prefix를 붙이는 새 변환을 반환
            return value -> prefix + " " + transform(value);
        }
    }
}
