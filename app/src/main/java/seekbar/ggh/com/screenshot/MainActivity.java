package seekbar.ggh.com.screenshot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wkp.runtimepermissions.callback.PermissionCallBack;
import com.wkp.runtimepermissions.util.RuntimePermissionUtil;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.shot_one) //绑定button 控件
    public Button shot_one;
    @BindView(R.id.shot_two) //绑定button 控件
    public Button shot_two;
    @BindView(R.id.shot_three) //绑定button 控件
    public Button shot_three;
    @BindView(R.id.tv) //绑定button 控件
    public TextView tv;
    @BindView(R.id.ll) //绑定button 控件
    public LinearLayout ll;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            applyPermission();
        }
    }

    @OnClick({R.id.shot_one, R.id.shot_two, R.id.shot_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shot_one:
                View dView = getWindow().getDecorView();
                dView.setDrawingCacheEnabled(true);
                dView.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
                if (bitmap != null) {
                    try {
                        // 获取内置SD卡路径
                        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                        // 图片文件路径
                        String filePath = sdCardPath + File.separator + "screenshot.png";
                        File file = new File(filePath);
                        FileOutputStream os = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                        os.flush();
                        os.close();
                        Log.d("a7888", "存储完成");
                        Toast.makeText(MainActivity.this, "截屏完成"+filePath, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
                break;
            case R.id.shot_two:

//                View dView2 = tv;
//                Bitmap bm = Bitmap.createBitmap(dView2.getWidth(), dView2.getHeight(), Bitmap.Config.ARGB_8888);
//                //使用Canvas，调用自定义view控件的onDraw方法，绘制图片
//                Canvas canvas = new Canvas(bm);
//                dView2.draw(canvas);
                Toast.makeText(MainActivity.this, "截屏完成2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shot_three:
                startActivity(new Intent(MainActivity.this,LongShotActivity.class));
//                getLinearLayoutBitmap(ll);
                Toast.makeText(MainActivity.this, "截屏完成3", Toast.LENGTH_SHORT).show();

                break;

        }
    }
    /**
     * 点击获取权限
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void applyPermission() {
        //权限检查，回调是权限申请结果
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.STORAGE, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    //直接做具有权限后的操作
                    Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();

                }else {
                    //显示权限不具备的界面
                    Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();

                }
            }
        });
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.CAMERA, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    //直接做具有权限后的操作
                    Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();

                }else {
                    //显示权限不具备的界面
                    Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    /**
     * 截取LinearLayout
     **/
    public Bitmap getLinearLayoutBitmap(LinearLayout linearLayout) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            h += linearLayout.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(linearLayout.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        linearLayout.draw(canvas);
        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "longscreenshot.png";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                Log.d("a7888", "存储完成");
                Toast.makeText(MainActivity.this, "截屏完成"+filePath, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }

    }
        return bitmap;

}}
