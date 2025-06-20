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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.elements.InsideLabelElementProps;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ILabelAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.LabelAppearanceHandler;
import org.eclipse.sirius.components.diagrams.renderer.LabelAppearanceProperty;
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
        String id = new LabelIdProvider().getInsideLabelId(this.props.getParentNodeId());
        String text = insideLabelDescription.getTextProvider().apply(variableManager);

        boolean isHeader = insideLabelDescription.getIsHeaderProvider().apply(variableManager);
        HeaderSeparatorDisplayMode headerSeparatorDisplayMode = HeaderSeparatorDisplayMode.NEVER;
        if (isHeader) {
            headerSeparatorDisplayMode = insideLabelDescription.getHeaderSeparatorDisplayModeProvider().apply(variableManager);
        }

        Set<String> newCustomizedStyleProperties = new LinkedHashSet<>();

        InsideLabelLocation insideLabelLocation = insideLabelDescription.getInsideLabelLocation();
        var labelStyle = this.computeLabelStyle(insideLabelDescription, variableManager, id, newCustomizedStyleProperties);

        InsideLabelElementProps insideLabelElementProps = InsideLabelElementProps.newInsideLabelElementProps(id)
                .text(text)
                .insideLabelLocation(insideLabelLocation)
                .style(labelStyle)
                .isHeader(isHeader)
                .headerSeparatorDisplayMode(headerSeparatorDisplayMode)
                .overflowStrategy(insideLabelDescription.getOverflowStrategy())
                .textAlign(insideLabelDescription.getTextAlign())
                .customizedStyleProperties(newCustomizedStyleProperties)
                .build();
        return new Element(InsideLabelElementProps.TYPE, insideLabelElementProps);
    }

    private LabelStyle computeLabelStyle(InsideLabelDescription insideLabelDescription, VariableManager variableManager, String id, Set<String> newCustomizedStyleProperties) {
        List<IDiagramEvent> diagramEvents = this.props.getDiagramEvents();
        Optional<InsideLabel> optionalPreviousLabel = this.props.getPreviousLabel();
        Optional<LabelStyle> optionalPreviousLabelStyle = optionalPreviousLabel.map(InsideLabel::getStyle);
        Set<String> previousCustomizedStyleProperties = optionalPreviousLabel.map(InsideLabel::getCustomizedStyleProperties).orElse(new LinkedHashSet<>());
        LabelStyleDescription labelStyleDescription = insideLabelDescription.getStyleDescriptionProvider().apply(variableManager);
        List<ILabelAppearanceChange> appearanceChanges = diagramEvents.stream()
                .filter(EditAppearanceEvent.class::isInstance)
                .map(EditAppearanceEvent.class::cast)
                .flatMap(appearanceEvent -> appearanceEvent.changes().stream())
                .filter(ILabelAppearanceChange.class::isInstance)
                .map(ILabelAppearanceChange.class::cast)
                .filter(appearanceChange -> Objects.equals(id, appearanceChange.labelId()))
                .toList();
        LabelAppearanceHandler labelAppearanceHandler = new LabelAppearanceHandler(appearanceChanges, previousCustomizedStyleProperties, optionalPreviousLabelStyle.orElse(null));

        LabelAppearanceProperty<String> colorAppearance = labelAppearanceHandler.getColor(() -> labelStyleDescription.getColorProvider().apply(variableManager));
        String color = colorAppearance.value();
        if (colorAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.COLOR);
        }
        LabelAppearanceProperty<Integer> fontSizeAppearance = labelAppearanceHandler.getFontSize(() -> labelStyleDescription.getFontSizeProvider().apply(variableManager));
        Integer fontSize = fontSizeAppearance.value();
        if (fontSizeAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.FONT_SIZE);
        }
        LabelAppearanceProperty<Boolean> boldAppearance = labelAppearanceHandler.isBold(() -> labelStyleDescription.getBoldProvider().apply(variableManager));
        Boolean bold = boldAppearance.value();
        if (boldAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.BOLD);
        }
        LabelAppearanceProperty<Boolean> italicAppearance = labelAppearanceHandler.isItalic(() -> labelStyleDescription.getItalicProvider().apply(variableManager));
        Boolean italic = italicAppearance.value();
        if (italicAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.ITALIC);
        }
        LabelAppearanceProperty<Boolean> strikeThroughAppearance = labelAppearanceHandler.isStrikeThrough(() -> labelStyleDescription.getStrikeThroughProvider().apply(variableManager));
        Boolean strikeThrough = strikeThroughAppearance.value();
        if (strikeThroughAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.STRIKE_THROUGH);
        }
        LabelAppearanceProperty<Boolean> underlineAppearance = labelAppearanceHandler.isUnderline(() -> labelStyleDescription.getUnderlineProvider().apply(variableManager));
        Boolean underline = underlineAppearance.value();
        if (underlineAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.UNDERLINE);
        }
        LabelAppearanceProperty<String> backgroundAppearance = labelAppearanceHandler.getBackground(() -> labelStyleDescription.getBackgroundProvider().apply(variableManager));
        String background = backgroundAppearance.value();
        if (backgroundAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.BACKGROUND);
        }
        LabelAppearanceProperty<String> borderColorAppearance = labelAppearanceHandler.getBorderColor(() -> labelStyleDescription.getBorderColorProvider().apply(variableManager));
        String borderColor = borderColorAppearance.value();
        if (borderColorAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.BORDER_COLOR);
        }
        LabelAppearanceProperty<Integer> borderRadiusAppearance = labelAppearanceHandler.getBorderRadius(() -> labelStyleDescription.getBorderRadiusProvider().apply(variableManager));
        Integer borderRadius = borderRadiusAppearance.value();
        if (borderRadiusAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.BORDER_RADIUS);
        }
        LabelAppearanceProperty<Integer> borderSizeAppearance = labelAppearanceHandler.getBorderSize(() -> labelStyleDescription.getBorderSizeProvider().apply(variableManager));
        Integer borderSize = borderSizeAppearance.value();
        if (borderSizeAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.BORDER_SIZE);
        }
        LabelAppearanceProperty<LineStyle> borderLineStyleAppearance = labelAppearanceHandler.getBorderStyle(() -> labelStyleDescription.getBorderStyleProvider().apply(variableManager));
        LineStyle borderLineStyle = borderLineStyleAppearance.value();
        if (borderLineStyleAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.BORDER_STYLE);
        }

        List<String> iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);
        String maxWidth = labelStyleDescription.getMaxWidthProvider().apply(variableManager);

        return LabelStyle.newLabelStyle()
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
    }
}
