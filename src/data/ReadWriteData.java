package data;

import server.Account;
import utility.BaseReadWrite;

import java.util.HashMap;

/**
 * Reads and writes data from the given HashMap to a specified json file.
 * Based on the code from GsonUtility.jar (https://github.com/972C8/GsonUtility)
 *
 * @param <T>
 */
public class ReadWriteData<T> extends BaseReadWrite<HashMap<Integer, Account>> {

    @Override
    public void write(HashMap<Integer, Account> o) throws Exception {
        try {
            writeToFile(o);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public HashMap<Integer, Account> read() throws Exception {
        return null;
    }
}
