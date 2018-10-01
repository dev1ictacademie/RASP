package nl.pameijer.ictacademie.rasp.view.pdf;

import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartSendPDFView extends Application {
	@Override
	public void start(Stage primary)
	{
		try
		{
			OverviewSitplacesPDF overviewSitplacesPDF = new OverviewSitplacesPDF();
			File pdfFile = new File("OverviewSitplaces.pdf");
            getHostServices().showDocument(pdfFile.toURI().toString());

			SendPDFView view = new SendPDFView();

			System.exit(0);
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
