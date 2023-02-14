package ren.icraft.boatinstaller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.*;
import android.provider.Settings;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    static Context context;
    private boolean isInstallPermission;
    GetPropertiesValue value;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        value = new GetPropertiesValue();
        new OnlineAnnouncement(findViewById(R.id.announcement)).run();
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    //删除按钮事件
    public void delete(View v) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){//安卓 >= 11
            if(Environment.isExternalStorageManager()){
                setContentView(R.layout.waiting_activity);
                new Thread(()->{
                    new DeleteResources().DeleteFolder(Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory"));
                    MainActivity.this.runOnUiThread(() -> setContentView(R.layout.activity_main));
                }).start();
            }else{
                new AlertDialog.Builder(this).setMessage("请授予读写权限，否则应用无法正常运行").setPositiveButton("给予权限", (dialog1, which) -> requestPermission()).setNegativeButton("取消(不给予)", null).create().show();
            }
        }else{//安卓<11
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                setContentView(R.layout.waiting_activity);
                new Thread(()->{
                    new DeleteResources().DeleteFolder(Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory"));
                    MainActivity.this.runOnUiThread(() -> setContentView(R.layout.activity_main));
                }).start();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        }
    }
    //安装按钮事件
    public void install(View v){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){//安卓 >= 11
            if(Environment.isExternalStorageManager()){
                setContentView(R.layout.waiting_activity);
                new Thread(()->{
                    new DeleteResources().DeleteFolder(Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory"));
                    ResourceInstallation.copyFilesFromAssets(context,value.getPropertiesReaderNoSlash(context,"putDirectory"),Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory"));
                    MainActivity.this.runOnUiThread(() -> setContentView(R.layout.activity_main));
                    IntallAPP.install(Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory") + "/澪.apk");
                }).start();
            }else{
                new AlertDialog.Builder(this).setMessage("请授予读写权限，否则应用无法正常运行").setPositiveButton("给予权限", (dialog1, which) -> requestPermission()).setNegativeButton("取消(不给予)", null).create().show();
            }
        }else{//安卓<11
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                setContentView(R.layout.waiting_activity);
                new Thread(()->{
                    new DeleteResources().DeleteFolder(Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory"));
                    ResourceInstallation.copyFilesFromAssets(context,value.getPropertiesReaderNoSlash(context,"putDirectory"),Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory"));
                    MainActivity.this.runOnUiThread(() -> setContentView(R.layout.activity_main));
                    IntallAPP.install(Environment.getExternalStorageDirectory() + value.getPropertiesReader(context,"putDirectory") + "/澪.apk");
                }).start();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void requestPermission() {
        int permission_read = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission_read != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1000);
        }
    }
}