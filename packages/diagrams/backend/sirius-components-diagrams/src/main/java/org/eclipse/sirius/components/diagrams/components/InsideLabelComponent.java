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
import java.util.UUID;

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
import org.eclipse.sirius.components.diagrams.events.appearance.ILabelAppearanceChange;
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
        String idFromProvider = insideLabelDescription.getIdProvider().apply(variableManager);
        String id = UUID.nameUUIDFromBytes(idFromProvider.getBytes()).toString();
        String text = insideLabelDescription.getTextProvider().apply(variableManager);

        boolean isHeader = insideLabelDescription.getIsHeaderProvider().apply(variableManager);
        HeaderSeparatorDisplayMode headerSeparatorDisplayMode = HeaderSeparatorDisplayMode.NEVER;
        if (isHeader) {
            headerSeparatorDisplayMode = insideLabelDescription.getHeaderSeparatorDisplayModeProvider().apply(variableManager);
        }

        LabelStyleDescription labelStyleDescription = insideLabelDescription.getStyleDescriptionProvider().apply(variableManager);

        List<IDiagramEvent> diagramEvents = this.props.getDiagramEvents();
        Optional<InsideLabel> optionalPreviousLabel = this.props.getPreviousLabel();
        Optional<LabelStyle> optionalPreviousLabelStyle = optionalPreviousLabel.map(InsideLabel::getStyle);
        Set<String> previousCustomizedStyleProperties = optionalPreviousLabel.map(InsideLabel::getCustomizedStyleProperties).orElse(new LinkedHashSet<>());

        Set<String> newCustomizedStyleProperties = new LinkedHashSet<>();
        List<ILabelAppearanceChange> appearanceChanges = diagramEvents.stream()
                .filter(EditAppearanceEvent.class::isInstance)
                .map(EditAppearanceEvent.class::cast)
                .flatMap(appearanceEvent -> appearanceEvent.changes().stream())
                .filter(ILabelAppearanceChange.class::isInstance)
                .map(ILabelAppearanceChange.class::cast)
                .filter(appearanceChange -> Objects.equals(id, appearanceChange.labelId()))
                .toList();

        LabelAppearanceHandler labelAppearanceHandler = new LabelAppearanceHandler(appearanceChanges, previousCustomizedStyleProperties, optionalPreviousLabelStyle.orElse(null));

        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);

        LabelAppearanceProperty<Boolean> boldAppearance = labelAppearanceHandler.isBold(() -> labelStyleDescription.getBoldProvider().apply(variableManager));
        Boolean bold = boldAppearance.value();
        if (boldAppearance.customized()) {
            newCustomizedStyleProperties.add(LabelAppearanceHandler.BOLD);
        }

        Boolean italic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean strikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        Boolean underline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        List<String> iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);
        String background = labelStyleDescription.getBackgroundProvider().apply(variableManager);
        String borderColor = labelStyleDescription.getBorderColorProvider().apply(variableManager);
        Integer borderRadius = labelStyleDescription.getBorderRadiusProvider().apply(variableManager);
        Integer borderSize = labelStyleDescription.getBorderSizeProvider().apply(variableManager);
        LineStyle borderLineStyle = labelStyleDescription.getBorderStyleProvider().apply(variableManager);
        String maxWidth = labelStyleDescription.getMaxWidthProvider().apply(variableManager);

        InsideLabelLocation insideLabelLocation = insideLabelDescription.getInsideLabelLocation();

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
}
