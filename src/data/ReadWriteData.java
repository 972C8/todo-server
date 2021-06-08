package data;

import com.google.gson.reflect.TypeToken;
import server.Account;
import server.TodoItem;
import utility.BaseReadWrite;

import java.util.HashMap;

/**
 * Reads and writes data from the given HashMap to a specified json file.
 * Based on the code from GsonUtility.jar (https://github.com/972C8/GsonUtility)
 * This separate package was created by us for the last project and is used in this project again
 *
 * @param <T>
 */
public class ReadWriteData<T> extends BaseReadWrite<HashMap<Integer, Account>> {

    @Override
    public void write(HashMap<Integer, Account> o) throws Exception {
        try {
            if (o != null) {
                writeToFile(o);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public HashMap<Integer, Account> read() throws Exception {
        try {
            String dataContent = readFromFile();

            return gson.fromJson(dataContent, new TypeToken<HashMap<Integer, Account>>() {
            }.getType());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Overwrites static nextId to ensure correct id tracking after import.
     * <p>
     * Static nextIds of Account and TodoItem class are overwritten with highest found id +1 in the imported json file.
     *
     * @param accounts the hashmap containing all accounts
     */
    public void overwriteNextIds(HashMap<Integer, Account> accounts) {
        int accountId = 0;
        int todoId = 0;

        for (Account account : accounts.values()) {
            //Stores the highest accountId of all iterated accounts
            if (account.getId() > accountId) {
                accountId = account.getId();
            }
            //Stores the highest todoId of all iterated todoLists
            for (TodoItem todo : account.getTodoList().values()) {
                if (todo.getId() > todoId) {
                    todoId = todo.getId();
                }
            }
        }
        //Set the id to the highest id + 1
        Account.setNextId(++accountId);
        TodoItem.setNextId(++todoId);
    }
}
