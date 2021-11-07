package com.pischyk.task3.handler;

import com.pischyk.task3.entity.Gem;
import com.pischyk.task3.entity.Name;
import com.pischyk.task3.entity.Precious;
import com.pischyk.task3.entity.Semiprecious;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class GemHandler extends DefaultHandler {
    private Set<Gem> gems;
    private Gem current;
    private GemXmlTag currentXmlTag;
    private EnumSet<GemXmlTag> withText;

    public GemHandler() {
        gems = new HashSet<>();
        withText = EnumSet.range(GemXmlTag.COLOR, GemXmlTag.DATE_OF_PROCESSING);
    }

    public Set<Gem> getGems() {
        return gems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (GemXmlTag.PRECIOUS.getTitle().equals(qName)) {
            current = new Precious();
            Name name = Name.valueOf(attrs.getValue(0).toUpperCase());
            ((Precious) current).setName(name);
            if (attrs.getLength() == 2) {
                current.setOrigin(attrs.getValue(1));
            }
        } else if (GemXmlTag.SEMIPRECIOUS.getTitle().equals(qName)) {
            current = new Semiprecious();
            ((Semiprecious) current).setName(attrs.getValue(0));
            if (attrs.getLength() == 2) {
                current.setOrigin(attrs.getValue(1));
            }
        } else {
            GemXmlTag temp = GemXmlTag.valueOf(qName.toUpperCase().replace("-", "_"));
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (GemXmlTag.PRECIOUS.getTitle().equals(qName)
                || GemXmlTag.SEMIPRECIOUS.getTitle().equals(qName)) {
            gems.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case COLOR:
                    current.getParameters().setColor(data);
                    break;
                case TRANSPARENCY:
                    current.getParameters().setTransparency(Integer.parseInt(data));
                    break;
                case CUT:
                    current.getParameters().setCut(Integer.parseInt(data));
                    break;
                case VALUE:
                    current.setValue(Double.parseDouble(data));
                    break;
                case DATE_OF_PROCESSING:
                    current.setDate(LocalDate.parse(data));
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
