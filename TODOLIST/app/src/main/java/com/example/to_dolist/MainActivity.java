package com.example.to_dolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    public void pressed(View view){
        Intent intent= new Intent(MainActivity.this, NoteEditor.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("todolist",MODE_PRIVATE);

        listView = (ListView) findViewById(R.id.listview);

        HashSet<String> hashSet = (HashSet<String>) sharedPreferences.getStringSet("todolist",null);
        if(hashSet== null){
            arrayList.add("Physics Assingment");
        }
        else{
            arrayList = new ArrayList(hashSet);
        }
        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // calling Noteeditor.class will create a object of that class as we havent made object by ourself so we need to call it by reference of class
                Intent intent = new Intent(MainActivity.this,NoteEditor.class);
                // here i is the item no in the list that is pressed
                intent.putExtra("item no",i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//USING ALERT DIALOGUE TO ASK THE USER TO DELETE THAT
                int itemtodelete= i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_launcher_background)
                        .setTitle("Are you Sure??")
                        .setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // WE HAVE AN I HERE ALSO IN THE ONnclik SO IT WILL OVERRIDE OUR I IN THE ONITEMLONGCLICK SO WE NEED TO HAVE VALUE OF I IN SEPERATE VARIABLE BEFORE
                                arrayList.remove(itemtodelete);
                                adapter.notifyDataSetChanged();
                                //AFTEE THE ITEM GETS DELETED THE SHARED [REFERENCE MUS BE NOTIFIES
                                //HASHSET MUST BE WRITTEN EVERYWHERE BECAUSE WE NEED TO MODIFY THE VALUES OF HASHSET AFTER WE DELETE ANY ITEM

                                HashSet<String> hashSet = new HashSet<String>(MainActivity.arrayList);
                                sharedPreferences.edit().putStringSet("todolist",hashSet).apply();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });
    }
// FOR MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()== R.id.addnote){
             Intent intent= new Intent(MainActivity.this, NoteEditor.class);
             startActivity(intent);
             return true;
         }
         return false;
    }
}