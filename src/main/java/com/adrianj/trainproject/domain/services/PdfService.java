package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

@Log4j2
@Service
public class PdfService {

    public byte[] createTicketPdf(Ticket ticket) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell();

            cell.addElement(new Chunk("Ticket ID: " + ticket.getId() + " " + "Train: " + ticket.getEndStops().getTrainStops().getNumber() + " Seat: " + ticket.getSeat() + "/" + ticket.getEndStops().getTrainStops().getSeats() + "\n"));
            cell.addElement(new Chunk("Passenger: " + ticket.getPassenger().getName() + " " + ticket.getPassenger().getSurname() + "\n"));
            cell.addElement(new Chunk("Start Station: " + ticket.getStartStops().getStationStop().getName() + " Time: " + ticket.getStartStops().getTime() + "\n"));
            cell.addElement(new Chunk("End Station: " + ticket.getEndStops().getStationStop().getName() + " Time: " + ticket.getEndStops().getTime() + "\n"));

            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(2f);
            cell.setPadding(10);

            table.addCell(cell);
            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}

