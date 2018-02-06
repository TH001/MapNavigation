/*
 * Name			: main.java
 * Author		: Tom
 * Created on	: 17.12.2017
 * Description	: empty
 */

package mapnavigation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Tom
 *
 */
public class mainprogram {

	
////Objects//////////////////////////////////////////
	
	
//	mainprogram mp= new mainprogram();
//	static decoding deco = new decoding();		//kann 1:1 gegen converter ausgetauscht werden um manuell zu testen
	converter conv = new converter();
	uifunction gui	= new uifunction();
	
	
////Variables////////////////////////////////////////
	
	double maxdistance; 				//maximale distance für den Fall, dass
										//alle Punkte abgelaufen werden müssen
										//eh der Zielpunkt ereicht wird
	double lastmindistance;
	double tempdistance = 0;
	int[] tempcoodinate = new int [2];
	int done = 1;
	int buffer;
	boolean diagonal=false;
	
	int nowalls = 0;
	double outpercentage;
	double percentage;
	
	int[][] colormap;
	double[][] distancemap;
	int[][][] lasthopmap;
	
	private int writingattempt = 0;
	private String [] fileOutput = new String[9];
	private int inputfound = 0;
	private String inputname;
	private int startX = -1;
	private int startY = -1;
	private int tagetX = -1;
	private int tagetY = -1;
	private String outputname;
	
	
////Constructor//////////////////////////////////////
	
	public mainprogram() {
		// TODO Auto-generated constructor stub
	}
	
	
////Main/////////////////////////////////////////////
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
////		mp = new mainprogram();
////		run();
//	}
	
	public int test = 1;
	void run(){	
		gui.unstaticmain(null);
		gui.writetoFile();
//		workingcore.getGui().unstaticmain(null);
//		if(gui.getdynamictest()<20) {		//auf Bild als input warten
//			do {
//				gui.request();
//				System.out.println("testvalue:" + gui.getdynamictest());
//			}while(gui.getdynamictest()<20);//TODO ist das richtig?
//		}
		if(inputfound<2) {		//auf Bild als input warten
			do {
//				gui.request();
//				gui.writetoFile();
				File transferbuffer = new File("transferbuffer.tmp");
			      if(transferbuffer.exists()){
			    	  readfromFile();
			      }
//				System.out.println("reseved:" + gui.checkedInput);
			}while(inputfound<2);//TODO ist das richtig?
		}
		System.out.println("readingpicture...");
		conv.readpicture(inputname);
		System.out.println("creating maps...");
		this.createmaps();				//colormap und distancemap werden erstellt und
										//mit direkt aus bild genommen informationen gefüllt
		//TODO object size...
//		this.output("colormap");//testing
		System.out.println("setting locations...");
		this.setuplocation();
		System.out.println("calc distances...");
		this.calcdistances();
//		this.output("distancemap double");//testing
		System.out.println("marking way...");
		this.markway();
		System.out.println("writing picture...");
		conv.writepicture(outputname,colormap);
		gui.outputfinal();
		this.output("distancemap double");//testing
		this.output("lasthopmap");//testing
		this.output("colormap");//testing
	}
	
////Methods/////////////////////////////////////////
	
	public void createmaps() {
		colormap= new int[conv.pixelX][conv.pixelY];		//karten größe intitaliesienen
		distancemap= new double[conv.pixelX][conv.pixelY];	//karten größe initialiesieren
		lasthopmap= new int[conv.pixelX][conv.pixelY][2];	//karten größe initialiesieren
															//dreistufig um koodinaten des last hop zu speichern
		this.maxdistance = (conv.pixelX * conv.pixelY);			//anzahl der pixel als maximale
																//distance setzten, Annahme alle
																//pixel sind gleich weit von ein-
																//ander entfernt
		
		for (int i = 0; i < distancemap.length; i++) {			//für alle Felder der distancemap
			for (int j = 0; j < distancemap[i].length; j++) {	//die maximale distance setzten
				distancemap[i][j]=maxdistance;
			}
		}
		for (int i = 0; i < colormap.length; i++) {				//colormap komplett weiss färben
			for (int j = 0; j < colormap[i].length; j++) {
				colormap[i][j]=DEFINES.WHITE;
			}
		}
		for (int i = 0; i < colormap.length; i++) {				//mackiere alle Wande in der Colormap
			for (int j = 0; j < colormap[i].length; j++) {			//als Rot (nich begehbar)
//				System.out.println("x:"+i+" pixelX:"+conv.pixelX+ " y:"+j+" pixelY:"+conv.pixelY);
				if(conv.getcolor(i, j)==DEFINES.BLACK) {
					colormap[i][j]=DEFINES.RED;
				}
			}
		}
		for (int i = 0; i < lasthopmap.length; i++) {			//für alle Felder der lasthopmap
			for (int j = 0; j < lasthopmap[i].length; j++) {	//-1 in der x und y coodinate setzen
				lasthopmap[i][j][0]=-1;
				lasthopmap[i][j][1]=-1;
			}
		}
		
	}
	//TODO wann brauch man this.XYZ
	
