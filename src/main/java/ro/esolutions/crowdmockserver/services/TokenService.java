package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.entities.Token;
import ro.esolutions.crowdmockserver.repositories.TokenRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token findTokenByUserUUID(String userUUID) {
        return tokenRepository.findAllByCrowdUser_UUID(userUUID);
    }
    public void saveToken(String token, long createdDate, long expireDate, CrowdUser crowdUser) {
        tokenRepository.save(new Token(token, createdDate, expireDate, crowdUser));
    }

    public void deleteOldTokens() {
        tokenRepository.deleteAllByExpireDateIsLessThanEqual(System.currentTimeMillis());
    }

    public void deleteTokensByUserUUID(String userUUID) {
        tokenRepository.deleteAllByCrowdUser_UUID(userUUID);
    }
}
