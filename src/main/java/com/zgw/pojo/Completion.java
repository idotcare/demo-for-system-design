package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/22 - 04 - 22 - 8:40
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Completion {

    @TableId(type = IdType.AUTO)
    private int completid; //

    private String completquestion; //
    private String completanswer; //
    private String completanalysis; //
    private int subjectid; //

}
