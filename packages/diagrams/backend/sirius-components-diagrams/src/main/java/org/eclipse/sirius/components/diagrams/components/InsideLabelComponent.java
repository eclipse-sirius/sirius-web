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
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.elements.InsideLabelElementProps;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
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

        LabelStyleDescription labelStyleDescription = insideLabelDescription.getStyleDescriptionProvider().apply(variableManager);

        List<IDiagramEvent> diagramEvents = this.props.getDiagramEvents();
        Set<String> newCustomizedStyleProperties = new LinkedHashSet<>();
        Optional<LabelStyle> optionalPreviousLabelStyle = this.props.getPreviousLabel().map(InsideLabel::getStyle);
        Set<String> previousCustomizedStyleProperties = this.props.getPreviousLabel().map(InsideLabel::getCustomizedStyleProperties).orElse(new LinkedHashSet<>());

        InsideLabelLocation insideLabelLocation = insideLabelDescription.getInsideLabelLocation();
        var labelStyle = new LabelStyleComponentProvider().getLabelStyle(id, diagramEvents, previousCustomizedStyleProperties, optionalPreviousLabelStyle, labelStyleDescription, variableManager, newCustomizedStyleProperties);

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
