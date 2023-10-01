package com.example.tgbotanimalshelter.factory;

import com.example.tgbotanimalshelter.entity.Dog;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class DogTestFactory {
    public static Dog buildDog() {
        return new Dog(0L, "dog", LocalDate.now(), "desc", true);
    }

    public static List<Dog> buildDogs(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new Dog(0L,
                        "dog" + i, LocalDate.now().minusDays(i),
                        "desc" + i,
                        ThreadLocalRandom.current().nextBoolean()))
                .collect(Collectors.toList());
    }

    public static List<Dog> buildDogs() {
        return buildDogs(10);
    }
}
