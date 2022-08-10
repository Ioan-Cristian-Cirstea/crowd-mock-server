package ro.esolutions.crowdmockserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String UUID;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private long createdDate;
    @Column(nullable = false)
    private long expireDate;
    @OneToOne
    @JoinColumn(name = "userUUID", referencedColumnName = "UUID")
    private CrowdUser crowdUser;

    public Token(String token, long createdDate, long expireDate, CrowdUser crowdUser) {
        this.token = token;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.crowdUser = crowdUser;
    }
}
