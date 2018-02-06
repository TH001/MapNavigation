/**
 * Name			: core.java
 * Author		: Tom
 * Created on	: 26.01.2018
 * Description	: empty
 */
package mapnavigation;

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
		programm.run();
//		ui1.create();					//User Interface erstellen
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
