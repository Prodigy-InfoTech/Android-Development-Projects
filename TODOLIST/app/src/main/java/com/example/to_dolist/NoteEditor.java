package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;


public class NoteEditor extends AppCompatActivity {
    int itemno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_note_editor);
        Intent intent = getIntent();
        itemno=intent.getIntExtra("item no",-1);
        EditText editText= (EditText) findViewById(R.id.edittext);

        if(itemno != -1 ) {
            editText.setText(MainActivity.arrayList.get(itemno));
        }else{
            MainActivity.arrayList.add("");
            itemno= MainActivity.arrayList.size() -1 ;

        }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // this will basically put the final text after we type i.e the charsequence and put it in the arraylist
                    MainActivity.arrayList.set(itemno,charSequence.toString());
                    //now after we have changed the item we have to notify the arrayadapter about that to make the changes
                    MainActivity.adapter.notifyDataSetChanged();
// IF WE HAVENT NOTIFIED ARRAYADAPTER ABOUT THAT AFTER WE HAVE CHNAGED TEXT AND DO BACK IN ANDROID THE LIST REMAINS THE SAME AS IT WAS BEFORE BUT THEN AFTER IF WE AGAIN PRESS THAT LIST
//THE TEXT IS CHANGED TO WHAT WE WROTE SO
// CONCLUSION:: NOTIFYDATASETCHANGED() WILL H=CHANGE THE LIST IN THE APP AS PER THE TEXT WE WROTE.

                    HashSet<String> hashSet = new HashSet<String>(MainActivity.arrayList);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("todolist",MODE_PRIVATE);
                    sharedPreferences.edit().putStringSet("todolist",hashSet).apply();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
    }
}