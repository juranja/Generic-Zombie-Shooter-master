/**
    This file is part of Generic Zombie Shooter.

    Generic Zombie Shooter is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Generic Zombie Shooter is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Generic Zombie Shooter.  If not, see <http://www.gnu.org/licenses/>.
 **/
package genericzombieshooter.structures.items;

import genericzombieshooter.actors.Player;
import genericzombieshooter.misc.Globals;
import genericzombieshooter.misc.Images;
import genericzombieshooter.misc.Sounds;
import genericzombieshooter.structures.Item;
import genericzombieshooter.structures.Message;
import java.awt.geom.Point2D;

/**
 * Used to restore ammo to the player's weapons.
 * @author Darin Beaudreau
 */
public class Ammo extends Item {
    public static final int ID = 2;
    public static final long SPAWN_TIME = 40 * 1000;
    public static final long DURATION = 15 * 1000;
    private String weapon;
    private int ammoCount;
    
    public Ammo(String weapon, int ammoCount, Point2D.Double p) {
        super(Ammo.ID, "Ammo Pack", Images.AMMO_PACK, Ammo.DURATION);
        this.weapon = weapon;
        this.ammoCount = ammoCount;
        this.x = p.x;
        this.y = p.y;
    }
    
    @Override
    public void applyEffect(Player player) {
        if(player.hasWeapon(this.weapon)) player.getWeapon(this.weapon).addAmmo(this.ammoCount);
        synchronized(Globals.GAME_MESSAGES) {
            Globals.GAME_MESSAGES.add(new Message((this.ammoCount + " ammo for " + this.weapon + " found!"), 5000));
        }
        Sounds.POWERUP.play();
    }
}
