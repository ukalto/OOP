public class HackschnitzelArbeitskopf implements Arbeitskopf {
    // (I) >= 0
    private final int maxThickness;

    // (Vor): maxThickness >= 0
    // (Nach): Setzt this.maxThickness = maxThickness
    public HackschnitzelArbeitskopf(int maxThickness) {
        this.maxThickness = maxThickness;
    }

    // (Nach): Retuniert die maxThickness von diesem Objekt welche >=0 ist
    public int getMaxThickness() {
        return maxThickness;
    }

    // (Nach): Retuniert immer falsch in dieser Klasse, weil Hackschnitzel kein Schneider ist
    @Override
    public boolean istSchneide() {
        return false;
    }

    // (Nach): Retuniert immer wahr in dieser Klasse, weil Hackschnitzel ein Schnetzler ist
    @Override
    public boolean istHackschnitzel() {
        return true;
    }
}
