package draw;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import draw.fxObj.FXCircle;
import draw.fxObj.FXGadget01;
import draw.fxObj.FXPolygon;
import draw.fxObj.FXRect;
import main.Task;
import main.midi.MIDIMessage;

/**速度変化しつつ衛星のような軌道で動くジェネレータ。
 * 移動に合わせていろいろな図形やガジェットを生成できる。
 */
public class LineYGenerator implements Task {
	protected float centerX;
	protected float centerY;
	protected float size;
	protected int tr;

	protected FXPolygon[] polygons;
	protected float[] disY;

	private boolean isActive;
	private int n;

	public LineYGenerator(float centerX, float centerY, float size, int tr) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.size = size;
		this.polygons = null;
		this.tr = tr;
		this.disY = null;
		isActive = true;
		n = 0;
	}

	public LineYGenerator generateRect(int num) {
		FXPolygon[] array = new FXPolygon[num];
		disY = new float[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXRect();
		}
		polygons = array;
		return this;
	}
	public LineYGenerator generateCircle(int num) {
		FXPolygon[] array = new FXPolygon[num];
		disY= new float[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXCircle(centerX, centerY, 20, 0, 20, 50, Color.WHITE);
		}
		polygons = array;
		return this;
	}
	public LineYGenerator generateGagdet(int num, int innerNum){
		FXPolygon[] array = new FXPolygon[num];
		disY = new float[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXGadget01(centerX, centerY, 20, (float)(Math.PI * 2 / num * i), 20, innerNum);
		}
		polygons = array;
		return this;
	}

	@Override
	public void action(ArrayList<MIDIMessage> mes) {

		if(polygons != null) {
			boolean flug = mesAnalysis(mes, tr);
			if(flug && !polygons[n].isActive()) {
				resetPolygon(n);
				n++;
				if(n >= polygons.length) {n = 0;}
				flug = false;
			}
			if(polygons != null) {
				for(int i = 0; i< polygons.length; i++) {
					if(polygons[i].isActive()) {polygons[i].action(mes);}
					if(flug) {
						if(!polygons[i].isActive()) {
							resetPolygon(i);
							flug = false;
						}
					}
				}
			}
		}
	}
	//対応するチャンネルのMIDIメッセージが来ているか判定
	protected boolean mesAnalysis(ArrayList<MIDIMessage> mes, int tr) {
		if(mes == null) {return false;}
		for(MIDIMessage token : mes) {
			if(token.getChannel() == tr) {
				disY[n] = token.getNote();
				return true;
			}
		}
		return false;
	}
	protected void resetPolygon(int index) {
		polygons[index].reset(centerX, centerY + disY[index] * size);
	}

	@Override
	public void paint(Graphics g) {
		if(polygons != null) {
			for(Task polygon : polygons) {
				if(polygon.isActive()) {polygon.paint(g);}
			}
		}
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
