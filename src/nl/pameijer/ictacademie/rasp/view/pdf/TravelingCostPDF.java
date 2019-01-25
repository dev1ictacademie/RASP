/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.pameijer.ictacademie.rasp.view.pdf;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.view.tools.ExportToPDF;

/**
 * This create a page with a landscape orientation.
 */
public class TravelingCostPDF
{
	float startX = 50.0f;
	float startY = 640.0f;

    private ExportToPDF exporter;

	/**
     * Constructor.
     */
    public TravelingCostPDF()
    {
    	exporter = new ExportToPDF();

    }

    /**
     * creates a sit place overview document with a portrait orientation.
     *
     * @param outfile The resulting PDF.
     */
    public void createPDFDoc( String  outfile ) throws IOException
    {
    	 try (PDDocument doc = new PDDocument())
         {
    		 float fontSize = 10f;
             PDPage page = new PDPage();
             doc.addPage(page);

             try (PDPageContentStream contents = new PDPageContentStream(doc, page))
             {
                 headText(contents, TravelingCostView.getMonth(), TravelingCostView.getYear());
                 drawHeaderTable(contents, fontSize);
                 drawContentTable(contents, TravelingCostView.getNames(), TravelingCostView.getId(),
                		 					TravelingCostView.getTotal(), TravelingCostView.getIban());

             }

             doc.save(outfile);
         }

    }// end method createPDFDoc

    /**
     * places the head text
     * @param month
     * @param year
     */
    public void headText( PDPageContentStream contents, String month, String year )
    {
    	PDFont font = PDType1Font.HELVETICA_BOLD;

    	try
    	{
    		contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(50, 740);
            contents.showText("Uit te betalen dagdeel- en reiskostenvergoedingen");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(50, 725);
            contents.showText("Locatie:                Werkt Centrum -ICT Academie");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(50, 710);
            contents.showText("Kostenplaats:      8000024");
            contents.endText();
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(50, 695);
            contents.showText("Kostensoort:       46231 - vergoedingen deelnemers");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(50, 680);
            contents.showText("Periode:               " + month + " " + year);
            contents.endText();

		}
    	catch (Exception e)
    	{

		}

	}// end headText

	/**
	 * draws the header of the table
	 * @param contentStream
	 * @param fontSize
	 */
    public void drawHeaderTable( PDPageContentStream contentStream, Float fontSize )
    {
    	try
    	{
            // set background color
            Color blue = new Color(100,149,237);
            contentStream.addRect( startX-1.5f, startY-9.5f, startX+471, 29);
    		contentStream.setNonStrokingColor(blue);
            contentStream.fill();

            PDFont font = PDType1Font.HELVETICA_BOLD;

            contentStream.setNonStrokingColor(Color.WHITE);// set text color to white
	        contentStream.setFont( font, fontSize );
			contentStream.setFont(font, 12);

			contentStream.beginText();
            contentStream.newLineAtOffset(startX+50, startY);
            contentStream.showText("Naam Plancare");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(startX+197, startY);
            contentStream.showText("Plancare ID");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(startX+285, startY);
            contentStream.showText("Totaal");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(startX+410, startY);
            contentStream.showText("IBAN");
            contentStream.endText();

            // vertical lines
            contentStream.moveTo(startX-2, startY+20.4f);
            contentStream.lineTo(startX-2, startY-10.0f);
            contentStream.stroke();

            contentStream.moveTo(startX+190.0f, startY+20.0f);// name column
            contentStream.lineTo(startX+190.0f, startY-10.0f);
            contentStream.stroke();

            contentStream.moveTo(startX+270.0f, startY+20.0f);// ID column
            contentStream.lineTo(startX+270.0f, startY-10.0f);
            contentStream.stroke();

            contentStream.moveTo(startX+340.0f, startY+20.0f);// total € column
            contentStream.lineTo(startX+340.0f, startY-10.0f);
            contentStream.stroke();


            //bottom horizontal line
            contentStream.moveTo(startX-2, startY-10.0f);
            contentStream.lineTo(startX+520, startY-10.0f);
            contentStream.stroke();

            //right vertical line
            contentStream.moveTo(startX+520, startY+20.4f);
            contentStream.lineTo(startX+520, startY-10.0f);
            contentStream.stroke();

    		//top line
            contentStream.moveTo(startX-2, startY+20);
            contentStream.lineTo(startX+520, startY+20);
            contentStream.stroke();

		}
    	catch (Exception e)
    	{

		}
	}// end method drawHeaderTable

