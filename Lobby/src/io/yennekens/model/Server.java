package io.yennekens.model;


import org.bukkit.inventory.ItemStack;

public class Server {

    private int serverId;
    private String serverName;
    private String description;
    private ItemStack icon;

     public Server(int serverId, String serverName, String description, ItemStack icon) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.description = description;
        this.icon = icon;
    }


    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }


    @Override
    public String toString() {
        return "Server{" +
                "serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", description='" + description + '\'' +
                ", icon=" + icon +
                '}';
    }
}
