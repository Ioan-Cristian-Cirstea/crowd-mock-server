package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Token findAllByCrowdUser_UUID(String userUUID);

    void deleteAllByExpireDateIsLessThanEqual(long dateDeletion);
}
