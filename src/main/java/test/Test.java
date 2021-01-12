package test;

import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author GuoYongMing
 * @Date 2021/1/11 13:46
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {

        String basePath="E://二维码图片/";
        File baseFile=new File(basePath);
        File[] files=baseFile.listFiles();
        if(files.length>0){
            for(File f:files){
                f.delete();
            }
        }
        baseFile.delete();

    }
}
