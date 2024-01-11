package chatcolor.chatcolor.listeners;

import chatcolor.chatcolor.Configs.ChatColorConfig;
import chatcolor.chatcolor.Configs.DataFile;
import chatcolor.chatcolor.Configs.NameColorConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static chatcolor.chatcolor.GUI.createGUI.*;
import static chatcolor.chatcolor.listeners.InventoryClick.getRainbowColoredName;

public class PlayerJoinlistener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerJoin(PlayerJoinEvent e)
    {
        ChatColorConfig.reload();
        NameColorConfig.reload();
        DataFile.reload();

        Player p = e.getPlayer();
        if (!cgui.containsKey(p.getName())) {
            GUI(Bukkit.createInventory(null, ChatColorConfig.get().getInt("guiSize"), ChatColor.translateAlternateColorCodes('&', ChatColorConfig.get().getString("guiTitle"))), p, ChatColorConfig.get(), "c");
        }

        if (!ngui.containsKey(p.getName())) {
            GUI(Bukkit.createInventory(null, NameColorConfig.get().getInt("guiSize"), ChatColor.translateAlternateColorCodes('&', NameColorConfig.get().getString("guiTitle"))), p, NameColorConfig.get(), "n");
        }

        if (DataFile.get().contains(p.getName() + ".Name")) {
            String data = DataFile.get().getString(p.getName() + ".Name");
            String coloredName;

            if (data.contains("%"))
                coloredName = getRainbowColoredName(p.getName(), data);
            else
                coloredName = ChatColor.translateAlternateColorCodes('&', "&" + DataFile.get().getString(p.getName() + ".Name")) + p.getName() + ChatColor.WHITE;

            p.setDisplayName(coloredName);
        }
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        cgui.remove(p.getName());
        ccolorMap.remove(p.getName());

        ngui.remove(p.getName());
        ncolorMap.remove(p.getName());
    }
}
