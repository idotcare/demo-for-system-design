package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/25 - 04 - 25 - 21:22
 * @Description: com.zgw.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer3 {

    @TableId(type = IdType.AUTO)
    private int answerid; //

    private int studentid; //
    private int questionid; //
    private int tpaperid; //
    private String answer; //
    private int actualscore; //
    private int weight; //
    private boolean isread;

}
