package com.example.client.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class InterviewQuestionTest {
    private static int[] originValues = {268, 670, 0, -999, -9, -1234, 9, 556, 777, 7999};
    private static int[] expectValues = {5268, 6750, 50, -5999, -59, -12345, 95, 5565, 7775, 79995};

    @Test
    void solution1Test() {
        for (int i = 0; i < originValues.length; i++) {
            log.info("solution1: originValue={}, expectValues={}", originValues[i], expectValues[i]);
            Assertions.assertEquals(expectValues[i], InterviewQuestion.solution1(originValues[i]));
        }
    }

    @Test
    void solution2Test() {
        for (int i = 0; i < originValues.length; i++) {
            log.info("solution2: originValue={}, expectValues={}", originValues[i], expectValues[i]);
            Assertions.assertEquals(expectValues[i], InterviewQuestion.solution2(originValues[i], 5));
        }
    }


}