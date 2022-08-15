package ro.esolutions.crowdmockserver.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class JsonNewGroupRequest {
    private String name;
    private String description;
    //### Why is this field required?
    private String type = "GROUP";
}
