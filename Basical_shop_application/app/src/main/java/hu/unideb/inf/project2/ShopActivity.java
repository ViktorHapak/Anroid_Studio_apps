package hu.unideb.inf.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.inf.project2.fragments.HomeFragment;
import hu.unideb.inf.project2.fragments.MeatFragment;
import hu.unideb.inf.project2.fragments.MilkFragment;
import hu.unideb.inf.project2.fragments.SweetsFragment;
import hu.unideb.inf.project2.fragments.VegetablesFragment;
import hu.unideb.inf.project2.fragments.WheatFragment;

public class ShopActivity extends AppCompatActivity {

    private List<String> transactionList = new ArrayList<String>();
    private List<Integer> countList = new ArrayList<Integer>();
    private List<Integer> priceList = new ArrayList<Integer>();;
    LinearLayout linearLayout;
    private EditText countNumberText;

    private TextView username;



    VegetablesFragment vegetablesFragment;
    MilkFragment  milkFragment;
    WheatFragment wheatFragment;
    MeatFragment meatFragment;
    SweetsFragment sweetsFragment;
    HomeFragment homeFragment;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        transactionList = new ArrayList<String>();
        priceList = new ArrayList<Integer>();
        countList = new ArrayList<Integer>();

        linearLayout = findViewById(R.id.linearLayout);
        vegetablesFragment = new VegetablesFragment();
        milkFragment = new MilkFragment();
        wheatFragment = new WheatFragment();
        meatFragment = new MeatFragment();
        sweetsFragment = new SweetsFragment();
        homeFragment = new HomeFragment();
        countNumberText = findViewById(R.id.countNumber);
        username = findViewById(R.id.usernameLabel);
        username.setText(MainActivity.username);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }  //a meglévő fragment-ek törlése

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerView, homeFragment).addToBackStack(null).commit();

    }


    public void selectFragment(View view) {

        findViewById(R.id.VegetablesItem).setBackgroundColor(Color.rgb(255, 119, 0));
        findViewById(R.id.MilkItem).setBackgroundColor(Color.rgb(255, 119, 0));
        findViewById(R.id.WheatItem).setBackgroundColor(Color.rgb(255, 119, 0));
        findViewById(R.id.MeatItem).setBackgroundColor(Color.rgb(255, 119, 0));
        findViewById(R.id.SweetsItem).setBackgroundColor(Color.rgb(255, 119, 0));



        TextView textView = (TextView) view;
        textView.setBackgroundColor(Color.rgb(235, 186, 52));

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }  //a meglévő fragment-ek törlése

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if(textView.getId() == R.id.VegetablesItem){
            fragmentTransaction.add(R.id.fragmentContainerView, vegetablesFragment).addToBackStack(null).commit();
        } else if (textView.getId() == R.id.MilkItem) {
            fragmentTransaction.add(R.id.fragmentContainerView, milkFragment).addToBackStack(null).commit();

        } else if (textView.getId() == R.id.WheatItem) {
            fragmentTransaction.add(R.id.fragmentContainerView, wheatFragment).addToBackStack(null).commit();

        } else if (textView.getId() == R.id.MeatItem) {
            fragmentTransaction.add(R.id.fragmentContainerView, meatFragment).addToBackStack(null).commit();

        } else {
            fragmentTransaction.add(R.id.fragmentContainerView, sweetsFragment).addToBackStack(null).commit();

        }


    }

    public void addtoList(String productName, Integer price) {

        if(isItNewProduct(productName) && (!countNumberText.getText().toString().equals(""))) {
            transactionList.add(productName);
            priceList.add(price*Integer.valueOf(countNumberText.getText().toString()));
            countList.add(Integer.valueOf(countNumberText.getText().toString()));

        }

        if (!countNumberText.getText().toString().equals("")) openDialog();


    }




    public void gotoSell(View view) {
        Intent replyIntent = new Intent();
        /* Visszatérési String-ként a termék- és darabszám-listákból, valamint a fizetendő értékvől álló
         karatkerláncot adjuk vissza*/
        String transaction = String.join(" ", transactionList) + "~" +
                String.join(" ", subNeedlesschars(countList.toString())) + "~" + countPrice();

        Log.i("a",transaction);
        replyIntent.putExtra("ITEM",transaction);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void logout(View view) {
        MainActivity.username = ""; MainActivity.name = "";
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean isItNewProduct(String productName){

        for(int i=0; i<transactionList.size(); i++){
            if(productName.equals(transactionList.get(i))){
                int newprice = priceList.get(i)+(int)(priceList.get(i)/countList.get(i))*Integer.valueOf(countNumberText.getText().toString());
                int newcount = countList.get(i)+Integer.valueOf(countNumberText.getText().toString());
                priceList.set(i,newprice);
                countList.set(i,newcount);
                return false;
            }

        }

        return true;
    }

    private int countPrice() {
        int price = 0;
        for(int i=0; i<transactionList.size(); i++){
            price = price + priceList.get(i);
        }
        return price;
    }

    public void openDialog() {
        DialogToAddItem dialogToAddItem = new DialogToAddItem("Új termék hozzáadva a listához");
        dialogToAddItem.show(getSupportFragmentManager(), "example dialog");
    }

    public static String subNeedlesschars(String s){
        s = s.replace(",","");
        s = s.substring(1,s.length());
        s = s.substring(0,s.length()-1);
        return s;
    }



}

