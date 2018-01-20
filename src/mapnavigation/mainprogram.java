/*
 * Name			: main.java
 * Author		: Tom
 * Created on	: 17.12.2017
 * Description	: empty
 */

package mapnavigation;

public class mainprogram {

	
////Objects//////////////////////////////////////////
	
	static mainprogram mp= new mainprogram();
	static ui ui1 = new ui();
//	static decoding deco = new decoding();		//kann 1:1 gegen converter ausgetauscht werden um manuell zu testen
	static converter conv = new converter();
	
	
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
	
////Constructor//////////////////////////////////////
	
	public mainprogram() {
		// TODO Auto-generated constructor stub
	}
	
	
////Main/////////////////////////////////////////////
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ui1.create();					//User Interface erstellen	
		if(ui1.picturefound()==0) {		//auf Bild als input warten
			do {
				ui1.request();
			}while(ui1.picturefound()==0);
		}
		System.out.println("readingpicture...");
		conv.readpicture(ui1.inputname);
		System.out.println("creating maps...");
		mp.createmaps();				//colormap und distancemap werden erstellt und
										//mit direkt aus bild genommen informationen gefüllt
		//TODO object size...
//		mp.output("colormap");//testing
		System.out.println("setting locations...");
		mp.setuplocation();
		System.out.println("calc distances...");
		mp.calcdistances();
//		mp.output("distancemap double");//testing
		System.out.println("marking way...");
		mp.markway();
		System.out.println("writing picture...");
		conv.writepicture(ui1.outputname,mp.colormap);
		ui1.outputfinal();
		mp.output("distancemap double");//testing
		mp.output("lasthopmap");//testing
		mp.output("colormap");//testing
	}

	
////Methods/////////////////////////////////////////
	
	public void createmaps() {
		colormap= new int[conv.pixelX][conv.pixelY];		//karten größe intitaliesienen
		distancemap= new double[conv.pixelX][conv.pixelY];	//karten größe initialiesieren
		lasthopmap= new int[conv.pixelX][conv.pixelY][2];	//karten größe initialiesieren
															//dreistufig um koodinaten des last hop zu speichern
		mp.maxdistance = (conv.pixelX * conv.pixelY);			//anzahl der pixel als maximale
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
	//TODO wann brauch man mp.XYZ
	
	private void setuplocation() {
		if(mp.colormap[ui1.getStartX()][ui1.getStartY()]!=DEFINES.RED) {	//Startpunkt muss begehbar sein
			tempcoodinate[0]=ui1.getStartX();
			tempcoodinate[1]=ui1.getStartY();
			mp.colormap[tempcoodinate[0]][ui1.getStartY()]=DEFINES.BLUE;
			mp.distancemap[tempcoodinate[0]][tempcoodinate[1]]=0;
			mp.lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]=ui1.getStartX();
			mp.lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]=ui1.getStartY();
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
//				mp.output("colormap");//testing
//			}
		}
