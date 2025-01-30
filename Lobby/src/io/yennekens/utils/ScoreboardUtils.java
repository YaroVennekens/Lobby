package io.yennekens.utils;

import io.yennekens.Core;
import io.yennekens.model.Rank;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
public class ScoreboardUtils {
    public static void setupPlayerScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("stats", "dummy");
        objective.setDisplayName(ChatUtils.format("Lobby"));

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(scoreboard);

        updateScoreboard(player);
        animateDisplayName(player);

    }

    public static void updateScoreboard(Player player) {

        Rank rank = Core.getInstance().getRankManager().getRank(player.getUniqueId());

        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("stats");

        if (objective != null) {
            for (String entry : scoreboard.getEntries()) {
                scoreboard.resetScores(entry);
            }
            objective.getScore("§r ").setScore(10);
            objective.getScore("§6Speler§f " + player.getName()).setScore(9);
            objective.getScore("§6Rank " + rank.getPrefix()).setScore(8);
            objective.getScore("§r").setScore(7);
            objective.getScore("§r").setScore(6);
            objective.getScore("§r").setScore(3);
            objective.getScore("§r").setScore(1);
            objective.getScore("§e§lplay.hatrex.com").setScore(0);
        }
    }

    public static void removeScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }


    public static void animateDisplayName(Player player) {


        new BukkitRunnable() {
            private String originalDisplayName = ChatUtils.format("LOBBY");
            private int charIndex = 0;
            private boolean isWhite = false;

            @Override
            public void run() {

                Scoreboard scoreboard = player.getScoreboard();
                Objective objective = scoreboard.getObjective("stats");


                if (objective == null) {
                    objective = scoreboard.registerNewObjective("stats", "dummy");
                    objective.setDisplayName(ChatUtils.format("LOBBY"));
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }

                StringBuilder animatedDisplayName = new StringBuilder();
                for (int i = 0; i < originalDisplayName.length(); i++) {
                    if (i <= charIndex) {
                        animatedDisplayName.append(isWhite ? ChatColor.GOLD : ChatColor.YELLOW)
                                .append(originalDisplayName.charAt(i));
                    } else {
                        animatedDisplayName.append(ChatColor.YELLOW)
                                .append(originalDisplayName.charAt(i));
                    }
                }

                objective.setDisplayName(animatedDisplayName.toString());

                charIndex = (charIndex + 1) % originalDisplayName.length();
                if (charIndex == 0) {
                    isWhite = !isWhite;
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("Lobby"), 0L, 5L);
    }
}