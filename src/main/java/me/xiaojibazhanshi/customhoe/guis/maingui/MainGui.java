package me.xiaojibazhanshi.customhoe.guis.maingui;

import dev.triumphteam.gui.guis.Gui;
import me.xiaojibazhanshi.customhoe.data.playerdata.PlayerDataManager;
import me.xiaojibazhanshi.customhoe.upgrades.UpgradeManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class MainGui extends MainGuiHelper {

    //private PlayerDataManager playerDataManager;
    //private UpgradeManager upgradeManager;

    public void openGui(Player player) {
        Gui gui = Gui.gui()
                .rows(3)
                .title(Component.text("Main Gui"))
                .create();

        gui.open(player);
    }

}
