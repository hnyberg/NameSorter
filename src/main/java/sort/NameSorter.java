package sort;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;


/**
* NameSorter
* by Hannes Nyberg
* 2018-09-02
*
*/

public class NameSorter{

  private static final String FOLDER_STRUCTURE = "/src/main/resources/file/";
  private static final String FILENAME = "names.json";

  public static void main(String[] args){

    //  Read arguments
    boolean sortedByLetter = false;
    boolean filteredInAscendingOrder = false;
    boolean filteredByEvenFrequency = false;
    boolean filteredByOddFrequency = false;
    boolean filteredByName = false;
    String nameToFilter = "";

    for (String arg : args){
      if (arg.equals("-a")){
        sortedByLetter = true;
        filteredInAscendingOrder = true;
        System.out.println("Sorting by letter, ascending");
      }
      if (arg.equals("-d")){
        sortedByLetter = true;
        filteredInAscendingOrder = false;
        System.out.println("Sorting by letter, descending");
      }
      if (arg.equals("-e")){
        filteredByEvenFrequency = true;
        System.out.println("Filtering by frequency, even");
      }
      if (arg.equals("-o")){
        filteredByOddFrequency = true;
        System.out.println("Filtering by frequency, odd");
      }
      if (arg.contains("name=") && arg.length() >= 4){
        filteredByName = true;
        nameToFilter = arg.substring(5, arg.length());
        nameToFilter = nameToFilter.substring(0, 1).toUpperCase() + nameToFilter.substring(1);
        System.out.println("Filtering by name, " + nameToFilter);
      }
    }

    //  Locate file to read data from
    String fileURL = System.getProperty("user.dir") + FOLDER_STRUCTURE + FILENAME;
    System.out.println("Reading file from " + fileURL);
    JSONObject currentPerson;
    String firstname, surname, gender;
    Map<String, Integer> firstnames = new HashMap<String, Integer>();
    Map<String, Integer> surnames = new HashMap<String, Integer>();

    //  Save file data to managable maps/lists
    try {

      //  Load data (names) from file
      FileReader reader = new FileReader(fileURL);
      JSONObject json = (JSONObject) new JSONParser().parse(reader);
      JSONArray jsonArrayOfAllNames = (JSONArray) json.get("names");
      Iterator<JSONObject> jsonIterator = jsonArrayOfAllNames.iterator();

      //  Loop list of names, and save them
      while(jsonIterator.hasNext()){
        currentPerson = (JSONObject) jsonIterator.next();
        firstname = (String) currentPerson.get("firstname");
        surname = (String) currentPerson.get("surname");

        //  Count firstnames
        if (firstnames.containsKey(firstname)){
          firstnames.replace(firstname, (int) firstnames.get(firstname) + 1);
        } else {
          firstnames.put(firstname, 1);
        }

        //  Count surnames
        if (surnames.containsKey(surname)){
          surnames.replace(surname, (int) surnames.get(surname) + 1);
        } else {
          surnames.put(surname, 1);
        }
      }
    } catch(FileNotFoundException fe){ fe.printStackTrace(); }
    catch(Exception e){ e.printStackTrace(); }

    //  Filter name
    if (filteredByName){
      firstnames = getNamesFilteredByName(firstnames, nameToFilter);
      surnames = getNamesFilteredByName(surnames, nameToFilter);
    }

    //  Filter odd / even frequency
    if (filteredByEvenFrequency){
      firstnames = getNamesFilteredByFrequency(firstnames, filteredByEvenFrequency);
      surnames = getNamesFilteredByFrequency(surnames, filteredByEvenFrequency);
    }
    if (filteredByOddFrequency){
      firstnames = getNamesFilteredByFrequency(firstnames, !filteredByOddFrequency);
      surnames = getNamesFilteredByFrequency(surnames, !filteredByOddFrequency);
    }

    //  Sort by name if asked, else by decreasing frequency (default behavior)
    if (sortedByLetter){
      firstnames = getNamesSortedByFirstLetter(firstnames, filteredInAscendingOrder);
      surnames = getNamesSortedByFirstLetter(surnames, filteredInAscendingOrder);
    } else {
      firstnames = getNamesSortedByFrequency(firstnames);
      surnames = getNamesSortedByFrequency(surnames);
    }

    //  Print results
    System.out.println();
    System.out.println("Printing firstnames ");
    for(String key: firstnames.keySet()){
      System.out.println(key + " - " + firstnames.get(key));
    }

    System.out.println();
    System.out.println("Printing surnames ");
    for(String key: surnames.keySet()){
      System.out.println(key + " - " + surnames.get(key));
    }
  }

  private static HashMap getNamesSortedByFrequency(Map<String, Integer> map){
    HashMap newMap = new LinkedHashMap();
    List list = new LinkedList(map.entrySet());

    //  Sort list
    Collections.sort(list, new Comparator() {
      public int compare(Object o1, Object o2) {
        return ((Comparable) ((Map.Entry) (o2)).getValue())
        .compareTo(((Map.Entry) (o1)).getValue());
      }
    });

    //  Save to map
    for (Iterator it = list.iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry) it.next();
      newMap.put(entry.getKey(), entry.getValue());
    }
    return newMap;
  }

  private static TreeMap getNamesSortedByFirstLetter(Map<String, Integer> map, boolean ascendingOrder){
    TreeMap newMap;
    if (ascendingOrder){
      newMap = new TreeMap(Collections.reverseOrder());
      newMap.putAll(map);
    } else {
      newMap = new TreeMap(map);
    }
    return newMap;
  }

  private static HashMap getNamesFilteredByFrequency(Map<String, Integer> map, boolean filterEven){
    HashMap newMap = new LinkedHashMap();
    int value;

    for(String key: map.keySet()){
      value = (int) map.get(key);
      if (value % 2 == 0){
        if (filterEven){
          newMap.put(key, value);
        }
      } else {
        if (!filterEven){
          newMap.put(key, value);
        }
      }
    }
    return newMap;
  }

  private static HashMap getNamesFilteredByName(Map<String, Integer> map, String nameToFilter){
    HashMap newMap = new LinkedHashMap();
    for(String key: map.keySet()){
      if (key.equals(nameToFilter)){
        newMap.put(nameToFilter, map.get(key));
      }
    }
    return newMap;
  }

}
