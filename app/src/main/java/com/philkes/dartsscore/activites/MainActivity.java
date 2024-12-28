package com.philkes.dartsscore.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.philkes.dartsscore.R;

/**  Main Menu Screen **/
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnNewGame=findViewById(R.id.btn_newGame);
        btnNewGame.setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this,PlayerActivity.class);
            startActivity(intent);
        });
    }

    public void onExitClick(View view) {
        finish();
        System.exit(0);
    }

    public void onStatisticsClick(View view) {
        Intent intent=new Intent(MainActivity.this,StatisticsActivity.class);
        startActivity(intent);
    }

    public void onOptionsClick(View view) {
        Intent intent=new Intent(MainActivity.this,OptionsActivity.class);
        startActivity(intent);
    }
}
