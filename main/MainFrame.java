package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.midi.MIDIMessage;

public class MainFrame extends JFrame {
	private JPanel panel;
	private DrawPanel drawPanel;
	private ButtonPanel buttonPanel;
	private LinkedList<Task> tasks; //処理するタスク

	public MainFrame(LinkedList<Task> tasks) {
		super("MIDITest"); //タイトルバーをセット
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ウィンドウを閉じる処理
		panel = new JPanel(); //パネルを生成
		drawPanel = new DrawPanel(); //パネルを生成
		buttonPanel = new ButtonPanel(); //パネルを生成
		setContentPane(panel); //パネルをコンテントペインにセット
		setResizable(false); //サイズ変更を禁止
		//ウィンドウサイズを設定
		getContentPane().setPreferredSize(new Dimension(MainConfig.width, MainConfig.height));
		pack();
		drawPanel.setPreferredSize(new Dimension(MainConfig.dWidth, MainConfig.dHeight));
		buttonPanel.setPreferredSize(new Dimension(MainConfig.bWidth, MainConfig.bHeight));
		getContentPane().add(drawPanel);
		getContentPane().add(buttonPanel);
		setVisible(true); // ウィンドウを見えるようにする
	    //addKeyListener(Key.getInstance()); //キーリスナーをセット
	    this.tasks = tasks; //初期化の際のNullPointerExceptionを防ぐためインスタンスを生成しておく
	}

	public LinkedList<Task> getTasks() { //タスクをセット
		return tasks;
	}
	public void setTasks(LinkedList<Task> tasks) { //タスクをセット
		this.tasks = tasks;
	}
	public void addTasks(Task task) { //タスクをセット
		this.tasks.add(task);
	}
	public JPanel getPanel() { //パネルを提供
		return panel;
	}
	public DrawPanel getDrawPanel() { //パネルを提供
		return drawPanel;
	}
	public ButtonPanel getButtonPanel() { //パネルを提供
		return buttonPanel;
	}

	class DrawPanel extends JPanel { //パネル
	    public void action(ArrayList<MIDIMessage> arrayList) { //タスクの更新処理
	    	for(Task task : tasks) {
	    		if(task.isActive()) { task.action(arrayList); }
	    	}
	    }
		public void paint(Graphics g) { //タスクの描画処理
			for(Task task: tasks) {
				if(task.isActive()) { task.paint(g); }
			}
		}
	}

	class ButtonPanel extends JPanel {

	}
}
