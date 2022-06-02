package io.rently.mailerservice.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.rently.mailerservice.configs.MailerControllerTestConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = MailerControllerTestConfigs.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class MailerControllerTest {

    @Autowired
    public MockMvc mvc;
    public static final String URL = "/api/v1/emails/dispatch";
    public static final String SECRET = "HelloDarknessMyOldFriend";
    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS384;
    public static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(SECRET.getBytes(), ALGORITHM.getJcaName());
    public String token;

    @BeforeEach
    public void generateValidJwt() {
        Date expiredDate = new Date(System.currentTimeMillis() + 60000L);
        token = Jwts.builder()
                .setIssuedAt(expiredDate)
                .setExpiration(expiredDate)
                .signWith(ALGORITHM, SECRET_KEY_SPEC)
                .compact();
    }

    @Test
    void handleDispatch_greetingsType_sendGreetings() throws Exception {
//        Map<String, Object> body = new HashMap<>();
//        body.put("type", MailType.GREETINGS);
//        body.put("name", "my name");
//        body.put("email", "my email");
//        String jsonBody = new ObjectMapper().writeValueAsString(body);
//
//        ResultActions response = mvc.perform(post(URL)
//                .content(jsonBody).contentType(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token));
//
//        String responseJson = response.andReturn().getResponse().getContentAsString();
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty());
//        assert responseJson.contains(String.valueOf(HttpStatus.OK.value()));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.content").doesNotExist());
    }

    @Test
    void handleDispatch_newListingType_sendNewListingNotification() {

    }

    @Test
    void handleDispatch_deleteAccountType_sendAccountDeletionNotification() {

    }

    @Test
    void handleDispatch_genericNotificationType_sendNotification() {

    }

    @Test
    void handleDispatch_listingDeletionType_sendListingDeletionNotification() {

    }

    @Test
    void handleDispatch_updateListingType_sendUpdateListingNotification() {

    }

    @Test
    void handleDispatch_devErrorType_sendReportToDevs() {

    }

    @Test
    void handleErrorDispatch() {
    }
}