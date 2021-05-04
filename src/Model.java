import java.util.HashMap;
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
            if (account.validMailAddress(mailAddress) && account.validPassword(password)) {
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

    protected Account getCurrentAccount() {
        if (currentAccountId != 0) {
            return accounts.get(currentAccountId);
        }
        return null;
    }

    protected HashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    /**
     * Change password of the current account
     *
     * @param newPassword is the new password
     * @return true if password was changed successfully
     */
    protected boolean changePassword(String newPassword) {
        Account currentAccount = getCurrentAccount();
        if (currentAccount != null) {
            currentAccount.setPassword(newPassword);
        }
        //TODO: Exception handling, return false
        return true;
    }

    /**
     * Create a new to do item and add it to the account
     *
     * @param title       of the to do item
     * @param priority    of the to do item
     * @param description of the to do item
     * @return true if to do item is created and added successfully
     */
    protected boolean createToDo(String title, String priority, String description) {
        Account currentAccount = getCurrentAccount();
        if (currentAccount != null) {
            currentAccount.addTodoItem(new TodoItem(title, description, priority));
        }

        //TODO: Exception handling, return false
        return true;
    }

    /**
     * Return the current account's TodoItem with the provided id
     *
     * @param todoId of the item
     * @return the current account's TodoItem of the provided id
     */
    protected TodoItem getToDo(int todoId) {
        Account currentAccount = getCurrentAccount();
        if (currentAccount != null) {
            return currentAccount.getTodoItem(todoId);
        }
        return null;
    }
}
