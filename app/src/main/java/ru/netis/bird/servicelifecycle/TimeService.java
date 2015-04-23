package ru.netis.bird.servicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service {

    // constant
    public static final long NOTIFY_INTERVAL = 30 * 1000; // 30 seconds

    //
    private int count;

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    public TimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
        count = 0;
        // cancel if already existed
        if (mTimer != null) {
//            mTimer.cancel();
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
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), getDateTime() + " count=" + count,
                            Toast.LENGTH_SHORT).show();
                    if (++count >= 50) {
                        stopSelf();
                    }
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
