package com.example.sofe4640bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MySQLiteHelper mydb=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("step1","step1");
        mydb = new MySQLiteHelper(this,null,null,2);


        Log.d("step2","step2");

    }

/*
    public void onClickActivity2(View view){
        Intent activity2Intent = new Intent(this,AddNewBook.class);
        activity2Intent.putExtra("msg1","Greetings from Activity 1");
        //activity2Intent.putExtra("msg2"," Another Greetings from Activity 1");
        // activity2Intent.putExtra("msg3",100);
        startActivity(activity2Intent);

    }
*/

    public void signup(View view){

        Intent intent = new Intent(this,SignupUsers.class);
        startActivity(intent);

    }


    public void checkExistingUser(View view){
        Log.d("step2.5","step2.5");
        EditText userName = (EditText) findViewById(R.id.txtUserID);
        String userNameStr = userName.getText().toString();

        EditText password = (EditText) findViewById(R.id.txtPassword);
        String passwordStr = password.getText().toString();
        Log.d("step3","step3");
        boolean isFound = mydb.checkUsers(userNameStr,passwordStr);

        if (isFound) {
            Log.d("check1", "Record is Found");
            Intent activity2Intent = new Intent(this, AddNewBook.class);
            activity2Intent.putExtra("userName",userNameStr );
            startActivity(activity2Intent);
        }
        else
            Toast.makeText(this,"User name or password is incorrect",Toast.LENGTH_LONG).show();


    }

}