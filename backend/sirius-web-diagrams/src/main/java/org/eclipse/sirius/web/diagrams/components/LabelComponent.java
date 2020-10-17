/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
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

        String id = labelDescription.getIdProvider().apply(variableManager);
        String text = labelDescription.getTextProvider().apply(variableManager);

        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescription();

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
        LabelElementProps labelElementProps = LabelElementProps.newLabelElementProps(id)
                .type("label:inside-center") //$NON-NLS-1$
                .text(text)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .style(labelStyle)
                .build();
        // @formatter:on
        return new Element(LabelElementProps.TYPE, labelElementProps);
    }

}
