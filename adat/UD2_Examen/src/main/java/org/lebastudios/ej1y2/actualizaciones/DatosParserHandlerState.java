package org.lebastudios.ej1y2.actualizaciones;

import org.lebastudios.parser.ParserHandlerState;
import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DatosParserHandlerState extends ParserHandlerState<Datos>
{
    private String actualElement = "";
    
    public DatosParserHandlerState(StateMachineParserHandler parserHandler, Datos element, Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        switch(qName)
        {
            case "pH":
                PH ph = new PH();
                element.setPh(ph);
                parserHandler.addState(new PHParserHandlerState(parserHandler, ph, attributes));
                break;
            case "Oxigeno":
                element.setOxigeno(Float.parseFloat(attributes.getValue("valor")));
                break;
            case "Temperatura":
                element.setTemperatura(Integer.parseInt(attributes.getValue("grados")));
                break;
            case "calidad":
                break;
        }
        
        actualElement = qName;
    }

    @Override
    public void text(String text) throws SAXException
    {
        if (actualElement.equals("calidad")) 
        {
            element.setCalidad(text);
        }
        
        actualElement = "";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("Datos")) 
        {
            parserHandler.popState();
        }
    }
}
