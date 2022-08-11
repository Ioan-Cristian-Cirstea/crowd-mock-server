package ro.esolutions.crowdmockserver.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.json.JsonUserDetails;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReturnJsonUserDetails {
    private JsonUserDetails jsonUserDetails;
    private HttpStatus httpStatus;
}
