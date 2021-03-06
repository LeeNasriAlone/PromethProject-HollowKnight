package object;

import application.Main;
import item.FullInventoryException;
import item.Item;

public abstract class Enemy extends Character {
	
	private double[] spawnLocation = new double[2]; //index 0 is X-axis, index 1 is Y-axis
	protected String currentState;
	
	protected static final double heroKnockBackX = 25;
	protected static final double heroKnockBackY = 15;
	
	public Enemy(double x, double y, double width, double height) {
		super(x, y, width, height);
		spawnLocation[0] = x;
		spawnLocation[1] = y;
	}
	
	public abstract void setMovement();
	
	public void update() {
		setMovement();
		//deal damage to hero when hit
		if (Main.hero.intersectCheck(x, y, size[0], size[1])) {
			Main.hero.attacked(attackDamage, ((Main.hero.getCenterX() < getCenterX()) ? -heroKnockBackX : heroKnockBackX), heroKnockBackY);
		}
		super.update();
	}
	
	protected void moveY() {
		if (dy > maxFallSpeed) {
			dy = maxFallSpeed;
		}
		if (dy < 0) {
			try {
				topCheck();
				inAir = true;
			} catch(HitWallException exception) {
				dy = exception.distance;
			}
		}else if (dy >= 0) {
			try {
				landingCheck();
				inAir = true;
			} catch(HitWallException exception) {
				dy = exception.distance;
				inAir = false;
			}
		}
		y += dy;
	}
	
	public void spawn() {
		reset();
		alive = true;
		hp = maxHp;
		Main.world.getChildren().add(this);
		x = spawnLocation[0];
		y = spawnLocation[1];
		turn(false);
	}
	
	protected void dropItem(Item item, double dropRate) {
		if (Math.random() <= dropRate) {
			try {
				Main.controlInventory.getInventory().addItem(item);
				Main.eventLog.addText("You have received a " + item.getName());
			} catch (FullInventoryException e) {
				Main.eventLog.addText("Your inventory is full");
			}
		}
	}
	
	public void die() {
		remove();
	}
	
	protected void reset() {
		//always turn right when reset
		turn(false);
		super.reset();
	}
	
	public void remove() {
		Main.world.getDestroyableList().remove(this);
		super.remove();
	}
	
	protected void changeSprite(String art) {
		currentState = art;
		super.changeSprite(art);
	}
	
}
