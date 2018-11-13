package draw.fxObj;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Task;
import main.midi.MIDIMessage;

/**時間経過で消滅する物体を複数管理する。
 * ここでは、円形に拡散するように図形を放出する。
 * */
public class FXGadget01 extends FXPolygon implements Task {
	protected int num; //生成する図形の数
	protected FXPolygon[] polygons; //生成する図形のオブジェクト

	public FXGadget01(float pos0X, float pos0Y, float vel0, float dir0, int time, int num) {
		this.num = num;
		FXPolygon[] array = new FXPolygon[num];
		//円形に放出するように四角形を生成
		for(int i = 0; i < num; i++) {
			array[i] = new FXRect(pos0X, pos0Y, vel0, (float)(Math.PI * 2 / num * i + dir0), time, 50, 0, 10, Color.WHITE);
		}
		polygons = array;
		super.setActive(false); //初期状態では非アクティブ
	}

	@Override
	public void action(ArrayList<MIDIMessage> mes) {
		//保持する図形を処理
		//全ての図形が非アクティブならこのオブジェクトも非アクティブに
		boolean act = false;
		for(int i = 0; i < num; i++) {
			if(polygons[i].isActive()) {
				polygons[i].action(mes);
				act = true;
			}
		}
		super.setActive(act);
	}
	@Override
	public void reset(float posX, float posY) {
		reset(posX, posY, vel0, dir0);
	}
	@Override
	public void reset(float dir) {
		reset(pos0X, pos0Y, vel0, dir);
	}
	@Override
	public void reset(float posX, float posY, float vel, float dir) {
		super.setActive(true); //このオブジェクトをアクティブに
		//図形を再生成
		for(int i = 0; i < num; i++) {
			polygons[i].reset(posX, posY);
		}
	}

	@Override
	public void paint(Graphics g) {
		//保持する図形を描画
		for(int i = 0; i < num; i++) {
			if(polygons[i].isActive()) {polygons[i].paint(g);}
		}
	}

}
