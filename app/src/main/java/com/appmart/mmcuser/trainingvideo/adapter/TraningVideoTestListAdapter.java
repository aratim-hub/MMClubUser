package com.appmart.mmcuser.trainingvideo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.trainingvideo.TrainingVideoMCQListData;
import com.appmart.mmcuser.trainingvideo.model.VedioQuestionList;

import java.util.List;

import static com.appmart.mmcuser.trainingvideo.TrainingVideoMCQActivity.CORRECTOPTION;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class TraningVideoTestListAdapter extends RecyclerView.Adapter<TraningVideoTestListAdapter.ViewHolder> {

    private Context context;
    private List<VedioQuestionList> my_data;
    private final OnItemClickListener listener;
    String video_status;
    public interface OnItemClickListener {
        void onItemClick(List<VedioQuestionList> item);
    }

    public TraningVideoTestListAdapter(Context context, List<VedioQuestionList> dataReceived, String status, OnItemClickListener listener) {
        this.context = context;
        this.my_data = dataReceived;
        this.listener = listener;
        this.video_status = status;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_video_test_list_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtQuestion.setText((position+1) + ". " + my_data.get(position).getQuestion());

        if (my_data.get(position).getStatus() != null ){
            if (my_data.get(position).getStatus().equals("0")){
                holder.txtQuestion.setTextColor(context.getResources().getColor(R.color.choclateColor));
            }
        }
        holder.txtOptionA.setText(my_data.get(position).getOption_a());
        holder.txtOptionB.setText(my_data.get(position).getOption_b());
        holder.txtOptionC.setText(my_data.get(position).getOption_c());
        holder.txtOptionD.setText(my_data.get(position).getOption_d());

        if (my_data.get(position).getStatus().equals("2")){
            holder.txtOptionA.setChecked(false);
            holder.txtOptionB.setChecked(false);
            holder.txtOptionC.setChecked(false);
            holder.txtOptionD.setChecked(false);
            my_data.get(position).setMy_answer("");
            my_data.get(position).setStatus("");
        }else{
            if (video_status.equals("1")){
                if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_a())){
                    holder.txtOptionA.setChecked(true);
                    holder.txtOptionB.setChecked(false);
                    holder.txtOptionC.setChecked(false);
                    holder.txtOptionD.setChecked(false);
                    my_data.get(position).setMy_answer(my_data.get(position).getOption_a());
                    my_data.get(position).setStatus("1");
                }else if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_b())){
                    holder.txtOptionB.setChecked(true);
                    holder.txtOptionA.setChecked(false);
                    holder.txtOptionC.setChecked(false);
                    holder.txtOptionD.setChecked(false);
                    my_data.get(position).setMy_answer(my_data.get(position).getOption_b());
                    my_data.get(position).setStatus("1");
                }else if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_c())){
                    holder.txtOptionC.setChecked(true);
                    holder.txtOptionA.setChecked(false);
                    holder.txtOptionB.setChecked(false);
                    holder.txtOptionD.setChecked(false);
                    my_data.get(position).setMy_answer(my_data.get(position).getOption_c());
                    my_data.get(position).setStatus("1");
                }else if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_d())){
                    holder.txtOptionD.setChecked(true);
                    holder.txtOptionA.setChecked(false);
                    holder.txtOptionB.setChecked(false);
                    holder.txtOptionC.setChecked(false);
                    my_data.get(position).setMy_answer(my_data.get(position).getOption_d());
                    my_data.get(position).setStatus("1");
                }
            }
        }



        Log.i("TAG", "onBindViewHolder: "+position+" : "+my_data.get(position).getStatus()+" : "+my_data.get(position).getMy_answer());
        if (my_data.get(position).getStatus() == null){
            holder.txtOptionA.setChecked(false);
            holder.txtOptionB.setChecked(false);
            holder.txtOptionC.setChecked(false);
            holder.txtOptionD.setChecked(false);
        }else  if (my_data.get(position).getStatus().equals("2") || my_data.get(position).getStatus().equals("")){
            holder.txtOptionA.setChecked(false);
            holder.txtOptionB.setChecked(false);
            holder.txtOptionC.setChecked(false);
            holder.txtOptionD.setChecked(false);
        }else{
            if (my_data.get(position).getMy_answer().equals(my_data.get(position).getOption_a())){
                holder.txtOptionA.setChecked(true);
                holder.txtOptionB.setChecked(false);
                holder.txtOptionC.setChecked(false);
                holder.txtOptionD.setChecked(false);
            }else if (my_data.get(position).getMy_answer().equals(my_data.get(position).getOption_b())){
                holder.txtOptionB.setChecked(true);
                holder.txtOptionA.setChecked(false);
                holder.txtOptionC.setChecked(false);
                holder.txtOptionD.setChecked(false);
            }else if (my_data.get(position).getMy_answer().equals(my_data.get(position).getOption_c())){
                holder.txtOptionC.setChecked(true);
                holder.txtOptionA.setChecked(false);
                holder.txtOptionB.setChecked(false);
                holder.txtOptionD.setChecked(false);
            }else if (my_data.get(position).getMy_answer().equals(my_data.get(position).getOption_d())){
                holder.txtOptionD.setChecked(true);
                holder.txtOptionA.setChecked(false);
                holder.txtOptionB.setChecked(false);
                holder.txtOptionC.setChecked(false);
            }
        }

        holder.txtOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtOptionA.setChecked(true);
                holder.txtOptionB.setChecked(false);
                holder.txtOptionC.setChecked(false);
                holder.txtOptionD.setChecked(false);

                if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_a())){
                    my_data.get(position).setStatus("1");
                }else{
                    my_data.get(position).setStatus("0");
                }
                my_data.get(position).setMy_answer(my_data.get(position).getOption_a());
                listener.onItemClick(my_data);
            }
        });
        holder.txtOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtOptionA.setChecked(false);
                holder.txtOptionB.setChecked(true);
                holder.txtOptionC.setChecked(false);
                holder.txtOptionD.setChecked(false);

                if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_b())){
                    my_data.get(position).setStatus("1");
                }else{
                    my_data.get(position).setStatus("0");
                }
                my_data.get(position).setMy_answer(my_data.get(position).getOption_b());
                listener.onItemClick(my_data);
            }
        });
        holder.txtOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtOptionA.setChecked(false);
                holder.txtOptionB.setChecked(false);
                holder.txtOptionC.setChecked(true);
                holder.txtOptionD.setChecked(false);

                if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_c())){
                    my_data.get(position).setStatus("1");
                }else{
                    my_data.get(position).setStatus("0");
                }
                my_data.get(position).setMy_answer(my_data.get(position).getOption_c());
                listener.onItemClick(my_data);
            }
        });
        holder.txtOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtOptionA.setChecked(false);
                holder.txtOptionB.setChecked(false);
                holder.txtOptionC.setChecked(false);
                holder.txtOptionD.setChecked(true);

                if (my_data.get(position).getCorrect_answer().equals(my_data.get(position).getOption_d())){
                    my_data.get(position).setStatus("1");
                }else{
                    my_data.get(position).setStatus("0");
                }
                my_data.get(position).setMy_answer(my_data.get(position).getOption_d());
                listener.onItemClick(my_data);
            }
        });
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
