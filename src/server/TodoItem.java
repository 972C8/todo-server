package server;

public class TodoItem {

    //Static to have same variable over all objects
    //Tracks the item ids to ensure a unique identifier
    private static int nextId = 1;

    private int id;
    private String title;
    private String description;
    private Priority priority;

    public enum Priority {
        LOW("Low"), MEDIUM("Medium"), HIGH("High");

        private final String priority;

        Priority(String priority) {
            this.priority = priority;
        }

        public String getPriority() {
            return priority;
        }
    }

    public TodoItem(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = Priority.valueOf(priority.toUpperCase());

        //Increment id for every new item
        this.id = nextId++;
    }

    public int getId() {
        return id;
    }

    /**
     * Set the static nextId of TodoItem
     *
     * @param nextId
     */
    public static void setNextId(int nextId) {
        TodoItem.nextId = nextId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }


    /**
     * Return response in String[] format
     *
     * @return response as String[]
     */
    public String[] getResponse() {
        return new String[]{
                String.valueOf(getId()),
                getTitle(),
                getPriority().toString(),
                getDescription()
        };
    }
}