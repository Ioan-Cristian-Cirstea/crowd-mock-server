package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Component
@NoArgsConstructor
@ToString
public class JsonAuthenticateResponse {
    private final static long EXPIRATION_DURATION_HOURS = 24;
    private final String expand = "user";
    private String token;
    private JsonUser user;
    private JsonLink link;
    private long created_date;
    private long expire_date;

    public JsonAuthenticateResponse(String  username) {
        this.token = UUID.randomUUID().toString();
        this.user = new JsonUser(username);
        this.link = new JsonLink(username);
        this.created_date = System.currentTimeMillis();
        this.expire_date = this.created_date + JsonAuthenticateResponse.EXPIRATION_DURATION_HOURS * 8600 * 1000;
    }
}
