package io.yennekens.utils;

import io.yennekens.Core;
import io.yennekens.Lobby;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.persistence.Lob;
import java.io.*;
import java.util.*;

public class BungeeCordUtils {
    private static List<String> serverList = new ArrayList<>();
    private static Map<String, Integer> serverPlayerCounts = new HashMap<>();


    public static void initialize() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Lobby.getInstance(), "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(Lobby.getInstance(), "BungeeCord", (channel, player, message) -> {
            if (!channel.equals("BungeeCord")) return;
            try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(message))) {
                String subChannel = in.readUTF();

                if (subChannel.equals("GetServers")) {
                    String servers = in.readUTF();
                    serverList = Arrays.asList(servers.split(", "));
                    addServersToDatabase(serverList);
                } else if (subChannel.equals("PlayerCount")) {
                    String server = in.readUTF();
                    int count = in.readInt();
                    serverPlayerCounts.put(server, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        requestServerList();

        Bukkit.getScheduler().runTaskTimer(Lobby.getInstance(), () -> {
            for (String server : serverList) {
                requestPlayerCount(server);
            }
        }, 0L, 200L);
    }

    public static void sendPlayerToServer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Lobby.getInstance(), "BungeeCord", b.toByteArray());
    }

    public static void requestServerList() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("GetServers");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getServer().sendPluginMessage(Lobby.getInstance(), "BungeeCord", b.toByteArray());
    }

    public static void requestPlayerCount(String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("PlayerCount");
            out.writeUTF(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getServer().sendPluginMessage(Lobby.getInstance(), "BungeeCord", b.toByteArray());
    }


    private static void addServersToDatabase(List<String> servers) {
        for (String serverName : servers) {

            if (Core.getInstance().getServerManager().getServerByName(serverName) == null) {

                String description = "Je moet de beschrijving nog aanpassen " + serverName;
                ItemStack icon = new ItemStack(Material.DIAMOND);
                Core.getInstance().getServerManager().addServer(serverName, description, icon);

            }
        }
    }

    public static List<String> getServers() {
        return serverList;
    }

    public static Map<String, Integer> getServerPlayerCounts() {
        return serverPlayerCounts;
    }
}
