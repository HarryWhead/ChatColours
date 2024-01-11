package chatcolor.chatcolor.PluginAPIs;

import chatcolor.chatcolor.Configs.ChatColorConfig;
import chatcolor.chatcolor.Configs.DataFile;
import chatcolor.chatcolor.Configs.NameColorConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static chatcolor.chatcolor.listeners.InventoryClick.getRainbowColoredName;

public class ChatColorPlaceholderHook extends PlaceholderExpansion
{
    private Plugin plugin;

    public static Map<Player, String> lastMessages;

    public ChatColorPlaceholderHook(Plugin plugin) {
        this.plugin = plugin;
        lastMessages = new HashMap<>();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ChatColor";
    }

    @Override
    public @NotNull String getAuthor() {
        return "BarryYanno";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) {
            return "";
        }

        if (identifier.equals("custom_displayname")) {
            return getCustomDisplayName(p);
        } else if (identifier.equals("custom_message")) {
            String message = getLastMessage(p);
            return getCustomColorText(p, message);
        } else if (identifier.equals("current_chatcolor")) {
            return getCurrentColorChat(p);
        } else if (identifier.equals("current_chatname")) {
            return getCurrentColorName(p);
        }
        return "";
    }

    public String getLastMessage(Player player) {
        return lastMessages.get(player);
    }

    public String getCustomDisplayName(Player player) {
        String coloredName;

        coloredName = ChatColor.translateAlternateColorCodes('&', "&f" + player.getName());
        if (DataFile.get().contains(player.getName() + ".Name")) {
            String data = DataFile.get().getString(player.getName() + ".Name");

            if (data.contains("%"))
                coloredName = getRainbowColoredName(player.getName(), data);
            else
                coloredName = ChatColor.translateAlternateColorCodes('&', "&" + DataFile.get().getString(player.getName() + ".Name")) + player.getName() + ChatColor.WHITE;
        }
        return coloredName;
    }

    public String getCustomColorText(Player p, String lastMessage) {
        String coloredText = ChatColor.translateAlternateColorCodes('&', "&7" + lastMessage);
        if (DataFile.get().contains(p.getName() + ".Chat")) {
            String data = DataFile.get().getString(p.getName() + ".Chat");

            if (data.contains("%"))
                coloredText = getRainbowColoredName(lastMessage, data);
            else
                coloredText = ChatColor.translateAlternateColorCodes('&',   "&" + DataFile.get().getString(p.getName() + ".Chat") + lastMessage);
        }
        return (coloredText);
    }

    public String getCurrentColorName(Player p)
    {
        if (DataFile.get().contains(p.getName() + ".Name")) {
            String data = DataFile.get().getString(p.getName() + ".Name");

            for (String key : NameColorConfig.get().getConfigurationSection("items").getKeys(false)) {
                String tempdata = NameColorConfig.get().getString("items." + key + ".data");
                if (Objects.equals(tempdata, data)) {
                    return NameColorConfig.get().getString("items." + key + ".name");
                }
            }
        }
        return "Default";
    }

    public String getCurrentColorChat(Player p)
    {
        if (DataFile.get().contains(p.getName() + ".Chat")) {
            String data = DataFile.get().getString(p.getName() + ".Chat");

            for (String key : ChatColorConfig.get().getConfigurationSection("items").getKeys(false)) {
                String tempdata = ChatColorConfig.get().getString("items." + key + ".data");
                if (Objects.equals(tempdata, data)) {
                    return ChatColorConfig.get().getString("items." + key + ".name");
                }
            }
        }
        return "Default";
    }
}
