package chatcolor.chatcolor.listeners;

import chatcolor.chatcolor.Configs.ChatColorConfig;
import chatcolor.chatcolor.Configs.DataFile;
import chatcolor.chatcolor.Configs.NameColorConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static chatcolor.chatcolor.PluginAPIs.ChatColorPlaceholderHook.lastMessages;


public class PlayerMessageListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void OnPlayerMessage(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        lastMessages.put(p, e.getMessage());
    }
}
