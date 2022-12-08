# The secora-blockchain-apdu Library

A Java package to handle Infineon Secora Blockchain APDU translation layer. This is based on the project [BlockchainSecurity2Go-Android](https://github.com/Infineon/BlockchainSecurity2Go-Android).

# Library Import Guide

Maven *pom.xml*:
```
<dependency>
  <groupId>com.github.infineon</groupId>
  <artifactId>secora-blockchain-apdu</artifactId>
  <version>x.y.z</version>
</dependency>
```

Gradle Groovy *build.gradle*:
```
dependencies {
    implementation 'com.github.infineon:secora-blockchain-apdu:x.y.z'
}
```

# Library User Guide in Android Application

Wraps *IsoDep* tag into *NfcTranceiver* interface. This wrapper is used so that the `com.github.infineon` package doesn't have any Android dependencies:
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

Example of readPublicKeyOrCreateIfNotExists():
```
    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    IsoDep isoDep = IsoDep.get(tag); // ISO 14443-4 Type A & B
    String pubkey = com.github.infineon
                        .NfcUtils.readPublicKeyOrCreateIfNotExists(
                            IsoTagWrapper.of(isoDep), 1
                        ).getPublicKeyInHexWithoutPrefix();
    isoDep.close();
```

# License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.