package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/21 - 04 - 21 - 10:03
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Judge {

    @TableId(type = IdType.AUTO)
    private int judgeid; //

    private String judgequestion; //
    private String judgeoptiona; //
    private String judegoptionb; //
    private String judgeanswer; //
    private String judgeanalysis; //
    private int subjectid; //


}
