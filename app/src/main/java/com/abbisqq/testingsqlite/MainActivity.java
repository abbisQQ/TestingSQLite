package com.abbisqq.testingsqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.button;
import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText name , surname , marks, idText;
    Button buttonADD,viewALL,buttonUpdate,deleteButton;
    Boolean isUpdated;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        marks = (EditText)findViewById(R.id.marks);
        idText = (EditText)findViewById(R.id.idEditText);

        buttonADD = (Button)findViewById(R.id.add_button);
        viewALL = (Button)findViewById(R.id.viewDB);
        buttonUpdate = (Button)findViewById(R.id.updateDB);
        deleteButton = (Button)findViewById(R.id.deleteDB);

        //functions we made
        addData();
        viewAllData();
        updateFunction();
        deleteData();
    }

    public void addData(){
        buttonADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we  use the insertdata method we create in dbhelper class
                boolean insertedData = myDB.insertData(name.getText().toString(),
                        surname.getText().toString(),
                        marks.getText().toString());
                if(insertedData){
                    Toast.makeText(MainActivity.this,"Data inserted successful",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"Error no data inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAllData(){
        viewALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting all the data using the getAllData method we have create
                Cursor res = myDB.getAllData();
                // if there is no data
                if(res.getCount()==0){

                    showMessage("Error","No Data FOUND");


                }else {
                    //if there is data we use the cursor res and the method has next to iterate over it
                    StringBuffer buffer =  new StringBuffer();

                    while (res.moveToNext()){
                        buffer.append("ID " +res.getString(0)+"\n");
                        buffer.append("Name "+res.getString(1)+"\n");
                        buffer.append("Surname "+res.getString(2)+"\n");
                        buffer.append("Marks "+res.getString(3)+"\n\n\n");
                    }


                    showMessage("Data",buffer.toString());
                }

            }
        });
    }


    //Just an error builder to show if no data is found nothing to do with the actual database
    public  void showMessage(String title,String message){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    public void updateFunction(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            isUpdated = myDB.updateData(idText.getText().toString(),name.getText().toString(),
                    surname.getText().toString(),marks.getText().toString());

            if(isUpdated){
                Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MainActivity.this,"Error data is not Updated",Toast.LENGTH_LONG).show();
            }



            }
        });
    }

    public void deleteData(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDB.deleteData(idText.getText().toString());
                //id delete returns 0 no data has been deleted
                if(deletedRows>0){
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"Error data is not deleted",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
