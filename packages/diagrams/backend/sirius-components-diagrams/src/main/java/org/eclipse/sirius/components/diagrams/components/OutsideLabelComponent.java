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

import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.OutsideLabelDescription;
import org.eclipse.sirius.components.diagrams.elements.OutsideLabelElementProps;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
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
        String id = new LabelIdProvider().getOutsideLabelId(this.props.getParentNodeId(), outsideLabelDescription.getOutsideLabelLocation().name());
        String text = outsideLabelDescription.getTextProvider().apply(variableManager);

        LabelStyleDescription labelStyleDescription = outsideLabelDescription.getStyleDescriptionProvider().apply(variableManager);

        List<IDiagramEvent> diagramEvents = this.props.getDiagramEvents();
        Optional<OutsideLabel> optionalPreviousLabel = this.props.getPreviousOutsideLabels().stream().filter(label -> label.id().equals(id)).findFirst();
        Optional<LabelStyle> optionalPreviousLabelStyle = optionalPreviousLabel.map(OutsideLabel::style);
        Set<String> previousCustomizedStyleProperties = optionalPreviousLabel.map(OutsideLabel::customizedStyleProperties).orElse(new LinkedHashSet<>());

        Set<String> newCustomizedStyleProperties = new LinkedHashSet<>();

        var labelStyle = new LabelStyleComponentProvider().getLabelStyle(id, diagramEvents, previousCustomizedStyleProperties, optionalPreviousLabelStyle, labelStyleDescription, variableManager, newCustomizedStyleProperties);

        OutsideLabelElementProps outsideLabelElementProps = OutsideLabelElementProps.newOutsideLabelElementProps(id)
                .text(text)
                .outsideLabelLocation(outsideLabelDescription.getOutsideLabelLocation())
                .style(labelStyle)
                .overflowStrategy(outsideLabelDescription.getOverflowStrategy())
                .textAlign(outsideLabelDescription.getTextAlign())
                .customizedStyleProperties(newCustomizedStyleProperties)
                .build();
        return new Element(OutsideLabelElementProps.TYPE, outsideLabelElementProps);
    }

}