//		System.out.println(percentage + ";" + outpercentage);
	}
	private void calcroundpoint() {
		//kreisformiges abarbeiten der punkte um den temporen punkt(unter tempcoodinate) nach
		//der reihnfolge NOSW
			//direkt angrenzende
			if(mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED && mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.YELLO) {				//nicht für wände berechnen
				if(mp.distancemap [tempcoodinate[0]+1][tempcoodinate[1]]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					mp.distancemap[tempcoodinate[0]+1][tempcoodinate[1]]   = tempdistance+1;		//neue distance setzen
					mp.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
					mp.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
					mp.colormap   [tempcoodinate[0]+1][tempcoodinate[1]]   = DEFINES.ORANGE;//als berechnet makieren
					done++;
				}
			}
			if(mp.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED && mp.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.YELLO) {
				if(mp.distancemap [tempcoodinate[0]][tempcoodinate[1]+1]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					mp.distancemap[tempcoodinate[0]][tempcoodinate[1]+1]   =tempdistance+1;		//neue distance setzen
					mp.lasthopmap [tempcoodinate[0]][tempcoodinate[1]+1][0]=tempcoodinate[0];	//aktuellen punkt als last hop setzen
					mp.lasthopmap [tempcoodinate[0]][tempcoodinate[1]+1][1]=tempcoodinate[1];	//aktuellen punkt als last hop setzen
					mp.colormap   [tempcoodinate[0]][tempcoodinate[1]+1]   =DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			if(mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED && mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.YELLO) {
				if(mp.distancemap [tempcoodinate[0]-1][tempcoodinate[1]]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					mp.distancemap[tempcoodinate[0]-1][tempcoodinate[1]]   = tempdistance+1;		//neue distance setzen
					mp.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
					mp.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
					mp.colormap   [tempcoodinate[0]-1][tempcoodinate[1]]   = DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			if(mp.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED && mp.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.YELLO) {
				if(mp.distancemap [tempcoodinate[0]][tempcoodinate[1]-1]>tempdistance+1) {		//kontrolle weg mit temp punkt kürzer
					mp.distancemap[tempcoodinate[0]][tempcoodinate[1]-1]   =tempdistance+1;		//neue distance setzen
					mp.lasthopmap [tempcoodinate[0]][tempcoodinate[1]-1][0]=tempcoodinate[0];	//aktuellen punkt als last hop setzen
					mp.lasthopmap [tempcoodinate[0]][tempcoodinate[1]-1][1]=tempcoodinate[1];	//aktuellen punkt als last hop setzen
					mp.colormap   [tempcoodinate[0]][tempcoodinate[1]-1]   =DEFINES.ORANGE;			//als berechnet makieren
					done++;
				}
			}
			//über ecke angrenzende
			if(mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED || mp.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED) {					//nich durch wände berechnen
				if(mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]+1]!=DEFINES.RED && mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]+1]!=DEFINES.YELLO) {	//nicht für wände berechnen
					if( mp.distancemap[tempcoodinate[0]+1][tempcoodinate[1]+1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						mp.distancemap[tempcoodinate[0]+1][tempcoodinate[1]+1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						mp.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]+1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						mp.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]+1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						mp.colormap   [tempcoodinate[0]+1][tempcoodinate[1]+1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]]!=DEFINES.RED || mp.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED) {					//nich durch wände berechnen
				if(mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]-1]!=DEFINES.RED && mp.colormap[tempcoodinate[0]+1][tempcoodinate[1]-1]!=DEFINES.YELLO) {		//nicht für wände berechnen
					 if(mp.distancemap[tempcoodinate[0]+1][tempcoodinate[1]-1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						mp.distancemap[tempcoodinate[0]+1][tempcoodinate[1]-1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						mp.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]-1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						mp.lasthopmap [tempcoodinate[0]+1][tempcoodinate[1]-1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						mp.colormap   [tempcoodinate[0]+1][tempcoodinate[1]-1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED || mp.colormap[tempcoodinate[0]][tempcoodinate[1]-1]!=DEFINES.RED) {							//nich durch wände berechnen
				if(mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]-1]!=DEFINES.RED && mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]-1]!=DEFINES.YELLO) {				//nicht für wände berechnen
					if( mp.distancemap[tempcoodinate[0]-1][tempcoodinate[1]-1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						mp.distancemap[tempcoodinate[0]-1][tempcoodinate[1]-1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						mp.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]-1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						mp.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]-1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						mp.colormap   [tempcoodinate[0]-1][tempcoodinate[1]-1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
			if(mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]]!=DEFINES.RED || mp.colormap[tempcoodinate[0]][tempcoodinate[1]+1]!=DEFINES.RED) {							//nich durch wände berechnen
				if(mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]+1]!=DEFINES.RED && mp.colormap[tempcoodinate[0]-1][tempcoodinate[1]+1]!=DEFINES.YELLO) {				//nicht für wände berechnen
					if( mp.distancemap[tempcoodinate[0]-1][tempcoodinate[1]+1]>tempdistance+Math.sqrt(2)) {		//kontrolle weg mit temp punkt kürzer
						mp.distancemap[tempcoodinate[0]-1][tempcoodinate[1]+1]   = tempdistance+Math.sqrt(2);		//neue distance setzen
						mp.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]+1][0]= tempcoodinate[0];	//aktuellen punkt als last hop setzen
						mp.lasthopmap [tempcoodinate[0]-1][tempcoodinate[1]+1][1]= tempcoodinate[1];	//aktuellen punkt als last hop setzen
						mp.colormap   [tempcoodinate[0]-1][tempcoodinate[1]+1]   = DEFINES.ORANGE;//als berechnet makieren
						done++;
						diagonal=true;
					}
				}
			}
		//aktuellen punkt als final berechnet makieren
		mp.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.YELLO;
	}
	public void markway() {
			tempcoodinate[0]=ui1.getStartX();
			tempcoodinate[1]=ui1.getStartY();
			mp.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.BLUE;
		if(mp.colormap[ui1.getTagetX()][ui1.getTagetY()]!=DEFINES.RED && mp.distancemap[ui1.getTagetX()][ui1.getTagetY()]!=mp.maxdistance) {	//Zielpunkt muss begehbar sein
			tempcoodinate[0]=ui1.getTagetX();
			tempcoodinate[1]=ui1.getTagetY();
			mp.colormap[tempcoodinate[0]][tempcoodinate[1]]=DEFINES.PURPLE;
		}
		else {
			System.out.println("Tagetlocation couldn't be set!");
		}
		while(ui1.getStartX()!=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][0]||ui1.getStartY()!=lasthopmap[tempcoodinate[0]][tempcoodinate[1]][1]) {
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
				for (int i = 0; i < mp.colormap[0].length; i++) {
					for (int j = 0; j < mp.colormap.length; j++) {
						System.out.print(" __");
					}
					System.out.println();
					for (int j = 0; j < mp.colormap.length; j++) {
						System.out.print("| " + mp.colormap[j][i] +"");
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < mp.colormap.length; j++) {
					System.out.print(" __");
				}
				System.out.println();
			break;
			case "distancemap double":
				for (int i = 0; i < mp.distancemap[0].length; i++) {
					for (int j = 0; j < mp.distancemap.length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < mp.distancemap.length; j++) {
						if(mp.distancemap[j][i]<10) {
							System.out.print("|   " + (int)mp.distancemap[j][i] +"");
						}
						else if(mp.distancemap[j][i]<100){
							System.out.print("|  " + (int)mp.distancemap[j][i] +"");
						}
						else {
							System.out.print("| " + (int)mp.distancemap[j][i] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < mp.distancemap.length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "lasthopmap":
				for (int i = 1; i < mp.lasthopmap[0].length-1; i++) {
					for (int j = 1; j < mp.lasthopmap.length-1; j++) {
						System.out.print(" _____");
					}
					System.out.println();
					for (int j = 1; j < mp.lasthopmap.length-1; j++) {
						if(mp.lasthopmap[j][i][0]<0 && mp.lasthopmap[j][i][1]<0) {
							System.out.print("|" + mp.lasthopmap[j][i][0] + ";" + mp.lasthopmap[j][i][1] +"");
						}
						else if(mp.lasthopmap[j][i][0]<10 && mp.lasthopmap[j][i][1]<10 ) {
							System.out.print("| " + mp.lasthopmap[j][i][0] + ";" + mp.lasthopmap[j][i][1] +" ");
						}
						else if((mp.lasthopmap[j][i][0]>=10||mp.lasthopmap[j][i][0]<0) && mp.lasthopmap[j][i][1]<10) {
							System.out.print("|" + mp.lasthopmap[j][i][0] + ";" + mp.lasthopmap[j][i][1] +" ");
						}
						else if(mp.lasthopmap[j][i][0]<10 && (mp.lasthopmap[j][i][1]>=10||mp.lasthopmap[j][i][1]<0)) {
							System.out.print("| " + mp.lasthopmap[j][i][0] + ";" + mp.lasthopmap[j][i][1] +"");
						}
						else {
							System.out.print("|" + mp.lasthopmap[j][i][0] + ";" + mp.lasthopmap[j][i][1] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 1; j < mp.lasthopmap.length-1; j++) {
					System.out.print(" _____");
				}
				System.out.println();
			break;
			
			//special versions
			case "colormap invert":
				for (int i = 0; i < mp.colormap.length; i++) {
					for (int j = 0; j < mp.colormap[i].length; j++) {
						System.out.print(" __");
					}
					System.out.println();
					for (int j = 0; j < mp.colormap[i].length; j++) {
						System.out.print("| " + mp.colormap[i][j] +"");
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < mp.colormap[colormap.length-1].length; j++) {
					System.out.print(" __");
				}
				System.out.println();
			break;
			case "distancemap invert":
				for (int i = 0; i < mp.distancemap.length; i++) {
					for (int j = 0; j < mp.distancemap[i].length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < mp.distancemap[i].length; j++) {
						if(mp.distancemap[i][j]<10) {
							System.out.print("|   " + mp.distancemap[i][j] +"");
						}
						else if(mp.distancemap[i][j]<100){
							System.out.print("|  " + mp.distancemap[i][j] +"");
						}
						else {
							System.out.print("| " + mp.distancemap[i][j] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < mp.distancemap[distancemap.length-1].length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "distancemap double invert":
				for (int i = 0; i < mp.distancemap.length; i++) {
					for (int j = 0; j < mp.distancemap[i].length; j++) {
						System.out.print(" ____");
					}
					System.out.println();
					for (int j = 0; j < mp.distancemap[i].length; j++) {
						if(mp.distancemap[i][j]<10) {
							System.out.print("|   " + (int)mp.distancemap[i][j] +"");
						}
						else if(mp.distancemap[i][j]<100){
							System.out.print("|  " + (int)mp.distancemap[i][j] +"");
						}
						else {
							System.out.print("| " + (int)mp.distancemap[i][j] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 0; j < mp.distancemap[distancemap.length-1].length; j++) {
					System.out.print(" ____");
				}
				System.out.println();
			break;
			case "lasthopmap invert":
				for (int i = 1; i < mp.lasthopmap.length-1; i++) {
					for (int j = 1; j < mp.lasthopmap[i].length-1; j++) {
						System.out.print(" _____");
					}
					System.out.println();
					for (int j = 1; j < mp.lasthopmap[i].length-1; j++) {
						if(mp.lasthopmap[i][j][0]<10 && mp.lasthopmap[i][j][1]<10) {
							System.out.print("| " + mp.lasthopmap[i][j][0] + ";" + mp.lasthopmap[i][j][1] +" ");
						}
						else if(mp.lasthopmap[i][j][0]>=10 && mp.lasthopmap[i][j][1]<10) {
							System.out.print("|" + mp.lasthopmap[i][j][0] + ";" + mp.lasthopmap[i][j][1] +" ");
						}
						else if(mp.lasthopmap[i][j][0]<10 && mp.lasthopmap[i][j][1]>=10) {
							System.out.print("| " + mp.lasthopmap[i][j][0] + ";" + mp.lasthopmap[i][j][1] +"");
						}
						else {
							System.out.print("|" + mp.lasthopmap[i][j][0] + ";" + mp.lasthopmap[i][j][1] +"");
						}
					}
					System.out.println("|");
					
				}
				for (int j = 1; j < mp.lasthopmap[lasthopmap.length-1].length-1; j++) {
					System.out.print(" _____");
				}
				System.out.println();
			break;
		}
	}
}
