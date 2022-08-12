package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.entities.Application;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

@Getter
@NoArgsConstructor
@Component
@ToString
public class JsonApplicationList {
    private final String expand = "applications";
    private final List<JsonApplicationSummary> applications = new ArrayList<>();

    public JsonApplicationList(List<Application> applicationList) {
        for (Application application: applicationList)
            applications.add(new JsonApplicationSummary(application));
    }
}
