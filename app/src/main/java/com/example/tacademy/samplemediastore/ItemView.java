package com.example.tacademy.samplemediastore;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Created by Tacademy on 2016-04-29.
 */
public class ItemView extends FrameLayout implements Checkable{
    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemView(Context context) {
        super(context);
        init();
    }
    private void drawCheck(){
        if(isChecked)
        {
            setBackgroundColor(Color.BLUE);
        }
        else{
            setBackgroundColor(Color.WHITE);
        }
    }
    boolean isChecked=false;
    @Override
    public void setChecked(boolean checked) {
        if(!isChecked)
        {
            isChecked=true;
        }


    }
    private void init(){
        inflate(getContext(),R.layout.view_item,this);
    }
    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }
}
