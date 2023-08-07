package org.example.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.annotation.Documented;

/**
 * @ClassName Message
 * @Description
 * @Author zhucui
 * @Date 2023/8/7 10:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements Serializable {
    /**
     * 消息内容
     */
    String content;

    /**
     * 消息描述
     */
    String description;
}
