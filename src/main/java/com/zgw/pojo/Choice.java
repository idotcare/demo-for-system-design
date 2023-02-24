package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/17 - 04 - 17 - 9:16
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {

    @TableId(type = IdType.AUTO)
    private int choiceid; //

    private String choicequestion; //
    private String choiceoptiona; //
    private String choiceoptionb; //
    private String choiceoptionc; //
    private String choiceoptiond; //
    private String choiceanswer; //
    private String choiceanalysis; //
    private int subjectid; //

}
