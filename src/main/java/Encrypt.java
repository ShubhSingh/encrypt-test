import com.example.securedjson.CryptoModule;
import com.example.securedjson.EncryptionService;
import com.example.securedjson.PasswordCryptoContext;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Encrypt {

    private static final String CIPHER_NAME = "DES/CBC/NoPadding";
    private static final String KEY_NAME = "PBKDF2WithHmacSHA1";

    private final Logger log = LogManager.getLogger(this.getClass());

    public boolean testEncryptDecrypt() {
        Signup request =
                Signup.builder()
                        .id("s1")
                        .userInfo(
                                UserInfo.builder()
                                        .userName("user1")
                                        .password("Wow!123")
                                        .address("Chennai TN")
                                        .build())
                        .build();
        ObjectMapper mapper = getMapper("SecretPass@!@#123");

        String json = null;
        try {
            Instant start = Instant.now();
            json = mapper.writeValueAsString(request);
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();

            log.info("Encrypt to json time taken in millis: {}", timeElapsed);

            log.info("Encrypted signup request to json: {}", json);
        } catch (final JsonProcessingException e) {
            System.out.println("JSON writing error during convert to json string");
            return false;
        }

        Signup response = null;
        try {
            if (json != null) {
                Instant start = Instant.now();
                response = mapper.readValue(json, Signup.class);
                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();

                log.info("Decrypt to object time taken in millis: {}", timeElapsed);

                log.info("Decrypted json back to signup object: " + response);
            }
        } catch (final IOException e) {
            System.out.println("JSON reading error during convert to signup object");
            return false;
        }

        return true;
    }

    public ObjectMapper getMapper(String encryptionSecret) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Enable encryption using jackson crypto
        // TODO: Find a way to rotate SecretKey and try 3 different keys
        EncryptionService encryptionService =
                new EncryptionService(mapper, new PasswordCryptoContext(encryptionSecret, encryptionSecret,
                        CIPHER_NAME, KEY_NAME, 5000, 64, "DES"));

        mapper.registerModule(new CryptoModule().addEncryptionService(encryptionService));
        return mapper;
    }
}
