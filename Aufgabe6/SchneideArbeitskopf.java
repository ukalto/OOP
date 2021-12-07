public class SchneideArbeitskopf implements Arbeitskopf {
    // (I): >= 0
    private final double maxLength;

    // (Vor): maxLength >= 0
    // (Nach): Setzt this.maxLength = maxLength
    public SchneideArbeitskopf(double maxLength) {
        this.maxLength = maxLength;
    }

    // (Nach): Retuniert die maxLength von diesem Objekt welche >=0 ist
    public double getMaxLength() {
        return maxLength;
    }

    // (Nach): Retuniert immer wahr in dieser Klasse, weil Schneide ein Schneider ist
    @Override
    public boolean istSchneide() {
        return true;
    }

    // (Nach): Retuniert immer falsch in dieser Klasse, weil Schneide kein Schneider ist
    @Override
    public boolean istHackschnitzel() {
        return false;
    }
}
