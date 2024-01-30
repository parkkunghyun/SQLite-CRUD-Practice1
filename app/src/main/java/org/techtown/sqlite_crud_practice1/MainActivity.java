package org.techtown.sqlite_crud_practice1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editTextName, editTextPhone, editTextAddress, editTextID;
    Button buttonInsert, buttonView, buttonUpdate, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        editTextName = findViewById(R.id.nameEdit);
        editTextID = findViewById(R.id.editId);
        editTextAddress = findViewById(R.id.addressEdit);
        editTextPhone = findViewById(R.id.callEdit);

        buttonInsert = findViewById(R.id.addBtn);
        buttonUpdate = findViewById(R.id.updateBtn);
        buttonDelete = findViewById(R.id.deleteBtn);
        buttonView = findViewById(R.id.showBtn);


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(editTextName.getText().toString(),
                        editTextPhone.getText().toString(), editTextAddress.getText().toString());

                if(isInserted == true) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if(res.getCount() == 0) {
                    ShowMessage("fail", "no data");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("ID" + res.getString(0) + "\n");
                    buffer.append("이름" + res.getString(1) + "\n");
                    buffer.append("전화번호" + res.getString(2) + "\n");
                    buffer.append("주소" + res.getString(3) + "\n\n");
                }
                ShowMessage("data", buffer.toString());
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDB.updateData(editTextID.getText().toString(),
                        editTextName.getText().toString(),
                        editTextPhone.getText().toString(),
                        editTextAddress.getText().toString());

                if(isUpdated == true) {
                    Toast.makeText(getApplicationContext(), "data success", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "data success", Toast.LENGTH_SHORT).show();
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDB.deleteData(editTextID.getText().toString());
                if (deleteRows > 0) {
                    Toast.makeText(getApplicationContext(), "data 삭제 성공", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void  ShowMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}