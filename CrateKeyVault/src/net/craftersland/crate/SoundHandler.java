package net.craftersland.crate;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundHandler {
	
	@SuppressWarnings("unused")
	private CKV pl;
	
	public SoundHandler(CKV pl) {
		this.pl = pl;
	}
	
	public void sendOpenedChestSound(Player p) {
		if (CKV.oldSounds == false) {
			p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 2F, 2F);
		} else {
			p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_OPEN"), 2F, 2F);
		}
	}
	
	public void sendClosedChestSound(Player p) {
		if (CKV.oldSounds == false) {
			p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 2F, 2F);
		} else {
			p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_CLOSE"), 2F, 2F);
		}
	}
	
	public void sendFailedSound(Player p) {
		if (CKV.oldSounds == false) {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2F, 2F);
		} else {
			p.playSound(p.getLocation(), Sound.valueOf("BLOCK_NOTE_PLING"), 2F, 2F);
		}
	}
	
	public void sendCompleteSound(Player p) {
		if (CKV.oldSounds == false) {
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2F, 2F);
		} else {
			p.playSound(p.getLocation(), Sound.valueOf("ENTITY_PLAYER_LEVELUP"), 2F, 2F);
		}
	}
	
	public void sendConfirmSound(Player p) {
		if (CKV.oldSounds == false) {
			p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2F, 2F);
		} else {
			p.playSound(p.getLocation(), Sound.valueOf("ENTITY_ARROW_HIT_PLAYER"), 2F, 2F);
		}
	}

}
