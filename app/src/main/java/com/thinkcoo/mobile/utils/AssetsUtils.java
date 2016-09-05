package com.thinkcoo.mobile.utils;

import android.content.Context;

import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Robert.yao on 2016/3/24.
 */
@Singleton
public class AssetsUtils {

    public static final String DATA_DICTIONARY_DB_NAME = "external.db";
    public static final String ASSERTS_DB_FILE_PATH = "db/" + DATA_DICTIONARY_DB_NAME;
    public static final String DB_PATH = DbManagerProvider.DD_DB_FILE_DIR;
    public static final int BUFFER_SIZE = 1024*4;
    private static final String TAG = "AssetsUtils" ;
    Context mContext;

    @Inject
    public AssetsUtils(Context context) {
        this.mContext = context;
    }

    public boolean copyDataDictionaryDbFile() {
        String scrPath = ASSERTS_DB_FILE_PATH;
        String targetPath = DB_PATH + DATA_DICTIONARY_DB_NAME;
        try {
            File dbFile = new File(targetPath);
            if (dbFile.exists()) {
                if (!isDBFileChanged(dbFile)) return true;
                if (dbFile.delete()) return copyAssetsFile(scrPath, targetPath);
            } else {
                if (!checkDBDir()) throw new IllegalArgumentException("illegal db path ");
                return copyAssetsFile(scrPath, targetPath);
            }
        } catch (Exception e) {
            throw new RuntimeException("数据库copy失败");
        }
        return false;
    }

    private boolean isDBFileChanged(File dbFile) {
        int assetSize = getAssetsFileSize(ASSERTS_DB_FILE_PATH);
        int dataSzie = (int) dbFile.length();
        if (dataSzie < assetSize) {
            return true;
        }
        return false;
    }

    private boolean checkDBDir() {
        File dbDir = new File(DB_PATH);
        if (dbDir.exists()) {
           return true;
        }
        return  dbDir.mkdirs();
    }

    public boolean copyAssetsFile(String fromFilePath, String toFilePath) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = mContext.getAssets().open(fromFilePath);
            os = new FileOutputStream(toFilePath);
            byte buffer[] = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            ThinkcooLog.e(TAG,"data dictionary db copy finish ");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            IOUtils.safeIoClose(os);
            IOUtils.safeIoClose(is);
        }
    }

    public int getAssetsFileSize(String assetDir) {
        try {
            InputStream is = mContext.getAssets().open(assetDir);
            return is.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getJson(Context context, String path) {
        if (context == null || path == null) {
            throw new NullPointerException("context or path cannot be null");
        }
        InputStream is = null;
        try {
            is = context.getAssets().open(path);
            return FileUtils.readTextFromStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.safeIoClose(is);
        }
        return null;
    }


}