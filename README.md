[![Github actions](https://github.com/wxleong/secora-blockchain-apdu/actions/workflows/main.yml/badge.svg)](https://github.com/wxleong/secora-blockchain-apdu/actions)

# Introduction

The secora-blockchain-apdu library is a Java package designed to handle the APDU commands of [Infineon's SECORA™ Blockchain](https://www.infineon.com/cms/en/product/security-smart-card-solutions/secora-security-solutions/secora-blockchain-security-solutions/) and [Blockchain Security 2Go starter kit](https://www.infineon.com/cms/en/product/evaluation-boards/blockchainstartkit/). It is based on the project [BlockchainSecurity2Go-Android@085aa39](https://github.com/Infineon/BlockchainSecurity2Go-Android/tree/085aa3914235ab0e262b73323403e626f399d53f).

# Library Import Guide

For evaluation, you may use the [JitPack](https://jitpack.io/#wxleong/secora-blockchain-apdu) package repository to import the library into your project.

# User Guide for Android Application

In order to incorporate the library into an Android application, it is necessary to wrap the [*IsoDep*](https://developer.android.com/reference/android/nfc/tech/IsoDep) object within the *NfcTransceiver* interface. Here is an example:
```
import android.nfc.tech.IsoDep;
import com.github.infineon.NfcTranceiver;

import java.io.IOException;

/**
 * Wraps IsoDep tag into NfcTranceiver interface.
 * (this wrapper is used so that the com.github.infineon package
 * doesn't have any Android dependencies)
 */
public class IsoTagWrapper implements NfcTranceiver {

    private final IsoDep isoDep;

    private IsoTagWrapper(IsoDep isoDep) {
        this.isoDep = isoDep;
    }

    /**
     * Create wrapper of given IsoDep tag.
     *
     * @param isoDep
     * @return wrapper instance
     */
    public static IsoTagWrapper of(IsoDep isoDep) {
        return new IsoTagWrapper(isoDep);
    }

    /**
     * Sends a command APDU to the NFC card and returns the received response APDU.
     *
     * @param commandApdu command APDU to send
     * @return bytes received as response
     * @throws IOException in case of communication errors
     */
    @Override
    public byte[] transceive(byte[] commandApdu) throws IOException {

        if (!isoDep.isConnected()) {
            isoDep.connect();
        }
        return isoDep.transceive(commandApdu);
    }
}
```

Example of how to read the public key from a Secora Blockchain contactless card:
```
public void readPublicKey() {
    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    IsoDep isoDep = IsoDep.get(tag); // ISO 14443-4 Type A & B
    String pubkey = com.github.infineon
                       .NfcUtils.readPublicKeyOrCreateIfNotExists(
                           IsoTagWrapper.of(isoDep), 1
                       ).getPublicKeyInHexWithoutPrefix();
    isoDep.close();
}
```

# Examples

- https://github.com/wxleong/secora-blockchain-walletconnect

# License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.