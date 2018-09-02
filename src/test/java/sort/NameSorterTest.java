package sort;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import org.json.simple.*;
import org.junit.Test;
import org.junit.Before;
import java.util.Iterator;
import java.util.Map;

public class NameSorterTest {

  private static String fileArgument = "file=test.json";
  private static String nameArgument = "name=Sundberg";
  private static String ascendArgument = "-a";
  private static String evenArgument = "-e";

  //  Open different datafile
  @Test
  public void aDifferentTestDataFileCanBeOpened(){
    String[] args = new String[] {fileArgument};
    NameSorter nameSorter = new NameSorter(args);
    assertNotNull(nameSorter.getFirstnames());
  }

  //  Check correct order of frequency
  @Test
  public void hannesIsMostCommonFirstName(){
    String[] args = new String[] {fileArgument};
    NameSorter nameSorter = new NameSorter(args);
    Iterator it = nameSorter.getFirstnames().entrySet().iterator();
    String mostPopularName = (String) ((Map.Entry) it.next()).getKey();
    assertEquals(mostPopularName, "Hannes");
  }

  //  Filter a name
  @Test
  public void filteringSundbergSurnameShowsOnlySundberg(){
    String[] args = new String[] {fileArgument, nameArgument};
    NameSorter nameSorter = new NameSorter(args);
    Iterator it = nameSorter.getSurnames().entrySet().iterator();
    String surname = (String) ((Map.Entry) it.next()).getKey();
    assertEquals(surname, "Sundberg");
  }

  //  Sort ascending order
  @Test
  public void runeIsLeastFirstCommonName(){
    String[] args = new String[] {fileArgument, ascendArgument};
    NameSorter nameSorter = new NameSorter(args);
    Iterator it = nameSorter.getFirstnames().entrySet().iterator();
    String firstname = (String) ((Map.Entry) it.next()).getKey();
    assertEquals(firstname, "Rune");
  }

  //  Filter even frequency
  @Test
  public void filteringForEvenFrequencyOnlyGivesEvenResults(){
    String[] args = new String[] {fileArgument, evenArgument};
    NameSorter nameSorter = new NameSorter(args);
    boolean allResultsAreEven = true;
    int modulus;
    Iterator it = nameSorter.getFirstnames().entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        modulus = ((int) pair.getValue()) % 2;
        if (modulus != 0){
          allResultsAreEven = false;
        }
        it.remove();
    }
    assertTrue(allResultsAreEven);
  }

}
