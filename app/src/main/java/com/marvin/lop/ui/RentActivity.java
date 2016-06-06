package com.marvin.lop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.bean.RentInfo;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CreateNewCommodity;
import com.marvin.lop.utils.CreateNewRent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 发布出租信息的界面
 */
public class RentActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = RentActivity.class.getSimpleName();

    private ImageButton mBackbtn;
    private EditText mTitle;
    private EditText mDes;
    private ImageView img01;
    private ImageView img02;
    private ImageView img03;
    private ImageView img04;
    private ImageView img05;
    private EditText mPrice;
    private TextView mCategory;
    private RelativeLayout mCategoryClick;
    private Button mRent;

    private String itemTitle;//物品标题
    private String itemDes;//物品描述
    private String itemPrice;//价格
    private String categoryName1;//物品分类大类
    private String categoryName2;//物品分类小类
    private String picPath01;
    private String picPath02;
    private String picPath03;
    private String picPath04;
    private String picPath05;
    private String picAbsPath1;
    private String picAbsPath2;
    private String picAbsPath3;
    private String picAbsPath4;
    private String picAbsPath5;

    private Bitmap defaultBitmap;//默认显示的图片

    // 记录图片是否已经替换
    private boolean pic01 = false;
    private boolean pic02 = false;
    private boolean pic03 = false;
    private boolean pic04 = false;
    private boolean pic05 = false;

    // 记录是否分类
    private boolean isCategory = false;

    private Uri imageUri01;
    private Uri imageUri02;
    private Uri imageUri03;
    private Uri imageUri04;
    private Uri imageUri05;

    private RentInfo rentInfo;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBackbtn = (ImageButton) findViewById(R.id.rent_back_button);
        mTitle = (EditText) findViewById(R.id.rent_item_title_et);
        mDes = (EditText) findViewById(R.id.rent_item_description_et);
        img01 = (ImageView) findViewById(R.id.rent_pic_01);
        img02 = (ImageView) findViewById(R.id.rent_pic_02);
        img03 = (ImageView) findViewById(R.id.rent_pic_03);
        img04 = (ImageView) findViewById(R.id.rent_pic_04);
        img05 = (ImageView) findViewById(R.id.rent_pic_05);
        mPrice = (EditText) findViewById(R.id.rent_price_default);
        mCategory = (TextView) findViewById(R.id.rent_classification);
        mCategoryClick = (RelativeLayout) findViewById(R.id.rent_classification_layout_click);
        mRent = (Button) findViewById(R.id.rent_btn);
    }

    @Override
    protected void initView() {
        rentInfo = new RentInfo();
        defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.publish_add_pic_default);

        mBackbtn.setOnClickListener(this);
        img01.setOnClickListener(this);
        img02.setOnClickListener(this);
        img03.setOnClickListener(this);
        img04.setOnClickListener(this);
        img05.setOnClickListener(this);
        mCategoryClick.setOnClickListener(this);
        mRent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rent_back_button:
                // 返回个人信息中心界面
                setResult(Constants.IntentResultCode.RentBack2PersonalCenter);
                this.finish();
                break;
            case R.id.rent_pic_01:
                // 点击第一张图片，选择物品图片，作为主图片，替换掉默认的图片，必须提供5张物品图片
                // 下一张图片的位置显示默认图片,当图片已经设置之后，再次点击图片，弹出一个对话框
                // 选择是否删除当前图片
                if (!pic01) { // 如果当前图片是默认图片,点击该图片弹出对话框，选择是拍照还是从图库中选择一张图片
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("拍照或从图库中选择一张图片")
                            .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    picPath01 = Environment.getExternalStorageDirectory() + "/img01.jpg";
                                    File outputImage01 = new File(picPath01);
                                    try {
                                        if (outputImage01.exists()) {
                                            outputImage01.delete();
                                        }
                                        outputImage01.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    imageUri01 = Uri.fromFile(outputImage01);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri01);
                                    startActivityForResult(intent, Constants.IntentRequestCode.TakePicture01);
                                    pic01 = true;// 将图片状态改为已经修改
                                }
                            }).setNegativeButton("从图库选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 创建File对象，用于存储选择的照片
                            picPath01 = Environment.getExternalStorageDirectory() + "/outputimage01.jpg";
                            File image01 = new File(picPath01);
                            try {
                                if (image01.exists()) {
                                    image01.delete();
                                }
                                image01.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageUri01 = Uri.fromFile(image01);
                            Intent intent = new Intent("android.intent.action.PICK");
                            intent.setType("image/*");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri01);
                            startActivityForResult(intent, Constants.IntentRequestCode.TakePicture01);
                            pic01 = true;// 修改图片状态
                        }
                    }).show();
                } else { // 当前是替换后的图片，点击该图片弹出对话框，选择是否删除当前图片，删除后使用默认图片替换当前图片
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("是否删除当前图片")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    img01.setImageBitmap(defaultBitmap);
                                    pic01 = false;
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 无操作
                        }
                    }).show();
                }
                break;
            case R.id.rent_pic_02:
                if (!pic02) {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("拍照或从图库中选择一张图片")
                            .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    picPath02 = Environment.getExternalStorageDirectory() + "/img02.jpg";
                                    File outputImage02 = new File(picPath02);
                                    try {
                                        if (outputImage02.exists()) {
                                            outputImage02.delete();
                                        }
                                        outputImage02.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    imageUri02 = Uri.fromFile(outputImage02);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri02);
                                    startActivityForResult(intent, Constants.IntentRequestCode.TakePicture02);
                                    pic02 = true;// 将图片状态改为已经修改
                                }
                            }).setNegativeButton("从图库选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            picPath02 = Environment.getExternalStorageDirectory() + "/outputimage02.jpg";
                            File image02 = new File(picPath02);
                            try {
                                if (image02.exists()) {
                                    image02.delete();
                                }
                                image02.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageUri02 = Uri.fromFile(image02);
                            Intent intent = new Intent("android.intent.action.PICK");
                            intent.setType("image/*");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri02);
                            startActivityForResult(intent, Constants.IntentRequestCode.TakePicture02);
                            pic02 = true;// 修改图片状态
                        }
                    }).show();
                } else {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("是否删除当前图片")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    img02.setImageBitmap(defaultBitmap);
                                    pic02 = false;
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 无操作
                        }
                    }).show();
                }
                break;
            case R.id.rent_pic_03:
                if (!pic03) {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("拍照或从图库中选择一张图片")
                            .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    picPath03 = Environment.getExternalStorageDirectory() + "/img03.jpg";
                                    File outputImage03 = new File(picPath03);
                                    try {
                                        if (outputImage03.exists()) {
                                            outputImage03.delete();
                                        }
                                        outputImage03.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    imageUri03 = Uri.fromFile(outputImage03);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri03);
                                    startActivityForResult(intent, Constants.IntentRequestCode.TakePicture03);
                                    pic03 = true;// 将图片状态改为已经修改
                                }
                            }).setNegativeButton("从图库选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            picPath03 = Environment.getExternalStorageDirectory() + "/outputimage03.jpg";
                            File image03 = new File(picPath03);
                            try {
                                if (image03.exists()) {
                                    image03.delete();
                                }
                                image03.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageUri03 = Uri.fromFile(image03);
                            Intent intent = new Intent("android.intent.action.PICK");
                            intent.setType("image/*");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri03);
                            startActivityForResult(intent, Constants.IntentRequestCode.TakePicture03);
                            pic03 = true;// 修改图片状态
                        }
                    }).show();
                } else {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("是否删除当前图片")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    img03.setImageBitmap(defaultBitmap);
                                    pic03 = false;
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 无操作
                        }
                    }).show();
                }
                break;
            case R.id.rent_pic_04:
                if (!pic04) {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("拍照或从图库中选择一张图片")
                            .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    picPath04 = Environment.getExternalStorageDirectory() + "/img04.jpg";
                                    File outputImage04 = new File(picPath04);
                                    try {
                                        if (outputImage04.exists()) {
                                            outputImage04.delete();
                                        }
                                        outputImage04.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    imageUri04 = Uri.fromFile(outputImage04);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri04);
                                    startActivityForResult(intent, Constants.IntentRequestCode.TakePicture04);
                                    pic04 = true;// 将图片状态改为已经修改
                                }
                            }).setNegativeButton("从图库选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            picPath04 = Environment.getExternalStorageDirectory() + "/outputimage04.jpg";
                            File image04 = new File(picPath04);
                            try {
                                if (image04.exists()) {
                                    image04.delete();
                                }
                                image04.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageUri04 = Uri.fromFile(image04);
                            Intent intent = new Intent("android.intent.action.PICK");
                            intent.setType("image/*");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri04);
                            startActivityForResult(intent, Constants.IntentRequestCode.TakePicture04);
                            pic04 = true;// 修改图片状态
                        }
                    }).show();
                } else {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("是否删除当前图片")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    img04.setImageBitmap(defaultBitmap);
                                    pic04 = false;
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 无操作
                        }
                    }).show();
                }
                break;
            case R.id.rent_pic_05:
                if (!pic05) {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("拍照或从图库中选择一张图片")
                            .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    picPath05 = Environment.getExternalStorageDirectory() + "/img05.jpg";
                                    File outputImage05 = new File(picPath05);
                                    try {
                                        if (outputImage05.exists()) {
                                            outputImage05.delete();
                                        }
                                        outputImage05.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    imageUri05 = Uri.fromFile(outputImage05);
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri05);
                                    startActivityForResult(intent, Constants.IntentRequestCode.TakePicture05);
                                    pic05 = true;// 将图片状态改为已经修改
                                }
                            }).setNegativeButton("从图库选择", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            picPath05 = Environment.getExternalStorageDirectory() + "/outputimage05.jpg";
                            File image05 = new File(picPath05);
                            try {
                                if (image05.exists()) {
                                    image05.delete();
                                }
                                image05.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageUri05 = Uri.fromFile(image05);
                            Intent intent = new Intent("android.intent.action.PICK");
                            intent.setType("image/*");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri05);
                            startActivityForResult(intent, Constants.IntentRequestCode.TakePicture05);
                            pic05 = true;// 修改图片状态
                        }
                    }).show();
                } else {
                    new AlertDialog.Builder(RentActivity.this)
                            .setMessage("是否删除当前图片")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    img05.setImageBitmap(defaultBitmap);
                                    pic05 = false;
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 无操作
                        }
                    }).show();
                }
                break;
            case R.id.rent_classification_layout_click:
                // 选择物品所在分类
                Intent mIntent = new Intent(RentActivity.this, PublishCategoryListActivity.class);
                startActivityForResult(mIntent, Constants.IntentRequestCode.Publish2PublishCategoryList);
                break;
            case R.id.rent_btn:
                // 提交物品信息之后 返回到个人信息界面并将物品信息在后台上传到服务器
                // TODO: 2016/5/7  另外，需要把上传物品信息作为异步操作来处理，当上传成功，上传的时候在通知里面显示一个进度条用来表示上传的进度
                if (!mTitle.getText().toString().equals("")) {
                    if (!mDes.getText().toString().equals("")) {
                        if (pic01 && pic02 && pic03 && pic04 && pic05) {
                            if (!mPrice.getText().toString().equals("")) {
                                if (isCategory) {
                                    itemTitle = mTitle.getText().toString();
                                    itemDes = mDes.getText().toString();
                                    itemPrice = mPrice.getText().toString();
                                    final String[] filePaths = new String[5];
                                    filePaths[0] = picPath01;
                                    filePaths[1] = picPath02;
                                    filePaths[2] = picPath03;
                                    filePaths[3] = picPath04;
                                    filePaths[4] = picPath05;
                                    BmobFile.uploadBatch(RentActivity.this, filePaths,
                                            new UploadBatchListener() {
                                                @Override
                                                public void onSuccess(List<BmobFile> list, List<String> urls) {
                                                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                                                    //2、urls-上传文件的完整url地址
                                                    if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                                                        picAbsPath1 = urls.get(0);
                                                        picAbsPath2 = urls.get(1);
                                                        picAbsPath3 = urls.get(2);
                                                        picAbsPath4 = urls.get(3);
                                                        picAbsPath5 = urls.get(4);
                                                        Log.i(TAG, picAbsPath1);
                                                        Log.i(TAG, picAbsPath2);
                                                        Log.i(TAG, picAbsPath3);
                                                        Log.i(TAG, picAbsPath4);
                                                        Log.i(TAG, picAbsPath5);

                                                        // 将商品信息上传到数据库
                                                        CreateNewRent cnr = new CreateNewRent();
                                                        String userObjectId = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
                                                        rentInfo = cnr.createRentInfoInServer(itemTitle, itemDes, picAbsPath1, picAbsPath2,
                                                                picAbsPath3, picAbsPath4, picAbsPath5, itemPrice, categoryName1, categoryName2,
                                                                userObjectId, false);
                                                        rentInfo.save(RentActivity.this, new SaveListener() {
                                                            @Override
                                                            public void onSuccess() {
                                                                DisPlay("商品信息发布成功!");
                                                                // TODO: 2016/6/4 在发布商品信息的时候讲操作放到后台运行，就行发微博一样，先显示一个通知，显示正在发送，
                                                                // 发布成功后就提示发布成功
                                                                setResult(Constants.IntentResultCode.RentSuccess);
                                                                RentActivity.this.finish();
                                                            }

                                                            @Override
                                                            public void onFailure(int i, String s) {
                                                                DisPlay("商品信息发布失败!");
                                                                Log.i(TAG, i + "  " + s);
                                                            }
                                                        });
                                                    }

                                                }

                                                @Override
                                                public void onProgress(int i, int i1, int i2, int i3) {
                                                    //1、curIndex--表示当前第几个文件正在上传
                                                    //2、curPercent--表示当前上传文件的进度值（百分比）
                                                    //3、total--表示总的上传文件数
                                                    //4、totalPercent--表示总的上传进度（百分比）
                                                    Log.i(TAG, "curIndex = " + i);
                                                    Log.i(TAG, "curPercent = " + i1);
                                                    Log.i(TAG, "total = " + i2);
                                                    Log.i(TAG, "totalPercent = " + i3);
                                                }

                                                @Override
                                                public void onError(int statuscode, String errormsg) {
                                                    DisPlay("错误码" + statuscode + ",错误描述：" + errormsg);
                                                }
                                            });
                                } else {
                                    DisPlay("请选择物品分类");
                                }
                            } else {
                                DisPlay("请输入价格");
                            }
                        } else {
                            DisPlay("请上传5张物品图片");
                        }
                    } else {
                        DisPlay("请输入物品描述");
                    }
                } else {
                    DisPlay("请输入标题");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Uri uri = data.getData();
// 不能返回data的原因，有的机型的系统相机不返回data，只能手动提供照片的uri
        switch (requestCode) {
            case Constants.IntentRequestCode.TakePicture01:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(uri, "image/*");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri01);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture01);
                    } else {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(imageUri01, "image/*");
                        Log.i(TAG, imageUri01 + "");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri01);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture01);// 启动裁剪程序
                    }
                }
                break;
            case Constants.IntentRequestCode.CropPicture01:
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;//宽高为原来的四分之一
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri01), null, options);
                        img01.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case Constants.IntentRequestCode.TakePicture02:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(uri, "image/*");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri02);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture02);
                    } else {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(imageUri02, "image/*");
                        Log.i(TAG, imageUri02 + "");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri02);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture02);// 启动裁剪程序
                    }
                }
                break;
            case Constants.IntentRequestCode.CropPicture02:
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;//宽高为原来的四分之一
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri02), null, options);
                        img02.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.IntentRequestCode.TakePicture03:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(uri, "image/*");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri03);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture03);
                    } else {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(imageUri03, "image/*");
                        Log.i(TAG, imageUri03 + "");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri03);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture03);// 启动裁剪程序
                    }
                }
                break;
            case Constants.IntentRequestCode.CropPicture03:
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;//宽高为原来的四分之一
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri03), null, options);
                        img03.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.IntentRequestCode.TakePicture04:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(uri, "image/*");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri04);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture04);
                    } else {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(imageUri04, "image/*");
                        Log.i(TAG, imageUri04 + "");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri04);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture04);// 启动裁剪程序
                    }
                }
                break;
            case Constants.IntentRequestCode.CropPicture04:
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;//宽高为原来的四分之一
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri04), null, options);
                        img04.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.IntentRequestCode.TakePicture05:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(uri, "image/*");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri05);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture05);
                    } else {
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(imageUri05, "image/*");
                        Log.i(TAG, imageUri05 + "");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri05);
                        startActivityForResult(intent, Constants.IntentRequestCode.CropPicture05);// 启动裁剪程序
                    }
                }
                break;
            case Constants.IntentRequestCode.CropPicture05:
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;//宽高为原来的四分之一
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri05), null, options);
                        img05.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.IntentRequestCode.Publish2PublishCategoryList:
                switch (resultCode) {
                    case Constants.IntentResultCode.PublishCategoryListBack2Publish:
                        // 从物品分类界面返回该界面，没有操作
                        break;
                    case Constants.IntentResultCode.CategoryName:
                        Bundle bundle = data.getExtras();
                        categoryName1 = bundle.getString("categoryName1");
                        categoryName2 = bundle.getString("categoryName2");
                        Log.i(TAG, "CategoryName = " + categoryName1 + "--" + categoryName2);
                        mCategory.setText(categoryName1 + "--" + categoryName2);
                        isCategory = true;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
