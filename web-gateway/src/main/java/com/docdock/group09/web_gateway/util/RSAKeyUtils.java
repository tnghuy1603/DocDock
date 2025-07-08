package com.docdock.group09.web_gateway.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAKeyUtils {

    private final ResourceLoader resourceLoader;

    public RSAKeyUtils(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public PrivateKey getPrivateKey(String privateKeyPath) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String keyContent = loadKeyContent(privateKeyPath);
        keyContent = keyContent
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(keyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public PublicKey getPublicKey(String publicKeyPath) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String keyContent = loadKeyContent(publicKeyPath);
        keyContent = keyContent
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(keyContent);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private String loadKeyContent(String path) throws IOException {
        if (path.startsWith("classpath:")) {
            Resource resource = resourceLoader.getResource(path);
            try (InputStream inputStream = resource.getInputStream()) {
                return new String(inputStream.readAllBytes());
            }
        } else {
            return Files.readString(Paths.get(path));
        }
    }
}
