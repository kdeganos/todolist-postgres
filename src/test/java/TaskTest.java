import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;


public class TaskTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/todo_list_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteTasksQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  @Rule
  public ClearRule clearRule = new ClearRule();

  @Test
  public void Task_instantiatesCorrectly_true() {
    Task myTask = new Task("Mow the lawn", 1, "12/01/2016");
    assertEquals(true, myTask instanceof Task);
  }

  @Test
  public void Task_instantiatesWithDescription_String() {
    Task myTask = new Task("Mow the lawn", 1, "12/01/2016");
    assertEquals("Mow the lawn", myTask.getDescription());
  }
  //
  // @Test
  // public void isCompleted_isFalseAfterInstantiation_false() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(false, myTask.isCompleted());
  // }
  //
  // @Test
  // public void getCreatedAt_instantiatesWithCurrentTime_today() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
  // }

  // @Test
  // public void all_returnsAllInstancesOfTask_true() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Buy groceries");
  //   assertTrue(Task.all().contains(firstTask));
  //   assertTrue(Task.all().contains(secondTask));
  // }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Task firstTask = new Task("Mow the lawn", 1, "12/01/2016");
    Task secondTask = new Task("Mow the lawn", 1, "12/01/2016");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Task myTask = new Task("Mow the lawn", 1, "12/01/2016");
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("Mow the lawn", 1, "12/01/2016");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(),savedTask.getId());
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn", 1, "12/01/2016");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task myTask = new Task("Mow the lawn", myCategory.getId(), "12/01/2016");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }

  @Test
  public void getTasks_retrievesAllTasksFromDatabase_tasksList() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task firstTask = new Task("Mow the lawn", myCategory.getId(), "12/01/2016");
    firstTask.save();
    Task secondTask = new Task("Do the dishes", myCategory.getId(), "12/01/2016");
    secondTask.save();
    Task[] tasks = new Task [] { firstTask, secondTask };
    assertTrue(myCategory.getTasks().containsAll(Arrays.asList(tasks)));
  }

  // @Test
  // public void sort_sortsByDueDate() {
  //   Task firstTask = new Task("Mow the lawn", 1, "12/01/2016");
  //   firstTask.save();
  //   Task secondTask = new Task("Do the dishes", 1, "11/02/2016");
  //   secondTask.save();
  //
  //   Task savedTask = Task.find(1);
  //   assertEquals("Do the dishes", savedTask.getDescription());
  // }

  // @Test
  // public void clear_emptiesAllTasksFromArrayList_0() {
  //   Task myTask = new Task("Mow the lawn");
  //   Task.clear();
  //   assertEquals(Task.all().size(), 0);
  // }

  // @Test
  // public void getId_tasksInstantiateWithAnID_1() {
  //   Task myTask = new Task("Mow the lawn");
  //   assertEquals(1, myTask.getId());
  // }

  // @Test
  // public void find_returnsTaskWithSameId_secondTask() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Buy groceries");
  //   assertEquals(Task.find(secondTask.getId()), secondTask);
  // }

  // @Test
  // public void find_returnsNullWhenNoTaskFound_null() {
  //   assertTrue(Task.find(999) == null);
  // }
}
