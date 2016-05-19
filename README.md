# NoSQLDatabase

The problem with in-memory data is that it is not persistent. So when the program stops running
the data is lost. For persistence we will use two files. 
The first file called “commands.txt” will contain all the operations done on the database that
change it state. The file will contain text, not binary. Each operation will be on a separate line.
The operation needs to be saved to the file when the operation is being performed, preferably
just before the operation is done.
The second file called “dbSnapshot.txt” will contain the snapshot of the database. When a
snapshot of the database is taken the current data in the database is saved to this file. The
previous contents of this file are removed. Also the contents of the file “commands.txt” are removed.

The database needs four methods related to snapshot:
snapshot()
snapshot(File commands, File snapshot)
Both methods perform the snapshot as defined above. The first method uses the default
names for the files.
class methods:
recover()
recover(File commands, File snapshot)
These methods recover the database from the two saved files. The first method uses the
default names for the files. These methods first read the snapshot file to recover the last
snapshot of the database. Then they read the operations from the commands file and execute
the operations in the order they are in the file.
Reactive
Since the database is in memory it does not have to be separate from an application, like
SQLite. So we could get some data and use it in the program. If the data in the database
changes we want our program to reflect the current state of the database. For this we will add
more methods to the database class.
getCursor(String key)
Where X is one of Int, Double, String, Array, or Object. Returns a Cursor object on the
value stored at the key in the database. Throws an exception if the key is not in the database.
Cursor
A cursor holds a value from the database. The cursor knows the current state of the value.
When the value changes in the database the cursor knows the new state of the value. The
cursor class needs the following methods:
getX()
Where X is one of Int, Double, String, Array, or Object. Returns the item in the cursor. If the
item at the given index is not of type X throw an exception. Returns the item as the type indicated
by X. So getInt will return an int, getString will return a String.
get()
Returns the item in the cursor. The value is returned as a Java object.
addObserver(Observer o)
Adds an observer to the cursor. Each cursor holds a value from the database. When that
value in the database changes all the observers on the cursor are notified.
removeObserver(Observer o)
Removes the given observer from the cursor.
Some examples:
database.put(“bar”, 10);
Cursor data = database.getCursor(“bar”);
data.value() // returns 10
database.put(“bar”, 20);
data.value() // returns 20
database.put(“bar”, 10);
Cursor data = database.get(“bar”);
data.addObserver(foo);
database.put(“bar”, 20);
//So here foo gets notified of the change.
Of course the data at “bar” could be more complex. If the value at “bar” are changed in a
transaction and the transaction is aborted the cursor and its observers could receive multiple
updates.
