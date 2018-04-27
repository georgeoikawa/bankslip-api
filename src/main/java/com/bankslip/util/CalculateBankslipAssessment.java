package com.bankslip.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.bankslip.constants.Constants;

public class CalculateBankslipAssessment {

    private static boolean isExpired(final LocalDate dueDate) {
        return dueDate.isBefore(LocalDate.now());
    }

    public static int calculateAssessment(LocalDate dueDate, Integer totalInCents) {
        int assessment = 0;

        if (isExpired(dueDate)) {
            int delay = (int) ChronoUnit.DAYS.between(dueDate, LocalDate.now());

            if (delay > Constants.ASSESSMENT_TEN_DAYS) {
            	assessment = (int) ((totalInCents  * 0.01) * delay);
            } else {
                assessment = (int) ((totalInCents  * 0.005) * delay);
            }
        }
        return assessment;
    }
}