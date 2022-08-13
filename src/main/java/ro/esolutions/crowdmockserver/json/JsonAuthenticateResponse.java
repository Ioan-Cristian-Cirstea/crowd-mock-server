package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.entities.Token;

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

    public JsonAuthenticateResponse(String username) {
        this.token = UUID.randomUUID().toString();
        this.user = new JsonUser(username);
        this.link = new JsonLink("user", username);
        this.created_date = System.currentTimeMillis();
        this.expire_date = this.created_date + JsonAuthenticateResponse.EXPIRATION_DURATION_HOURS * 8600 * 1000;
    }

    public JsonAuthenticateResponse(String username, @NotNull Token token) {
        this.token = token.getToken();
        this.user = new JsonUser(username);
        this.link = new JsonLink("user", username);
        this.created_date = token.getCreatedDate();
        this.expire_date = token.getExpireDate();
    }
}
