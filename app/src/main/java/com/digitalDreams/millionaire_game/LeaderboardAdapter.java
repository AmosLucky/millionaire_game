package com.digitalDreams.millionaire_game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderBoardVH> {
    private Context context;
    private List<PlayerObject> list;

    public LeaderboardAdapter(Context context, List<PlayerObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LeaderBoardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
                view = layoutInflater.inflate(R.layout.leader_score_item, parent, false);
                return new LeaderBoardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardVH holder, int position) {
        PlayerObject obj =list.get(position);
        int newPosition = Integer.parseInt( obj.getPosition()) + 1;

        String rank = String.valueOf(newPosition);

        //String rank = obj.getPosition();
        String playerName = obj.getPlayerName();
        String highscore = obj.getHighscore();

        holder.positionTXT.setText(rank);
        holder.playerNameTxt.setText(playerName);
        holder.playerScoreTxt.setText(highscore);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class LeaderBoardVH extends RecyclerView.ViewHolder{
        TextView positionTXT,playerNameTxt,playerScoreTxt;

        public LeaderBoardVH(@NonNull View itemView) {
            super(itemView);
            positionTXT = itemView.findViewById(R.id.position);
            playerNameTxt = itemView.findViewById(R.id.player_name);
            playerScoreTxt = itemView.findViewById(R.id.highscore);
        }
    }


}
