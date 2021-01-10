package vg.civcraft.mc.civmodcore.playersettings.gui;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vg.civcraft.mc.civmodcore.command.AikarCommand;
import vg.civcraft.mc.civmodcore.playersettings.PlayerSettingAPI;

@CommandAlias("config")
public class ConfigCommand extends AikarCommand {

	@Default
	@Description("Allows configuring player specific settings")
	public void execute(Player player) {
		PlayerSettingAPI.getMainMenu().showScreen(player);
	}

}
