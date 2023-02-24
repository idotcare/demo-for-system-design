package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther: zgw
 * @Date: 2021/4/18 - 04 - 18 - 10:20
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Testpaper {

    @TableId(type = IdType.AUTO)
    private int tpaperid; //

    private String tpapername; //
    private int totalscore; //
    private int totalminutes; //
    private Date starttime;
    private Date endtime;
    private int subjectid; //
    private int teacherid; //

}
