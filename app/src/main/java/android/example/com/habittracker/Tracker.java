package android.example.com.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.com.habittracker.data.DbHelper;
import android.example.com.habittracker.data.HabitContract;
import android.text.format.DateUtils;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by ByteTonight on 27.05.2017.
 */

public class Tracker {

    private DbHelper dbHelper;
    private Context context;
    private Cursor resultSet;
    private Random random = new Random();
    private String[] habitNames;
    private String saveSuccessMsg;
    private String saveFailedMsg;

    public Tracker(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        habitNames = context.getResources().getStringArray(R.array.habits);
        saveSuccessMsg = context.getResources().getString(R.string.save_success);
        saveFailedMsg = context.getResources().getString(R.string.save_fail);
    }

    public void track() {
        ContentValues data = new ContentValues();
        /* This is one value of type String*/
        data.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, getRandomHabit());
        /* This is another value of type Long (Integer in the Database) */
        data.put(HabitContract.HabitEntry.COLUMN_HABIT_TIMESTAMP, dbHelper.now());
        long insertId = dbHelper.insertData(data);

        if (insertId != -1)
            Toast.makeText(context, saveSuccessMsg + insertId, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, saveFailedMsg, Toast.LENGTH_SHORT).show();
    }

    private String getRandomHabit() {
        int index = random.nextInt(3);
        return habitNames[index];
    }

    /**
     * This calls a single read method
     * @return
     */
    public Cursor fetchAll() {
        return dbHelper.fetchAll();
    }



    /**
     * Time spans in the past are formatted like "42 minutes ago".
     * Time spans in the future are formatted like "In 42 minutes".
     *
     * @param eventTime : The UnixTime of an event in the  past or future
     * @return : a string describing 'eventTime' as a time relative to 'now'.
     */
    public String formatRelativeTime(long eventTime) {
        long now = dbHelper.now();
        return (DateUtils.getRelativeTimeSpanString(eventTime, now,
                DateUtils.FORMAT_ABBREV_ALL)).toString();
    }


    public void cleanUp() {
        dbHelper.closeDb();
        dbHelper.deleteDatabase();
    }
}
