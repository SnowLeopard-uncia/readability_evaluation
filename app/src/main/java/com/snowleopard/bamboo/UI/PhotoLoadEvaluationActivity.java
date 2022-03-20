package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION.SDK_INT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.Util.AccurateBasic;
import com.snowleopard.bamboo.Util.AuthService;
import com.snowleopard.bamboo.Util.AuthServiceSafe;
import com.snowleopard.bamboo.javaBean.OCRResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoLoadEvaluationActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_result;
    private Button btn_pop_load;
    private Uri imageUri;
    private EditText  et_text_photo;
    private String imagePathLocal;
    private String result;
    private List<OCRResult> mOCRResultList = new ArrayList<>();
    private String accessToken;
    public static  final  int TAKE_PHOTO=1;
    public static  final  int CHOOSE_PHOTO=2;

    private Handler handler = new Handler(){
        // 覆写这个方法，接收并处理消息。
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    result = parseJsonDataWithGson(result);
                    et_text_photo.setText(result);
                    break;
                case 2:
                    OCR();
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_load);
        initNavBar(true,getResources().getString(R.string.text_title));
        initView();
    }


    private void initView() {
        et_text_photo=findViewById(R.id.et_text_photo);
        btn_result=findViewById(R.id.btn_photo_score);
        btn_pop_load=findViewById(R.id.btn_pop_upload);
        btn_pop_load.setOnClickListener(this);
        btn_result.setOnClickListener((view)->{
            Intent intent = new Intent(PhotoLoadEvaluationActivity.this,ResultEvaluationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_pop_upload) {
            showBottomDialog();
        }
    }

    private void showBottomDialog() {
        //1使用dialog 设置style
        final Dialog dialog = new Dialog(this,R.style.PopStyleTheme);
        //设置布局
        View view = View.inflate(this,R.layout.dialog_pop_layout,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置 这里为底部
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.pop_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.tv_take_photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (SDK_INT>24){
                    imageUri = FileProvider.getUriForFile(PhotoLoadEvaluationActivity.this,"com.snowleopard.bamboo.UI.FileLoadEvaluationActivity.fileprovider",outputImage);

                }else {
                    imageUri=Uri.fromFile(outputImage);
                }
                openCamara();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PhotoLoadEvaluationActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED||
                        ContextCompat.checkSelfPermission(PhotoLoadEvaluationActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED    ){
                    Toast.makeText(PhotoLoadEvaluationActivity.this,"需要权限",Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(PhotoLoadEvaluationActivity.this, new
                            String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },1);//运行时权限处理，申请权限对SD卡读和写

                }else { //打开相册页面
                    openAlbum();
                }
                dialog.dismiss();

            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void openCamara() {
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        activityResultLauncherForCamara.launch(intent);
    }


    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        activityResultLauncher.launch(intent);//打开相册
    }

//暂时想不到别的方法，只能写两个
    private ActivityResultLauncher<Intent> activityResultLauncherForCamara = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //打开相机代码
                        Log.e(TAG, "onActivityResult: "+imageUri );
                        try {
                            displayImage(imageUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //打开相机代码
                        if (SDK_INT >= 19) {
                            //4.4及以上系统使用这个方法处理图片
                            try {
                                Intent data = result.getData();
                                assert data != null;
                                handleImageOnKitKat(data);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Intent data = result.getData();
                                assert data != null;
                                handleImageBeforeKitKat(data);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
    );

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) throws FileNotFoundException {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id =docId.split(":")[1];//解析出数字格式的id
                String selection =MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的uri，则使用普通方式处理
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri，直接获取图片路径
            imagePath=uri.getPath();
        }
        imagePathLocal=imagePath;
        Log.e("SHARE", "onClick: "+"display？");

//        hiddenMethodAccess();
        getAccessTokenFromSP();
//        displayImage(uri);
//这里坑个人！！！就是安卓9还可以通过寻找image path就是图片路径的方式来取出图片，所以修改前我这里参数传的是image path
        //然后下面用的是decodeFile(imagepath)来取图片，但是在安卓10不行
        //所以我就改了，安卓十直接传uri,用parcelFileDescriptor,这个好像叫"Scoped Storage"
        //On Android 10, they introduced the concept of "Scoped Storage" and this way, you no longer can OPEN a image using its path.
    }

    private void getAccessTokenFromSP() {
        SharedPreferences pref = getSharedPreferences("AccessToken",MODE_PRIVATE);
        accessToken  = pref.getString("AccessToken",null);
        if(accessToken==null){
            getAccessToken();
        }else {
            OCR();
        }
    }


    public void getAccessToken(){
        new Thread(()->{
            //获得AccessToken
            accessToken = AuthService.getAuth();
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
            Log.e(TAG, "handleImageOnKitKat: "+accessToken );
        }).start();
    }

    public void OCR(){
        new Thread(()->{
            result =  AccurateBasic.accurateBasic(imagePathLocal,accessToken);
            Message msg = new Message();
            msg.what=1;
            handler.sendMessage(msg);
        }).start();

    }

    public void hiddenMethodAccess(){
        if (SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        try {
            Method forName = Class.class.getDeclaredMethod("forName", String.class);
            Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
            Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
            Object sVmRuntime = getRuntime.invoke(null);
            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
        } catch (Throwable e) {
            Log.e("[error]", "reflect bootstrap failed:", e);
        }

    }


    private void goToEvaluatePart(String text) {
        Intent intent = new Intent(PhotoLoadEvaluationActivity.this,ResultEvaluationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("text",result);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void handleImageBeforeKitKat(Intent data) throws FileNotFoundException {
        Uri uri=data.getData();
        displayImage(uri);
    }
    private void displayImage(Uri uri) throws FileNotFoundException {
        ParcelFileDescriptor parcelFileDescriptor=this.getContentResolver().openFileDescriptor(uri,"r");
        if(parcelFileDescriptor!=null){
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
//            add_pic.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"图片存储错误",Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri,String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri
                ,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //图片转字节
    private byte[]img(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static String parseJsonDataWithGson(String jsonData){
        Gson gson = new Gson();
        OCRResult ocrResult = gson.fromJson(jsonData,new TypeToken<OCRResult>() {
        }.getType());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ocrResult.getWords_result().size(); i++) {
            stringBuilder.append(ocrResult.getWords_result().get(i).toString());
        }
        return stringBuilder.toString();
    }

//    开一个线程来把拍完的照片存起来
//存储图片的过程
private class ImageSaver implements Runnable {
    private Image image;

    public ImageSaver(Image image) {
        this.image = image;
    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
        byte[] data = new byte[byteBuffer.remaining()];
        byteBuffer.get(data);
//            String path =getFilesDir();
        String path = Environment.getExternalStorageDirectory() + "/DCIM/CameraV2/";
        File file = new File(path);
        //判断当前的文件目录是否存在，如果不存在就创建这个文件目录
        if (!file.exists()) {
            file.mkdir();
        }
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(data, 0, data.length);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"你拒绝了相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}