package main;

public class WaitLoop implements Runnable {
	private MainLoop loop;

	public WaitLoop(MainLoop loop) {
		this.loop = loop;
	}

	@Override
	synchronized public void run() {
        try {
        	while(true) {
                Thread.sleep(1000/30);
                loop.notifyAll();
        	}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

	}

}
