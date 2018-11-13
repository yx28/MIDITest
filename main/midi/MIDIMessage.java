package main.midi;

public class MIDIMessage {
	private int track; //トラック番号
	private int key; //キー
	private int length; //ノートの始点ならtrue、発音中ならfalse

	public MIDIMessage(int track, int note, int length) {
		super();
		this.track = track;
		this.key = note;
		this.length = length;
	}

	public int getChannel() {
		return track;
	}

	public int getNote() {
		return key;
	}

	public int getLength() {
		return length;
	}

	public String toString() {
		return "トラック" + track + " " + key + " ";
	}

}
