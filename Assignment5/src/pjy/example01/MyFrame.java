package pjy.example01;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;



@SuppressWarnings("serial")
public class MyFrame extends JFrame {

	Container frame;


	JLabel imgChar=new JLabel();
	JPanel panel = new JPanel();

	JPanel numname=new JPanel();
	JLabel nntext=new JLabel("202012708 박지연");

	JPanel buttonsP;

	JPanel nextbuttonP=new JPanel();
	JButton nextbutton=new JButton(">>");

	//파일읽어오기
	int flag=0;
	JFileChooser chooser=new JFileChooser();
	ArrayList<Word> voc=new ArrayList<Word>();

	ImageIcon img = new ImageIcon("img/pjy4.jpg");
	JLabel textLabel=new JLabel("안녕하세요");

	String strtext;

	//main panel
	JPanel mainP;

	CardLayout cl=new CardLayout();
	JPanel printC;
	JPanel searchC;
	JPanel quizC;
	JPanel wrongC;
	JPanel plusC;

	//btn1
	String vocStr;
	JTextArea text4;

	//bt2관련
	JTextField searchbox;
	JTextArea meaningbox;

	//bt3관련

	JPanel[] tenquiz=new JPanel[10]; 
	Color[] color = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK, Color.GRAY,Color.BLACK,Color.BLUE,Color.CYAN,Color.DARK_GRAY};	
	JRadioButton[] button;


	MyDialog dialog;

	int answernum;


	int correct=0;//맞은 개수
	int cnt10=0;//10개문제 돌기


	JTextArea judgebox;
	String[] answerarr=new String[10];


	//btn4 관련
	JTextArea wrongtext;

	//btn5 관련
	String filePath;




	public MyFrame(String title) {
		// TODO Auto-generated constructor stub
		super(title);
		this.setSize(520,580);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
		//init2();
		this.setVisible(true);
	}

	private void init() {
		// TODO Auto-generated method stub
		frame = this.getContentPane();
		initPanel();

		frame.setLayout(null);

		//이미지 패널
		panel.setLocation(10,20);
		panel.setSize(img.getIconWidth(),img.getIconHeight());
		frame.add(panel);

		//학번 이름
		numname.add(nntext);
		numname.setLocation(63,20);
		numname.setSize(200,50);
		frame.add(numname);



		//JFileChooser로 파일 선택하고 단어장 생성하고 textLabel(단어장리스트 가져오기)
		createMenu();

		//버튼들의 패널(GridLayout)
		buttonsP=new JPanel(new GridLayout(1,5,0,10));

		JButton btn1=new JButton("단어출력");
		buttonsP.add(btn1);

		JButton btn2=new JButton("단어검색");
		buttonsP.add(btn2);

		JButton btn3=new JButton("퀴즈");
		buttonsP.add(btn3);

		JButton btn4=new JButton("오답노트");
		buttonsP.add(btn4);

		JButton btn5=new JButton("단어추가");
		buttonsP.add(btn5);

		buttonsP.setLocation(0,150);
		buttonsP.setSize(500,50);
		frame.add(buttonsP);


		//mainP 패널들의 배열을 CardLayout으로  구현
		mainP=new JPanel(cl);




		printC=new JPanel();
		printC.setBackground(Color.PINK);
		//printC.add(textLabel);
		text4=new JTextArea(15,30);
		//text 스크롤패인으로 넣는거 구현
		printC.add(new JScrollPane(text4));
		mainP.add(printC,"1");

		searchC=new JPanel();
		//searchC.setLayout(new GridLayout());
		searchC.setBackground(Color.BLACK);
		searchbox=new JTextField(15);
		meaningbox=new JTextArea(10,30);
		searchbox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String searchboxstr=searchbox.getText();


				for(Word word:voc) {

					if(word.eng.equals(searchboxstr)) {
						meaningbox.setText("");
						meaningbox.append("단어의 뜻 : "+word.kor+"\n");

					}

				}

			}

		});
		searchC.add(searchbox);
		searchC.add(new JScrollPane(meaningbox));
		mainP.add(searchC,"2");

		quizC=new JPanel();
		quizC.setBackground(Color.BLUE);



		mainP.add(quizC,"3");

		wrongC=new JPanel();
		wrongC.setBackground(Color.GREEN);
		wrongtext=new JTextArea(15,30);
		//text 스크롤패인으로 넣는거 구현
		wrongC.add(new JScrollPane(wrongtext));
		mainP.add(wrongC,"4");

		plusC=new JPanel();
		plusC.setBackground(Color.YELLOW);
		mainP.add(plusC,"5");

		cl.show(mainP,"5");

		//mainP를 frame에 추가
		mainP.setLocation(0,200);
		mainP.setSize(500,300);
		frame.add(mainP);




		//버튼들의 이벤트 처리
		btn1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
					text4.append(vocStr);
					cl.show(mainP,"1");
				
			}

		});



		btn2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cl.show(mainP,"2");
			}

		});



		btn3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				dialog=new MyDialog(MyFrame.this,"quiz",false,voc);
				dialog.setVisible(true);
				cl.show(mainP,"3");
			}

		});

		btn4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				File wronganswerfile=new File("wronganswer.txt");
				String totalstr="";
				try {
					Scanner scan=new Scanner(wronganswerfile);
					int linenum=0;
					while(scan.hasNextLine()){
						if(linenum==20)
							break;
						String str=scan.nextLine();
						str+="\n";
						totalstr+=str;
						linenum++;
					}
					wrongtext.setText(totalstr);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				cl.show(mainP,"4");
			}

		});

		btn5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String engword=JOptionPane.showInputDialog("영어 단어를 입력하세요.");
				String korword=JOptionPane.showInputDialog("한글 뜻을 입력하세요.");
				if(engword!=null && korword!=null){
					String merge="";
					merge+=engword;
					merge+="\t";
					merge+=korword;
					merge+="\n";

					vocStr+=merge;
					//if(engword!=null)

					//단어 arraylist에 추가
					Word word=new Word(engword,korword);
					voc.add(word);
					try {
						Writer writer=new FileWriter(filePath,true);
						writer.write(merge);
						writer.flush();
						writer.close();


					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				cl.show(mainP,"5");
			}

		});


	}






	public void initPanel() {

		panel.setLayout(null);
		imgChar.setIcon(img);
		imgChar.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		panel.add(imgChar);

	}

	public void createMenu(){
		JMenuBar mb=new JMenuBar();
		JMenu fileMenu=new JMenu("File");
		JMenuItem openItem=new JMenuItem("Open");
		//OpenActionListener openL=new OpenActionListener();
		openItem.addActionListener(new ActionListener(){




			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				FileNameExtensionFilter filter=new FileNameExtensionFilter("TXT","txt");
				chooser.setFileFilter(filter);
				int ret=chooser.showOpenDialog(null);
				if(ret!=JFileChooser.APPROVE_OPTION){
					JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다","경고",JOptionPane.WARNING_MESSAGE);
					return;
				}
				filePath=chooser.getSelectedFile().getPath();
				VocManager voc= new VocManager("박지연");
				voc.makeVoc(filePath);

				vocStr=voc.VocList();

			}


		});
		fileMenu.add(openItem);
		mb.add(fileMenu);
		this.setJMenuBar(mb);

		//단어장 리스트 가져오기
		strtext= vocStr;




	}



	//VocManager class

	public class VocManager {
		private String userName;
		//		ArrayList<Word> voc=new ArrayList<Word>();
		private int number=0;

		Scanner scan=new Scanner(System.in);


		public VocManager(String userName) {
			super();
			this.userName = userName;
		}
		void addWord(Word word) {

			voc.add(word);

		}


		public void makeVoc(String filePath) {
			// TODO Auto-generated method stub
			try(Scanner scan=new Scanner(new File(filePath))){
				while(scan.hasNextLine()) {
					String str=scan.nextLine();
					String[] temp=str.split("\t");
					this.addWord(new Word(temp[0].trim(),temp[1].trim()));//trim은 공백 제거

				}
				System.out.println(userName+"의 단어장이 생성되었습니다.");
				//this.menu();


			}catch(FileNotFoundException e) {
				System.out.println(userName+"의 단어장이 생성되지 않았습니다. \n파일명을 확인하세요.");

			}
		}

		public String VocList(){
			String vocStr="";
			for(Word word:voc) {
				vocStr+=word.eng+":   "+word.kor+"\n";

			}
			return vocStr;
		}

	}



}
