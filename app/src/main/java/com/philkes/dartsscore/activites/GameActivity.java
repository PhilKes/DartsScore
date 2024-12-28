package com.philkes.dartsscore.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.PowerManager;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.philkes.dartsscore.APIPlayer;
import com.philkes.dartsscore.Player;
import com.philkes.dartsscore.R;
import com.philkes.dartsscore.TablePlayer;

public class GameActivity extends AppCompatActivity {

    TableLayout tablePlayers;
    ArrayList<TablePlayer> playersView;
    TextView txtCurrScore,txtScore,txtPlayer,txtFinish,txtAvg,txtDoubles,txtAvgAllTime;
    int currPlayer;
    int gameLegs,gameSets,gameScore,gameStart;
    int winner=-1;
    SoundPool soundPool;
    ArrayList<Integer> soundIDs;
    ArrayList<Player> players;
    LinkedList<Integer> scoreStack;

    /** Special SoundIndexes, HashMap for Finish recommendations **/
    private final static int LEG_WON=181,SET_WON=182,MATCH_WON=183,START_SOUND=184;
    static final HashMap<Integer,String> finishMap=new HashMap<>();
    static {
        finishMap.put(170,"T20 T20 Bull");		finishMap.put(132,"T20 T16 D12");	 finishMap.put(101,"T17 10 D20");	 	finishMap.put(70,"T18 D8");
        finishMap.put(167,"T20 T19 Bull");		finishMap.put(131,"T20 T13 D16");	 finishMap.put(100,"T20 D20");	 		finishMap.put(69,"19 Bull");
        finishMap.put(164,"T19 T19 Bull");		finishMap.put(130,"T20 T18 D8"); 	 finishMap.put(99,"T19 10 D16");		finishMap.put(68,"T20 D4");
        finishMap.put(161,"T20 T17 Bull");		finishMap.put(129,"T19 T16 D12");	 finishMap.put(98,"T20 D19");	 	 	finishMap.put(67,"T17 D8");
        finishMap.put(160,"T20 T20 D20");	 	finishMap.put(128,"T20 T20 D4");	 finishMap.put(97,"T19 D20");	 	 	finishMap.put(66,"T10 D18");
        finishMap.put(158,"T20 T20 D19");	 	finishMap.put(127,"T20 T17 D8");	 finishMap.put(96,"T20 D18");	 	 	finishMap.put(65,"T19 D4");
        finishMap.put(157,"T19 T20 D20");	 	finishMap.put(126,"T19 19 Bull");	 finishMap.put(95,"T19 D19");	 	 	finishMap.put(64,"T16 D8");
        finishMap.put(156,"T20 T20 D18");	 	finishMap.put(125,"T20 T19 D4");	 finishMap.put(94,"T18 D20");	 	 	finishMap.put(63,"T13 D12");
        finishMap.put(155,"T20 T19 D19");	 	finishMap.put(124,"T20 T16 D8");	 finishMap.put(93,"T19 D18");	 	 	finishMap.put(62,"T10 D16");
        finishMap.put(154,"T20 T18 D20");	 	finishMap.put(123,"T20 T13 D12");	 finishMap.put(92,"T20 D16");	 	 	finishMap.put(61,"T15 D8");
        finishMap.put(153,"T20 T19 D18");	 	finishMap.put(122,"T18 18 Bull");	 finishMap.put(91,"T17 D20");	 	 	finishMap.put(60,"20 D20");
        finishMap.put(152,"T20 T20 D16");	 	finishMap.put(121,"T19 14 Bull");	 finishMap.put(90,"T18 D18");	 	 	finishMap.put(59,"19 D20");
        finishMap.put(151,"T20 T17 D20");	 	finishMap.put(120,"T20 20 D20");	 finishMap.put(89,"T19 D16");	 	 	finishMap.put(58,"18 D20");
        finishMap.put(150,"T20 T18 D18");	 	finishMap.put(119,"T20 19 D20");	 finishMap.put(88,"T16 D20");	 	 	finishMap.put(57,"17 D20");
        finishMap.put(149,"T20 T19 D16");	 	finishMap.put(118,"T20 18 D20");	 finishMap.put(87,"T17 D18");	 	 	finishMap.put(56,"16 D20");
        finishMap.put(148,"T20 T20 D14");	 	finishMap.put(117,"T20 17 D20");	 finishMap.put(86,"T18 D16");	 	 	finishMap.put(55,"15 D20");
        finishMap.put(147,"T20 T17 D18");	 	finishMap.put(116,"T20 16 D20");	 finishMap.put(85,"T15 D20");	 	 	finishMap.put(54,"14 D20");
        finishMap.put(146,"T20 T18 D16");	 	finishMap.put(115,"T20 15 D20");	 finishMap.put(84,"T16 D18");	 	 	finishMap.put(53,"13 D20");
        finishMap.put(145,"T20 T15 D20");	 	finishMap.put(114,"T20 14 D20");	 finishMap.put(83,"T17 D16");	 	 	finishMap.put(52,"12 D20");
        finishMap.put(144,"T20 T20 D12");	 	finishMap.put(113,"T20 13 D20");	 finishMap.put(82,"T14 D20");	 	 	finishMap.put(51,"19 D16");
        finishMap.put(143,"T20 T17 D16");	 	finishMap.put(112,"T20 12 D20");	 finishMap.put(81,"T15 D18");	 	 	finishMap.put(50,"10 D20");
        finishMap.put(142,"T20 T14 D20");	 	finishMap.put(111,"T20 19 D16");	 finishMap.put(80,"T16 D16");	 	 	finishMap.put(49,"17 D16");
        finishMap.put(141,"T20 T15 D18");	 	finishMap.put(110,"T20 10 D20");	 finishMap.put(79,"T13 D20");	 	 	finishMap.put(48,"16 D16");
        finishMap.put(140,"T20 T16 D16");	 	finishMap.put(109,"T19 12 D20");	 finishMap.put(78,"T18 D12");	 	 	finishMap.put(47,"15 D16");
        finishMap.put(139,"T20 T13 D20");	 	finishMap.put(108,"T20 16 D16");	 finishMap.put(77,"T15 D16");	 	 	finishMap.put(46,"6 D20");
        finishMap.put(138,"T20 T16 D15");	 	finishMap.put(107,"T19 10 D20");	 finishMap.put(76,"T20 D8"); 	 		finishMap.put(45,"13 D16");
        finishMap.put(137,"T18 T17 D16");	 	finishMap.put(106,"T20 10 D18");	 finishMap.put(75,"T13 D18");	 	 	finishMap.put(44,"12 D16");
        finishMap.put(136,"T20 T20 D8");	 	finishMap.put(105,"T20 13 D16");	 finishMap.put(74,"T14 D16");	 	 	finishMap.put(43,"3 D20");
        finishMap.put(135,"T20 T13 D18");	 	finishMap.put(104,"T20 12 D16");	 finishMap.put(73,"T19 D8"); 	 		finishMap.put(42,"10 D16");
        finishMap.put(134,"T20 T14 D16");	 	finishMap.put(103,"T19 10 D18");	 finishMap.put(72,"T16 D12");	 	 	finishMap.put(41,"9 D16");
        finishMap.put(133,"T20 T19 D8"); 		finishMap.put(102,"T20 10 D16");	finishMap.put(71,"T13 D16");	 	 finishMap.put(40,"D20");
        for (int i = 2; i < 40; i++) {
            if(i%2==0)
                finishMap.put(i,"D"+i/2);
            else
                finishMap.put(i,"1 D"+i/2);
        }
    }
    protected PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        /** Keep Screen on with WakeLock **/
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "dartsscore:wakelock");
        this.mWakeLock.acquire();

        /** Get Players,GameSettings from Intent **/
        players=this.getIntent().getParcelableArrayListExtra("players");
        gameScore=getIntent().getIntExtra("score",301);
        gameSets=getIntent().getIntExtra("sets",1);
        gameLegs=getIntent().getIntExtra("legs",1);
        gameStart=getIntent().getIntExtra("first",0);
        for(Player p: players) {
            p.legs=0;
            p.sets = 0;
            p.darts=0;
            p.gameAvg=0;
            p.score=gameScore;
            p.doubleDarts=0;
            p.doubleGame =0;
        }
        scoreStack=new LinkedList<>();
        System.out.println("Players: ");
        players.forEach(p-> System.out.println(p.name));
        System.out.println("Score: "+gameScore);
        System.out.println("Sets: "+gameSets);
        System.out.println("Legs: "+gameLegs);
        tablePlayers=findViewById(R.id.table_players);

        /** Init SoundPool, Score Sounds **/
        AudioAttributes audioAttributes=new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool=new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        getSoundIDs();

        TextView txtGameSets=findViewById(R.id.txt_GameSets);
        txtGameSets.setText("First to: "+gameSets+" Sets ( "+gameLegs+" Legs )");
        /** Add TableRow for each Player to the Scoreboard**/
        playersView=new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            LayoutInflater layoutInflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ConstraintLayout view=(ConstraintLayout)layoutInflater.inflate(R.layout.table_row_player,null);
            tablePlayers.addView(view);
            playersView.add(new TablePlayer(view,players.get(i)));
        }

        txtCurrScore=findViewById(R.id.txt_curscore);
        txtScore=findViewById(R.id.txt_score);
        txtPlayer=findViewById(R.id.txt_player);
        txtDoubles=findViewById(R.id.txt_doubles);
        txtAvg=findViewById(R.id.txt_avg);
        txtAvgAllTime=findViewById(R.id.txt_avg_gesamt);
        txtFinish=findViewById(R.id.txt_finish);
        txtCurrScore.setText("");
        //currPlayer=gameStart-1;
        switchPlayer(gameStart);
        playSound(START_SOUND);
    }

    private void playSound(int id) {
        final int sound=soundPool.load(this,soundIDs.get(id),1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                soundPool.play(sound,0.2f,0.2f,1,0,1f);
            }
        });
    }
    /** Add Number to current Score Text if valid **/
    public void onClickNumber(View view) {
        hapticFeedback();
        int number= Integer.parseInt(((Button)view).getText()+"");
        int score = Integer.parseInt(txtCurrScore.getText() + "" + number);
        if(txtCurrScore.getText().length() < 3&& score<=180) {
            if(score> 158 &&(score==179 ||score==178||score==176||score==175 || score==173 || score==172 || score==169 || score==168 ||score== 166 || score == 165 ||score==163 ||score==162 ||score==159) )
                Toast.makeText(this, "Bogey Number!", Toast.LENGTH_SHORT).show();
            else
                txtCurrScore.setText(txtCurrScore.getText() + "" + number);
        }
        else
            Toast.makeText(this,"Number too high!",Toast.LENGTH_SHORT).show();
    }

    public void onDeleteClick(View view) {
        hapticFeedback();
        if(txtCurrScore.length()>0)
            txtCurrScore.setText(txtCurrScore.getText().subSequence(0,txtCurrScore.getText().length()-1));
    }

    /** Submit throw from txtCurrScore Text **/
    public void onDoneClick(View view) {
        hapticFeedback();
       try {
           int score = Integer.parseInt(txtCurrScore.getText() + "");
           scored(score);
       }catch (Exception e){}
    }

    /** Update Score, handle Leg/Set won, switch Player **/
    public void scored(int score){
        TablePlayer player=playersView.get(currPlayer);
        scoreStack.addFirst(score);
        int oldScore=player.getScore();
        if(player.turnScore(score)){
            /** Leg won **/
            if(player.getScore()==0) {
                scoreStack=new LinkedList<>();
                showDoublesDialog(player,score);
                return;
            }
            /** Bust **/
            else {
                player.setScore(oldScore);
                score=0;
                playSound(0);
            }
        }else{
            if(score > player.player.highestScore)
                player.player.highestScore=score;
            //TODO Add Option to Record Doubles or not in OptionsActivity
            /*if(player.getScore()<=50 )
                showDoublesDialog(player,score);*/
            playSound(score);
        }
        player.player.gameAvg+=score;
        player.player.darts+=3;
        System.out.println("Player "+currPlayer+" scored: "+score);
        switchPlayer((currPlayer+1)%playersView.size());
    }

    /** Dialog to determine how many Darts thrown at Double **/
    private void showDoublesDialog(TablePlayer player,int score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder.setTitle("Darts at Double");
        ListView listViewPlayers=new ListView(getBaseContext());
        builder.setView(listViewPlayers);
        AlertDialog dialog=builder.create();
        Integer[] thrownDarts;
        if(player.getScore()==0)
            thrownDarts=new Integer[]{1,2,3};
        else
            thrownDarts=new Integer[]{0,1,2,3};
        ArrayAdapter<Integer> adapter=new ArrayAdapter<>(getBaseContext(),R.layout.dialog_list_player,R.id.txt_PlayerName,thrownDarts);
        listViewPlayers.setOnItemClickListener((adapterView, view, i, l) -> {
            player.player.doubleDarts+=thrownDarts[i];
            /** Update Scoreboard if won Leg**/
            if(player.getScore()==0)
                legWon(player,score);
            dialog.dismiss();
        });
        listViewPlayers.setAdapter(adapter);
        dialog.show();
    }

    private void legWon(TablePlayer player,int score){
        player.player.doubleGame +=1;
        player.player.legsWon+=1;
        for (TablePlayer p :playersView)
            p.player.legsPlayed+=1;
        System.out.println("Player " + playersView.get(currPlayer).getName() + " won leg");
        if(score > player.player.bestFinish)
            player.player.bestFinish=score;
        /** Set won **/
        if(player.addLeg()==gameLegs){
            player.setLegs(0);
            /** Match won **/
            if(player.addSet()==gameSets) {
                playSound(MATCH_WON);
                System.out.println("Player " + player.getName() + " won the Game!");
                Toast.makeText(this,player.getName()+" won the Match! ",Toast.LENGTH_LONG).show();
                winner=currPlayer;
                showReplayDialog(player.getName());
            }
            else
                playSound(SET_WON);
        }
        else
            playSound(LEG_WON);
        /**  Start next Leg **/
        gameStart=(gameStart+1)%playersView.size();
        for (TablePlayer p:playersView) {
            p.setScore(gameScore);
            p.setSelected(false);
        }
        player.updateStats();
        switchPlayer(gameStart);
    }
    /** Dialog after Match is over, quit or replay (start GameSettings Acvitity)  **/
    private void showReplayDialog(String playerWon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder.setTitle(playerWon+" won the Match!")
                .setMessage("Replay Match?")
                .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(GameActivity.this,GameSettingsActivity.class);
                        intent.putParcelableArrayListExtra("players",players);
                        intent.putExtra("first",gameStart);
                        saveStats();
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(GameActivity.this,MainActivity.class);
                        saveStats();
                        startActivity(intent);
                        finish();
                    }
                });
         builder.show();
    }

    /** Deselect (TableRow,Score) currPlayer, select nextPlayer **/
    private void switchPlayer(int nextPlayer) {
        if(currPlayer>=0) {
            playersView.get(currPlayer).setSelected(false);
        }
        currPlayer=nextPlayer;
        TablePlayer player=playersView.get(currPlayer);
        playersView.get(currPlayer).setSelected(true);
        txtScore.setText(player.getScore()+"");
        txtCurrScore.setText("");
        txtPlayer.setText(player.getName());
        Double avg = (player.player.gameAvg / (double) player.player.darts) * 3;
        txtAvg.setText(avg.isNaN() ? "-": String.format("%.2f", avg));
        Double avgAllTime = (player.player.avg / (double) player.player.matches) * 3;
        txtAvgAllTime.setText(avgAllTime.isNaN() ? "-": String.format("%.2f", avgAllTime));
        Double avgDoubles = (player.player.doubleGame / (double) player.player.doubleDarts) * 100;
        txtDoubles.setText(avgDoubles.isNaN() ? "-": String.format("%.2f%%%n", avgDoubles));
        /** Get Finish recommendation if available **/
        String finish=finishMap.get(player.player.score);
        if(finish==null)
            finish="Impossible";
        txtFinish.setText(finish);
    }

    @Override
    protected void onDestroy() {
        this.mWakeLock.release();
        soundPool.release();
        soundPool=null;
        super.onDestroy();
    }

    /** Save Match Stats to Database **/
    private void saveStats() {
        APIPlayer api=new APIPlayer(this);
        for (int i = 0; i < players.size(); i++) {
            Player p=players.get(i);
            if(p.darts>0) {
                p.matches++;
                int avg = p.gameAvg / p.darts;
                p.avg += avg;
                if(p.doubleDarts>0)
                    p.doubles+=p.doubleGame/(double)p.doubleDarts;
            }
            if(winner==i)
                p.won++;
            api.updatePlayer(p);
        }
    }

    public void getSoundIDs() {
        soundIDs=new ArrayList<>();
        for (int i = 0; i <= 180; i++) {
            int id=getResources().getIdentifier("dsc_pro"+i, "raw", getPackageName());
            if(id !=-1)
                soundIDs.add(id);
            if(id==0)
                System.out.println(i+" missing");
        }
        soundIDs.add(getResources().getIdentifier("dsc_proleg", "raw", getPackageName()));  // Leg Won
        soundIDs.add(getResources().getIdentifier("dsc_proset", "raw", getPackageName()));  // Set Won
        soundIDs.add(getResources().getIdentifier("dsc_prowhatagame", "raw", getPackageName()));  // Match Won
        soundIDs.add(getResources().getIdentifier("dsc_proletsplaydarts", "raw", getPackageName()));  // Starting Sound
    }

    public void onBustClick(View view) {
        hapticFeedback();
        scored(0);
    }

    private void hapticFeedback() {
        getWindow().getDecorView().performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
    }

    /** Withdraw last throw, reselect last Player **/
    public void onBackClicked(View view) {
        hapticFeedback();
        if(!scoreStack.isEmpty()){
            int lastPlayer=currPlayer-1 <0? playersView.size()-1 : currPlayer-1;
            int lastScore=scoreStack.removeFirst();
            TablePlayer player=playersView.get(lastPlayer);
            player.player.gameAvg-=lastScore;
            player.player.darts-=3;
            player.setScore(player.player.score+lastScore);
            switchPlayer(lastPlayer);
        }
    }

    /** Show Dialog to Quit Match **/
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder.setTitle("Quit Match")
                .setMessage("Do you really want to quit the Match?")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GameActivity.super.onBackPressed();
                        saveStats();
                        finish();
                    }
                });
        builder.show();
    }
}
