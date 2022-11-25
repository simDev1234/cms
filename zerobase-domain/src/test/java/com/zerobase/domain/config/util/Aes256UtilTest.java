package com.zerobase.domain.config.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Aes256UtilTest {

    @Test
    void encrypt() {
        String encrypt = Aes256Util.encrypt("Hello World");
        Assertions.assertEquals(Aes256Util.decrypt(encrypt), "Hello World");
    }

}