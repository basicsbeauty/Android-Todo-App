package com.example.briankwon21.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TodoItemDatabaseHelper extends SQLiteOpenHelper {

    private static TodoItemDatabaseHelper sInstance;

    private static final String DATABASE_NAME = "todoItemDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TODOITEMS = "todoItems";

    private static final String KEY_TODOITEM_ID = "id";
    private static final String KEY_TODOITEM_TEXT = "text";

    private static final String TAG = "MainActivity";

    public static synchronized TodoItemDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TodoItemDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private TodoItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODOITEMS_TABLE = "CREATE TABLE " + TABLE_TODOITEMS +
                "(" +
                KEY_TODOITEM_ID + " INTEGER PRIMARY KEY," +
                KEY_TODOITEM_TEXT + " TEXT" +
                ")";

        db.execSQL(CREATE_TODOITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOITEMS);
            onCreate(db);
        }
    }

    public void addTodoItem(String itemText) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODOITEM_TEXT, itemText);

            long id = db.insertOrThrow(TABLE_TODOITEMS, null, values);
            TodoItem newItem = createItem(id, itemText);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<TodoItem> getAllItems() {
        ArrayList<TodoItem> items = new ArrayList<>();

        String ITEMS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABLE_TODOITEMS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TodoItem item = new TodoItem();
                    item.setText(cursor.getString(cursor.getColumnIndex(KEY_TODOITEM_TEXT)));
                    item.setId(cursor.getLong(cursor.getColumnIndex(KEY_TODOITEM_ID)));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    public int updateItem(long id, String text) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODOITEM_TEXT, text);

        return db.update(TABLE_TODOITEMS, values, "id = ? ", new String[] { Long.toString(id) } );
    }

    public void deleteItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TODOITEMS, "id = ? ", new String[]{Long.toString(item.getId())});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete a todo item");
        } finally {
            db.endTransaction();
        }
    }

    private TodoItem createItem(long id, String text) {
        TodoItem item = new TodoItem(text);
        item.setId(id);
        return item;
    }
}
