package main;

import java.awt.Graphics;
import java.util.ArrayList;

import main.midi.MIDIMessage;

public interface Task {
	public void action(ArrayList<MIDIMessage> arrayList); //更新処理
	public void paint(Graphics g); //描画処理
	public boolean isActive(); //タスクの有効、無効状態を取得
	public void setActive(boolean isActive); //タスクの有効、無効状態を設定
}
