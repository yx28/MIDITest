package draw.fxObj;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Task;
import main.midi.MIDIMessage;

/**時間経過で移動＆消滅する四角形。
 */
public class FXCircle extends FXPolygon implements Task {
	protected float size;
	protected int time;

	protected int red, green, blue;


	public FXCircle(float posX, float posY, float vel, float dir, int time, float size, Color color) {
		super(posX, posY, vel, dir, time);
		this.size = size;
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
	}
	public FXCircle() {
		this(0, 0, 0, 0, 20, 50, Color.WHITE);
	}

	@Override
	public void action(ArrayList<MIDIMessage> arrayList) {
		disappear();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(red, green, blue, (int)alpha));
		g.drawOval((int)posX, (int)posY, (int)size, (int)size);

	}

}
