package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/* Class Time.  chứa tất cả các biến và chức năng cần thiết 
   liên quan đến timer trên GUI chính Nó sử dụng Class Time */

public class Time {
	private JLabel label;
	Timer countdownTimer;
	int Timerem;

	public Time(JLabel passedLabel) {
		countdownTimer = new Timer(1000, new CountdownTimerListener());
		this.label = passedLabel;
		Timerem = Main.timeRemaining;
	}

	// chức năng bắt đầu giờ
	public void start() {
		countdownTimer.start();
	}

	// chức năng thiết lập lại bộ đếm thời gian
	public void reset() {
		Timerem = Main.timeRemaining;
	}

	// cập nhật thời gian
	class CountdownTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int min, sec;
			if (Timerem > 0) {
				min = Timerem / 60;
				sec = Timerem % 60;
				label.setText(
						String.valueOf(min) + ":" + (sec >= 10 ? String.valueOf(sec) : "0" + String.valueOf(sec)));
				Timerem--;
			} else {
				label.setText("Time's up!");
				reset();
				start();
				Main.Mainboard.changechance();
			}
		}
	}
}