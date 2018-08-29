package com.cft.ad.ccv;

import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

public class ViewInfo {

    private String text;
    private FloatingActionButton actionButton;
    private TextView textView;
    private int buttonBackground, angle, buttonIcon, id, textColor;


    public ViewInfo(String text, TextView textView,int textColor, FloatingActionButton actionButton,  int buttonBackground, int buttonIcon, int angle) {
        this.text = text;
        this.textView = textView;
        this.textColor = textColor;
        this.actionButton = actionButton;
        this.buttonBackground = buttonBackground;
        this.buttonIcon = buttonIcon;
        this.angle = angle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public FloatingActionButton getActionButton() {
        return actionButton;
    }

    public TextView getTextView() {
        return textView;
    }

    public int getButtonBackground() {
        return buttonBackground;
    }

    public int getAngle() {
        return angle;
    }

    public int getButtonIcon() {
        return buttonIcon;
    }

    public int getId() {
        return id;
    }

    public int getTextColor() {
        return textColor;
    }
}
