package chatcolor.chatcolor.listeners;

import chatcolor.chatcolor.Configs.ChatColorConfig;
import chatcolor.chatcolor.Configs.DataFile;
import chatcolor.chatcolor.Configs.NameColorConfig;
import chatcolor.chatcolor.GUI.createGUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static chatcolor.chatcolor.GUI.GUIMethods.updateGUI;
import static chatcolor.chatcolor.GUI.createGUI.*;

public class InventoryClick implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() != null && (event.getInventory().equals(ngui.get(event.getWhoClicked().getName())) || event.getInventory().equals(cgui.get(event.getWhoClicked().getName())))) {
            event.setCancelled(true);

            if (event.getRawSlot() >= 0 && event.getRawSlot() < 45) {
                Player player = (Player) event.getWhoClicked();
                int clickedSlot = event.getRawSlot();

                if (ncolorMap.get(player.getName()).containsKey(clickedSlot)) {
                    createGUI.ColorData selectedColor = ncolorMap.get(player.getName()).get(clickedSlot);
                    if (event.getInventory().equals(ngui.get(event.getWhoClicked().getName()))) {
                        applyColorToPlayer(player, selectedColor);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',NameColorConfig.get().getString("changeMessage")) + ChatColor.translateAlternateColorCodes('&',selectedColor.name));
                        updateGUI(player, clickedSlot, ngui.get(event.getWhoClicked().getName()), ncolorMap.get(player.getName()));
                    }
                }
                if (ccolorMap.get(player.getName()).containsKey(clickedSlot)) {
                    createGUI.ColorData selectedColor = ccolorMap.get(player.getName()).get(clickedSlot);
                    if (event.getInventory().equals(cgui.get(event.getWhoClicked().getName()))) {
                        applyColorToChat(player, selectedColor);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',ChatColorConfig.get().getString("changeMessage")) + ChatColor.translateAlternateColorCodes('&',selectedColor.name));
                        updateGUI(player, clickedSlot, cgui.get(event.getWhoClicked().getName()), ccolorMap.get(player.getName()));
                    }
                }
            }
        }
    }

    private void applyColorToChat(Player p, ColorData colorData)
    {
        DataFile.get().set(p.getName() + ".Chat", colorData.data);
        DataFile.save();
    }

    public static void applyColorToPlayer(Player p, ColorData colorData) {
        String playerName = p.getName();
        String coloredName;

        if (colorData.isRainbow()) {
            coloredName = getRainbowColoredName(playerName, colorData.data);
        } else {
            coloredName = ChatColor.translateAlternateColorCodes('&', "&" + colorData.data) + playerName + ChatColor.WHITE;
        }
        p.setDisplayName(coloredName);
        DataFile.get().set(p.getName() + ".Name", colorData.data);
        DataFile.save();
    }

    public static String getRainbowColoredName(String playerName, String data) {
        StringBuilder coloredName = new StringBuilder();
        String[] colorCodes = data.split("%");
        int colorCodeIndex = 0;
        for (char c : playerName.toCharArray()) {
            if (c != ChatColor.COLOR_CHAR) {
                coloredName.append(ChatColor.COLOR_CHAR).append(colorCodes[colorCodeIndex % colorCodes.length]).append(c);
                colorCodeIndex++;
            } else {
                coloredName.append(c);
            }
        }

        coloredName.append(ChatColor.WHITE);
        return coloredName.toString();
    }
}
