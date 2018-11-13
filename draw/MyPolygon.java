package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Task;
import main.midi.MIDIMessage;

public abstract class MyPolygon implements Task{
	protected float posX; //X座標
	protected float posY; //Y座標
	protected float sizeX; //横の大きさ
	protected float sizeY; //縦の大きさ
	protected Color color; //色

	private boolean isActive; //有効か無効か

	public MyPolygon(float posX, float posY, float sizeX, float sizeY, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.color = color;

		isActive = true;
	}

	@Override
	public abstract void action(ArrayList<MIDIMessage> mes);

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
