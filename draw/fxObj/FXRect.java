package draw.fxObj;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Task;
import main.midi.MIDIMessage;

/**時間経過で移動＆消滅する四角形。
 */
public class FXRect extends FXPolygon implements Task {
	protected float angle;
	protected float angVel;
	protected float size;
	protected int time;

	protected int red, green, blue;


	public FXRect(float posX, float posY, float vel, float dir, int time, float size, float angle, float angVel, Color color) {
		super(posX, posY, vel, dir, time);
		this.angle = angle;
		this.angVel = angVel;
		this.size = size;
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
	}
	public FXRect() {
		this(0, 0, 0, 0, 20, 50, 0, 10, Color.WHITE);
	}

	@Override
	public void action(ArrayList<MIDIMessage> arrayList) {
		angle += angVel;
		disappear();
	}

	@Override
	public void paint(Graphics g) {
		double radian = Math.toRadians(angle);
		int[] x = {
				(int)(posX + Math.cos(radian) * size),
				(int)(posX + Math.cos(radian + Math.PI * 0.5) * size),
				(int)(posX + Math.cos(radian + Math.PI) * size),
				(int)(posX + Math.cos(radian + Math.PI * 1.5) * size)
		};
		int[] y = {
				(int)(posY + Math.sin(radian) * size),
				(int)(posY + Math.sin(radian + Math.PI * 0.5) * size),
				(int)(posY + Math.sin(radian + Math.PI) * size),
				(int)(posY + Math.sin(radian + Math.PI * 1.5) * size)
		};
		g.setColor(new Color(red, green, blue, (int)alpha));
		g.drawPolygon(x, y, 4);

	}

}
