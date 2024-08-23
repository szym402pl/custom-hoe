package me.xiaojibazhanshi.customhoe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HoeCMDCompleter implements TabCompleter {


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1 && sender.isOp()) {
            return StringUtil.copyPartialMatches(args[0], List.of("reload"), new ArrayList<>());
        }

        return new ArrayList<>();
    }
}
