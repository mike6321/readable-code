package cleancode.studycafe.tobe_mine;

import cleancode.studycafe.tobe_mine.io.OutputHandler;
import cleancode.studycafe.tobe_mine.exception.AppException;
import cleancode.studycafe.tobe_mine.io.InputHandler;
import cleancode.studycafe.tobe_mine.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe_mine.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe_mine.model.StudyCafePass;
import cleancode.studycafe.tobe_mine.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

            StudyCafeLockerPass lockerPass = selectLockerPass( selectedPass);
            outputHandler.showPassOrderSummary(selectedPass, lockerPass);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = findPassCandidateBy(studyCafePassType);
        
        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private List<StudyCafePass> findPassCandidateBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }

    private StudyCafeLockerPass selectLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.getPassType() != StudyCafePassType.FIXED) {
            return null;
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return lockerPassCandidate;
            }
        }

        return null;
    }

    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
                .filter(lockerPass ->
                        lockerPass.getPassType() == selectedPass.getPassType()
                                && lockerPass.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }

}
