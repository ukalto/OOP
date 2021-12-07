public abstract class Holzvollernter {
    // (I): >= 0
    private final int id;

    // (I): >= 0
    private double zeitInBetrieb;

    // (I) != null
    private Arbeitskopf arbeitskopf;

    // (I): Beinhaltet nur Objekte die Instanzen von Holzvollernter sind
    private static final LinkedList allHolzvollernter = new LinkedList();

    // (Vor): arbeitskopf != null
    // (Nach): Setzt this.arbeitskopf auf den arbeitskopf und this.id auf die id und this.zeitInBetrieb auf 0 und fügt
    // den Hollvorernter zu der statischen Liste hinzu
    public Holzvollernter(int id, Arbeitskopf arbeitskopf) {
        // Hier soll eine Exception geworfen werden, da die id für zuküntige Operationen eindeutig sein muss
        for (Node i = allHolzvollernter.getHead(); i != null; i = i.getNext()) {
            if (((Holzvollernter) i.getValue()).getId() == id) {
                throw new IllegalStateException("Holzvollernter brauchen eine eindeutige ID");
            }
        }
        this.id = id;
        this.zeitInBetrieb = 0;
        this.arbeitskopf = arbeitskopf;
        allHolzvollernter.add(this);
    }

    // (Nach): Retuniert den arbeitskopf von diesem Objekt welcher != null ist
    public Arbeitskopf getArbeitskopf() {
        return arbeitskopf;
    }

    // (Vor): arbeitskopf != null
    // (Nach): Der arbeitskopf von diesem Objekt wird auf den übergebenen arbeitskopf gesetzt
    public void changeArbeitskopf(Arbeitskopf arbeitskopf) {
        this.arbeitskopf = arbeitskopf;
    }

    // (Vor): zeit >= 0
    // (Nach): Eine gewisse zeit wurde zur zeitInBetrieb hinzugefügt und sagt aus wie lange die Maschine schon in Betrieb ist
    public void increaseZeitInBetrieb(double zeit) {
        this.zeitInBetrieb += zeit;
    }

    // (Nach): Retuniert die eindeutige id welche >= 0
    public int getId() {
        return id;
    }

    // (Nach): Die Liste aller Holzvollernter wird zurückgegeben
    public static LinkedList getAlleErnter() {
        return allHolzvollernter;
    }

    // (Nach): Retuniert den zeiInBetrieb welcher >= 0 ist und representiert wie lange die Maschine schon arbeitet
    public double getZeitInBetrieb() {
        return zeitInBetrieb;
    }
}
