package com.marvin.lop.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;

/**
 * Created by Marvin on 2016/4/18.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class HandleCertifiedCursorAdapter extends CursorAdapter {

    private static final String TAG = HandleCertifiedCursorAdapter.class.getSimpleName();

    private Context context;

    public HandleCertifiedCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_adapter_authen_request, parent, false);

        viewHolder.tv_name = (TextView) view.findViewById(R.id.listview_adapter_authen_request_name);
        viewHolder.tv_studentid = (TextView) view.findViewById(R.id.listview_adapter_authen_request_studentid);
        view.setTag(viewHolder);
        Log.i(TAG, "newView = " + view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.i(TAG, "bindView = " + view);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String name = cursor.getString(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.RealName));//从数据库中查询姓名字段
        String studentid = cursor.getString(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.StudentID));//从数据库中查询学号字段

        viewHolder.tv_name.setText(name);
        viewHolder.tv_studentid.setText(studentid);

    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
    }

    class ViewHolder {
        TextView tv_name;
        TextView tv_studentid;
    }
}
