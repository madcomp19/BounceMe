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
                setOwnedSkinsPage1(view);
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_skin_shop_2, container, false);
                setOwnedSkinsPage2(view);
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_skin_shop_3, container, false);
                setOwnedSkinsPage3(view);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_skin_shop_1, container, false);
                setOwnedSkinsPage1(view);
                break;
        }

        return view;
    }

    private void setOwnedSkinsPage1(View view) {
        ArrayList<Integer> label_ids = storageManager.getOwnedSkinsLabels();

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutLabels1);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof TextView && label_ids.contains(linearLayout.getChildAt(i).getId())) {
                ((TextView) linearLayout.getChildAt(i)).setText("Owned");
            }
            if (storageManager.getSelectedLabel() == linearLayout.getChildAt(i).getId())
                ((TextView) linearLayout.getChildAt(i)).setText("Selected");
        }

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutLabels2);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof TextView && label_ids.contains(linearLayout.getChildAt(i).getId())) {
                ((TextView) linearLayout.getChildAt(i)).setText("Owned");
            }
            if (storageManager.getSelectedLabel() == linearLayout.getChildAt(i).getId())
                ((TextView) linearLayout.getChildAt(i)).setText("Selected");
        }
    }

    private void setOwnedSkinsPage2(View view) {
        ArrayList<Integer> label_ids = storageManager.getOwnedSkinsLabels();

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutLabels3);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof TextView && label_ids.contains(linearLayout.getChildAt(i).getId())) {
                ((TextView) linearLayout.getChildAt(i)).setText("Owned");
            }
            if (storageManager.getSelectedLabel() == linearLayout.getChildAt(i).getId())
                ((TextView) linearLayout.getChildAt(i)).setText("Selected");
        }

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutLabels4);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof TextView && label_ids.contains(linearLayout.getChildAt(i).getId())) {
                ((TextView) linearLayout.getChildAt(i)).setText("Owned");
            }
            if (storageManager.getSelectedLabel() == linearLayout.getChildAt(i).getId())
                ((TextView) linearLayout.getChildAt(i)).setText("Selected");
        }
    }

    private void setOwnedSkinsPage3(View view) {
        ArrayList<Integer> label_ids = storageManager.getOwnedSkinsLabels();

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutLabels5);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof TextView && label_ids.contains(linearLayout.getChildAt(i).getId())) {
                ((TextView) linearLayout.getChildAt(i)).setText("Owned");
            }
            if (storageManager.getSelectedLabel() == linearLayout.getChildAt(i).getId())
                ((TextView) linearLayout.getChildAt(i)).setText("Selected");
        }

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutLabels6);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof TextView && label_ids.contains(linearLayout.getChildAt(i).getId())) {
                ((TextView) linearLayout.getChildAt(i)).setText("Owned");
            }
            if (storageManager.getSelectedLabel() == linearLayout.getChildAt(i).getId())
                ((TextView) linearLayout.getChildAt(i)).setText("Selected");
        }
    }
}
