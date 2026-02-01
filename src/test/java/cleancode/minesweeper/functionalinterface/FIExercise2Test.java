package cleancode.minesweeper.functionalinterface;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class FIExercise2Test {

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_optional_pipeline() {
        Function<String, String> trimLower = null;

        String result = Optional.of("  Mine  ")
                // TODO: map with trimLower
                // TODO: wrap with brackets
                .orElse("[empty]");

        assertThat(result).isEqualTo("[mine]");
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_dynamic_predicate() {
        List<String> inputs = List.of("mine", "sweeper", "code", "clean");

        Predicate<String> startsWithC = null;
        Predicate<String> lengthAtLeast5 = null;
        Predicate<String> combined = null;

        List<String> filtered = inputs.stream()
                .filter(combined)
                .toList();

        assertThat(filtered).containsExactly("sweeper", "code", "clean");
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_command_table() {
        // TODO: Fill command map with upper/lower/bracket operations.
        Map<String, Function<String, String>> commands = null;

        String upper = commands.get("upper").apply("mine");
        String lower = commands.get("lower").apply("MINE");
        String bracket = commands.get("bracket").apply("mine");

        assertThat(upper).isEqualTo("MINE");
        assertThat(lower).isEqualTo("mine");
        assertThat(bracket).isEqualTo("[mine]");
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_lazy_supplier() {
        List<String> logs = new ArrayList<>();

        Supplier<String> expensive = null;

        Optional<String> empty = Optional.empty();
        String value = empty.orElseGet(expensive);

        assertThat(value).isEqualTo("lazy");
        assertThat(logs).containsExactly("built");
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_comparator_chain() {
        List<String> inputs = new ArrayList<>(List.of("mine", "sweeper", "code", "clean"));

        // TODO: Sort by length asc, then lexicographically.
        inputs.sort(null);

        assertThat(inputs).containsExactly("code", "mine", "clean", "sweeper");
    }

    @Disabled("Practice: remove @Disabled and implement TODOs")
    @Test
    void practice_custom_collect() {
        List<String> inputs = List.of("mine", "sweeper", "code", "clean");

        // TODO: Collect into map length -> word, keep first on conflict.
        Map<Integer, String> result = null;

        assertThat(result.get(4)).isIn("mine", "code");
        assertThat(result.get(5)).isEqualTo("clean");
        assertThat(result.get(7)).isEqualTo("sweeper");
    }
}
