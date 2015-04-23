package ru.netis.bird.servicelifecycle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public static final String NEW_INTERVAL = "newinterval";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText.setText(Long.toString(TimeService.NOTIFY_INTERVAL));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, TimeService.class));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                // запуск службы
                // используем явный вызов службы
                long interval = Long.valueOf(editText.getText().toString());
                Intent intent = new Intent(MainActivity.this, TimeService.class);
                intent.putExtra(NEW_INTERVAL, interval);
                startService(intent);
                break;
            case R.id.btnStop:
                // остановка службы
                stopService(new Intent(MainActivity.this, TimeService.class));
                break;
        }
    }
}
