package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/5/5 - 05 - 05 - 17:02
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {

    @TableId(type = IdType.AUTO)
    private int scoreid; //

    private int score; //
    private int studentid; //
    private int tpaperid; //
    private int subjectid; //

}
