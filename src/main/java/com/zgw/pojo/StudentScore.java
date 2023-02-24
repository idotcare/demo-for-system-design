package com.zgw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/5/9 - 05 - 09 - 22:18
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScore {

    private int ranking;
    private Student student;
    private Score score;

}
