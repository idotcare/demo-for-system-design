package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zgw
 * @Date: 2021/4/13 - 04 - 13 - 9:53
 * @Description: com.zgw.pojo
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @TableId
    private int studentid;

    private String studentname; //
    private String studentsex; //
    private String studentidnum;//
    private String studentgrade; //
    private String studentpassword; //
    private int adminid; //

}
