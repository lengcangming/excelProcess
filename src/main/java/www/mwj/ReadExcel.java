package www.mwj;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author MWJ 2020/3/8
 */
public class ReadExcel {

    File feedbackFile;
    File updateFile;

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
                    HuaweiInfo huaweiInfo = null;
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
        huaweiInfo.setBatch(getValue(batch));
        huaweiInfo.setPONo(getValue(PONo));
        huaweiInfo.setProjectNo(getValue(projectNo));
        huaweiInfo.setPOIssueDate(getValue(POIssueDate));
        huaweiInfo.setHuaweiContractNumber(getValue(huaweiContractNumber));
        huaweiInfo.setItemName(getValue(itemName));
        huaweiInfo.setModel(getValue(model));
        huaweiInfo.setNum(getValue(num));
        huaweiInfo.setShippingAddress(getValue(shippingAddress));
        huaweiInfo.setComputerLab(getValue(computerLab));
        huaweiInfo.setStatus(getValue(status));
        huaweiInfo.setExpectedDeliveryDate(getValue(expectedDeliveryDate));
        huaweiInfo.setExpectedCompletionDateOfGoodsPreparation(getValue(expectedCompletionDateOfGoodsPreparation));
        huaweiInfo.setEstimateArrivalDate(getValue(estimateArrivalDate));
        huaweiInfo.setBusinessControlled(getValue(businessControlled));
        huaweiInfo.setScheduledArrivalDate(getValue(scheduledArrivalDate));
        huaweiInfo.setRemark(getValue(remark));
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List<AliInfo> readAliExcel() {
        try {
            InputStream is = new FileInputStream(feedbackFile.getAbsolutePath());
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            List list = new ArrayList();
            // Read the Sheet
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                List<AliInfo> outerList = new ArrayList();
                AliInfo aliInfo = null;
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
        aliInfo.setId(getValue(id));
        aliInfo.setCountry(getValue(country));
        aliInfo.setCity(getValue(city));
        aliInfo.setComputerLab(getValue(computerLab));
        aliInfo.setProjectName(getValue(projectName));
        aliInfo.setProjectNo(getValue(projectNo));
        aliInfo.setGoodsAttribute(getValue(goodsAttribute));
        aliInfo.setBrand(getValue(brand));
        aliInfo.setModel(getValue(model));
        aliInfo.setStatus(getValue(status));
        aliInfo.setNum(getValue(num));
        aliInfo.setExpectedDeliveryDate(getValue(expectedDeliveryDate));
        aliInfo.setExtensionDate(getValue(extensionDate));
        aliInfo.setQuantityDeliveredOnSchedule(getValue(quantityDeliveredOnSchedule));
        aliInfo.setBusinessCategory(getValue(businessCategory));
        aliInfo.setPONo(getValue(PONo));
        aliInfo.setPOIssueDate(getValue(POIssueDate));
        aliInfo.setStatus2(getValue(status2));
        aliInfo.setExpectedDeliveryDateOfThisWeek(getValue(expectedDeliveryDateOfThisWeek));
        aliInfo.setPlannedArrivalDateOfThisWeek(getValue(plannedArrivalDateOfThisWeek));
        aliInfo.setPlannedArrivalDateOfLastWeek(getValue(plannedArrivalDateOfLastWeek));
        aliInfo.setDelayReason(getValue(delayReason));
        aliInfo.setRemark(getValue(remark));
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfCell) {
        if (isCellEmpty(xssfCell)) {
            return "";
        }
        if (xssfCell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(xssfCell)) {
                Date d = xssfCell.getDateCellValue();
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                return String.valueOf(formater.format(d));
            } else {
                xssfCell.setCellType(CellType.STRING);
                return String.valueOf(xssfCell.getStringCellValue());
            }
        } else if (xssfCell.getCellType() == CellType.FORMULA) {
            return "";
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

    private static boolean isCellEmpty(final XSSFCell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }
        return cell.getCellType() == CellType.STRING && cell.getStringCellValue().isEmpty();
    }
}
