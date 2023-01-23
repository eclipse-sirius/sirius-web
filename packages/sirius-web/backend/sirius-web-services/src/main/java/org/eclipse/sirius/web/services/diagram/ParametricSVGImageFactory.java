/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.services.diagram;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageFactory;
import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.ParametricSVGImage;
import org.eclipse.sirius.components.collaborative.diagrams.api.SVGAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Implementation of {@link IParametricSVGImageFactory}.
 *
 * @author lfasani
 */
@Service
public class ParametricSVGImageFactory implements IParametricSVGImageFactory {

    private static final String HEIGHT = "height";

    private static final String WIDTH = "width";

    private static final String RECTANGLE_ELEMENT_LABEL_ID = "labelRectangle";

    private static final String RECTANGLE_ELEMENT_MAIN_ID = "mainRectangle";

    private static final Integer PADDING = 5;

    private final Logger logger = LoggerFactory.getLogger(ParametricSVGImageFactory.class);

    private final List<IParametricSVGImageRegistry> parametricSVGImageServices;

    public ParametricSVGImageFactory(List<IParametricSVGImageRegistry> parametricSVGImageServices) {
        this.parametricSVGImageServices = parametricSVGImageServices;
    }

    @Override
    public Optional<byte[]> createSvg(String svgName, EnumMap<SVGAttribute, String> attributesValues) {

        // @formatter:off
        Optional<ParametricSVGImage> svgImageOpt = this.parametricSVGImageServices.stream()
            .flatMap(service-> service.getImages().stream())
            .filter(image -> {
                return svgName.equals(image.getId().toString());
            })
            .findFirst();
        // @formatter:on

        if (svgImageOpt.isPresent()) {
            ClassPathResource classPathResource = new ClassPathResource(svgImageOpt.get().getPath());
            try (InputStream inputStream = classPathResource.getInputStream()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                Document document = factory.newDocumentBuilder().parse(inputStream);
                XPath xpath = XPathFactory.newInstance().newXPath();

                // change the global size
                // @formatter:off
                Node svgNode = Optional.of(xpath.evaluate("/svg", document, XPathConstants.NODESET))
                        .filter(NodeList.class::isInstance)
                        .map(NodeList.class::cast)
                        .filter(nodeList -> nodeList.getLength() > 0)
                        .map(nodeList -> nodeList.item(0))
                        .orElse(null);
                // @formatter:on

                if (svgNode instanceof Element element) {
                    Double halfBorderSize = Double.valueOf(attributesValues.get(SVGAttribute.BORDERSIZE)) * 0.5d;
                    String realWidth = String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.WIDTH)) + halfBorderSize * 2);
                    String realHeight = String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.HEIGHT)) + halfBorderSize * 2);
                    String viewBox = String.format(Locale.US, "-%f -%f %s %s", halfBorderSize, halfBorderSize, realWidth, realHeight);
                    element.setAttribute(WIDTH, realWidth);
                    element.setAttribute(HEIGHT, realHeight);
                    element.setAttribute("viewBox", viewBox);

                }

                String expr = String.format("//*[contains(@id, '%s')]|//*[contains(@id, '%s')]", RECTANGLE_ELEMENT_LABEL_ID, RECTANGLE_ELEMENT_MAIN_ID);

                // @formatter:off
                Optional.of(xpath.evaluate(expr, document, XPathConstants.NODESET))
                        .filter(NodeList.class::isInstance)
                        .map(NodeList.class::cast)
                        .ifPresent(nodes-> {
                            for (int i = 0; i < nodes.getLength(); i++) {
                                Element node = (Element) nodes.item(i);
                                this.updateNode(attributesValues, node, svgImageOpt.get());
                            }
                        });
                // @formatter:on

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Source xmlSource = new DOMSource(document);
                Result outputTarget = new StreamResult(outputStream);
                try {
                    TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
                } catch (TransformerException | TransformerFactoryConfigurationError e) {
                    this.logger.warn(e.getMessage());
                }

                InputStream documentInputStream = new ByteArrayInputStream(outputStream.toByteArray());
                return Optional.of(documentInputStream.readAllBytes());
            } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
                this.logger.warn(e.getMessage());
            }
        }

        return Optional.empty();
    }

    private void updateNode(EnumMap<SVGAttribute, String> attributesValues, Element node, ParametricSVGImage parametricSVGImage) {
        String idValue = node.getAttributes().getNamedItem("id").getNodeValue();
        Node styleNode = node.getAttributes().getNamedItem("style");
        if (styleNode != null) {
            String style = styleNode.getNodeValue();
            for (SVGAttribute svgAttribute : attributesValues.keySet()) {
                String styleProperty = null;
                if (SVGAttribute.COLOR.equals(svgAttribute)) {
                    styleProperty = "fill";
                } else if (SVGAttribute.BORDERCOLOR.equals(svgAttribute)) {
                    styleProperty = "stroke";
                } else if (SVGAttribute.BORDERSIZE.equals(svgAttribute)) {
                    styleProperty = "stroke-width";
                } else if (SVGAttribute.BORDERSTYLE.equals(svgAttribute)) {
                    styleProperty = "stroke-dasharray";
                }
                if (styleProperty != null) {
                    style = this.updateStyleValue(style, styleProperty, attributesValues.get(svgAttribute));
                }
            }
            node.setAttribute("style", style);
        }
        if (RECTANGLE_ELEMENT_LABEL_ID.equals(idValue)) {
            // TODO we should use ICustomNodeLabelPositionProvider
            if (parametricSVGImage.getLabel().equals("Class")) {
                // The label is centered so the label area is the same size than the container
                node.setAttribute(WIDTH, attributesValues.get(SVGAttribute.WIDTH));
            } else {
                // The label is left aligned
                node.setAttribute(WIDTH, String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.LABELWIDTH)) + 2 * PADDING));
            }
            node.setAttribute(HEIGHT, String.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.LABELHEIGHT)) + 2 * PADDING));
        } else if (RECTANGLE_ELEMENT_MAIN_ID.equals(idValue)) {
            Double globalHeight = 100.;
            Double labelHeight = 10.;
            try {
                globalHeight = Double.valueOf(attributesValues.get(SVGAttribute.HEIGHT));
                labelHeight = Double.valueOf(Double.valueOf(attributesValues.get(SVGAttribute.LABELHEIGHT)) + 2 * PADDING);
            } catch (NumberFormatException e) {
                this.logger.error(e.getMessage());
            }
            Double mainRectangleYPosition = labelHeight;
            Double mainRectangleHeight = globalHeight - labelHeight;
            node.setAttribute("y", mainRectangleYPosition.toString());
            node.setAttribute(HEIGHT, mainRectangleHeight.toString());
            node.setAttribute(WIDTH, attributesValues.get(SVGAttribute.WIDTH));
        }
    }

    private String updateStyleValue(String style, String styleProperty, String newValue) {
        String updatedStyle = style;
        if (style.contains(styleProperty)) {
            updatedStyle = style.replaceFirst(styleProperty + ":(.*?)([;\"])", styleProperty + ":" + newValue + "$2");
        } else {
            updatedStyle = styleProperty + ":" + newValue + ";" + updatedStyle;
        }
        return updatedStyle;
    }
}
