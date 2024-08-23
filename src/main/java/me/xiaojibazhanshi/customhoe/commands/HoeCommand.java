package me.xiaojibazhanshi.customhoe.commands;

import com.sun.tools.javac.Main;
import me.xiaojibazhanshi.customhoe.CustomHoe;
import me.xiaojibazhanshi.customhoe.data.config.ConfigManager;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.guis.hoeitemgui.HoeItemGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HoeCommand implements CommandExecutor {

    private final CustomHoe instance;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;

    public HoeCommand(CustomHoe instance) {
        this.instance = instance;
        this.playerDataManager = instance.getPlayerDataManager();
        this.configManager = instance.getConfigManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info("Only a player can execute this command!");
            return true;
        }

        if (args.length == 1
            && args[0].equalsIgnoreCase("reload")
            && player.isOp()) {

            configManager.reload(instance);
            player.sendMessage(ChatColor.GREEN + "Successfully reloaded the config!");

        } else {
            HoeItemGui gui = new HoeItemGui(playerDataManager);
            gui.openGui(player);
        }

        return true;
    }
}
