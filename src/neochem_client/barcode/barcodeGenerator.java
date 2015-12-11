/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.barcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javafx.scene.control.Alert;
import javax.swing.ImageIcon;
import neochem_client.dialogs.WarningE;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

/**
 *
 * @author JanithaT
 */
public class barcodeGenerator {
    public WarningE alerts = new WarningE();

    public static void generateBarCode(String code) throws BarcodeException {

        
        Barcode barcode = BarcodeFactory.createCode128(code);
        barcode.setBarHeight(50);
        barcode.setBarWidth(1);
        barcode.setBackground(Color.WHITE);
        System.out.println("GENERATED");

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BarcodeImageHandler.writePNG(barcode, baos);
            File f = new File("mybarcode.png");
            f.setReadable(true);
            FileOutputStream fos = new FileOutputStream(f);
            BarcodeImageHandler.writePNG(barcode, fos);
            
            fos.close();
            
            //BarcodeImageHandler.savePNG(barcode, f);

            Image img = new ImageIcon(".\\mybarcode.png").getImage();
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setPrintable(new Printable() {
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex != 0) {
                        return NO_SUCH_PAGE;
                    }
                    graphics.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
                    return PAGE_EXISTS;
                }
            });
            if (printJob.printDialog()) {
                try {
                    printJob.print();
                } catch (Exception prt) {
                    Alert alert = WarningE.getWarningExceptionStatic(prt);
                    alert.showAndWait();
                    System.err.println(prt.getMessage());
                    
                }
            }
            // Let the barcode image handler do the hard work
         img.flush();
        } catch (Exception e) {
            Alert alert = WarningE.getWarningExceptionStatic(e);
            alert.showAndWait();
        }
        
        
    }
}
