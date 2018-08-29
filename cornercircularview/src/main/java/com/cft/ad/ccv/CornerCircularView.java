package com.cft.ad.ccv;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CornerCircularView extends ConstraintLayout implements View.OnTouchListener {

    private List<ViewInfo> viewInfoList;
    private List<ModelInfo> modelInfoList;
    private Context mContext;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton addButton;
    private boolean close, up, clockwise;
    private ValueAnimator anim;
    private int initialX, initialY, finalX, finalY, rotation, width, height, addButtonColor, currentValue, onScreenIndex[] = new int[3];
    private ConstraintSet set;

    public CornerCircularView(Context context) {
        super(context);
    }

    public CornerCircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_corner_circular_view, this);
        constraintLayout = findViewById(R.id.root_layout);
        addButton = findViewById(R.id.add);
        viewInfoList = new ArrayList<>();
        close = true;
        set = new ConstraintSet();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (close) {
                    addView();
                    addButton.setImageResource(R.drawable.minus);
                    close = false;
                } else {
                    removeView();
                    addButton.setImageResource(R.drawable.add);
                    close = true;
                }
            }
        });

    }

    public void setAddButtonColor(int addButtonColor) {
        this.addButtonColor = addButtonColor;
        addButton.setBackgroundTintList(ColorStateList.valueOf(this.addButtonColor));
    }

    public CornerCircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMenuFromList(List<ModelInfo> modelInfos) {
        modelInfoList = modelInfos;
        Log.i("Size", "" + viewInfoList.size());
        if (modelInfos.size() == 2) {
            viewInfoList.add(new ViewInfo(modelInfoList.get(0).getText(), new TextView(mContext), modelInfos.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(0).getButtonBackground(),
                    modelInfoList.get(0).getButtonIcon(), 0));

            viewInfoList.add(new ViewInfo(modelInfoList.get(1).getText(), new TextView(mContext), modelInfos.get(1).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(1).getButtonBackground(),
                    modelInfoList.get(1).getButtonIcon(), 315));

        } else if (modelInfos.size() == 1) {
            viewInfoList.add(new ViewInfo(modelInfoList.get(0).getText(), new TextView(mContext), modelInfos.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(0).getButtonBackground(),
                    modelInfoList.get(0).getButtonIcon(), 315));
        } else {
            viewInfoList.add(new ViewInfo(modelInfoList.get(0).getText(), new TextView(mContext), modelInfos.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(0).getButtonBackground(),
                    modelInfoList.get(0).getButtonIcon(), 0));
            viewInfoList.add(new ViewInfo(modelInfoList.get(1).getText(), new TextView(mContext), modelInfos.get(1).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(1).getButtonBackground(),
                    modelInfoList.get(1).getButtonIcon(), 315));
            viewInfoList.add(new ViewInfo(modelInfoList.get(2).getText(), new TextView(mContext), modelInfos.get(2).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(2).getButtonBackground(),
                    modelInfoList.get(2).getButtonIcon(), 270));
            for (int i = 3; i < modelInfos.size(); i++) {
                viewInfoList.add(new ViewInfo(modelInfoList.get(i).getText(), new TextView(mContext), modelInfos.get(i).getTextColor(),
                        new FloatingActionButton(mContext), modelInfoList.get(i).getButtonBackground(),
                        modelInfoList.get(i).getButtonIcon(), 45));
            }
        }
    }


    private void addView() {
        Log.i("Size", "" + viewInfoList.size());
        currentValue = 1;
        onScreenIndex = new int[]{0, 1, 2};
        for (int i = 0; i < viewInfoList.size(); i++) {
            FloatingActionButton fabTemp = viewInfoList.get(i).getActionButton();
            TextView textTemp = viewInfoList.get(i).getTextView();
            fabTemp.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(viewInfoList.get(i).getButtonBackground())));
            fabTemp.setImageResource(viewInfoList.get(i).getButtonIcon());
            fabTemp.setId(View.generateViewId());
            fabTemp.setLayoutParams(new Constraints.LayoutParams((int) getResources().getDimension(R.dimen._45sdp),
                    (int) getResources().getDimension(R.dimen._45sdp)));
            textTemp.setId(View.generateViewId());
            textTemp.setText(viewInfoList.get(i).getText());
            textTemp.setTextColor(viewInfoList.get(i).getTextColor());
            textTemp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen._4sdp));
            constraintLayout.addView(fabTemp);
            constraintLayout.addView(textTemp);
            set.clone(constraintLayout);
            set.constrainCircle(fabTemp.getId(), addButton.getId(), (int) (width * 0.3), viewInfoList.get(i).getAngle());
            set.connect(textTemp.getId(), ConstraintSet.TOP, fabTemp.getId(), ConstraintSet.BOTTOM, 0);
            set.connect(textTemp.getId(), ConstraintSet.RIGHT, fabTemp.getId(), ConstraintSet.RIGHT, 0);
            set.connect(textTemp.getId(), ConstraintSet.LEFT, fabTemp.getId(), ConstraintSet.LEFT, 0);
            set.applyTo(constraintLayout);
            viewInfoList.get(i).setId(fabTemp.getId());
            fabTemp.setOnTouchListener(this);
        }
    }

    private void removeView() {
        FloatingActionButton fabTemp;
        TextView textTemp;
        for (int i = 0; i < viewInfoList.size(); i++) {
            fabTemp = viewInfoList.get(i).getActionButton();
            textTemp = viewInfoList.get(i).getTextView();
            constraintLayout.removeView(fabTemp);
            constraintLayout.removeView(textTemp);
        }
    }

    private void onClickButton(View v) {

        for (int i = 0; i < viewInfoList.size(); i++) {
            if (v.getId() == viewInfoList.get(i).getId()) {
                if (modelInfoList.get(i).getIntentForActivity() != null)
                    mContext.startActivity(modelInfoList.get(i).getIntentForActivity());
                break;
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                initialX = (int) motionEvent.getRawX() - width;
                initialY = (int) motionEvent.getRawY() - height;
                up = true;
                break;
            case MotionEvent.ACTION_UP:
                up = false;
                finalX = (int) motionEvent.getRawX() - width;
                finalY = (int) motionEvent.getRawY() - height;
                if (initialX == finalX && initialY == initialY)
                    onClickButton(view);
                break;
            case MotionEvent.ACTION_MOVE:
                if (viewInfoList.size() > 3) {
                    finalX = (int) motionEvent.getRawX() - width;
                    finalY = (int) motionEvent.getRawY() - height;
                    rotation = (initialX * finalY - initialY * finalX);
                    if (rotation > 0 && up) {
                        up = false;
                        clockwise = true;
                    } else if (rotation < 0 && up) {
                        up = false;
                        clockwise = false;
                    }
                    if (clockwise) {
                        if (currentValue == 45) {
                            for (int i = 0; i < 3; i++)
                                onScreenIndex[i] = onScreenIndex[i] < viewInfoList.size() - 1 ? onScreenIndex[i] + 1 : 0;
                            currentValue = 1;
                        }
                        animate(2, 315, 270);
                    } else {
                        if (currentValue == -45) {
                            for (int i = 2; i >= 0; i--)
                                onScreenIndex[i] = onScreenIndex[i] > 0 ? onScreenIndex[i] - 1 : viewInfoList.size() - 1;
                            currentValue = 1;
                        }
                        animate(-2, -45, -90);
                    }
                }
                break;
        }
        return true;
    }

    private void animate(int degRange, final int angle1, final int angle2) {
        currentValue += degRange;
        anim = ValueAnimator.ofInt(currentValue + degRange, currentValue);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                int val = (Integer) valueAnimator.getAnimatedValue();
                ConstraintLayout.LayoutParams element1 = (ConstraintLayout.LayoutParams) viewInfoList.get(onScreenIndex[0]).getActionButton().getLayoutParams();
                ConstraintLayout.LayoutParams element2 = (ConstraintLayout.LayoutParams) viewInfoList.get(onScreenIndex[1]).getActionButton().getLayoutParams();
                ConstraintLayout.LayoutParams element3 = (ConstraintLayout.LayoutParams) viewInfoList.get(onScreenIndex[2]).getActionButton().getLayoutParams();

                element1.circleAngle = val;
                element2.circleAngle = val + angle1;
                element3.circleAngle = val + angle2;
                viewInfoList.get(onScreenIndex[0]).getActionButton().setLayoutParams(element1);
                viewInfoList.get(onScreenIndex[1]).getActionButton().setLayoutParams(element2);
                viewInfoList.get(onScreenIndex[2]).getActionButton().setLayoutParams(element3);
            }
        });
        anim.start();
    }
}
