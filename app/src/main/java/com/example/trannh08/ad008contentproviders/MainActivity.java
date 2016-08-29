package com.example.trannh08.ad008contentproviders;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trannh08.ad008contentproviders.utils.StudentsProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddName(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentsProvider.NAME,
                ((EditText) findViewById(R.id.editText2)).getText().toString());

        contentValues.put(StudentsProvider.GRADE,
                ((EditText) findViewById(R.id.editText3)).getText().toString());

        Uri uri = getContentResolver().insert(
                StudentsProvider.CONTENT_URI, contentValues);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveStudents(View view) {
        // Retrieve student records
        String studentName = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String studentGrade = ((EditText) findViewById(R.id.editText3)).getText().toString();

        String URL = "content://com.example.trannh08.ad008contentproviders.School/students";
        Uri students = Uri.parse(URL);

        String [] requestedColumns = {
                StudentsProvider.ID,
                StudentsProvider.NAME,
                StudentsProvider.GRADE
        };
        String condition = null;
        if(studentName != null && studentGrade != null) {
            condition = StudentsProvider.NAME + "=? and " + StudentsProvider.GRADE + "=?";
        } else if(studentName != null && studentGrade == null) {
            condition = StudentsProvider.NAME + "=?";
        }else if(studentName == null && studentGrade != null) {
            condition = StudentsProvider.GRADE + "=?";
        }
        String[] condition_args = {
                ((EditText) findViewById(R.id.editText2)).getText().toString(),
                ((EditText) findViewById(R.id.editText3)).getText().toString()
        };
        String sortOrder = StudentsProvider.ID + " desc";
        Cursor cursor = getContentResolver().query(students, requestedColumns, condition, condition_args, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Toast.makeText(this,
                        cursor.getString(cursor.getColumnIndex(StudentsProvider.ID)) +
                                ", " + cursor.getString(cursor.getColumnIndex(StudentsProvider.NAME)) +
                                ", " + cursor.getString(cursor.getColumnIndex(StudentsProvider.GRADE)),
                        Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
    }
}
