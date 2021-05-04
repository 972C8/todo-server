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
        this.priority = Priority.valueOf(priority);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}