package top.zsmile;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class test {
    public static void main(String[] args) {
        String uuid = get32UUID();
        System.out.println(uuid);
    }
    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return (new UUID(random.nextLong(), random.nextLong())).toString().replace("-", "");
    }
}
