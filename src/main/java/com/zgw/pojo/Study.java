package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/15 - 04 - 15 - 14:25
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Study {

    @TableId(type = IdType.AUTO)
    private int studyid; //

    private int studentid; //
    private int subjectid; //
    private int teacherid; //
    private int adminid; //

}
