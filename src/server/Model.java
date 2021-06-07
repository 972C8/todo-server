package server;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Model {

    private HashMap<Integer, Account> accounts = new HashMap<>();

    //TODO: obsolete, remove
    private static Account currentAccount;

    private final Logger logger = Logger.getLogger("");

    /**
     * Create new account from provided credentials.
     *
     * @param requestData is the parameters provided
     */
    public Response createLogin(String[] requestData) {
        try {
            //Only two parameters allowed
            if (requestData == null || requestData.length > 2) {
                return new Response(false);
            }
            String mailAddress = requestData[0];
            String password = requestData[1];

            //TODO: Fails if name already taken, or invalid
            //TODO: Check mail and password for correctness (regex), return false if incorrect

            Account account = new Account(mailAddress, password);
            accounts.put(account.getId(), account);

            return new Response(true);
        } catch (Exception e) {
            return new Response(false);
        }
    }

    /**
     * Login into existing account
     *
     * @param requestData is the login parameters provided
     * @return Response with generated token
     */
    public Response login(String[] requestData) {
        //Only two parameters allowed
        if (requestData == null || requestData.length > 2) {
            return new Response(false);
        }
        String mailAddress = requestData[0];
        String password = requestData[1];

        try {
            for (Map.Entry<Integer, Account> accountEntry : accounts.entrySet()) {
                Account account = accountEntry.getValue();
                if (account.validMailAddress(mailAddress) && account.validPassword(password)) {

                    //TODO: Return token instead
                    setCurrentAccount(account);
                    return new Response(true);
                }
            }
            return new Response(false);
        } catch (Exception e) {
            return new Response(false);
        }

    }

    /**
     * Logout from server
     *
     * @return
     */
    public boolean logout() {
        //TODO: logout functionality
        return true;
    }

    /**
     * Set a new current account
     *
     * @param newAccount is the new current account
     */
    private void setCurrentAccount(Account newAccount) {
        currentAccount = newAccount;
    }

    public HashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    /**
     * Reset the accounts HashMap
     */
    public void resetAccounts() {
        accounts = new HashMap<>();
    }

    /**
     * Change password of the current account
     *
     * @param requestData is the new password
     * @return true if password was changed successfully
     */
    public Response changePassword(String[] requestData) {
        //Only one parameters allowed
        if (requestData == null || requestData.length > 1) {
            return new Response(false);
        }
        String newPassword = requestData[0];

        try {
            //TODO: use token instead of currentAccount
            if (currentAccount != null) {
                //TODO: Exception handling, return false
                currentAccount.setPassword(newPassword);
                return new Response(true);
            }
            return new Response(false);
        } catch (Exception e) {
            return new Response(false);
        }
    }

    /**
     * Create a new to do item and add it to the account
     *
     * @param requestData the content of the item to be created
     * @return true if to do item is created and added successfully
     */
    public Response createToDo(String[] requestData) {
        //Only three parameters allowed
        if (requestData == null || requestData.length > 3) {
            return new Response(false);
        }
        String title = requestData[0];
        String priority = requestData[1];
        String description = requestData[2];

        try {
            //TODO: use token instead of currentAccount
            if (currentAccount != null) {
                TodoItem item = new TodoItem(title, description, priority);
                currentAccount.addTodoItem(item);

                //Add id of created TodoItem to response
                String[] response = {String.valueOf(item.getId())};
                return new Response(true, response);
            }
            //TODO: Exception handling, return false
            return new Response(false);
        } catch (Exception e) {
            return new Response(false);
        }
    }

    /**
     * Return the current account's TodoItem with the provided id
     *
     * @param requestData of the item
     * @return the current account's TodoItem of the provided id
     */
    public Response getToDo(String[] requestData) {
        //Only three parameters allowed
        if (requestData == null || requestData.length > 1) {
            return new Response(false);
        }
        String todoId = requestData[0];

        try {
            //TODO: use token instead of currentAccount
            if (currentAccount != null) {
                TodoItem item = currentAccount.getTodoItem(Integer.parseInt(todoId));
                return new Response(true, item.getResponse());
            }
            return new Response(false);

        } catch (Exception e) {
            return new Response(false);
        }
    }

    /**
     * Delete the current account's TodoItem with the provided id
     *
     * @param todoId of the item
     * @return true if item was deleted
     */
    public boolean deleteToDo(int todoId) {
        if (currentAccount != null) {
            return currentAccount.deleteTodoItem(todoId);
        }
        return false;
    }

    /**
     * Return the current account's todolist
     *
     * @return a HashMap containing the account's todos
     */
    public HashMap<Integer, TodoItem> listToDos() {
        if (currentAccount != null) {
            return currentAccount.getTodoList();
        }
        return null;
    }
}
