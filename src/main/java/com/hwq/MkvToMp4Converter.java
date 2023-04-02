package com.hwq;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MkvToMp4Converter {
    public static void main(String[] args) {

        String inDir="C://Users/18636/Videos/";  //需要转换的文件目录
        String outDir="C://Users/18636/Videos/covert/"; //转换后的文件输出目录
        if(null != args && args.length>0){
            inDir = args[0];  //输入的文件目录
            outDir=args[1];   // 输出的文件目录
        }
        //获取指定地址的文件
        //如果给定的地址是一个目录，则遍历这个目录，找出所有文件
        //调用转换方法 将mkv 转换 为 mp4
        //定义一个Map 存储要转换的文件 key 为输入文件  value为输出文件
        Map<String,String> fsMap=new HashMap<String,String>();

        File file = new File(inDir);

        System.out.println("开始获取要转换的文件...........");
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                String inputFile = f.getAbsolutePath();
                if(inputFile.endsWith(".mkv")){
                    String outFile=outDir+f.getName().replace(".mkv",".mp4");
                    fsMap.put(inputFile,outFile);
                }
            }
        }else{
            String inputFile = file.getName();
            if(inputFile.endsWith(".mkv")){
                String outFile=outDir+inputFile.replace(".mkv",".mp4");
                fsMap.put(inputFile,outFile);
            }
        }
        System.out.println("=========获取到要转换的文件列表============");
        System.out.println(fsMap.toString());
        System.out.println("=======================================");
        //遍历map 转换视频
        int i =1;
        for(Map.Entry entry:fsMap.entrySet()){
            System.out.printf("开始转换第 {%d}个文件",i);
             mvcovert(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    private static void mvcovert(String inputFilePath, String outputFilePath) {
        // 执行FFmpeg命令
        ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", inputFilePath, "-c:v", "libx264", "-preset", "ultrafast", "-crf", "18", "-c:a", "copy", outputFilePath);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
            System.out.println("转换完成！");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
