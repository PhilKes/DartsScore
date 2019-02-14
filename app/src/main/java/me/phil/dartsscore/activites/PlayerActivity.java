package me.phil.dartsscore.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import me.phil.dartsscore.APIPlayer;
import me.phil.dartsscore.Player;
import me.phil.dartsscore.PlayerAdapter;
import me.phil.dartsscore.R;

/** Player Selection Screen for new Match **/
public class PlayerActivity extends AppCompatActivity {

    ListView lvPlayers;
    PlayerAdapter adapterPlayers;
    ArrayList<RadioButton> groupFirst;
    int firstIdx=0;
    public static int _ID=0;
    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        lvPlayers=findViewById(R.id.lv_players);
        ArrayList<Player> listPlayers=new ArrayList<>();
        groupFirst=new ArrayList<>();
        listPlayers.add(new Player(-1));
        players=new APIPlayer(getBaseContext()).queryAllPlayers();
        /** Show Dialog to pick new Player **/
        lvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Player player= listPlayers.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayerActivity.this,R.style.AlertDialogCustom);
                builder.setTitle("Enter new Player");
                /**  ListView to show Players**/
                LinearLayout linearLayout=new LinearLayout(getBaseContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                /** Load Players from Database into the List**/
                ListView listViewPlayers=new ListView(getBaseContext());
                ArrayAdapter<Player> adapter=new ArrayAdapter<>(getBaseContext(),R.layout.dialog_list_player,R.id.txt_PlayerName,players);
                final int playerIDx=i;
                final APIPlayer api=new APIPlayer(getBaseContext());
                listViewPlayers.setAdapter(adapter);
                linearLayout.addView(listViewPlayers);
                final EditText input = new EditText(PlayerActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setTextColor(getResources().getColor(R.color.colorButtonLight));
                if(player.score!=-1) {
                    input.setText(player.name);
                    builder.setTitle("Change Player");
                }
                linearLayout.addView(input);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                builder.setView(linearLayout);
                /** Add new Player to database and List**/
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(player.score==-1) {
                            if(api.createPlayer(input.getText().toString())==-1) {
                                Toast.makeText(PlayerActivity.this, "Player already exists!", Toast.LENGTH_SHORT).show();
                                //player.name="New Player...";
                            }
                            //player=api.queryPlayer(player.name);
                            else {
                                listPlayers.add(new Player(-1));
                                listPlayers.set(i,api.queryPlayer(input.getText().toString()).get(0));
                                adapterPlayers.notifyDataSetChanged();
                                RadioButton rBtn=view.findViewById(R.id.rbtn_first);
                                rBtn.setEnabled(true);
                                rBtn.setOnCheckedChangeListener((v,checked)->onFirstChecked(i,checked));
                                groupFirst.add(rBtn);
                            }
                        }
                        else{
                            if(api.createPlayer(input.getText().toString())==-1) {
                                Toast.makeText(PlayerActivity.this, "Player already exists!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                listPlayers.set(i,api.queryPlayer(input.getText().toString()).get(0));
                                adapterPlayers.notifyDataSetChanged();
                            }
                        }
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                });
                /** Remove clicked Player from GameList **/
                builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(player.score!=-1){
                            groupFirst.remove(i);
                            //listPlayers.remove(i);
                            players.add(listPlayers.remove(i));
                            adapterPlayers.notifyDataSetChanged();
                            //TODO REMOVED ID !!
                        }
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        dialog.cancel();
                    }
                });
                AlertDialog dialog=builder.create();
                /** Add Player from Database to GameList **/
                listViewPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view2, int i, long l) {
                        if(listPlayers.get(playerIDx).score==-1)
                            listPlayers.add(new Player(-1));
                        else
                            players.add(listPlayers.get(playerIDx));
                        listPlayers.set(playerIDx,api.queryPlayer(players.get(i).name).get(0));
                        players.removeIf(p->p.name.equals(players.get(i).name));
                        adapterPlayers.notifyDataSetChanged();
                        RadioButton rBtn=view.findViewById(R.id.rbtn_first);
                        rBtn.setEnabled(true);
                        rBtn.setOnCheckedChangeListener((v,checked)->onFirstChecked(playerIDx,checked));
                        groupFirst.add(rBtn);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                input.requestFocus();
            }
        });
        adapterPlayers=new PlayerAdapter(listPlayers,null);
        lvPlayers.setAdapter(adapterPlayers);
        /** Start GameSettings Activity, pass players and first Player to intent**/
        Button next=findViewById(R.id.btn_next);
        next.setOnClickListener(v->{
            if(listPlayers.size()>1) {
                listPlayers.remove(listPlayers.size()-1);
                Intent intent = new Intent(PlayerActivity.this, GameSettingsActivity.class);
                intent.putParcelableArrayListExtra("players", listPlayers);
                intent.putExtra("first",firstIdx);
                startActivity(intent);
                finish();
            }
            else
                Toast.makeText(this,"Please Add at least 1 Player !",Toast.LENGTH_SHORT).show();
        });
        /** Go back to Main Menu **/
        Button back=findViewById(R.id.btn_back);
        back.setOnClickListener(v->super.onBackPressed());
    }
    /** Select Player (RadioButton) for starting Player**/
    private void onFirstChecked(int i,boolean checked) {
        if(checked) {
            groupFirst.get(firstIdx).setChecked(false);
            firstIdx = i;
            groupFirst.get(firstIdx).setChecked(true);
        }

    }
}
