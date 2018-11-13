package draw.transObj;

import java.awt.Color;
import java.awt.Graphics;

import main.Task;

public class TransCircle extends TransPolygon implements Task{

	public TransCircle(float posX, float posY, float sizeX, float sizeY, Color color, int tr, int preDelay,
			int postDelay) {
		super(posX, posY, sizeX, sizeY, color, tr, preDelay, postDelay);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(posX-sizeX/2), (int)(posY-sizeY/2), (int)sizeX, (int)sizeY);
	}


}
