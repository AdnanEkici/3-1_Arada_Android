package com.example.winxbitirmeapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.winxbitirmeapp.Models.AchievementModel;
import com.example.winxbitirmeapp.Models.ToDoModel;
import com.example.winxbitirmeapp.R;

import java.util.ArrayList;

public class AchievementAdapter extends ArrayAdapter<AchievementModel> {


    private class ViewHolder {
        private TextView toDo;
    }

    private ArrayList<AchievementModel> array;
    private Context context;

    public AchievementAdapter(Context context, ArrayList<AchievementModel> arrayList) {
        super(context, R.layout.achievements_row, arrayList);
        this.array = arrayList;
        this.context = context;

    }


    public int getCount() {
        return array.size();
    }

    public AchievementModel getItem(int position) {
        return array.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        AchievementAdapter.ViewHolder holder = new AchievementAdapter.ViewHolder();
        view = LayoutInflater.from(context).inflate(R.layout.achievements_row, null);

        if (view != null) {
            holder.toDo = view.findViewById(R.id.AchievementsTextRowID);
        }

        holder.toDo.setText(array.get(position).getDescription());

        return view;
    }

}
