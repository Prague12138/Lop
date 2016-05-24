package com.marvin.lop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;

/**
 * Created by Marvin on 2016/5/7.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class PublishCategoryListActivity extends BaseActivity {

    private static final String TAG = PublishCategoryListActivity.class.getSimpleName();

    private ImageButton mBack_imgbtn;
    private ListView list;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_category_list);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBack_imgbtn = (ImageButton) findViewById(R.id.publish_category_list_back_button);
        list = (ListView) findViewById(R.id.publish_category_list_listview);
        title = (TextView) findViewById(R.id.publish_category_list_title);
    }

    @Override
    protected void initView() {
        mBack_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮返回上一个页面
                setResult(Constants.IntentResultCode.PublishCategoryListBack2Publish);
                finish();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.category_all));

        list.setAdapter(adapter);

        // 当Item被点击
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position1, long id) {
                switch (position1) {
                    case 0:
                        title.setText("女装");
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category01));
                        list.setAdapter(adapter1);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category01)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 1:
                        title.setText("男装");
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category02));
                        list.setAdapter(adapter2);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category02)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 2:
                        title.setText("鞋包配饰");
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category03));
                        list.setAdapter(adapter3);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category03)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 3:
                        title.setText("手机");
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category04));
                        list.setAdapter(adapter4);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category04)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 4:
                        title.setText("相机/摄像机");
                        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category05));
                        list.setAdapter(adapter5);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category05)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 5:
                        title.setText("电脑/电脑配件");
                        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category06));
                        list.setAdapter(adapter6);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category06)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 6:
                        title.setText("数码3C产品");
                        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category07));
                        list.setAdapter(adapter7);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category07)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 7:
                        title.setText("奢侈品转让");
                        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category08));
                        list.setAdapter(adapter8);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category08)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 8:
                        title.setText("服装/服饰");
                        ArrayAdapter<String> adapter9 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category09));
                        list.setAdapter(adapter9);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category09)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 9:
                        title.setText("美容/美颜/香水");
                        ArrayAdapter<String> adapter10 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category10));
                        list.setAdapter(adapter10);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category10)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 10:
                        title.setText("家居/日用品");
                        ArrayAdapter<String> adapter11 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category11));
                        list.setAdapter(adapter11);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category11)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 11:
                        title.setText("食品/保健品");
                        ArrayAdapter<String> adapter12 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category12));
                        list.setAdapter(adapter12);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category12)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 12:
                        title.setText("家用电器/影音设备");
                        ArrayAdapter<String> adapter13 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category13));
                        list.setAdapter(adapter13);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category13)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 13:
                        title.setText("母婴/儿童用品/玩具");
                        ArrayAdapter<String> adapter14 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category14));
                        list.setAdapter(adapter14);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category14)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 14:
                        title.setText("宠物/宠物用品");
                        ArrayAdapter<String> adapter15 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category15));
                        list.setAdapter(adapter15);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category15)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 15:
                        title.setText("生活服务/票务/卡券");
                        ArrayAdapter<String> adapter16 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category16));
                        list.setAdapter(adapter16);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category16)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 16:
                        title.setText("书刊音像/文体用品");
                        ArrayAdapter<String> adapter17 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category17));
                        list.setAdapter(adapter17);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category17)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 17:
                        title.setText("二手汽车");
                        ArrayAdapter<String> adapter18 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category18));
                        list.setAdapter(adapter18);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category18)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 18:
                        title.setText("汽摩/电动车/自行车");
                        ArrayAdapter<String> adapter19 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category19));
                        list.setAdapter(adapter19);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category19)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 19:
                        title.setText("珠宝/黄金/手表");
                        ArrayAdapter<String> adapter20 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category20));
                        list.setAdapter(adapter20);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category20)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 20:
                        title.setText("艺术品/收藏品/古董古玩");
                        ArrayAdapter<String> adapter21 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category21));
                        list.setAdapter(adapter21);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category21)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    case 21:
                        title.setText("其他闲置");
                        ArrayAdapter<String> adapter22 = new ArrayAdapter<>(PublishCategoryListActivity.this, android.R.layout.simple_list_item_1,
                                getResources().getStringArray(R.array.category22));
                        list.setAdapter(adapter22);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("categoryName1", getResources().getStringArray(R.array.category_all)[position1]);
                                intent.putExtra("categoryName2", getResources().getStringArray(R.array.category22)[position]);
                                setResult(Constants.IntentResultCode.CategoryName, intent);
                                finish();
                            }
                        });
                        break;
                    default:
                        break;
                }


            }
        });

    }
}
