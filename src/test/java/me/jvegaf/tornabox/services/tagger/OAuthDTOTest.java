package me.jvegaf.tornabox.services.tagger;

import org.awaitility.Durations;
import org.junit.jupiter.api.Test;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class OAuthDTOTest {


    @Test
    void checkResponseOfValidToken() {
        OAuthDTO oAuthDTO = OAuthDTO.create("Ft6g7cATZCDhbYVncycwMPUw7D8L9W","36000");

        assertTrue(oAuthDTO.isValid());
    }

    @Test
    void checkResponseOfInvalidToken() {
        OAuthDTO oAuthDTO = OAuthDTO.create("Ft6g7cATZCDhbYVncycwMPUw7D8L9W","1");

        await().atMost(Durations.TWO_SECONDS).untilAsserted(() -> assertFalse(oAuthDTO.isValid()));

    }

}
