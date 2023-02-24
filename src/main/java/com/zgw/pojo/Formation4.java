package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/23 - 04 - 23 - 9:41
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation4 {

    //简答题组成部分

    @TableId(type = IdType.AUTO)
    private int formationid; //

    private int tpaperid;
    private int questionid; //
    private int weight; //

}
