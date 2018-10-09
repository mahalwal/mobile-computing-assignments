package me.manishmahalwal.android.ass3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/*
* References:
* https://www.simplifiedcoding.net/sqliteopenhelper-tutorial/
* */

public class DatabaseManager extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "QuestionsDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "questions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "title";
    private static final String COLUMN_QUES = "question";
    private static final String COLUMN_ANSWER = "answer";
////    private static final String COLUMN_SALARY = "salary";


    DatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_QUES + " varchar(1000) NOT NULL,\n" +
                "    " + COLUMN_ANSWER + " varchar(5) NOT NULL\n" +
                ");";

        sqLiteDatabase.execSQL(sql);

//        int ret = this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
////        Toast.makeText(this, "Employee Added "+ret, Toast.LENGTH_SHORT).show();

//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
//        this.addQuestion("Question", "Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.", "true");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    /*
     * CREATE OPERATION
     * ====================
     * This is the first operation of the CRUD.
     * This method will create a new employee in the table
     * Method is taking all the parameters required
     *
     * Operation is very simple, we just need a content value objects
     * Inside this object we will put everything that we want to insert.
     * So each value will take the column name and the value that is to inserted
     * for the column name we are using the String variables that we defined already
     * And that is why we converted the hardcoded string to variables
     *
     * Once we have the contentValues object with all the values required
     * We will call the method getWritableDatabase() and it will return us the SQLiteDatabase object and we can write on the database using it.
     *
     * With this object we will call the insert method it takes 3 parameters
     * 1. String -> The table name where the value is to be inserted
     * 2. String -> The default values of null columns, it is null here as we don't have any default values
     * 3. ContentValues -> The values that is to be inserted
     *
     * insert() will return the inserted row id, if there is some error inserting the row
     * it will return -1
     *
     * So here we are returning != -1, it will be true of record is inserted and false if not inserted
     * */

    int addQuestion(String name, String dept, String joiningdate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_QUES, dept);
        contentValues.put(COLUMN_ANSWER, joiningdate);
//        contentValues.put(COLUMN_SALARY, salary);
        SQLiteDatabase db = getWritableDatabase();
        return (int)db.insert(TABLE_NAME, null, contentValues);
    }


    /*
     * READ OPERATION
     * =================
     * Here we are reading values from the database
     * First we called the getReadableDatabase() method it will return us the SQLiteDatabase instance
     * but using it we can only perform the read operations.
     *
     * We are running rawQuery() method by passing the select query.
     * rawQuery takes two parameters
     * 1. The query
     * 2. String[] -> Arguments that is to be binded -> We use it when we have a where clause in our query to bind the where value
     *
     * rawQuery returns a Cursor object having all the data fetched from database
     * */
    Cursor getAllQuestions() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /*
     * UPDATE OPERATION
     * ==================
     * Here we are performing the update operation. The proecess is same as the Create operation.
     * We are first getting a database instance using getWritableDatabase() method as the operation we need to perform is a write operation
     * Then we have the contentvalues object with the new values
     *
     * to update the row we use update() method. It takes 4 parameters
     * 1. String -> It is the table name
     * 2. ContentValues -> The new values
     * 3. String -> Here we pass the column name = ?, the column we want to use for putting the where clause
     * 4. String[] -> The values that is to be binded with the where clause
     * */
    boolean updateQuestion(int id, String ans ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ANSWER, ans);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }


    /*
     * DELETE OPERATION
     * ======================
     *
     * This is the last delete operation.  To delete again we need a writable database using getWritableDatabase()
     * Then we will call the delete method. It takes 3 parameters
     * 1. String -> Table name
     * 2. String -> The where clause passed as columnname = ?
     * 3. String[] -> The values to be binded on the where clause
     * */
    boolean deleteEmployee(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
}
