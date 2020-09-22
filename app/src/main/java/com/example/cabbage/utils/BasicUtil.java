package com.example.cabbage.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cabbage.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

public class BasicUtil {
    //查看大图
    public static void watchLargePhoto(Context context, Uri imageUri) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View imgLargeView = layoutInflater.inflate(R.layout.dialog_watch_big_photo, null);
        final AlertDialog alertDialogShowLargeImage = new AlertDialog.Builder(context).setTitle("点击可关闭").create();
        //获取ImageView
        ImageView imvLargePhoto = (ImageView) imgLargeView.findViewById(R.id.imv_large_photo);
        //设置图片到ImageView
        imvLargePhoto.setImageURI(imageUri);
        //定义dialog
        alertDialogShowLargeImage.setView(imgLargeView);
        alertDialogShowLargeImage.show();
        //点击大图关闭dialog
        imgLargeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShowLargeImage.cancel();
            }
        });
    }

    //查看网络大图
    public static void watchOnlineLargePhoto(Context context, Uri imageUri, String title) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LayoutInflater layoutInflater_2 = LayoutInflater.from(context);

        View imgLargeView = layoutInflater.inflate(R.layout.dialog_watch_online_big_photo, null);
        View titleView = layoutInflater_2.inflate(R.layout.dialog_custom_title, null);

        TextView dialog_title = titleView.findViewById(R.id.dialog_picture_title);
        dialog_title.setText(title);
        final AlertDialog alertDialogShowLargeImage = new AlertDialog.Builder(context, R.style.Dialog_Fullscreen)
                .setCustomTitle(titleView)
                .create();
        Objects.requireNonNull(alertDialogShowLargeImage.getWindow()).setWindowAnimations(R.style.dialogWindowAnim);
        //获取ImageView
        final PhotoDraweeView imvLargePhoto = imgLargeView.findViewById(R.id.imv_online_large_photo);
        alertDialogShowLargeImage.setView(imgLargeView);
        alertDialogShowLargeImage.show();
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(imageUri);//设置图片url
        controller.setOldController(imvLargePhoto.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                imvLargePhoto.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        imvLargePhoto.setController(controller.build());

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShowLargeImage.cancel();
            }
        });
        imvLargePhoto.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                alertDialogShowLargeImage.cancel();
            }
        });
        alertDialogShowLargeImage.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    //选择日期
    public static void showDatePickerDialog(Context context, final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String zeroMonth = "";
                String zeroDay = "";
                if (month < 10) zeroMonth = "0"; //当月份小于10时，需要在月份前加入0，需要符合yyyy-mm-dd当格式
                if (dayOfMonth < 10) zeroDay = "0"; //同上
                textView.setText(year + "-" + zeroMonth + (month + 1) + "-" + zeroDay + dayOfMonth); //yyyy-mm-dd
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //计算平均数
    public static String getAverage(ArrayList<String> arrayList) {
        //解析两位小数
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        float sum = 0;
        for (String s :
                arrayList) {
            sum += Float.parseFloat(s);
        }
        float avg = sum / arrayList.size();
        return decimalFormat.format(avg);
    }

    //计算生育日数
    public static String getGrowingDays(Context context, String sowingDateStr, String matureDateStr) throws Exception {
        //解析日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //播种期
        Date sowingDate = simpleDateFormat.parse(sowingDateStr);
        //成熟期
        Date matureDate = simpleDateFormat.parse(matureDateStr);
        Integer growingDays = ((int) ((matureDate.getTime() - sowingDate.getTime()) / (3600 * 1000 * 24)));
        if (growingDays < 0) {
            throw new Exception("输入的成熟期早于播种期！");
        }
        return growingDays.toString();
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * 将json对象转换成Map
     *
     * @param jsonObject json对象
     * @return Map对象
     */
    public static Map<String, String> JsonToMap(JSONObject jsonObject) {
        Map<String, String> result = new HashMap<>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = iterator.next();
            value = jsonObject.optString(key);
            result.put(key, value);
        }
        return result;
    }

    /**
     * 将json转换成Javabean
     *
     * @param javabean javaBean
     * @param data     json数据
     */
    public static Object toJavaBean(Object javabean, JSONObject data) {
        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName(); //setName   setPassword
                    field = field.substring(field.indexOf("set") + 3);//Name  Password
                    field = field.toLowerCase().charAt(0) + field.substring(1);//name  password
                    method.invoke(javabean, new Object[]
                            {
                                    data.optString(field)
                            });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return javabean;
    }

}
