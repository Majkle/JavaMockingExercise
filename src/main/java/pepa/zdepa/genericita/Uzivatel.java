package pepa.zdepa.genericita;

public class Uzivatel extends Entita {

    private static int autoIndex = 0;

    public Uzivatel() {
        id = autoIndex++;
    }

    @Override
    public String toString() {
        return "Uzivatel{" +
               "id=" + id +
               "} " + super.toString();
    }
}
