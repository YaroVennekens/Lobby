package io.yennekens.listener.lobbySelector;

import io.yennekens.Lobby;
import io.yennekens.utils.BungeeCordUtils;
import io.yennekens.utils.ChatUtils;
import io.yennekens.utils.LobbySelector;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class LobbySelectorListener implements Listener {

    @EventHandler
    public void onInteractLobbySelector(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (player.getItemInHand().getType() == Material.COMPASS) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                new LobbySelector(Lobby.getServerManager()).openLobbySelector(player);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 1);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String serverName = clickedItem.getItemMeta().getDisplayName().replace("§a", "");
        event.setCancelled(true);

        ChatUtils.sendPlayerMessage(player, "Je wordt verbonden met " + serverName + "...");
        BungeeCordUtils.sendPlayerToServer(player, serverName);
    }
}