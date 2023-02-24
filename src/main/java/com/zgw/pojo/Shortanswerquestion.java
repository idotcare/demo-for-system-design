package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/22 - 04 - 22 - 9:00
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shortanswerquestion {

    @TableId(type = IdType.AUTO)
    private int saqid; //

    private String saquestion; //
    private String saqanswer; //
    private String saqanalysis; //
    private int subjectid; //

}
