package hu.unideb.inf.project2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.unideb.inf.project2.DialogToAddItem;
import hu.unideb.inf.project2.R;
import hu.unideb.inf.project2.ShopActivity;

public class WheatFragment extends Fragment {

    Button[] button;


    public WheatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wheat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for(int i = 1; i<10; i++) {
            String idName = "wheat" + i;
            Button button1 = view.findViewById(getResources().getIdentifier(idName, "id", getActivity().getPackageName()));
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btn = (Button)view;
                    String[] product = btn.getText().toString().split(" ");
                    String productName = product[0];

                    Integer price = Integer.valueOf(product[1]);
                    ((ShopActivity)getActivity()).addtoList(productName, price);
                }
            });
        }
    }

    public void addProduct(View view) {


    }



    }
