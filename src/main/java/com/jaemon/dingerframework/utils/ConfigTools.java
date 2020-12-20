/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingerframework.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.jaemon.dingerframework.utils.DingerUtils.base64ToByteArray;
import static com.jaemon.dingerframework.utils.DingerUtils.byteArrayToBase64;


/**
 * Config Tools
 *
 * @author Jaemon
 * @since 1.0
 */
public class ConfigTools {

    private ConfigTools() {}

    private static final String DEFAULT_PRIVATE_KEY_STRING = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK3UkOuP3zsAHap4ImPXuhVskiKGm3PXcM/Xo02CbrmBYHMb0bGuByesSpXhep6OPWGdrIW6LvDnS6ivyVrsDeBd82QS6KmfEMRVJoqeqVZy3mphstAOtHK4OGtABFogXw2RfDRunGq5GW9shF4DWFhrWWsBK/UGs7kJrVir6v77AgMBAAECgYEApnOMTbSXmmSTA6BDtf1ll4w+JsdkZbmfsbYYDmleY03KsI6r7grpmQi25uxhQSCNEEMuZ2MP/ehNH3ssQV8WLPU/wobm/2qaRALtD02sQRefhP95SVvOrgry+ElxawWQyUoyLSyH1bpoCI9J2orx/tnrQQi9nYDeR2+aH4S7JpkCQQDZDr/1dvTqX+ZOmlBF3lI6P0xKLklHinSp3anmW4tRyxA/W9gguZ9JlGFVKP9Ml2YxH3dBVktkfPITloj0smltAkEAzQRtpyUPx3HqjnveZ0CIDeI7j6hbLGGANZ5Gm99lWPVzNMPv6bFcJ2TEXzNq3fqE3Dmv6veSxV3eUOrzHOVxBwJBAKTpD+7u8iUft1sA4vwybUbT0KKLiCFSkFB+mRbrdm4uWanJnes/HEZK9ag9/bmzTXEE9xYs+hre0w0O0f8XjgECQCckEUs36CtLtFw/idZsm40LBBQJMF7ovnF+JjzcCZ1SPwxz2/nhwpZCxrrmNiDrEzJ4UP2rBnpn0WnhcUizBUECQFSU/s7cLaMSjlGmacDDf1r+u8cD2rKyWQWmEiXI155L4gSz2s/Pu5u1X8fcKqlGvtJFfSUP68w1e0x7mPGFohc=";
    public static final String DEFAULT_PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt1JDrj987AB2qeCJj17oVbJIihptz13DP16NNgm65gWBzG9GxrgcnrEqV4Xqejj1hnayFui7w50uor8la7A3gXfNkEuipnxDEVSaKnqlWct5qYbLQDrRyuDhrQARaIF8NkXw0bpxquRlvbIReA1hYa1lrASv1BrO5Ca1Yq+r++wIDAQAB";

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("secret is empty");
            return;
        }

        String password = args[0];
        String[] arr = genKeyPair(1024);
        System.out.println("privateKey: " + arr[0]);
        System.out.println("decryptKey: " + arr[1]);
        String encrypt = encrypt(arr[0], password);
        System.out.println("encrypt tokenId: " + encrypt);
        String decrypt = decrypt(arr[1], encrypt);
        System.out.println("decrypt tokenId:" + decrypt);
    }

    public static String decrypt(String cipherText) throws Exception {
        return decrypt((String) null, cipherText);
    }

    public static String decrypt(String publicKeyText, String cipherText)
            throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyText);

        return decrypt(publicKey, cipherText);
    }

    public static PublicKey getPublicKey(String publicKeyText) {
        if (publicKeyText == null || publicKeyText.length() == 0) {
            publicKeyText = DEFAULT_PUBLIC_KEY_STRING;
        }

        try {
            byte[] publicKeyBytes = base64ToByteArray(publicKeyText);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
                    publicKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get public key", e);
        }
    }


    public static String decrypt(PublicKey publicKey, String cipherText)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            // 因为 IBM JDK 不支持私钥加密, 公钥解密, 所以要反转公私钥
            // 也就是说对于解密, 可以通过公钥的参数伪造一个私钥对象欺骗 IBM JDK
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, fakePrivateKey);
        }

        if (cipherText == null || cipherText.length() == 0) {
            return cipherText;
        }

        byte[] cipherBytes = base64ToByteArray(cipherText);
        byte[] plainBytes = cipher.doFinal(cipherBytes);

        return new String(plainBytes);
    }

    public static String encrypt(String plainText) throws Exception {
        return encrypt((String) null, plainText);
    }

    public static String encrypt(String key, String plainText) throws Exception {
        if (key == null) {
            key = DEFAULT_PRIVATE_KEY_STRING;
        }

        byte[] keyBytes = base64ToByteArray(key);
        return encrypt(keyBytes, plainText);
    }

    public static String encrypt(byte[] keyBytes, String plainText)
            throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
        PrivateKey privateKey = factory.generatePrivate(spec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            //For IBM JDK, 原因请看解密方法中的说明
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            Key fakePublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, fakePublicKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedString = byteArrayToBase64(encryptedBytes);

        return encryptedString;
    }

    public static byte[][] genKeyPairBytes(int keySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[][] keyPairBytes = new byte[2][];

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
        gen.initialize(keySize, new SecureRandom());
        KeyPair pair = gen.generateKeyPair();

        keyPairBytes[0] = pair.getPrivate().getEncoded();
        keyPairBytes[1] = pair.getPublic().getEncoded();

        return keyPairBytes;
    }

    public static String[] genKeyPair(int keySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[][] keyPairBytes = genKeyPairBytes(keySize);
        String[] keyPairs = new String[2];

        keyPairs[0] = byteArrayToBase64(keyPairBytes[0]);
        keyPairs[1] = byteArrayToBase64(keyPairBytes[1]);

        return keyPairs;
    }

}