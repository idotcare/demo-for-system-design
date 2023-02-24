package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/23 - 04 - 23 - 9:36
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation2 {

    //试卷判断题组成
    @TableId(type = IdType.AUTO)
    private int formationid; //

    private int tpaperid;
    private int questionid; //
    private int weight; //

}
