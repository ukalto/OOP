public class Forstbetrieb {

    // beinhaltet alle bisher erzeugten Forstbetriebe
    // (I): Beinhaltet nur Objekte die Instanz von Forstbetrieb sind
    private static final LinkedList alleForstbetriebe = new LinkedList();

    // (Inv): name.length() > 0
    private final String name;

    // (Inv): Nur Objekte des Typs Holzvollernter
    private final LinkedList holzvollernter;

    // (Vor): name.length() > 0
    // (Nach): setzt this.name auf name und initialisiert this.holzvollernter
    public Forstbetrieb(String name) {
        // Hier soll eine Exception geworfen werden, da der Name für zuküntige Operationen eindeutig sein muss
        for (Node i = alleForstbetriebe.getHead(); i != null; i = i.getNext()) {
            if (((Forstbetrieb) i.getValue()).getName().equals(name)) {
                throw new IllegalStateException("Forstbetriebe brauchen eine eindeutigen Namen");
            }
        }
        this.name = name;
        holzvollernter = new LinkedList();
        alleForstbetriebe.add(this);
    }

    // (Nach): Falls this.list keinen Holzvollernter mit gegebener id beinhaltet, wird er der Liste hinzugefügt
    //         --> Liste ändert sich.
    //         Ansonsten bleibt die Liste unverändert.
    public void addErnter(int id) {
        // Liste aller erzeugten Holzvollernter
        LinkedList alleErnter = Holzvollernter.getAlleErnter();

        Holzvollernter h = mapErnter(id, alleErnter);
        if (h != null && mapErnter(id, holzvollernter) == null) {
            this.holzvollernter.add(h);
        }
    }

    // (Vor): h != null
    // (Nach): Falls this.list den übergebenen Holzvollernter beinhaltet, wird er
    //         aus der Liste entfernt --> Liste ändert sich.
    //         Ansonsten bleibt die Liste unverändert.
    public void removeErnter(int id) {
        Holzvollernter h = mapErnter(id, holzvollernter);
        if (h != null)
            this.holzvollernter.remove(h);
    }

    // (Vor): h != null
    // (Nach): gibt den Namen dieses Forstbetriebs zurück
    public String getName() {
        return this.name;
    }

    // (Vor): zeit >= 0
    // (Nach): erhöht die Arbeitsstunden des Holzvollernters mit der ID "id" sofern in der Liste vorhanden
    //         um den Wert "zeit"
    public void increaseZeit(int id, double zeit) {
        Holzvollernter h = mapErnter(id, holzvollernter);
        if (h != null)
            h.increaseZeitInBetrieb(zeit);
    }

    // (Nach): gibt die bisher notierten Arbeitsstunden des Holzvollernters mit der ID "id" sofern in der Liste
    //         vorhanden
    //         Gibt -1 zurück sofern kein Holzvollernter mit der id gefunden wurde.
    //         Falls gefunden >= 0
    public double getZeit(int id) {
        Holzvollernter h = mapErnter(id, holzvollernter);
        if (h != null)
            return h.getZeitInBetrieb();

        return -1.0;
    }

    // (Nach): gibt die bisher zurückgelegte Distanz des Holzvollernters mit der ID "id" sofern in der Liste
    //         vorhanden. Das können die Meter eines Radernters oder die Schritte eines Schreiterernters sein.
    //         Gibt -1 zurück sofern kein Holzvollernter mit der id gefunden wurde.
    //         Falls gefunden >= 0
    public double getDistanz(int id) {
        Holzvollernter h = mapErnter(id, holzvollernter);
        if (h != null) {
            if (h instanceof RadHolzvollernter)
                return ((RadHolzvollernter) h).getMeterDone();
            else if (h instanceof SchreiterHolzvollernter)
                return ((SchreiterHolzvollernter) h).getStepsDone();
        }

        return -1;
    }

    // (Vor): distanz >= 0
    // (Nach): erhöht die Schritte/Meter des Holzvollernters mit der ID "id" sofern in der Liste vorhanden
    //         um den Wert "zeit"
    public void increaseDistanz(int id, double distanz) {
        Holzvollernter h = mapErnter(id, holzvollernter);
        if (h != null) {
            if (h instanceof RadHolzvollernter)
                ((RadHolzvollernter) h).increaseMeter(distanz);
            else if (h instanceof SchreiterHolzvollernter)
                ((SchreiterHolzvollernter) h).increaseSteps((int) distanz);
        }

    }

    // (Vor): kopf != null
    // (Nach): aktualisiert den Arbeitskopf des Holzvollernters mit der ID "id" sofern in der Liste vorhanden.
    public void changeEinsatzart(int id, Arbeitskopf kopf) {
        Holzvollernter h = mapErnter(id, holzvollernter);
        if (h != null) {
            h.changeArbeitskopf(kopf);
        }
    }

    // (Nach): gibt die maximale Stücklänge/Dicke des Holzvollernters mit der ID "id" sofern in der Liste vorhanden
    public double getMaxBaumInfo(int id) {
        Holzvollernter h = mapErnter(id, holzvollernter);
        if (h != null) {
            if (h.getArbeitskopf() instanceof SchneideArbeitskopf)
                return ((SchneideArbeitskopf) h.getArbeitskopf()).getMaxLength();
            else if (h.getArbeitskopf() instanceof HackschnitzelArbeitskopf)
                return ((HackschnitzelArbeitskopf) h.getArbeitskopf()).getMaxThickness();
        }

        return -1;
    }

    // (Nach) sucht nach einem Vollholzernter mittels id in this.holzvollernter und gibt das Objekt zurück
    //        falls nicht gefunden --> Rückgabewert = null
    private Holzvollernter mapErnter(int id, LinkedList l) {
        Holzvollernter gefunden = null;
        for (Node i = l.getHead(); i != null; i = i.getNext()) {
            if (((Holzvollernter) i.getValue()).getId() == id) {
                gefunden = (Holzvollernter) i.getValue();
            }
        }

        return gefunden;
    }

    // (Nach): Die Liste aller Forstbetriebe wird zurückgegeben
    public static LinkedList getAlleForstbetriebe() {
        return alleForstbetriebe;
    }

    // Durschnittliche Betriebsstunden:

    // (Nach): Gibt die gesamten durchschnittlichen Betriebsstunden aller Vollholzernter in this.holzvollernter zurück
    public double statdurchBstunden() {
        return statDurchBstundenHelper(0);
    }

    // (Nach): Gibt die durchschnittlichen Betriebsstunden aller Vollholzernter mit SchnetzlerArbeitskopf
    //         in this.holzvollernter zurück
    public double statdurchBstundenHack() {
        return statDurchBstundenHelper(1);
    }

    // (Nach): Gibt die durchschnittlichen Betriebsstunden aller Vollholzernter mit SchneideArbeitskopf
    //         in this.holzvollernter zurück
    public double statdurchBstundenSchneiden() {
        return statDurchBstundenHelper(2);
    }

    // (Nach): Gibt die durchschnittlichen Betriebsstunden aller Radernter in this.holzvollernter zurück
    public double statdurchBstundenRad() {
        return statDurchBstundenHelper(3);
    }

    // (Nach): Gibt die durchschnittlichen Betriebsstunden aller Schreiterernter in this.holzvollernter zurück
    public double statdurchBstundenSchreiter() {
        return statDurchBstundenHelper(4);
    }

    // Durchschnittliche zurueckgelegte Distanz Meter/Schritte

    // (Nach): Gibt die durchschnittlich zurückgelegte Wegstrecke aller Radernter in this.holzvollernter zurück
    public double statdurchMeter() {
        return durchDistanzHelper(0, true);
    }

    // (Nach): Gibt die durchschnittlich zurückgelegte Wegstrecke aller Radernter mit Schnetzlerkopf
    //         in this.holzvollernter zurück
    public double statdurchMeterHack() {
        return durchDistanzHelper(1, true);
    }

    // (Nach): Gibt die durchschnittlich zurückgelegte Wegstrecke aller Radernter mit Schneidekopf
    //         in this.holzvollernter zurück
    public double statdurchMeterSchneiden() {
        return durchDistanzHelper(2, true);
    }

    // (Nach): Gibt die durchschnittlich zurückgelegten Schritte aller Schreiter in this.holzvollernter zurück
    public int statdurchSchritte() {
        return (int) durchDistanzHelper(0, false);
    }

    // (Nach): Gibt die durchschnittlich zurückgelegten Schritte aller Schreiter mit Schnetzlerkopf
    //         in this.holzvollernter zurück
    public int statdurchSchritteHack() {
        return (int) durchDistanzHelper(1, false);
    }

    // (Nach): Gibt die durchschnittlich zurückgelegten Schritte aller Schreiter mit Schneidekopf
    //         in this.holzvollernter zurück
    public int statdurchSchritteSchneiden() {
        return (int) durchDistanzHelper(2, false);
    }

    // kleinste/groeßte maximale Laenge/Dicke

    // (Nach): Gibt die maximale Stücklänge aller Holzvollernter mit Schneidearbeitskopf in this.holzvollernter zurück
    public double statMaxLength() {
        return schneideMaxMinHelper(0, true);
    }

    // (Nach): Gibt die maximale Stücklänge aller Radernter mit Schneidearbeitskopf in this.holzvollernter zurück
    public double statMaxLengthRad() {
        return schneideMaxMinHelper(1, true);
    }

    // (Nach): Gibt die maximale Stücklänge aller Schreiterernter mit Schneidearbeitskopf in this.holzvollernter zurück
    public double statMaxLengthSchreiter() {
        return schneideMaxMinHelper(2, true);
    }

    // (Nach): Gibt die minimale Stücklänge aller Holzvollernter mit Schneidearbeitskopf in this.holzvollernter zurück
    public double statMinLength() {
        return schneideMaxMinHelper(0, false);
    }

    // (Nach): Gibt die minimale Stücklänge aller Radernter mit Schneidearbeitskopf in this.holzvollernter zurück
    public double statMinLengthRad() {
        return schneideMaxMinHelper(1, false);
    }

    // (Nach): Gibt die minimale Stücklänge aller Schreiternter mit Schneidearbeitskopf in this.holzvollernter zurück
    public double statMinLengthSchreiter() {
        return schneideMaxMinHelper(2, false);
    }

    // Durchschnittliche Baumdicke

    // (Nach): Gibt die durcschnittliche Baumdicke aller Holzvollernter mit Hackschnitzelkopf
    //         in this.holzvollernter zurück
    public double statdurchDicke() {
        return statDickeHelper(0);
    }

    // (Nach): Gibt die durcschnittliche Baumdicke aller Radernter mit Hackschnitzelkopf
    //         in this.holzvollernter zurück
    public double statdurchDickeRad() {
        return statDickeHelper(1);
    }

    // (Nach): Gibt die durcschnittliche Baumdicke aller Schreiternter mit Hackschnitzelkopf
    //         in this.holzvollernter zurück
    public double statdurchDickeSchreiter() {
        return statDickeHelper(2);
    }


    // (Vor): wahl >= 0 && wahl <= 4
    // (Nach): Für wahl == 0 -> die durchschnittlichen Arbeitsstunden aller Holzvollernter in this.holzvollernter
    //         Für wahl == 1 -> die durchschnittlichen Arbeitsstunden aller Holzvollernter mit HackschnitzelArbeitskopf
    //                          in this.holzvollernter
    //         Für wahl == 2 -> die durchschnittlichen Arbeitsstunden aller Holzvollernter mit SchneideArbeitskopf
    //                          in this.holzvollernter
    //         Für wahl == 3 -> die durchschnittlichen Arbeitsstunden aller Radernter in this.holzvollernter
    //         Für wahl == 4 -> die durchschnittlichen Arbeitsstunden aller Screiterernter in this.holzvollernter
    private double statDurchBstundenHelper(int wahl) {
        double std = 0;
        int anz = 0;

        for (Node i = holzvollernter.getHead(); i != null; i = i.getNext()) {
            if (wahl == 0) {
                std += ((Holzvollernter) i.getValue()).getZeitInBetrieb();
                anz++;
            } else if (wahl == 1) {
                if (((Holzvollernter) i.getValue()).getArbeitskopf().istHackschnitzel()) {
                    std += ((Holzvollernter) i.getValue()).getZeitInBetrieb();
                    anz++;
                }
            } else if (wahl == 2) {
                if (((Holzvollernter) i.getValue()).getArbeitskopf().istSchneide()) {
                    std += ((Holzvollernter) i.getValue()).getZeitInBetrieb();
                    anz++;
                }
            } else if (wahl == 3) {
                if ((i.getValue()) instanceof RadHolzvollernter) {
                    std += ((RadHolzvollernter) i.getValue()).getZeitInBetrieb();
                    anz++;
                }
            } else if (wahl == 4) {
                if ((i.getValue()) instanceof SchreiterHolzvollernter) {
                    std += ((SchreiterHolzvollernter) i.getValue()).getZeitInBetrieb();
                    anz++;
                }
            }
        }
        return (anz == 0) ? 0 : std / anz;
    }

    // (Vor): wahl >= 0 && wahl <= 2
    // (Nach): Für wahl == 0 && rad   -> die durchschnittlichen Meter aller Radernter in this.holzvollernter
    //         Für wahl == 0 && !rad  -> die durchschnittlichen Meter aller Schreiterernter in this.holzvollernter
    //         Für wahl == 1 && rad   -> die durchschnittlichen Meter aller Radernter mit Hackschnitzelkopf
    //                                   in this.holzvollernter
    //         Für wahl == 1 && !rad  -> die durchschnittlichen Meter aller Schreiterernter mit Hackschnitzelkopf
    //                                   in this.holzvollernter
    //         Für wahl == 2 && rad   -> die durchschnittlichen Meter aller Radernter mit Schneidekopf
    //                                   in this.holzvollernter
    //         Für wahl == 2 && !rad  -> die durchschnittlichen Meter aller Schreiterernter mit Schneidekopf
    //                                   in this.holzvollernter
    private double durchDistanzHelper(int wahl, boolean rad) {
        double distanz = 0;
        int anz = 0;

        for (Node i = holzvollernter.getHead(); i != null; i = i.getNext()) {
            if (wahl == 0) {
                if (rad) {
                    if (i.getValue() instanceof RadHolzvollernter) {
                        distanz += ((RadHolzvollernter) i.getValue()).getMeterDone();
                        anz++;
                    }
                } else {
                    if (i.getValue() instanceof SchreiterHolzvollernter) {
                        distanz += ((SchreiterHolzvollernter) i.getValue()).getStepsDone();
                        anz++;
                    }
                }
            } else if (wahl == 1) {
                if (rad) {
                    if (i.getValue() instanceof RadHolzvollernter) {
                        if (((RadHolzvollernter) i.getValue()).getArbeitskopf().istHackschnitzel()) {
                            distanz += ((RadHolzvollernter) i.getValue()).getMeterDone();
                            anz++;
                        }
                    }
                } else {
                    if (i.getValue() instanceof SchreiterHolzvollernter) {
                        if (((SchreiterHolzvollernter) i.getValue()).getArbeitskopf().istHackschnitzel()) {
                            distanz += ((SchreiterHolzvollernter) i.getValue()).getStepsDone();
                            anz++;
                        }
                    }
                }
            } else if (wahl == 2) {
                if (rad) {
                    if (i.getValue() instanceof RadHolzvollernter) {
                        if (((RadHolzvollernter) i.getValue()).getArbeitskopf().istSchneide()) {
                            distanz += ((RadHolzvollernter) i.getValue()).getMeterDone();
                            anz++;
                        }
                    }
                } else {
                    if (i.getValue() instanceof SchreiterHolzvollernter) {
                        if (((SchreiterHolzvollernter) i.getValue()).getArbeitskopf().istSchneide()) {
                            distanz += ((SchreiterHolzvollernter) i.getValue()).getStepsDone();
                            anz++;
                        }
                    }
                }
            }
        }

        return (anz == 0) ? 0 : distanz / anz;
    }


    // (Vor): wahl >= 0 && wahl <= 2
    // (Nach): Für wahl == 0 && max    -> die maximale Stücklänge aller Holzvollernter mit Schneidearbeitskopf
    //                                    in this.holzvollernter
    //         Für wahl == 0 && !max   -> die minimale Stücklänge aller Holzvollernter mit Schneidearbeitskopf
    //                                    in this.holzvollernter
    //         Für wahl == 1 && max    -> die maximale Stücklänge aller Radernter mit Schneidearbeitskopf
    //                                    in this.holzvollernter
    //         Für wahl == 1 && !max   -> die minimale Stücklänge aller Radernter mit Schneidearbeitskopf
    //                                    in this.holzvollernter
    //         Für wahl == 2 && max    -> die maximale Stücklänge aller Schreiterernter mit Schneidearbeitskopf
    //                                    in this.holzvollernter
    //         Für wahl == 2 && !max   -> die minimale Stücklänge aller Schreiterernter mit Schneidearbeitskopf
    //                                    in this.holzvollernter
    private double schneideMaxMinHelper(int wahl, boolean maximum) {
        double min = Double.MAX_VALUE;
        double max = 0;

        for (Node i = holzvollernter.getHead(); i != null; i = i.getNext()) {
            if (((Holzvollernter) i.getValue()).getArbeitskopf().istSchneide()) {
                if (wahl == 0) {
                    if (((SchneideArbeitskopf) ((Holzvollernter) i.getValue()).getArbeitskopf()).getMaxLength() > max) {
                        max = ((SchneideArbeitskopf) ((Holzvollernter) i.getValue()).getArbeitskopf()).getMaxLength();
                    }
                    if (((SchneideArbeitskopf) ((Holzvollernter) i.getValue()).getArbeitskopf()).getMaxLength() < min) {
                        min = ((SchneideArbeitskopf) ((Holzvollernter) i.getValue()).getArbeitskopf()).getMaxLength();
                    }
                } else if (wahl == 1) {
                    if (i.getValue() instanceof RadHolzvollernter) {
                        if (((SchneideArbeitskopf) ((RadHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength() > max) {
                            max = ((SchneideArbeitskopf) ((RadHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength();
                        }
                        if (((SchneideArbeitskopf) ((RadHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength() < min) {
                            min = ((SchneideArbeitskopf) ((RadHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength();
                        }
                    }
                } else if (wahl == 2) {
                    if (i.getValue() instanceof SchreiterHolzvollernter) {
                        if (((SchneideArbeitskopf) ((SchreiterHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength() > max) {
                            max = ((SchneideArbeitskopf) ((SchreiterHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength();
                        }
                        if (((SchneideArbeitskopf) ((SchreiterHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength() < min) {
                            min = ((SchneideArbeitskopf) ((SchreiterHolzvollernter) i.getValue()).getArbeitskopf()).getMaxLength();
                        }
                    }
                }
            }

        }
        if (min == Double.MAX_VALUE)
            min = 0;
        return (maximum) ? max : min;
    }

    // (Vor): wahl >= 0 && wahl <= 2
    // (Nach): Für wahl == 0    -> die durchschnittliche Baumdicke aller Holzvollernter mit Hackschnitzelkopf
    //                             in this.holzvollernter
    //         Für wahl == 1   -> die durchschnittliche Baumdicke aller Radernter mit Hackschnitzelkopf
    //                            in this.holzvollernter
    //         Für wahl == 2   -> die durchschnittliche Baumdicke aller Schreiterernter mit Hackschnitzelkopf
    //                            in this.holzvollernter
    private double statDickeHelper(int wahl) {
        double dicke = 0;
        int anz = 0;

        for (Node i = holzvollernter.getHead(); i != null; i = i.getNext()) {
            if (((Holzvollernter) i.getValue()).getArbeitskopf().istHackschnitzel()) {
                if (wahl == 0) {
                    dicke += ((HackschnitzelArbeitskopf) ((Holzvollernter) i.getValue()).getArbeitskopf()).getMaxThickness();
                    anz++;
                } else if (wahl == 1) {
                    if (i.getValue() instanceof RadHolzvollernter) {
                        dicke += ((HackschnitzelArbeitskopf) ((RadHolzvollernter) i.getValue()).getArbeitskopf()).getMaxThickness();
                        anz++;
                    }
                } else if (wahl == 2) {
                    if (i.getValue() instanceof SchreiterHolzvollernter) {
                        dicke += ((HackschnitzelArbeitskopf) ((SchreiterHolzvollernter) i.getValue()).getArbeitskopf()).getMaxThickness();
                        anz++;
                    }
                }
            }
        }

        return (anz == 0) ? 0 : dicke / (double) anz;
    }

}
