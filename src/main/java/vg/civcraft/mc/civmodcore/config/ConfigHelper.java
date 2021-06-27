package vg.civcraft.mc.civmodcore.config;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import vg.civcraft.mc.civmodcore.inventory.items.MaterialUtils;

@UtilityClass
public final class ConfigHelper {

	/**
	 * Retrieves a string list from a given config section. If the keyed value is a standalone string instead of a
	 * list, that value will be converted to a list.
	 *
	 * @param config The config section to retrieve the list from.
	 * @param key The key to get the list of.
	 * @return Returns a list of strings, which is never null.
	 */
	public static List<String> getStringList(@Nonnull final ConfigurationSection config, @Nonnull final String key) {
		Preconditions.checkNotNull(config, "Config cannot be null!");
		Preconditions.checkNotNull(key, "Key cannot be null!");
		if (config.isString(key)) {
			final var list = new ArrayList<String>(1);
			list.add(config.getString(key));
			return list;
		}
		return config.getStringList(key);
	}

	/**
	 * Attempts to retrieve a list from a config section.
	 *
	 * @param <T> The type to parse the list into.
	 * @param config The config section.
	 * @param key The key of the list.
	 * @param parser The parser to convert the string value into the correct type.
	 * @return Returns a list, or null.
	 */
	public static <T> List<T> parseList(@Nonnull final ConfigurationSection config,
										@Nonnull final String key,
										@Nonnull final Function<String, T> parser) {
		Preconditions.checkNotNull(config, "Config cannot be null!");
		Preconditions.checkNotNull(key, "Key cannot be null!");
		Preconditions.checkNotNull(parser, "Parse cannot be null!");
		if (!config.isList(key)) {
			return null;
		}
		List<T> result = new ArrayList<>();
		for (String entry : config.getStringList(key)) {
			T item = parser.apply(entry);
			if (item != null) {
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * Attempts to retrieve a list of materials from a config section.
	 *
	 * @param config The config section.
	 * @param key The key of the list.
	 * @return Returns a list of materials, or null.
	 */
	public static List<Material> parseMaterialList(@Nonnull final ConfigurationSection config,
												   @Nonnull final String key) {
		return parseList(config, key, MaterialUtils::getMaterial);
	}

}
