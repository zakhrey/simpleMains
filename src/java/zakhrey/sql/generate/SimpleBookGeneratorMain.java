package zakhrey.sql.generate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class SimpleBookGeneratorMain {


    private static final String CHAR_SET = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer MAX_LENGTH = 30;
    private static final LocalDateTime DATE_START = LocalDateTime.of(1800, 1, 1, 0, 0, 0);
    private static final LocalDateTime DATE_END = LocalDateTime.now();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int bookCount = sc.nextInt();

        for (int i = 0; i < bookCount; i++) {
            System.out.printf(
                "insert into simple_main.book(id, name, release_date) VALUES ('%s', '%s', '%s')%n",
                UUID.randomUUID(),
                generateRandomString(),
                generateRandomDateTime()
            );
        }
    }

    private static LocalDateTime generateRandomDateTime() {
        if (DATE_START.isAfter(DATE_END)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        long secondsBetween = ChronoUnit.SECONDS.between(DATE_START, DATE_END);
        Random random = new Random();
        long randomSeconds = random.nextLong(secondsBetween + 1); // +1 to include the end date

        return DATE_START.plusSeconds(randomSeconds);
    }

    private static String generateRandomString() {
        Random random = new Random();
        int length = random.nextInt(MAX_LENGTH);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHAR_SET.length());
            sb.append(CHAR_SET.charAt(index));
        }
        return sb.toString();
    }
}
