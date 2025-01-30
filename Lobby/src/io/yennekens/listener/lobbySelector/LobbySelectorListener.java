package io.yennekens.listener.lobbySelector;

import io.yennekens.Lobby;
import io.yennekens.utils.BungeeCordUtils;
import io.yennekens.utils.ChatUtils;
import io.yennekens.utils.LobbySelector;
import io.yennekens.utils.ScoreboardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

import static io.yennekens.utils.ChatUtils.resetPlayerChat;
import static io.yennekens.utils.PlayerUtils.*;

public class LobbySelectorListener implements Listener {

    @EventHandler
    public void onInteractLobbySelector(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (player.getItemInHand().getType() == Material.COMPASS) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                new LobbySelector().openLobbySelector(player);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 1);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        String title = inventory.getTitle();
        if (clickedItem == null || !clickedItem.hasItemMeta() || !title.equalsIgnoreCase(new LobbySelector().LOBBY_SELECTOR_UI_TITLE)) return;

        String serverName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        event.setCancelled(true);

        ChatUtils.sendPlayerMessage(player, "Je wordt verbonden met " + serverName + "...");
        BungeeCordUtils.sendPlayerToServer(player, serverName);
    }
}