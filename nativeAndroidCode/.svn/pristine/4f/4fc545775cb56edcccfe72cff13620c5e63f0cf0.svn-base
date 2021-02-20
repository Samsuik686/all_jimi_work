package jimi.dev.youtHope;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;

public class MyService extends Service {

    @Override
    public void onCreate() {
        Log.i("long","onCreate - Thread ID = " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int getIntentData = intent.getIntExtra("data",-1);
        Log.e("long", "onStartCommand   " + intent+"  " + getIntentData);

//        SharedPreferences sp = getSharedPreferences("default",MODE_PRIVATE);
//        String username=sp.getString("username", "error");
//        SharedPreferences.Editor editor=sp.edit();
//        editor.putString("username", "root");
//        editor.apply();

//        intent.setClassName();
//        intent.putExtra("ad",12);

//        Uri uri = Uri.parse("https://www.baidu.com");
//        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
//        Intent intent1 = new Intent();
//        intent1.setAction(Intent.ACTION_VIEW);
//        startActivity(intent);


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("long", "onDestroy - Thread ID = " + Thread.currentThread().getId());
        super.onDestroy();
    }

    private void createFile (byte[] data) {
        File fd = new File("/sdcard/Download");
        if(!fd.exists()){
            fd.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(fd, "testNativeJs.txt"),false);
            out.write(data);
            out.close();
        } catch (Exception e) {

        }
    }


}
