package com.pischyk.task3.builder;

import com.pischyk.task3.entity.*;
import com.pischyk.task3.exception.GemException;
import com.pischyk.task3.handler.GemXmlTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class GemStaxBuilder extends AbstractGemBuilder {
    private static Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;

    public GemStaxBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetGems(String filename) throws GemException {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(filename))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(GemXmlTag.PRECIOUS.getTitle())) {
                        Precious precious = buildPrecious(reader);
                        gems.add(precious);
                    } else if (name.equals(GemXmlTag.SEMIPRECIOUS.getTitle())) {
                        Semiprecious semiprecious = buildSemiprecious(reader);
                        gems.add(semiprecious);
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException ex) {
            logger.log(Level.ERROR, "Error in Stax: " + ex.getMessage());
            throw new GemException("Error in Stax: " + ex.getMessage());
        } catch (IOException ex) {
            logger.log(Level.ERROR, "Error in Stax, check your filename: " + filename);
            throw new GemException("Error in Stax, check your filename: " + filename);
        }
        logger.log(Level.INFO, "Minerals from stax builder are:\n" + gems);
    }

    private Precious buildPrecious(XMLStreamReader reader) throws XMLStreamException {
        Precious precious = new Precious();
        Name name_of_precious = Name.valueOf(reader.getAttributeValue(null, GemXmlTag.NAME.getTitle()).toUpperCase());
        precious.setName(name_of_precious);
        build(reader, precious);
        return precious;
    }

    private Semiprecious buildSemiprecious(XMLStreamReader reader) throws XMLStreamException {
        Semiprecious semiprecious = new Semiprecious();
        semiprecious.setName(reader.getAttributeValue(null, GemXmlTag.NAME.getTitle()));
        build(reader, semiprecious);
        return semiprecious;
    }


    private void build(XMLStreamReader reader, Gem gem) throws XMLStreamException {
        gem.setOrigin(reader.getAttributeValue(null, GemXmlTag.ORIGIN.getTitle()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (GemXmlTag.valueOf(name.toUpperCase().replace("-", "_"))) {
                        case VISUAL_PARAMETERS:
                            gem.setParameters(getXMLParameters(reader));
                            break;
                        case VALUE:
                            gem.setValue(Double.parseDouble(getXMLText(reader)));
                            break;
                        case DATE_OF_PROCESSING:
                            gem.setDate(LocalDate.parse(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (GemXmlTag.valueOf(name.toUpperCase().replace("-", "_")) == GemXmlTag.PRECIOUS ||
                            GemXmlTag.valueOf(name.toUpperCase().replace("-", "_")) == GemXmlTag.SEMIPRECIOUS) {
                        return;
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <precious>");
    }

    private VisualParameters getXMLParameters(XMLStreamReader reader) throws XMLStreamException {
        VisualParameters parameters = new VisualParameters();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (GemXmlTag.valueOf(name.toUpperCase().replace("-", "_"))) {
                        case COLOR:
                            parameters.setColor(getXMLText(reader));
                            break;
                        case TRANSPARENCY:
                            parameters.setTransparency(Integer.parseInt(getXMLText(reader)));
                            break;
                        case CUT:
                            parameters.setCut(Integer.parseInt(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (GemXmlTag.valueOf(name.toUpperCase().replace("-", "_")) == GemXmlTag.VISUAL_PARAMETERS) {
                        return parameters;
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <visual-parameters>");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
