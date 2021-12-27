package com.example.winxbitirmeapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winxbitirmeapp.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder>
{

    private String list[];
    private TextView dots[];

    public SliderAdapter(String[] list , TextView[] dots) {
        this.list = list;
        this.dots = dots;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);

        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull SliderAdapter.MyViewHolder holder, int position) {

        if(position == 0)
        {
            holder.questionTextView1.setText(list[0]);
            holder.questionTextView2.setText(list[1]);
            holder.questionTextView3.setText(list[2]);
            holder.questionTextView4.setText(list[3]);
            holder.questionTextView5.setText(list[4]);
        }
        else if(position == 1)
        {
            holder.questionTextView1.setText(list[5]);
            holder.questionTextView2.setText(list[6]);
            holder.questionTextView3.setText(list[7]);
            holder.questionTextView4.setText(list[8]);
            holder.questionTextView5.setText(list[9]);
        }
        else
        {
            holder.questionTextView1.setText(list[10]);
            holder.questionTextView2.setText(list[11]);
            holder.questionTextView3.setText(list[12]);
            holder.questionTextView4.setText(list[13]);
            holder.questionTextView5.setText(list[14]);
        }

    }





    @Override
    public int getItemCount() {
        return dots.length;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView questionTextView1;
        TextView questionTextView2;
        TextView questionTextView3;
        TextView questionTextView4;
        TextView questionTextView5;



        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            questionTextView1 = itemView.findViewById(R.id.sliderItemText1ID);
            questionTextView2 = itemView.findViewById(R.id.sliderItemText2ID);
            questionTextView3 = itemView.findViewById(R.id.sliderItemText3ID);
            questionTextView4 = itemView.findViewById(R.id.sliderItemText4ID);
            questionTextView5 = itemView.findViewById(R.id.sliderItemText5ID);
        }
    }//inner class end





}//class end
