package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class SignatureGenerator{
    String toSign;
    RSAPrivateKey rsaPrivateKey = null;
    public SignatureGenerator
            (String[] signatureInfo){
          toSign = toSign(signatureInfo);
    }

    public static RSAPrivateKey getPKFromString(String privatekeyPEM) throws IOException, GeneralSecurityException {
        privatekeyPEM = privatekeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privatekeyPEM = privatekeyPEM.replace("-----END PRIVATE KEY-----", "");
        byte[] encoded = Base64.getDecoder().decode(privatekeyPEM);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    public String sign(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, InvalidKeyException,SignatureException, UnsupportedEncodingException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes("UTF-8"));
        return new String(Base64.getDecoder().decode(sign.sign()), "UTF-8");
    }

    private String toSign(String[] signatureData){
        String concatenatedData = "";
        for (int i = 0; i < signatureData.length; i++) {
            concatenatedData = concatenatedData+signatureData[i];
        }

        return concatenatedData;
    }
    public String readFile(){
        String privateKey = "";
        try{
            File privateKeyFile = new File("/../src/privatekey.pem");
            Scanner  privateKeyContent = new Scanner(privateKeyFile);
            while(privateKeyContent.hasNextLine()){
                privateKey = privateKeyContent.nextLine();
            }
            privateKeyContent.close();
        }catch (FileNotFoundException e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
        return privateKey;
    }
}
