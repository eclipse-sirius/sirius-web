/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.elements.LabelElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

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
        String type = this.props.getType();
        String idFromProvider = labelDescription.getIdProvider().apply(variableManager);
        String id = UUID.nameUUIDFromBytes(idFromProvider.getBytes()).toString();
        String text = labelDescription.getTextProvider().apply(variableManager);

        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescriptionProvider().apply(variableManager);

        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean bold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean italic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean strikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        Boolean underline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        List<String> iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);
        String background = labelStyleDescription.getBackgroundProvider().apply(variableManager);
        String borderColor = labelStyleDescription.getBorderColorProvider().apply(variableManager);
        Integer borderRadius = labelStyleDescription.getBorderRadiusProvider().apply(variableManager);
        Integer borderSize = labelStyleDescription.getBorderSizeProvider().apply(variableManager);
        LineStyle borderLineStyle = labelStyleDescription.getBorderStyleProvider().apply(variableManager);

        Position position = optionalPreviousLabel.map(Label::getPosition).orElse(Position.UNDEFINED);
        Size size = optionalPreviousLabel.map(Label::getSize).orElse(Size.UNDEFINED);
        Position aligment = optionalPreviousLabel.map(Label::getAlignment).orElse(Position.UNDEFINED);

        // @formatter:off
        var labelStyle = LabelStyle.newLabelStyle()
                .color(color)
                .fontSize(fontSize)
                .bold(bold)
                .italic(italic)
                .strikeThrough(strikeThrough)
                .underline(underline)
                .iconURL(iconURL)
                .background(background)
                .borderColor(borderColor)
                .borderRadius(borderRadius)
                .borderSize(borderSize)
                .borderStyle(borderLineStyle)
                .build();

        LabelElementProps labelElementProps = LabelElementProps.newLabelElementProps(id)
                .type(type)
                .text(text)
                .position(position)
                .size(size)
                .alignment(aligment)
                .style(labelStyle)
                .build();
        // @formatter:on
        return new Element(LabelElementProps.TYPE, labelElementProps);
    }
}
