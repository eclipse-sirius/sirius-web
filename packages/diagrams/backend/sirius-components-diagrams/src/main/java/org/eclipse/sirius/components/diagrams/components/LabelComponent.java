/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.elements.LabelElementProps;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
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
        String type = this.props.getType();
        String id = new LabelIdProvider().getEdgeLabelId(this.props.getParentEdgeId(), this.props.getPosition());
        String text = labelDescription.getTextProvider().apply(variableManager);

        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescriptionProvider().apply(variableManager);

        List<IDiagramEvent> diagramEvents = this.props.getDiagramEvents();
        Optional<LabelStyle> optionalPreviousLabelStyle = this.props.getPreviousLabel().map(Label::style);
        Set<String> newCustomizedStyleProperties = new LinkedHashSet<>();
        Set<String> previousCustomizedStyleProperties = this.props.getPreviousLabel().map(Label::customizedStyleProperties).orElse(new LinkedHashSet<>());

        var labelStyle = new LabelStyleComponentProvider().getLabelStyle(id, diagramEvents, previousCustomizedStyleProperties, optionalPreviousLabelStyle, labelStyleDescription, variableManager, newCustomizedStyleProperties);

        LabelElementProps labelElementProps = LabelElementProps.newLabelElementProps(id)
                .type(type)
                .text(text)
                .style(labelStyle)
                .customizedStyleProperties(newCustomizedStyleProperties)
                .build();
        return new Element(LabelElementProps.TYPE, labelElementProps);
    }
}
