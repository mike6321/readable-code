package cleancode.studycafe.tobe_mine.io;

import cleancode.studycafe.tobe_mine.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe_mine.model.StudyCafePass;
import cleancode.studycafe.tobe_mine.model.StudyCafePassType;

import java.util.List;

public class StudyCafeIOHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void showWelcomeMessage() {
        this.outputHandler.showWelcomeMessage();
    }

    public void showAnnouncement() {
        this.outputHandler.showAnnouncement();
    }

    public void showPassOrderSummary(StudyCafePass selectedPass) {
        this.outputHandler.showPassOrderSummary(selectedPass);
    }

    public void showPassOrderSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass) {
        this.outputHandler.showPassOrderSummary(selectedPass, lockerPass);
    }

    public void showSimpleMessage(String message) {
        this.outputHandler.showSimpleMessage(message);
    }


    public StudyCafePassType askPassTypeSelection() {
        this.outputHandler.askPassTypeSelection();
        return this.inputHandler.getPassTypeSelectingUserAction();
    }

    public StudyCafePass showPassListForSelection(List<StudyCafePass> passCandidates) {
        this.outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    public boolean askLockerPass(StudyCafeLockerPass lockerPassCandidate) {
        this.outputHandler.askLockerPass(lockerPassCandidate);
        return inputHandler.getLockerSelection();
    }
}
