package com.cft.CornerCircularView;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cft.ad.ccv.CornerCircularView;
import com.cft.ad.ccv.ModelInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private CornerCircularView cornerCircularView;
    private List<ModelInfo> modelInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cornerCircularView = findViewById(R.id.ccv);
        modelInfos = new ArrayList<>();
        modelInfos.add(new ModelInfo("Message", Color.RED,R.drawable.message,R.color.colorAccent,null ));
        modelInfos.add(new ModelInfo("Mic",Color.BLUE,R.drawable.mic,R.color.colorPrimary,null ));
        modelInfos.add(new ModelInfo("Photo",Color.GREEN,R.drawable.photo,R.color.colorPrimaryDark,null ));
        modelInfos.add(new ModelInfo("Settings",Color.YELLOW,R.drawable.settings,R.color.colorAccent,null ));

        cornerCircularView.setMenuFromList(modelInfos);
        cornerCircularView.setAddButtonColor(Color.BLACK);
    }
}
