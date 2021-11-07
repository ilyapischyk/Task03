package com.pischyk.task3.builder;

import com.pischyk.task3.exception.GemException;
import com.pischyk.task3.handler.GemErrorHandler;
import com.pischyk.task3.handler.GemHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class GemSaxBuilder extends AbstractGemBuilder {
    private static Logger logger = LogManager.getLogger();
    private GemHandler handler = new GemHandler();
    private XMLReader reader;

    public GemSaxBuilder() {
        super();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException ex) {
            logger.log(Level.ERROR, "Error in Sax: " + ex.getMessage());

        }
        reader.setErrorHandler(new GemErrorHandler());
        reader.setContentHandler(handler);
    }

    @Override
    public void buildSetGems(String filename) throws GemException {
        try {
            reader.parse(filename);
        } catch (IOException ex) {
            logger.log(Level.ERROR, "Error in Sax, check your filename: " + filename);
            throw new GemException("Error in Sax, check your filename: " + filename);
        } catch (SAXException ex) {
            logger.log(Level.ERROR, "Error in Sax: " + ex.getMessage());
            throw new GemException("Error in Sax: " + ex.getMessage());
        }
        gems = handler.getGems();
        logger.log(Level.INFO, "Minerals from sax builder are:\n" + gems);
    }
}
