package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.entities.Application;

@NoArgsConstructor
@Component
@ToString
@Getter
public class JsonApplicationSummary {
    private String name;

    public JsonApplicationSummary(Application application) {
        this.name = application.getName();
    }
}
