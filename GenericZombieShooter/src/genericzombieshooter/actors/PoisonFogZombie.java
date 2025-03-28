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
package genericzombieshooter.actors;

import genericzombieshooter.misc.Globals;
import genericzombieshooter.misc.Images;
import genericzombieshooter.misc.Sounds;
import genericzombieshooter.structures.Animation;
import genericzombieshooter.structures.Explosion;
import genericzombieshooter.structures.items.Invulnerability;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Used to represent poison fog zombies, which explode into a poisonous gas cloud
 * when killed, which poisons the player on contact.
 * @author Darin Beaudreau
 */
public class PoisonFogZombie extends Zombie {
    // Final Variables
    private static final int EXP_VALUE = 100;
    private static final int POISON_DAMAGE = 5;
    // Member Variables
    private Explosion explosion;
    private boolean exploded;
    private boolean damageDone;
    
    public PoisonFogZombie(Point2D.Double p_, int health_, int damage_, double speed_, int score_, Animation animation_) {
        super(p_, Globals.ZOMBIE_POISONFOG_TYPE, health_, damage_, speed_, score_, PoisonFogZombie.EXP_VALUE, animation_);
        this.explosion = new Explosion(Images.POISON_GAS_SHEET, new Point2D.Double(this.x, this.y));
        this.exploded = false;
        this.damageDone = false;
    }
    
    @Override
    public boolean isDead() {
        // If the zombie has exploded and the explosion animation is no longer active, return true.
        return (this.exploded && !this.explosion.getImage().isActive());
    }
    
    @Override
    public int getDamage() {
        if(!isDead()) return super.getDamage();
        else return 0;
    }
    
    private boolean collidesWithExplosion(Rectangle2D.Double rect) {
        int width = this.explosion.getSize().width;
        int height = this.explosion.getSize().height;
        Rectangle2D.Double expRect = new Rectangle2D.Double((this.explosion.x - (width / 2)), 
                                                            (this.explosion.y - (height / 2)), 
                                                            width, height);
        return rect.intersects(expRect);
    }
    
    @Override
    public void update(Player player, List<Zombie> zombies) {
        // If the zombie has been shot and killed, set exploded to true.
        if(this.getHealth() <= 0 && !this.exploded) {
            this.exploded = true;
            this.explosion = new Explosion(Images.POISON_GAS_SHEET, new Point2D.Double(this.x, this.y));
            Sounds.POISONCLOUD.play();
        }
        if(this.exploded) { // Otherwise...
            // Update the explosion animation and damage the player if he is touching it.
            if(collidesWithExplosion(player) && !this.damageDone) {
                if(!player.hasEffect(Invulnerability.EFFECT_NAME)) {
                    player.addStatusEffect(6, "Poison", Images.POISON_STATUS_ICON, 
                            (15 * 1000), PoisonFogZombie.POISON_DAMAGE);
                    this.damageDone = true;
                }
            }
            if(this.explosion.getImage().isActive()) this.explosion.getImage().update();
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if(!this.exploded) {
            // If the zombie has not been shot yet, continue drawing the zombie.
            super.draw(g2d);
        } else {
            // Otherwise, draw the explosion.
            if(this.explosion.getImage().isActive()) this.explosion.draw(g2d);
        }
    }
    
    @Override
    public void moan(Player player) {
        if(!this.moaned) {
            if(Globals.gameTime.getElapsedMillis() >= this.nextMoan) {
                double xD = player.getCenterX() - this.x;
                double yD = player.getCenterY() - this.y;
                double dist = Math.sqrt((xD * xD) + (yD * yD));
                double gain = 1.0 - (dist / Player.AUDIO_RANGE);
                Sounds.MOAN4.play(gain);
                this.moaned = true;
            }
        }
    }
}
