package com.wsj.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class FileUtils {

    /**
     * 手动生成csv文件
     * 
     * @param exportData 导出数据
     * @param title 文件标题
     * @param outPutPath 文件存放路径
     * @param filename 文件名称
     * @return
     */
    public static File createCSVFile(List<Map<String, String>> exportData, LinkedHashMap<String, String> title,
                                     String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            csvFile = new File(outPutPath + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // UTF-8使正确读取分隔符"\u0001"
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"),
                                                     1024);
            // 解决utf-8编码BOM问题
            // csvFileOutputStream.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
            // 写入文件头部
            for (Iterator<Entry<String, String>> propIt = title.entrySet().iterator(); propIt.hasNext();) {
                Entry<String, String> propEntry = (Entry<String, String>) propIt.next();
                String val = StringUtils.isNotBlank(propEntry.getValue()) ? propEntry.getValue() : "";
                csvFileOutputStream.write("\"" + val + "\"");
                if (propIt.hasNext()) {
                    csvFileOutputStream.write("\u0001");
                }
            }
            csvFileOutputStream.newLine();

            // 写入文件内容
            for (Iterator<Map<String, String>> iterator = exportData.iterator(); iterator.hasNext();) {
                LinkedHashMap<String, String> contentMap = (LinkedHashMap<String, String>) iterator.next();

                for (Iterator<Entry<String, String>> propIt = contentMap.entrySet().iterator(); propIt.hasNext();) {
                    Entry<String, String> propEntry = (Entry<String, String>) propIt.next();
                    String val = StringUtils.isNotBlank(propEntry.getValue()) ? propEntry.getValue() : "";
                    csvFileOutputStream.write("\"" + val + "\"");
                    if (propIt.hasNext()) {
                        csvFileOutputStream.write("\u0001");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

}