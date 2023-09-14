package com.yundingshuyuan.recruit.service.verify;

import com.yundingshuyuan.constant.RegexConstant;
import com.yundingshuyuan.recruit.service.exception.AdminException;
import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;

@AllArgsConstructor
public class StudentNumberValidation extends AbstractUserInfoValidation {
    private String studentNum;

    @Override
    public boolean validate() {
        if (!(studentNum.matches(RegexConstant.ADMIN_SID) || studentNum.matches(RegexConstant.USER_SID))) {
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
