package com.example.winxbitirmeapp.toDoAndAchivements;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.winxbitirmeapp.Adapters.QuestionnaireAdapter;
import com.example.winxbitirmeapp.Adapters.ToDoListAdapter;
import com.example.winxbitirmeapp.CustomListView.SwipeListViewTouchListener;
import com.example.winxbitirmeapp.HomeActivity;
import com.example.winxbitirmeapp.MeditationActivity;
import com.example.winxbitirmeapp.Models.ToDoModel;
import com.example.winxbitirmeapp.ProfileActivity;
import com.example.winxbitirmeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//profil/todo
public class ToDoActivity extends AppCompatActivity{

    private ListView todoListView;
    private ListView doneListView;
    private ArrayList<ToDoModel> tasks;
    private ArrayList<ToDoModel> dones;
    private TextView todoemptyText , doneemptyText;
    private ToDoListAdapter adapterTask;
    private ToDoListAdapter adapterDone;
    private String tokenType;
    private String token;
    public static Context context;

    private String email;
    private String password;

    final String URL = "http://10.2.37.139:8080/profile/todo";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        context = getApplicationContext();
        this.init();

        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(todoListView, new SwipeListViewTouchListener.OnSwipeCallback() {
                            @Override
                            public void onSwipeLeft(ListView listView, int [] reverseSortedPositions)
                            {
                                try {
                                    ToDoModel temp = tasks.remove(reverseSortedPositions[0]);
                                    updateTask(new ToDoModel(temp.getTask() , true));
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
                                    System.out.println("View Memory Leaked Hatas?? Dokunmay??n");
                                }

                            }
                            @Override
                            public void onSwipeRight(ListView listView, int [] reverseSortedPositions)
                            {
                                try {
                                    ToDoModel temp = tasks.remove(reverseSortedPositions[0]);
                                    updateTask(new ToDoModel(temp.getTask() , true));
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
                                    System.out.println("View Memory Leaked Hatas?? Dokunmay??n");
                                }

                            }
                        }, true, true);
        todoListView.setOnTouchListener(touchListener);
        todoListView.setOnScrollListener(touchListener.makeScrollListener());

        System.out.println("??nemli Token::::" + token + "  " + tokenType);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ToDoActivity.this , ProfileActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("tokenType", tokenType);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    private void init()
    {
        todoListView = findViewById(R.id.ToDoListViewID);
        doneListView = findViewById(R.id.doneListViewID);
        todoemptyText = findViewById(R.id.todoemptyTextID);
        doneemptyText = findViewById(R.id.doneEmptyTextID);

        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        tasks = new ArrayList<>(10);
        dones = new ArrayList<>(10);
        //this.getUserData();
        this.getUserData();










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
                        saveUserData(new ToDoModel(newTask , false));
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
        RequestQueue queue = Volley.newRequestQueue(this);



        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<>();
        params.put("none", "none");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = new JSONObject(String.valueOf(response));
                            JSONArray jsonArray = jsonObject.getJSONArray("tasks");
                             for (int i = 0; i < jsonArray.length(); i++)
                            {
                                 JSONObject jo = jsonArray.getJSONObject(i);

                                 if(jo.getString("isDone").equalsIgnoreCase("false"))
                                 {
                                    tasks.add(new ToDoModel(jo.getString("task") , false));
                                 }
                                 else
                                 {
                                     dones.add(new ToDoModel(jo.getString("task") , true));
                                 }

                            }
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
                             adapterTask = new ToDoListAdapter(context , tasks);
                             todoListView.setAdapter(adapterTask);

                            adapterDone = new ToDoListAdapter(context , dones);
                            doneListView.setAdapter(adapterDone);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                System.out.println("ERRRRROR: " + error.getLocalizedMessage());
            }
        }){

            //Headera g??nder
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", tokenType + " " + token);
                return headers;
            }
        };

        queue.add(req);
    }

    protected void saveUserData(ToDoModel taskModel)//update
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<>();
        params.put("task", taskModel.getTask());
        params.put("isDone", taskModel.isDone() + "");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(String.valueOf(response));
                            //Alttaki yorumlu kod json arrayi okur
                            // JSONArray jsonArray = jsonObject.getJSONArray("data");
                            // for (int i = 0; i < jsonArray.length(); i++)
                            //{
                            //     JSONObject jo = jsonArray.getJSONObject(i);
                            //     System.out.println("Bruh: " + jo.getString("tokenType"));
                            // }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){

            //Headera g??nder
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", tokenType + " " + token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        queue.add(req);

    }

    protected void updateTask(ToDoModel taskModel)//update
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        final String URL = "http://10.2.37.139:8080/profile/todo";
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<>();
        params.put("task", taskModel.getTask());
        params.put("isDone", taskModel.isDone() + "");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(String.valueOf(response));
                            //Alttaki yorumlu kod json arrayi okur
                            // JSONArray jsonArray = jsonObject.getJSONArray("data");
                            // for (int i = 0; i < jsonArray.length(); i++)
                            //{
                            //     JSONObject jo = jsonArray.getJSONObject(i);
                            //     System.out.println("Bruh: " + jo.getString("tokenType"));
                            // }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){

            //Headera g??nder
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", tokenType + " " + token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        queue.add(req);

    }



}

