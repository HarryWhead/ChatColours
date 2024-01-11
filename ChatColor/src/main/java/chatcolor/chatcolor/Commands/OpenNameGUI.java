package chatcolor.chatcolor.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static chatcolor.chatcolor.GUI.createGUI.ngui;
public class OpenNameGUI implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player p = (Player) sender;
            p.openInventory(ngui.get(p.getName()));
        }
        return true;
    }
}
