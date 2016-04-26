package com.marvin.lop.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.bean.AuthenRequestList;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.AuthenRequestDataBaseHelper;
import com.marvin.lop.widgets.AuthenRequestListViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marvin on 2016/4/16.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 用来处理认证请求信息的ListView的Adapter，异步加载请求信息
 */
//public class HandleCertifiedAdapter extends BaseAdapter {
//
//    private static final String TAG = HandleCertifiedAdapter.class.getSimpleName();
//
//    private List<AuthenRequestList> listItems;
//    private LayoutInflater listContainer;// 视图容器
//    private Context context;
//
//    public HandleCertifiedAdapter(Context context) {
//        this.context = context;
//        listItems = new ArrayList<>();
//        listContainer = LayoutInflater.from(context);//创建视图容器并设置上下文
//    }
//
//    @Override
//    public int getCount() {
//        return listItems.size();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.i(TAG, "getView()");
//        // 自定义视图
//        AuthenRequestListViewItem listViewItem = null;
//        if (convertView == null) {
//            listViewItem = new AuthenRequestListViewItem();
//            // 获取item布局文件的视图
//            convertView = listContainer.inflate(R.layout.listview_adapter_authen_request, null);
//            // 获取控件对象
//            listViewItem.setName((TextView) convertView.findViewById(R.id.listview_adapter_authen_request_name));
//            listViewItem.setStudentId((TextView) convertView.findViewById(R.id.listview_adapter_authen_request_studentid));
//            // 设置控件集到convertView
//            convertView.setTag(listViewItem);
//        } else {
//            listViewItem = (AuthenRequestListViewItem) convertView.getTag();
//        }
//
//        // 设置文字内容
////        listViewItem.getName().setText(listItems.get(position).getUserRealName());
////        listViewItem.getStudentId().setText(listItems.get(position).getUserStudentId());
//
//        // 查询数据库从数据库中获取数据
//        AuthenRequestDataBaseHelper dbHelper = new AuthenRequestDataBaseHelper(context,
//                Constants.AuthenRequestDataBaseConfig.DataBaseName, null, 1);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from " + Constants.AuthenRequestDataBaseConfig.TableName, null);
//        Cursor c;
//        if (cursor.getCount() < 1) {
//            Log.i(TAG, "该数据库为空，ListView没有数据可以显示");
//        } else {
//            Log.i(TAG, "数据库 不为空，ListView可以显示数据");
////            if (cursor.moveToFirst())
////            listViewItem.getName().setText(cursor.);
//        }
//
//        return convertView;
//    }
//
//    public List<AuthenRequestList> getListItems() {
//        return listItems;
//    }
//
//    public void setListItems(List<AuthenRequestList> listItems) {
//        this.listItems = listItems;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//}
