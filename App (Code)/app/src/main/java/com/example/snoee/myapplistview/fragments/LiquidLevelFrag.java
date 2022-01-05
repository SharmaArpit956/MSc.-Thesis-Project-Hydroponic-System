package com.example.snoee.myapplistview.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.snoee.myapplistview.R;

public class LiquidLevelFrag extends Fragment {
    TextView liquidLevelTv;
    public double liquidLevel;

    private @StyleRes
    int themeResId;
    private static final int NO_CUSTOM_THEME = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ↓↓↓
        if (themeResId != NO_CUSTOM_THEME) {
            inflater = inflater.cloneInContext(
                    new ContextThemeWrapper(getActivity(), themeResId)
            );
        }
        // ↑↑↑
        View view = inflater.inflate(R.layout.fragment_liquid_level, container, false);
        liquidLevelTv = view.findViewById(R.id.liquid_level);
        return view;
    }

    @Override
    public void onInflate(
            @NonNull Context context,
            AttributeSet attrs,
            Bundle savedInstanceState
    ) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.ChildFragment
        );
        themeResId = a.getResourceId(
                R.styleable.ChildFragment_customTheme,
                NO_CUSTOM_THEME
        );
        a.recycle();
    }

    public void setLiquidLevelTv(double ll) {
        liquidLevel = ll;
        if (liquidLevelTv != null) {
            liquidLevelTv.setText(String.valueOf(ll));
        }
    }

}
