package server;

import data.ReadWriteData;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Model {
    //Hashmap in format <AccountId, Account Object>
    private HashMap<Integer, Account> accounts = new HashMap<>();

    //Hashmap in format <TOKEN, AccountId>
    private HashMap<String, Integer> tokens = new HashMap<>();

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

                    //Add token session to hashmap of current tokens
                    //TODO: use actual token instead of mail address
                    tokens.put(account.getMailAddress(), account.getId());

                    //Return the generated token together with the response
                    //TODO: Use actual token instead of mail address
                    String[] token = {account.getMailAddress()};
                    return new Response(true, token);
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
        //Only two parameters allowed
        if (requestData == null || requestData.length > 2) {
            return new Response(false);
        }

        try {
            String token = requestData[0];
            String newPassword = requestData[1];

            if (token == null || newPassword == null) {
                return new Response(false);
            }

            //Verify token and set password
            if (verifyToken(token)) {
                //Get account from provided token
                Account account = getAccountByToken(token);

                //Set new password
                account.setPassword(newPassword);
                return new Response(true);
            }
            return new Response(false);
        } catch (Exception e) {
            return new Response(false);
        }
    }

    /**
     * Get account from provided token
     *
     * @param token
     * @return account associated with token
     */
    private Account getAccountByToken(String token) {
        Integer accountId = tokens.get(token);
        return accounts.get(accountId);
    }

    /**
     * Return true if token provided by client is valid
     *
     * @param token
     * @return true if token is valid
     */
    private boolean verifyToken(String token) {
        return token != null && tokens.containsKey(token);
    }

    /**
     * Create a new to do item and add it to the account
     *
     * @param requestData the content of the item to be created
     * @return true if to do item is created and added successfully
     */
    public Response createToDo(String[] requestData) {
        //Only four parameters allowed (fourth is due date which is ignored)
        if (requestData == null || requestData.length > 4) {
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
