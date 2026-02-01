package cleancode.minesweeper.functionalinterface;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class FIExerciseTest {

    @Test
    void example_solution_bifunction_add() {
        BiFunction<Integer, Integer, Integer> add = Integer::sum;

        int sum = add.apply(3, 7);

        assertThat(sum).isEqualTo(10);
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_supplier_consumer() {
        // TODO: Supply a word and consume it into a list.
        Supplier<String> supplier = null;
        Consumer<String> consumer = null;

        List<String> collected = new ArrayList<>();
        consumer = collected::add;
        // TODO: use supplier and consumer to add the supplied value

        assertThat(collected).containsExactly("mine");
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_function_chain() {
        // TODO: trim -> lowercase -> wrap with brackets
        Function<String, String> trim = null;
        Function<String, String> lower = null;
        Function<String, String> wrap = null;

        String result = null;

        assertThat(result).isEqualTo("[clean code]");
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_predicate_combine() {
        // TODO: non-null AND length >= 4 AND not blank
        Predicate<String> nonNull = null;
        Predicate<String> lengthAtLeast4 = null;
        Predicate<String> notBlank = null;

        Predicate<String> valid = null;

        assertThat(valid.test(" mine ")).isTrue();
        assertThat(valid.test("a")).isFalse();
        assertThat(valid.test("   ")).isFalse();
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_custom_functional_interface() {
        // TODO: Implement StringTransformer to trim then shout (uppercase + "!")
        StringTransformer trim = null;
        StringTransformer shout = null;

        StringTransformer composed = null;
        String result = null;

        assertThat(result).isEqualTo("MINESWEEPER!");
    }

    @FunctionalInterface
    private interface StringTransformer {
        String transform(String value);

        default StringTransformer andThen(StringTransformer after) {
            return value -> after.transform(transform(value));
        }
    }
}
