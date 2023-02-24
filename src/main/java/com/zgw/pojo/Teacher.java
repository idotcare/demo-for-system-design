package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/12 - 04 - 12 - 16:37
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @TableId
    private int teacherid; //

    private String teachername; //
    private String teachersex; //
    private String teacherphone; //
    private String teachermajor; //
    private String teacherdepart; //
    private String teacherpassword; //
    private int adminid; //
    
    
}
