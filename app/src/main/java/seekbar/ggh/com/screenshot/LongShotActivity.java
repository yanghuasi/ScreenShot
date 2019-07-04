package seekbar.ggh.com.screenshot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class LongShotActivity extends Activity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long);
//
        listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }
            @Override
            public Object getItem(int position) {
                return null;
            }
            @Override
            public long getItemId(int position) {
                return 0;
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    Button button= (Button) LayoutInflater.from(getApplication()).inflate(R.layout.item,listView,false);
                    button.setText(""+position);
                    return button;
                }
                ((Button)convertView).setText(""+position);
                return convertView;
            }
        });
//
        File file=new File(Environment.getExternalStorageDirectory(),"aaa");
        file.mkdirs();
        for (File f:file.listFiles()){
            f.delete();
        }
        listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                start();
            }
        });
    }
    private void start(){
        final View view=findViewById(R.id.view);
        final ScrollableViewRECUtil scrollableViewRECUtil=new ScrollableViewRECUtil(view,ScrollableViewRECUtil.VERTICAL);
        scrollableViewRECUtil.start(new ScrollableViewRECUtil.OnRecFinishedListener() {
            @Override
            public void onRecFinish(Bitmap bitmap) {
                File f= Environment.getExternalStorageDirectory();
                System.out.print(f.getAbsoluteFile().toString());
                Toast.makeText(getApplicationContext(),f.getAbsolutePath(),Toast.LENGTH_LONG).show();
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG,60,new FileOutputStream(new File(f,"rec"+System.currentTimeMillis()+".jpg")));
//                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                    if (bitmap != null) {
//                        try {
//                            // 获取内置SD卡路径
//                            String sdCardPath = Environment.getExternalStorageDirectory().getPath();
//                            // 图片文件路径
//                            String filePath = sdCardPath + File.separator + "longscreenshot.png";
//                            File file = new File(filePath);
//                            FileOutputStream os = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
//                            os.flush();
//                            os.close();
//                            Log.d("a7888", "存储完成");
//                            Toast.makeText(LongShotActivity.this, "长截屏完成"+filePath, Toast.LENGTH_SHORT).show();
//                        } catch (Exception e) {
//                        }}
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        });
        // scrollableViewRECUtil
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollableViewRECUtil.stop();
            }
        },90*1000);
    }
}
