package me.phil.dartsscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerStatsAdapter extends BaseAdapter {
    ArrayList<Player> listPlayers;
    View.OnClickListener listener;
    public PlayerStatsAdapter(ArrayList<Player> listPlayers) {
        this.listPlayers = listPlayers;
    }

    @Override
    public int getCount() {
        return listPlayers.size();
    }

    @Override
    public Player getItem(int i) {
        return listPlayers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context=viewGroup.getContext();
        if(view==null){
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.list_player_statistics,null);
        }
        Player p=listPlayers.get(i);
        TextView txtPlayer=view.findViewById(R.id.txt_Name);
        txtPlayer.setText(p.name+"");
        TextView txtWon=view.findViewById(R.id.txt_won);
        txtWon.setText(p.won+"");
        TextView txtGames=view.findViewById(R.id.txt_games);
        txtGames.setText(p.games+"");
        TextView txtAvg=view.findViewById(R.id.txt_avg);
        txtAvg.setText(((p.avg/(double)p.games)*3)+"");
        TextView txtDoubles=view.findViewById(R.id.txt_doubles);
        txtDoubles.setText(String.format("%.2f%%%n",(p.doubles/(double)p.games)*100));
        TextView txtFinish=view.findViewById(R.id.txt_finish);
        txtFinish.setText(p.bestFinish+"");
        TextView txtScore=view.findViewById(R.id.txt_score);
        txtScore.setText(p.highestScore+"");

        return view;
    }
}
