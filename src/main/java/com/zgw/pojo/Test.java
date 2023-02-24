package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/24 - 04 - 24 - 21:34
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    @TableId(type = IdType.AUTO)
    private int testid; //
    
    private int tpaperid; //
    private int studentid; //
    private boolean istest; //
    private int subjectid; //

}
