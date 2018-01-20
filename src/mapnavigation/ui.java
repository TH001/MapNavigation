/*
 * Name			: main.java
 * Author		: Tom
 * Created on	: 17.12.2017
 * Description	: empty
 */

package mapnavigation;


public class ui {
//	static ui UI = new ui();
	private int theirisapicturefound = 1;
	private int startX = 2;			//counting from (0)-(pixelwide-1)
	private int startY = 2;			//counting from (0)-(pixelheight-1)
	private int tagetX = 38;
	private int tagetY = 4;
	
	public String inputname = "test4.png";
	public String outputname = "output1.png";
	
	public ui() {
		// TODO Auto-generated constructor stub
	}
	public void create() {
		
	}
	public int picturefound () {
		if(theirisapicturefound==1) {
			return 1;
		}
		else {
			return 0;
		}
	}
	public void request() {
		//add output to put in a map
	}
	
	public void outputfinal() {
		//give distance and way
	}
	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public int getTagetX() {
		return tagetX;
	}
	public int getTagetY() {
		return tagetY;
	}

}
