package window;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutDialog extends JDialog
{  public AboutDialog(JFrame parent)
{  super( parent, "关于制作人员", true);


   JPanel b = new JPanel(new GridLayout(0,1));
   JLabel Inf = new JLabel("");
   Inf.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\About.png")));
   b.add(Inf);
   getContentPane().add(b, "Center");

   JPanel p2 = new JPanel();
   JButton ok = new JButton("确认");
   p2.add(ok);
   getContentPane().add(p2, "South");
   setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE );

   ok.addActionListener(new ActionListener()
      {  public void actionPerformed(ActionEvent evt)
         { setVisible(false); }
      } );

   setSize(565, 430);
   setLocationRelativeTo(null);
}
}
