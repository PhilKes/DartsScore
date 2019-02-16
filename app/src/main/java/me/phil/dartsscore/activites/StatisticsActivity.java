package me.phil.dartsscore.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import me.phil.dartsscore.APIPlayer;
import me.phil.dartsscore.PlayerStatsAdapter;
import me.phil.dartsscore.R;

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
