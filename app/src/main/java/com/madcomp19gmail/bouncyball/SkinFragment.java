package com.madcomp19gmail.bouncyball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SkinFragment extends Fragment {

    private Bundle bundle;
    private TextView coins;
    private StorageManager storageManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view;
        bundle = getArguments();
        int pageNumber = bundle.getInt("pageNumber");
        coins = (TextView) getActivity().findViewById(R.id.coins);
        storageManager = StorageManager.getInstance();

        //set correct view from pageNumber
        switch (pageNumber) {
            case 1:
                view = inflater.inflate(R.layout.fragment_skin_shop_1, container, false);
                setOwnedSkinsPage(view);
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_skin_shop_2, container, false);
                setOwnedSkinsPage(view);
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_skin_shop_3, container, false);
                setOwnedSkinsPage(view);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_skin_shop_1, container, false);
                setOwnedSkinsPage(view);
                break;
        }

        return view;
    }

    private void setOwnedSkinsPage(View view)
    {

        ArrayList<Integer> label_ids = storageManager.getOwnedSkinsLabels();

        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();

        for(int i = 0; i < childCount; i++)
        {
            View v = viewGroup.getChildAt(i);

            if(v instanceof TextView)
            {
                if(label_ids.contains(v.getId()))
                    ((TextView) v).setText("Owned");

                if (storageManager.getSelectedLabel() == v.getId())
                    ((TextView) v).setText("Selected");
            }
            else if(v instanceof ViewGroup)
                setOwnedSkinsPage(v);
        }
    }
}
