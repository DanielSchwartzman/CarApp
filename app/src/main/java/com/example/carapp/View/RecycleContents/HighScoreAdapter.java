package com.example.carapp.View.RecycleContents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.carapp.General_Singletons.SignalGenerator;
import com.example.carapp.HighScore.HighScoreList;
import com.example.carapp.R;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreViewHolder>
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    Context context;
    HighScoreList highScoreList;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Constructor

    public HighScoreAdapter(Context context, HighScoreList highScoreList)
    {
        this.context = context;
        this.highScoreList = highScoreList;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Overridden interface methods

    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new HighScoreViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int position)
    {
        String pos=addPostfix((position+1)+"");
        holder.position.setText(pos);
        holder.txt.setText( highScoreList.getAllScores().get(position).getScoreAsString());
        holder.button.setOnClickListener(View->
                SignalGenerator.getInstance().setFocusOnLocation(highScoreList.getAllScores().get(position).getLongitude(),highScoreList.getAllScores().get(position).getLatitude()));
    }

    @Override
    public int getItemCount()
    {
        return highScoreList.getAllScores().size();
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //General methods

    public String addPostfix(String pos)
    {
        switch (pos)
        {
            case "1":
                pos += "st:";
                break;
            case "2":
                pos += "nd:";
                break;
            case "3":
                pos += "rd:";
                break;
            default:
                pos += "th:";
                break;
        }
        return pos;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

}
