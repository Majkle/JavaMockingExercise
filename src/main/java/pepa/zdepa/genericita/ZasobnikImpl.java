package pepa.zdepa.genericita;

import java.util.function.Predicate;

public class ZasobnikImpl<T> implements Zasobnik<T> {

    public static int instanceCounter = 0;

    private final T[] stack;
    private int itemCount;

    public ZasobnikImpl(int size) {
        this.stack = (T[]) new Object[size];
        this.itemCount = 0;

        instanceCounter++;
    }

    public ZasobnikImpl(int size, Class<T> clazz) {
        this(size);

        if (!clazz.isEnum())
            throw new RuntimeException(clazz + " is not an enum");
    }

    @Override
    public void add(T object) {
        stack[itemCount++] = object;
    }

    @Override
    public T remove() {
        return stack[--itemCount];
    }

    public boolean find(Predicate<T> predicate) {
        for (T t : stack) {
            if (predicate.test(t))
                return true;
        }
        return false;
    }
}
