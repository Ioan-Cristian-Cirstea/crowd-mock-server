package ro.esolutions.crowdmockserver.utilities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
