package pepa.zdepa.genericita;

public interface Zasobnik<T> {
    void add(T object);

    T remove();
}
