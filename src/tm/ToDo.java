package tm;

public class ToDo {
    private String id;
    private String description;
    private String user_id;

    public ToDo(String id, String description, String user_id) {
        this.id = id;
        this.description = description;
        this.user_id = user_id;
    }


    public ToDo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return description;
    }


}
