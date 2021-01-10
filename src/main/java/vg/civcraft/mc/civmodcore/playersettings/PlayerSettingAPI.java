package vg.civcraft.mc.civmodcore.playersettings;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import vg.civcraft.mc.civmodcore.CivModCorePlugin;
import vg.civcraft.mc.civmodcore.playersettings.gui.MenuOption;
import vg.civcraft.mc.civmodcore.playersettings.gui.MenuSection;
import vg.civcraft.mc.civmodcore.playersettings.impl.AltConsistentSetting;

/**
 * Allows creating settings, which will automatically be available in players
 * configuration GUI
 *
 */
public final class PlayerSettingAPI {

	private static final Map<String, PlayerSetting<?>> SETTINGS_BY_IDENTIFIER = new HashMap<>();

	private static final Map<String, List<PlayerSetting<?>>> SETTINGS_BY_PLUGIN = new HashMap<>();

	private static final MenuSection MAIN_MENU = new MenuSection("Config", "", null);
	
	private PlayerSettingAPI() { }

	/**
	 * @return GUI main menu
	 */
	public static MenuSection getMainMenu() {
		return MAIN_MENU;
	}

	/**
	 * Gets a setting by its identifier
	 * 
	 * @param identifier Identifier to get setting for
	 * @return Setting with the given identifier or null if no such setting exists
	 */
	public static PlayerSetting<?> getSetting(String identifier) {
		return SETTINGS_BY_IDENTIFIER.get(identifier);
	}

	/**
	 * Settings must be registered on every startup to be available. Identifiers
	 * must be unique globally.
	 * 
	 * If a setting had values assigned but is not registered on startup its old
	 * values will be left alone.
	 * 
	 * @param setting Setting to register
	 * @param menu Menu in which this value will appear
	 */
	public static void registerSetting(PlayerSetting<?> setting, MenuSection menu) {
		Preconditions.checkArgument(setting != null, "Player setting cannot be null.");
		if (setting instanceof AltConsistentSetting) {
			if (setting.canBeChangedByPlayer()) {
				menu.addItem(new MenuOption(menu, setting));
			}
			menu = null;
			setting = ((AltConsistentSetting<?,?>) setting).getWrappedSetting();
		}
		List<PlayerSetting<?>> pluginSettings = SETTINGS_BY_PLUGIN.computeIfAbsent(
				setting.getOwningPlugin().getName(),
				k -> new ArrayList<>());
		Preconditions.checkArgument(!pluginSettings.contains(setting),
				"Cannot register the same player setting twice.");
		SETTINGS_BY_IDENTIFIER.put(setting.getIdentifier(), setting);
		pluginSettings.add(setting);
		if (menu != null && setting.canBeChangedByPlayer()) {
			menu.addItem(new MenuOption(menu, setting));
		}
	}

}
