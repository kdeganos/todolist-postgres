import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class CategoryTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/todo_list_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteTasksQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  @Test
  public void category_instantiatesCorrectly_true() {
    Category myCategory = new Category("Household chores");
    assertEquals(true, myCategory instanceof Category);
  }

  @Test
  public void getName_categoryInstantiatesWithName_String() {
    Category myCategory = new Category("Household chores");
    assertEquals("Household chores", myCategory.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Category.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Category firstCategory = new Category("Household chores");
    Category secondCategory = new Category("Household chores");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    assertTrue(Category.all().get(0).equals(myCategory));
  }

  @Test
  public void save_assignsIdToObject() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Category savedCategory = Category.all().get(0);
    assertEquals(myCategory.getId(), savedCategory.getId());
  }

  @Test
  public void find_findCategoryInDatabase_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Category savedCategory = Category.find(myCategory.getId());
    assertTrue(myCategory.equals(savedCategory));
  }
}
  // @After
  // public void tearDown() {
  //   Category.clear();
  //   Task.clear();
  // }
  //
  // @Test
  // public void category_instantiatesCorrectly_true() {
  //   Category testCategory = new Category("Home");
  //   assertEquals(true, testCategory instanceof Category);
  // }
  //
  // @Test
  // public void getName_categoryInstantiatesWithName_Home() {
  //   Category testCategory = new Category("Home");
  //   assertEquals("Home", testCategory.getName());
  // }
  //
  // @Test
  // public void all_returnsAllInstancesOfCategory_true() {
  //   Category firstCategory = new Category("Home");
  //   Category secondCategory = new Category("Work");
  //   assertTrue(Category.all().contains(firstCategory));
  //   assertTrue(Category.all().contains(secondCategory));
  // }
  //
  // @Test
  // public void clear_emptiesAllCategoriesFromList_0() {
  //   Category testCategory = new Category("Home");
  //   Category.clear();
  //   assertEquals(Category.all().size(), 0);
  // }
  //
  // @Test
  // public void getId_categoriesInstantiateWithAnId_1() {
  //   Category testCategory = new Category("Home");
  //   assertEquals(1, testCategory.getId());
  // }
  //
  // @Test
  // public void find_returnsCategoryWithSameId_secondCategory() {
  //   Category firstCategory = new Category("Home");
  //   Category secondCategory = new Category("Work");
  //   assertEquals(Category.find(secondCategory.getId()), secondCategory);
  // }
  //
  // @Test
  // public void find_returnsNullWhenNoCategoryFound_null() {
  //   assertTrue(Category.find(999) == null);
  // }
  //
  // @Test
  // public void getTasks_initiallyReturnsEmptyList_ArrayList() {
  //   Category testCategory = new Category("Home");
  //   assertEquals(0, testCategory.getTasks().size());
  // }
  //
  // @Test
  // public void addTask_addsTaskToList_true() {
  //   Category testCategory = new Category("Home");
  //   Task testTask = new Task("Mow the lawn");
  //   testCategory.addTask(testTask);
  //   assertTrue(testCategory.getTasks().contains(testTask));
  // }
// }
