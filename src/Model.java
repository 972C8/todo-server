import java.util.HashMap;
import java.util.Map;

public class Model {

    private HashMap<Integer, Account> accounts = new HashMap<>();
    private static Account currentAccount;

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
                setCurrentAccount(account);
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
     * Set a new current account
     *
     * @param newAccount is the new current account
     */
    protected void setCurrentAccount(Account newAccount) {
        currentAccount = newAccount;
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
        if (currentAccount != null) {
            return currentAccount.getTodoItem(todoId);
        }
        return null;
    }

    /**
     * Delete the current account's TodoItem with the provided id
     *
     * @param todoId of the item
     * @return true if item was deleted
     */
    protected boolean deleteToDo(int todoId) {
        if (currentAccount != null) {
            return currentAccount.deleteTodoItem(todoId);
        }
        return false;
    }
}
