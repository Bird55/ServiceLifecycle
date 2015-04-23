package ru.netis.bird.servicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service {
    
    private static final String LOG_TAG = "myLog";

    // constant
    public static final long NOTIFY_INTERVAL = 30 * 1000; // 30 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    public TimeService() {
        Log.d(LOG_TAG, "TimeService constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind ");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand ");
        super.onCreate();
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        long interval = intent.getLongExtra(MainActivity.NEW_INTERVAL, NOTIFY_INTERVAL);
        Toast.makeText(getApplicationContext(), "onStartCommand: interval = " + interval, Toast.LENGTH_SHORT).show();
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, interval);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate ");
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onDestroy ");
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    Toast.makeText(getApplicationContext(), getDateTime(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        // используйте метод в сообщении для вывода текущего времени вместо слов о кормежке кота
        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "[dd/MM/yyyy - HH:mm:ss]", Locale.getDefault());
            return sdf.format(new Date());
        }
    }
}
