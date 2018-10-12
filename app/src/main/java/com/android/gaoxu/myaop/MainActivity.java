package com.android.gaoxu.myaop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.gaoxu.aoplibrary.annotation.CheckNet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = this.findViewById(R.id.checkNet);
        button.setOnClickListener(this);
    }

    @Override
    @CheckNet()
    public void onClick(View view) {
        Toast.makeText(this, "有网络连接", Toast.LENGTH_LONG).show();
    }
}
