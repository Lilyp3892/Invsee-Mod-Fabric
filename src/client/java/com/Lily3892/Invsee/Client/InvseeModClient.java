package com.Lily3892.Invsee.Client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

public class InvseeModClient implements ClientModInitializer {
	private static KeyBinding keyBinding;
	public void onInitializeClient() {
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				null,
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_END,
				"Invsee Mod"
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// Get the targeted entity
			if (keyBinding.wasPressed()) {
				LivingEntity target = (LivingEntity) client.targetedEntity;
				if (target != null) {
					var nbtcopy = new NbtCompound();
					var helmetDurability = (target.getEquippedStack(EquipmentSlot.HEAD).getMaxDamage() - target.getEquippedStack(EquipmentSlot.HEAD).getDamage()) + "/" + (target.getEquippedStack(EquipmentSlot.HEAD).getMaxDamage());
					var chestplateDurability = (target.getEquippedStack(EquipmentSlot.CHEST).getMaxDamage() - target.getEquippedStack(EquipmentSlot.CHEST).getDamage()) + "/" + (target.getEquippedStack(EquipmentSlot.CHEST).getMaxDamage());
					var leggingsDurability = (target.getEquippedStack(EquipmentSlot.LEGS).getMaxDamage() - target.getEquippedStack(EquipmentSlot.LEGS).getDamage()) + "/" + (target.getEquippedStack(EquipmentSlot.LEGS).getMaxDamage());
					var bootsDurability = (target.getEquippedStack(EquipmentSlot.FEET).getMaxDamage() - target.getEquippedStack(EquipmentSlot.FEET).getDamage()) + "/" + (target.getEquippedStack(EquipmentSlot.FEET).getMaxDamage());
					var MainhandDurability = (target.getEquippedStack(EquipmentSlot.MAINHAND).getMaxDamage() - target.getEquippedStack(EquipmentSlot.MAINHAND).getDamage()) + "/" + (target.getEquippedStack(EquipmentSlot.MAINHAND).getMaxDamage());
					var OffhandDurability = (target.getEquippedStack(EquipmentSlot.OFFHAND).getMaxDamage() - target.getEquippedStack(EquipmentSlot.OFFHAND).getDamage()) + "/" + (target.getEquippedStack(EquipmentSlot.OFFHAND).getMaxDamage());
					SimpleInventory shulker = new SimpleInventory(9);

					for (int i = 0; i < 6; i++) {
						if(i==0) {
							target.getEquippedStack(EquipmentSlot.HEAD).writeNbt(nbtcopy);
							shulker.setStack(i, ItemStack.fromNbt(nbtcopy));
						} else if (i==1) {
							target.getEquippedStack(EquipmentSlot.CHEST).writeNbt(nbtcopy);
							shulker.setStack(i, ItemStack.fromNbt(nbtcopy));
						} else if (i==2) {
							target.getEquippedStack(EquipmentSlot.LEGS).writeNbt(nbtcopy);
							shulker.setStack(i, ItemStack.fromNbt(nbtcopy));
						} else if (i==3) {
							target.getEquippedStack(EquipmentSlot.FEET).writeNbt(nbtcopy);							shulker.setStack(i, ItemStack.fromNbt(nbtcopy));
							shulker.setStack(i, ItemStack.fromNbt(nbtcopy));
						} else if (i==4) {
							target.getEquippedStack(EquipmentSlot.MAINHAND).writeNbt(nbtcopy);
							shulker.setStack(i+1, ItemStack.fromNbt(nbtcopy));
						} else {
							target.getEquippedStack(EquipmentSlot.OFFHAND).writeNbt(nbtcopy);
							shulker.setStack(i+1, ItemStack.fromNbt(nbtcopy));
						}
						nbtcopy = new NbtCompound();
					}
					String name  = target.getName().getContent().toString();
					name = name.replaceAll(".*\\{", "").replaceAll("}.*", "");
					if (name.length() >= 5 && name.substring(0, 5).equals("key='")) {
						int closingQuoteIndex = name.indexOf("'", 5);
						if (closingQuoteIndex != -1) {
							name = name.substring(5, closingQuoteIndex);
						}
					}

					target.writeNbt(nbtcopy);
					System.out.println("NBT: " + name);
					System.out.println(nbtcopy);
					System.out.println("NBT: " + name);
					nbtcopy = new NbtCompound();

					GenericContainerScreen screen = new GenericContainerScreen(new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, 69420, client.player.getInventory(), shulker, 1), client.player.getInventory(), Text.translatable(name));

					client.setScreen(screen);
					// Print the armor durability to the console
					System.out.println("Helmet Durability: " + helmetDurability);
					System.out.println(target.getEquippedStack(EquipmentSlot.HEAD).writeNbt(nbtcopy));
					System.out.println("Chestplate Durability: " + chestplateDurability);
					System.out.println(target.getEquippedStack(EquipmentSlot.CHEST).writeNbt(nbtcopy));
					System.out.println("Leggings Durability: " + leggingsDurability);
					System.out.println(target.getEquippedStack(EquipmentSlot.LEGS).writeNbt(nbtcopy));
					System.out.println("Boots Durability: " + bootsDurability);
					System.out.println(target.getEquippedStack(EquipmentSlot.FEET).writeNbt(nbtcopy));
					System.out.println("Main hand Durability: " + MainhandDurability);
					System.out.println(target.getEquippedStack(EquipmentSlot.MAINHAND).writeNbt(nbtcopy));
					System.out.println("Off hand Durability: " + OffhandDurability);
					System.out.println(target.getEquippedStack(EquipmentSlot.OFFHAND).writeNbt(nbtcopy));

					// Send a chat message with the armor durability to the player
					boolean printDurability = false;
					if (printDurability) {
						client.player.sendMessage(Text.literal("Helmet Durability: " + helmetDurability), false);
						client.player.sendMessage(Text.literal("Chestplate Durability: " + chestplateDurability), false);
						client.player.sendMessage(Text.literal("Leggings Durability: " + leggingsDurability), false);
						client.player.sendMessage(Text.literal("Boots Durability: " + bootsDurability), false);
						client.player.sendMessage(Text.literal("Main hand Durability: " + MainhandDurability), false);
						client.player.sendMessage(Text.literal("Off hand Durability: " + OffhandDurability), false);
					}
					File file = Paths.get(URI.create(FabricLoader.getInstance().getGameDir().toUri() + "/logs/latest.log")).toFile();
					MutableText text = Text.literal("Data sent to " + (String)file.getName()).formatted(Formatting.UNDERLINE).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath())));
					client.player.sendMessage(text, false);
				}
			}
		});
	}
	
}