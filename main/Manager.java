package main;

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;

import javax.sound.sampled.Clip;

import draw.BackGround;
import draw.LineXGenerator;
import draw.LineXGenerator2;
import draw.SatelliteGenerator;
import draw.transObj.TransCircle;
import draw.transObj.TransRect;
import main.audio.Music;
import main.audio.PlayClip;

public class Manager {
	private LinkedList<Task> tasks; //処理するタスク
	private MainFrame frame; //フレーム
	private Clip clip; //オーディオ

	public Manager() {
		clip = Music.createClip(new File("Practice.wav"));

		tasks = new LinkedList<Task>();
		tasks.add(new BackGround(Color.BLACK)); //背景追加

		tasks.add(new TransRect(MainConfig.dWidth/2-200, MainConfig.dHeight/2, 30, 30, Color.WHITE, 13, 2, 5)
				.setSizeTrans(300, 300));
		tasks.add(new TransRect(MainConfig.dWidth/2+200, MainConfig.dHeight/2, 30, 30, Color.WHITE, 16, 2, 5)
				.setSizeTrans(300, 300));
		tasks.add(new TransRect(MainConfig.dWidth/2-100, MainConfig.dHeight/2-200, 0, 0, Color.WHITE, 8, 2, 3)
				.setSizeTrans(100, 100));
		tasks.add(new TransRect(MainConfig.dWidth/2+100, MainConfig.dHeight/2-200, 0, 0, Color.WHITE, 9, 2, 3)
				.setSizeTrans(100, 100));
		tasks.add(new TransCircle(MainConfig.dWidth/2, MainConfig.dHeight/2+200, 0, 0, Color.WHITE, 10, 2, 3)
				.setSizeTrans(200, 200));

		tasks.add(new LineXGenerator(MainConfig.dWidth/2-1900, 0, 30, 2)
		.generateCircle(10));
		tasks.add(new LineXGenerator2(MainConfig.dWidth/2-1900, MainConfig.dHeight, 30, 5)
		.generateCircle(10));
		tasks.add(new SatelliteGenerator(MainConfig.dWidth/2, MainConfig.dHeight/2, 0.01f, 300, 0, 17)
				.generateGagdet(30, 4));
		tasks.add(new SatelliteGenerator(MainConfig.dWidth/2, MainConfig.dHeight/2, 0.01f, 300, 0, 18)
				.generateGagdet(30, 4));

		tasks.add(new LineXGenerator(MainConfig.dWidth/2-400, MainConfig.dHeight/2-100, 0, 6)
		.generateRect(10));
		tasks.add(new LineXGenerator(MainConfig.dWidth/2-400, MainConfig.dHeight/2+100, 0, 7)
		.generateRect(10));

		tasks.add(new LineXGenerator(MainConfig.dWidth/2+400, MainConfig.dHeight/2-150, 0, 22)
		.generateRect(10));
		tasks.add(new LineXGenerator(MainConfig.dWidth/2+400, MainConfig.dHeight/2, 0, 21)
		.generateRect(10));
		tasks.add(new LineXGenerator(MainConfig.dWidth/2+400, MainConfig.dHeight/2+150, 0, 23)
		.generateRect(10));

		tasks.add(new SatelliteGenerator(MainConfig.dWidth/2, MainConfig.dHeight/2, 0.01f, 300, 0, 30)
				.generateCircle(10));
		tasks.add(new PlayClip(clip));

		frame = new MainFrame(tasks); //フレームを生成
	}

	public void startMainLoop() {
		MainLoop loop = new MainLoop(frame);
		Thread thread = new Thread(loop);
		thread.start(); //メインループ開始
	}
}
