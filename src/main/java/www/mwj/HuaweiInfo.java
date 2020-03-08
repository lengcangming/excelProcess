package www.mwj;

/**
 * @author MWJ 2020/3/8
 */
public class HuaweiInfo {
    /**
     * 批次
     */
    private String batch;
    /**
     * PO号
     */
    private String PONo;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * PO下发日期
     */
    private String POIssueDate;
    /**
     * 合同号
     */
    private String huaweiContractNumber;
    /**
     * 物品名称
     */
    private String itemName;
    /**
     * 型号
     */
    private String model;
    /**
     * 数量
     */
    private String num;
    /**
     * 发货地址
     */
    private String shippingAddress;
    /**
     * 机房
     */
    private String computerLab;
    /**
     * 状态
     */
    private String status;
    /**
     * 期望到货时间
     */
    private String expectedDeliveryDate;
    /**
     * 预计备货完成时间
     */
    private String expectedCompletionDateOfGoodsPreparation;
    /**
     * 预计到货时间
     */
    private String estimateArrivalDate;
    /**
     * 商务受控
     */
    private String businessControlled;
    /**
     * 计划送达时间
     */
    private String scheduledArrivalDate;
    /**
     * 备注
     */
    private String remark;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getPONo() {
        return PONo;
    }

    public void setPONo(String PONo) {
        this.PONo = PONo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getPOIssueDate() {
        return POIssueDate;
    }

    public void setPOIssueDate(String POIssueDate) {
        this.POIssueDate = POIssueDate;
    }

    public String getHuaweiContractNumber() {
        return huaweiContractNumber;
    }

    public void setHuaweiContractNumber(String huaweiContractNumber) {
        this.huaweiContractNumber = huaweiContractNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getComputerLab() {
        return computerLab;
    }

    public void setComputerLab(String computerLab) {
        this.computerLab = computerLab;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getExpectedCompletionDateOfGoodsPreparation() {
        return expectedCompletionDateOfGoodsPreparation;
    }

    public void setExpectedCompletionDateOfGoodsPreparation(String expectedCompletionDateOfGoodsPreparation) {
        this.expectedCompletionDateOfGoodsPreparation = expectedCompletionDateOfGoodsPreparation;
    }

    public String getEstimateArrivalDate() {
        return estimateArrivalDate;
    }

    public void setEstimateArrivalDate(String estimateArrivalDate) {
        this.estimateArrivalDate = estimateArrivalDate;
    }

    public String getBusinessControlled() {
        return businessControlled;
    }

    public void setBusinessControlled(String businessControlled) {
        this.businessControlled = businessControlled;
    }

    public String getScheduledArrivalDate() {
        return scheduledArrivalDate;
    }

    public void setScheduledArrivalDate(String scheduledArrivalDate) {
        this.scheduledArrivalDate = scheduledArrivalDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HuaweiInfo{" +
                "batch='" + batch + '\'' +
                ", PONo='" + PONo + '\'' +
                ", projectNo='" + projectNo + '\'' +
                ", POIssueDate='" + POIssueDate + '\'' +
                ", huaweiContractNumber='" + huaweiContractNumber + '\'' +
                ", itemName='" + itemName + '\'' +
                ", model='" + model + '\'' +
                ", num='" + num + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", computerLab='" + computerLab + '\'' +
                ", status='" + status + '\'' +
                ", expectedDeliveryDate='" + expectedDeliveryDate + '\'' +
                ", expectedCompletionDateOfGoodsPreparation='" + expectedCompletionDateOfGoodsPreparation + '\'' +
                ", estimateArrivalDate='" + estimateArrivalDate + '\'' +
                ", businessControlled='" + businessControlled + '\'' +
                ", scheduledArrivalDate='" + scheduledArrivalDate + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
