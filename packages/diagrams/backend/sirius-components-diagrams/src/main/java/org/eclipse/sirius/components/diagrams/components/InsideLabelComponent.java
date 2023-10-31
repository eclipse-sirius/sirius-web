/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.elements.InsideLabelElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the inside label.
 *
 * @author gcoutable
 */
public class InsideLabelComponent implements IComponent {

    private final InsideLabelComponentProps props;

    public InsideLabelComponent(InsideLabelComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        InsideLabelDescription insideLabelDescription = this.props.getInsideLabelDescription();
        Optional<InsideLabel> optionalPreviousInsideLabel = this.props.getPreviousInsideLabel();
        String type = this.props.getType();
        String idFromProvider = insideLabelDescription.getIdProvider().apply(variableManager);
        String id = UUID.nameUUIDFromBytes(idFromProvider.getBytes()).toString();
        String text = insideLabelDescription.getTextProvider().apply(variableManager);

        boolean isHeader = insideLabelDescription.getIsHeaderProvider().apply(variableManager);
        boolean displayHeaderSeparator = false;
        if (isHeader) {
            type = LabelType.INSIDE_CENTER.getValue();
            displayHeaderSeparator = insideLabelDescription.getDisplayHeaderSeparatorProvider().apply(variableManager);
        }

        LabelStyleDescription labelStyleDescription = insideLabelDescription.getStyleDescriptionProvider().apply(variableManager);

        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean bold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean italic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean strikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        Boolean underline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        List<String> iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);

        InsideLabelLocation insideLabelLocation = insideLabelDescription.getInsideLabelLocation();
        Position position = optionalPreviousInsideLabel.map(InsideLabel::getPosition).orElse(Position.UNDEFINED);
        Size size = optionalPreviousInsideLabel.map(InsideLabel::getSize).orElse(Size.UNDEFINED);
        Position aligment = optionalPreviousInsideLabel.map(InsideLabel::getAlignment).orElse(Position.UNDEFINED);

        var labelStyle = LabelStyle.newLabelStyle()
                .color(color)
                .fontSize(fontSize)
                .bold(bold)
                .italic(italic)
                .strikeThrough(strikeThrough)
                .underline(underline)
                .iconURL(iconURL)
                .build();

        InsideLabelElementProps insideLabelElementProps = InsideLabelElementProps.newInsideLabelElementProps(id)
                .type(type)
                .text(text)
                .insideLabelLocation(insideLabelLocation)
                .position(position)
                .size(size)
                .alignment(aligment)
                .style(labelStyle)
                .isHeader(isHeader)
                .displayHeaderSeparator(displayHeaderSeparator)
                .build();
        return new Element(InsideLabelElementProps.TYPE, insideLabelElementProps);
    }
}