	private void setuplocation() {
		if(this.colormap[startX][startY]!=DEFINES.RED) {	//Startpunkt muss begehbar sein
			tempcoodinate[0]=startX;
			tempcoodinate[1]=startY;
			this.colormap[tempcoodinate[0]][startY]=DEFINES.BLUE;
			this.distancemap[tempcoodinate[0]][tempcoodinate[1]]=0;
			this.lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]=startX;
			this.lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]=startY;
			lastmindistance = 0;
		}
		else {
			System.out.println("Startlocation couldn't be set!");
		}
	}
	private void calcdistances() {
//		for (int i = 0; i < colormap.length; i++) {
//			for (int j = 0; j < colormap[i].length; j++) {
//				if(colormap[i][j]!=DEFINES.WHITE) {
//					nowalls++;
//				}
//			}
//		}
		System.out.println(nowalls);
		while(done!=0) {// bis kein weiterer punkt auf diese art und weise berechnet werden kann
			done=0;
			for (int i = 0; i < distancemap.length; i++) {
				for (int j = 0; j < distancemap[i].length; j++) {
					if(colormap[i][j]!=DEFINES.YELLO) {
						if(distancemap[i][j]<=lastmindistance) {		//<= nur verwendet falls ich quere schnitte noch programieren sollte = muss auch funktionien
							tempcoodinate[0]=i;
							tempcoodinate[1]=j;
							tempdistance=distancemap[i][j];
							calcroundpoint();
//							percentage=percentage+1;
						}
					}
				}
			}
			if(diagonal==true) {
				lastmindistance=lastmindistance+Math.sqrt(2);
				diagonal=false;
			}
			else {
				lastmindistance++;
			}
//			//output
//			if(outpercentage+5>=(percentage/nowalls)) {
//				System.out.print(". ");
//				outpercentage=outpercentage+5;
//			}
//			if(outpercentage==25||outpercentage==50 ||outpercentage==75) {
//				System.out.print("| ");
//			}
//			if(Math.round((percentage*100)/nowalls)==100) {
//				System.out.println("|||");
//				this.output("colormap");//testing
//			}
		}
//		System.out.println(percentage + ";" + outpercentage);
	}
	private void calcroundpoint() {
		//kreisformiges abarbeiten der punkte um den temporen punkt(unter tempcoodinate) nach
		//der reihnfolge NOSW
			//direkt angrenzende
			if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED && this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.YELLO) {				//nicht für wände berechnen
				if(this.distancemap [tempcoodinate[0]+1][tempcoodinate[1]]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]]   = tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]+1][tempcoodinate[1]]   = DEFINES.ORANGE;//als berechnet makieren
					done++;
				}
			}
			if(this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED && this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.YELLO) {
				if(this.distancemap [tempcoodinate[0]][tempcoodinate[1]+1]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					this.distancemap[tempcoodinate[0]][tempcoodinate[1]+1]   =tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]+1][0]=tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]+1][1]=tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]][tempcoodinate[1]+1]   =DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED && this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.YELLO) {
				if(this.distancemap [tempcoodinate[0]-1][tempcoodinate[1]]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]]   = tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]-1][tempcoodinate[1]]   = DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			if(this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED && this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.YELLO) {
				if(this.distancemap [tempcoodinate[0]][tempcoodinate[1]-1]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					this.distancemap[tempcoodinate[0]][tempcoodinate[1]-1]   =tempdistance+1;		//neue distance setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]-1][0]=tempcoodinate[0];	//aktuellen punkt als last hop setzen
					this.lasthopmap [tempcoodinate[0]][tempcoodinate[1]-1][1]=tempcoodinate[1];	//aktuellen punkt als last hop setzen
					this.colormap   [tempcoodinate[0]][tempcoodinate[1]-1]   =DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			//über ecke angrenzende
			if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED) {					//nich durch wände berechnen
				if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]+1]!=DEFINES.RED && this.colormap[tempcoodinate[0]+1][tempcoodinate[1]+1]!=DEFINES.YELLO) {	//nicht für wände berechnen
					if( this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]+1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]+1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]+1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]+1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]+1][tempcoodinate[1]+1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED) {					//nich durch wände berechnen
				if(this.colormap[tempcoodinate[0]+1][tempcoodinate[1]-1]!=DEFINES.RED && this.colormap[tempcoodinate[0]+1][tempcoodinate[1]-1]!=DEFINES.YELLO) {		//nicht für wände berechnen
					 if(this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]-1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						this.distancemap[tempcoodinate[0]+1][tempcoodinate[1]-1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]-1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]-1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]+1][tempcoodinate[1]-1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED) {							//nich durch wände berechnen
				if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]-1]!=DEFINES.RED && this.colormap[tempcoodinate[0]-1][tempcoodinate[1]-1]!=DEFINES.YELLO) {				//nicht für wände berechnen
					if( this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]-1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]-1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]-1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]-1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]-1][tempcoodinate[1]-1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED || this.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED) {							//nich durch wände berechnen
				if(this.colormap[tempcoodinate[0]-1][tempcoodinate[1]+1]!=DEFINES.RED && this.colormap[tempcoodinate[0]-1][tempcoodinate[1]+1]!=DEFINES.YELLO) {				//nicht für wände berechnen
					if( this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]+1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						this.distancemap[tempcoodinate[0]-1][tempcoodinate[1]+1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]+1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						this.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]+1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						this.colormap   [tempcoodinate[0]-1][tempcoodinate[1]+1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
		//aktuellen punkt als final berechnet makieren
		this.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.YELLO;
	}
	public void markway() {
			tempcoodinate[0]=startX;
			tempcoodinate[1]=startY;
			this.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.BLUE;
		if(this.colormap[tagetX][tagetY]!=DEFINES.RED && this.distancemap[tagetX][tagetY]!=this.maxdistance) {	//Zielpunkt muss begehbar sein
			tempcoodinate[0]=tagetX;
			tempcoodinate[1]=tagetY;
			this.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.PURPLE;
		}
		else {
			System.out.println("Tagetlocation couldn't be set!");
		}
		while(startX!=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]||startY!=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]) {
//			System.out.println(buffer + ";" + tempcoodinate[0] + "," + tempcoodinate[1]); //testing
			colormap[lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]][lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]]=DEFINES.GREEN;
			buffer=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0];
			tempcoodinate[1]=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1];
			tempcoodinate[0]=buffer;
		}
	}
	
	/**
	 * @param usage
	 */
	public void output(String usage){
		switch (usage){
			case "colormap":
				for (int i = 0; i < this.colormap[0].length; i++) {
					for (int j = 0; j < this.colormap.length; j++) {
						System.out.print(" __");
					}
					System.out.println();
					for (int j = 0; j < this.colormap.length; j++) {
						System.out.print("| " + this.colormap[j][i] +"");
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < this.colormap.length; j++) {
					System.out.print(" __");
				}
				System.out.println();
			break;
			case "distancemap double":
				for (int i = 0; i < this.distancemap[0].length; i++) {
					for (int j = 0; j < this.distancemap.length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < this.distancemap.length; j++) {
						if(this.distancemap[j][i]<10) {
							System.out.print("|   " + (int)this.distancemap[j][i] +"");
						}
						else if(this.distancemap[j][i]<100){
							System.out.print("|  " + (int)this.distancemap[j][i] +"");
						}
						else {
							System.out.print("| " + (int)this.distancemap[j][i] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < this.distancemap.length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "lasthopmap":
				for (int i = 1; i < this.lasthopmap[0].length-1; i++) {
					for (int j = 1; j < this.lasthopmap.length-1; j++) {
						System.out.print(" _____");
					}
					System.out.println();
					for (int j = 1; j < this.lasthopmap.length-1; j++) {
						if(this.lasthopmap[j][i][0]<0 && this.lasthopmap[j][i][1]<0) {
							System.out.print("|" + this.lasthopmap[j][i][0] + ";" + this.lasthopmap[j][i][1] +"");
						}
						else if(this.lasthopmap[j][i][0]<10 && this.lasthopmap[j][i][1]<10 ) {
							System.out.print("| " + this.lasthopmap[j][i][0] + ";" + this.lasthopmap[j][i][1] +" ");
						}
						else if((this.lasthopmap[j][i][0]>=10||this.lasthopmap[j][i][0]<0) && this.lasthopmap[j][i][1]<10) {
							System.out.print("|" + this.lasthopmap[j][i][0] + ";" + this.lasthopmap[j][i][1] +" ");
						}
						else if(this.lasthopmap[j][i][0]<10 && (this.lasthopmap[j][i][1]>=10||this.lasthopmap[j][i][1]<0)) {
							System.out.print("| " + this.lasthopmap[j][i][0] + ";" + this.lasthopmap[j][i][1] +"");
						}
						else {
							System.out.print("|" + this.lasthopmap[j][i][0] + ";" + this.lasthopmap[j][i][1] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 1; j < this.lasthopmap.length-1; j++) {
					System.out.print(" _____");
				}
				System.out.println();
			break;
			
			//special versions
			case "colormap invert":
				for (int i = 0; i < this.colormap.length; i++) {
					for (int j = 0; j < this.colormap[i].length; j++) {
						System.out.print(" __");
					}
					System.out.println();
					for (int j = 0; j < this.colormap[i].length; j++) {
						System.out.print("| " + this.colormap[i][j] +"");
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < this.colormap[colormap.length-1].length; j++) {
					System.out.print(" __");
				}
				System.out.println();
			break;
			case "distancemap invert":
				for (int i = 0; i < this.distancemap.length; i++) {
					for (int j = 0; j < this.distancemap[i].length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < this.distancemap[i].length; j++) {
						if(this.distancemap[i][j]<10) {
							System.out.print("|   " + this.distancemap[i][j] +"");
						}
						else if(this.distancemap[i][j]<100){
							System.out.print("|  " + this.distancemap[i][j] +"");
						}
						else {
							System.out.print("| " + this.distancemap[i][j] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < this.distancemap[distancemap.length-1].length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "distancemap double invert":
				for (int i = 0; i < this.distancemap.length; i++) {
					for (int j = 0; j < this.distancemap[i].length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < this.distancemap[i].length; j++) {
						if(this.distancemap[i][j]<10) {
							System.out.print("|   " + (int)this.distancemap[i][j] +"");
						}
						else if(this.distancemap[i][j]<100){
							System.out.print("|  " + (int)this.distancemap[i][j] +"");
						}
						else {
							System.out.print("| " + (int)this.distancemap[i][j] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < this.distancemap[distancemap.length-1].length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "lasthopmap invert":
				for (int i = 1; i < this.lasthopmap.length-1; i++) {
					for (int j = 1; j < this.lasthopmap[i].length-1; j++) {
						System.out.print(" _____");
					}
					System.out.println();
					for (int j = 1; j < this.lasthopmap[i].length-1; j++) {
						if(this.lasthopmap[i][j][0]<10 && this.lasthopmap[i][j][1]<10) {
							System.out.print("| " + this.lasthopmap[i][j][0] + ";" + this.lasthopmap[i][j][1] +" ");
						}
						else if(this.lasthopmap[i][j][0]>=10 && this.lasthopmap[i][j][1]<10) {
							System.out.print("|" + this.lasthopmap[i][j][0] + ";" + this.lasthopmap[i][j][1] +" ");
						}
						else if(this.lasthopmap[i][j][0]<10 && this.lasthopmap[i][j][1]>=10) {
							System.out.print("| " + this.lasthopmap[i][j][0] + ";" + this.lasthopmap[i][j][1] +"");
						}
						else {
							System.out.print("|" + this.lasthopmap[i][j][0] + ";" + this.lasthopmap[i][j][1] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 1; j < this.lasthopmap[lasthopmap.length-1].length-1; j++) {
					System.out.print(" _____");
				}
				System.out.println();
			break;
		}
	}
	
	private void readfromFile() {
		String line;
	    try {
	    	File transferbuffer = new File("transferbuffer.tmp");
			// Creates a FileReader Object
			FileReader filereader = new FileReader(transferbuffer); 
	        BufferedReader bufferreader = new BufferedReader(filereader);
	        line = bufferreader.readLine();

	        for (int i = 0; line != null; i++) {
	        	if(i == 0)if(Integer.parseInt(line) == writingattempt)return;	//no change no date read again
	        	fileOutput[i] = line;
	        	line = bufferreader.readLine();
			}
	        bufferreader.close();
//	        while (line != null) {     
//	          //do whatever here 
//	            line = bufferreader.readLine();
//	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    
	    //TESTOUTPUT
	    System.out.println("beginn");
	    for (int i = 0; i < fileOutput.length; i++) {
			System.out.println(fileOutput[i]);
		}
	    System.out.println("end");
	    
	    //OUTPUTTODATA
	    
		writingattempt = Integer.parseInt(fileOutput[0]);
		inputfound = Integer.parseInt(fileOutput[1]);
		inputname= fileOutput[2];
		startX= Integer.parseInt(fileOutput[3]);
		startY= Integer.parseInt(fileOutput[4]);
		tagetX= Integer.parseInt(fileOutput[5]);
		tagetY= Integer.parseInt(fileOutput[6]);
		outputname= fileOutput[7];
		
//		File transferbuffer = new File("transferbuffer.tmp");
//		transferbuffer.delete();
	}
}
