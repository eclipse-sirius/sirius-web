/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.appearancedata.LabelAppearanceData;
import org.eclipse.sirius.components.diagrams.appearancedata.LabelCustomizedStyle;
import org.eclipse.sirius.components.diagrams.appearancedata.NodeAppearanceData;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.OutsideLabelDescription;
import org.eclipse.sirius.components.diagrams.elements.OutsideLabelElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the outside label.
 *
 * @author frouene
 */
public class OutsideLabelComponent implements IComponent {

    private final OutsideLabelComponentProps props;

    public OutsideLabelComponent(OutsideLabelComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        OutsideLabelDescription outsideLabelDescription = this.props.getOutsideLabelDescription();
        String idFromProvider = outsideLabelDescription.getIdProvider().apply(variableManager);
        String id = UUID.nameUUIDFromBytes(idFromProvider.getBytes()).toString();
        String text = outsideLabelDescription.getTextProvider().apply(variableManager);

        Optional<LabelCustomizedStyle> optionalLabelCustomizedStyle = variableManager.get(NodeAppearanceData.NODE_APPEARANCE_DATA, NodeAppearanceData.class)
                .flatMap(nodeAppearanceData -> nodeAppearanceData.labelsAppearanceData().stream().filter(labelAppearanceData -> Objects.equals(labelAppearanceData.id(), id))
                        .findFirst()).map(LabelAppearanceData::customizedLabelStyle);

        LabelStyleDescription labelStyleDescription = outsideLabelDescription.getStyleDescriptionProvider().apply(variableManager);

        String color = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getColor).orElse(labelStyleDescription.getColorProvider().apply(variableManager));
        Integer fontSize = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getFontSize).orElse(labelStyleDescription.getFontSizeProvider().apply(variableManager));
        Boolean bold = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::isBold).orElse(labelStyleDescription.getBoldProvider().apply(variableManager));
        Boolean italic = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::isItalic).orElse(labelStyleDescription.getItalicProvider().apply(variableManager));
        Boolean strikeThrough = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::isStrikeThrough).orElse(labelStyleDescription.getStrikeThroughProvider().apply(variableManager));
        Boolean underline = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::isUnderline).orElse(labelStyleDescription.getUnderlineProvider().apply(variableManager));
        List<String> iconURL = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getIconURL).orElse(labelStyleDescription.getIconURLProvider().apply(variableManager));
        String background = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getBackground).orElse(labelStyleDescription.getBackgroundProvider().apply(variableManager));
        String borderColor = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getBorderColor).orElse(labelStyleDescription.getBorderColorProvider().apply(variableManager));
        Integer borderRadius = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getBorderRadius).orElse(labelStyleDescription.getBorderRadiusProvider().apply(variableManager));
        Integer borderSize = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getBorderSize).orElse(labelStyleDescription.getBorderSizeProvider().apply(variableManager));
        LineStyle borderLineStyle = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getBorderStyle).orElse(labelStyleDescription.getBorderStyleProvider().apply(variableManager));
        String maxWidth = optionalLabelCustomizedStyle.map(LabelCustomizedStyle::getMaxWidth).orElse(labelStyleDescription.getMaxWidthProvider().apply(variableManager));

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
                .maxWidth(maxWidth)
                .build();

        OutsideLabelElementProps outsideLabelElementProps = OutsideLabelElementProps.newOutsideLabelElementProps(id)
                .text(text)
                .outsideLabelLocation(outsideLabelDescription.getOutsideLabelLocation())
                .style(labelStyle)
                .overflowStrategy(outsideLabelDescription.getOverflowStrategy())
                .textAlign(outsideLabelDescription.getTextAlign())
                .build();
        return new Element(OutsideLabelElementProps.TYPE, outsideLabelElementProps);
    }
}
