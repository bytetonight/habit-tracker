package android.example.com.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by ByteTonight on 03.05.2017.
 * There exists a contract class that defines name of table and constants.
 */

public final class HabitContract {

    /**
     * Inside the contract class, there is an inner class for each table created.
     */
    public static abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_TIMESTAMP = "tstamp";

    }

}
