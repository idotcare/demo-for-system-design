package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/15 - 04 - 15 - 21:23
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teach {

    @TableId(type = IdType.AUTO)
    private int teachid; //

    private int teacherid; //
    private int subjectid; //
    private int adminid; //
    
}
