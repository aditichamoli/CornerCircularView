package com.cft.ad.ccv;

import android.content.Intent;
import android.support.annotation.Nullable;

public class ModelInfo {
    private String text;
    private int buttonBackground,textColor;
    private int buttonIcon;
    private Intent intentForActivity;

    public ModelInfo(String text,int textColor ,int buttonIcon ,int buttonBackground, @Nullable Intent intentForActivity) {
        this.text = text;
        this.textColor = textColor;
        this.buttonIcon = buttonIcon;
        this.buttonBackground = buttonBackground;
        this.intentForActivity = intentForActivity;
    }


    public String getText() {
        return text;
    }

    public int getButtonBackground() {
        return buttonBackground;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getButtonIcon() {
        return buttonIcon;
    }

    public Intent getIntentForActivity() {
        return intentForActivity;
    }
}
