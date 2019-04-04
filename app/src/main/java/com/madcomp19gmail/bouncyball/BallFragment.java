package com.madcomp19gmail.bouncyball;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BallFragment extends Fragment {

    private StorageManager storageManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();
        int pageNumber = bundle.getInt("pageNumber");
        storageManager = StorageManager.getInstance();
        String packageName = getContext().getPackageName();

        int fragment_id = getResources().getIdentifier("fragment_ball_shop_" + pageNumber,
                "layout", packageName);

        view = inflater.inflate(fragment_id, container, false);
        setOwnedBallsPage(view);
        initializeImageButtons(view);

        return view;
    }

    private void initializeImageButtons(View view) {
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = viewGroup.getChildAt(i);

            if (v instanceof ImageButton) {
                ImageButton imageButton = (ImageButton) v;

                String id = getResources().getResourceName(imageButton.getId()).split("/")[1].replace("_Button", "");
                int image_id = getResources().getIdentifier(id, "drawable", getActivity().getPackageName());

                Glide.with(this).load(image_id).into(imageButton);
            } else if (v instanceof ViewGroup)
                initializeImageButtons((ViewGroup) v);
        }
    }

    private void setOwnedBallsPage(View view) {
        ArrayList<Integer> label_ids = storageManager.getOwnedBallsLabels();
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = viewGroup.getChildAt(i);

            if (v instanceof TextView) {
                TextView label = ((TextView) v);

                if (label_ids.contains(v.getId())) {
                    label.setText("Owned");
                    label.setCompoundDrawables(null, null, null, null);
                    label.setPadding(0, 0, 0, 0);
                }

                if (storageManager.getSelectedBallLabel() == v.getId()) {
                    label.setText("");
                    label.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selected_icon_vector, 0, 0);
                    label.setPadding(0, 10, 0, 0);
                }
            } else if (v instanceof ViewGroup)
                setOwnedBallsPage(v);
        }
    }
}