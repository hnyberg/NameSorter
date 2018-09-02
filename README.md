# NameSorter
A program sorting names from a json file

To use, open a terminal in the NameSorter main folder, containing the pom.xml file.
There, first run "mvn package" to let program know your exact data-file URL.
Then, run "java -jar target/NameSorter-0.1.0.jar".
The program will first scan through a "names.json" file in the "/src/main/resources/file" folder, containing a json object with persons' firstname, surname and gender. Then it will print out all first names, then surnames, both sorted by descending frequency (with frequency listen beside names).


To further sort or filter the lists, use the following flags/arguments when running the program:

**-d**	    sort names by alphabetical order  

**-a**	    sort names by reverse alphabetical order  

**-e**	    filter out names with even frequency/occurrence  

**-o**	    filter out names with odd frequency/occurrence  

**name=**	  filter out a specific name

**file=**	  chose to read data from another file (include full name, i.e. "test.json")

Examples:

java -jar NameSorter-0.1.0.jar name=Ida file=test.json

java -jar NameSorter-0.1.0.jar -e -a
