package com.example.tgbotanimalshelter.factory;

import com.example.tgbotanimalshelter.entity.Report;

import java.util.Date;

public class ReportTestFactory {

    public static Report buildReport() {
        return new Report(
                1L,
                1L,
                new Date(),
                new byte[]{},
                "diet",
                "wellBeing",
                "behaviours"
        );
    }
}
