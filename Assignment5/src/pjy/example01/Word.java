package pjy.example01;


public class Word implements Comparable<Word>{
	
	String eng;
	String kor;
	int wrongcnt;



	public Word(String eng, String kor) {
		// TODO Auto-generated constructor stub
		super();
		this.eng=eng;
		this.kor=kor;
	 
	
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return eng+" : "+kor;
	}
	
	
	public int compareTo(Word o){
		return (this.wrongcnt-o.wrongcnt)*(-1);
	}
	
}
