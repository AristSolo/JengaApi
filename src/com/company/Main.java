package com.company;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static void main(String[] args){
        String[] toSign =  {"account_number","",""} ;
        SignatureGenerator signature = new SignatureGenerator(toSign);
        String key = signature.readFile();
        try {
            signature.rsaPrivateKey = SignatureGenerator.getPKFromString(key);
            //String base64Signature = signature.sign();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
