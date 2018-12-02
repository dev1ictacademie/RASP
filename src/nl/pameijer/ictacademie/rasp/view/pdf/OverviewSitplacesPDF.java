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
public class OverviewSitplacesPDF
{
    private ExportToPDF exporter;

	/**
     * Constructor.
     */
    public OverviewSitplacesPDF()
    {
    	exporter = new ExportToPDF();

    }

    /**
     * creates a sit place overview document with a landscape orientation.
     *
     * @param message The message to write in the file.
     * @param outfile The resulting PDF.
     *
     * @throws IOException If there is an error writing the data.
     */
    public void createPDFDoc( String  outfile ) throws IOException
    {
        try (PDDocument doc = new PDDocument())
        {
            PDFont font = PDType1Font.HELVETICA;
            PDPage page = new PDPage(PDRectangle.A4);
            page.setRotation(90);
            doc.addPage(page);
            PDRectangle pageSize = page.getMediaBox();
            float pageWidth = pageSize.getWidth();
            float fontSize = 10f;
            Color backGroundColor = Color.WHITE;

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false))
            {
                // add the rotation using the current transformation matrix
                // including a translation of pageWidth to use the lower left corner as 0,0 reference
                contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

                // draw Monday overview
                float startX = 40, startY = 360;

                drawFilledRectangles(contentStream, startX, startY, 30f, 10f);
                drawDayOverview(contentStream, startX, startY, font, fontSize, "Maandag",
                		exporter.getPlacesOverview(DayPart.MONDAY_MORNING, "ICT"),
                		exporter.getPlacesOverview(DayPart.MONDAY_AFTERNOON, "ICT"), backGroundColor);


                // draw Tuesday overview
                startX += 250;
                drawFilledRectangles(contentStream, startX, startY, 30f, 10f);
                drawDayOverview(contentStream, startX, startY, font, fontSize, "Dinsdag",
                		exporter.getPlacesOverview(DayPart.TUESDAY_MORNING, "ICT"),
                		exporter.getPlacesOverview(DayPart.TUESDAY_AFTERNOON, "ICT"), backGroundColor);


                // draw Wednesday overview
                startX += 250;
                drawFilledRectangles(contentStream, startX, startY, 30f, 10f);
                drawDayOverview(contentStream, startX, startY, font, fontSize, "Woensdag",
                		exporter.getPlacesOverview(DayPart.WEDNESDAY_MORNING, "ICT"),
                		exporter.getPlacesOverview(DayPart.WEDNESDAY_AFTERNOON, "ICT"), backGroundColor);


                // draw Thursday overview
                startX = 40; startY -= 260;
                drawFilledRectangles(contentStream, startX, startY, 30f, 10f);
                drawDayOverview(contentStream, startX, startY, font, fontSize, "Donderdag",
                		exporter.getPlacesOverview(DayPart.THURSDAY_MORNING, "ICT"),
                		exporter.getPlacesOverview(DayPart.THURSDAY_AFTERNOON, "ICT"), backGroundColor);


                // draw Friday overview
                startX += 250;
                drawFilledRectangles(contentStream, startX, startY, 30f, 10f);
                drawDayOverview(contentStream, startX, startY, font, fontSize, "Vrijdag",
                		exporter.getPlacesOverview(DayPart.FRIDAY_MORNING, "ICT"),
                		exporter.getPlacesOverview(DayPart.FRIDAY_AFTERNOON, "ICT"), backGroundColor);

            }

