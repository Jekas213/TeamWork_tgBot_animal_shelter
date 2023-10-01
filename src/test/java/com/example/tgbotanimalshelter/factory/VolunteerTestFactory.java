package com.example.tgbotanimalshelter.factory;

import com.example.tgbotanimalshelter.entity.Volunteer;

import java.util.List;
import java.util.stream.LongStream;

public class VolunteerTestFactory {


    public static Volunteer buildVolunteer() {
        return new Volunteer(1L, "volunteer");
    }

    public static List<Volunteer> buildVolunteers(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new Volunteer(i, "volunteer" + i))
                .toList();
    }

    public static List<Volunteer> buildVolunteers() {
        return buildVolunteers(10);
    }
}
