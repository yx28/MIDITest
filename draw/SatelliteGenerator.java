package draw;

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
public class SatelliteGenerator implements Task {
	protected float centerX;
	protected float centerY;
	protected float vel;
	protected float radius;
	protected float angle0;
	protected int tr;

	protected FXPolygon[] polygons;

	protected float posX;
	protected float posY;
	protected float angle;
	protected float sine;
	protected float radian;

	private boolean isActive;
	private int n;

	public SatelliteGenerator(float centerX, float centerY, float vel, float radius, float angle0, int tr) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.vel = vel;
		this.radius = radius;
		this.angle0 = angle0;
		this.angle = angle0;
		this.polygons = null;
		this.tr = tr;
		posX = centerX;
		posY = centerY;
		radian = 0;
		isActive = true;
		n = 0;
	}

	public SatelliteGenerator generateRect(int num) {
		FXPolygon[] array = new FXPolygon[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXRect();
		}
		polygons = array;
		return this;
	}
	public SatelliteGenerator generateCircle(int num) {
		FXPolygon[] array = new FXPolygon[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXCircle();
		}
		polygons = array;
		return this;
	}
	public SatelliteGenerator generateGagdet(int num, int innerNum){
		FXPolygon[] array = new FXPolygon[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXGadget01(posX, posY, 20, (float)(Math.PI * 2 / num * i), 20, innerNum);
		}
		polygons = array;
		return this;
	}

	@Override
	public void action(ArrayList<MIDIMessage> mes) {
		posX = (float)(centerX + Math.cos(Math.toRadians(angle)) * radius);
		posY = (float)(centerY + Math.sin(Math.toRadians(angle)) * radius);
		angle = (float)(angle0 + Math.sin(radian) * 400);
		radian += vel;

		if(polygons != null) {
			boolean flug = mesAnalysis(mes, tr);
			if(flug && !polygons[n].isActive()) {
				resetPolygon(polygons[n]);
				n++;
				if(n >= polygons.length) {n = 0;}
				flug = false;
			}
			if(polygons != null) {
				for(int i = 0; i< polygons.length; i++) {
					if(polygons[i].isActive()) {polygons[i].action(mes);}
					if(flug) {
						if(!polygons[i].isActive()) {
							resetPolygon(polygons[i]);
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
				return true;
			}
		}
		return false;
	}
	protected void resetPolygon(FXPolygon polygon) {
		polygon.reset(posX, posY);
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
