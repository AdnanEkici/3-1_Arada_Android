package com.example.winxbitirmeapp.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.winxbitirmeapp.R;

import java.util.ArrayList;

//ADAPTER
public class QuestionnaireAdapter extends ArrayAdapter<String> {


    private class ViewHolder {
        private TextView question;
        private RadioButton answer1, answer2, answer3;
        private RadioGroup rg;
    }

    private ArrayList<String> array;
    private Context context;
    private ArrayList<Integer> selectedAnswers;

    public QuestionnaireAdapter(Context context, ArrayList<String> arrayList , ArrayList<Integer> selectedAnswers) {
        super(context, R.layout.question_item, arrayList);
        this.array = arrayList;
        this.context = context;
        this.selectedAnswers = selectedAnswers;

    }


    public int getCount() {
        return array.size();
    }

    public String getItem(int position) {
        return array.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        view = LayoutInflater.from(context).inflate(R.layout.question_item, null);

        if (view != null) {
            holder.question = view.findViewById(R.id.QuestionnaireItemTextID);
            holder.answer1 = view.findViewById(R.id.QuestionnaireItemAnswer1ID);
            holder.answer2 = view.findViewById(R.id.QuestionnaireItemAnswer2ID);
            holder.answer3 = view.findViewById(R.id.QuestionnaireItemAnswer3ID);
            holder.rg = view.findViewById(R.id.QuestionnaireItemRadioGroupID);


            holder.question.setText(array.get(position));

            holder.answer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        selectedAnswers.set(position, 0);
                        notifyDataSetChanged();
                    }

                }
            });
            holder.answer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        selectedAnswers.set(position, 1);
                        notifyDataSetChanged();
                    }

                }
            });

            holder.answer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        selectedAnswers.set(position, 2);
                        notifyDataSetChanged();
                    }

                }
            });

            method(position , holder.rg);

        }

        return view;
    }

    private void method(int i , RadioGroup rg) {
        for (int j=0;j<=selectedAnswers.size();j++)
        {
            if (i == j) {
                int a =  selectedAnswers.get( i );
                if (a != -1)
                    ((RadioButton) rg.getChildAt( a )).setChecked( true );
            }
        }
    }
}
