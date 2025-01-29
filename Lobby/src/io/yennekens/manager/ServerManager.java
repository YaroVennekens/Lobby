package io.yennekens.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.yennekens.model.Server;
import io.yennekens.utils.MySQLConnection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    private final Connection connection;

    public ServerManager() {
        this.connection = MySQLConnection.getConnection();

        try (Statement statement = connection.createStatement()) {
            String createTableSQL =
                    "CREATE TABLE IF NOT EXISTS servers (" +
                            "server_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "server_name VARCHAR(255) NOT NULL UNIQUE, " +
                            "description TEXT NOT NULL, " +
                            "icon TEXT NOT NULL" +
                            ");";
            statement.execute(createTableSQL);
            System.out.println("Servers table has been created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addServer(String serverName, String description, ItemStack icon) {
        try {
            String iconString = serializeItem(icon);

            String query = "INSERT INTO servers (server_name, description, icon) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, serverName);
            statement.setString(2, description);
            statement.setString(3, iconString);

            statement.executeUpdate();
            System.out.println("Server '" + serverName + "' has been added to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Server getServerByName(String serverName) {
        try {
            String query = "SELECT * FROM servers WHERE server_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, serverName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Server(
                        resultSet.getInt("server_id"),
                        resultSet.getString("server_name"),
                        resultSet.getString("description"),
                        deserializeItem(resultSet.getString("icon"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Server> getAllServers() {
        List<Server> servers = new ArrayList<>();
        try {
            String query = "SELECT * FROM servers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                servers.add(new Server(
                        resultSet.getInt("server_id"),
                        resultSet.getString("server_name"),
                        resultSet.getString("description"),
                        deserializeItem(resultSet.getString("icon"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servers;
    }

    public void deleteServer(String serverName) {
        try {
            String query = "DELETE FROM servers WHERE server_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, serverName);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Server '" + serverName + "' has been deleted.");
            } else {
                System.out.println("No server found with the name '" + serverName + "'.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ItemStack deserializeItem(String itemString) {
        JsonObject jsonObject = new JsonParser().parse(itemString).getAsJsonObject();
        Material material = Material.valueOf(jsonObject.get("type").getAsString());
        int amount = jsonObject.get("amount").getAsInt();
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (jsonObject.has("displayName")) {
                meta.setDisplayName(jsonObject.get("displayName").getAsString());
            }
            if (jsonObject.has("lore")) {
                List<String> lore = new ArrayList<>();
                for (JsonElement loreElement : jsonObject.getAsJsonArray("lore")) {
                    lore.add(loreElement.getAsString());
                }
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
        }

        return item;
    }

    private String serializeItem(ItemStack item) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", item.getType().name());
        jsonObject.addProperty("amount", item.getAmount());

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta.hasDisplayName()) {
                jsonObject.addProperty("displayName", meta.getDisplayName());
            }
            if (meta.hasLore()) {
                JsonArray loreArray = new JsonArray();
              //  for (String lore : meta.getLore()) {
                //    loreArray.add(lore);
                //}
                jsonObject.add("lore", loreArray);
            }
        }

        return jsonObject.toString();
    }
}
