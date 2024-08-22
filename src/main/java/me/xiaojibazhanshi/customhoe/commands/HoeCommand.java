package me.xiaojibazhanshi.customhoe.commands;

import com.sun.tools.javac.Main;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.guis.hoeitemgui.HoeItemGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HoeCommand implements CommandExecutor {

    private final PlayerDataManager playerDataManager;

    public HoeCommand(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info("Only a player can execute this command!");
            return true;
        }

        HoeItemGui gui = new HoeItemGui(playerDataManager);
        gui.openGui(player);

        return true;
    }
}
