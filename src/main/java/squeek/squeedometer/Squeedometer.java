package squeek.squeedometer;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;
import squeek.squeedometer.client.SqueedometerHud;
import squeek.squeedometer.config.ConfigWrapper;
import squeek.squeedometer.config.SqueedometerConfig;

public class Squeedometer implements ClientModInitializer {

	public static SqueedometerHud squeedometerHud;

	@Override
	public void onInitializeClient() {
		System.out.println("[Squeedometer] Loaded");

		AutoConfig.register(SqueedometerConfig.class, JanksonConfigSerializer::new);
		ConfigWrapper.loadConfig();
		squeedometerHud = new SqueedometerHud();

		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT,
			Identifier.of("squeedometer", "speed"),
			(context, tickCounter) -> {
				if (ConfigWrapper.config.enabled) {
					Squeedometer.squeedometerHud.draw(context, tickCounter);
				}
			}
		);
	}
}
