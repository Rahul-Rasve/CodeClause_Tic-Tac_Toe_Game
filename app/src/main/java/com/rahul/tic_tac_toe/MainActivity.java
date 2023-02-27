package com.rahul.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
    STATES ->
    0 => X
    1 => O
    2 => NULL
    */

    int activePlayer = 0;

    //each element represents a cell in the grid & the value represents the state
    int [] state = {2,2,2,2,2,2,2,2,2};

    //set the winning positions
    int [][] winningPositions = {{0,1,2} , {3,4,5} , {6,7,8} , //horizontal
                                 {0,3,6} , {1,4,7} , {2,5,8} , //vertical
                                 {0,4,8} , {2,4,6}}; //diagonal

    boolean gameActive = true;

    int countOfFilledCells = -1;

    public void taped(View view){

        ImageView img = (ImageView) view;
        int tappedCell = Integer.parseInt(img.getTag().toString()); //extract the cell tag to use it as matrix index

        if(!gameActive){ //someone wins || reset button hit || game is tied
            gameReset(view);
        }
        if(countOfFilledCells == 8){ //tie condition
            Toast.makeText(this, "It's a Tie!", Toast.LENGTH_SHORT).show();
            gameReset(view);
        }
        if(state[0] == 3){ //first tap
            state[0] = 2;
        }
        else if(state[tappedCell] == 2){ //if cell is empty
            ++countOfFilledCells;
            state[tappedCell] = activePlayer;
            img.setTranslationY(-1000f);
            if(activePlayer == 0){
                img.setImageResource(R.drawable.x);
                activePlayer = 1; //change the player
                TextView status = findViewById(R.id.status);
                status.setText("O - Tap to Play");
            }
            else{
                img.setImageResource(R.drawable.o);
                activePlayer = 0; //change player
                TextView status = findViewById(R.id.status);
                status.setText("X - Tap to Play");
            }
            img.animate().translationYBy(1000f).setDuration(400); //400 milli sec
        }

        //check for wins
        for(int[] winningPositions: winningPositions){
            //this checks the 1D array of 2D array
            if(state[winningPositions[0]] == state[winningPositions[1]] &&
                state[winningPositions[1]] == state[winningPositions[2]] &&
                state[winningPositions[0]] != 2){ //should not be null
                gameActive = false;
                String winner;
                if(state[winningPositions[0]] == 0){
                    winner = "Player 1(X) Wins!";
                }
                else{
                    winner = "Player 2(O) Wins!";
                }
                TextView status = findViewById(R.id.status);
                status.setText(winner);
            }
        }
    }

    public void resetButtonTapped(View view){
        countOfFilledCells = -1;
        gameActive = true;
        activePlayer = 0;

        for(int i=0; i< state.length; i++){
            state[i] = 2;
        }

        //replace all image sources with empty
        ((ImageView)findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);

        //change the status
        TextView status = findViewById(R.id.status);
        status.setText("X - Tap to Play");
    }

    //same as reset, but will react on first tap
    public void gameReset(View view){

        resetButtonTapped(view);

        //to reject first tap, we change the state[0] to 3
        state[0] = 3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}