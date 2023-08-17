package com.yundingshuyuan.recruit.service.verify;

import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;

@AllArgsConstructor
public class StudentNumberValidation extends AbstractUserInfoValidation {
    private String studentNum;

    @Override
    public boolean validate() {
        String pattern = "^[0-9]{10}$";
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
