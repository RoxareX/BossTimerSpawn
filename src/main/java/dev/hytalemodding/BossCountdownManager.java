package dev.hytalemodding;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class BossCountdownManager {
    private final List<BossCountdown> countdowns = new ArrayList<>();

    public synchronized void addCountdown(String bossName, int seconds, Consumer<BossCountdown> onExpire) {
        countdowns.add(new BossCountdown(UUID.randomUUID(), bossName, seconds, onExpire));
    }

    public synchronized void tick() {
        try {
            List<BossCountdown> expired = new ArrayList<>();
            for (BossCountdown countdown : countdowns) {
                countdown.decrement();
                if (countdown.isExpired()) {
                    expired.add(countdown);
                }
            }
            for (BossCountdown countdown : expired) {
                try {
                    countdown.trigger();
                } catch (Exception e) {
                    System.err.println("[BossCountdownManager] Error triggering expiration for " + countdown.getBossName());
                    e.printStackTrace();
                }
                countdowns.remove(countdown);
            }
        } catch (Exception e) {
            System.err.println("[BossCountdownManager] Error in tick()");
            e.printStackTrace();
        }
    }

    public synchronized List<BossCountdown> getCountdowns() {
        return new ArrayList<>(countdowns);
    }

    public static class BossCountdown {
        private final UUID id;
        private final String bossName;
        private int remainingSeconds;
        private final Consumer<BossCountdown> onExpire;

        public BossCountdown(UUID id, String bossName, int seconds, Consumer<BossCountdown> onExpire) {
            this.id = id;
            this.bossName = bossName;
            this.remainingSeconds = seconds;
            this.onExpire = onExpire;
        }

        public void decrement() {
            if (remainingSeconds > 0) {
                remainingSeconds--;
            }
        }

        public boolean isExpired() {
            return remainingSeconds <= 0;
        }

        public void trigger() {
            if (onExpire != null) {
                onExpire.accept(this);
            }
        }

        public String getBossName() {
            return bossName;
        }

        public int getRemainingSeconds() {
            return remainingSeconds;
        }

        public String getFormattedTime() {
            int mins = remainingSeconds / 60;
            int secs = remainingSeconds % 60;
            return String.format("%02d:%02d", mins, secs);
        }

        public UUID getId() {
            return id;
        }
    }
}
