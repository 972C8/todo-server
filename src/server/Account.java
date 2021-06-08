package server;

import java.util.HashMap;
import java.util.stream.Stream;

public class Account {

    //Static to have same variable over all objects
    //Tracks the account ids to ensure a unique identifier
    private static int nextId = 1;

    private int id;
    private String mailAddress;
    private String password;
    private HashMap<Integer, TodoItem> todoList = new HashMap<>();

    public Account(String mailAddress, String password) {
        //Increment id for every new account
        this.id = nextId++;

        this.mailAddress = mailAddress;
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    /**
     * Set static next id.
     *
     * @param nextId
     */
    public static void setNextId(int nextId) {
        Account.nextId = nextId;
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
        return getPassword().equals(password);
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
     * <p>
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
}
