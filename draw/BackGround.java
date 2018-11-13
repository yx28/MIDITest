package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.MainConfig;
import main.Task;
import main.midi.MIDIMessage;

public class BackGround implements Task {
	private Color color;

	public BackGround(Color color) {
		this.color = color;
	}

	@Override
	public void action(ArrayList<MIDIMessage> mes) {}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, MainConfig.width, MainConfig.height);

	}

	@Override
	public boolean isActive() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public void setActive(boolean isActive) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
