package android.example.com.habittracker;

import android.database.Cursor;
import android.example.com.habittracker.data.HabitContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class TrackerActivity extends AppCompatActivity {

    public static final String LOG_TAG = TrackerActivity.class.getSimpleName();
    public static final String NEW_LINE = "\n";
    public static final String COMMA = ", ";
    private Tracker tracker;
    private TextView debugOutputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        debugOutputView = new TextView(this);
        debugOutputView.setPadding(25, 25, 25, 25);
        setContentView(debugOutputView);

        tracker = new Tracker(this);
        tracker.track();
        Cursor results = tracker.fetchAll();
        Log.v(LOG_TAG, getString(R.string.num_records) + String.valueOf(results.getCount()));
        renderTrackerData(results, debugOutputView);
    }


    private void renderTrackerData(Cursor resultSet, TextView textView) {
        if (textView == null)
            return;
        textView.setText(String.valueOf(resultSet.getCount()));
        String out;

        int idColumnIndex = resultSet.getColumnIndex(HabitContract.HabitEntry._ID);
        int nameColumnIndex = resultSet.getColumnIndex(
                HabitContract.HabitEntry.COLUMN_HABIT_NAME);
        int timeColumnIndex = resultSet.getColumnIndex(
                HabitContract.HabitEntry.COLUMN_HABIT_TIMESTAMP);

        while (resultSet.moveToNext()) {
            out = HabitContract.HabitEntry._ID + " " +
                    resultSet.getString(idColumnIndex) + COMMA +
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME + " " +
                    resultSet.getString(nameColumnIndex) + COMMA + " " +
                    tracker.formatRelativeTime(resultSet.getLong(timeColumnIndex)) + NEW_LINE;
            textView.append(out);

        }

        resultSet.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tracker != null)
            tracker.cleanUp();
    }
}
