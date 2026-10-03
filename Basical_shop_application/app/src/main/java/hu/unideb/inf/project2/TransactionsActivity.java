package hu.unideb.inf.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TransactionsActivity extends AppCompatActivity {

    private TextView username;

    private TableLayout tableview;

    private TextView sumOfDay;
    private TextView dateView;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        username = findViewById(R.id.usernamelabel);
        username.setText(MainActivity.username);

        dateView = findViewById(R.id.dateLabel);

        tableview = (TableLayout) findViewById(R.id.dailyTable);
        sumOfDay = findViewById(R.id.sumOfDay);

        for(int i=0; i<SellActivity.transactionList.size(); i++){
            TableRow tableRow = new TableRow(this);
            String id = SellActivity.transactionList.get(i).getId() + "";
            String seller = SellActivity.transactionList.get(i).getName();
            String sold = String.valueOf(SellActivity.transactionList.get(i).getSum());

            TextView idText = new TextView(this);
            idText.setText(id);
            idText.setTextSize(14);
            idText.setGravity(Gravity.CENTER);
            //idText.setWidth(60);

            TextView sellerText = new TextView(this);
            sellerText.setText(seller);
            sellerText.setTextSize(14);
            sellerText.setGravity(Gravity.CENTER);
            //idText.setWidth(150);

            TextView soldText = new TextView(this);
            soldText.setText(sold + " Ft");
            soldText.setTextSize(14);
            soldText.setGravity(Gravity.CENTER);
            //idText.setWidth(120);


            tableRow.addView(idText);
            tableRow.addView(sellerText);
            tableRow.addView(soldText);

            tableview.addView(tableRow);
        }

        countSum();

        dateView.setText(SellActivity.getYesterdayDateString());




    }

    public void backtoSell(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra("ITEM2","");
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private void countSum(){
        int sum = 0;
        for(int i = 0; i<SellActivity.transactionList.size(); i++){
            sum = sum + SellActivity.transactionList.get(i).getSum();

        }
        sumOfDay.setText(sum + " Ft");
    }
}