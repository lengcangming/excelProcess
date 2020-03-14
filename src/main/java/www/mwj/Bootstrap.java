package www.mwj;

import www.mwj.action.ProcessExcel;
import www.mwj.action.ReadExcel;
import www.mwj.action.WriteExcel;
import www.mwj.pojo.AliInfo;
import www.mwj.pojo.HuaweiInfo;

import java.io.File;
import java.util.List;

/**
 * @author MWJ 2020/3/11
 */
public class Bootstrap {
    public static void main(String[] args) {
        File feedbackFile=new File("D:/excel/1.xlsx");
        File updateFile =new File("D:/excel/2.xlsx");
        ReadExcel readExcel=new ReadExcel(feedbackFile,updateFile);
        List<AliInfo> aliInfos = readExcel.readAliExcel();
        List<HuaweiInfo> huaweiInfos = readExcel.readHuaweiExcel();
        ProcessExcel processExcel=new ProcessExcel(aliInfos,huaweiInfos);
        processExcel.process();
        WriteExcel writeExcel=new WriteExcel(aliInfos,huaweiInfos);
        writeExcel.writeAliExcel("D:/excel/3.xlsx");
        writeExcel.writeHuaweiExcel("D:/excel/4.xlsx");
    }
}
