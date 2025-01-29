package io.yennekens.utils;

import org.bukkit.entity.Player;

public class ChatUtils {


    public static String format(String message) {
        return message.replaceAll("&", "§");
    }



    public static void resetPlayerChat(Player player) {

        for(int i = 0; i<= 300; i++){
            player.sendMessage(" ");

        }
    }
}
