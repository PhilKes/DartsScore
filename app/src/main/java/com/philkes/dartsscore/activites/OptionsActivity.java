package com.philkes.dartsscore.activites;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.philkes.dartsscore.APIPlayer;
import com.philkes.dartsscore.R;

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
