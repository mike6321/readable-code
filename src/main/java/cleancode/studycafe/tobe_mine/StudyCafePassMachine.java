package cleancode.studycafe.tobe_mine;

import cleancode.studycafe.tobe_mine.exception.AppException;
import cleancode.studycafe.tobe_mine.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe_mine.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe_mine.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe_mine.model.StudyCafePass;
import cleancode.studycafe.tobe_mine.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            studyCafeIOHandler.showWelcomeMessage();
            studyCafeIOHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass( selectedPass);
            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> studyCafeIOHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> studyCafeIOHandler.showPassOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            studyCafeIOHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            studyCafeIOHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        StudyCafePassType studyCafePassType = studyCafeIOHandler.askPassTypeSelection();
        List<StudyCafePass> passCandidates = findPassCandidateBy(studyCafePassType);
        
        return studyCafeIOHandler.showPassListForSelection(passCandidates);
    }

    private List<StudyCafePass> findPassCandidateBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            boolean isLockerSelected = studyCafeIOHandler.askLockerPass(lockerPassCandidate);

            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
                .filter(selectedPass::isSameDurationType)
                .findFirst()
                .orElse(null);
    }

}
