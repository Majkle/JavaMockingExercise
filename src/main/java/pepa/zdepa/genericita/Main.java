package pepa.zdepa.genericita;

public class Main {

    public static void main(String[] args) {
//        new ZasobnikImpl<>(69);
//        new ZasobnikImpl<>(69);
//        new ZasobnikImpl<>(69);
//        new ZasobnikImpl<>(69);
//        new ZasobnikImpl<>(69);
//        ZasobnikImpl<Integer> zasobnikImpl = new ZasobnikImpl<>(69);
//        zasobnikImpl.add(4);
//        zasobnikImpl.add(5);
//
//        System.out.println(zasobnikImpl.remove());
//        System.out.println(zasobnikImpl.instanceCounter);
//
//        ZasobnikImpl<String> zasobnikImpl2 = new ZasobnikImpl<>(69);
//        zasobnikImpl2.add("Pepa");
//        zasobnikImpl2.add("Z");
//        zasobnikImpl2.add("Depa");
//        zasobnikImpl2.add("asd");
//        zasobnikImpl2.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//
//        System.out.println(zasobnikImpl2.find(s -> s.startsWith("P")));
//
//
//         new ZasobnikImpl<>(69, AccessOperationType.class);
//         new ZasobnikImpl<>(69, Integer.class);

        EntitaSeznam<Uzivatel> uzivatelEntitaSeznam = new EntitaSeznam<>(10, Uzivatel.class);
        uzivatelEntitaSeznam.elements[0] = new Uzivatel();
        uzivatelEntitaSeznam.elements[1] = new Uzivatel();
        uzivatelEntitaSeznam.elements[2] = new Uzivatel();
        uzivatelEntitaSeznam.elements[3] = new Uzivatel();

        System.out.println(uzivatelEntitaSeznam.findId(2));

        uzivatelEntitaSeznam.sort();
        uzivatelEntitaSeznam.print();

        print(uzivatelEntitaSeznam.elements);

    }

    private static <T extends Entita> void print(T[] list) {
        for (T t : list) {
            if (t == null)
                continue;
            System.out.println(t.id);
        }
    }
}
