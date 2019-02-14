package me.phil.dartsscore.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.phil.dartsscore.APIPlayer;
import me.phil.dartsscore.R;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void onResetClick(View view) {
        new APIPlayer(this).resetAll();
    }

    public void onBackClicked(View view) {
        super.onBackPressed();
        finish();
    }
}
