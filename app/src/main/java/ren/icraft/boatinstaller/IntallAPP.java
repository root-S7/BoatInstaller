package ren.icraft.boatinstaller;

import static ren.icraft.boatinstaller.MainActivity.context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.io.File;

public class IntallAPP {
    public static void install(String path) {
        File apk = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            System.out.println("执行了！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //注意第二个参数，要保持和manifest中android:authorities的值相同
            Uri uri = FileProvider.getUriForFile(context, "net.kdt.pojavlaunch.debug" + ".fileProvider", apk);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
