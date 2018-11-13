package main.audio;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import main.Task;
import main.midi.MIDIMessage;

public class PlayClip implements Task {
	private Clip clip;
	private boolean playing;

	private boolean isActive;

	public PlayClip(Clip clip) {
		this.clip = clip;
		playing = false;
		isActive = true;
	}

	@Override
	public void action(ArrayList<MIDIMessage> arrayList) {
		if(!playing) {
			playing = true;
			clip.start();
		}

	}

	@Override
	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
