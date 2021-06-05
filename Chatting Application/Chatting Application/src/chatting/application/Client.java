package chatting.application;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Calendar;
import java.io.*;
import java.text.SimpleDateFormat;
public class Client implements ActionListener{
	
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JPanel a1;
	static JFrame f1 = new JFrame();
	static Box vertical = Box.createVerticalBox();
	
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	
	JLabel l4;
	Client(){
		
		p1 = new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,400,70);
		p1.setLayout(null);
		f1.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5,17,30,30);
		p1.add(l1);
		
		l1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/a.png"));
		Image i5 = i4.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2 = new JLabel(i6);
		l2.setBounds(30,-5,80,80);
		p1.add(l2);
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel l5 = new JLabel(i9);
		l5.setBounds(270,20,30,30);
		p1.add(l5);
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel l6 = new JLabel(i12);
		l6.setBounds(320,20,35,30);
		p1.add(l6);
		
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel l7 = new JLabel(i15);
		l7.setBounds(370,20,13,25);
		p1.add(l7);
		
		JLabel l3 = new JLabel("Amit Shah");
		l3.setFont(new Font("SAN_SERIF",Font.BOLD,18));
		l3.setForeground(Color.white);
		l3.setBounds(110,15,100,18);
		p1.add(l3);
		
		l4 = new JLabel("Active Now");
		l4.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
		l4.setForeground(Color.white);
		l4.setBounds(110,35,100,20);
		p1.add(l4);
		
		a1 = new JPanel();
		//a1.setBounds(5,75,388,465);
		a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		//f1.add(a1);
		
		JScrollPane sp = new JScrollPane(a1);
		sp.setBounds(5,75,388,465);
		sp.setBorder(BorderFactory.createEmptyBorder());
		f1.add(sp);
		
		t1 = new JTextField();
		t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		t1.setBounds(5,550,280,30);
		t1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				l4.setText("typing...");
				
			}
			public void keyReleased(KeyEvent e) {
				
			}
		});
		f1.add(t1);
		
		b1 = new JButton("Send");
		b1.setBackground(new Color(7,94,84));
		b1.setForeground(Color.white);		b1.setBounds(290,550,100,30);
		b1.addActionListener(this);
		f1.add(b1);
		
		f1.setSize(400,600);
		f1.setLocation(800,100);
		f1.setLayout(null);
		f1.setUndecorated(true);
		f1.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae) {
		try {
			String out = t1.getText();
			JPanel p2 = formatLabel(out);
			a1.setLayout(new BorderLayout());
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2,BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
			a1.add(vertical,BorderLayout.PAGE_START);
			dout.writeUTF(out);
			t1.setText("");
			l4.setText("Active Now");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static JPanel formatLabel(String out) {
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));
		
		JLabel l1 = new JLabel(out);
		l1.setBackground(new Color(37,211,102));
		l1.setBorder(new EmptyBorder(5,5,5,5));
		l1.setFont(new Font("Tahoma",Font.PLAIN,16));
		l1.setOpaque(true);
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
		JLabel l2 = new JLabel();
		l2.setText(sdf.format(c.getTime()));
		
		p3.add(l1);
		p3.add(l2);
		return p3;
	}
	public static void main(String args[]){
		new Client();
		
		try {
			
			s = new Socket("localhost",6001);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			String msginput = "";
			while(true) {
				msginput = din.readUTF();
				JPanel p2 = formatLabel(msginput);
				
				JPanel left = new JPanel(new BorderLayout());
				left.add(p2,BorderLayout.LINE_START);
				vertical.add(left);
				f1.validate();
			}
		}catch(Exception e) {
			
		}
	}
}

