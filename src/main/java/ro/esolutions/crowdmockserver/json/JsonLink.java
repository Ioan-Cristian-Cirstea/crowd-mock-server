package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@ToString
@Getter
public class JsonLink {
    private String href;
    private final String ref = "self";

    public JsonLink(final String type, final String name) {
        this.href = String.format("http://crowd.esolutions.ro/crowd/rest/usermanagement/1/%s?%sname=", type, type, name);
    }
}
