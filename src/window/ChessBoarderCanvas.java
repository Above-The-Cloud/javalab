package window;
import sql.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import chessBoard.ChessBoarder;
import defaultSet.DefaultSet;

public class ChessBoarderCanvas extends JPanel{
	UserInfo[] userInfo = new UserInfo[2];
	ChessBoarder MyChessBoarder;
	//Canvas�ı���ͼƬ
	Image BgImage;
	//����ͼƬ�����Ͻ�����
	int Mysx1;
	int Mysy1;
	//����ͼƬ�����½�����
	int Mysx2;
	int Mysy2;
	//ʤ��ͼƬ
	char Winner = '��';
	
	/**
	 * �������ݸ�Canvas����
	 * @param srcChessԴ����
	 * @param srcImage
	 * @param sx1����ͼƬ�����Ͻ�����x
	 * @param sy1����ͼƬ�����Ͻ�����y
	 * @param sx2����ͼƬ�����½�����x
	 * @param sy2����ͼƬ�����½�����y
	 * @return�ɹ�����0
	 * ʱ�䣺20141120
	 */
	public int SendData(ChessBoarder srcChess,Image srcImage,int sx1,int sy1,int sx2,int sy2){
		MyChessBoarder = srcChess;
		BgImage = srcImage;
		Mysx1 = sx1;
		Mysy1 = sy1;
		Mysx2 = sx2;
		Mysy2 = sy2;
		return 0;
	}
	
	public void SendWinner(char a, UserInfo[] userInfo){
		this.userInfo = userInfo; 
		this.Winner = a;
		switch(a)
		{
		case '��': userInfo[0].update("win");
		userInfo[1].update("lose");
		break;
		case '��': userInfo[1].update("win");
		userInfo[0].update("lose");
		break;
		case '��': userInfo[1].update("peace");
		userInfo[0].update("peace");
		break;
		default: break;
		}
	}
	
	@Override
	/**
	 * ���ػ�ͼ����paint
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//test
		System.out.println("Canvas paint done");
		
		//BufferedImage BImage = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		//Graphics g = BImage.getGraphics();
		//��Canvas����
		g.drawImage(BgImage, 0, 0, this.getWidth(), this.getHeight(), Mysx1, Mysy1, Mysx2, Mysy2,this);
		//������
		g.drawImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/chessboardafter.png")), 0, 0,
				Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/chessboardafter.png")).getWidth(null), 
				Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/chessboardafter.png")).getHeight(null), this);
		
		//������
		int xx = DefaultSet.ChessBoarderXX;
		int yy = DefaultSet.ChessBoarderYY;
		int pp = DefaultSet.ChessBoarderPP;
		for (int i = 0;i < 10;i ++){
			for (int j = 0;j < 9;j ++){
				//�������Ӿͻ���
				if (ChineseChessMainFrame.MyBoarder.MyPieces[i][j] != null){
					g.drawImage(ChineseChessMainFrame.MyBoarder.MyPieces[i][j].Icon, 
							xx + j  * pp, yy + i * pp,
							ChineseChessMainFrame.MyBoarder.MyPieces[i][j].Icon.getWidth(null) , ChineseChessMainFrame.MyBoarder.MyPieces[i][j].Icon.getHeight(null), this);
				}
			}
		}
		//����2����
		//System.out.println(ChineseChessMainFrame.MyBoarder.p1);
		if (ChineseChessMainFrame.MyBoarder.p1 != null){
			g.drawImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/kuang.png")), 
					xx +  ChineseChessMainFrame.MyBoarder.p1.x * pp, yy + ChineseChessMainFrame.MyBoarder.p1.y * pp,
					Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/kuang.png")).getWidth(null) , Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/kuang.png")).getHeight(null), this);
		}
		if (ChineseChessMainFrame.MyBoarder.p2 != null){
			g.drawImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/kuang.png")), 
					xx +  ChineseChessMainFrame.MyBoarder.p2.x * pp, yy + ChineseChessMainFrame.MyBoarder.p2.y * pp,
					Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/kuang.png")).getWidth(null) , Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/kuang.png")).getHeight(null), this);
		}
		//����ʤ��ͼƬ
		if (this.Winner == '��'){
			g.drawImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/red-win.png")), 50, 270,559, 132, this);
			ChineseChessMainFrame.Pane2.repaint();
			//ChineseChessMainFrame.Pane2.paintImmediately(0, 0, ChineseChessMainFrame.Pane2.getWidth(),ChineseChessMainFrame.Pane2.getHeight());
			ChineseChessMainFrame.Pane3.repaint();
			//ChineseChessMainFrame.Pane3.paintImmediately(0, 0, ChineseChessMainFrame.Pane3.getWidth(),ChineseChessMainFrame.Pane3.getHeight());
			
		}
		else if (this.Winner == '��'){	
			g.drawImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/black-win.png")), 50, 270,559, 132, this);
			ChineseChessMainFrame.Pane2.repaint();
			//ChineseChessMainFrame.Pane2.paintImmediately(0, 0, ChineseChessMainFrame.Pane2.getWidth(),ChineseChessMainFrame.Pane2.getHeight());
			ChineseChessMainFrame.Pane3.repaint();
		}
		else if (this.Winner == '��'){
			g.drawImage(Toolkit.getDefaultToolkit().getImage("Image\\both-win.png"), 50, 270,559, 132, this);
			
		}
		//gg.drawImage(BImage, 0, 0, null);
	}
}
