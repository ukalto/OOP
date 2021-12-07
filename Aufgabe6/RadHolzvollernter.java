public class RadHolzvollernter extends Holzvollernter {
    // (I) >= 0
    private double meterDone;

    // (Vor): arbeitskopf != null
    // (Nach): Setzt this.id = id, this.arbeitskopf = arbeitskopf
    public RadHolzvollernter(int id, Arbeitskopf workinghead) {
        super(id, workinghead);
        this.meterDone = 0;
    }

    // (Nach): Retuniert meterDone dieser Maschine was >= 0 sein muss
    public double getMeterDone() {
        return meterDone;
    }

    // (Vor): distance >= 0
    // (Nach): Die distance wurde zu meterDone hinzugefügt The distance is added to the meterDone of this and tells how much meter the machine moved since the start
    public void increaseMeter(double distance) {
        this.meterDone += distance;
    }
}
