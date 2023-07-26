package com.yundingshuyuan.recruit.service.otverify;

import org.springframework.stereotype.Component;

@Component
public class OpenTimeValidationBuilder {
        private OpenTimeValidation firstTask;
        private OpenTimeValidation currentTask;

        public OpenTimeValidationBuilder add(OpenTimeValidation validationTask) {
            if (firstTask == null) {
                firstTask = validationTask;
                currentTask = validationTask;
            } else {
                currentTask.next(validationTask);
                currentTask = validationTask;
            }
            return this;
        }

        public OpenTimeValidation build() {
            return firstTask;
        }
}
