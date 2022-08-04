package com.piero.spiritguardians;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piero.spiritguardians.Cards.Card;
import com.piero.spiritguardians.Cards.Deck1;
import com.piero.spiritguardians.Game.Match;
import com.piero.spiritguardians.Players.Player;
import com.piero.spiritguardians.Utility.GuestNameDialogFragment;
import com.piero.spiritguardians.Utility.WinDefeatDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class GameActivity extends AppCompatActivity {

    private Context context = this;

    private int turn = 1;
    private Match activeMatch;
    private Player player_self;
    private Player player_opponent;
    private ArrayList<Card> deck_self = new ArrayList<>();
    private ArrayList<Card> deck_opponent = new ArrayList<>();
    private boolean turn_self = false;
    private CountDownTimer countDownTimer;
    private String username;

    private int cardPosition1 = 0;
    private int cardPosition2 = 1;
    private int cardPosition3 = 2;
    private int cardPosition4 = 3;
    private int cardPosition5 = 4;

    private TextView player1Name;
    private TextView hp1;
    private TextView fence1;
    private TextView worker1;
    private TextView soldier1;
    private TextView magic1;
    private TextView gold1;
    private ImageView card1;
    private ImageView card2;
    private ImageView card3;
    private ImageView card4;
    private ImageView card5;
    private ImageView castle1;

    private TextView player2Name;
    private TextView hp2;
    private TextView fence2;
    private TextView worker2;
    private TextView soldier2;
    private TextView magic2;
    private TextView gold2;
    private ImageView card1_opponent;
    private ImageView card2_opponent;
    private ImageView card3_opponent;
    private ImageView card4_opponent;
    private ImageView card5_opponent;
    private ImageView castle2;

    private Button skip;
    private Button clearSelect;
    private TextView turno;
    private ImageView card;
    private TextView contador;

    private int castleHeight = 200;
    private int castleWidth = 300;

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Assigning the references to every Object in the Player1 Layout
        player1Name = findViewById(R.id.txt_player1);
        hp1 = findViewById(R.id.txt_hp);
        fence1 = findViewById(R.id.txt_fence);
        worker1 = findViewById(R.id.txt_worker);
        soldier1 = findViewById(R.id.txt_soldier);
        magic1 = findViewById(R.id.txt_magic);
        gold1 = findViewById(R.id.txt_gold);
        card1 = findViewById(R.id.img_card1);
        card2 = findViewById(R.id.img_card2);
        card3 = findViewById(R.id.img_card3);
        card4 = findViewById(R.id.img_card4);
        card5 = findViewById(R.id.img_card5);
        castle1 = findViewById(R.id.img_castle1);
        //Getting Max Height and Width for castles
        castleHeight = castle1.getHeight();
        castleWidth = castle1.getWidth();
        //Assigning the references to every Object in the Player2 Layout
        player2Name = findViewById(R.id.txt_player2);
        hp2 = findViewById(R.id.txt_hp_opponent);
        fence2 = findViewById(R.id.txt_fence_opponent);
        worker2 = findViewById(R.id.txt_worker_opponent);
        soldier2 = findViewById(R.id.txt_soldier_opponent);
        magic2 = findViewById(R.id.txt_magic_opponent);
        gold2 = findViewById(R.id.txt_gold_opponent);
        card1_opponent = findViewById(R.id.img_card1_opponent);
        card2_opponent = findViewById(R.id.img_card2_opponent);
        card3_opponent = findViewById(R.id.img_card3_opponent);
        card4_opponent = findViewById(R.id.img_card4_opponent);
        card5_opponent = findViewById(R.id.img_card5_opponent);
        castle2 = findViewById(R.id.img_castle2);

        skip = findViewById(R.id.btn_skip);
        clearSelect = findViewById(R.id.btn_clearSelect);
        turno = findViewById(R.id.txt_turno);
        card = findViewById(R.id.img_card);
        contador = findViewById(R.id.txt_contador);
        //Setting starting values for player 1
        username = getIntent().getStringExtra("guestName");
        Player player1_default = new Player("", "Deck 1",25,0,2,2,2,10,10,10,10,true);
        //Setting starting values for player 2
        Player player2_default = new Player("","Deck 1",25,0,2,2,2,10,10,10,10,false);

        //If Player entering the room is Player2, asign Player2
        //Else asign Player1
        boolean isPlayer2 = getIntent().getBooleanExtra("isPlayer2", false);
        //The player joined the room
        if(isPlayer2) {
            player_self = player2_default;
            player_opponent = player1_default;
            turn = 1;
            skip.setVisibility(View.GONE);
        }
        //The player created the room
        else {
            player_self = player1_default;
            player_opponent = player2_default;
            turn = 1;
            skip.setVisibility(View.VISIBLE);
        }
        player_self.setName(username);
        //Create Match
        String matchCode = getIntent().getStringExtra("matchCode");
        if(matchCode != null) activeMatch = new Match(matchCode, player_self, player_opponent, turn);
        else activeMatch = new Match("MTEST1", player_self, player_opponent, turn);

        updateMatch(player_self, player_opponent, turn);

        if(player_self.isFirstPlayer() && turn ==1){
            turno.setText("tu turno "+turn);
        } else turno.setText("turno oponente "+turn);

        //Getting Castle 1 satrting size
        castle1.setMaxHeight(castleHeight*(player_self.getCastle_points()/100));
        castle1.setMaxWidth(castleWidth*(player_self.getCastle_points()/100));
        //Getting Castle 2 starting size
        castle2.setMaxHeight(castleHeight*(player_opponent.getCastle_points()/100));
        castle2.setMaxWidth(castleWidth*(player_opponent.getCastle_points()/100));
        //Shuflling your deck
        deck_self = new Deck1().getDeck();
        deck_opponent = new Deck1().getDeck();
        Collections.shuffle(deck_self);
        Collections.shuffle(deck_opponent);
        //Assigning the img to every card in your starting hand
        card1.setImageResource(deck_self.get(0).getImage());
        card2.setImageResource(deck_self.get(1).getImage());
        card3.setImageResource(deck_self.get(2).getImage());
        card4.setImageResource(deck_self.get(3).getImage());
        card5.setImageResource(deck_self.get(4).getImage());

        player1Name.setText(player_self.getName());
        player2Name.setText(player_opponent.getName());

        updateTurn();
    }

    View selected;

    public void onClickCard(View view) {
        try {
            //Checks if the card selected is 1,2,3,4 or 5. If the view clicked is not a card then returns null.
            isSelectedCard(view, card1);
            isSelectedCard(view, card2);
            isSelectedCard(view, card3);
            isSelectedCard(view, card4);
            isSelectedCard(view, card5);

            if (view == clearSelect) {
                card.setImageResource(R.drawable.cartaparteposterior);
                selected = null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onClickSkip(View view) {
        try{
            calculateTurnAndPlayerstats();
        //onClick Event will only trigger if it's the player's turn
        if((player_self.isFirstPlayer() && turn % 2 !=0) || (!player_self.isFirstPlayer() && turn % 2 == 0)) {

            if(view == skip){
                if (selected == card1) {
                    if (cardAction(cardPosition1)){
                        Toast.makeText(this, "Card Played: " + deck_self.get(cardPosition1).getTitle(), Toast.LENGTH_SHORT).show();
                        cardPosition1 = Math.round((float) turn/2) + 5;
                        card1.setImageResource(deck_self.get(cardPosition1).getImage());
                    }
                }
                else if (selected == card2) {
                    if(cardAction(cardPosition2)) {
                        Toast.makeText(this, "Card Played: " + deck_self.get(cardPosition2).getTitle(), Toast.LENGTH_SHORT).show();
                        cardPosition2 = Math.round((float) turn/2) + 5;
                        card2.setImageResource(deck_self.get(cardPosition2).getImage());
                    }
                }
                else if (selected == card3) {
                    if(cardAction(cardPosition3)) {
                        Toast.makeText(this, "Card Played: " + deck_self.get(cardPosition3).getTitle(), Toast.LENGTH_SHORT).show();
                        cardPosition3 = Math.round((float) turn/2) + 5;
                        card3.setImageResource(deck_self.get(cardPosition3).getImage());
                    }
                }
                else if (selected == card4) {
                    if(cardAction(cardPosition4)) {
                        Toast.makeText(this, "Card Played: " + deck_self.get(cardPosition4).getTitle(), Toast.LENGTH_SHORT).show();
                        cardPosition4 = Math.round((float) turn/2) + 5;
                        card4.setImageResource(deck_self.get(cardPosition4).getImage());
                    }
                }
                else if (selected == card5) {
                    if(cardAction(cardPosition5)) {
                        Toast.makeText(this, "Card Played: " + deck_self.get(cardPosition5).getTitle(), Toast.LENGTH_SHORT).show();
                        cardPosition5 = Math.round((float) turn/2) + 5;
                        card5.setImageResource(deck_self.get(cardPosition5).getImage());
                    }
                }
                turn++;
                endTurnAction();
                card.setImageResource(R.drawable.cartaparteposterior);
                selected = null;
                contador.setText("00");
                if(countDownTimer != null){
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
            }
        }

    }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Verifies whether is your turn or the opponent's, also if the game is over.
    public void updateTurn(){

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
        try {
                    if (player_self.getCastle_points() <= 0 || player_opponent.getCastle_points() <= 0 || player_self.getCastle_points() >= 100 || player_opponent.getCastle_points() >= 100) {
                        if (player_self.getCastle_points() <= 0 || player_opponent.getCastle_points() >= 100) {
                            String dialogMessage = "Juego Terminado: Ganador " + player_opponent.getName();
                            WinDefeatDialogFragment dialog = new WinDefeatDialogFragment(context, dialogMessage, player_self.getName());
                            dialog.show(getSupportFragmentManager(), "game");
                        } else {
                            String dialogMessage = "Juego Terminado: Ganador " + player_self.getName();
                            WinDefeatDialogFragment dialog = new WinDefeatDialogFragment(context, dialogMessage, player_self.getName());
                            dialog.show(getSupportFragmentManager(), "game");
                        }
                        timer.cancel();
                    } else
                        calculateTurnAndPlayerstats();
                    //if it's player turn or else if it's not player turn
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Stuff that updates the UI
                    if((player_self.isFirstPlayer() && turn % 2 !=0) || (!player_self.isFirstPlayer() && turn % 2 == 0)){
                        if(turn_self == false) {
                            turn_self = true;
                            setMaxTimeForTurn();
                        }
                        turno.setText("Turno "+turn);
                        skip.setVisibility(View.VISIBLE);
                    } else {
                        if(turn_self == true) turn_self = false;
                        turno.setText("Turno "+turn);
                        skip.setVisibility(View.GONE);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
            }
        }, 0, 1000);
    }

    public boolean cardAction(int cardPosition){
        switch (deck_self.get(cardPosition).getCode()){
            //Ataque 3
            case 1 :    if(player_self.getSoldier()>=5){
                        player_opponent.setCastle_points(player_opponent.getCastle_points()- calculateDamage(3));
                        player_self.setSoldier(player_self.getSoldier()-5);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Escudo 5
            case 2 :    if(player_self.getWorker()>=3) {
                        player_self.setFence_points(player_self.getFence_points() + 5);
                        player_self.setWorker(player_self.getWorker() - 3);
                        return true;
                        } else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Obreros +1
            case 3 :    if(player_self.getWorker()>=8) {
                        player_self.setWorker_points(player_self.getWorker_points() + 1);
                        player_self.setWorker(player_self.getWorker() - 8);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Soldados +1
            case 4 :    if(player_self.getSoldier()>=8) {
                        player_self.setSoldier_points(player_self.getSoldier_points() + 1);
                        player_self.setSoldier(player_self.getSoldier() - 8);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Magia +1
            case 5 :    if(player_self.getMagic()>=8) {
                        player_self.setMagic_points(player_self.getMagic_points()+1);
                        player_self.setMagic(player_self.getMagic()-8);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Economia +10
            case 6 :    if(player_self.getGold()>=5) {
                        player_self.setGold(player_self.getGold()+10);
                        player_self.setGold(player_self.getGold()-5);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Maldición
            case 7 :    if(player_self.getMagic()>=25) {
                        player_self.setCastle_points(player_self.getCastle_points()+1);
                        player_self.setFence_points(player_self.getFence_points()+1);
                        player_self.setWorker_points(player_self.getWorker_points()+1);
                        player_self.setWorker(player_self.getWorker()+1);
                        player_self.setSoldier_points(player_self.getSoldier_points()+1);
                        player_self.setSoldier(player_self.getSoldier()+1);
                        player_self.setMagic_points(player_self.getMagic_points()+1);
                        player_self.setMagic(player_self.getMagic()+1);
                        player_self.setGold(player_self.getGold()+1);
                        player_self.setMagic(player_self.getMagic()-25);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Ladrón
            case 8 :    if(player_self.getGold()>=12) {
                        player_opponent.setWorker(player_opponent.getWorker()-5);
                        player_opponent.setSoldier(player_opponent.getSoldier()-5);
                        player_opponent.setMagic(player_opponent.getMagic()-5);
                        player_opponent.setGold(player_opponent.getGold()-5);
                        player_self.setWorker(player_self.getWorker()+5);
                        player_self.setSoldier(player_self.getSoldier()+5);
                        player_self.setMagic(player_self.getMagic()+5);
                        player_self.setGold(player_self.getGold()+5);
                        player_self.setGold(player_self.getGold()-12);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Ataque 5
            case 9 :    if(player_self.getSoldier()>=8){
                        player_opponent.setCastle_points(player_opponent.getCastle_points() - calculateDamage(5));
                        player_self.setSoldier(player_self.getSoldier()-8);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Ataque 10
            case 10 :   if(player_self.getSoldier()>=15){
                        player_opponent.setCastle_points(player_opponent.getCastle_points()-calculateDamage(10));
                        player_self.setSoldier(player_self.getSoldier()-15);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Escudo 10
            case 11 :   if(player_self.getWorker()>=5){
                        player_self.setFence_points(player_self.getFence_points()+10);
                        player_self.setWorker(player_self.getWorker()-5);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Bancarrota
            case 12 :   if(player_self.getGold()>=15){
                        player_opponent.setWorker(player_opponent.getWorker()-10);
                        player_opponent.setSoldier(player_opponent.getSoldier()-10);
                        player_opponent.setMagic(player_opponent.getMagic()-10);
                        player_opponent.setGold(player_opponent.getGold()-10);
                        player_self.setGold(player_self.getGold()-15);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Muralla
            case 13 :   if(player_self.getWorker()>=15){
                        player_self.setFence_points(player_self.getFence_points()+20);
                        player_self.setWorker(player_self.getWorker()-15);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Quita Muralla
            case 14 :   if(player_self.getWorker()>=10){
                        player_self.setCastle_points(player_self.getCastle_points()+8);
                        player_self.setFence_points(player_self.getFence_points()-16);
                        player_self.setWorker(player_self.getWorker()-10);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Castillo
            case 15 :   if(player_self.getWorker()>=20){
                        player_self.setCastle_points(player_self.getCastle_points()+24);
                        player_self.setWorker(player_self.getWorker()-20);
                        return true;}
                        else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Muerte
            case 16 :   if(player_self.getSoldier()>=20){
                        player_opponent.setCastle_points(player_opponent.getCastle_points()-calculateDamage(28));
                        player_self.setSoldier(player_self.getSoldier()-20);
                        return true;}
                        else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Dragon
            case 17 :   if(player_self.getMagic()>=20) {
                        player_opponent.setCastle_points(player_opponent.getCastle_points()-calculateDamage(25));
                        player_self.setMagic(player_self.getMagic()-20);
                        return true;}
                        else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            //Babilonia
            case 18 :    if(player_self.getWorker()>=25){
                        player_self.setCastle_points(player_self.getCastle_points()+32);
                        player_self.setWorker(player_self.getWorker()-25);
                        return true;
                        }else {Toast.makeText(this, "No tienes suficientes recursos", Toast.LENGTH_SHORT).show();
                        return false;}
            default : return false;
        }
    }

    public void endTurnAction(){
        //Player 1 gets extra resources at the end of each turn
        player_self.setWorker(player_self.getWorker()+ player_self.getWorker_points());
        player_self.setSoldier(player_self.getSoldier()+ player_self.getSoldier_points());
        player_self.setMagic(player_self.getMagic()+ player_self.getMagic_points());
        player_self.setGold(player_self.getGold()+2);
        //Update the player1 stats
        hp1.setText(player_self.getCastle_points()+"");
        fence1.setText(player_self.getFence_points()+"");
        worker1.setText(player_self.getWorker()+"");
        soldier1.setText(player_self.getSoldier()+"");
        magic1.setText(player_self.getMagic()+"");
        gold1.setText(player_self.getGold()+"");
        //Update the player2 stats
        hp2.setText(player_opponent.getCastle_points()+"");
        fence2.setText(player_opponent.getFence_points()+"");
        worker2.setText(player_opponent.getWorker()+"");
        soldier2.setText(player_opponent.getSoldier()+"");
        magic2.setText(player_opponent.getMagic()+"");
        gold2.setText(player_opponent.getGold()+"");

        updateMatch(player_self, player_opponent, turn);
    }

    //update values on DB
    public void updateMatch(Player player_self, Player player_opponent, int turn) {
        try {
            if ((player_self.isFirstPlayer() && turn % 2 != 0) || (!player_self.isFirstPlayer() && turn % 2 == 0)) {
                turno.setText("Turno " + turn);
                skip.setVisibility(View.VISIBLE);
            } else {
                turno.setText("Turno " + turn);
                skip.setVisibility(View.GONE);
            }


            DatabaseReference myMatchRef = FirebaseDatabase.getInstance().getReference("matches/" + activeMatch.getMatchCode());
            if (player_self.isFirstPlayer()) {
                myMatchRef.child("0").setValue(player_self);
                myMatchRef.child("1").setValue(player_opponent);
            } else {
                myMatchRef.child("0").setValue(player_opponent);
                myMatchRef.child("1").setValue(player_self);
            }
            myMatchRef.child("2").setValue(turn);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //updates the values on Screen
    public void calculateTurnAndPlayerstats(){
        try {
            //updateMatch(player_self, player_opponent, turn);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("matches/" + activeMatch.getMatchCode());

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    List<Object> value = (List<Object>) dataSnapshot.getValue();
                    if(value != null) {
                        turn = Integer.parseInt(value.get(2).toString());
                        if (player_self.isFirstPlayer()) {
                            player_self = mapToPlayer(value, 0, player_self);
                            player_opponent = mapToPlayer(value, 1, player_opponent);
                            if(!player_self.getName().equals("")) player1Name.setText(player_self.getName());
                            if(!player_opponent.getName().equals("")) player2Name.setText(player_opponent.getName());
                        } else {
                            player_self = mapToPlayer(value, 1, player_self);
                            player_opponent = mapToPlayer(value, 0, player_opponent);
                            if(!player_self.getName().equals("")) player2Name.setText(player_self.getName());
                            if(!player_opponent.getName().equals("")) player1Name.setText(player_opponent.getName());
                        }
                    }

                    //Update the player1 stats
                    hp1.setText(player_self.getCastle_points()+"");
                    fence1.setText(player_self.getFence_points()+"");
                    worker1.setText(player_self.getWorker()+"");
                    soldier1.setText(player_self.getSoldier()+"");
                    magic1.setText(player_self.getMagic()+"");
                    gold1.setText(player_self.getGold()+"");
                    //Update the player2 stats
                    hp2.setText(player_opponent.getCastle_points()+"");
                    fence2.setText(player_opponent.getFence_points()+"");
                    worker2.setText(player_opponent.getWorker()+"");
                    soldier2.setText(player_opponent.getSoldier()+"");
                    magic2.setText(player_opponent.getMagic()+"");
                    gold2.setText(player_opponent.getGold()+"");
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Player mapToPlayer(List<Object> value, int playerLocation, Player player){
        String valueString = value.get(playerLocation).toString();
        String[] playerString = valueString.substring(1, valueString.length()-1).split("=");

        String name = playerString[9].split(",")[0];
        String deckCode = playerString[3].split(",")[0];
        int castle_points = Integer.parseInt(playerString[10].split(",")[0]);
        int fence_points = Integer.parseInt(playerString[4].split(",")[0]);
        int worker_points = Integer.parseInt(playerString[14].split(",")[0]);
        int soldier_points = Integer.parseInt(playerString[11].split(",")[0]);
        int magic_points = Integer.parseInt(playerString[5].split(",")[0]);
        int worker = Integer.parseInt(playerString[13].split(",")[0]);
        int soldier = Integer.parseInt(playerString[8].split(",")[0]);
        int magic = Integer.parseInt(playerString[1].split(",")[0]);
        int gold = Integer.parseInt(playerString[6].split(",")[0]);
        boolean isFirstPlayer = false;
        if(playerString[2].split(",")[0].equals("true")) isFirstPlayer = true;
        else isFirstPlayer = false;

        return new Player(name, deckCode, castle_points,fence_points,worker_points,soldier_points,magic_points,worker,soldier,magic,gold,isFirstPlayer);

    }

    public void isSelectedCard(View view, ImageView cardClicked){
        if(view == cardClicked){
            card.setImageDrawable(cardClicked.getDrawable());
            selected = cardClicked;
        }
    }

    public int calculateDamage(int atk){
        if (player_opponent.getFence_points() <= 0){
            return atk;
        }
        else if (player_opponent.getFence_points() >= atk){
            player_opponent.setFence_points(player_opponent.getFence_points() - atk);
            return 0;
        }
        else if(player_opponent.getFence_points() < atk){
            atk = atk - player_opponent.getFence_points();
            player_opponent.setFence_points(0);
            return atk;
        } else return atk;
    }

    public void setMaxTimeForTurn(){
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                contador.setText(String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L));
            }

            public void onFinish() {
                contador.setText("00");
                turn++;
                endTurnAction();
                card.setImageResource(R.drawable.cartaparteposterior);
                selected = null;
            }
        }.start();
    }

}