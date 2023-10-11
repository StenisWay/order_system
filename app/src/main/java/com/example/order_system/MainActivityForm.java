package com.example.order_system;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivityForm extends AppCompatActivity {

    private Button calbutton;
    private EditText rawExpense, salaryExpense, otherExpense;
    private TextView totalTable, totalRevenue, Income;

    Format nf1 = new DecimalFormat("0");
    private SQLiteDatabase db;
    int sumRevenue = 0;
    int count;

    Calendar mCal = Calendar.getInstance();
    String dateformat = "yyyyMMdd";
    SimpleDateFormat df = new SimpleDateFormat(dateformat);
    String today = df.format(mCal.getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        totalRevenue = findViewById(R.id.totalRevenue);
        totalTable = findViewById(R.id.totaltable);
        rawExpense = findViewById(R.id.rawExpense);
        salaryExpense = findViewById(R.id.salaryExpense);
        otherExpense = findViewById(R.id.otherExpense);
        calbutton = findViewById(R.id.calbutton);
        Income = findViewById(R.id.Income);

        db = openOrCreateDatabase(  "'" + today + "'" + ".db", MODE_PRIVATE, null);



            Cursor cursor = db.query(false, "RevenueDetail", new String[]{
                            "revenue"}, null, null, null,
                    null, null, null);

            //Cursor cursor = db.rawQuery("SELECT _id, revenue FROM RevenueDetail", null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int re = cursor.getInt(0);
                    sumRevenue += re;
                    count += 1;
                }

            }

            totalTable.setText(nf1.format(count));
            totalRevenue.setText(nf1.format(sumRevenue));


        db.close();
        cursor.close();

        calbutton.setOnClickListener(calbuttonOnClick);

    }


    private View.OnClickListener calbuttonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(sumRevenue > 0) {
                int income;
                int x = Integer.parseInt(rawExpense.getText().toString());
                int y = Integer.parseInt(salaryExpense.getText().toString());
                int z = Integer.parseInt(otherExpense.getText().toString());
                income = sumRevenue - x - y - z;
                Income.setText(nf1.format(income));
            }else{
                Toast.makeText(MainActivityForm.this, "尚未有任何收入", Toast.LENGTH_SHORT).show();
            }
        }
    };



}