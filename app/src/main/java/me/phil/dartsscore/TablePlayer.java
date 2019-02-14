package me.phil.dartsscore;

import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TablePlayer {
    public ConstraintLayout layout;
    public Player player;
    private TextView txtScore, txtLegs,txtSets,txtName;

    public TablePlayer(ConstraintLayout layout,Player player) {
        this.layout = layout;
        this.player=player;
        txtLegs=layout.findViewById(R.id.txt_playerLegs);
        txtSets=layout.findViewById(R.id.txt_playerSets);
        txtScore=layout.findViewById(R.id.txt_playerScore);
        txtScore.setText(""+player.score);
        txtSets.setText(""+player.sets);
        txtLegs.setText(""+player.legs);
        txtName=layout.findViewById(R.id.txt_playerName);
        txtName.setText(player.name);
    }
    /** Returns true if score is 0 or overthrown**/
    public boolean turnScore(int score){
        player.score-=score;
        txtScore.setText(""+player.score);
        return player.score<=1;
    }
    public void setScore(int score){
        player.score=score;
        txtScore.setText(""+player.score);
    }

    public void setSelected(boolean selected) {
        txtLegs.setSelected(selected);
        txtSets.setSelected(selected);
        txtScore.setSelected(selected);
        txtName.setSelected(selected);
        if(selected)
            txtName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_dart, 0);
        else
            txtName.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
    }

    public int getScore() {
        return player.score;
    }

    public String getName() {
        return player.name;
    }

    public int addLeg() {
        return ++player.legs;
    }

    public int addSet() {
        return ++player.sets;
    }

    public void setLegs(int legs) {
        player.legs = legs;
    }

    public void updateStats() {
        txtScore.setText(""+player.score);
        txtSets.setText(""+player.sets);
        txtLegs.setText(""+player.legs);
    }
}
