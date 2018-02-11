package bitcoin.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FileUtils {
    public static final String FILEPATH = "info";

    // json写入文件
    public synchronized static void write2File(Object json, String fileName) {
        BufferedWriter writer = null;
        File filePath = new File(FILEPATH);
        JSONObject eJSON = null;

        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdirs();
        }

        FileWriter fw = null;
        try {
            File f=new File(FILEPATH + File.separator + fileName + ".xml");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(json);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 读文件到json
    public static JSONObject read2JSON(String fileName) {
        File file = new File(FILEPATH + File.separator + fileName + ".xml");
        if (!file.exists()) {
            return null;
        }

        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (JSONObject) JSON.parse(laststr);
    }

}