    /**
     * Draws the content and lines of the table. Parameter name is used to determine
     * the number of rows in the table and must not be empty.
     * The name is shortened to 28 characters include spaces.
     * @param contentStream
     * @param name
     * @param id
     * @param total
     * @param iban
     */
    public void drawContentTable( PDPageContentStream contentStream, String[] name, String[] id,
    							  String[] total, String[] iban )
    {

    	startY -= 10.0f;
    	try
    	{
    		//left vertical line
            contentStream.moveTo(startX-2, startY+20.4f);
            contentStream.lineTo(startX-2, startY-20.0f*name.length);
            contentStream.stroke();

            //vertical lines to make columns
            contentStream.moveTo(startX+190.0f, startY);// name column
            contentStream.lineTo(startX+190.0f, startY-20.0f*name.length);
            contentStream.stroke();

            contentStream.moveTo(startX+270.0f, startY);// ID column
            contentStream.lineTo(startX+270.0f, startY-20.0f*name.length);
            contentStream.stroke();

            contentStream.moveTo(startX+340.0f, startY);// total € column
            contentStream.lineTo(startX+340.0f, startY-20.0f*name.length);
            contentStream.stroke();

            //right vertical line
            contentStream.moveTo(startX+520, startY+20.4f);
            contentStream.lineTo(startX+520, startY-20.0f*name.length);
            contentStream.stroke();

            contentStream.setNonStrokingColor(Color.BLACK);// set text color to black

            int j = 1;
            // loop through names array to fill the rows
    		for (int i = 0; i < name.length; i++)
    		{
    			// fill name column
    			if(name[i].length() > 24)
    			{
    				StringBuilder sb = new StringBuilder();
    				sb.append(name[i]).delete(28, sb.length());
    				contentStream.beginText();
                    contentStream.newLineAtOffset(startX+5, startY-20.0f*j+5);
                    contentStream.showText(sb.toString());
                    contentStream.endText();
    			}
    			else
    			{
    				contentStream.beginText();
    				contentStream.newLineAtOffset(startX+5, startY-20.0f*j+5);
    				contentStream.showText(name[i]);
    				contentStream.endText();
    			}

                // fill id column
    			if(i < id.length)
    			{

	                contentStream.beginText();
	                contentStream.newLineAtOffset(startX+195, startY-20.0f*j+5);
	                contentStream.showText(id[i]);
	                contentStream.endText();
    			}
    			else
    			{
//    				contentStream.beginText();
//	                contentStream.newLineAtOffset(startX+195, startY-20.0f*j+5);
//	                contentStream.showText("0");
//	                contentStream.endText();
    			}

                // fill total column
    			if(i < total.length)
    			{
	                contentStream.beginText();
	                contentStream.newLineAtOffset(startX+275, startY-20.0f*j+5);
	                contentStream.showText("€ "+total[i]);
	                contentStream.endText();
    			}
    			else
    			{
//    				contentStream.beginText();
//	                contentStream.newLineAtOffset(startX+275, startY-20.0f*j+5);
//	                contentStream.showText("0");
//	                contentStream.endText();
    			}

                // fill iban column
    			if(i < iban.length)
    			{
	                contentStream.beginText();
	                contentStream.newLineAtOffset(startX+345, startY-20.0f*j+5);
	                contentStream.showText(iban[i]);
	                contentStream.endText();
    			}
    			else
    			{
//    				contentStream.beginText();
//	                contentStream.newLineAtOffset(startX+345, startY-20.0f*j+5);
//	                contentStream.showText("0");
//	                contentStream.endText();
    			}

    			//between row horizontal line
                contentStream.moveTo(startX-2, startY-20.0f*j);
                contentStream.lineTo(startX+520, startY-20.0f*j);
                contentStream.stroke();

                j++;
			} // end for

    		//bottom horizontal line
            contentStream.moveTo(startX-2, startY-20.0f*name.length);
            contentStream.lineTo(startX+520, startY-20.0f*name.length);
            contentStream.stroke();

		}
    	catch (Exception e)
    	{

		}

	}// end drawContentTable


    /**
     * This will create a PDF document with a landscape orientation and some text surrounded by a box.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException
    {
        TravelingCostPDF app = new TravelingCostPDF();
        app.createPDFDoc( args[1] );// set name of document in run configurations...
        	//Program arguments: "TravelingCost" TravelingCost.pdf
        	//VM arguments: -Dsun.java2d.cmm=sun.java2d.cmm.kcms.KcmsServiceProvider

        System.exit(0);
    }// end method main

}// end class CreateLandscapePDF