package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/18 - 04 - 18 - 10:28
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation1 {

    //试卷选择题组成
    @TableId(type = IdType.AUTO)
    private int formationid; //

    private int tpaperid;
    private int questionid; //
    private int weight; //

}
