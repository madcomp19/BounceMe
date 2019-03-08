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

        int fragment_id = this.getResources().getIdentifier("fragment_skin_shop_" + pageNumber,
                "layout", getActivity().getPackageName());

        view = inflater.inflate(fragment_id, container, false);
        setOwnedSkinsPage(view);

        return view;
    }

    private void setOwnedSkinsPage(View view) {
        ArrayList<Integer> label_ids = storageManager.getOwnedSkinsLabels();
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = viewGroup.getChildAt(i);

            if (v instanceof TextView) {
                if (label_ids.contains(v.getId()))
                    ((TextView) v).setText("Owned");

                if (storageManager.getSelectedSkinLabel() == v.getId())
                    ((TextView) v).setText("Selected");
            } else if (v instanceof ViewGroup)
                setOwnedSkinsPage(v);
        }
    }
}
