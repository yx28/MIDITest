package draw.transObj;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import draw.MyPolygon;
import main.Task;
import main.midi.MIDIMessage;

public abstract class TransPolygon extends MyPolygon implements Task {
	protected int tr;
	protected int preDelay;
	protected int postDelay;
	protected int count;
	protected int length;

	protected boolean posT;
	protected boolean sizeT;
	protected boolean alphaT;

	protected float defPosX;
	protected float defPosY;
	protected float disPosX;
	protected float disPosY;

	protected float minSizeX; //元の大きさ
	protected float minSizeY; //元の大きさ
	protected float maxSizeX; //最大の大きさ
	protected float maxSizeY; //最大の大きさ

	protected float alpha; //透明度
	protected int r, g, b;

	public TransPolygon(float posX, float posY, float sizeX, float sizeY, Color color, int tr, int preDelay, int postDelay) {
		super(posX, posY, sizeX, sizeY, color);
		this.tr = tr;
		this.preDelay = preDelay;
		this.postDelay = postDelay;
		count = -1;
		length = 0;
		this.posT = false;
		this.sizeT = false;
		this.alphaT = false;
	}
	public TransPolygon setPosTrans(float disPosX, float disPosY) {
		posT = true;
		this.defPosX = posX;
		this.defPosY = posY;
		this.disPosX = disPosX;
		this.disPosY = disPosY;
		return this;
	}
	public TransPolygon setSizeTrans(float maxSizeX, float maxSizeY) {
		sizeT = true;
		this.minSizeX = sizeX;
		this.minSizeY = sizeY;
		this.maxSizeX = maxSizeX;
		this.maxSizeY = maxSizeY;
		return this;
	}
	public TransPolygon setAlphaTrans() {
		alphaT = true;
		alpha = 0;
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
		return this;
	}

	@Override
	public void action(ArrayList<MIDIMessage> mes) {
		mesAnalysis(mes, tr);
		posTrans();
		sizeTrans();
		alphaTrans();
		if(count >= 0) {count++;}
		if(count > preDelay + length + postDelay) {count = -1;}
	}
	//対応するチャンネルのMIDIメッセージが来ているか判定
	protected boolean mesAnalysis(ArrayList<MIDIMessage> mes, int tr) {
		if(mes == null) {return false;}
		for(MIDIMessage token : mes) {
			if(token.getChannel() == tr) {
				count = 0;
				length = token.getLength();
				return true;
			}
		}
		return false;
	}
	//位置変化
	protected void posTrans() {
		if(posT) {
			if(count < 0) {
				posX = defPosX;
				posY = defPosY;
				return;
			}
			if(count >= 0 && count < preDelay) {
				prePosTrans();
			} else if(count > preDelay && count < preDelay + length) {
				keepPosTrans();
			} else if(count > preDelay + length && count < preDelay + length + postDelay) {
				postPosTrans();
			}
			 if(disPosX > 0) {
				 if(posX > defPosX + disPosX) {posX = defPosX + disPosX;}
				 else if(posX < defPosX) {posX = defPosX;}
			 } else {
				 if(posX < defPosX + disPosX) {posX = defPosX + disPosX;}
				 else if(posX > defPosX) {posX = defPosX;}
			 }
			 if(disPosY > 0) {
				 if(posY > defPosY + disPosY) {posY = defPosY + disPosY;}
				 else if(posY < defPosY) {posY = defPosY;}
			 } else {
				 if(posY < defPosY + disPosY) {posY = defPosY + disPosY;}
				 else if(posY > defPosY) {posY = defPosY;}
			 }
		}
	}
	protected void prePosTrans() {
		posX += disPosX / preDelay;
		posY += disPosY / preDelay;
	}
	protected void keepPosTrans() {
		//発音中の処理
	}
	protected void postPosTrans() {
		posX -= disPosX / postDelay;
		posY -= disPosY / postDelay;
	}
	//サイズ変化
	protected void sizeTrans() {
		if(sizeT) {
			if(count < 0) {
				sizeX = minSizeX;
				sizeY = minSizeY;
				return;
			}
			if(count >= 0 && count < preDelay) {
				preSizeTrans();
			} else if(count > preDelay && count < preDelay + length) {
				keepSizeTrans();
			} else if(count > preDelay + length && count < preDelay + length + postDelay) {
				postSizeTrans();
			}
			if(maxSizeX >= minSizeX) {
				 if(sizeX > maxSizeX) {sizeX = maxSizeX;}
			else if(sizeX < minSizeX) {sizeX = minSizeX;}
			} else {
				 if(sizeX < maxSizeX) {sizeX = maxSizeX;}
			else if(sizeX > minSizeX) {sizeX = minSizeX;}
			}
			if(maxSizeY >= minSizeY) {
				 if(sizeY > maxSizeY) {sizeY = maxSizeY;}
			else if(sizeY < minSizeY) {sizeY = minSizeY;}
			} else {
				 if(sizeY < maxSizeY) {sizeY = maxSizeY;}
			else if(sizeY > minSizeY) {sizeY = minSizeY;}
			}
		}
	}
	protected void preSizeTrans() {
		sizeX += (maxSizeX - minSizeX) / preDelay;
		sizeY += (maxSizeY - minSizeY) / preDelay;
	}
	protected void keepSizeTrans() {
		//発音中の処理
	}
	protected void postSizeTrans() {
		sizeX -= (maxSizeX - minSizeX) / postDelay;
		sizeY -= (maxSizeY - minSizeY) / postDelay;
	}
	//透明度変化
	protected void alphaTrans() {
		if(alphaT) {
			if(count < 0) {
				alpha = 0;
				//変化した透明度を適用
				if(alphaT) {color = new Color(r, g, b, (int)alpha);}
				return;
			}
			if(count >= 0 && count < preDelay) {
				preAlphaTrans();
			} else if(count > preDelay && count < preDelay + length) {
				keepAlphaTrans();
			} else if(count > preDelay + length && count < preDelay + length + postDelay) {
				postAlphaTrans();
			}
				 if(alpha > 255) {alpha = 255;}
			else if(alpha < 0  ) {alpha = 0;}
			//変化した透明度を適用
			if(alphaT) {color = new Color(r, g, b, (int)alpha);}
		}
	}
	protected void preAlphaTrans() {
		alpha += 255 / preDelay;
	}
	protected void keepAlphaTrans() {
		//発音中の処理
	}
	protected void postAlphaTrans() {
		alpha -= 255 / postDelay;
	}

	@Override
	public abstract void paint(Graphics g);

}
