public class SchreiterHolzvollernter extends Holzvollernter {
    // (I): >= 0
    private int stepsDone;

    // (Vor): arbeitskopf != null
    // (Nach): Setzt this.id = id, this.arbeitskopf = arbeitskopf
    public SchreiterHolzvollernter(int id, Arbeitskopf arbeitskopf) {
        super(id, arbeitskopf);
        this.stepsDone = 0;
    }

    // (Nach): Retuniert die stepsDone von diesem Objekt welche >=0 ist
    public int getStepsDone() {
        return stepsDone;
    }

    // (Vor): distance >= 0
    // (Nach): Die steps werden zu stepsDone hinzugefügt
    public void increaseSteps(int steps) {
        this.stepsDone += steps;
    }
}
