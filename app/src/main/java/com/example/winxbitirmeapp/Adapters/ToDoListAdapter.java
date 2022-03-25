package com.example.winxbitirmeapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.winxbitirmeapp.Models.ToDoModel;
import com.example.winxbitirmeapp.R;

import java.util.ArrayList;

public class ToDoListAdapter extends ArrayAdapter<ToDoModel> {


    private class ViewHolder {
        private TextView toDo;
        private RadioButton answer1, answer2, answer3;
        private RadioGroup rg;
    }

    private ArrayList<ToDoModel> array;
    private Context context;

    public ToDoListAdapter(Context context, ArrayList<ToDoModel> arrayList) {
        super(context, R.layout.todo_list_row, arrayList);
        this.array = arrayList;
        this.context = context;

    }


    public int getCount() {
        return array.size();
    }

    public ToDoModel getItem(int position) {
        return array.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        view = LayoutInflater.from(context).inflate(R.layout.todo_list_row, null);

        if (view != null) {
            holder.toDo = view.findViewById(R.id.ToDoRowTextID);
        }

        holder.toDo.setText(array.get(position).getTask());

        return view;
    }

}