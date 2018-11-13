package main.midi;

import java.util.ArrayList;

public class Source {
	ArrayList<ArrayList<MIDIMessage>> mesList;
	int count;

	public Source() {
		MIDIAnalysis midiAnalysis = new MIDIAnalysis();
		midiAnalysis.analysis("Practice.mid");
		mesList = midiAnalysis.getList();
		count = 0;
	}

	public ArrayList<MIDIMessage> getMessage() {
		ArrayList<MIDIMessage> mes = mesList.get(count);
		//System.out.println(mes);
		if(count % 2 == 0) {
			mes.add(new MIDIMessage(30, 0, 1));
		}
//		if(count % 13 == 0) {
//			mes.add(new MIDIMessage(1, 0, 1));
//		}
		count++;
			return mes;
	}
}
