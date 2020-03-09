package www.mwj;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
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

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

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
     * @author 王春林
     * @return
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
     * @Title: querySSXByRegionLength
     * @Description: 根据行政区划编码的长度判断省市县
     * @Author:Wang Zhefeng
     * @param @param region
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
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
     * @Title: genJiJson
     * @Description: TODO
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
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
     * @Title: genYueJson
     * @Description: TODO
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
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

    /**
     * @description 转换字符集
     * @param request
     * @return
     */
    public static String convertUtf(HttpServletRequest request) {
        String _data = request.getParameter("data");
        if (StrUtil.isNotBlank(_data)) {
            try {
                _data = java.net.URLDecoder.decode(_data, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _data;
    }

    /**
     * @description 根据区化反回级别
     * @param region
     * @return
     */
    public static String getLevel(String region) {
        String regionStr = "";
        String level = "";
        if (StrUtil.isNotBlank(region)) {
            regionStr = new StringBuffer(region).reverse().toString();
            regionStr = Long.parseLong(regionStr) + "";
            switch (regionStr.length()) {
                // 省
                case 2:
                    level = "1";
                    break;
                // 市
                case 3:
                case 4:
                    level = "2";
                    break;
                // 县
                case 5:
                case 6:
                    level = "3";
                    break;
                // 乡镇
                case 7:
                case 8:
                case 9:
                    level = "4";
                    break;
                // 村
                case 10:
                case 11:
                case 12:
                    level = "5";
                    break;
                default:
                    level = "error";
            }
        }
        return level;
    }

    public static List<Map<String, Object>> getChildsUnit(String region,
                                                          SqlMapper sqlMapper) {
        String sregion = region.replaceFirst("00", "__");
        if (sregion.length() == 6)
            sregion += "000000";
        String selstr = "SELECT * from  sys_region WHERE code LIKE '" + sregion
                + "' AND deleted='0' AND code <> '" + region
                + "' ORDER BY code DESC ";
        List<Map<String, Object>> list = sqlMapper.selectList(selstr);
        return list;
    }
    //导入时格式化日期   zhanglei
    public static String dataformat(String source){
        String date="";
        if(RegExpValidatorUtils.isDate(source)){
            if(source.contains(".")){
                source=source.replace(".", "-");
            }
            if(source.contains("/")){
                source=source.replace("/", "-");
            }


            if(!"".equals(source)){
                SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = sdft.format(sdff.parse(source));
                } catch (ParseException e) {
                    // TODO Auto-generated catch blockO
                    e.printStackTrace();
                    date="error";
                }
            }
        }else{
            date="error";
        }
        return date;
    }
    //导入时 判断数值
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    //导入时 判断数值与小数点
    public static boolean isNumericfloat(String str){
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }
    //导入时 判断空行
    public static boolean konghang(String[] content){
        int flag = 0;
        for( int t=0;t<content.length;t++){
            if(!"".equals(content[t])&&content[t] != null){
                flag=1;
                break;
            }
        }
        if(flag==0){
            return true;
        }
        return false;
    }
    //导入时 行政区划    作者：张磊
    //取出市级id
    public static String xingzhengquhua(String shi,String xian,String xiang,String cun,SqlMapper sqlMapper){
        String code = null;
        List<Map<String, Object>> list1=null;
        List<Map<String, Object>> list2=null;
        List<Map<String, Object>> list3=null;
        List<Map<String, Object>> list4=null;
        String pid1 = null;
        String pid2 = null;
        String pid3 = null;
        if(!"".equals(shi)){
            String sql1 = "SELECT id,code  FROM sys_region WHERE name = '"+shi.trim()+"'";
            list1=sqlMapper.selectList(sql1);
        }
        if(list1 !=null && list1.size()>0){
            pid1 = list1.get(0).get("id").toString();
        }else{
            return "1";
        }
        //取出县级id
        if(pid1!=null){
            if(xian!=null&&!"".equals(xian)){
                String sql2 = "SELECT id,code  FROM sys_region WHERE pid = '"+pid1+"' AND  name = '"+xian.trim()+"'";
                list2 = sqlMapper.selectList(sql2);
                if(list2!=null && list2.size()>0){
                    pid2 = list2.get(0).get("id").toString();
                }else{
                    return "2";
                }
            }else{
                return "2";
            }
        }
        //取出乡级id
        if(pid2!=null){
            if(xiang!=null&&!"".equals(xiang)){
                String sql3 = "SELECT id,code  FROM sys_region WHERE pid = '"+pid2+"'AND  name = '"+xiang.trim()+"'";
                list3 = sqlMapper.selectList(sql3);
                if(list3!=null && list3.size()>0){
                    pid3 = list3.get(0).get("id").toString();
                }else{
                    return "3";
                }
            }else{
                return "3";
            }
        }
        //取出村的code
        if(pid3!=null){
            if(cun!=null&&!"".equals(cun)){
                String sql4 = "SELECT id,code  FROM sys_region WHERE pid = '"+pid3+"'AND  name = '"+cun.trim()+"'";
                list4 = sqlMapper.selectList(sql4);
                if(list4!=null && list4.size()>0){
                    code = list4.get(0).get("code").toString();
                }else{
                    return "4";
                }
            }else{
                return "4";
            }
        }
        return code;
    }
    //导入时 判断excel里数据是否重复  zhanglei
    public static String excelrepeat(String content,int j,List<Map<String, Object>> list,String name){
        String login="";
        int flag2 =0;
        if(list.size()>0){
            for(int k=0;k<j-1;k++){
                if(content.trim().equals(list.get(k).get(name).toString())){
                    flag2=1;
                    break;
                }
            }
        }
        if(flag2==1){
            login="error";
        }
        return login;
    }
    //导入时 判断基地与区划在excel里是否重复
    public static String exrebaseregion(String base,String code, int j, List<Map<String, Object>> list,String basename,String codename){
        String flag ="norepeat";
        if(list.size()>0){
            for(int k=0;k<j-1;k++){
                if(base.trim().equals(list.get(k).get(basename).toString()) && code.trim().equals(list.get(k).get(codename).toString())){
                    flag=String.valueOf(k);
                    break;
                }
            }
        }
        return flag;
    }
    //发微信提醒
    //公司id--"wx8f4660948e6d4b0d"；平台秘钥："5EZTTcXj5qc8YO96IUTL06rDDce2UDd03WR_0clGxuEeIUNNhHXWS1EGawpZQWnj"；
    //touser--成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）;toparty --部门ID列表，多个接收者用‘|’分隔，最多支持100个;
    //totag --标签ID列表，多个接收者用‘|’分隔，最多支持100个;msgtype--消息类型，此时固定为--text ;agentid--企业应用的id，整型;
    //content--消息内容，最长不超过2048个字节;safe--是否是保密消息，0表示否，1表示是，默认0
    public static void WecharSend(String wecharName,String secret, String content){
//      AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
        String request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getSendToken();
        String output = "{ \"touser\":\""+wecharName+"\",\"toparty\": \"\",\"totag\": \"\",\"msgtype\": \"text\"," +
                "\"agentid\":1 ,\"text\": {\"content\":  \"  [河北省农产品质量安全监管平台]提示:"+content+"\"},\"safe\":0}";
        JSONObject json1 = null;
        try {
            if(SaveToken.getIstance().getToken()!=null){
                json1 = NetUtil.httpRequest(request, "POST", output);
//              json1 = NetUtil.httpRequest(request, "POST", new String(output.getBytes("iso-8859-1"),"utf-8"));
            }
            boolean isTrue = new RequestTokenUitl().judgmentTokens(json1);
            if(isTrue){
//              if(json1==null||((Integer)json1.get("errcode") + "").equals("40001")||((Integer)json1.get("errcode") + "").equals("40014")){
                AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
                SaveToken.getIstance().setSendToken(accessToken.getToken());
                request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getSendToken();
                json1 = NetUtil.httpRequest(request, "POST", output);
//              json1 = NetUtil.httpRequest(request, "POST", new String(output.getBytes("iso-8859-1"),"utf-8"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//      System.out.println("errcode:"+NetUtil.httpRequest(request, "POST", output).getString("errcode"));
//      MyErr myErr = WecharUtil.sendMessage("wx8f4660948e6d4b0d", "3Mt--M-qSxLMYU6eoLYyfDF_7NXy6bSFr6TPQKjzUlkAArLPiNPL6TgYQb5RjCX8",
//              "{ \"touser\":\""+wecharName+"\",\"toparty\": \"\",\"totag\": \"\",\"msgtype\": \"text\"," +
//              "\"agentid\":1 ,\"text\": {\"content\":\""+content+"\"},\"safe\":0}");
//      System.out.println("myErr ："+myErr.getErrcode()+";;;"+myErr.getErrmsg());
//      System.out.println("{ \"touser\":\""+wecharName+"\",\"toparty\": \"\",\"totag\": \"\",\"msgtype\": \"text\"," +
//              "\"agentid\":1 ,\"text\": {\"content\":\""+content+"\"},\"safe\":0}");
    }
    public static void WecharSendHasAgentid(String wecharName,String secret, String content,String agentid){
//      AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
        String request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getToken();
        String output = "{ \"touser\":\""+wecharName+"\",\"toparty\": \"\",\"totag\": \"\",\"msgtype\": \"text\"," +
                "\"agentid\":\""+agentid+"\" ,\"text\": {\"content\":  \"  [河北省农产品质量安全监管平台]提示:"+content+"\"},\"safe\":0}";
        JSONObject json1 = null;
        try {
            if(SaveToken.getIstance().getToken()!=null){
                json1 = NetUtil.httpRequest(request, "POST", new String(output));
            }
            boolean isTrue = new RequestTokenUitl().judgmentTokens(json1);
            if(isTrue){
//              if(json1==null||((Integer)json1.get("errcode") + "").equals("40001")||((Integer)json1.get("errcode") + "").equals("40014")){
                AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
                SaveToken.getIstance().setToken(accessToken.getToken());
                request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getToken();
                json1 = NetUtil.httpRequest(request, "POST", output);
//              json1 = NetUtil.httpRequest(request, "POST", new String(output.getBytes("iso-8859-1"),"utf-8"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void WecharSendHasAgentid(String wecharName,String secret, String content,String agentid,String safe){
//      AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
        String request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getSendToken();
        String output = "{ \"touser\":\""+wecharName+"\",\"toparty\": \"\",\"totag\": \"\",\"msgtype\": \"text\"," +
                "\"agentid\":\""+agentid+"\" ,\"text\": {\"content\":  \"  [河北省农产品质量安全监管平台]提示:"+content+"\"},\"safe\":0}";//@@@@@@@@@@
        JSONObject json1 = null;
        try {
            if(SaveToken.getIstance().getToken()!=null){
                json1 = NetUtil.httpRequest(request, "POST", new String(output));
            }
            boolean isTrue = new RequestTokenUitl().judgmentTokens(json1);
            if(isTrue){
//              if(json1==null||((Integer)json1.get("errcode") + "").equals("40001")||((Integer)json1.get("errcode") + "").equals("40014")){
                AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
                SaveToken.getIstance().setSendToken(accessToken.getToken());
                request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getSendToken();
                json1 = NetUtil.httpRequest(request, "POST", output);
//              json1 = NetUtil.httpRequest(request, "POST", new String(output.getBytes("iso-8859-1"),"utf-8"));
            }
            //NetUtil.httpRequest(request, "POST", new String(output.getBytes("iso-8859-1"),"utf-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void WecharSendHasAgentids(String wecharName,String secret, String content,String agentid,String safe){
//      AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
        String request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getSendToken();
        String output = "{ \"touser\":\""+wecharName+"\",\"toparty\": \"\",\"totag\": \"\",\"msgtype\": \"text\"," +
                "\"agentid\":\""+agentid+"\" ,\"text\": {\"content\":  \"  [河北省农产品质量安全监管平台]提示:"+content+"\"}}";
        //,\"safe\":"+safe+"
        JSONObject jsonOutPut = new JSONObject();
        jsonOutPut.put("touser", wecharName);
        jsonOutPut.put("msgtype", "text");
        jsonOutPut.put("agentid", Integer.valueOf(agentid));
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("content", "[河北省农产品质量安全监管平台]提示:"+content);
        jsonOutPut.put("text", jsonContent.toString());
//      jsonOutPut.put("safe", safe);
        JSONObject json1 = null;
        try {
            if(SaveToken.getIstance().getToken()!=null){
                json1 = NetUtil.httpRequest(request, "POST", jsonOutPut.toString());
            }
            if(json1!=null){
                logger.debug("WecharSendHasAgentids#####################>>>>>>>:"+json1.toString()+" , "+json1.toString());
            } else {
                logger.debug("WecharSendHasAgentids##########json1###########>>>>>>>:"+"是空的");
            }
            boolean isTrue = new RequestTokenUitl().judgmentTokens(json1);
            if(isTrue){
//              if(json1==null||((Integer)json1.get("errcode") + "").equals("40001")||((Integer)json1.get("errcode") + "").equals("40014")){
                AccessToken accessToken = NetUtil.getAccessToken("wx8f4660948e6d4b0d", secret);
                SaveToken.getIstance().setSendToken(accessToken.getToken());
                request = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+SaveToken.getIstance().getSendToken();
                json1 = NetUtil.httpRequest(request, "POST", jsonOutPut.toString());
                System.out.println(json1);
                if(json1!=null){
                    logger.debug("WecharSendHasAgentids$$$$$$$$$$$$$$$$$$$$>>>>>>>:"+json1.toString()+" , "+json1.toString());
                } else {
                    logger.debug("WecharSendHasAgentids$$$$$$$$$$$$$$$$json1$$$$$$$$$$$$$$>>>>>>>:"+"是空的");
                }
            }
            //NetUtil.httpRequest(request, "POST", new String(output.getBytes("iso-8859-1"),"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //name--对应的表名
    //idList--向表中插入的对应的字段名
    //lis--要插入的数据Map内容顺序上无要求

    public static String spStr(String name,List<String> idlist,List<Map<String,String>> lis){
        StringBuffer sbf = new StringBuffer();
        sbf.append("INSERT into ");
        sbf.append(name);
        sbf.append(" (");
        for(int i=0; i<idlist.size();i++){
            sbf.append(idlist.get(i)+",");
        }
        sbf.deleteCharAt(sbf.length()-1);
        sbf.append(") VALUES ");

        for(int i=0;i<lis.size();i++){
            sbf.append(" ('");
            for(int j=0;j<idlist.size();j++){
                sbf.append(lis.get(i).get(idlist.get(j)));
                sbf.append("','");
            }
            sbf.deleteCharAt(sbf.length()-1);
            sbf.deleteCharAt(sbf.length()-1);
            sbf.append("),");
        }
        sbf.deleteCharAt(sbf.length()-1);
        sbf.append(";");
        return sbf.toString();
    }
    //name--对应的表名
    //idList--向表中插入的对应的字段名
    //lis--要插入的数据Map内容顺序上无要求

    public static String spStrCheck(String name,List<String> idlist,List<Map<String,Object>> lis){
        StringBuffer sbf = new StringBuffer();
        sbf.append("INSERT into ");
        sbf.append(name);
        sbf.append(" (");
        for(int i=0; i<idlist.size();i++){
            sbf.append(idlist.get(i)+",");
        }
        sbf.deleteCharAt(sbf.length()-1);
        sbf.append(") VALUES ");

        for(int i=0;i<lis.size();i++){
            sbf.append(" ('");
            for(int j=0;j<idlist.size();j++){
                if(null != lis.get(i).get(idlist.get(j))){
                    sbf.append(lis.get(i).get(idlist.get(j)).toString());
                }else{
                    if("uplimes".equals(idlist.get(j))){
                        sbf.append("0");
                    }
                    if("lowerlimes".equals(idlist.get(j))){
                        sbf.append("0");
                    }
                    if("checkvalue".equals(idlist.get(j))){
                        sbf.append("0");
                    }
                }
                sbf.append("','");
            }
            sbf.deleteCharAt(sbf.length()-1);
            sbf.deleteCharAt(sbf.length()-1);
            sbf.append("),");
        }
        sbf.deleteCharAt(sbf.length()-1);
        sbf.append(";");
        return sbf.toString();
    }
    //名称
    //读取excel表适用于单sheet数据低版本excel
    //data--读入的excel文件路径
    //lis--自定义的list保存值对应于页面相应字段需要顺序加入
    //index--数据标志位对应于业务数据起始行,第一行下标为0
    public static List<Map<String, String>> viewPre(String data,List<String> lis,int index) {
        List<Map<String,String>> prelist=new ArrayList<Map<String,String>>();
        try {
            int flag;
            InputStream is = new FileInputStream(data);
            jxl.Workbook rwb = Workbook.getWorkbook(is);
            Sheet[] rslist = rwb.getSheets();
            //获取行数
            int rows = rslist[0].getRows();
            if(index < rows){
                for (int j = index; j < rslist[0].getRows(); j++) {
                    Map<String, String> premap = new HashMap<String, String>();
                    //列数
                    int columnnum =rslist[0].getColumns();
                    String content;
                    //如果一行没有任何内容，则跳过
                    flag = 0;
                    premap.clear();
                    for( int t=0;t<columnnum;t++){
                        premap.put(lis.get(t), rslist[0].getCell(t,j).getContents().trim());//将excel值赋给数组
                        content = rslist[0].getCell(t,j).getContents().trim();
                        if(!"".equals(content)&&content != null){
                            flag=1;
                        }
                    }
                    if(flag==1){
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
    //根据列名返回其字段值
    //name--对应的表名
    public static List<String> getColumnNameStr(SqlMapper sqlMapper,String name){
        return sqlMapper.selectList("SELECT column_name name FROM information_schema.columns WHERE table_schema = 'agripro' AND table_name ='"+name+"'", String.class);
    }

    /**
     * querySSXByRegionLength(这里用一句话描述这个方法的作用)
     *
     * @Title: dealViewdata
     * @Description: 动态拼装日期等现在仅用于日期；多参传参形式必须成对出现为（...,int 1，list<String> lis）(1:代表格式化日期 ;lis代表所替换对应日期的列名)
     * (2：代表处理字典表；对应的传入参数为Map<String,String>其中键为字典表中的dicttypeid值为对应的字段值
     *      例如：map.put("dict_sex", "sex");)
     * (3:替换唯一情况字段传入类型;对应传入类型为Map<String,String> key为列名，value为表名)
     * (4:校验必填项是否为空传入类型为list<String> lis 其中String为校验的列名)
     * @Author:dsq
     * @return List<Map<String,String>> 返回类型
     */
    public static List<Map<String,String>> dealViewdata(List<Map<String,String>> lisMap,Map<String,String> mapflag,SqlMapper sqlMapper,Object... obj){
        for(int i=0; i<lisMap.size(); i++){
            for(int j=0;j<obj.length;j+=2){
                switch ((int)obj[j]) {
                    //替换日期
                    case 1:
                        for(int t=0;t<((List<String>)obj[j+1]).size();t++){
                            if(null != lisMap.get(i).get(((List<String>)obj[j+1]).get(t)) && !"".equals(lisMap.get(i).get(((List<String>)obj[j+1]).get(t)))){
                                if(RegExpValidatorUtils.isDate(lisMap.get(i).get(((List<String>)obj[j+1]).get(t)))){
                                    lisMap.get(i).put(((List<String>)obj[j+1]).get(t), StrUtil.dataformat(lisMap.get(i).get(((List<String>)obj[j+1]).get(t))));
                                }else{
                                    lisMap.get(i).put(((List<String>)obj[j+1]).get(t), "1error");
                                    mapflag.put("flag", "1");
                                }
                            }
                        }
                        break;
                    //替换字典表对应的数据
                    case 2:
                        for(String key : ((Map<String,String>)(obj[j+1])).keySet()){
                            String sql="select value from sys_dict where dicttypeid ='"+key+"' and item ='"+lisMap.get(i).get(((Map<String,String>)(obj[j+1])).get(key))+"'";
                            String value=sqlMapper.selectOne(sql,String.class);
                            lisMap.get(i).put(((Map<String,String>)(obj[j+1])).get(key), value);
                        }
                        break;
                    //校验数据库中是否存在该数据
                    case 3:
                        for(String key : ((Map<String,String>)(obj[j+1])).keySet()){
                            int count = 0;
                            String value = lisMap.get(i).get(key);
                            if(null != value && !"".equals(value)){
                                count = StrUtil.compareData(sqlMapper, ((Map<String,String>)(obj[j+1])).get(key), key,value);
                                if(count != 0){
                                    lisMap.get(i).put(key, "2error");
                                    mapflag.put("flag", "1");
                                }
                            }
                        }
                        break;
                    //校验excel表中传入的数据不能为空
                    case 4:
                        for(int t=0;t<((List<String>)obj[j+1]).size();t++){
                            if("".equals(lisMap.get(i).get(((List<String>)obj[j+1]).get(t))) || null == lisMap.get(i).get(((List<String>)obj[j+1]).get(t))){
                                lisMap.get(i).put(((List<String>)obj[j+1]).get(t),"3error");
                                mapflag.put("flag", "1");
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return lisMap;
    }

    /**
     *
     * @Title: dealDataViewdata
     * @Description:验证数据库中是否存在重复数据
     * name:表名
     * colum:列名
     * value:列值
     * @Author:dsq
     * @return int返回类型0-数据库中不存在该条数据;>0存在该条数据
     */
    public static int compareData(SqlMapper sqlMapper,String name,String colum,String value){
        String sql = "select count(*) from "+name+" where "+colum+"='"+value+"'";
        return sqlMapper.selectOne(sql, Integer.class);
    }
    /**
     *
     * @Title: dealMoreColum
     * @Description:替换格式为种植业、畜牧业、水产业这类数据
     * lisMap:比较的元数据（和comparelis传入的值相同）
     * map:键为所比较的列名，值为所对应的dicttypeid
     * @Author:dsq
     * @return List<Map<String,String>>
     */
    public static List<Map<String,String>> dealMoreColum(List<Map<String,String>> lisMap,SqlMapper sqlMapper,Map<String,String> map){
        String metadata = "";
        for(int i = 0; i < lisMap.size(); i++){
            for(String key : map.keySet()){
                StringBuffer sbf = new StringBuffer();
                metadata = lisMap.get(i).get(key);
                String[] arraystr = metadata.split("、");
                for(int j = 0;j<arraystr.length;j++){
                    String sql="select value from sys_dict where dicttypeid ='"+map.get(key)+"' and item ='"+arraystr[j]+"'";
                    sbf.append(sqlMapper.selectOne(sql, String.class)+",");
                }
                sbf.deleteCharAt(sbf.length()-1);
                lisMap.get(i).put(key,sbf.toString());
            }
        }
        return lisMap;
    }

    /**
     *
     * @Title: compareMoreColum
     * @Description:校验格式为种植业、畜牧业、水产业这类数据
     * lisMap:比较的元数据（和comparelis传入的值相同）
     * lis:比较的列名集合
     * Str:拼成的类似 种植业，畜牧业，水产业的串以校验数据
     * @Author:dsq
     * @return List<Map<String,String>>
     */
    public static List<Map<String,String>> compareMoreColum(List<Map<String,String>> lisMap,Map<String,String> mapflag,List<String> lis,String str){
        for(int i = 0; i < lisMap.size(); i++){
            for(int j=0; j < lis.size(); j++){
                String metadata = lisMap.get(i).get(lis.get(j));
                String[] arraystr = metadata.split("、");
                for(int t = 0;t<arraystr.length;t++){
                    if(!str.contains(arraystr[t])){
                        lisMap.get(i).put(lis.get(j), "4error");
                        mapflag.put("flag", "1");
                        break;
                    }
                }
            }
        }
        return lisMap;
    }

    /**
     *
     * @Title: compareExcelData
     * @Description:校验excel表中是否存在相同数据
     * lisMap:比较的元数据（和comparelis传入的值相同）
     * comparelis:比较的数据
     * lis:比较的字段集合
     * @Author:dsq
     * @return List<Map<String,String>>
     */
    public static List<Map<String,String>> compareExcelData(List<Map<String,String>> lisMap,Map<String,String> mapflag,List<String> lis){
        String metadata = "";
        String comparedata = "";
        List<Map<String,String>> comparelis = lisMap;
        //要比较的数据
        for(int i = 0; i < lisMap.size() - 1; i++){
            //需要比较的字段
            for(int t=0; t < lis.size();t++){
                metadata = lisMap.get(i).get(lis.get(t));
                for(int j = i + 1; j < comparelis.size(); j++){
                    comparedata = lisMap.get(j).get(lis.get(t));
                    if(comparedata.equals(metadata) && !"".equals(comparedata) && !"".equals(comparedata)){
                        lisMap.get(i).put(lis.get(t), "5error");
                        mapflag.put("flag", "1");
                        break;
                    }
                }
            }
        }
        return lisMap;
    }
    /**
     *
     * @Title: compareExcelData
     * @Description:校验违法企业单位这类数据（处理数据格式为：河北省、石家庄市、鹿泉区、...公司）
     * lisMap:处理的数据元
     * lis:处理的字段集合
     * units:为了方便解决这类数据需要专门增加一个字段以保存其id值方便向数据库中保存
     * @Author:dsq
     * @return List<Map<String,String>>
     */
    public static List<Map<String,String>> compareUnit(List<Map<String,String>> lisMap,Map<String,String> mapflag,SqlMapper sqlMapper,List<String> lis,String units){

        for(int i = 0; i < lisMap.size(); i++){
            for(int j=0;j<lis.size();j++){
                String colum = "";
                String shengid = "";
                String shiid = "";
                String code = "";
                colum = lisMap.get(i).get(lis.get(j));
                if(!"".equals(colum) && null != colum){
                    String[] str = colum.split("、");
                    if(str.length==4 && null != str){
                        shengid = sqlMapper.selectOne("select id from sys_region where name = '"+str[0]+"'", String.class);
                    }
                    if(!"".equals(shengid) && null != shengid){
                        shiid = sqlMapper.selectOne("select id from sys_region where pid = '"+shengid+"' and name = '"+str[1]+"'", String.class);
                    }
                    if(!"".equals(shiid) && null != shiid){
                        code = sqlMapper.selectOne("select code from sys_region where pid = '"+shiid+"' and name = '"+str[2]+"'", String.class);
                    }
                    if(!"".equals(code) && null != code){
                        List<String> lisid = sqlMapper.selectList("select id from sys_service_org where name = '"+str[3]+"' and regionarea like '"+StrUtil.dealRegion(code)+"%'", String.class);
                        if(lisid.size() != 1 || null == lisid ){
                            lisMap.get(i).put(lis.get(j), "6error");
                            mapflag.put("flag", "1");
                        }else{
                            lisMap.get(i).put(units, lisid.get(0));
                        }
                    }else{
                        lisMap.get(i).put(lis.get(j), "6error");
                        mapflag.put("flag", "1");
                    }
                }
            }
        }
        return lisMap;
    }
    /**
     *
     * @Title: compareExcelData
     * @Description:校验并保存行政区划这类数据
     * lisMap:处理的数据元
     * str:处理的字符串对应的字段
     * regionarea:为了方便解决这类数据需要专门增加一个字段以保存其id值方便向数据库中保存
     * @Author:dsq
     * @return List<Map<String,String>>
     */
    public static List<Map<String,String>> compareRegion(List<Map<String,String>> lisMap,Map<String,String> mapflag,SqlMapper sqlMapper,String regionarea,String... str){
        for(int i=0;i<lisMap.size();i++){
            String code = "";
            int flag = 0;
            String sheng = "";
            String shi = "";
            String xian = "";
            String xiang = "";
            String cun = "";
            String id = "";

            sheng = lisMap.get(i).get(str[0]);
            shi = lisMap.get(i).get(str[1]);
            xian = lisMap.get(i).get(str[2]);
            xiang = lisMap.get(i).get(str[3]);
            cun = lisMap.get(i).get(str[4]);

            List<Map<String, Object>> shengLis = null;
            List<Map<String, Object>> shiLis = null;
            List<Map<String, Object>> xianLis = null;
            List<Map<String, Object>> xiangLis = null;
            List<Map<String, Object>> cunLis = null;

            String shengid = "";
            String shiid = "";
            String xianid = "";
            String xiangid = "";

            if(!"".equals(sheng)){
                flag=1;
            }
            if(!"".equals(shi)){
                flag=2;
            }
            if(!"".equals(xian)){
                flag=3;
            }
            if(!"".equals(xiang)){
                flag=4;
            }
            if(!"".equals(cun)){
                flag=5;
            }
            int j=1;
            if(j<=flag){
                if(sheng != null && !"".equals(sheng)){
                    shengLis = sqlMapper.selectList("select id,code from sys_region where name = '"+sheng+"'");
                }
                if(shengLis !=null && shengLis.size() == 1){
                    shengid = shengLis.get(0).get("id").toString();
                    lisMap.get(i).put(regionarea, shengLis.get(0).get("code").toString());
                    lisMap.get(i).put(str[0], sheng);
                }else{
                    lisMap.get(i).put(str[0], "7.1error");
                    mapflag.put("flag", "1");
                }
                j++;
            }
            if(j<=flag && !"".equals(shengid)){
                if(shi != null && !"".equals(shi)){
                    shiLis = sqlMapper.selectList("select id,code from sys_region where pid = '"+shengid+"' and name = '"+shi+"'");
                }
                if(shiLis !=null && shiLis.size() == 1){
                    shiid = shiLis.get(0).get("id").toString();
                    lisMap.get(i).put(regionarea, shiLis.get(0).get("code").toString());
                    lisMap.get(i).put(str[0], shi);
                }else{
                    lisMap.get(i).put(str[0], "7.2error");
                    mapflag.put("flag", "1");
                }
                j++;
            }
            if(j<=flag && !"".equals(shiid)){
                if(xian != null && !"".equals(xian)){
                    xianLis = sqlMapper.selectList("select id,code from sys_region where pid = '"+shiid+"' and name = '"+xian+"'");
                }
                if(xianLis !=null && xianLis.size() == 1){
                    xianid = xianLis.get(0).get("id").toString();
                    lisMap.get(i).put(regionarea, xianLis.get(0).get("code").toString());
                    lisMap.get(i).put(str[0], xian);
                }else{
                    lisMap.get(i).put(str[0], "7.3error");
                    mapflag.put("flag", "1");
                }
                j++;
            }
            if(j<=flag && !"".equals(xianid)){
                if(xiang != null && !"".equals(xiang)){
                    xiangLis = sqlMapper.selectList("select id,code from sys_region where pid = '"+xianid+"' and name = '"+xiang+"'");
                }
                if(xiangLis !=null && xiangLis.size() == 1){
                    xiangid = xiangLis.get(0).get("id").toString();
                    lisMap.get(i).put(regionarea, xiangLis.get(0).get("code").toString());
                    lisMap.get(i).put(str[0], xiang);
                }else{
                    lisMap.get(i).put(str[0], "7.4error");
                    mapflag.put("flag", "1");
                }
                j++;
            }
            if(j<=flag && !"".equals(xiangid)){
                if(cun != null && !"".equals(cun)){
                    cunLis = sqlMapper.selectList("select id,code from sys_region where pid = '"+xiangid+"' and name = '"+cun+"'");
                }
                if(cunLis !=null && cunLis.size() == 1){
                    lisMap.get(i).put(regionarea,cunLis.get(0).get("code").toString());
                    lisMap.get(i).put(str[0], cun);
                }else{
                    lisMap.get(i).put(str[0], "7.5error");
                    mapflag.put("flag", "1");
                }
                j++;
            }
        }
        return lisMap;
    }
    //验证手机号码
    public static List<Map<String,String>> valiphone(List<Map<String,String>> lisMap,Map<String,String> mapflag,List<String> lis){
        for(int i=0;i<lisMap.size();i++ ){
            for(int t=0;t<lis.size();t++){
                if(null != lisMap.get(i).get(lis.get(t)) && !"".equals(lisMap.get(i).get(lis.get(t)))){
                    if(!RegExpValidatorUtils.isPhone(lisMap.get(i).get(lis.get(t)))){
                        lisMap.get(i).put((lis.get(t)), "8error");
                        mapflag.put("flag", "1");
                    }
                }
            }
        }
        return lisMap;
    }

    //验证办公室号码mtt
    public static List<Map<String,String>> valitelphone(List<Map<String,String>> lisMap,Map<String,String> mapflag,List<String> lis){
        for(int i=0;i<lisMap.size();i++ ){
            for(int t=0;t<lis.size();t++){
                if(null != lisMap.get(i).get(lis.get(t)) && !"".equals(lisMap.get(i).get(lis.get(t)))){
                    if(!RegExpValidatorUtils.isTelPhone(lisMap.get(i).get(lis.get(t)))){
                        lisMap.get(i).put((lis.get(t)), "9error");
                        mapflag.put("flag", "1");
                    }
                }
            }
        }
        return lisMap;
    }

    //验证身份证号
    public static List<Map<String,String>> valiIdcard(List<Map<String,String>> lisMap,Map<String,String> mapflag,List<String> lis){
        for(int i=0;i<lisMap.size();i++ ){
            for(int t=0;t<lis.size();t++){
                if(null != lisMap.get(i).get(lis.get(t)) && !"".equals(lisMap.get(i).get(lis.get(t)))){
                    if(!RegExpValidatorUtils.isIdCard(lisMap.get(i).get(lis.get(t)))){
                        lisMap.get(i).put((lis.get(t)), "9error");
                        mapflag.put("flag", "1");
                    }
                }
            }
        }
        return lisMap;
    }

    //验证行政区划不能为空
    public static List<Map<String,String>> valiregionarea(List<Map<String,String>> lisMap,Map<String,String> mapflag,List<String> lis){
        for(int i=0;i<lisMap.size();i++){
            int flag = 0;
            for(int t=0;t<lis.size();t++){
                if(null != lisMap.get(i).get(lis.get(t)) && !"".equals(lisMap.get(i).get(lis.get(t)))){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                lisMap.get(i).put(lis.get(0), "10error");
                mapflag.put("flag", "1");
            }
        }
        return lisMap;
    }


    /**
     * 根据用户类型进行行业校验
     * 综合业的用户可以添加种植业、畜牧业、水产业三种的行业类型
     * 种植业只能添加种植业的行业类型
     * 畜牧业只能添加畜牧业的行业类型
     * 水产业只能添加水产业的行业类型
     * @param industries 用户类型
     * @param colum 比较列
     */
    public static List<Map<String,String>> valijobtype(String industries,String colum,List<Map<String,String>> lisMap,Map<String,String> mapflag){

        for(int i=0;i<lisMap.size();i++){
            lisMap.get(i).get(colum);
            if(industries.equals("2") &&!industries.equals("种植业")){
                lisMap.get(i).put(colum, "11error");
                mapflag.put("flag", "1");
            }else if(industries.equals("3") &&!industries.equals("畜牧业")){
                lisMap.get(i).put(colum, "11error");
                mapflag.put("flag", "1");
            }else if(industries.equals("4") &&!industries.equals("水产业")){
                lisMap.get(i).put(colum, "11error");
                mapflag.put("flag", "1");
            }
        }
        return lisMap;

    }
    /**
     * 验证的日期类型为2017.04
     */
    public static List<Map<String,String>> valiDate(List<Map<String,String>> lisMap,Map<String,String> mapflag,List<String> lis){

        for(int i=0;i<lisMap.size();i++){
            for(int t=0;t<lis.size();t++){
                if(null != lisMap.get(i).get(lis.get(t)) && !"".equals(lisMap.get(i).get(lis.get(t)))){
                    if(!RegExpValidatorUtils.isDate2(lisMap.get(i).get(lis.get(t)))){
                        lisMap.get(i).put((lis.get(t)), "1error");
                        mapflag.put("flag", "1");
                    }else{
                        lisMap.get(i).put(lis.get(t),lisMap.get(i).get(lis.get(t)).replace('.', '-'));
                    }
                }
            }
        }
        return lisMap;

    }
    //对比list与数据库中的字段，返回数据库中存在而列表中不存在的字段
    //dataname数据表名称
    //lis列表中存在的字段
    public static String comparedata2list(String dataname,SqlMapper sqlMapper,List<Map<String,String>> lis){
        List<String> list =  StrUtil.getColumnNameStr(sqlMapper, dataname);
        String name = "***";
        List<String> lists = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            int flag = 0;
            for(String key : lis.get(0).keySet()){
                if(key.equals(list.get(i))){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                lists.add(list.get(i));
            }
        }
        for(int i=0;i<lists.size();i++){
            name += "'"+i+"' ='"+lists.get(i)+"'";
        }
        return name;
    }

    public static String deleteRepetion(String name){
        String[] str = name.split(",");
        if (str.length == 0 )
        {
            return null;
        }
        List<String> list = new ArrayList();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++)
        {
            if (!list.contains(str[i]))
            {
                list.add(str[i]);
                sb.append(str[i] + ",");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

}
