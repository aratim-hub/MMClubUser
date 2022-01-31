package com.appmart.mmcuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.MCQList;
import com.appmart.mmcuser.models.MCQListData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.appmart.mmcuser.fragments.MCQList.CORRECTANSWER;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MCQListAdapter extends RecyclerView.Adapter<MCQListAdapter.ViewHolder> {

    MCQList mcqList;
    private Context context;
    private List<MCQListData> my_data;

    public MCQListAdapter(Context context, List<MCQListData> dataReceived, MCQList myTeamList) {
        this.context = context;
        this.my_data = dataReceived;
        this.mcqList = myTeamList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_list_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtQuestion.setText(my_data.get(position).getQuestion_id() + ". " + my_data.get(position).getQuestion());
        holder.txtOptionA.setText(my_data.get(position).getOption_a());
        holder.txtOptionB.setText(my_data.get(position).getOption_b());

        holder.txtOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_data.get(position).getCorrect_answer().equalsIgnoreCase("a")) {
                    CORRECTANSWER.set(Integer.parseInt(my_data.get(position).getQuestion_id())-1, true);
                } else {
                    CORRECTANSWER.set(Integer.parseInt(my_data.get(position).getQuestion_id())-1, false);
                }

            }
        });
        holder.txtOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_data.get(position).getCorrect_answer().equalsIgnoreCase("b")) {
                    CORRECTANSWER.set(Integer.parseInt(my_data.get(position).getQuestion_id())-1, true);

                } else {
                    CORRECTANSWER.set(Integer.parseInt(my_data.get(position).getQuestion_id())-1, false);

                }

            }
        });
        CORRECTANSWER.add(false);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        RadioButton txtOptionA, txtOptionB;
        RadioGroup radioGroup;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtOptionA = itemView.findViewById(R.id.txtOptionA);
            txtOptionB = itemView.findViewById(R.id.txtOptionB);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }
}
