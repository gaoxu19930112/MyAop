package com.android.gaoxu.myaop;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.gaoxu.aoplibrary.annotation.ApplyPermission;
import com.android.gaoxu.aoplibrary.annotation.ApplyPermissionFailedCallback;
import com.android.gaoxu.aoplibrary.annotation.CheckNet;
import com.android.gaoxu.aoplibrary.annotation.SingleClick;
import com.android.gaoxu.aoplibrary.aspect.SingleClickAspect;
import com.android.gaoxu.aoplibrary.constant.PermissionConstants;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private ListView mListView;

    private String[] data = {"防止多重点击", "没有网络没有方法参数就弹Toast", "没有网络有方法参数就执行该方法",
            "判断是否有权限并申请权限"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = this.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, data);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SingleClick
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.e("singleClick", "测试singleClick");
               switch (position) {
                   case 1:
                       checkNetTest();
                       break;
                   case 2:
                       checkNetTestHasParam();
                       break;
                   case 3:
                       applyPermissionTest();
                       break;
                   default:
                       break;
               }
            }
        });
    }

    @CheckNet
    private void checkNetTest(){
        Log.e(TAG, "checkNetTest: 网络已连接，dothing" );
    }

    @CheckNet(notNetMethod = "notNet")
    private void checkNetTestHasParam(){
        Log.e(TAG, "checkNetTestHasParam: 网络已连接，dothing" );
    }

    private void notNet(){
        Toast.makeText(MainActivity.this, "没有网络执行方法", Toast.LENGTH_LONG).show();
    }

    @ApplyPermission({PermissionConstants.CAMERA,PermissionConstants.STORAGE})
    private void applyPermissionTest() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @ApplyPermissionFailedCallback
    public void applyPermissionFailedTest(String[] failedPermissions) {
        Toast.makeText(MainActivity.this, "权限请求失败", Toast.LENGTH_LONG).show();
    }
}
