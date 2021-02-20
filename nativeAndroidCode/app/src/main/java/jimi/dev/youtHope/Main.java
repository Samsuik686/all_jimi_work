package jimi.dev.youtHope;


import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        FileOutputStream out = null;
        try {
            String data = "dsa";
            out = new FileOutputStream(new File("/sdcard/Doownload/testNativeJs.txt"), true);
            byte[] bytes = new byte[data.length() / 2];
            out.write(data.getBytes("UTF-8"));              //将数据写入到文件中
            out.close();

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void test(Context context) {

    }
}
