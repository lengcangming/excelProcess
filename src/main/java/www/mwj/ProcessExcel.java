package www.mwj;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author MWJ 2020/3/11
 */
public class ProcessExcel {

    List<AliInfo> aliInfos;
    List<HuaweiInfo> huaweiInfos;

    public ProcessExcel(List<AliInfo> aliInfos, List<HuaweiInfo> huaweiInfos) {
        this.aliInfos = aliInfos;
        this.huaweiInfos = huaweiInfos;
    }

    public void process() {
        Stream<AliInfo> stream = aliInfos.stream();
        List<AliInfo> filterAliList = stream.filter(s -> (s.getStatus2().equals("生产中") || s.getStatus2().equals("已发货"))).collect(Collectors.toList());
        for (AliInfo aliInfo : filterAliList) {
            String strProjectNo = aliInfo.getProjectNo();
            String strModel = aliInfo.getModel();
            String strNum = aliInfo.getNum();
            List<HuaweiInfo> filterHuaweiList = huaweiInfos.stream().
                    filter(s -> s.getProjectNo().equals(strProjectNo)).collect(Collectors.toList());
            for (HuaweiInfo huaweiInfo : filterHuaweiList) {
                String strHuaweiProjectNo = huaweiInfo.getProjectNo();
                String strHuaweiMode = huaweiInfo.getModel();
                String strHuaweiNum = huaweiInfo.getNum();
                if (strHuaweiProjectNo.equals(strProjectNo) && strHuaweiMode.equals(strModel) && strHuaweiNum.equals(strNum)) {
                    processEntryPO(aliInfo,huaweiInfo);
                    processEntryDate(aliInfo,huaweiInfo);
                    processEntryStatus(aliInfo,huaweiInfo);
                }else{
                    processEntryData(aliInfo,huaweiInfos);
                }
            }
        }
    }

    private void processEntryData(AliInfo aliInfo, List<HuaweiInfo> huaweiExcelList) {
        HuaweiInfo huaweiInfo=new HuaweiInfo();
        huaweiInfo.setProjectNo(aliInfo.getProjectNo());
        huaweiInfo.setItemName(aliInfo.getGoodsAttribute());
        huaweiInfo.setModel(aliInfo.getModel());
        huaweiInfo.setNum(aliInfo.getNum());
        huaweiInfo.setChange(true);
        huaweiExcelList.add(huaweiInfo);
    }

    private void processEntryStatus(AliInfo aliInfo, HuaweiInfo huaweiInfo) {
        if(StrUtil.isNotBlank(huaweiInfo.getStatus())){
            if("已签收".equals(huaweiInfo.getStatus())||"已验收".equals(huaweiInfo.getStatus())){
                aliInfo.setStatus2("已签收");
                aliInfo.setChange(true);
            }else if("华为验货".equals(huaweiInfo.getStatus())){
                aliInfo.setStatus2("已发货");
                aliInfo.setChange(true);
            }
        }
    }

    private void processEntryPO(AliInfo aliInfo, HuaweiInfo huaweiInfo) {
        String strHuaweiPONo = huaweiInfo.getPONo();
        if (StrUtil.isNotBlank(strHuaweiPONo)) {
            aliInfo.setPONo(strHuaweiPONo);
            aliInfo.setPOIssueDate(huaweiInfo.getPOIssueDate());
            aliInfo.setChange(true);
        }
    }

    private void processEntryDate(AliInfo aliInfo, HuaweiInfo huaweiInfo) {
        if (StrUtil.isBlank(huaweiInfo.getExpectedCompletionDateOfGoodsPreparation())) {
            aliInfo.setExpectedDeliveryDateOfThisWeek(aliInfo.getExtensionDate());
            aliInfo.setExpectedDeliveryDate(DateUtil.getAppointTime(aliInfo.getExpectedDeliveryDate(), -5));
            aliInfo.setChange(true);
        } else {
            if (StrUtil.isBlank(aliInfo.getExtensionDate())) {
                System.out.println(aliInfo.getProjectName() + "无延期日期");
            } else {
                if (huaweiInfo.getExpectedCompletionDateOfGoodsPreparation().compareTo(aliInfo.getExtensionDate()) < 0) {
                    aliInfo.setExpectedDeliveryDateOfThisWeek(aliInfo.getExtensionDate());
                    aliInfo.setExpectedDeliveryDate(DateUtil.getAppointTime(aliInfo.getExpectedDeliveryDate(), -5));
                    aliInfo.setChange(true);
                } else {
                    aliInfo.setExpectedDeliveryDateOfThisWeek(huaweiInfo.getExpectedCompletionDateOfGoodsPreparation());
                    aliInfo.setExpectedDeliveryDate(DateUtil.getAppointTime(aliInfo.getExpectedDeliveryDate(), -5));
                    aliInfo.setChange(true);
                }
            }
        }
        if(StrUtil.isNotBlank(aliInfo.getPlannedArrivalDateOfThisWeek())){
            aliInfo.setPlannedArrivalDateOfLastWeek(aliInfo.getPlannedArrivalDateOfThisWeek());
            aliInfo.setChange(true);
        }
    }
}
