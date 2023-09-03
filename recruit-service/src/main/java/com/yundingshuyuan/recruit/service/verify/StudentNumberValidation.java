package com.yundingshuyuan.recruit.service.verify;

import com.yundingshuyuan.recruit.service.exception.AdminException;
import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;

@AllArgsConstructor
public class StudentNumberValidation extends AbstractUserInfoValidation {
    private String studentNum;

    @Override
    public boolean validate() {
        String flag = "^ydsy2022[0-9]{6}$";
        if (studentNum.matches(flag)) {
            throw new AdminException("管理员登录");
        }
        String pattern = "^2023[0-9]{6}$";
        if (!studentNum.matches(pattern)) {
            throw new InvalidParameterException("学号格式不正确");
        }
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }

    @Override
    public void next(AbstractUserInfoValidation nextTask) {
        super.next(nextTask);
    }
}
