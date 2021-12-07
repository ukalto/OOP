public interface Arbeitskopf {
    // (Nach): Retuniert immer falsch wenn die Klasse ein Hackschnitzel ist und immer wahr wenn die Klasse eine Schneide ist
    boolean istSchneide();

    // (Nach): Retuniert immer wahr wenn die Klasse ein Hackschnitzel ist und immer falsch wenn die Klasse eine Schneide ist
    boolean istHackschnitzel();
}
