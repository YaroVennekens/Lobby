package io.yennekens.utils;


import io.yennekens.Core;
import io.yennekens.model.Server;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LobbySelector {
    public ItemStack LOBBY_SELECTOR_ITEM = new ItemStack(Material.COMPASS);
    public String LOBBY_SELECTOR_ITEM_NAME = ChatUtils.format("&eServer selector &7(Right Click)");
    public String LOBBY_SELECTOR_ITEM_LORE_NAME = ChatUtils.format("&eKies een server!");
    public String LOBBY_SELECTOR_UI_TITLE = ChatUtils.format("&eSelecteer een server");


    public ItemStack LobbySelector() {
        ItemStack lobbySelector = LOBBY_SELECTOR_ITEM;
        ItemMeta meta = lobbySelector.getItemMeta();
        meta.setDisplayName(LOBBY_SELECTOR_ITEM_NAME);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(LOBBY_SELECTOR_ITEM_LORE_NAME);
        meta.setLore(lore);
        lobbySelector.setItemMeta(meta);
        return lobbySelector;
    }

    public Inventory lobbySelectorUI(Player player) {

        Inventory inv = Bukkit.createInventory(null, 9, LOBBY_SELECTOR_UI_TITLE);

        int slot = 0;
        for (Server server : Core.getInstance().getServerManager().getAllServers()) {


            if (server == null) {
                continue;
            }

            Integer playerCount = BungeeCordUtils.getServerPlayerCounts().get(server.getServerName());
            String serverStatus;

            if (playerCount != null) {
                serverStatus = "§6§l" + playerCount + " §espelers";
            } else {
                serverStatus = "§7Server is offline";
            }

            ItemStack item = server.getIcon();
            ItemMeta meta = item.getItemMeta();


            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);


            meta.setDisplayName("§6§l" + server.getServerName().toUpperCase());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(" ");
            String description = server.getDescription();
            lore.addAll(splitDescription(description));
            lore.add(" ");
            lore.add(serverStatus);
            lore.add(" ");
            lore.add(ChatUtils.format("&6&lKlik om te joinen"));
            meta.setLore(lore);

            item.setItemMeta(meta);

            if (!(server.getServerName().contains("lobby"))) {
                inv.setItem(slot, item);
            }

            slot++;
            if (slot >= 9) break;
        }

        return inv;
    }

    private List<String> splitDescription(String description) {
        List<String> lines = new ArrayList<>();
        description = ChatUtils.format(description);

        String[] words = description.split(" ");
        StringBuilder line = new StringBuilder();
        boolean isFirstLine = true;

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (line.length() + word.length() + 1 <= 25) {
                if (line.length() > 0) line.append(" ");
                line.append(word);
            } else {

                if (!isFirstLine) {
                    lines.add(ChatUtils.format("&e" + line.toString()));
                } else {
                    lines.add(line.toString());
                    isFirstLine = false;
                }
                line = new StringBuilder(word);
            }


            if (i == words.length - 1) {
                if (!isFirstLine) {
                    lines.add(ChatUtils.format("&e" + line.toString()));
                } else {
                    lines.add(line.toString());
                }
            }
        }

        return lines;
    }
    public void openLobbySelector(Player player) {
        player.openInventory(lobbySelectorUI(player));
    }
}