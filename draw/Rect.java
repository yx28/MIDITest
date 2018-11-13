package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Task;
import main.midi.MIDIMessage;

public class Rect extends MyPolygon implements Task{

	public Rect(float posX, float posY, float sizeX, float sizeY, Color color) {
		super(posX, posY, sizeX, sizeY, color);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	@Override
	public void action(ArrayList<MIDIMessage> mes) {}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect((int)(posX-sizeX/2), (int)(posY-sizeY/2), (int)sizeX, (int)sizeY); //描画
	}

}
