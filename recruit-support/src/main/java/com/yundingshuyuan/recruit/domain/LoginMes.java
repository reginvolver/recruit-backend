package com.yundingshuyuan.recruit.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMes {

    private String openid;

    private Integer id;

    private Integer isAdmin;
}
