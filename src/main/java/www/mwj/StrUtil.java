package www.mwj;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StrUtil {
    private static Logger logger = Logger.getLogger(StrUtil.class);

    /**/// /
    // / 转全角的函数(SBC case)
    // /
    // /任意字符串
    // /全角字符串
    // /
    // /全角空格为12288，半角空格为32
    // /其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
    // /
    public static String ToSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**/// /
    // / 转半角的函数(DBC case)
    // /
    // /任意字符串
    // /半角字符串
    // /
    // /全角空格为12288，半角空格为32
    // /其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
    // /
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 对象为null;"";"null";"   "都返回true
     *
     * @param str
     * @return
     */
    public static boolean isBlank(Object str) {
        String tem = str + "";
        if ("".equals(tem.trim()) || "null".equals(tem.trim())) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isNotBlank(Object str) {
        String tem = (str + "").trim();
        if ("".equals(tem) || "null".equals(tem) || "undefined".equals(tem)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * null转换为空字符串
     *
     * @param str
     * @return
     */
    public static String nullToKStr(Object str) {
        String tem = (str + "").trim();
        if ("".equals(tem) || "null".equals(tem) || "undefined".equals(tem)) {
            return "";
        } else {
            return tem;
        }

    }

    /**
     * 字符串排序
     *
     * @param oldstr
     * @return
     */
    public static String arrSort(String oldstr) {
        String[] a = oldstr.split(",");
        Arrays.sort(a);
        String tmp = "";
        for (String b : a) {
            tmp += b + ",";
        }
        return tmp.substring(0, tmp.length() - 1);
    }

    /**
     * 生成UUID
     *
     * @return
     */
    public static String geneUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    /**
     * 处理行政区划，将130000从右边数第一个非0数字到开头的全部串截取并返回 //13 01 10 102 201 //省 市 县 乡 村
     *
     * @param region
     * @return
     * @author 王春林
     */
    public static String dealRegion(String region) {
        String regionStr = "";
        if (StrUtil.isNotBlank(region)) {
            regionStr = new StringBuffer(region).reverse().toString();
            regionStr = Long.parseLong(regionStr) + "";
            regionStr = new StringBuffer(regionStr.trim()).reverse().toString();
        }
        regionStr = regionStr.trim();
        if (regionStr.length() <= 6) {
            if (regionStr.length() % 2 == 1) {
                regionStr += "0";
            }
        } else if (regionStr.length() <= 9) {
            if (regionStr.length() % 3 == 1) {
                // 七位
                regionStr += "00";
            } else if (regionStr.length() % 3 == 2) {
                // 八位
                regionStr += "0";
            }
        } else if (regionStr.length() <= 12) {
            if (regionStr.length() % 3 == 1) {
                // 十位
                regionStr += "00";
            } else if (regionStr.length() % 3 == 2) {
                // 十一位
                regionStr += "0";
            }
        }
        return regionStr;
    }

    /**
     * querySSXByRegionLength(这里用一句话描述这个方法的作用)
     *
     * @param @param  region
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: querySSXByRegionLength
     * @Description: 根据行政区划编码的长度判断省市县
     * @Author:Wang Zhefeng
     */
    public static String querySSXByRegionLength(String regionStr) {
        String ret = "";
        switch (StrUtil.dealRegion(regionStr).length()) {
            case 2:
                ret = "省";
                break;
            case 4:
                ret = "市";
                break;
            case 6:
                ret = "县";
                break;
            case 9:
                ret = "乡";
                break;
            case 12:
                ret = "村";
                break;
            default:
                ret = "其他";
        }
        return ret;
    }

    /*
     * public static String dealRegion(String region) { String regionStr = "";
     * if(StrUtil.isNotBlank(region)) { regionStr = new
     * StringBuffer(region).reverse().toString(); regionStr =
     * Long.parseLong(regionStr) + ""; regionStr = new
     * StringBuffer(regionStr.trim()).reverse().toString(); } regionStr =
     * regionStr.trim(); if(regionStr.length() % 2 == 1) { regionStr += "0"; }
     * return regionStr; }
     */

    /**
     * 处理行政区划，通过字符串截取
     *
     * @param region
     * @return
     */
    public static String SubRegion(String region) {
        String regionStr = "";
        if (StrUtil.isNotBlank(region)) {
            if (region.length() == 6) {
                regionStr = new StringBuffer(region).reverse().toString();
                regionStr = Long.parseLong(regionStr) + "";
                regionStr = new StringBuffer(regionStr.trim()).reverse()
                        .toString();
            } else if (region.length() == 12) {
                String shi = region.substring(2, 4);
                String xian = region.substring(4, 6);
                String zhen = region.substring(6, 9);
                String cun = region.substring(9, 12);
                if (!cun.equals("000")) {
                    regionStr = region;
                } else if (!zhen.equals("000")) {
                    regionStr = region.substring(0, 9);
                } else if (!xian.equals("00")) {
                    regionStr = region.substring(0, 6);
                } else if (!shi.equals("00")) {
                    regionStr = region.substring(0, 4);
                }
            }
        }
        return regionStr;
    }


    /**
     * genJiJson(生成季度的JSON)
     *
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: genJiJson
     * @Description: TODO
     */
    public static String genJiJson() {
        StringBuffer sb = new StringBuffer("[");
        for (int x = 1; x <= 4; x++) {
            sb.append("{ji:'" + x + "'},");
        }
        String str = sb.substring(0, sb.length() - 1);
        str += "]";
        return str;
    }

    /**
     * genYueJson(生成当yue的JSON)
     *
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: genYueJson
     * @Description: TODO
     */
    public static String genYueJson() {
        StringBuffer sb = new StringBuffer("[");
        for (int x = 1; x <= 12; x++) {
            sb.append("{yue:'" + x + "'},");
        }
        String str = sb.substring(0, sb.length() - 1);
        str += "]";
        return str;
    }

    //导入时 判断数值
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    //导入时 判断数值与小数点
    public static boolean isNumericfloat(String str) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }

    //导入时 判断空行
    public static boolean konghang(String[] content) {
        int flag = 0;
        for (int t = 0; t < content.length; t++) {
            if (!"".equals(content[t]) && content[t] != null) {
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            return true;
        }
        return false;
    }

    //导入时 判断excel里数据是否重复  zhanglei
    public static String excelrepeat(String content, int j, List<Map<String, Object>> list, String name) {
        String login = "";
        int flag2 = 0;
        if (list.size() > 0) {
            for (int k = 0; k < j - 1; k++) {
                if (content.trim().equals(list.get(k).get(name).toString())) {
                    flag2 = 1;
                    break;
                }
            }
        }
        if (flag2 == 1) {
            login = "error";
        }
        return login;
    }

    //导入时 判断基地与区划在excel里是否重复
    public static String exrebaseregion(String base, String code, int j, List<Map<String, Object>> list, String basename, String codename) {
        String flag = "norepeat";
        if (list.size() > 0) {
            for (int k = 0; k < j - 1; k++) {
                if (base.trim().equals(list.get(k).get(basename).toString()) && code.trim().equals(list.get(k).get(codename).toString())) {
                    flag = String.valueOf(k);
                    break;
                }
            }
        }
        return flag;
    }


    //name--对应的表名
    //idList--向表中插入的对应的字段名
    //lis--要插入的数据Map内容顺序上无要求

    public static String spStr(String name, List<String> idlist, List<Map<String, String>> lis) {
        StringBuffer sbf = new StringBuffer();
        sbf.append("INSERT into ");
        sbf.append(name);
        sbf.append(" (");
        for (int i = 0; i < idlist.size(); i++) {
            sbf.append(idlist.get(i) + ",");
        }
        sbf.deleteCharAt(sbf.length() - 1);
        sbf.append(") VALUES ");

        for (int i = 0; i < lis.size(); i++) {
            sbf.append(" ('");
            for (int j = 0; j < idlist.size(); j++) {
                sbf.append(lis.get(i).get(idlist.get(j)));
                sbf.append("','");
            }
            sbf.deleteCharAt(sbf.length() - 1);
            sbf.deleteCharAt(sbf.length() - 1);
            sbf.append("),");
        }
        sbf.deleteCharAt(sbf.length() - 1);
        sbf.append(";");
        return sbf.toString();
    }
    //name--对应的表名
    //idList--向表中插入的对应的字段名
    //lis--要插入的数据Map内容顺序上无要求

    public static String spStrCheck(String name, List<String> idlist, List<Map<String, Object>> lis) {
        StringBuffer sbf = new StringBuffer();
        sbf.append("INSERT into ");
        sbf.append(name);
        sbf.append(" (");
        for (int i = 0; i < idlist.size(); i++) {
            sbf.append(idlist.get(i) + ",");
        }
        sbf.deleteCharAt(sbf.length() - 1);
        sbf.append(") VALUES ");

        for (int i = 0; i < lis.size(); i++) {
            sbf.append(" ('");
            for (int j = 0; j < idlist.size(); j++) {
                if (null != lis.get(i).get(idlist.get(j))) {
                    sbf.append(lis.get(i).get(idlist.get(j)).toString());
                } else {
                    if ("uplimes".equals(idlist.get(j))) {
                        sbf.append("0");
                    }
                    if ("lowerlimes".equals(idlist.get(j))) {
                        sbf.append("0");
                    }
                    if ("checkvalue".equals(idlist.get(j))) {
                        sbf.append("0");
                    }
                }
                sbf.append("','");
            }
            sbf.deleteCharAt(sbf.length() - 1);
            sbf.deleteCharAt(sbf.length() - 1);
            sbf.append("),");
        }
        sbf.deleteCharAt(sbf.length() - 1);
        sbf.append(";");
        return sbf.toString();
    }

    //名称
    //读取excel表适用于单sheet数据低版本excel
    //data--读入的excel文件路径
    //lis--自定义的list保存值对应于页面相应字段需要顺序加入
    //index--数据标志位对应于业务数据起始行,第一行下标为0
    public static List<Map<String, String>> viewPre(String data, List<String> lis, int index) {
        List<Map<String, String>> prelist = new ArrayList<Map<String, String>>();
        try {
            int flag;
            InputStream is = new FileInputStream(data);
            jxl.Workbook rwb = Workbook.getWorkbook(is);
            Sheet[] rslist = rwb.getSheets();
            //获取行数
            int rows = rslist[0].getRows();
            if (index < rows) {
                for (int j = index; j < rslist[0].getRows(); j++) {
                    Map<String, String> premap = new HashMap<String, String>();
                    //列数
                    int columnnum = rslist[0].getColumns();
                    String content;
                    //如果一行没有任何内容，则跳过
                    flag = 0;
                    premap.clear();
                    for (int t = 0; t < columnnum; t++) {
                        premap.put(lis.get(t), rslist[0].getCell(t, j).getContents().trim());//将excel值赋给数组
                        content = rslist[0].getCell(t, j).getContents().trim();
                        if (!"".equals(content) && content != null) {
                            flag = 1;
                        }
                    }
                    if (flag == 1) {
                        prelist.add(premap);
                    }
                }
            }
            rwb.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return prelist;
    }

    public static String getValue(XSSFCell xssfCell) {
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

    public static boolean isCellEmpty(final XSSFCell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }
        return cell.getCellType() == CellType.STRING && cell.getStringCellValue().isEmpty();
    }
}
