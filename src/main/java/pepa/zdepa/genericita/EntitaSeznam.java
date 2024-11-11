package pepa.zdepa.genericita;

import java.lang.reflect.Array;
import java.util.Arrays;

public class EntitaSeznam<T extends Entita> {
    public final T[] elements;

    public EntitaSeznam(int size, Class<T> elementClass) {
        this.elements = (T[]) Array.newInstance(elementClass, size);
    }

    public T findId(int id) {
        for (T element : elements) {
            if (element.getId() == id)
                return element;
        }

        return null;
    }

    /**
     * Lazy sort tzv
     */
    public void sort() {
        Arrays.sort(elements, 0, 4);
    }

    public void print() {
        for (T element : elements) {
            System.out.println(element);
        }
    }

    public T[] getElements() {
        return elements;
    }
}
