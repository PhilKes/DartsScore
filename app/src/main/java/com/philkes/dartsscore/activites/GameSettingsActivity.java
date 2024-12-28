package com.philkes.dartsscore.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.philkes.dartsscore.Player;
import com.philkes.dartsscore.R;

/** Score,Sets,Legs settings for new Match **/
public class GameSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        ArrayList<Player> players=this.getIntent().getParcelableArrayListExtra("players");
        int firstIdx=getIntent().getIntExtra("first",0);
        /** Spinner for Score selection **/
        Integer[] scores={501,301,201,701,1001};
        Spinner spinner=findViewById(R.id.spin_score);
        spinner.setAdapter(new ArrayAdapter<Integer>(this,R.layout.spinner_score_item,scores));
        spinner.setSelection(1);
        /** SeekBars for Sets,Legs **/
        SeekBar barSets=findViewById(R.id.sbar_sets);
        barSets.setProgress(1);
        SeekBar barLegs=findViewById(R.id.sbar_legs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            barSets.setMin(1);
            barLegs.setMin(1);
        }
        barLegs.setProgress(2);
        TextView txtSets=findViewById(R.id.txt_sets);
        txtSets.setText("Sets: "+barSets.getProgress());
        TextView txtLegs=findViewById(R.id.txt_legs);
        txtLegs.setText("Legs per Set: "+barLegs.getProgress());
        /** Update TextViews for Sets,Legs setting **/
        barSets.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i < 1) {
                    seekBar.setProgress(1);
                    txtSets.setText("Sets: 1");
                }
                else
                    txtSets.setText("Sets: "+i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        barLegs.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i < 1) {
                    seekBar.setProgress(1);
                    txtLegs.setText("Legs per Set: 1");
                }
                else
                    txtLegs.setText("Legs per Set: "+i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /** Start Match, pass Players,GameSettings to GameActivity **/
        Button btnStart=findViewById(R.id.btn_start);
        btnStart.setOnClickListener(v->{
            Intent intent=new Intent(GameSettingsActivity.this,GameActivity.class);
            intent.putParcelableArrayListExtra("players",players);
            intent.putExtra("score",(int)spinner.getSelectedItem());
            intent.putExtra("sets",barSets.getProgress());
            intent.putExtra("legs",barLegs.getProgress());
            intent.putExtra("first",firstIdx);
            startActivity(intent);
            finish();
        });
    }

    public void onBackClicked(View view) {
        Intent intent=new Intent(GameSettingsActivity.this,PlayerActivity.class);
        startActivity(intent);
        finish();
    }
}
