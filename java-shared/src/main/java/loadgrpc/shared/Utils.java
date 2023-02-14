package loadgrpc.shared;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Utils {

  public static final Random rng = new Random();
  private static AtomicBoolean once = new AtomicBoolean(false);

  static final class FormatterExtension extends Formatter {
    private final String fmt = "%1$tF %1$tT,%1$tL [%4$s] %2$-8s | %3$s%n";
    private final String debug = "%1$tF %1$tT,%1$tL [%4$s](%5$s.java:%7$s) %2$-8s | %3$s%n";

    @Override
    public String format(LogRecord record) {

      return String.format(
          fmt,
          record.getMillis(),
          record.getLevel(),
          record.getMessage(),
          record.getLongThreadID(),
          "", // record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".")+1),
          "", // record.getSourceMethodName(),
          "" // getLineNumber(record)
      );
    }
  }

  public static int getLineNumber(LogRecord record) {
    String className = record.getSourceClassName();
    String methodName = record.getSourceMethodName();
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (StackTraceElement element : stackTrace) {
      if (element.getClassName().equals(className) && element.getMethodName().equals(methodName)) {
        return element.getLineNumber();
      }
    }
    return -1;
  }

  public static void setupLogging(Logger logger) {
    logger.setUseParentHandlers(false);
    logger.setLevel(Level.INFO);

    var consoleHandler = new ConsoleHandler();
    consoleHandler.setFormatter(new FormatterExtension());
    logger.addHandler(consoleHandler);
    if (!once.getAcquire()) {
      long seed = rng.nextLong();
      rng.setSeed(seed);
      logger.info(String.format("[RNG] seed(%s)", seed));
      once.set(true);
    }
  }

  public static Integer readEnv(String key, Integer fallback) {
    return Optional.of(System.getenv(key)).map(Integer::parseInt).orElse(fallback);
  }

  public static String readEnv(String key, String fallback) {
    return Optional.of(System.getenv(key)).orElse(fallback);
  }

  public static Float readEnv(String key, Float fallback) {
    return Optional.of(System.getenv(key)).map(Float::parseFloat).orElse(fallback);
  }

  public static Long readEnv(String key, Long fallback) {
    return Optional.of(System.getenv(key)).map(Long::parseLong).orElse(fallback);
  }

  public static long[] interval(long total, int numIntervals, long variance, long minimum) {
    long[] vars = new long[numIntervals];
    if (variance != 0f) {
      for (int i = 0; i < numIntervals; i++) {
        vars[i] = rng.nextLong(-variance / 2, variance / 2);
      }
    }
    long[] bins = new long[numIntervals];
    for (int i = 0; i < numIntervals; i++) {
      bins[i] = total / numIntervals;
    }
    boolean anyNeg = false;
    long min = Long.MAX_VALUE;
    for (int i = 0; i < numIntervals; i++) {
      bins[i] += vars[i];
      anyNeg |= bins[i] < 0;
      min = Math.min(bins[i], min);
    }
    if (anyNeg) {
      long d = minimum - min;
      for (int i = 0; i < numIntervals; i++) {
        bins[i] += d;
      }
    }
    return bins;
  }
}
