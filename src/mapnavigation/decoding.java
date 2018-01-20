/*
 * Name			: decoding.java
 * Author		: Tom
 * Created on	: 28.12.2017
 * Description	: empty
 */

package mapnavigation;


public class decoding {
	
	public int pixelX;
	public int pixelY;
	public int[][][] picture;
	public decoding() {
		// TODO Auto-generated constructor stub
	}
	public void readpicture() {
		pixelX = 12;//pictureheight;
		pixelY = 24;//picturewide;
		picture= new int[pixelX][pixelY][3];			//drei stufiges array ist als bild zu sehen
														//erste und zweite stufe sind die Koordinaten
														//in der dritten stufe werden die Farbinformationen
														//für die einzelenen Kanale abgelegt
		testmap();
	}
	public int getcolor (int xcoordinate, int ycoordinate) {
			if (picture[xcoordinate][ycoordinate][0]>200 &&		//for testing only one channel
				picture[xcoordinate][ycoordinate][1]>200 &&
				picture[xcoordinate][ycoordinate][2]>200) {
				return DEFINES.BLACK;
		}
		else if(picture[xcoordinate][ycoordinate][0]<50 &&		//for testing only one channel
				picture[xcoordinate][ycoordinate][1]<50 &&
				picture[xcoordinate][ycoordinate][2]<50) {
				return DEFINES.WHITE;
			}
//		else if(picture[xcoordinate][ycoordinate][0]<50 &&	//same for every defined color
//				picture[xcoordinate][ycoordinate][1]<50 &&
//				picture[xcoordinate][ycoordinate][2]<50) {
//				return DEFINES.WHITE;
//			}
		else {
			return -1;
		}
	}
	private void testmap(){
		for (int i = 0; i < pixelX; i++) {		//top line
			picture[i][0][0]=255;
			picture[i][0][1]=255;
			picture[i][0][2]=255;
		}
		for (int i = 1; i < (pixelY-1); i++) {	//left line
			picture[0][i][0]=255;
			picture[0][i][1]=255;
			picture[0][i][2]=255;
		}
		for (int i = 1; i < (pixelY-1); i++) {	//right line
			picture[pixelX-1][i][0]=255;
			picture[pixelX-1][i][1]=255;
			picture[pixelX-1][i][2]=255;
		}
		for (int i = 0; i < pixelX; i++) {		//ground line
			picture[i][pixelY-1][0]=255;
			picture[i][pixelY-1][1]=255;
			picture[i][pixelY-1][2]=255;
		}
		//Zusatzwand
		for (int i = 1; i < (pixelY-2); i++) {	//left line
			picture[3][i][0]=255;
			picture[3][i][1]=255;
			picture[3][i][2]=255;
		}
		for (int i = 2; i < (pixelY-1); i++) {	//left line
			picture[6][i][0]=255;
			picture[6][i][1]=255;
			picture[6][i][2]=255;
		}
		
	}
}
