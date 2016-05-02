import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import org.sql2o.*;

public class Task {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private static ArrayList<Task> instances = new ArrayList<Task>();
  private int id;
  private int category_id;

  public Task(String description, int category_id) {
    this.description = description;
    this.category_id = category_id;
    // completed = false;
    // createdAt = LocalDateTime.now();
    instances.add(this);
    // category_id = 0;
    // id = instances.size();
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public static void clear() {
    instances.clear();
  }

  public int getId() {
    return id;
  }

  public int getCategoryId() {
    return category_id;
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
    return task;
    }
  }

  @Override
  public boolean equals(Object otherTask) {
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description, category_id) VALUES (:description, :category_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("category_id", this.category_id)
        .executeUpdate()
        .getKey();
    }
  }
}
