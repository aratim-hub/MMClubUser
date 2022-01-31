package com.appmart.mmcuser.trainingvideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.fragments.MCQList;
import com.appmart.mmcuser.models.MCQListData;

import java.util.List;

import static com.appmart.mmcuser.fragments.MCQList.CORRECTANSWER;
import static com.appmart.mmcuser.trainingvideo.TrainingVideoMCQActivity.CORRECTOPTION;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class TraningVideoMCQListAdapter extends RecyclerView.Adapter<TraningVideoMCQListAdapter.ViewHolder> {

    private Context context;
    private List<TrainingVideoMCQListData> my_data;

    public TraningVideoMCQListAdapter(Context context, List<TrainingVideoMCQListData> dataReceived) {
        this.context = context;
        this.my_data = dataReceived;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_video_mcq_list_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtQuestion.setText((position+1) + ". " + my_data.get(position).getQuestion());
        holder.txtOptionA.setText(my_data.get(position).getOption_a());
        holder.txtOptionB.setText(my_data.get(position).getOption_b());
        holder.txtOptionC.setText(my_data.get(position).getOption_c());
        holder.txtOptionD.setText(my_data.get(position).getOption_d());

        holder.txtOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_data.get(position).getCorrect_answer().equalsIgnoreCase("a")) {
                    CORRECTOPTION.set(position, true);
                } else {
                    CORRECTOPTION.set(position, false);
                }

            }
        });
        holder.txtOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_data.get(position).getCorrect_answer().equalsIgnoreCase("b")) {
                    CORRECTOPTION.set(position, true);

                } else {
                    CORRECTOPTION.set(position, false);

                }

            }
        });
        holder.txtOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_data.get(position).getCorrect_answer().equalsIgnoreCase("c")) {
                    CORRECTOPTION.set(position, true);

                } else {
                    CORRECTOPTION.set(position, false);

                }

            }
        });
        holder.txtOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_data.get(position).getCorrect_answer().equalsIgnoreCase("d")) {
                    CORRECTOPTION.set(position, true);

                } else {
                    CORRECTOPTION.set(position, false);

                }

            }
        });
//        CORRECTOPTION.add(false);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuestion;
        RadioButton txtOptionA, txtOptionB, txtOptionC, txtOptionD;
        RadioGroup radioGroup;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            txtOptionA = itemView.findViewById(R.id.txtOptionA);
            txtOptionB = itemView.findViewById(R.id.txtOptionB);
            txtOptionC = itemView.findViewById(R.id.txtOptionC);
            txtOptionD = itemView.findViewById(R.id.txtOptionD);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }
}
