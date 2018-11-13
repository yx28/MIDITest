package draw.fxObj;

import java.awt.Graphics;
import java.util.ArrayList;

import main.Task;
import main.midi.MIDIMessage;

/**時間経過で移動＆消滅するポリゴン。
 */
public abstract class FXPolygon implements Task {
	protected float pos0X;
	protected float pos0Y;
	protected float vel0;
	protected float dir0;
	protected int time;

	protected float alpha;
	protected float posX;
	protected float posY;
	protected float vel;
	protected float dir;
	protected float acc;

	private boolean isActive;

	public FXPolygon(float pos0X, float pos0Y, float vel0, float dir0, int time) {
		this.pos0X = pos0X;
		this.pos0Y = pos0Y;
		this.vel0 = vel0;
		this.dir0 = dir0;
		this.time = time;
		reset();
		isActive = false;
	}
	public FXPolygon() {
		this(0, 0, 0, 0, 20);
	}

	@Override
	public void action(ArrayList<MIDIMessage> mes) {
		disappear();
	};

	protected void disappear() {
		posX += Math.cos(dir) * vel;
		posY += Math.sin(dir) * vel;
		vel -= acc;
		alpha -= 255/time;
		if(alpha < 0) {
			isActive = false;
		}
	}

	public void reset() {
		reset(pos0X, pos0Y, vel0, dir0);
	}
	public void reset(float dir) {
		reset(pos0X, pos0Y, vel0, dir);
	}
	public void reset(float posX, float posY) {
		reset(posX, posY, vel0, dir0);
	}
	public void reset(float posX, float posY, float vel, float dir) {
		alpha = 255;
		this.posX = posX;
		this.posY = posY;
		this.vel = vel;
		this.dir = dir;
		acc = vel / time;
		isActive = true;
	}

	@Override
	public abstract void paint(Graphics g);

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


}
