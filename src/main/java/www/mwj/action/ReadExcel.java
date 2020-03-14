package www.mwj.action;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import www.mwj.pojo.AliInfo;
import www.mwj.pojo.HuaweiInfo;
import www.mwj.util.StrUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MWJ 2020/3/8
 */
public class ReadExcel {

    private File feedbackFile;
    private File updateFile;

    public ReadExcel(File feedbackFile, File updateFile) {
        this.feedbackFile = feedbackFile;
        this.updateFile = updateFile;
    }

    // File file2 =new File("D:/excel/2.xlsx");
    //        List<HuaweiInfo> huaweiExcelList=obj.readHuaweiExcel(file2);
    public List<HuaweiInfo> readHuaweiExcel() {
        try {
            InputStream is = new FileInputStream(updateFile.getAbsolutePath());
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            // Read the Sheet
            List<HuaweiInfo> outerList = new ArrayList<>();
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                if (numSheet == 2 || numSheet == 3) {
                    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                    if (xssfSheet == null) {
                        continue;
                    }
                    HuaweiInfo huaweiInfo;
                    // Read the Row
                    for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
                        XSSFRow xssfRow = xssfSheet.getRow(i);
                        if (xssfRow != null) {
                            huaweiInfo = new HuaweiInfo();
                            setHuaweiCellToPojo(xssfRow, huaweiInfo);
                            outerList.add(huaweiInfo);
                        }
                    }
                }
            }
            return outerList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHuaweiCellToPojo(XSSFRow xssfRow, HuaweiInfo huaweiInfo) {
        XSSFCell batch = xssfRow.getCell(0);
        XSSFCell PONo = xssfRow.getCell(1);
        XSSFCell projectNo = xssfRow.getCell(2);
        XSSFCell POIssueDate = xssfRow.getCell(3);
        XSSFCell huaweiContractNumber = xssfRow.getCell(4);
        XSSFCell itemName = xssfRow.getCell(5);
        XSSFCell model = xssfRow.getCell(6);
        XSSFCell num = xssfRow.getCell(7);
        XSSFCell shippingAddress = xssfRow.getCell(8);
        XSSFCell computerLab = xssfRow.getCell(9);
        XSSFCell status = xssfRow.getCell(10);
        XSSFCell expectedDeliveryDate = xssfRow.getCell(11);
        XSSFCell expectedCompletionDateOfGoodsPreparation = xssfRow.getCell(12);
        XSSFCell estimateArrivalDate = xssfRow.getCell(13);
        XSSFCell businessControlled = xssfRow.getCell(14);
        XSSFCell scheduledArrivalDate = xssfRow.getCell(15);
        XSSFCell remark = xssfRow.getCell(16);
        huaweiInfo.setBatch(StrUtil.getValue(batch));
        huaweiInfo.setPONo(StrUtil.getValue(PONo));
        huaweiInfo.setProjectNo(StrUtil.getValue(projectNo));
        huaweiInfo.setPOIssueDate(StrUtil.getValue(POIssueDate));
        huaweiInfo.setHuaweiContractNumber(StrUtil.getValue(huaweiContractNumber));
        huaweiInfo.setItemName(StrUtil.getValue(itemName));
        huaweiInfo.setModel(StrUtil.getValue(model));
        huaweiInfo.setNum(StrUtil.getValue(num));
        huaweiInfo.setShippingAddress(StrUtil.getValue(shippingAddress));
        huaweiInfo.setComputerLab(StrUtil.getValue(computerLab));
        huaweiInfo.setStatus(StrUtil.getValue(status));
        huaweiInfo.setExpectedDeliveryDate(StrUtil.getValue(expectedDeliveryDate));
        huaweiInfo.setExpectedCompletionDateOfGoodsPreparation(StrUtil.getValue(expectedCompletionDateOfGoodsPreparation));
        huaweiInfo.setEstimateArrivalDate(StrUtil.getValue(estimateArrivalDate));
        huaweiInfo.setBusinessControlled(StrUtil.getValue(businessControlled));
        huaweiInfo.setScheduledArrivalDate(StrUtil.getValue(scheduledArrivalDate));
        huaweiInfo.setRemark(StrUtil.getValue(remark));
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List<AliInfo> readAliExcel() {
        try {
            InputStream is = new FileInputStream(feedbackFile.getAbsolutePath());
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            // Read the Sheet
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                List<AliInfo> outerList = new ArrayList<>();
                AliInfo aliInfo;
                // Read the Row
                for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
                    XSSFRow xssfRow = xssfSheet.getRow(i);
                    if (xssfRow != null) {
                        aliInfo = new AliInfo();
                        setCellToPojo(xssfRow, aliInfo);
                        outerList.add(aliInfo);
                    }
                }
                return outerList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setCellToPojo(XSSFRow xssfRow, AliInfo aliInfo) {
        XSSFCell id = xssfRow.getCell(0);
        XSSFCell country = xssfRow.getCell(1);
        XSSFCell city = xssfRow.getCell(2);
        XSSFCell computerLab = xssfRow.getCell(3);
        XSSFCell projectName = xssfRow.getCell(4);
        XSSFCell projectNo = xssfRow.getCell(5);
        XSSFCell goodsAttribute = xssfRow.getCell(6);
        XSSFCell brand = xssfRow.getCell(7);
        XSSFCell model = xssfRow.getCell(8);
        XSSFCell status = xssfRow.getCell(9);
        XSSFCell num = xssfRow.getCell(10);
        XSSFCell expectedDeliveryDate = xssfRow.getCell(11);
        XSSFCell extensionDate = xssfRow.getCell(12);
        XSSFCell quantityDeliveredOnSchedule = xssfRow.getCell(13);
        XSSFCell businessCategory = xssfRow.getCell(14);
        XSSFCell PONo = xssfRow.getCell(15);
        XSSFCell POIssueDate = xssfRow.getCell(16);
        XSSFCell status2 = xssfRow.getCell(17);
        XSSFCell expectedDeliveryDateOfThisWeek = xssfRow.getCell(18);
        XSSFCell plannedArrivalDateOfThisWeek = xssfRow.getCell(19);
        XSSFCell plannedArrivalDateOfLastWeek = xssfRow.getCell(20);
        XSSFCell delayReason = xssfRow.getCell(21);
        XSSFCell remark = xssfRow.getCell(22);
        aliInfo.setId(StrUtil.getValue(id));
        aliInfo.setCountry(StrUtil.getValue(country));
        aliInfo.setCity(StrUtil.getValue(city));
        aliInfo.setComputerLab(StrUtil.getValue(computerLab));
        aliInfo.setProjectName(StrUtil.getValue(projectName));
        aliInfo.setProjectNo(StrUtil.getValue(projectNo));
        aliInfo.setGoodsAttribute(StrUtil.getValue(goodsAttribute));
        aliInfo.setBrand(StrUtil.getValue(brand));
        aliInfo.setModel(StrUtil.getValue(model));
        aliInfo.setStatus(StrUtil.getValue(status));
        aliInfo.setNum(StrUtil.getValue(num));
        aliInfo.setExpectedDeliveryDate(StrUtil.getValue(expectedDeliveryDate));
        aliInfo.setExtensionDate(StrUtil.getValue(extensionDate));
        aliInfo.setQuantityDeliveredOnSchedule(StrUtil.getValue(quantityDeliveredOnSchedule));
        aliInfo.setBusinessCategory(StrUtil.getValue(businessCategory));
        aliInfo.setPONo(StrUtil.getValue(PONo));
        aliInfo.setPOIssueDate(StrUtil.getValue(POIssueDate));
        aliInfo.setStatus2(StrUtil.getValue(status2));
        aliInfo.setExpectedDeliveryDateOfThisWeek(StrUtil.getValue(expectedDeliveryDateOfThisWeek));
        aliInfo.setPlannedArrivalDateOfThisWeek(StrUtil.getValue(plannedArrivalDateOfThisWeek));
        aliInfo.setPlannedArrivalDateOfLastWeek(StrUtil.getValue(plannedArrivalDateOfLastWeek));
        aliInfo.setDelayReason(StrUtil.getValue(delayReason));
        aliInfo.setRemark(StrUtil.getValue(remark));
    }

}
