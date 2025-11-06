package cleancode.studycafe.tobe_mine.model;

import java.util.List;

/**
 * 일급 컬렉션의 장점 - 컬렉션과 관련된 로직을 해당 클래스에 캡슐화할 수 있다.
 * */
public class StudyCafePasses {

    private final List<StudyCafePass> studyCafePasses;

    public StudyCafePasses(List<StudyCafePass> studyCafePasses) {
        this.studyCafePasses = studyCafePasses;
    }

    public static StudyCafePasses of(List<StudyCafePass> studyCafePasses) {
        return new StudyCafePasses(studyCafePasses);
    }

    public List<StudyCafePass> findPassBy(StudyCafePassType studyCafePassType) {
        return  studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }
}
