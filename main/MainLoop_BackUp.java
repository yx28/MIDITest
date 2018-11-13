package main;

import main.midi.Source;

public class MainLoop_BackUp implements Runnable {
	private MainFrame frame;
	private Source source;

	private long millis;
	private int nanos;

	public MainLoop_BackUp(MainFrame frame) {
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
		long timeGap = 0;
		long count = 0;;

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	t0 = System.currentTimeMillis(); //再生開始時刻を保存
        try {
            while (true) {
            	frame.getDrawPanel().action(source.getMessage()); //更新処理
            	frame.getDrawPanel().repaint(); //再描画(paintメソッドが呼び出される)
            	count++;
                //休止
                    timeGap = (count * millis + (count * nanos) / 1000000) - (System.currentTimeMillis() + (int)(count * 0.4) - t0);
                    if(millis + timeGap < 0) {timeGap = -millis;}
                    Thread.sleep(millis + timeGap, nanos);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
