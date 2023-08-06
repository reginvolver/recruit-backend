package com.yundingshuyuan.recruit.service.verify;

/**
 * 预约面试时间参数校验
 */
public abstract class AbstractOpenTimeValidation {
    protected AbstractOpenTimeValidation nextTask;

    /**
     * 校验
     *
     * @return 是否成功
     */
    public abstract boolean validate();

    /**
     * 校验链下一个
     *
     * @param nextTask 下一个校验任务
     */
    public void next(AbstractOpenTimeValidation nextTask) {
        this.nextTask = nextTask;
    }

    /**
     * Builder 内部类
     */
    public static class Builder {

        private AbstractOpenTimeValidation firstTask;
        private AbstractOpenTimeValidation currentTask;

        public Builder add(AbstractOpenTimeValidation validationTask) {
            if (firstTask == null) {
                firstTask = validationTask;
            } else {
                currentTask.next(validationTask);
            }
            currentTask = validationTask;
            return this;
        }

        public AbstractOpenTimeValidation build() {
            return this.firstTask;
        }
    }

}
