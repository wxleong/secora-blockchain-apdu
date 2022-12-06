package com.github.wxleong.apdu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandApduTest {

    @Test
    public void testChangePinApdu() {
        byte[] currentPin = new byte[] { (byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF };
        byte[] newPin = new byte[] { (byte)0xBE, (byte)0xEF, (byte)0xCA, (byte)0xFE };
        byte[] expectedApdu = new byte[] { (byte)0x00, (byte)0x42, (byte)0x00, (byte)0x00,
                (byte)0x0A, (byte)0x04, (byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF,
                (byte)0x04, (byte)0xBE, (byte)0xEF, (byte)0xCA, (byte)0xFE, (byte)0x00 };

        ChangePinApdu changePinApdu = new ChangePinApdu(currentPin, newPin);
        Assertions.assertArrayEquals(expectedApdu, changePinApdu.toBytes());
    }
}
