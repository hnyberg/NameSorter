package sort;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

public class NameSorterTest {

  private NameSorter sorter = new NameSorter();

  @Test
  public void firstTest(){
    assertThat("Hello world", containsString("Hello"));
  }
  
}
