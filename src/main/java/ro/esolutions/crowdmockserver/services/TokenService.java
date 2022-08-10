package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.entities.Token;
import ro.esolutions.crowdmockserver.repositories.TokenRepository;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveToken(String token, long createdDate, long expireDate, CrowdUser crowdUser) {
        tokenRepository.save(new Token(token, createdDate, expireDate, crowdUser));
    }
}
