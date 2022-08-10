package ro.esolutions.crowdmockserver.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JsonUser {
    private String name;
}
