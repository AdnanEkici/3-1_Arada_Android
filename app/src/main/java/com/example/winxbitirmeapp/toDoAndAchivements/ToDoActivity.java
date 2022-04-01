package com.example.winxbitirmeapp.toDoAndAchivements;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.winxbitirmeapp.Adapters.ToDoListAdapter;
import com.example.winxbitirmeapp.CustomListView.SwipeListViewTouchListener;
import com.example.winxbitirmeapp.Models.ToDoModel;
import com.example.winxbitirmeapp.R;

import java.util.ArrayList;
//profil/todo
public class ToDoActivity extends AppCompatActivity {

    private ListView todoListView;
    private ListView doneListView;
    private ArrayList<ToDoModel> tasks;
    private ArrayList<ToDoModel> dones;
    private TextView todoemptyText , doneemptyText;
    private ToDoListAdapter adapterTask;
    private ToDoListAdapter adapterDone;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        this.init();

        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(todoListView, new SwipeListViewTouchListener.OnSwipeCallback() {
                            @Override
                            public void onSwipeLeft(ListView listView, int [] reverseSortedPositions)
                            {
                                try {
                                    ToDoModel temp = tasks.remove(reverseSortedPositions[0]);
                                    dones.add(new ToDoModel(temp.getTask() , true));
                                    adapterTask.notifyDataSetChanged();
                                    adapterDone.notifyDataSetChanged();
                                    if(dones.isEmpty())
                                    {
                                        doneListView.setVisibility(View.INVISIBLE);
                                        doneemptyText.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        doneListView.setVisibility(View.VISIBLE);
                                        doneemptyText.setVisibility(View.INVISIBLE);
                                    }
                                    if (tasks.isEmpty())
                                    {
                                        todoemptyText.setVisibility(View.VISIBLE);
                                        todoListView.setVisibility(View.INVISIBLE);
                                    }
                                    else
                                    {
                                        todoemptyText.setVisibility(View.INVISIBLE);
                                        todoListView.setVisibility(View.VISIBLE);
                                    }
                                }catch (Exception e)
                                {
                                    System.out.println("View Memory Leaked Hatası Dokunmayın");
                                }

                            }
                            @Override
                            public void onSwipeRight(ListView listView, int [] reverseSortedPositions)
                            {
                                try {
                                    ToDoModel temp = tasks.remove(reverseSortedPositions[0]);
                                    dones.add(new ToDoModel(temp.getTask() , true));
                                    adapterTask.notifyDataSetChanged();
                                    adapterDone.notifyDataSetChanged();
                                    if(dones.isEmpty())
                                    {
                                        doneListView.setVisibility(View.INVISIBLE);
                                        doneemptyText.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        doneListView.setVisibility(View.VISIBLE);
                                        doneemptyText.setVisibility(View.INVISIBLE);
                                    }
                                    if (tasks.isEmpty())
                                    {
                                        todoemptyText.setVisibility(View.VISIBLE);
                                        todoListView.setVisibility(View.INVISIBLE);
                                    }
                                    else
                                    {
                                        todoemptyText.setVisibility(View.INVISIBLE);
                                        todoListView.setVisibility(View.VISIBLE);
                                    }
                                }catch (Exception e)
                                {
                                    System.out.println("View Memory Leaked Hatası Dokunmayın");
                                }

                            }
                        }, true, true);
        todoListView.setOnTouchListener(touchListener);
        todoListView.setOnScrollListener(touchListener.makeScrollListener());


    }


    private void init()
    {
        todoListView = findViewById(R.id.ToDoListViewID);
        doneListView = findViewById(R.id.doneListViewID);
        todoemptyText = findViewById(R.id.todoemptyTextID);
        doneemptyText = findViewById(R.id.doneEmptyTextID);

        tasks = new ArrayList<>(10);
        dones = new ArrayList<>(10);


        adapterTask = new ToDoListAdapter(this , this.tasks);
        adapterDone  = new ToDoListAdapter(this , this.dones);
        todoListView.setAdapter(adapterTask);
        doneListView.setAdapter(adapterDone);






        if(dones.isEmpty())
        {
            doneListView.setVisibility(View.INVISIBLE);
            doneemptyText.setVisibility(View.VISIBLE);
        }
        else
        {
            doneListView.setVisibility(View.VISIBLE);
            doneemptyText.setVisibility(View.INVISIBLE);
        }
        if (tasks.isEmpty())
        {
            todoemptyText.setVisibility(View.VISIBLE);
            todoListView.setVisibility(View.INVISIBLE);
        }
        else
        {
            todoemptyText.setVisibility(View.INVISIBLE);
            todoListView.setVisibility(View.VISIBLE);
        }

    }

    public void addTask(View view)
    {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Yeni Hedef Ekleyiniz")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTask = String.valueOf(taskEditText.getText());
                        tasks.add(new ToDoModel(newTask , false));
                        if (tasks.isEmpty())
                        {
                            todoemptyText.setVisibility(View.VISIBLE);
                            todoListView.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            todoemptyText.setVisibility(View.INVISIBLE);
                            todoListView.setVisibility(View.VISIBLE);
                        }
                        adapterTask.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }


    private void getUserData()
    {

    }

    private void saveUserData()
    {

    }




}