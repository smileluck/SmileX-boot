package top.zsmile.auth;

import org.junit.Test;
import sun.security.provider.SecureRandom;

import java.util.Random;

public class RandomTest {

    @Test
    public void SecureRandomTest() {
        SecureRandom secureRandom = new SecureRandom();

        Random random = new Random();
    }
}
