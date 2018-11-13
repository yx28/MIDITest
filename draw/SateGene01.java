package draw;

import draw.fxObj.FXGadget01;
import draw.fxObj.FXPolygon;
import main.Task;

public class SateGene01 extends SatelliteGenerator implements Task {

	public SateGene01(float centerX, float centerY, float vel, float radius, float angle0, int tr) {
		super(centerX, centerY, vel, radius, angle0, tr);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public SatelliteGenerator generateGagdet(int num, int innerNum){
		FXPolygon[] array = new FXPolygon[num];
		for(int i = 0; i < num; i++) {
			array[i] = new FXGadget01(posX, posY, 20, (float)(Math.PI * 2 / num * i), 20, innerNum);
		}
		polygons = array;
		return this;
	}
}
