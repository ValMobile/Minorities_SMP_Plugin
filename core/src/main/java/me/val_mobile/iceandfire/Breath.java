/*
    Copyright (C) 2024  Val_Mobile

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.val_mobile.iceandfire;

import me.val_mobile.rsv.RSVPlugin;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;

public abstract class Breath extends GenericBreath {

    public Breath(EnderDragon dragon, Location target, RSVPlugin plugin) {
        super(dragon, target, plugin, BreathType.BREATH);
    }

    public Breath(Dragon dragon, Location target, RSVPlugin plugin) {
        super(dragon, target, plugin, BreathType.BREATH);
    }

    @Override
    public void performSpecialCollisionLogic() {}

    @Override
    public void performSpecialRunnableLogic() {}
}
