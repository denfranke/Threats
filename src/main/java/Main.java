import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                String route = generateRoute("RLRFR", 100);
                long countR = route.chars().filter(ch -> ch == 'R').count();
                int frequency = (int) countR;

                synchronized (sizeToFreq) {
                    sizeToFreq.merge(frequency, 1, Integer::sum);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        printStatistics();
    }

    private static void printStatistics() {
        if (sizeToFreq.isEmpty()) {
            System.out.println("Нет данных для анализа");
            return;
        }

        Entry<Integer, Integer> mostFrequent = sizeToFreq.entrySet().stream()
                .max(Entry.comparingByValue())
                .orElse(null);

        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)%n",
                mostFrequent.getKey(), mostFrequent.getValue());

        System.out.println("Другие размеры:");
        sizeToFreq.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(mostFrequent.getKey()))
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> System.out.printf("- %d (%d раз)%n",
                        entry.getKey(), entry.getValue()));
    }
}