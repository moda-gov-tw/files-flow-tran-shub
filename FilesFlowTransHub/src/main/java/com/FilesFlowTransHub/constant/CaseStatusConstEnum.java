package com.FilesFlowTransHub.constant;


public enum CaseStatusConstEnum {
    INITIAL("00", "初始化"),
    RECEIVED("01", "已收件"),
    PROCESSING("02", "處理中"),
    CLOSED("10", "案件結案"),
    ERROR("97", "案件異常");

    private String key;
    private String value;

    private CaseStatusConstEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
    public static String getValueByKey(String key) {
        for (CaseStatusConstEnum status : CaseStatusConstEnum.values()) {
            if (status.getKey().equals(key)) {
                return status.getValue(); 
            }
        }
        return null; 
    }
}
