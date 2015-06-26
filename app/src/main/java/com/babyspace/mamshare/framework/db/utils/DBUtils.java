package com.babyspace.mamshare.framework.db.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class DBUtils {
    public static void checkDatabase(Context context, String DB_NAME) {
        File db = context.getDatabasePath(DB_NAME);
        File parentFile = db.getParentFile();
        if (!parentFile.exists())
            parentFile.mkdir();

        FileOutputStream fos = null;
        if (!db.exists()) {
            try {
                db.createNewFile();
                fos = new FileOutputStream(db);
                AssetManager am = context.getAssets();
                if (am != null) {
                    InputStream is = am.open(DB_NAME);
                    if (is != null) {
                        byte[] buffer = new byte[8192];
                        while (is.read(buffer) > 0)
                            fos.write(buffer);
                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
