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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                // запуск службы
                // используем явный вызов службы
                long interval = Long.valueOf(editText.getText().toString());
                Intent intent = new Intent(MainActivity.this, TimeService.class);
                intent.getLongExtra(NEW_INTERVAL, interval);
                startService(intent);
                break;
            case R.id.btnStop:
                // остановка службы
                stopService(new Intent(MainActivity.this, TimeService.class));
                break;
        }
    }
}
