package com.cfpj.tiktaktoe;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LoadGameAdapter extends RecyclerView.Adapter<LoadGameAdapter.ViewHolder> {

    private int[] id, player1Score, player2Score;
    private String[] player1Name, player2Name;
    private Listener listener;

    public static interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }


    public LoadGameAdapter(int[] id, String[] player1Name, String[] player2Name, int[] player1Score, int[] player2Score){
        this.id = id;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        CardView v = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_loaded_game, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;
        TextView tvGameNumber = cardView.findViewById(R.id.tv_game_number);
        TextView tvPlayer1Name = cardView.findViewById(R.id.tv_player1_name);
        TextView tvPlayer1Score = cardView.findViewById(R.id.tv_player1_score);
        TextView tvPlayer2Name = cardView.findViewById(R.id.tv_player2_name);
        TextView tvPlayer2Score = cardView.findViewById(R.id.tv_player2_score);

        tvGameNumber.setText(String.valueOf(id[i]));
        tvPlayer1Name.setText(player1Name[i]);
        tvPlayer1Score.setText(String.valueOf(player1Score[i]));
        tvPlayer2Name.setText(player2Name[i]);
        tvPlayer2Score.setText(String.valueOf(player2Score[i]));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;

        }
    }
}
