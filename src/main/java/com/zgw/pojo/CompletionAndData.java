package com.zgw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/5/25 - 05 - 25 - 10:09
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletionAndData {

    private Completion completion;
    private double accuracy;//正确率

}
