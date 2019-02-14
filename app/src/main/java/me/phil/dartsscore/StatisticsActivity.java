package me.phil.dartsscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class StatisticsActivity extends AppCompatActivity {

    ListView listPlayers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        listPlayers=findViewById(R.id.lv_playerList);
        listPlayers.setAdapter(new PlayerStatsAdapter(new APIPlayer(this).queryAllPlayers()));
    }
}
