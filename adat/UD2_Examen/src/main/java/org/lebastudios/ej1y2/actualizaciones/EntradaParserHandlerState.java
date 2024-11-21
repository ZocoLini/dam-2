package org.lebastudios.ej1y2.actualizaciones;

import org.lebastudios.parser.ParserHandlerState;
import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EntradaParserHandlerState extends ParserHandlerState<Entrada>
{
    private String actualElement = "";
    
    public EntradaParserHandlerState(StateMachineParserHandler parserHandler, Entrada element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
       switch (qName) 
       {
           case "Rio":
               Rio rio = new Rio();
               element.setRio(rio);
               parserHandler.addState(new RioParserHandlerState(parserHandler, rio, attributes));
               break;
           case "Datos":
               Datos datos = new Datos();
               element.setDatos(datos);
               parserHandler.addState(new DatosParserHandlerState(parserHandler, datos, attributes));
               break;
       }
        actualElement = qName;
    }

    @Override
    public void text(String text) throws SAXException
    {
        if (actualElement.equals("Fecha")) 
        {
            element.setFecha(DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(text, LocalDate::from));
        }
        actualElement = "";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("Entrada")) 
        {
            parserHandler.popState();
        }
    }

    @Override
    protected void setAttributes(Attributes attributes)
    {
        element.setNumero(attributes.getValue("numero"));
    }
}
