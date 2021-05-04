import java.util.HashMap;

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

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", mailAddress='" + mailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void addTodoItem(TodoItem todoItem) {
        this.todoList.put(todoItem.getId(), todoItem);
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
}
