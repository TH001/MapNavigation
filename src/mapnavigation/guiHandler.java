/**
 * Name			: guiHandler.java
 * Author		: Tom
 * Created on	: 04.02.2018
 * Description	: empty
 */
package mapnavigation;

public class guiHandler {

	public guiHandler() {
		// TODO Auto-generated constructor stub
	}

////private Variables////////////////////////
	private int theirisapicturefound = 1;
	private int startX = 2;			//counting from (0)-(pixelwide-1)
	private int startY = 2;			//counting from (0)-(pixelheight-1)
	private int tagetX = 14;
	private int tagetY = 11;
	private String inputname = "test4.png";
	private String outputname = "output2.png";
	
////public Methods belonging main////////////
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
//		System.out.println("LOOP REQUEST");
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
	public String getInputName() {
		return inputname;
	}
	public String getOutputName() {
		return outputname;
	}

////public Methods belonging sub/////////////
}
