import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Model {

    private HashMap<Integer, Account> accounts = new HashMap<>();
    private static int currentAccountId = 0;

    /**
     * Create new account from provided credentials.
     *
     * @param mailAddress is the username
     * @param password    is the password
     */
    protected boolean createLogin(String mailAddress, String password) {
        //TODO: Check mail and password for correctness (regex), return false if incorrect
        Account account = new Account(mailAddress, password);
        accounts.put(account.getId(), account);
        return true;
    }

    protected boolean login(String mailAddress, String password) {
        for (Map.Entry<Integer, Account> accountEntry : accounts.entrySet()) {
            Account account = accountEntry.getValue();
            if (mailAddress.equals(account.getMailAddress()) && password.equals(account.getPassword())) {
                //TODO: do actual login
                setCurrentAccountId(account.getId());
                return true;
            }
        }
        return false;
    }

    protected boolean logout() {
        //TODO: logout functionality
        return true;
    }

    /**
     * Set a new current account id
     *
     * @param newAccountId is the new current account id
     */
    protected void setCurrentAccountId(int newAccountId) {
        currentAccountId = newAccountId;
    }

    private Account getCurrentAccount() {
        if (currentAccountId != 0) {
            return accounts.get(currentAccountId);
        }
        return null;
    }

    /**
     * Change password of the current account
     *
     * @param newPassword is the new password
     * @return true if password was changed successfully
     */
    protected boolean changePassword(String newPassword) {
        Account currentAccount = getCurrentAccount();
        assert currentAccount != null;
        currentAccount.setPassword(newPassword);
        accounts.put(currentAccountId, currentAccount);

        //TODO: Exception handling, return false
        return true;
    }
}
