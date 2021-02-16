/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.components;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.TextBoundsProvider;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.elements.LabelElementProps;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The component used to render the label.
 *
 * @author sbegaudeau
 */
public class LabelComponent implements IComponent {

    private final LabelComponentProps props;

    public LabelComponent(LabelComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        LabelDescription labelDescription = this.props.getLabelDescription();
        Optional<Label> optionalPreviousLabel = this.props.getPreviousLabel();
        ILabelPositionProvider labelBoundsProvider = this.props.getLabelBoundsProvider();
        String type = this.props.getType();

        String id = labelDescription.getIdProvider().apply(variableManager);
        String text = labelDescription.getTextProvider().apply(variableManager);

        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescriptionProvider().apply(variableManager);

        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean bold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean italic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean strikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        Boolean underline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        String iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);

        // @formatter:off
        var labelStyle = LabelStyle.newLabelStyle()
                .color(color)
                .fontSize(fontSize)
                .bold(bold)
                .italic(italic)
                .strikeThrough(strikeThrough)
                .underline(underline)
                .iconURL(iconURL)
                .build();
        // @formatter:on

        // @formatter:off
        TextBounds textBounds = new TextBoundsProvider().computeBounds(labelStyle, text);
        Position position = labelBoundsProvider.getPosition(optionalPreviousLabel, textBounds, type);
        Position alignment = optionalPreviousLabel.map(Label::getAlignment).orElse(textBounds.getAlignment());
        Size size = optionalPreviousLabel.map(Label::getSize).orElse(textBounds.getSize());

        LabelElementProps labelElementProps = LabelElementProps.newLabelElementProps(id)
                .type(type)
                .text(text)
                .position(position)
                .size(size)
                .alignment(alignment)
                .style(labelStyle)
                .build();
        // @formatter:on
        return new Element(LabelElementProps.TYPE, labelElementProps);
    }

}
