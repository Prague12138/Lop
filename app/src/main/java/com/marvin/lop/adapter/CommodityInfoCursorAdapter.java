package com.marvin.lop.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.marvin.lop.R;
import com.marvin.lop.config.Constants;


/**
 * Created by Marvin on 2016/6/10.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CommodityInfoCursorAdapter extends CursorAdapter {

    private static final String TAG = CommodityInfoCursorAdapter.class.getSimpleName();

    private Context context;

    public CommodityInfoCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        //初始化Fresco开源库
        Fresco.initialize(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_commodityinfo_index, parent, false);

        viewHolder.nickName = (TextView) view.findViewById(R.id.list_commodityinfo_index_nickname);
        viewHolder.publishTime = (TextView) view.findViewById(R.id.list_commodityinfo_index_publishtime);
        viewHolder.price = (TextView) view.findViewById(R.id.list_commodityinfo_index_price);
        viewHolder.oriPrice = (TextView) view.findViewById(R.id.list_commodityinfo_index_oripirce);
        viewHolder.img1 = (SimpleDraweeView) view.findViewById(R.id.list_commodityinfo_index_img1);
        viewHolder.img2 = (SimpleDraweeView) view.findViewById(R.id.list_commodityinfo_index_img2);
        viewHolder.img3 = (SimpleDraweeView) view.findViewById(R.id.list_commodityinfo_index_img3);
        viewHolder.img4 = (SimpleDraweeView) view.findViewById(R.id.list_commodityinfo_index_img4);
        viewHolder.img5 = (SimpleDraweeView) view.findViewById(R.id.list_commodityinfo_index_img5);
        viewHolder.title = (TextView) view.findViewById(R.id.list_commodityinfo_index_title);
        viewHolder.category = (TextView) view.findViewById(R.id.list_commodityinfo_index_category);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String name = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.UserPhoneNumber));
        String time = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CreateAt));
        String price = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPrice));
        String oriPrice = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityOriPrice));
        String picUri1 = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath1));
        String picUri2 = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath2));
        String picUri3 = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath3));
        String picUri4 = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath4));
        String picUri5 = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath5));
        String title = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityTitle));
        String category1 = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityCategory1));

        Uri uri1 = Uri.parse(picUri1);
        Uri uri2 = Uri.parse(picUri2);
        Uri uri3 = Uri.parse(picUri3);
        Uri uri4 = Uri.parse(picUri4);
        Uri uri5 = Uri.parse(picUri5);
        viewHolder.nickName.setText(name);
        viewHolder.publishTime.setText(time);
        viewHolder.price.setText(price);
        viewHolder.oriPrice.setText(oriPrice);
        viewHolder.img1.setImageURI(uri1);
        viewHolder.img2.setImageURI(uri2);
        viewHolder.img3.setImageURI(uri3);
        viewHolder.img4.setImageURI(uri4);
        viewHolder.img5.setImageURI(uri5);
        viewHolder.title.setText(title);
        viewHolder.category.setText(category1);
        Log.i(TAG, "objectid = " + name);
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
    }

    class ViewHolder {
        TextView nickName;
        TextView publishTime;
        TextView price;
        TextView oriPrice;
        SimpleDraweeView img1;
        SimpleDraweeView img2;
        SimpleDraweeView img3;
        SimpleDraweeView img4;
        SimpleDraweeView img5;
        TextView title;
        TextView category;
    }
}

