package org.example.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName RpcResponse
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 13:51
 **/
@Data
@ToString
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 8805095876470280465L;
    private String requestId;

    private Integer code;

    private String message;

    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        if (data != null) {
            response.setData(data);
        }
        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCodeEnum responseEnum) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(responseEnum.getCode());
        response.setMessage(responseEnum.getDescription());
        return response;
    }

}
