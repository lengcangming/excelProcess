package www.mwj.pojo;

/**
 * @author MWJ 2020/3/8
 */
public class AliInfo {
    /**
     * id
     */
    private String id;
    /**
     * 国家
     */
    private String country;
    /**
     * 城市
     */
    private String city;
    /**
     * 机房
     */
    private String computerLab;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 商品属性
     */
    private String goodsAttribute;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String model;
    /**
     * 状态
     */
    private String status;
    /**
     * 数量
     */
    private String num;
    /**
     * 预期交付日期
     */
    private String expectedDeliveryDate;
    /**
     * 延期日期
     */
    private String extensionDate;
    /**
     * 按期交付数量
     */
    private String quantityDeliveredOnSchedule;
    /**
     * 业务大类
     */
    private String businessCategory;
    /**
     * PO号
     */
    private String PONo;
    /**
     * PO下发日期
     */
    private String POIssueDate;
    /**
     * 状态（待生产，生产中，待发货，已发货，已签收）
     */
    private String status2;
    /**
     * 本周预计发货日期
     */
    private String expectedDeliveryDateOfThisWeek;
    /**
     * 本周计划到机房日期
     */
    private String plannedArrivalDateOfThisWeek;
    /**
     * 上周计划到机房日期
     */
    private String plannedArrivalDateOfLastWeek;
    /**
     * 延期原因
     */
    private String delayReason;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否改变
     */
    private Boolean change=false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(String goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(String extensionDate) {
        this.extensionDate = extensionDate;
    }

    public String getQuantityDeliveredOnSchedule() {
        return quantityDeliveredOnSchedule;
    }

    public void setQuantityDeliveredOnSchedule(String quantityDeliveredOnSchedule) {
        this.quantityDeliveredOnSchedule = quantityDeliveredOnSchedule;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getPONo() {
        return PONo;
    }

    public void setPONo(String PONo) {
        this.PONo = PONo;
    }

    public String getPOIssueDate() {
        return POIssueDate;
    }

    public void setPOIssueDate(String POIssueDate) {
        this.POIssueDate = POIssueDate;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getExpectedDeliveryDateOfThisWeek() {
        return expectedDeliveryDateOfThisWeek;
    }

    public void setExpectedDeliveryDateOfThisWeek(String expectedDeliveryDateOfThisWeek) {
        this.expectedDeliveryDateOfThisWeek = expectedDeliveryDateOfThisWeek;
    }

    public String getPlannedArrivalDateOfThisWeek() {
        return plannedArrivalDateOfThisWeek;
    }

    public void setPlannedArrivalDateOfThisWeek(String plannedArrivalDateOfThisWeek) {
        this.plannedArrivalDateOfThisWeek = plannedArrivalDateOfThisWeek;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getComputerLab() {
        return computerLab;
    }

    public void setComputerLab(String computerLab) {
        this.computerLab = computerLab;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getPlannedArrivalDateOfLastWeek() {
        return plannedArrivalDateOfLastWeek;
    }

    public void setPlannedArrivalDateOfLastWeek(String plannedArrivalDateOfLastWeek) {
        this.plannedArrivalDateOfLastWeek = plannedArrivalDateOfLastWeek;
    }

    @Override
    public String toString() {
        return "AliInfo{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", computerLab='" + computerLab + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectNo='" + projectNo + '\'' +
                ", goodsAttribute='" + goodsAttribute + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", status='" + status + '\'' +
                ", num='" + num + '\'' +
                ", expectedDeliveryDate='" + expectedDeliveryDate + '\'' +
                ", extensionDate='" + extensionDate + '\'' +
                ", quantityDeliveredOnSchedule='" + quantityDeliveredOnSchedule + '\'' +
                ", businessCategory='" + businessCategory + '\'' +
                ", PONo='" + PONo + '\'' +
                ", POIssueDate='" + POIssueDate + '\'' +
                ", status2='" + status2 + '\'' +
                ", expectedDeliveryDateOfThisWeek='" + expectedDeliveryDateOfThisWeek + '\'' +
                ", plannedArrivalDateOfThisWeek='" + plannedArrivalDateOfThisWeek + '\'' +
                ", plannedArrivalDateOfLastWeek='" + plannedArrivalDateOfLastWeek + '\'' +
                ", delayReason='" + delayReason + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Boolean getChange() {
        return change;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }
}
