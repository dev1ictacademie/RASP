package nl.pameijer.ictacademie.rasp.view.pdf;

import java.awt.Desktop;
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartSendPDFView extends Application {
	@Override
	public void start(Stage primary) 
	{
		try
		{
	        File myFile = new File("/OverviewSitplaces.pdf");
	        Desktop.getDesktop().open(myFile);
	        
			SendPDFView view = new SendPDFView();
			
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		launch(args);

	}
}
