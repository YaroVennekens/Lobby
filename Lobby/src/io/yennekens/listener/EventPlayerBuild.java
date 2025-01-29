package io.yennekens.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class EventPlayerBuild implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDropitem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);
    }
}
