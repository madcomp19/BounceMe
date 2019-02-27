package com.madcomp19gmail.bouncyball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SkinFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();
        int pageNumber = bundle.getInt("pageNumber");

        //set correct view from pageNumber
        switch(pageNumber){
            case 1:
                view = inflater.inflate(R.layout.fragment_skin_shop_1, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_skin_shop_2, container, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_skin_shop_3, container, false);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_skin_shop_1, container, false);
                break;
        }

        return view;
    }
}
