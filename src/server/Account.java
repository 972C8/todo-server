package server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Account {

    //Static to have same variable over all objects
    //Tracks the account ids to ensure a unique identifier
    private static int nextId = 1;
    private static final Logger logger = Logger.getLogger("");

    private int id;
    private String mailAddress;
    private byte[] salt;
    private String password;
    private HashMap<Integer, TodoItem> todoList = new HashMap<>();

    public Account(String mailAddress, String password) {
        //Increment id for every new account
        this.id = nextId++;

        this.mailAddress = mailAddress;
        this.salt = generateSalt();
        this.password = hashPassword(password);
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Sets new password of account.
     * Refreshes salt and hashes given password.
     *
     * @param password passwordString
     */
    public void setPassword(String password) {
        // Refresh salt
        this.salt = generateSalt();

        this.password = hashPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Reset the static nextId of Account
     */
    public static void resetNextId() {
        Account.nextId = 1;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", mailAddress='" + mailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Check if mailAddress is correct
     *
     * @param mailAddress is checked
     * @return true if mailAddress is correct
     */
    public boolean validMailAddress(String mailAddress) {
        return getMailAddress().equals(mailAddress);
    }

    /**
     * Check if password is correct
     *
     * @param password is checked
     * @return true if password is correct
     */
    public boolean validPassword(String password) {
        String hashedPassword = hashPassword(password);

        return (hashedPassword.equals(this.getPassword()));
    }

    /**
     * Add a new todoItem to the account
     *
     * @param todoItem is the item added to the account
     */
    public void addTodoItem(TodoItem todoItem) {
        todoList.put(todoItem.getId(), todoItem);
    }

    /**
     * Return the TodoItem of the given id
     *
     * @param todoId of the item
     * @return TodoItem of the provided id
     */
    public TodoItem getTodoItem(int todoId) {
        return todoList.get(todoId);
    }

    /**
     * Delete the TodoItem of the given id
     *
     * @param todoId of the item
     * @return true if item was deleted
     */
    public boolean deleteTodoItem(int todoId) {
        if (todoList.get(todoId) != null) {
            todoList.remove(todoId);
            return true;
        }
        return false;
    }

    /**
     * Return the account's todolist
     *
     * @return a HashMap containing the account's todos
     */
    public HashMap<Integer, TodoItem> getTodoList() {
        return todoList;
    }

    /**
     * Return a String[] response of all ids (keys in hashmap)
     *
     * Result is in format of {"1", "2", ..} for all ids
     *
     * @return String[] of keysets in hashmap
     */
    public String[] getTodoIds() {
        return todoList.keySet()
                .stream()
                .flatMap(entry -> Stream.of(entry.toString()))
                .toArray(String[]::new);
    }

    /**
     * Hashes a given password string with SHA-512 and a random salt sequence.
     *
     * @param password password as string
     * @return SHA-256 hashed password
     */
    public String hashPassword(String password) {
        MessageDigest digest;
        byte[] encodedHash = {};

        try {
            digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt);
            encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            logger.severe(e.toString());
        }

        // convert array of bytes to hexadecimal string
        return byteArrayToHexString(encodedHash);
    }

    /**
     * Generates random sequence for hashing the password.
     * Prevents the usage of "rainbow tables" (comparing with hash-tables)
     *
     * @return salt
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }

    /**
     * Converts array of bytes to hexadecimal string
     *
     * @param hash byte array of hash
     * @return hexadecimal string of hash
     */
    public static String byteArrayToHexString(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
