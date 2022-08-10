package ro.esolutions.crowdmockserver.utilities;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.services.TokenService;

@RequiredArgsConstructor
@Component
public class TokenUtility {
    private final TokenService tokenService;
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOldTokens() {
        System.out.println("Deleting old tokens...");
        tokenService.deleteOldTokens();
    }
}
