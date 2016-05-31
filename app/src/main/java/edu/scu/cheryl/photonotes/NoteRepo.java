package edu.scu.cheryl.photonotes;

        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

public class NoteRepo {
    static DBHelper dbHelper;
    static SQLiteDatabase db;

    public NoteRepo(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public void executeQuery(String query) {
        try {

            if (db.isOpen()) {
                db.close();
            }

            db = dbHelper.getWritableDatabase();
            db.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public static Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (db.isOpen()) {
                db.close();

            }
            db = dbHelper.getWritableDatabase();
            c1 = db.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }

//    public int insert(Notes notes) {
//        //Open connection to write data
//        db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Notes.KEY_caption, notes.caption);
//        values.put(Notes.KEY_filename, notes.filename);
//
//        // Inserting Row
//        long  note_Id = db.insert(Notes.TABLE, null, values);
//        db.close(); // Closing database connection
//        return (int) note_Id;
//    }

//    public void delete(int student_Id) {
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        // It's a good practice to use parameter ?, instead of concatenate string
//        db.delete(Notes.TABLE, Notes.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
//        db.close(); // Closing database connection
//    }

//    public void update(Student student) {
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(Student.KEY_age, student.age);
//        values.put(Student.KEY_email,student.email);
//        values.put(Student.KEY_name, student.name);
//
//        // It's a good practice to use parameter ?, instead of concatenate string
//        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
//        db.close(); // Closing database connection
//    }

//    public ArrayList<HashMap<String, String>> getNoteList() {
//        //Open connection to read only
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String selectQuery = "SELECT  " +
//                Notes.KEY_ID + "," +
//                Notes.KEY_caption + "," +
//                Notes.KEY_filename +
//                " FROM " + Notes.TABLE;
//
//        //Student student = new Student();
//        ArrayList<HashMap<String, String>> noteList = new ArrayList<HashMap<String, String>>();
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // looping through all rows and adding to list
//
//        if (cursor.moveToFirst()) {
//            do {
//                HashMap<String, String> note = new HashMap<String, String>();
//                note.put("name", cursor.getString(cursor.getColumnIndex(Notes.KEY_caption)));
//                note.put("filename", cursor.getString(cursor.getColumnIndex(Notes.KEY_filename)));
//                noteList.add(note);
//
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return noteList;
//
//    }

//    public Notes getNoteById(int Id) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String selectQuery = "SELECT  " +
//                Notes.KEY_ID + "," +
//                Notes.KEY_caption + "," +
//                Notes.KEY_filename +
//                " FROM " + Notes.TABLE
//                + " WHERE " +
//                Notes.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string
//
//        int iCount = 0;
//        Notes notes = new Notes();
//
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});
//
//        if (cursor.moveToFirst()) {
//            do {
//                notes.notes_id = cursor.getInt(cursor.getColumnIndex(Notes.KEY_ID));
//                notes.caption = cursor.getString(cursor.getColumnIndex(Notes.KEY_caption));
//                notes.filename = cursor.getString(cursor.getColumnIndex(Notes.KEY_filename));
//
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return notes;
//    }
}