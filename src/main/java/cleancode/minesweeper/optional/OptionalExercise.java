package cleancode.minesweeper.optional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * • 핵심 차이: orElse는 항상 fallback을 즉시 계산하고, orElseGet은 값이 없을 때만 지연 계산합니다.
 *
 *   - orElse(fallback()): Optional이 값이 있어도 fallback()이 실행됨
 *   - orElseGet(() -> fallback()): Optional이 비어있을 때만 실행됨
 *
 *   실전 팁:
 *
 *   - fallback 계산이 비싸면 orElseGet
 *   - 계산이 가볍고 단순하면 orElse도 OK
 * */
public class OptionalExercise {

    public static void main(String[] args) {
        practiceOrElseVsOrElseGet();
    }

    private static void practiceOrElseVsOrElseGet() {
        AtomicInteger fallbackCallCount = new AtomicInteger();

        Optional<String> present = Optional.of("value");
        Optional<String> empty = Optional.empty();

        String presentOrElse = present.orElse(expensiveFallback(fallbackCallCount));
        String presentOrElseGet = present.orElseGet(() -> expensiveFallback(fallbackCallCount));

        String emptyOrElse = empty.orElse(expensiveFallback(fallbackCallCount));
        String emptyOrElseGet = empty.orElseGet(() -> expensiveFallback(fallbackCallCount));

        System.out.println("presentOrElse=" + presentOrElse);
        System.out.println("presentOrElseGet=" + presentOrElseGet);
        System.out.println("emptyOrElse=" + emptyOrElse);
        System.out.println("emptyOrElseGet=" + emptyOrElseGet);
        System.out.println("fallbackCallCount=" + fallbackCallCount.get());
    }

    private static String expensiveFallback(AtomicInteger counter) {
        counter.incrementAndGet();
        return "fallback";
    }
}
