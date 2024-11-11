package pepa.zdepa.genericita;

public class Entita implements Comparable<Entita> {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Entita o) {
        return -1 * (this.id - o.id);
    }
}
