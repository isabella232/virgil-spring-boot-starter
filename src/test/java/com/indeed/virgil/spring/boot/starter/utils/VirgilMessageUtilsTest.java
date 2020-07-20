package com.indeed.virgil.spring.boot.starter.utils;

import com.indeed.virgil.spring.boot.starter.util.VirgilMessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class VirgilMessageUtilsTest {

    private VirgilMessageUtils virgilMessageUtils;

    @BeforeEach
    void setup() {
        virgilMessageUtils = new VirgilMessageUtils();
    }

    @Nested
    class generateFingerprint {

        @Test
        void shouldReturnFingerprint() {
            //Arrange
            final byte[] body = "".getBytes();
            final MessageProperties messageProperties = new MessageProperties();
            messageProperties.setTimestamp(new Date(1234567L));
            final Message msg = new Message(body, messageProperties);

            //Act
            final String result = virgilMessageUtils.generateFingerprint(msg);

            //Assert
            assertThat(result).isEqualTo("eca11ca575db74bb0a450d18832b5f41");
        }

        @Test
        void shouldReturnFingerprintForMessageWithNullBody() {
            //Arrange
            final MessageProperties messageProperties = new MessageProperties();
            messageProperties.setTimestamp(new Date(1234567L));
            final Message msg = new Message(null, messageProperties);

            //Act
            final String result = virgilMessageUtils.generateFingerprint(msg);

            //Assert
            assertThat(result).isEqualTo("eca11ca575db74bb0a450d18832b5f41");
        }

        @Test
        void shouldReturnFingerprintForMessageWithNoProperties() {
            //Arrange
            final byte[] body = "asfdafdas".getBytes();
            final Message msg = new Message(body, null);

            //Act
            final String result = virgilMessageUtils.generateFingerprint(msg);

            //Assert
            assertThat(result).isEqualTo("81f2dd8b7cf2ef9e7b3210c6741766b7");
        }

        @Test
        void shouldReturnValidFingerprintWhenMessageIsNull() {
            //Arrange

            //Act
            final String result = virgilMessageUtils.generateFingerprint(null);

            //Assert
            assertThat(result).isEqualTo("d41d8cd98f00b204e9800998ecf8427e");
        }
    }
}
