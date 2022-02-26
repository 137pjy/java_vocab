package pjy.example01;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;



public class MyDialog extends JDialog{
	MyFrame frame;
	//JButton okButton=new JButton("OK");
	JPanel fourradio=new JPanel(new GridLayout(4,1));
	ArrayList<Word> voc=new ArrayList<Word>();
	ArrayList<Word> fouranswer=new ArrayList<Word>();
	JRadioButton[] button=new JRadioButton[4];
	String answerkor;
	int correct=0;
	int cnt10=0;
	long starttime;
	int flagcorrect=0;
	ArrayList<Word> wronganswerarr=new ArrayList<Word>();
	ArrayList<Word> answerword=new ArrayList<Word>();

	JLabel question=new JLabel("00의 한글뜻은 무엇입니까?");

	public MyDialog(MyFrame frame,String title,boolean modal,ArrayList<Word> voc){
		super(frame,title,modal);
		this.frame=frame;
		this.voc=voc;
		setLayout(new GridLayout(3,1));
		setSize(300,300);

		this.add(question);
		this.add(fourradio);
		//this.add(okButton);
		starttime=System.nanoTime();



		//********************************************************//
		//wronganswer.txt 읽어온 다음 wronganswerarr에 저장
		File wronganswerfile=new File("wronganswer.txt");
		try {
			Scanner scan=new Scanner(wronganswerfile);
			while(scan.hasNextLine()){

				String str=scan.nextLine();
				String[] temp=str.split("\t");

				Word word = new Word(temp[0],temp[1]);
				word.wrongcnt=Integer.parseInt(temp[2]);
				wronganswerarr.add(word);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		makequiz();


	}

	void makequiz(){


		Random rand=new Random();
		int randindex=rand.nextInt(voc.size());
		Word q=voc.get(randindex);
		//fouranswer.add(q);
		System.out.println(q.eng+"의 뜻은 무엇일까요?");

		//4지선다 arraylist 만들기
		label:for(int j=0;j<3;j++) {
			int arandindex=rand.nextInt(voc.size());
			Word pw=voc.get(arandindex);
			for(int z=0; z<j; z++) {
				if(pw.equals(fouranswer.get(z).kor)) {
					j--;
					continue label;


				}

			}
			if(!pw.equals(q))
				fouranswer.add(pw);
			else
				j--;
		}

		int qinsert=rand.nextInt(4);
		fouranswer.add(qinsert,q);


		//4지선다 출력
		//for(int k=0;k<4;k++) {
		//   System.out.println((k+1)+") "+fouranswer.get(k).kor);
		//  }

		int answernum=fouranswer.indexOf(q);
		answerkor=fouranswer.get(answernum).kor;
		answerword.add(fouranswer.get(answernum));


		question.setText((cnt10+1)+"번) "+q.eng+"의 뜻은 무엇일까요?");


		//4지선다 radiobutton 출력
		ButtonGroup g=new ButtonGroup();
		for(int k=0;k<4;k++) {
			//System.out.println((k+1)+") "+fouranswer.get(k).kor);
			button[k]=new JRadioButton(fouranswer.get(k).kor);
			button[k].addItemListener(new MyItemListener());
			g.add(button[k]);
			fourradio.add(button[k]);

		}




		fouranswer.clear();



	}

	class MyItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.DESELECTED)
				return; //라디오버튼이 선택 해제된 경우 그냥 리턴

			if (button[0].isSelected()){
				String str=button[0].getText();

				if(str.equals(answerkor)){
					flagcorrect=1;
					correct++;

				}

			}
			else if (button[1].isSelected()){
				String str=button[1].getText();

				if(str.equals(answerkor)){
					flagcorrect=1;
					correct++;

				}
			}
			else if (button[2].isSelected()){
				String str=button[2].getText();

				if(str.equals(answerkor)){
					flagcorrect=1;
					correct++;


				}
			}
			else{
				String str=button[3].getText();

				if(str.equals(answerkor)){
					flagcorrect=1;
					correct++;

				}

			}

			if(flagcorrect==0){
				Word answer=answerword.get(cnt10);
				answer.wrongcnt++;
				if(answer.wrongcnt==1){
					wronganswerarr.add(answerword.get(cnt10));
				}
				else{
					for(Word word:wronganswerarr){
						if(answer.equals(word))
							word.wrongcnt++;//wronganswerarr의 단어의 오답횟수를 증가시킴
					}
				}
				//System.out.println(answerword.get(cnt10)+"을 틀렸습니다!! wronganswer에 추가!!");

			}

			fourradio.removeAll();
			cnt10++;
			if(cnt10<=9){
				flagcorrect=0;
				repaint();

				makequiz();
			}
			if(cnt10==10){
				long endtime=System.nanoTime();
				long duration=endtime-starttime;
				long convert= TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS) ;
				JOptionPane.showMessageDialog(null,"맞은 개수는 "+correct+"개 입니다\n걸린시간은 "+convert+"초입니다","Message",JOptionPane.INFORMATION_MESSAGE);


				frame.voc=voc;
				String str="";
				try {
					//wronganswer.txt 없애기
					Writer writer=new FileWriter("wronganswer.txt");

					Collections.sort(wronganswerarr);
					for(Word word:wronganswerarr){
						str+=word.eng+"\t"+word.kor+"\t"+word.wrongcnt+"\n";

					}
					writer.write(str);
					writer.flush();
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}


		}

	}
}


