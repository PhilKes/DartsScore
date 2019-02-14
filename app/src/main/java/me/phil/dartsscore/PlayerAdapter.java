package me.phil.dartsscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends BaseAdapter {
    ArrayList<Player> listPlayers;
    View.OnClickListener listener;
    public PlayerAdapter(ArrayList<Player> listPlayers,View.OnClickListener listener) {
        this.listPlayers = listPlayers;
        this.listener=listener;
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
            view=layoutInflater.inflate(R.layout.list_player_item,null);
        }
        Player p=listPlayers.get(i);
        TextView txtPlayer=view.findViewById(R.id.txt_name);

        txtPlayer.setText(p.name);
        //txtPlayer.setOnClickListener(listener);

        return view;
    }
}
