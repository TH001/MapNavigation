/**
 * Name			: core.java
 * Author		: Tom
 * Created on	: 26.01.2018
 * Description	: empty
 */
package mapnavigation;

import java.io.File;

public class core {
////Objects//////////////////////////////////////////
	
	mainprogram programm = new mainprogram();
	guiHandler gui = new guiHandler();
	ui ui1 = new ui();
//	uifunction testgui	= new uifunction();
	
	public core() {
		
	}
	public static void main(String[] args) {
		try
		{
			core MAIN = new core();
			MAIN.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run() throws Exception {
		System.out.println("Point0");
		programm.run();
	}
	
	
//	public guiHandler getGui() {
//		return gui;
//	}
//	public mainprogram getProgramm() {
//		return programm;
//	}
}

//if(testgui.checkedInput<2) {
//do {
//testgui.request();
//System.out.println("reseved:" + testgui.checkedInput);
//}while(testgui.inputfound()<2);//TODO ist das richtig?
//}
