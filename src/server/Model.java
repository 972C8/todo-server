package server;

import data.ReadWriteData;

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
    public Response logout() {
        //TODO: writeData on logout?
        writeData();
        return new Response(true);
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
        //Only one parameters allowed
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
     * @param requestData of the item
     * @return true if item was deleted
     */
    public Response deleteToDo(String[] requestData) {
        //Only one parameters allowed
        if (requestData == null || requestData.length > 1) {
            return new Response(false);
        }
        String todoId = requestData[0];

        try {
            //TODO: use token instead of currentAccount
            if (currentAccount != null) {
                //TODO: check if id exists for this user
                boolean itemDeleted = currentAccount.deleteTodoItem(Integer.parseInt(todoId));
                if (itemDeleted) {
                    return new Response(true);
                }
            }
            return new Response(false);
        } catch (Exception e) {
            return new Response(false);
        }
    }

    /**
     * Return the current account's todolist
     *
     * @return a HashMap containing the account's todos
     */
    public Response listToDos() {
        try {
            //TODO: use token instead of currentAccount
            if (currentAccount != null) {
                String[] todoIds = currentAccount.getTodoIds();
                if (todoIds.length > 0) {
                    return new Response(true, todoIds);
                }
            }
            return new Response(false);
        } catch (Exception e) {
            return new Response(false);
        }
    }

    /**
     * Write all data (todos + accounts) to specified json file
     * Uses the ReadWriteData class, which extends BaseReadWrite.java from GsonUtility.jar
     */
    public void writeData() {
        try {
            ReadWriteData<HashMap<Integer, Account>> readWriteData = new ReadWriteData<>();
            readWriteData.setLocation(System.getProperty("user.dir") + "/data.json");

            //Write all data (todos) of all accounts to json
            readWriteData.write(getAccounts());

        } catch (Exception e) {
            logger.severe("Exception occured while importing data");
        }
    }
}
