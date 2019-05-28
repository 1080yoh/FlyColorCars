package pes.ralter.flycolorcars.model;

public class Car {
    int idColored;
    int idFlyColored;

    public Car(int idColored, int idFlyColored) {
        this.idColored = idColored;
        this.idFlyColored = idFlyColored;
    }

    public int getIdColored() {
        return idColored;
    }

    public int getIdFlyColored() {
        return idFlyColored;
    }
}
