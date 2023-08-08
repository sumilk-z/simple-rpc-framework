package org.example.common;

/**
 * @ClassName RpcResponseCodeEnum
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 14:01
 **/
public enum RpcResponseCodeEnum {
    SUCCESS(200, "Remote Process Call successfully."),
    FAIL(500, "Remote Process Call fail.");
    private Integer code;
    private String description;
    RpcResponseCodeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public Integer getCode() {
        return code;
    }
}