            doc.save( outfile );
        }// end try catch

    }// end method createPDFDoc

    // Draw overview of one day
    public void drawDayOverview( PDPageContentStream contentStream, float startX, float startY,
    		PDFont font, float fontSize, String day, String[] morning, String[] afternoon, Color backGroundColor )
    {
    	try
    	{
    		// fill head of day overview with color
    		Color headColor = new Color(30,144,255);
    		contentStream.addRect( startX-2, startY+160, 204, 40);
    		contentStream.setNonStrokingColor(headColor);
            contentStream.fill();

            // set text color white
            contentStream.setNonStrokingColor(Color.WHITE);
	        contentStream.setFont( font, fontSize );

	        // place day name in surrounded box
	        contentStream.beginText();
	        contentStream.newLineAtOffset(startX+80, startY+190);
	        contentStream.showText(day);
	        contentStream.endText();

	        contentStream.setFont( font, 8f );
	        contentStream.beginText();
	        contentStream.newLineAtOffset(startX, startY+170);
	        contentStream.showText("plaats");
	        contentStream.endText();

	        contentStream.beginText();
	        contentStream.newLineAtOffset(startX+40, startY+170);
	        contentStream.showText("ochtend");
	        contentStream.endText();

	        contentStream.beginText();
	        contentStream.newLineAtOffset(startX+125, startY+170);
	        contentStream.showText("middag");
	        contentStream.endText();

	        contentStream.setNonStrokingColor(Color.BLACK);
	        // line under plaats, ochtend, middag
	        contentStream.moveTo(startX-2, startY+160);
	        contentStream.lineTo(startX+202, startY+160);
	        contentStream.stroke();

	        // line right of numbers column
	        contentStream.moveTo(startX+28, startY-20);
	        contentStream.lineTo(startX+28, startY+160);
	        contentStream.stroke();

	        // place numbers 1 to 18
	        for (int i = 1; i < 19; i++)
	        {
	        	if( i < 10) // place numbers from 1 up to 9 include
	        	{
	        		contentStream.beginText();
	                contentStream.newLineAtOffset(startX+6, startY+162-i*10);
	                contentStream.showText("  " + i);
	                contentStream.endText();
	        	}
	        	else //// place numbers from 10 up to 18 include
	        	{
	        		contentStream.beginText();
	                contentStream.newLineAtOffset(startX+6, startY+162-i*10);
	                contentStream.showText("" + i);
	                contentStream.endText();
	        	}

	        	// place line between sit place numbers
            	contentStream.moveTo(startX-2, startY+160-i*10);
            	contentStream.lineTo(startX+28, startY+160-i*10);
            	contentStream.stroke();

			}// end for

	        // fill names in columns
	        for(int i = 0; i < 18; i++)
	        {
	        	font = PDType1Font.HELVETICA;
	        	fontSize = 8;

	        	contentStream.beginText();
	        	contentStream.setFont(font, fontSize);
		        contentStream.newLineAtOffset(startX+40, startY+152-i*10);
		        contentStream.showText( getFirstName(morning[i], i) );
		        contentStream.endText();

		        contentStream.beginText();
	        	contentStream.setFont(font, fontSize);
		        contentStream.newLineAtOffset(startX+125, startY+152-i*10);
		        contentStream.showText( getFirstName(afternoon[i], i) );
		        contentStream.endText();

		     }

	        //left line
            contentStream.moveTo(startX-2, startY-20.5f);
            contentStream.lineTo(startX-2, startY+200.5f);
            contentStream.stroke();

            //top line
            contentStream.moveTo(startX-2, startY+200);
            contentStream.lineTo(startX+202, startY+200);
            contentStream.stroke();

            //right line
            contentStream.moveTo(startX+202, startY+200.5f);
            contentStream.lineTo(startX+202, startY-20.5f);
            contentStream.stroke();

            //bottom line
            contentStream.moveTo(startX+202, startY-20);
            contentStream.lineTo(startX-2, startY-20);
            contentStream.stroke();
    	}
    	catch (Exception e)
    	{
			e.printStackTrace();
		}

	}// end method dayOverview

    // Draw a filled rectangle placed in rectangles of sit place numbers
    public void drawFilledRectangles(PDPageContentStream contentStream,
    		float x, float y, float width, float height)
    {
    	Color white = Color.WHITE;
    	Color green = new Color(154,205,50);
    	Color orange = new Color(255,140,0);
    	Color blue = new Color(100,149,237);

    	for (int i = 0; i < 18; i++)
    	{
			try
			{
				if(i == 0)
				{
					contentStream.addRect( x-2, y+150-i*10, width, height);
	        		contentStream.setNonStrokingColor(white);
	                contentStream.fill();
				}
				else if(i > 0 & i < 6)
	        	{
	        		contentStream.addRect( x-2, y+150-i*10, width, height);
	        		contentStream.setNonStrokingColor(green);
	                contentStream.fill();
	        	}
	        	else if(i > 5 & i < 10)
	        	{
	        		contentStream.addRect( x-2, y+150-i*10, width, height);
	        		contentStream.setNonStrokingColor(orange);
	                contentStream.fill();
	        	}
	        	else if(i == 10)
	        	{
	        		contentStream.addRect( x-2, y+150-i*10, width, height);
	        		contentStream.setNonStrokingColor(white);
	                contentStream.fill();
	        	}
	        	else if(i == 11)
	        	{
	        		contentStream.addRect( x-2, y+150-i*10, width, height);
	        		contentStream.setNonStrokingColor(orange);
	                contentStream.fill();
	        	}
	        	else
	        	{
	        		contentStream.addRect( x-2, y+150-i*10, width, height);
	        		contentStream.setNonStrokingColor(blue);
	                contentStream.fill();
	        	}
			}
			catch (Exception e)
			{

			}

    	}// end for

    }// end method drawRect

    /**
     * get the first name from array of students
     * @param dayPart String
     * @param i of loop
     * @return
     */
    public String getFirstName(String dayPart, int i)
    {
    	String fName = "";

    	try
    	{
    		if(i < 10) // sit places under ten
    		{
    			fName = dayPart.substring(2);
    			if( !fName.isEmpty() )
    			{
    				return fName;
    			}
    		}
        	else // sit places from ten and beyond
        	{
        		fName = dayPart.substring(3);
        		if(!fName.isEmpty())
        		{
        			return fName;
        		}
        	}
		}
    	catch (Exception e)
    	{
    		// ignored
		}

		return "";
	}// end method getFirstName

    /**
     * This will create a PDF document with a landscape orientation and some text surrounded by a box.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException
    {
        OverviewSitplacesPDF app = new OverviewSitplacesPDF();
        app.createPDFDoc( args[1] );

        System.exit(0);
    }// end method main

}// end class CreateLandscapePDF
