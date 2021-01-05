package nl.makeitwork.Showmaster.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author ****
 */

@Entity
public class VerificatieToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer verificatieTokenId;

    private String token;

    private Date expiryDate;

    private Boolean tokenGebruikt;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificatieToken() {
        this.token = UUID.randomUUID().toString();
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.tokenGebruikt = false;
    }

    public Boolean getTokenGebruikt() {
        return tokenGebruikt;
    }

    public void setTokenGebruikt(Boolean tokenGebruikt) {
        this.tokenGebruikt = tokenGebruikt;
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Integer getVerificatieTokenId() {
        return verificatieTokenId;
    }

    public void setVerificatieTokenId(Integer verificatieTokenId) {
        this.verificatieTokenId = verificatieTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}