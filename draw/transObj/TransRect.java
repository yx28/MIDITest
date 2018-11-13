package draw.transObj;

import java.awt.Color;
import java.awt.Graphics;

import main.Task;

public class TransRect extends TransPolygon implements Task {

	public TransRect(float posX, float posY, float sizeX, float sizeY, Color color, int tr, int preDelay,
			int postDelay) {
		super(posX, posY, sizeX, sizeY, color, tr, preDelay, postDelay);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect((int)(posX-sizeX/2), (int)(posY-sizeY/2), (int)sizeX, (int)sizeY);
	}

}
