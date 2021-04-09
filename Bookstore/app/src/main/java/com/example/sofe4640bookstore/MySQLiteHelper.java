package com.example.sofe4640bookstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "bookstore.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "id";
    private static final String COL_2 = "firstName";
    private static final String COL_3 = "lastName";
    private static final String COL_4 = "userName";
    private static final String COL_5 = "passowrd";
    private static final String COL_6 = "email";
    private static final String COL_7 = "status";

    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*===================================================================*/
        String createTableUsers = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_1 + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " Text NOT NULL," +
                COL_3 + " Text NOT NULL," +
                COL_4 + " Text NOT NULL," +
                COL_5 + " Text NOT NULL," +
                COL_6 + " Text NOT NULL," +
                COL_7 + " number DEFAULT 0)" + ";";

        //Log.d("DBText","createTable: "+createTableUsers);
        sqLiteDatabase.execSQL(createTableUsers);
        /*================================================================*/

        /* ---------------------------------------------------------------------- */
        /* Add table "BookCategories"                                             */
        /* ---------------------------------------------------------------------- */
        String createTableBookCategories = "CREATE TABLE IF NOT EXISTS BookCategories ( " +
                " categoryID INTEGER, " +
                " categoryName Text NOT NULL, " +
                " CONSTRAINT PK_BookCategories  PRIMARY KEY (categoryID) ); ";
        Log.d("BookCategories",createTableBookCategories);
        sqLiteDatabase.execSQL(createTableBookCategories);
        /*================================================================*/

        /* ---------------------------------------------------------------------- */
        /* Add table "Authors"                                                    */
        /* ---------------------------------------------------------------------- */
        String createTableAuthors = " CREATE TABLE IF NOT EXISTS Authors ( " +
                " authorID INTEGER NOT NULL, " +
                " firstName TEXT, " +
                " lastName  TEXT, " +
                " CONSTRAINT PK_Authors PRIMARY KEY (authorID)); ";
        Log.d("Authors",createTableAuthors);

        sqLiteDatabase.execSQL(createTableAuthors);


        /* ---------------------------------------------------------------------- */
        /* Add table "BookDecriptions"                                            */
        /* ---------------------------------------------------------------------- */
        String createTableBookDecriptions = " CREATE TABLE IF NOT EXISTS BookDecriptions ( " +
                " ISBN INTEGER NOT NULL, " +
                " title  TEXT NOT NULL, " +
                " publisher  TEXT, " +
                " price  INTEGER, " +
                " quantity INTEGER, " +
                " edition TEXT, " +
                " pages INTEGER, " +
                " user TEXT , " +
                " CONSTRAINT PK_BookDecriptions  PRIMARY KEY (ISBN));";
        sqLiteDatabase.execSQL(createTableBookDecriptions);
        /*================================================================*/

        /* ---------------------------------------------------------------------- */
        /* Add table "BookCategoriesBooks"                                        */
        /* ---------------------------------------------------------------------- */
        String createTableBookCategoriesBooks = " CREATE TABLE IF NOT EXISTS BookCategoriesBooks ( " +
                "ISBN  INTEGER NOT NULL, " +
                "categoryID INTEGER NOT NULL, " +
                " CONSTRAINT PK_BookCategoriesBooks PRIMARY KEY (ISBN, categoryID), " +
                " FOREIGN KEY (ISBN) REFERENCES BookDecriptions(ISBN) ," +
                " FOREIGN KEY (categoryID) REFERENCES BookCategories (categoryID));";
        sqLiteDatabase.execSQL(createTableBookCategoriesBooks);
        /*================================================================*/




        /* ---------------------------------------------------------------------- */
        /* Add table "BookAuthorsBooks"                                           */
        /* ---------------------------------------------------------------------- */
        String BookAuthorsBooks = " CREATE TABLE IF NOT EXISTS BookAuthorsBooks ( " +
                " authorID INTEGER NOT NULL, " +
                " ISBN INTEGER NOT NULL, " +
                " CONSTRAINT PK_BookAuthorsBooks PRIMARY KEY (authorID, ISBN), " +
                " FOREIGN KEY (authorID) REFERENCES Authors (authorID), " +
                " FOREIGN KEY (ISBN) REFERENCES BookDecriptions( ISBN));";
        sqLiteDatabase.execSQL(BookAuthorsBooks);
    }

    /*Database drop script*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("Drop table IF EXISTS " + TABLE_NAME + ";");
            sqLiteDatabase.execSQL("Drop table IF EXISTS BookAuthorsBooks ;");
            sqLiteDatabase.execSQL("Drop table IF EXISTS Authors ;");
            sqLiteDatabase.execSQL("Drop table IF EXISTS BookCategoriesBooks ;");
            sqLiteDatabase.execSQL("Drop table IF EXISTS BookCategories ;");
            sqLiteDatabase.execSQL("Drop table IF EXISTS BookDecriptions ;");
            this.onCreate(sqLiteDatabase);
    }



/*Insert a new record in users table*/
    public boolean addRecord(Users users){
        ContentValues values= new ContentValues();
        values.put(COL_2,users.getFirstName());
        values.put(COL_3,users.getLastName());
        values.put(COL_4,users.getUserName());
        values.put(COL_5,users.getPassword());
        values.put(COL_6,users.getEmail());
        values.put(COL_7,"1");

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_NAME,null,values);


        /*==============================*/
        ContentValues values1= new ContentValues();
        values1.put("authorID",1);
        values1.put("firstName","Anwar");
        values1.put("lastName","Bari");
        /*==============================*/
        db.close();

        if (result ==0) return false;
        else
            return true;
    }

/* check if a record exists in users table*/
    public boolean checkUsers(String userNameStr, String passwordStr) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_NAME
                       + " where " + COL_4 + " = " + "'" +userNameStr + "'" +
                          " and " +  COL_5 + " = " + "'" +passwordStr+"'" +" ; ";

        Log.d("query",query);
        Cursor c = db.rawQuery(query,null);
        int rowsCount= c.getCount();
        c.close();
        if (rowsCount>0) return true;
        else return false;

    }



}
