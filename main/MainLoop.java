package main;

import main.midi.Source;

public class MainLoop implements Runnable {
	private MainFrame frame;
	private Source source;

	private long millis;
	private int nanos;

	public MainLoop(MainFrame frame) {
		this.frame = frame; //GameManagerからパネルを受け取る
		source = new Source();

		double waitTime = 1000.0/30;
		millis = (long)waitTime;
		nanos = (int)((waitTime - millis) * 1000000);
		System.out.println("millis = " + millis);
		System.out.println("nanos = " + nanos);
	}

	@Override
	public void run() {
		long t0;
		long t;
		long count = 0;

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	t0 = System.currentTimeMillis(); //再生開始時刻を保存
        while (true) {
        	t = System.currentTimeMillis(); //現在時刻を保存
        	frame.getDrawPanel().action(source.getMessage()); //更新処理
        	frame.getDrawPanel().repaint(); //描画処理(paintメソッドが呼び出される)
        	count++;
            //休止
        	while(true) {
        		if(System.currentTimeMillis() - t >= millis) {break;}
        	}
        }
    }

}
