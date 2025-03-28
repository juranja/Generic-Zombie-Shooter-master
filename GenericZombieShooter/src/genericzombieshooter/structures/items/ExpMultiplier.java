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
 * Used to increase the player's experience multiplier.
 * @author Darin Beaudreau
 */
public class ExpMultiplier extends Item {
    // Final Variables
    public static final int ID = 6;
    public static final String EFFECT_NAME = "Exp Multiplier";
    public static final long ITEM_DURATION = 15 * 1000;
    public static final long DURATION = 20 * 1000;
    
    public ExpMultiplier(Point2D.Double p) {
        super(ExpMultiplier.ID, ExpMultiplier.EFFECT_NAME, Images.EXP_MULTIPLIER, ExpMultiplier.DURATION);
        this.x = p.x;
        this.y = p.y;
    }
    
    @Override
    public void applyEffect(Player p) {
        p.addStatusEffect(ExpMultiplier.ID, ExpMultiplier.EFFECT_NAME, Images.EXP_MULTIPLIER, ExpMultiplier.DURATION, 0);
        synchronized(Globals.GAME_MESSAGES) { Globals.GAME_MESSAGES.add(new Message("Player experience doubled for " + (ExpMultiplier.DURATION / 1000) +" seconds!", 5000)); }
        Sounds.POWERUP.play();
    }
}
