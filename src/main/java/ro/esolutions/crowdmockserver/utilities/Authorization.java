package ro.esolutions.crowdmockserver.utilities;

import lombok.NoArgsConstructor;
import lombok.Getter;
//import org.springframework.stereotype.Component;

import java.util.Base64;

//@Component
@NoArgsConstructor
@Getter
public class Authorization {
    private String type;
    private String username;
    private String password;

    public Authorization(final String authorization) {
        final int blankIndex = authorization.indexOf(' ');
        if (blankIndex == -1) {
            this.type = "";
            this.username = "";
            this.password = "";
            return;
        }
        this.type = authorization.substring(0, blankIndex); 
        String decodedCredentials;
        try {
            decodedCredentials = new String(Base64.getDecoder().decode(authorization.substring(blankIndex + 1)));
        }
        catch (IllegalArgumentException exception) {
            this.type = "";
            this.username = "";
            this.password = "";
            return;
        }
        final int semicolonIndex = decodedCredentials.indexOf(':');
        if (semicolonIndex == -1) {
            this.username = "";
            this.password = "";
            return;
        }
        this.username = decodedCredentials.substring(0, semicolonIndex);
        this.password = decodedCredentials.substring(semicolonIndex + 1);
    }
}
