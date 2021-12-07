public class Test {

    public static void main(String[] args) {

        // Erzeugen der Testdaten:

        // Erzeugen von Arbeitskopf:
        // SchneideArbeitskopf
        Arbeitskopf sa1 = new SchneideArbeitskopf(5.3);
        Arbeitskopf sa2 = new SchneideArbeitskopf(2.4);
        Arbeitskopf sa3 = new SchneideArbeitskopf(7.1);
        Arbeitskopf sa4 = new SchneideArbeitskopf(3.7);
        Arbeitskopf sa5 = new SchneideArbeitskopf(1.8);
        Arbeitskopf sa6 = new SchneideArbeitskopf(8.9);
        // HackschnitzelArbeitskopf
        Arbeitskopf ha1 = new HackschnitzelArbeitskopf(3);
        Arbeitskopf ha2 = new HackschnitzelArbeitskopf(6);
        Arbeitskopf ha3 = new HackschnitzelArbeitskopf(2);
        Arbeitskopf ha4 = new HackschnitzelArbeitskopf(5);
        Arbeitskopf ha5 = new HackschnitzelArbeitskopf(8);
        Arbeitskopf ha6 = new HackschnitzelArbeitskopf(9);

        /*
            Hinweis:
                Beim Erzeugen wird automatisch in die static Listen in Forstbetrieb und Holzvollbetrieb
                eingefügt, dass die Forstbetriebe in Region und die Holzvollernter in Forsbetrieb über den
                Namen/ID angesprochen werden können.
        */

        // Erzeugen von Holzvollerntern:
        // SchreiterHolzvollernter
        SchreiterHolzvollernter sh1 = new SchreiterHolzvollernter(0, sa1);
        sh1.increaseZeitInBetrieb(12);
        sh1.increaseZeitInBetrieb(8);
        sh1.increaseSteps(300);
        SchreiterHolzvollernter sh2 = new SchreiterHolzvollernter(1, sa2);
        sh2.increaseZeitInBetrieb(30);
        sh2.increaseZeitInBetrieb(70);
        sh2.increaseSteps(93478);
        sh2.increaseSteps(3247);
        SchreiterHolzvollernter sh3 = new SchreiterHolzvollernter(2, sa3);
        sh3.increaseZeitInBetrieb(90);
        sh3.increaseZeitInBetrieb(1);
        sh3.increaseSteps(50);
        sh3.increaseSteps(12309);
        SchreiterHolzvollernter sh4 = new SchreiterHolzvollernter(3, ha1);
        sh4.increaseZeitInBetrieb(21);
        sh4.increaseZeitInBetrieb(4);
        sh4.increaseSteps(1232);
        SchreiterHolzvollernter sh5 = new SchreiterHolzvollernter(4, ha2);
        sh5.increaseZeitInBetrieb(30);
        sh5.increaseZeitInBetrieb(23);
        sh5.increaseSteps(1232);
        sh5.increaseZeitInBetrieb(32.23);
        sh5.increaseZeitInBetrieb(4.9);
        sh5.increaseSteps(1232);
        SchreiterHolzvollernter sh6 = new SchreiterHolzvollernter(5, ha3);
        sh6.increaseZeitInBetrieb(2.3121);
        sh6.increaseZeitInBetrieb(4);
        sh6.increaseSteps(1232);
        sh6.increaseZeitInBetrieb(21.12);
        sh6.increaseZeitInBetrieb(23.2);
        sh6.increaseSteps(1123);
        sh6.increaseZeitInBetrieb(654);
        sh6.increaseZeitInBetrieb(54.12);
        sh6.increaseSteps(1231);
        // Erzeugen von RadHolzvollernter
        RadHolzvollernter rh1 = new RadHolzvollernter(6, sa4);
        rh1.changeArbeitskopf(ha6);
        rh1.increaseZeitInBetrieb(12.2);
        rh1.increaseMeter(1000);
        rh1.increaseMeter(180);
        RadHolzvollernter rh2 = new RadHolzvollernter(7, sa5);
        rh2.increaseZeitInBetrieb(10);
        rh2.increaseZeitInBetrieb(100);
        rh2.increaseZeitInBetrieb(72.5);
        rh2.increaseMeter(1000);
        rh2.increaseMeter(150);
        rh2.increaseMeter(7);
        RadHolzvollernter rh3 = new RadHolzvollernter(8, sa6);
        rh3.increaseZeitInBetrieb(82);
        rh3.increaseZeitInBetrieb(54);
        rh3.increaseZeitInBetrieb(41.2);
        rh3.increaseMeter(1860);
        rh3.increaseMeter(830);
        rh3.increaseMeter(540);
        RadHolzvollernter rh4 = new RadHolzvollernter(9, ha4);
        rh4.increaseZeitInBetrieb(42);
        rh4.increaseZeitInBetrieb(11);
        rh4.increaseZeitInBetrieb(53.2);
        rh4.increaseMeter(723);
        rh4.increaseMeter(12);
        rh4.increaseMeter(1);
        rh4.increaseZeitInBetrieb(23);
        rh4.increaseZeitInBetrieb(11.8);
        rh4.increaseZeitInBetrieb(9.5);
        rh4.increaseMeter(456);
        rh4.increaseMeter(23);
        rh4.increaseMeter(129);
        RadHolzvollernter rh5 = new RadHolzvollernter(10, sa5);
        rh5.increaseZeitInBetrieb(64);
        rh5.increaseZeitInBetrieb(43.2);
        rh5.increaseZeitInBetrieb(98.9);
        rh5.increaseMeter(432);
        rh5.increaseMeter(124);
        rh5.increaseMeter(723);
        RadHolzvollernter rh6 = new RadHolzvollernter(11, ha6);
        rh6.increaseZeitInBetrieb(664);
        rh6.increaseZeitInBetrieb(221);
        rh6.increaseZeitInBetrieb(23.2);
        rh6.increaseMeter(5432);
        rh6.increaseMeter(213123);
        rh6.increaseMeter(123);


        // Erzeugen von Fortsbetrieben:
        Forstbetrieb b0 = new Forstbetrieb("Sunny Vale");
        b0.addErnter(0);
        b0.addErnter(4);
        b0.addErnter(10);
        b0.addErnter(11);
        Forstbetrieb b1 = new Forstbetrieb("Sunshine Farm");
        b1.addErnter(2);
        b1.addErnter(3);
        b1.addErnter(1);
        b1.addErnter(8);
        Forstbetrieb b2 = new Forstbetrieb("Winter Wonder Land");
        b2.addErnter(11);
        b2.addErnter(8);
        Forstbetrieb b3 = new Forstbetrieb("Snowy Lake");
        b3.addErnter(6);
        // leer für explizite Division durch 0
        Forstbetrieb b4 = new Forstbetrieb("Wood workers");
        Forstbetrieb b5 = new Forstbetrieb("Woody Woods");
        b5.addErnter(5);
        b5.addErnter(2);
        b5.addErnter(10);
        b5.addErnter(11);
        Forstbetrieb b6 = new Forstbetrieb("Tree farmers");
        b6.addErnter(2);
        Forstbetrieb b7 = new Forstbetrieb("Tree Vale");
        b7.addErnter(7);
        b7.addErnter(5);
        b7.addErnter(11);
        Forstbetrieb b8 = new Forstbetrieb("Mountain Vale");
        b8.addErnter(1);
        b8.addErnter(4);
        b8.addErnter(6);
        b8.addErnter(11);
        Forstbetrieb b9 = new Forstbetrieb("Big Trees");
        b9.addErnter(2);
        b9.addErnter(2);
        b9.addErnter(2);
        Forstbetrieb b10 = new Forstbetrieb("Wind Vale");
        b10.addErnter(5);
        b10.addErnter(4);
        b10.addErnter(9);
        b10.addErnter(8);
        b10.addErnter(1);


        // Erzeugen von Regionen:
        Region r1 = new Region("Wien");
        r1.addForstbetrieb("Sunny Vale");
        r1.addForstbetrieb("Wind Vale");
        r1.addForstbetrieb("Woody Woods");
        Region r2 = new Region("Graz");
        r2.addForstbetrieb("Big Trees");
        r2.addForstbetrieb("Sunshine Farm");
        r2.addForstbetrieb("Winter Wonder Land");
        r2.addForstbetrieb("Snowy Lake");
        r2.addForstbetrieb("Mountain Vale");
        Region r3 = new Region("Linz");
        r3.addForstbetrieb("Mountain Vale");
        r3.addForstbetrieb("Tree Vale");
        r3.addForstbetrieb("Tree farmers");
        r3.addForstbetrieb("Wood workers");

        System.out.println("\n~~~~~~~~~~~~~~Hinzufügen und Entfernen Tests~~~~~~~~~~~~~~\n");
        // Hinzufügen und Entfernen Tests
        System.out.println("Vorher: ");
        System.out.println(r1);
        // wird entfernt
        r1.removeForstbetrieb("Sunny Vale");
        // wird nicht entfernt weil nicht vorhanden
        r1.removeForstbetrieb("Sunny Vale");
        // wird entfernt
        r1.removeForstbetrieb("Wind Vale");
        // wird hinzugefügt
        r1.addForstbetrieb("Sunshine Farm");
        // wird hinzugefügt
        r1.addForstbetrieb("Winter Wonder Land");
        System.out.println("Ohne Sunny Vale, Ohne Wind Vale, Mit Sunshine Farm, Mit Wonder Land");
        System.out.println("Nacher: ");
        System.out.println(r1);

        System.out.println("Vorher: ");
        System.out.println(r2);
        // wird entfernt
        r2.removeForstbetrieb("Big Trees");
        // wird entfernt
        r2.removeForstbetrieb("Sunshine Farm");
        // wird entfernt
        r2.removeForstbetrieb("Winter Wonder Land");
        // wird entfernt
        r2.removeForstbetrieb("Snowy Lake");
        // wird hinzugefügt
        r2.addForstbetrieb("Sunshine Farm");
        // wird hinzugefügt
        r2.addForstbetrieb("Wood workers");
        // wird hinzugefügt
        r2.addForstbetrieb("Winter Wonder Land");
        System.out.println("Nacher: ");
        System.out.println(r2);

        System.out.println("\n~~~~~~~~~~~~~~Test für Division durch 0~~~~~~~~~~~~~~\n");
        System.out.println(r3);

        System.out.println("\n~~~~~~~~~~~~~~Zusätzliche Tests~~~~~~~~~~~~~~\n");
        // Forstbetriebetest:
        Region testReg = new Region("Wonder Wood");
        b0.addErnter(sh1.getId());
        b1.addErnter(sh2.getId());
        b0.addErnter(sh3.getId());
        b1.addErnter(sh5.getId());
        testReg.addForstbetrieb("Sunny Vale");
        testReg.addForstbetrieb("Sunshine Farm");
        // Gibt nun Sunny Vale und Sunshine Farm aus
        System.out.println(testReg);
        testReg.removeForstbetrieb("Sunny Vale");
        testReg.removeForstbetrieb("Sunshine Farm");
        // Gibt nichts aus, außer den Regionssnamen
        System.out.println("Nur Regionsname");
        System.out.println(testReg);
        b1.removeErnter(sh2.getId());
        b0.removeErnter(sh3.getId());
        sh2.increaseZeitInBetrieb(10);
        sh2.changeArbeitskopf(sa1);
        sh2.increaseSteps(1000);
        sh3.changeArbeitskopf(ha3);
        sh3.increaseSteps(123);
        b1.addErnter(sh2.getId());
        b1.addErnter(sh3.getId());
        testReg.addForstbetrieb("Sunny Vale");
        testReg.addForstbetrieb("Sunshine Farm");
        // Gibt Sunny Vale und Sunshine Farm mit abgeänderten Informationen aus
        System.out.println("Wieder Ausgabe:");
        System.out.println(testReg);

    }
}
