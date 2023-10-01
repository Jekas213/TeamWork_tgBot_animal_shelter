package com.example.tgbotanimalshelter.factory;

import com.example.tgbotanimalshelter.entity.Cat;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CatTestFactory {
    public static Cat buildCat() {
        return new Cat(0L, "cat", LocalDate.now(), "desc", true);
    }

    public static List<Cat> buildCats(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new Cat(0L,
                        "cat" + i, LocalDate.now().minusDays(i),
                        "desc" + i,
                        ThreadLocalRandom.current().nextBoolean()))
                .collect(Collectors.toList());
    }

    public static List<Cat> buildCats() {
        return buildCats(10);
    }
}
