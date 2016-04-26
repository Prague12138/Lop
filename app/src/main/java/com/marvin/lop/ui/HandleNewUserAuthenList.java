package com.marvin.lop.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.marvin.lop.R;
import com.marvin.lop.adapter.HandleCertifiedCursorAdapter;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.AuthenRequestDataBaseHelper;
import com.marvin.lop.task.LoadingAuthenRequestTask;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.ui.linstener.OnDataLoadSuccessLinstener;

/**
 * Created by Marvin on 2016/4/16.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 显示用户请求认证的列表
 */
public class HandleNewUserAuthenList extends BaseActivity {

    private static final String TAG = HandleNewUserAuthenList.class.getSimpleName();

    private ImageButton mBack_btn;
    private ListView listView;

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private HandleCertifiedCursorAdapter handleCertifiedCursorAdapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_authen_request);

        findViewById();
        // 使用异步加载去获取服务器数据
//        LoadingAuthenRequestTask loadAuthenRequestTask = new LoadingAuthenRequestTask(this);
//        loadAuthenRequestTask.setOnDataLoadSuccessLinstener(new OnDataLoadSuccessLinstener() {
//            @Override
//            public void onSuccess(Boolean isSuccess) {
//                DisPlay("SHOW&&&&");
//                //notifyListDataChange();
//                if (isSuccess) {
//
//                }
//
//            }
//        });
//        loadAuthenRequestTask.execute();
        initView();

    }

    @Override
    protected void findViewById() {
        mBack_btn = (ImageButton) findViewById(R.id.listview_authen_request_back_button);
        listView = (ListView) findViewById(R.id.listview_authen_request);
    }

    @Override
    protected void initView() {
        Log.i(TAG, "使用异步加载去获取服务器的数据");

        // 初始化数据库
        openHelper = new AuthenRequestDataBaseHelper(HandleNewUserAuthenList.this,
                Constants.AuthenRequestDataBaseConfig.DataBaseName, null, 1);
        database = openHelper.getWritableDatabase();

        cursor = database.rawQuery("select * from " + Constants.AuthenRequestDataBaseConfig.TableName, null);
        handleCertifiedCursorAdapter = new HandleCertifiedCursorAdapter(HandleNewUserAuthenList.this, cursor, true);
        if (listView != null) {
            Log.i(TAG, "setAdapter()设置Adapter给ListView");
            listView.setAdapter(handleCertifiedCursorAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                // 发送被点击的数据项的索引
                mIntent.putExtra("_id", cursor.getInt(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.ID)));
                mIntent.setClass(HandleNewUserAuthenList.this, HandlingOthersCertifiedActivity.class);
                startActivityForResult(mIntent, Constants.IntentRequestCode.AuthenHandleList2HandleActivity);
            }
        });

    }

    protected void notifyListDataChange() {
        handleCertifiedCursorAdapter.getCursor().close();
        cursor = database.rawQuery("select * from " + Constants.AuthenRequestDataBaseConfig.TableName, null);
        handleCertifiedCursorAdapter.changeCursor(cursor);
        handleCertifiedCursorAdapter.notifyDataSetChanged();
        Toast.makeText(HandleNewUserAuthenList.this, "Res!!!!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.IntentRequestCode.AuthenHandleList2HandleActivity) {
            switch (resultCode) {
                case Constants.IntentResultCode.HandleActivity2AuthenHandleList:
                    // 当从处理界面返回时，更新数据给List
                    Log.i(TAG, "从处理界面返回ListView");
                    notifyListDataChange();
                    if (handleCertifiedCursorAdapter.getCount() == 0) {
                        Log.i(TAG, "本地数据库没有可以处理的请求用户数据");
                        HandleNewUserAuthenList.this.finish();
                    }
                    break;
                case Constants.IntentResultCode.AuthenHandleBackToAuthenList:
                    // 在处理界面点击返回按钮, 不做任何处理
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
        openHelper.close();
    }

}
