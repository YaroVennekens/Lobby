package io.yennekens.utils;

import io.yennekens.Lobby;
import org.bukkit.entity.Player;

import javax.persistence.Lob;

public class ChatUtils {


    public static String format(String message) {
        return message.replaceAll("&", "§");
    }



    public static void sendPlayerMessage(Player player, String message) {
        player.sendMessage(format(Lobby.prefix + message));
    }

    public static void resetPlayerChat(Player player) {

        for(int i = 0; i<= 300; i++){
            player.sendMessage(" ");

        }
    }
}
