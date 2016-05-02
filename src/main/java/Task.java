import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import org.sql2o.*;

public class Task {
  private String description;
  private boolean mCompleted;
  private LocalDateTime mCreatedAt;
  private static ArrayList<Task> instances = new ArrayList<Task>();
  private int id;

  public Task(String description) {
    this.description = description;
    mCompleted = false;
    mCreatedAt = LocalDateTime.now();
    instances.add(this);
    id = instances.size();
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return mCompleted;
  }

  public LocalDateTime getCreatedAt() {
    return mCreatedAt;
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

  public static Task find(int id) {
    try {
      return instances.get(id - 1);
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }
}
