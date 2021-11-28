public class Test {
    /*
    Explanation why between some types are no subtyping-relationships:

    Quercus
        Domestic: The context of the task describes that: many types of quercus are only domestic in America and some
                  in Europe. Therefore, not ALL types of quercus are domestic in Austria and it would be wrong to assume
                  the contrary that every type of quercus is domestic in Austria and in further action a subtype
                  of domestic. The principle of subtyping: "U is a subtype of T if an instance of U could be
                  used everywhere where an instance of T is expected". But since we can't use an object of quercus
                  where domestic is expected quercus is no subtype of domestic.
        CarpinusBetulus: Quercus represents a whole tree species of Fagaceae's, while CarpinusBetulus is a specific
                         tree type from the family of Betulaceae's. It would not be rational to assume
                         that a species is a subtype of a tree type of another family also for the sole reason
                         that trees can only belong to one single family or type. Even if this attribute wasn't
                         given properties of subtyping would have been violated (e.g. domesticity).
        FagusSylvitica: Pretty much the same goes for this as for CarpinusBetulus with small differences.
                        Quercus represents a whole tree species of Fagaceae's, while CarpinusBetulus is a specific
                        tree type from the family of Fagaceae's It would not be rational to assume
                        that a species is a subtype of a tree type for the sole reason
                        that trees can only belong to one single family or type. Even if this attribute wasn't
                        given properties of subtyping would have been violated (e.g. domesticity).
        LightDemanding: The specification tells us that there exists the QuercusRubra (=Roteiche) which is shadow
                        and environmental resistant. Therefore, not all trees of the family are LightDemanding and
                        Quercus can not be a subtype of LightDemanding.
    QuercusPatraea
     The reasons here are very similliar to the reasons of QuercusRobur.
        CarpinusBetulus: Let's begin with the problem that trees can only belong to one single family or type. Since
                         CarpinusBetulus are from the species Betulaceae and QuercusPatraea of the Type Fagaceae
                         this would be violated. Furthermore contrary properties exclude each other -> CarpinusBetulus
                         are shadow resistant while QuercusPatraea are LightDemanding - meaning this would also be
                         violated. Additionally they also don't share some other properties such as:
                         QuercusPatraea are good for forestry and CarpinusBetulus aren't.
                         We could not use an object of the type QuercusPatraea where an object of
                         CarpinusBetulus is expected. Therefore QuercusPatraea is no subtype of CarpinusBetulus.
                         We could inherit from CarpinusBetulus but that would only save us duplicate code in the
                         beginning but would cause much more problems in the future.
        FagusSylvitica: This time the conflict isn't located in the differences of family but much more in the
                        different properties that exclude each other such as:
                        The QuercusPatraea(A) grows under ContinentalClimate while the FagusSylvitica(B) grows under
                        oceanic climate. Furthermore A is LightDemanding while B is resistant against shadows. Also
                        the impact on forestry of A is higher than from B because the important parts of B are located
                        in the treetops. Therefore, QuercusPatraea is not a subtype of FagusSylvitica.
        QuercusRobur: This tree type is very similar to QuercusPatraea but the difference lies in the detail:
                      - The fruit of the the two types are different.
                      - The QuercusRobur is more LightDemanding than the QuercusPatraea.
                      - They are located in partly different locations which indicates that the QuercusRobur
                        lives better under ContinentalClimate than the QuercusPatraea.
                      Therefore it would not be correct to assume that everywhere where we expect an object of
                      QuercusRobur we could use an object of QuercusPatraea -> QuercusPatraea is no subtype of
                      QuercusRobur.
    QuercusRobur
     The reasons here are very similliar to the reasons of QuercusPatraea.
        CarpinusBetulus: Let's begin with the problem that trees can only belong to one single family or type. Since
                         CarpinusBetulus are from the species Betulaceae and QuercusRobur of the Type Fagaceae
                         this would be violated. Furthermore contrary properties exclude each other -> CarpinusBetulus
                         are shadow resistant while QuercusRobur are LightDemanding - meaning this would also be
                         violated. Additionally they also don't share some other properties such as:
                         QuercusRobur are good for forestry and CarpinusBetulus aren't.
                         We could not use an object of the type QuercusRobur where an object of
                         CarpinusBetulus is expected. Therefore QuercusRobur is no subtype of CarpinusBetulus.
                         We could inherit from CarpinusBetulus but that would only save us duplicate code in the
                         beginning but would cause much more problems in the future.
        FagusSylvitica: This time the conflict isn't located in the differences of family but much more in the
                        different properties that exclude each other such as:
                        The QuercusRobur(A) grows under ContinentalClimate while the FagusSylvitica(B) grows under
                        oceanic climate. Furthermore A is LightDemanding while B is resistant against shadows. Also,
                        the impact on forestry of A is higher than from B because the important parts of B are located
                        in the treetops. Therefore, QuercusRobur is not a subtype of FagusSylvitica.
        QuercusPatraea: This tree type is very similar to QuercusRobur but the difference lies in the detail:
                         - The fruit of the two types are different.
                         - The QuercusRobur is more LightDemanding than the QuercusPatraea.
                         - They are located in partly different locations which indicates that the QuercusRobur
                           lives better under ContinentalClimate than the QuercusPatraea.
                        Therefore it would not be correct to assume that everywhere where we expect an object of
                        QuercusPatraea we could use an object of QuercusRobur -> QuercusRobur is no subtype of
                        QuercusPatraea.
    Domestic
        ContinentalClimate: Not every domestic tree is a tree from ContinentalClimate, therefore Domestic is not a
                            subtype of ContinentalClimate. In Austria oceanic and continental climate are both
                            very close.
        Fagaceae: Not every domestic tree is also a Fagaceae, therefore Domestic is not a subtype of Fagaceae.

        LightDemanding: Not every domestic tree is LightDemanding, therefore Domestic is not a subtype of LightDemanding.

        Quercus: Domestic is not a subtype of Quercus, because Domestic is a tree type and Quercus is a tree family.

    ContinentalClimate
        Domestic: Not every continental tree is a domestic tree, therefore ContinentalClimate is not a
                            subtype of Domestic.
        Fragaceae: Not every continental tree is a fragaceae tree, therefore ContinentalClimate is not a
                            subtype of fragaceae.
        LightDemanding: Not every continental tree is a LightDemanding tree, therefore ContinentalClimate is not a
                            subtype of LightDemanding.
        FagusSylvatica: ContinentalClimate is not a tree family, it is a tree type, therefore ContinentalClimate is not
                        a subtype of FagusSylvatica

    CarpinusBetulus
        Fagaceae: CarpinusBetulus is not a tree in the family of Fagaceae(as stated in the exercise),
                  therefore CarpinusBetulus is not a subtype of Fagaceae.
        LightDemanding: In the text it is not stated that CarpinusBetulus is a LightDemanding tree. Thats why it is not a
                        subtype of LightDemanding.
        Quercus: CarpinusBetulus is not part of the Quercus family, therefore not a subtype.

        QuercusPetraea: CarpnusBetulus has a low forestry use and QuercusPetraea has a high forestry use. They are also different trees,
                        therefore not a subtype.
        QuercusRobur: CarpinusBetulus is shade compatible and QuercusPetraea not shade compatible. They are also different trees,
                      therefore not a subtype.
        FagusSylvatica: CarpinusBetulus is not easily affected by the habitual activity, FagusSylvatica is. Therefore not a
                        subtype.
    FagusSylvatica
        ContinentalClimate: FagusSylvatica is a tree of pacific climate, therefore FagusSylvatica is not a
                            subtype of ContinentalClimate. In Austria oceanic and continental climate are both
                            very close.
        LightDemanding: FagusSylvatica is not a LightDemanding tree therefore not a subtype.

        CarpinusBetulus: FagusSylvatica is easily affected by the habitual activity, CarpinusBetulus is not. Therefore not a
                        subtype.
        Quercus: FagusSylvatica is not part of the Quercus family, therefore not a subtype.

        QuercusPetraea: FagusSylvatica has a big enviromental consumption in difference to QuercusPetraea. They are also different
                        tree types. Therefore not a subtype.
        QuercusRobur: QuercusRobur is LightDemanding, FagusSylvatica is not LightDemanding. They are also different tree types.
                      Thats why there is not subtyping between them.

    LightDemanding
        Quercus: There are more tree species than quercus and if light demanding would be subtype of quercus every other
                 tree species would be from now on a quercus which is obviously wrong.
        FagusSylvitica: While Light demanding represents a whole tree species and fagus sylvitica represents only
                        specific tree type, light demanding can't be a subtype of fagus sylvitica . Furthermore, there
                        are more tree species than fagus sylvitica and if light demanding would be subtype of fagus
                        sylvitica  every other tree species would be from now on a fagus sylvitica  which is obviously
                        wrong.
        CarpinusBetulus: While Light demanding represents a whole tree species and carpinus betulus represents only
                         specific tree type, light demanding can't be a subtype of carpinus betulus. Furthermore, there
                         are more tree species than carpinus betulus and if light demanding would be subtype of carpinus
                         betulus every other tree species would be from now on a carpinus betulus which is obviously wrong.
        Domestic: Not every light demanding tree is domestic, therefore light demanding is not a subtype of domestic.
        ContinentalClimate: Not every light demanding tree is continental climate, therefore light demanding is not a
                            subtype of continental climate.
        Fagaceae: Not every light demanding tree is fagaceae, therefore light demanding is not a subtype of Fagaceae.
    Fagaceae:
        FagusSylvitica: While Light demanding represents a whole tree species and fagus sylvitica represents only
            specific tree type, fagaceae can't be a subtype of fagus sylvitica . Furthermore, there
            are more tree species than fagus sylvitica and if fagaceae would be subtype of fagus
            sylvitica  every other tree species would be from now on a fagus sylvitica  which is obviously
            wrong.
        CarpinusBetulus: While Light demanding represents a whole tree species and carpinus betulus represents only
            specific tree type, Fagaceae can't be a subtype of carpinus betulus. Furthermore, there
            are more tree species than carpinus betulus and if Fagaceae would be subtype of carpinus
            betulus every other tree species would be from now on a carpinus betulus which is obviously wrong.
        Domestic: Not every Fagaceae tree is domestic, therefore Fagaceae is not a subtype of domestic.
        ContinentalClimate: Not every Fagaceae tree is continental climate, therefore Fagaceae is not a
            subtype of continental climate.
        LightDemanding: Not every Fagaceae tree is light demanding, therefore Fagaceae is not a subtype of light demanding.
     */
    public static void main(String[] args) {

        double lf1 = 10.5, lf2 = 5.4, lf3 = 30, lf4 = 55.4, lf5 = 33.2;
        double lt1 = 47.253741, lt2 = 48.1441, lt3 = 47.153716, lt4 = 47.249743, lt5 = 46.722203;
        double lg1 = 11.601487, lg2 = 16.3062, lg3 = 16.268880, lg4 = 9.979737, lg5 = 14.180588;
        // Tirol
        Domestic t1 = new FagusSylvatica(lf1, lg1, lt1);
        // Vienna
        Tree t2 = new FagusSylvatica(lf2, lg2, lt2);
        // Burgenland
        Fagaceae t3 = new FagusSylvatica(lf3, lg3, lt3);
        // Vorarlberg
        FagusSylvatica t4 = new FagusSylvatica(lf4, lg4, lt4);
        // Carinthia
        FagusSylvatica t5 = new FagusSylvatica(lf5, lg5, lt5);

        System.out.println("Test subtyping (replaceability) of Fagus sylvatica:\n");
        System.out.println("t1 domestic located in Tirol: ");
        testDomestic(t1);
        testTree(t1);
        System.out.println("t2 tree located in Vienna: ");
        testTree(t2);
        System.out.println("t3 fagaceae located in Burgenland: ");
        testTree(t3);
        testFagaceae(t3);
        System.out.println("t4 fagus sylvatica located in Vorarlberg: ");
        testTree(t4);
        testDomestic(t4);
        testFagaceae(t4);
        System.out.println("t5 fagus sylvatica located in Carinthia: ");
        testTree(t5);
        testDomestic(t5);
        testFagaceae(t5);

        //Tests for CarpinunusBetulus
        // Tirol
        Domestic c1 = new CarpinusBetulus(lf1, lg1, lt1);
        // Vienna
        Tree c2 = new CarpinusBetulus(lf2, lg2, lt2);
        // Burgenland
        ContinentalClimate c3 = new CarpinusBetulus(lf3, lg3, lt3);
        // Vorarlberg
        CarpinusBetulus c4 = new CarpinusBetulus(lf4, lg4, lt4);
        // Carinthia
        CarpinusBetulus c5 = new CarpinusBetulus(lf5, lg5, lt5);

        System.out.println("Test subtyping (replaceability) of Carpinunus Betulus:\n");
        System.out.println("c1 domestic located in Tirol: ");
        testDomestic(c1);
        testTree(c1);
        System.out.println("c2 tree located in Vienna: ");
        testTree(c2);
        System.out.println("c3 continental climate located in Burgenland: ");
        testTree(c3);
        testContinentalClimate(c3);
        System.out.println("c4 Carpinunus Betulus located in Vorarlberg: ");
        testTree(c4);
        testDomestic(c4);
        testContinentalClimate(c4);
        System.out.println("c5 Carpinunus Betulus located in Carinthia: ");
        testTree(c5);
        testDomestic(c5);
        testContinentalClimate(c4);


        // Tests for QuercusPetraea and QuercusRobur
        double sq1 = 30, sq2 = 34, sq3 = 16, sq4 = 40, sq5 = 31, sq6 = 2, sq7 = 4;
        QuercusPetraea q1 = new QuercusPetraea(sq1, lg2, lt2);
        Quercus q2 = new QuercusPetraea(sq2, lg3, lt3);
        ContinentalClimate q3 = new QuercusPetraea(sq3, lg1, lt1);
        Fagaceae q4 = new QuercusPetraea(sq4, lg4, lt4);
        LightDemanding q5 = new QuercusPetraea(sq5, lg5, lt5);
        Tree q6 = new QuercusPetraea(sq6, lg5, lt5);
        Domestic q7 = new QuercusPetraea(sq7, lg2, lt2);

        double sr1 = 20, sr2 = 22, sr3 = 7, sr4 = 14, sr5 = 31, sr6 = 32, sr7 = 28;
        QuercusRobur r1 = new QuercusRobur(sr1, lg3, lt3);
        Quercus r2 = new QuercusRobur(sr2, lg5, lt5);
        ContinentalClimate r3 = new QuercusRobur(sr3, lg2, lt2);
        Fagaceae r4 = new QuercusRobur(sr4, lg4, lt4);
        LightDemanding r5 = new QuercusRobur(sr5, lg5, lt5);
        Tree r6 = new QuercusRobur(sr6, lg1, lt1);
        Domestic r7 = new QuercusRobur(sr7, lg3, lt3);


        System.out.println("Test subtyping (replaceability) of QuercusPetraea and QuercusRobur:\n");
        // test subtyping:
        System.out.println("Test q1 (QuercusPetraea):");
        System.out.println("Tree:");
        testTree(q1);
        System.out.println("Fagaceae:");
        testFagaceae(q1);
        System.out.println("ContinentalClimate:");
        testContinentalClimate(q1);
        System.out.println("Domestic:");
        testDomestic(q1);
        System.out.println("Quercus:");
        testQuercus(q1);
        System.out.println("LightDemanding:");
        testLightDemanding(q1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test q2 (Quercus):");
        System.out.println("Tree:");
        testTree(q2);
        System.out.println("Fagaceae:");
        testFagaceae(q2);
        System.out.println("ContinentalClimate:");
        testContinentalClimate(q2);
        System.out.println("Quercus:");
        testQuercus(q2);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test q3 (ContinentalClimate):");
        System.out.println("Tree:");
        testTree(q3);
        System.out.println("ContinentalClimate:");
        testContinentalClimate(q3);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test q4 (Fagaceae):");
        System.out.println("Tree:");
        testTree(q4);
        System.out.println("Fagaceae:");
        testFagaceae(q4);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test q5 (LightDemanding):");
        System.out.println("Tree:");
        testTree(q5);
        System.out.println("LightDemanding:");
        testLightDemanding(q5);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test q6 (Tree):");
        System.out.println("Tree:");
        testTree(q6);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test q7 (Domestic):");
        System.out.println("Tree:");
        testTree(q7);
        System.out.println("Domestic:");
        testDomestic(q7);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");


        System.out.println("Test r1 (QuercusPetraea):");
        System.out.println("Tree:");
        testTree(r1);
        System.out.println("Fagaceae:");
        testFagaceae(r1);
        System.out.println("ContinentalClimate:");
        testContinentalClimate(r1);
        System.out.println("Domestic:");
        testDomestic(r1);
        System.out.println("Quercus:");
        testQuercus(r1);
        System.out.println("LightDemanding:");
        testLightDemanding(r1);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test r2 (Quercus):");
        System.out.println("Tree:");
        testTree(r2);
        System.out.println("Fagaceae:");
        testFagaceae(r2);
        System.out.println("ContinentalClimate:");
        testContinentalClimate(r2);
        System.out.println("Quercus:");
        testQuercus(r2);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test r3 (ContinentalClimate):");
        System.out.println("Tree:");
        testTree(r3);
        System.out.println("ContinentalClimate:");
        testContinentalClimate(r3);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test r4 (Fagaceae):");
        System.out.println("Tree:");
        testTree(r4);
        System.out.println("Fagaceae:");
        testFagaceae(r4);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test r5 (LightDemanding):");
        System.out.println("Tree:");
        testTree(r5);
        System.out.println("LightDemanding:");
        testLightDemanding(r5);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test r6 (Tree):");
        System.out.println("Tree:");
        testTree(r6);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Test r7 (Domestic):");
        System.out.println("Tree:");
        testTree(r7);
        System.out.println("Domestic:");
        testDomestic(r7);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");


        // general tests
        System.out.println("species of q1: " + q1.species());
        System.out.println("size of q1: " + q1.size() + "m");
        q1.changeSize(5);
        System.out.println("size of q1 (should be increased by 5m): " + q1.size() + "m");
        q1.changeSize(-20);
        System.out.println("size of q1 (should be decreased by 20m): " + q1.size() + "m");
        System.out.println("longitude of q1: " + q1.longitude() + "°");
        System.out.println("latitude of q1: " + q1.latitude() + "°");

        System.out.println("species of r6: " + r6.species());
        System.out.println("size of r6: " + r6.size() + "m");
        r6.changeSize(1);
        System.out.println("size of r6 (should be increased by 1m): " + r6.size() + "m");
        r6.changeSize(-10);
        System.out.println("size of r6 (should be decreased by 10m): " + r6.size() + "m");
        System.out.println("longitude of r6: " + ((QuercusRobur) r6).longitude() + "°");
        System.out.println("latitude of r6: " + ((QuercusRobur) r6).latitude() + "°");

    }

    // Following static methods test the replaceability of the subtypes

    //(Pre): t != NULL
    //(Post): Prints the attributes of the tree
    public static void testTree(Tree t) {
        System.out.println("species: " + t.species());
        System.out.println("size: " + t.size() + "m");
    }

    //(Pre): d != NULL
    //(Post): Prints the attributes of the domestic tree
    public static void testDomestic(Domestic d) {
        System.out.println("species: " + d.species());
        System.out.println("size: " + d.size() + "m");
        System.out.println("longitude: " + d.longitude() + "°");
        System.out.println("latitude: " + d.latitude() + "°");
        System.out.println("is Domestic: " + d.testIsDomestic());
        System.out.println("is tree: " + d.testIsTree());
    }

    //(Pre): c != NULL
    //(Post): Prints the attributes of the continental climate tree
    public static void testContinentalClimate(ContinentalClimate c) {
        System.out.println("species: " + c.species());
        System.out.println("size: " + c.size() + "m");
        System.out.println("incidence " + c.incidence());
        System.out.println("is ContinentalClimate: " + c.testIsContintentalClimate());
        System.out.println("is tree: " + c.testIsTree());
    }

    //(Pre): f != NULL
    //(Post): Prints the attributes of the fagaceae tree
    public static void testFagaceae(Fagaceae f) {
        System.out.println("species: " + f.species());
        System.out.println("size: " + f.size() + "m");
        System.out.println("is Fagaceae: " + f.testIsFagaceae());
        System.out.println("is tree: " + f.testIsTree());
    }

    //(Pre): d != NULL
    //(Post): Prints the attributes of the light demanding tree
    public static void testLightDemanding(LightDemanding d) {
        System.out.println("species: " + d.species());
        System.out.println("size: " + d.size() + "m");
        System.out.println("is LightDemanding: " + d.testIsLightDemanding());
        System.out.println("is tree: " + d.testIsTree());
    }

    //(Pre): q != NULL
    //(Post): Prints the attributes of the quercus tree
    public static void testQuercus(Quercus q) {
        System.out.println("species: " + q.species());
        System.out.println("size: " + q.size() + "m");
        System.out.println("incidence: " + q.incidence());
        System.out.println("is Quercus: " + q.testIsQuercus());
        System.out.println("is Fagaceae: " + q.testIsFagaceae());
        System.out.println("is ContinentalClimate: " + q.testIsContintentalClimate());
        System.out.println("is tree: " + q.testIsTree());
    }

    /*
    Allocation of responsibilities:
        Daniel Vercimak:   designed project structure; implemented Domestic, ContinentalClimate,
                           CarpinunusBetulus, FagusSylvatica; added test for CarpinunusBetulus;
                           added comments Domestic, ContinentalClimate,
                           CarpinunusBetulus, FagusSylvatica;
        Maximilian Gaber:  designed project structure; implemented tree, lightDemanding, fagaceae;
                           added test for FagusSylvatica; added comments for tree, lightDemanding, fagaceae
        Nico Lehegzek:     designed project structure; implemented Quercus, QuercusPatraea and QuercusRobur;
                           added tests for QuercusPatraea and QuercusRobur; added the static methods in main;
                           added comments why Quercus, QuercusPatraea and QuercusRobur aren't subtypes of other
                           interfaces/classes; added conditions in most of the classes for methods and object
                           variables
     */
}