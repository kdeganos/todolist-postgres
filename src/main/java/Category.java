import java.util.List;
import org.sql2o.*;
// import java.util.ArrayList;


public class Category {
  private String name;
  // private static ArrayList<Category> instances = new ArrayList<Category>();
  private int id;
  // private ArrayList<Task> mTasks;

  public Category(String name) {
    this.name = name;
    // instances.add(this);
    // id = instances.size();
    // mTasks = new ArrayList<Task>();
  }

  public String getName() {
    return name;
  }

  // public static ArrayList<Category> all() {
  //   return instances;
  // }

  // public static void clear() {
  //   instances.clear();
  // }

  public int getId() {
    return id;
  }

  public static List<Category> all() {
    String sql = "SELECT id, name FROM Categories";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory) {
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return this.getName().equals(newCategory.getName()) &&
             this.getId() == newCategory.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Categories(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Categories where id=:id";
      Category category = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return category;
    }
  }

  // public static Category find(int id) {
  //   try {
  //     return instances.get(id - 1);
  //   } catch (IndexOutOfBoundsException e) {
  //     return null;
  //   }
  // }
  //
  // public ArrayList<Task> getTasks() {
  //   return mTasks;
  // }
  //
  // public void addTask(Task task) {
  //   mTasks.add(task);
  // }
}
