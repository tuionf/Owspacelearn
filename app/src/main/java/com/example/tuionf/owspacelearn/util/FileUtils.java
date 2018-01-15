package com.example.tuionf.owspacelearn.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public class FileUtils {

    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String ADPATH = SDPATH + "/Owspacelearn";

    public static void createSdDir() {
        File file = new File(ADPATH);
        if (!file.exists()){
            file.mkdir();
        }else {
            file.delete();
            file.mkdir();
        }
    }

    public static boolean isFileExist(String paramString) {
        if (paramString == null)
            return false;
        File localFile = new File(ADPATH + "/" + paramString);
        if (localFile.exists()) {
            return true;
        }
        return false;
    }

    public static File createFile(String fileName) throws IOException {
        File file = new File(ADPATH,fileName);
        file.createNewFile();
        return file;
    }

    public static List<String> getAllAD(){
        File file = new File(ADPATH);
        File[] fileList = file.listFiles();
        List<String> list = new ArrayList<>();
        if(null != fileList){
            for (File f:fileList) {
                list.add(f.getAbsolutePath());
            }
        }
        return list;
    }
}
