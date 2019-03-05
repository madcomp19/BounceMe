package com.madcomp19gmail.bouncyball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            case 4:
                view = inflater.inflate(R.layout.fragment_skin_shop_4, container, false);
                setOwnedSkinsPage(view);
                break;
            case 5:
                view = inflater.inflate(R.layout.fragment_skin_shop_5, container, false);
                setOwnedSkinsPage(view);
                break;
            case 6:
                view = inflater.inflate(R.layout.fragment_skin_shop_6, container, false);
                setOwnedSkinsPage(view);
                break;
            case 7:
                view = inflater.inflate(R.layout.fragment_skin_shop_7, container, false);
                setOwnedSkinsPage(view);
                break;
            case 8:
                view = inflater.inflate(R.layout.fragment_skin_shop_8, container, false);
                setOwnedSkinsPage(view);
                break;
            case 9:
                view = inflater.inflate(R.layout.fragment_skin_shop_9, container, false);
                setOwnedSkinsPage(view);
                break;
            case 10:
                view = inflater.inflate(R.layout.fragment_skin_shop_10, container, false);
                setOwnedSkinsPage(view);
                break;
            case 11:
                view = inflater.inflate(R.layout.fragment_skin_shop_11, container, false);
                setOwnedSkinsPage(view);
                break;
            case 12:
                view = inflater.inflate(R.layout.fragment_skin_shop_12, container, false);
                setOwnedSkinsPage(view);
                break;
            case 13:
                view = inflater.inflate(R.layout.fragment_skin_shop_13, container, false);
                setOwnedSkinsPage(view);
                break;
            case 14:
                view = inflater.inflate(R.layout.fragment_skin_shop_14, container, false);
                setOwnedSkinsPage(view);
                break;
            case 15:
                view = inflater.inflate(R.layout.fragment_skin_shop_15, container, false);
                setOwnedSkinsPage(view);
                break;
            case 16:
                view = inflater.inflate(R.layout.fragment_skin_shop_16, container, false);
                setOwnedSkinsPage(view);
                break;
            case 17:
                view = inflater.inflate(R.layout.fragment_skin_shop_17, container, false);
                setOwnedSkinsPage(view);
                break;
            case 18:
                view = inflater.inflate(R.layout.fragment_skin_shop_18, container, false);
                setOwnedSkinsPage(view);
                break;
            case 19:
                view = inflater.inflate(R.layout.fragment_skin_shop_19, container, false);
                setOwnedSkinsPage(view);
                break;
            case 20:
                view = inflater.inflate(R.layout.fragment_skin_shop_20, container, false);
                setOwnedSkinsPage(view);
                break;
            case 21:
                view = inflater.inflate(R.layout.fragment_skin_shop_21, container, false);
                setOwnedSkinsPage(view);
                break;
            case 22:
                view = inflater.inflate(R.layout.fragment_skin_shop_22, container, false);
                setOwnedSkinsPage(view);
                break;
            case 23:
                view = inflater.inflate(R.layout.fragment_skin_shop_23, container, false);
                setOwnedSkinsPage(view);
                break;
            case 24:
                view = inflater.inflate(R.layout.fragment_skin_shop_24, container, false);
                setOwnedSkinsPage(view);
                break;
            case 25:
                view = inflater.inflate(R.layout.fragment_skin_shop_25, container, false);
                setOwnedSkinsPage(view);
                break;
            case 26:
                view = inflater.inflate(R.layout.fragment_skin_shop_26, container, false);
                setOwnedSkinsPage(view);
                break;
            case 27:
                view = inflater.inflate(R.layout.fragment_skin_shop_27, container, false);
                setOwnedSkinsPage(view);
                break;
            case 28:
                view = inflater.inflate(R.layout.fragment_skin_shop_28, container, false);
                setOwnedSkinsPage(view);
                break;
            case 29:
                view = inflater.inflate(R.layout.fragment_skin_shop_29, container, false);
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

                if (storageManager.getSelectedSkinLabel() == v.getId())
                    ((TextView) v).setText("Selected");
            }
            else if(v instanceof ViewGroup)
                setOwnedSkinsPage(v);
        }
    }
}
