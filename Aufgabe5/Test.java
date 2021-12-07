import java.util.Iterator;

public class Test {

    public static void main(String[] args) {
        System.out.println("Mandatory Test:");

        // 1.MultiGroup

        System.out.println("\n(1.)------------MultiGroup------------\n");

        Fagus fagus4 = new Fagus(12, 0.4);
        QuercusRobur quercusRobur4 = new QuercusRobur(10, 5, "moor resistant");
        Quercus quercus4 = new Quercus(10, 5);

        //Single Groups
        SingleGroup<Fagus> sgF1 = new SingleGroup<Fagus>();
        sgF1.add(fagus4);
        SingleGroup<Quercus> sgF2 = new SingleGroup<Quercus>();
        sgF2.add(quercus4);
        SingleGroup<QuercusRobur> sgF3 = new SingleGroup<QuercusRobur>();
        sgF3.add(quercusRobur4);
        SingleGroup<Integer> sgF4 = new SingleGroup<Integer>();
        sgF4.add(50);
        SingleGroup<Tree> sgT = new SingleGroup<Tree>();


        //mg1
        //given from iterator
        Fagus fagus3 = new Fagus(16, 0.3);
        //given from iterator
        Fagus fagus2 = new Fagus(18, 0.2);
        MultiGroup<Fagus, Fagus> mg1 = new MultiGroup<Fagus, Fagus>(sgF1, Fagus.relation());
        mg1.add(fagus3);
        mg1.add(fagus2);
        //mg1 Iterator
        System.out.println("mg1 Iterator:");
        for (Fagus c : mg1) {
            System.out.println("mg1: " + c.height());
        }


        //mg 2
        //given from iterator
        QuercusRobur quercusRobur3 = new QuercusRobur(20, 19, "storm resistant");
        //given from iterator
        QuercusRobur quercusRobur5 = new QuercusRobur(22, 21, "storm resistant");
        //not given from iterator
        QuercusRobur quercusRobur6 = new QuercusRobur(15, 14, "storm resistant");
        MultiGroup<QuercusRobur, Fagus> mg2 = new MultiGroup<QuercusRobur, Fagus>(mg1, QuercusRobur.relation());
        mg2.add(quercusRobur3);
        mg2.add(quercusRobur5);
        mg2.add(quercusRobur6);
        //mg2 Iterator
        System.out.println("mg2 Iterator:");
        for (QuercusRobur c : mg2) {
            System.out.println("mg2: " + c.height());
        }


        //mg3
        //given from iterator
        Quercus quercus3 = new Quercus(20, 19);
        //given from iterator
        Quercus quercus5 = new Quercus(22, 21);
        //not given from iterator
        Quercus quercus6 = new Quercus(15, 14);
        MultiGroup<Quercus, Fagus> mg3 = new MultiGroup<Quercus, Fagus>(mg1, Quercus.relation());
        mg3.add(quercus3);
        mg3.add(quercus5);
        mg3.add(quercus6);
        //mg3 Iterator
        System.out.println("mg3 Iterator:");
        for (Quercus c : mg3) {
            System.out.println("mg3: " + c.height());
        }


        //mg4
        //given from iterator
        QuercusRobur quercusRobur2 = new QuercusRobur(30, 29, "freeze resistant");
        //given from iterator
        QuercusRobur quercusRobur7 = new QuercusRobur(34, 33, "freeze resistant");
        //not given from iterator
        QuercusRobur quercusRobur8 = new QuercusRobur(20, 17, "freeze resistant");
        MultiGroup<QuercusRobur, QuercusRobur> mg4 = new MultiGroup<QuercusRobur, QuercusRobur>(mg2, QuercusRobur.relation());
        mg4.add(quercusRobur2);
        mg4.add(quercusRobur7);
        mg4.add(quercusRobur8);
        //mg4 Iterator
        System.out.println("mg4 Iterator:");
        for (QuercusRobur c : mg4) {
            System.out.println("mg4: " + c.height());
        }


        //mg 5
        //given from iterator
        QuercusRobur quercusRobur1 = new QuercusRobur(40, 35, "fire resistant");
        //given from iterator
        QuercusRobur quercusRobur9 = new QuercusRobur(43, 36, "fire resistant");
        //given from iterator
        QuercusRobur quercusRobur10 = new QuercusRobur(38, 32, "fire resistant");
        MultiGroup<QuercusRobur, Quercus> mg5 = new MultiGroup<QuercusRobur, Quercus>(mg3, QuercusRobur.relation());
        mg5.add(quercusRobur1);
        mg5.add(quercusRobur9);
        mg5.add(quercusRobur10);
        //mg5 Iterator
        System.out.println("mg5 Iterator:");
        for (QuercusRobur c : mg5) {
            System.out.println("mg5: " + c.height());
        }


        //mg 6
        //given from iterator
        Quercus quercus2 = new Quercus(50, 45);
        //not given from iterator
        Quercus quercus7 = new Quercus(37, 35);
        //given from iterator
        Quercus quercus8 = new Quercus(47, 43);
        MultiGroup<Quercus, QuercusRobur> mg6 = new MultiGroup<Quercus, QuercusRobur>(mg5, Quercus.relation());
        mg6.add(quercus2);
        mg6.add(quercus7);
        mg6.add(quercus8);
        //mg6 Iterator
        System.out.println("mg6 Iterator:");
        for (Quercus c : mg6) {
            System.out.println("mg6: " + c.height());
        }


        //mg 7
        //given from iterator
        Quercus quercus1 = new Quercus(60, 55);
        //given from iterator
        Quercus quercus9 = new Quercus(55, 53);
        //not given from iterator
        Quercus quercus10 = new Quercus(42, 41);
        MultiGroup<Quercus, Quercus> mg7 = new MultiGroup<Quercus, Quercus>(mg6, Quercus.relation());
        mg7.add(quercus1);
        mg7.add(quercus9);
        mg7.add(quercus10);
        //mg7 Iterator
        System.out.println("mg7 Iterator:");
        for (Quercus c : mg7) {
            System.out.println("mg7: " + c.height());
        }


        //mg 8
        //given from iterator
        QuercusRobur quercusRobur0 = new QuercusRobur(70, 65, "infestation resistant");
        //given from iterator
        QuercusRobur quercusRobur11 = new QuercusRobur(73, 66, "infestation resistant");
        //not given from iterator
        QuercusRobur quercusRobur12 = new QuercusRobur(52, 51, "infestation resistant");
        MultiGroup<QuercusRobur, Tree> mg8 = new MultiGroup<QuercusRobur, Tree>(mg7, QuercusRobur.relation());
        mg8.add(quercusRobur0);
        mg8.add(quercusRobur11);
        mg8.add(quercusRobur12);
        //mg8 Iterator
        System.out.println("mg8 Iterator:");
        for (QuercusRobur c : mg8) {
            System.out.println("mg8: " + c.height());
        }


        //mg 9
        //given from iterator
        Quercus quercus0 = new Quercus(80, 75);
        //given from iterator
        Quercus quercus11 = new Quercus(100, 80);
        //not given from iterator
        Quercus quercus12 = new Quercus(64, 62);
        MultiGroup<Quercus, Tree> mg9 = new MultiGroup<Quercus, Tree>(mg8, Quercus.relation());
        mg9.add(quercus0);
        mg9.add(quercus11);
        mg9.add(quercus12);
        //mg9 Iterator
        System.out.println("mg9 Iterator:");
        for (Quercus c : mg9) {
            System.out.println("mg9: " + c.height());
        }


        //mg10
        Numeric numeric5 = new Numeric(100);
        MultiGroup<Integer, Integer> mg10 = new MultiGroup<Integer, Integer>(sgF4, numeric5);
        //given from iterator
        mg10.add(20);
        //given from iterator
        mg10.add(21);
        //mg10 Iterator
        System.out.println("mg10 Iterator:");
        for (Integer c : mg10) {
            System.out.println("mg10: " + c);
        }


        // 2.Functionality
        System.out.println("\n(2.)------------Functionality------------\n");

        System.out.println("mg1\nBefore:");

        Iterator<Fagus> fagusIterator1 = mg1.iterator();
        for (Fagus c : mg1) {
            System.out.println("mg1: " + c.height());
        }

        System.out.println("After:");
        fagusIterator1.next();
        fagusIterator1.remove();
        System.out.println("removed first element from mg via iterator:");
        for (Fagus c : mg1) {
            System.out.println("mg1: " + c.height());
        }
        mg1.add(fagus3);
        System.out.println("added it again:");
        for (Fagus c : mg1) {
            System.out.println("mg1: " + c.height());
        }

        System.out.println("mg2");

        System.out.println("Before:");

        Iterator<QuercusRobur> fagusIterator2 = mg2.iterator();
        for (QuercusRobur c : mg2) {
            System.out.println("mg2: " + c.height());
        }

        System.out.println("After:");
        fagusIterator2.next();
        fagusIterator2.remove();
        System.out.println("removed first element from mg via iterator:");
        for (QuercusRobur c : mg2) {
            System.out.println("mg2: " + c.height());
        }
        mg2.add(quercusRobur3);
        System.out.println("added it again:");
        for (QuercusRobur c : mg2) {
            System.out.println("mg2: " + c.height());
        }


        // 3.Generic
        System.out.println("\n(3.)------------Generic------------\n");
        MultiGroup<Quercus, Fagus> u = mg3;
        SingleGroup<Tree> v = sgT;
        Group<QuercusRobur, Quercus> w = mg5;
        System.out.println("Group:");
        for (QuercusRobur c : w) {
            System.out.println(c.resistance());
            v.add(c);
            u.add(c);
        }

        System.out.println("\nSingle Group:");

        for (Tree c : v) {
            System.out.println(c.height());
        }

        System.out.println("\nMulti Group:");

        for (Quercus c : u) {
            System.out.println(c.height());
        }

        // 4.Invoked
        System.out.println("\n(4.)------------Invoked------------\n");
        sgF1.related(fagus2, fagus2);
        System.out.println("sgF1: " + sgF1.invoked());
        System.out.println("sgF2: " + sgF2.invoked());
        System.out.println("sgF3: " + sgF3.invoked());
        System.out.println("sgF4: " + sgF4.invoked());
        System.out.println("mg1: " + mg1.invoked());
        System.out.println("mg2: " + mg2.invoked());
        System.out.println("mg3: " + mg3.invoked());
        System.out.println("mg4: " + mg4.invoked());
        System.out.println("mg5: " + mg5.invoked());
        System.out.println("mg6: " + mg6.invoked());
        System.out.println("mg7: " + mg7.invoked());
        System.out.println("mg8: " + mg8.invoked());
        System.out.println("mg9: " + mg9.invoked());
        System.out.println("mg10: " + mg10.invoked());

        // 5.Further testcases
        System.out.println("\n(5.)------------Further testcases------------\n");
        Numeric numeric = new Numeric(3);
        System.out.println("Numeric with a c equal to: 3");
        System.out.println(numeric.related(1, 2)); // true
        System.out.println(numeric.related(1, 5)); // false
        System.out.println(numeric.related(3, 1)); // true
        System.out.println(numeric.related(5, 1)); // false


        Numeric numeric2 = new Numeric(10);
        System.out.println("\nNumeric2 with a c equal to: 10");
        System.out.println(numeric2.related(0, 100)); //false
        System.out.println(numeric2.related(-2, 4)); //true
        System.out.println(numeric2.related(-10, 0)); //true
        System.out.println(numeric2.related(-4, -3)); //true
        System.out.println(numeric2.related(-90, -4)); //false

        Numeric numeric3 = new Numeric(1);
        System.out.println("\nNumeric3 with a c equal to: 1");
        System.out.println(numeric3.related(0, -2)); //false
        System.out.println(numeric3.related(2, 0)); //false


        Numeric numeric4 = new Numeric(0);
        System.out.println("\nNumeric4 with a c equal to: 0");
        System.out.println(numeric4.related(0, 100)); //false
        System.out.println(numeric4.related(-2, 4)); //false
        System.out.println(numeric4.related(3, 3)); //true

        System.out.println("\nInvoked counts:");

        System.out.println("Numeric relation count: " + numeric.invoked()); //4
        System.out.println("Numeric2 relation count: " + numeric2.invoked()); //5
        System.out.println("Numeric3 relation count: " + numeric3.invoked()); //2
        System.out.println("Numeric3 relation count: " + numeric4.invoked()); //3

    }
}