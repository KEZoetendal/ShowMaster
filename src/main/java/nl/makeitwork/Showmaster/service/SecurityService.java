package nl.makeitwork.Showmaster.service;

/**
 * @author ****
 */

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}