public class Region {
    // (I): name !=null
    private final String name;

    // (I): Nur Objekte des Typs Forstbetrieb
    private final LinkedList forstbetriebe;

    // (Vor): name != null
    // (Nach):ein Objekt vom Typ Region wird erstellt
    public Region(String name) {
        this.name = name;
        this.forstbetriebe = new LinkedList();
    }

    // (Vor): name != null
    // (Nach): Wenn ein Forstbetrieb mit dem Namen name in allBetriebe existiert und in forstbetriebe nicht existiert, dann wird
    // er in forstbertiebe hinzugefügt.
    public void addForstbetrieb(String name) {
        // Liste aller erzeugten Holzvollernter
        LinkedList alleBetriebe = Forstbetrieb.getAlleForstbetriebe();

        Forstbetrieb h = mapBetrieb(name, alleBetriebe);
        if (h != null && mapBetrieb(name, forstbetriebe) == null) {
            this.forstbetriebe.add(h);
        }
    }

    // (Vor): name != null
    // (Nach): Wenn ein Forstbetrieb in mit dem Namen name in forstbetriebe existiert, dann wird er entfernt.
    public void removeForstbetrieb(String name) {
        Forstbetrieb f = mapBetrieb(name, forstbetriebe);
        if (f != null)
            this.forstbetriebe.remove(f);
    }

    // (Vor): l !=null und name !=null
    // (Nach): sucht nach einem Forstbetrieb mittels Namen in l und gibt das Objekt zurück
    //        falls nicht gefunden --> Rückgabewert = null
    private Forstbetrieb mapBetrieb(String name, LinkedList l) {
        Forstbetrieb gefunden = null;
        for (Node i = l.getHead(); i != null; i = i.getNext()) {
            if (((Forstbetrieb) i.getValue()).getName().equals(name)) {
                gefunden = (Forstbetrieb) i.getValue();
            }
        }

        return gefunden;
    }

    @Override
    // (Nach): Gibt einen StringBuilder mit allen Informationen zu allen Forstbetrieben in forstbetriebe zurück
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Region: ").append(this.name).append("\n");
        System.out.println("-----------------------");
        for (Node i = forstbetriebe.getHead(); i != null; i = i.getNext()) {
            builder.append("Forstberieb name: ").append(((Forstbetrieb) i.getValue()).getName()).append("\n");
            builder.append("Durchschnittliche Betriebsstunden: ").append(((Forstbetrieb) i.getValue()).
                    statdurchBstunden()).append(" std\n");
            builder.append("Durchschnittliche Betriebsstunden mit Schnetzlerkopf: ").
                    append(((Forstbetrieb) i.getValue()).statdurchBstundenHack()).append(" std\n");
            builder.append("Durchschnittliche Betriebsstunden Radernter: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchBstundenRad()).append(" std\n");
            builder.append("Durchschnittliche Dicke mit Hackschnitzelkopf: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchDicke()).append(" cm\n");
            builder.append("Durchschnittliche Betriebsstunden mit Schneidekopf: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchBstundenSchneiden()).append(" std\n");
            builder.append("Durchschnittliche Betriebsstunden aller Schreiterernter: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchBstundenSchreiter()).append(" std\n");
            builder.append("Durchschnittliche Wegstrecke aller Radernter: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchMeter()).append(" m\n");
            builder.append("Durchschnittliche Dicke aller Radenter mit Hackschnitzelkopf: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchDickeRad()).append(" cm\n");
            builder.append("Durchschnittliche Wegstrecke aller Radernter mit Schnetzlerkopf: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchMeterHack()).append(" m\n");
            builder.append("Durchschnittliche Wegstrecke aller Radernter mit Schneidekopf: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchMeterSchneiden()).append(" m\n");
            builder.append("Durchschnittliche Schritte: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchSchritte()).append("\n");
            builder.append("Durchschnittliche Schritte mit Schnetzlerkopf ")
                    .append(((Forstbetrieb) i.getValue()).statdurchSchritteHack()).append("\n");
            builder.append("Maximale Stücklänge mit Schneidearbeitskopf: ")
                    .append(((Forstbetrieb) i.getValue()).statMaxLength()).append(" m\n");
            builder.append("Minimale Stücklänge mit Schneidearbeitskopf: ")
                    .append(((Forstbetrieb) i.getValue()).statMinLength()).append(" m\n");
            builder.append("Maximale Stücklänge aller Radernter: ")
                    .append(((Forstbetrieb) i.getValue()).statMaxLengthRad()).append(" m\n");
            builder.append("Minimale Stücklänge aller Radernter: ")
                    .append(((Forstbetrieb) i.getValue()).statMinLengthRad()).append(" m\n");
            builder.append("Maximale Stücklänge aller Schreiterernter: ")
                    .append(((Forstbetrieb) i.getValue()).statMaxLengthSchreiter()).append(" m\n");
            builder.append("Minimale Stücklänge aller Schreiterernter: ")
                    .append(((Forstbetrieb) i.getValue()).statMinLengthSchreiter()).append(" m\n");
            builder.append("Durchschnittliche Dicke aller Schreiterernter mit Hackschnitzelkopf: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchDickeSchreiter()).append(" cm\n");
            builder.append("Durchschnittliche Schritte Schneidekopf: ")
                    .append(((Forstbetrieb) i.getValue()).statdurchSchritteSchneiden()).append("\n");
            if (i.getNext() != null) builder.append("----------\n");
        }
        builder.append("-----------------------");
        return builder.toString();
    }
}
