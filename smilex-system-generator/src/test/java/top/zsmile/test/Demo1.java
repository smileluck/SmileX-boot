package top.zsmile.test;

import java.util.HashSet;
import java.util.Set;

public class Demo1 {
    public static void main(String[] args) {
        Set<Short> set = new HashSet<>();
        for (short i = 0; i < 5; i++) {
            set.add(i);
//            short t = (short) (i - 1);
//            set.remove(t);
        }
        set.add((short) 1);

        System.out.println(set.size());
    }
}
