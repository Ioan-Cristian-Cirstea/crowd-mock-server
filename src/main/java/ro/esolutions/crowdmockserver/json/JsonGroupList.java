package ro.esolutions.crowdmockserver.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;
import ro.esolutions.crowdmockserver.json.JsonGroupSummary;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

@Getter
@NoArgsConstructor
@Component
@ToString
public class JsonGroupList {
    private final String expand = "group";
    private final List<JsonGroupSummary> groups = new ArrayList<>();

    public JsonGroupList(List<CrowdGroup> crowdGroupList) {
        for (CrowdGroup crowdGroup: crowdGroupList)
            groups.add(new JsonGroupSummary(crowdGroup));
    }
}

