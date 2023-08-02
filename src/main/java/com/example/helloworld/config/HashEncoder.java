package com.example.helloworld.config;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

@Configuration
public class HashEncoder {

    @Bean
    public HashEncoder hashFunction() {
        return new HashEncoder();
    }

    public String generateMurmurHashHex(String input) {
        // Gerar o hash MurmurHash de 32 bits
        int hashValue = Hashing.murmur3_32().hashString(input, StandardCharsets.UTF_8).asInt();

        // Converter o valor inteiro para a representação hexadecimal
        String hashHex = Integer.toHexString(hashValue);

        // Certificar-se de que o hashHex tenha 8 caracteres, preenchendo com zeros à esquerda, se necessário
        hashHex = String.format("%8s", hashHex).replace(' ', '0');

        return hashHex;
    }

}
