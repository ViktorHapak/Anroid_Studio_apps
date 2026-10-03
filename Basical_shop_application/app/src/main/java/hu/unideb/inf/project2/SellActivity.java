package hu.unideb.inf.project2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import hu.unideb.inf.project2.model.Transaction;

public class SellActivity extends AppCompatActivity {

    private static final String SAVESTATE_COUNTID = "hu.unideb.inf.shoppinglist2023.MainActivity.SAVESTATE.COUNTID";
    private static final String SAVESTATE_TODAY = "hu.unideb.inf.shoppinglist2023.MainActivity.SAVESTATE.TODAY";
    private static final String SAVESTATE_LIST = "hu.unideb.inf.shoppinglist2023.MainActivity.SAVESTATE.LIST";

    private TextView nameLabel;
    private TextView productListView;
    private TextView countListView;

    private TextView priceText;

    private TextView messageText;

    private Integer countId;

    private SharedPreferences sharedPref;
    private String sharedPrefFilename = "shrprf";

    public static List<Transaction> transactionList;


    private String today;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        nameLabel = findViewById(R.id.nameLabel);
        nameLabel.setText(MainActivity.username);
        productListView = findViewById(R.id.productListView);
        countListView = findViewById(R.id.countListView);
        priceText = findViewById(R.id.price);
        messageText = findViewById(R.id.messageText);
        countId = 0;

       today = getYesterdayDateString();

       transactionList = new ArrayList<>();

       sharedPref = getSharedPreferences(sharedPrefFilename, MODE_PRIVATE);

        if (savedInstanceState!=null) {
            countId = Integer.parseInt(savedInstanceState.getString(SAVESTATE_COUNTID));

            loadTransactionList(savedInstanceState.getString(SAVESTATE_LIST));

            if (!today.equals(savedInstanceState.getString(SAVESTATE_TODAY))){
                eraseTransactionList();
                countId = 0;
                openDialog();
            }


        }
        else {
            countId = Integer.parseInt(sharedPref.getString(SAVESTATE_COUNTID, countId + ""));

            loadTransactionList(sharedPref.getString(SAVESTATE_LIST, ShopActivity.subNeedlesschars(transactionList.toString())));

            if(!today.equals(sharedPref.getString(SAVESTATE_TODAY, today))){
                eraseTransactionList();
                countId = 0;
                openDialog();
            }
        }


    }


    /*Tábla rekordjának számlálásához le kell mentenünk annak számlálóját(countId), hogy a táblázatot
    innen tudjuk bővíteni */
     @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString(SAVESTATE_COUNTID, countId + "");
        prefEditor.putString(SAVESTATE_TODAY, today);
        prefEditor.putString(SAVESTATE_LIST, ShopActivity.subNeedlesschars(transactionList.toString()));


        prefEditor.apply(); // ezzel fogadtatjuk el

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVESTATE_COUNTID, countId + "");
        outState.putString(SAVESTATE_TODAY, today);
        outState.putString(SAVESTATE_LIST, ShopActivity.subNeedlesschars(transactionList.toString()));
    } // A bevásárló lista tartalmának mentése

    @SuppressLint("SuspiciousIndentation")
    ActivityResultLauncher activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode()!=RESULT_OK) return;
                //Log.i("alma",result.getData().getStringExtra("ITEM"));
                String transaction = result.getData().getStringExtra("ITEM");
                if ((transaction == null) || (transaction.equals("~~0")))
                    return;

                    //Log.i("alma", transaction);
                   /*A végeredmény-stringből ki kell vonnunk a termékek és darabszámaik listáját,
                     valamint az árat, amit a meglévőhöz adunk hozzá:*/

                    String[] transactionArray = transaction.split("~");
                    String transactionProduct = transactionArray[0];
                    String transactionCount = transactionArray[1];
                    String price = transactionArray[2];


                    List<String> productList = new ArrayList<String>(Arrays.asList(transactionProduct.split(" ")));
                    List<String> countList = new ArrayList<String>(Arrays.asList(transactionCount.split(" ")));

                    Log.i("alma", productList.toString());
                    Log.i("alma", countList.toString());


                    if (productListView.getText().toString().equals(getString(R.string.empty_list))) {
                        productListView.setText("");
                        countListView.setText("");
                    }

                    for (int i = 0; i < productList.size(); i++) {
                        productListView.append(productList.get(i) + "\n");
                        countListView.append(countList.get(i) + "\n");
                        Log.i("alma", countList.get(i).toString());
                    }

                    //Meghatározzuk a fizetendő árat az új elemek hozzáadása után:
                    Integer oldprice = Integer.valueOf(priceText.getText().toString().split(" ", 2)[0]);
                    Integer newprice = oldprice + Integer.valueOf(price);
                    priceText.setText(newprice + " Ft");

            }
    );

    ActivityResultLauncher activityResultLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode()!=RESULT_OK) return;
            });


    public void logout(View view) {
        MainActivity.username = ""; MainActivity.name = "";
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



    public void sell(View view) {

        if(productListView.getText().toString().equals(getString(R.string.empty_list))){
            messageText.setText("Üres bevásárlólista!");
        } else {

            countId++;
            String id = countId + "";
            String seller = MainActivity.name;
            String sold = priceText.getText().toString();

            Transaction transaction = new Transaction(Integer.parseInt(id), seller,
                    Integer.parseInt(sold.substring(0,sold.length()-3)));
            transactionList.add(transaction);

            productListView.setText(getString(R.string.empty_list));
            countListView.setText("");
            priceText.setText("0 Ft");

            Log.i("alma", transactionList.toString());

            Intent intent = new Intent(this, TransactionsActivity.class);
            activityResultLauncher2.launch(intent);
        }

    }

    public void transactionList(View view) {
        if(transactionList != null) {
            Intent intent = new Intent(this, TransactionsActivity.class);
            activityResultLauncher2.launch(intent);
            messageText.setText("");
        }

    }

    public void addItem(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        activityResultLauncher.launch(intent);
        messageText.setText("");

    }

    public void delete(View view) {
        productListView.setText(getString(R.string.empty_list));
        countListView.setText("");
        priceText.setText("0 Ft");

    }

    public static String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        return dateFormat.format(cal.getTime());
    }

    private void eraseTransactionList(){
        transactionList.clear();


    }

    private void loadTransactionList(String string){
        Log.i("apple",string);
        int i=0; int j=1;

        List<String> stringList = new ArrayList<String>(Arrays.asList(string.split(" ")));
        Log.i("alma", "A betöltendő lista: " + stringList);
        if(stringList != null){
        while (j<stringList.size()){
            Transaction transaction = new Transaction();
            transaction.setId(Integer.parseInt(stringList.get(j-1)));
            transaction.setName(stringList.get(j));
            transaction.setSum(Integer.parseInt(stringList.get(j+1)));
            transactionList.add(transaction);
            j=j+3;
        } }

    }

    public void openDialog() {
        DialogToAddItem dialogToAddItem = new DialogToAddItem("Előző napi tranzakciók listája ki lett törölve");
        dialogToAddItem.show(getSupportFragmentManager(), "example dialog");
    }
}