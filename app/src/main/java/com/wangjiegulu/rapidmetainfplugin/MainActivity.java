package com.wangjiegulu.rapidmetainfplugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wangjiegulu.rapidmetainfplugin.depmodule.DepModuleTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DepModuleTest().print();
    }
}
