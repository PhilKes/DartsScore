package com.philkes.dartsscore.activites;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.philkes.dartsscore.APIPlayer;
import com.philkes.dartsscore.PlayerStatsAdapter;
import com.philkes.dartsscore.R;

public class StatisticsActivity extends AppCompatActivity {

    //TODO HIGHLIGHT BEST PLAYER IN CATEGORIES
    //TODO SYNCHRONIZE DATABASES
    ListView listPlayers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        listPlayers=findViewById(R.id.lv_playerList);
        listPlayers.setAdapter(new PlayerStatsAdapter(new APIPlayer(this).queryAllPlayers()));
    }
}
