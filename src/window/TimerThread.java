package window;

import java.awt.Color;

import javax.swing.JLabel;

import window.LabelEvent.ChessPieceClick;

public class TimerThread extends Thread{
	JLabel desLabel1;
	JLabel desLabel2;
//	String TIMEINTERVAL = "120";
//	String WARNING = "15";
	public TimerThread(JLabel src1, JLabel src2){
		desLabel1 = src1;
		desLabel2 = src2;
	}
	
	public void run(){
		char initPlayer = ChineseChessMainFrame.DoPlayer;
		long oldTime = System.currentTimeMillis();
		desLabel1.setText("wait..");
		desLabel2.setText("wait..");
		while (!Thread.interrupted()){
			if (ChineseChessMainFrame.DoPlayer != 'ÎÞ'){
				if (ChineseChessMainFrame.DoPlayer == initPlayer){
					if (System.currentTimeMillis() > oldTime + 120000){
						ChessPieceClick.SwitchDoPlayer();
						initPlayer = ChineseChessMainFrame.DoPlayer;
						oldTime = System.currentTimeMillis();
						if(ChineseChessMainFrame.DoPlayer=='ºì'){
							desLabel1.setText("120");
							desLabel2.setText("timeout");
						}
						else{
							desLabel2.setText("120");
							desLabel1.setText("timeout");
						}
					}
				}
				else{
					initPlayer = ChineseChessMainFrame.DoPlayer;
					oldTime = System.currentTimeMillis();
					if(ChineseChessMainFrame.DoPlayer=='ºì'){
						desLabel1.setText("120");
						desLabel2.setText("wait..");
					}
					else{
						desLabel2.setText("120");
						desLabel1.setText("wait..");
					}
				}
				if (120 - (System.currentTimeMillis() - oldTime) / 1000 <= 15){
					if(ChineseChessMainFrame.DoPlayer=='ºì'){
						desLabel1.setForeground(Color.red);
					}
					else{
						desLabel2.setForeground(Color.red);
					}
				}
				else{
					if(ChineseChessMainFrame.DoPlayer=='ºì'){
						desLabel1.setForeground(Color.black);
					}
					else{
						desLabel2.setForeground(Color.black);
					}
				}
				
				if(ChineseChessMainFrame.DoPlayer=='ºì'){
					desLabel1.setText(String.valueOf(120 - (System.currentTimeMillis() - oldTime) / 1000));
				}
				else{
					desLabel2.setText(String.valueOf(120 - (System.currentTimeMillis() - oldTime) / 1000));
				}
			}
			else{
				desLabel1.setText("");
				desLabel2.setText("");
			}
		}
	}
}
