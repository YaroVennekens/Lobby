package io.yennekens.utils;

import org.bukkit.Material;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class LobbySelector {


    public ItemStack LOBBY_SELECTOR_ITEM = new ItemStack(Material.COMPASS);


    public String KIT_SELECTOR_ITEM_NAME =  ChatUtils.format("&eServer selector &7(Right Click)");
    public String KIT_SELECTOR_ITEM_LORE_NAME = ChatUtils.format("&eKies een server!");

    public ItemStack LobbySelector(){
        ItemStack lobbySelector = LOBBY_SELECTOR_ITEM;

        ItemMeta meta = lobbySelector.getItemMeta();

        meta.setDisplayName(KIT_SELECTOR_ITEM_NAME);
        ArrayList<String> lore = new ArrayList<String>();

        lore.add(KIT_SELECTOR_ITEM_LORE_NAME);

        meta.setLore(lore);

        lobbySelector.setItemMeta(meta);



        return lobbySelector;

    }
}
