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
public class CreateLandscapePDF
{
    private ExportToPDF exporter;

	/**
     * Constructor.
     */
    public CreateLandscapePDF()
    {
    	exporter = new ExportToPDF();
		String[] arr = exporter.getPlacesOverview(DayPart.MONDAY_MORNING, "ICT");
		for (String s: arr) {
			System.out.println(s);
		}

    }

    /**
     * creates a sit place overview document with a landscape orientation.
     *
     * @param message The message to write in the file.
     * @param outfile The resulting PDF.
     *
     * @throws IOException If there is an error writing the data.
     */
    public void createPDFDoc( String message, String  outfile ) throws IOException
    {
        try (PDDocument doc = new PDDocument())
        {
            PDFont font = PDType1Font.HELVETICA;
            PDPage page = new PDPage(PDRectangle.A4);
            page.setRotation(90);// 0 = portrait, 90 = landscape
            doc.addPage(page);
            PDRectangle pageSize = page.getMediaBox();
            float pageWidth = pageSize.getWidth();
            float fontSize = 10;
            float startX = 30;
            float startY = 370;

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.OVERWRITE, false))
            {
                // add the rotation using the current transformation matrix
                // including a translation of pageWidth to use the lower left corner as 0,0 reference
                contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

                contentStream.setFont( font, fontSize );
                contentStream.beginText();


                contentStream.newLineAtOffset(135, 560);
                contentStream.showText("Maandag");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(30, 540);
                contentStream.showText("plaats");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(80, 540);
                contentStream.showText("ochtend");
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(160, 540);
                contentStream.showText("middag");
                contentStream.endText();

                contentStream.moveTo(startX-2, startY+160);
                contentStream.lineTo(startX+200+2, startY+160);
                contentStream.stroke();

                //line under plaats, ochtend, middag
                contentStream.moveTo(startX-2+30, startY-2-20);
                contentStream.lineTo(startX-2+30, startY+160);
                contentStream.stroke();


                for (int i = 1; i < 19; i++)
                {
                	if( i < 10)
                	{
                		contentStream.beginText();
                		//contentStream.newLineAtOffset(36, 531-i*10);
	                    contentStream.newLineAtOffset(36, 531-i*10);
	                    contentStream.showText("  " + i);
	                    contentStream.endText();
                	}
                	else
                	{
                		contentStream.beginText();
	                    contentStream.newLineAtOffset(36, 531-i*10);
	                    contentStream.showText("" + i);
	                    contentStream.endText();
                	}


                    if(i < 18)
                    {
                    	contentStream.moveTo(startX-2, startY+160-i*10);
                    	contentStream.lineTo(startX+26+2, startY+160-i*10);
                    	contentStream.stroke();
                    }

				}// end for


                //ochtend
                contentStream.beginText();
                contentStream.newLineAtOffset(65, 531-1*10);
                contentStream.showText("Piet");
                contentStream.endText();



                //left
                contentStream.moveTo(startX-2, startY-2-20);
                contentStream.lineTo(startX-2, startY+200);
                contentStream.stroke();

                //above
                contentStream.moveTo(startX-2, startY+200);
                contentStream.lineTo(startX+200+2, startY+200);
                contentStream.stroke();

                //right
                contentStream.moveTo(startX+200+2, startY+200);
                contentStream.lineTo(startX+200+2, startY-2-20);
                contentStream.stroke();

                contentStream.moveTo(startX+200+2, startY-2-20);
                contentStream.lineTo(startX-2, startY-2-20);
                contentStream.stroke();
            }

            doc.save( outfile );
        }
    }

    /**
     * This will create a PDF document with a landscape orientation and some text surrounded by a box.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException
    {
        CreateLandscapePDF app = new CreateLandscapePDF();
        if( args.length != 2 )
        {

        }
        else
        {
            app.createPDFDoc( args[0], args[1] );
        }
        System.exit(0);
    }


}
