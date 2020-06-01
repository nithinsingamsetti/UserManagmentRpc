package com.genecrusher.utils;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class HashUtil {

    public static String hashMe(String payload) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        return Hex.toHexString(digestSHA3.digest(payload.getBytes()));
    }
}
