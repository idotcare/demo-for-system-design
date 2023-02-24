package com.zgw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/5/25 - 05 - 25 - 10:12
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SAQAndData {

    private Shortanswerquestion saq;
    private  double scoringRate;//得分率
    private double accuracy;//正确率

}
