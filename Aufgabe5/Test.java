public class Test {

    public static void main(String[] args) {
        //TODO tests
        //SingleGroup<Fagus> sgF= new SingleGroup<Fagus>();

        //MultiGroup<Fagus, Fagus> mg1 = new MultiGroup<Fagus, Fagus>(sgF,);
        Numeric numeric = new Numeric(3);
        System.out.println("Numeric with a c equal to: 3");
        System.out.println(numeric.related(1, 2)); // true
        System.out.println(numeric.related(1, 5)); // false
        System.out.println(numeric.related(3, 1)); // true
        System.out.println(numeric.related(5, 1)); // false

        System.out.println("\n-----------------------------------\n");

        Numeric numeric2 = new Numeric(10);
        System.out.println("Numeric2 with a c equal to: 10");
        System.out.println(numeric2.related(0, 100)); //false
        System.out.println(numeric2.related(-2, 4)); //true
        System.out.println(numeric2.related(-10, 0)); //true
        System.out.println(numeric2.related(-4, -3)); //true
        System.out.println(numeric2.related(-90, -4)); //false

        System.out.println("\n-----------------------------------\n");

        Numeric numeric3 = new Numeric(1);
        System.out.println("Numeric3 with a c equal to: 1");
        System.out.println(numeric3.related(0, -2)); //false
        System.out.println(numeric3.related(2, 0)); //false

        System.out.println("\n-----------------------------------\n");

        Numeric numeric4 = new Numeric(0);
        System.out.println("Numeric2 with a c equal to: 0");
        System.out.println(numeric4.related(0, 100)); //false
        System.out.println(numeric4.related(-2, 4)); //false
        System.out.println(numeric4.related(3, 3)); //true

        System.out.println("\n-----------------------------------\n");

        System.out.println("Invoked counts:");

        System.out.println("Numeric relation count: " + numeric.invoked()); //4
        System.out.println("Numeric2 relation count: " + numeric2.invoked()); //5
        System.out.println("Numeric3 relation count: " + numeric3.invoked()); //2
        System.out.println("Numeric3 relation count: " + numeric4.invoked()); //3


        Quercus quercus1 = new Quercus(10,4);
        Quercus quercus2 = new Quercus(15,11);
        Quercus quercus3 = new Quercus(5,3);
        Quercus quercus4 = new Quercus(25,19);

        QuercusRobur quercusRobur1 = new QuercusRobur(12,10,"fire resistant");
        QuercusRobur quercusRobur2 = new QuercusRobur(24,22,"fire resistant");
        QuercusRobur quercusRobur3 = new QuercusRobur(7,6,"fire resistant");
        QuercusRobur quercusRobur4 = new QuercusRobur(32,30,"fire resistant");

        Fagus fagus1 = new Fagus(12,0.4);
        Fagus fagus2 = new Fagus(22,0.2);
        Fagus fagus3 = new Fagus(31,0.7);
        Fagus fagus4 = new Fagus(19,0.22);


    }
        /*
    Allocation of responsibilities: TODO
        Daniel Vercimak:
        Maximilian Gaber: Relation, Tree, Numeric, Quercus, QuercusRobur, Fagus and realated Test
        Nico Lehegzek:
     */

}
