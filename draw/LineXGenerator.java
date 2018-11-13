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
public class LineXGenerator implements Task {
	protected float centerX;
	protected float centerY;
	protected float size;
	protected int tr;

	protected FXPolygon[] polygons;
	protected float[] disX;

	protected float posX;

	private boolean isActive;
	private int n;

	public LineXGenerator(float centerX, float centerY, float size, int tr) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.size = size;
		this.polygons = null;
		this.tr = tr;
		this.disX = null;
		posX = centerX;
		isActive = true;
		n = 0;
	}

	public LineXGenerator generateRect(int num) {
		FXPolygon[] array = new FXPolygon[num];
		disX = new float[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXRect(posX, centerY, 5, (float) (Math.PI * 2 * Math.random()), 20, 30, 0, 10, Color.WHITE);
		}
		polygons = array;
		return this;
	}
	public LineXGenerator generateCircle(int num) {
		FXPolygon[] array = new FXPolygon[num];
		disX = new float[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXCircle(posX, centerY, 20, (float) (Math.PI * 2 / 4), 20, 50, Color.WHITE);
		}
		polygons = array;
		return this;
	}
	public LineXGenerator generateGagdet(int num, int innerNum){
		FXPolygon[] array = new FXPolygon[num];
		disX = new float[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXGadget01(posX, centerY, 20, (float)(Math.PI * 2 / num * i), 20, innerNum);
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
				disX[n] = token.getNote();
				return true;
			}
		}
		return false;
	}
	protected void resetPolygon(int index) {
		polygons[index].reset(centerX + disX[index] * size, centerY);
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
