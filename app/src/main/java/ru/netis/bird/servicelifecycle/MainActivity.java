package ru.netis.bird.servicelifecycle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                // запуск службы
                // используем явный вызов службы
                startService(new Intent(MainActivity.this, TimeService.class));
                break;
            case R.id.btnStop:
                // остановка службы
                stopService(new Intent(MainActivity.this, TimeService.class));
                break;
        }
    }
}
