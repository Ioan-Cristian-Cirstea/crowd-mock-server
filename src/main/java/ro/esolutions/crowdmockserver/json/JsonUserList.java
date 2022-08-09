package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Component
@ToString
public class JsonUserList {
    private final String expand = "users";
    private final List<JsonUserSummary> users = new ArrayList<>();

    public JsonUserList(List<String> usernames) {
        for (String username: usernames) {
            users.add(new JsonUserSummary(username));
        }
        System.out.println(users);
    }
}
