package com.example.snoee.myapplistview.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snoee.myapplistview.R;

public class DevFrag extends DialogFragment implements View.OnClickListener {
    Button sendFeedback;
    EditText feedbackEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dev, container, false);

        feedbackEt = (EditText) v.findViewById(R.id.feedback);
        sendFeedback = (Button) v.findViewById(R.id.send_feedback);
        sendFeedback.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_feedback:
                Toast.makeText(getActivity(),  feedbackEt.getText()+" feedback sent", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.dialog_width), getResources().getDimensionPixelSize(R.dimen.dialog_height));
    }
}
