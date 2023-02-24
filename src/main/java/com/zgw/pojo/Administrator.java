package com.zgw.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrator {

    @TableId
    private int adminid;

    private String adminname;
    private String adminsex;
    private String adminphone;
    private String admindepart;
    private String adminpassword;

}
