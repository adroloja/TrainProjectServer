package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Ticket;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.RoundDotsBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

@Log4j2
@Service
public class PdfService {

    public byte[] createTicketPdf(Ticket ticket) {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Color backgroundColor = new DeviceCmyk(0.2f, 0.1f, 0f, 0f);
            Color borderColor = new DeviceCmyk(0.4f, 0.2f, 0f, 0f);
            Color textColor = new DeviceCmyk(0f, 0f, 0f, 1f);


            Paragraph ticketInfo = new Paragraph()
                    .add("Ticket ID: " + ticket.getId() + " " + "\t\t\tTrain: " + ticket.getEndStops().getTrainStops().getNumber() + "\t\t\t Seat: " + ticket.getSeat() + "/" + ticket.getEndStops().getTrainStops().getSeats() + "\n").setBold().setFontColor(textColor)
                    .add("Passenger: " + ticket.getPassenger().getName() + " " + ticket.getPassenger().getSurname() + "\n").setBold().setFontColor(textColor)
                    .add("Start Station: " + ticket.getStartStops().getStationStop().getName() + "\t\t Time: " + ticket.getStartStops().getTime() + "\n").setBold().setFontColor(textColor)
                    .add("End Station: " + ticket.getEndStops().getStationStop().getName() + " \t\tTime: " + ticket.getEndStops().getTime() + "\n").setBold().setFontColor(textColor)
                    .setBorder(new RoundDotsBorder(borderColor, 2f))
                    .setPadding(10f)
                    .setMargins(20f, 20f, 20f, 20f)
                    .setTextAlignment(TextAlignment.LEFT);

            document.add(ticketInfo);
            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }

    }
}
