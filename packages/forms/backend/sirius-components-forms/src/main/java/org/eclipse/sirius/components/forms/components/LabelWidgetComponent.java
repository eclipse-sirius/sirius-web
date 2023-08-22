/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.LabelDescription;
import org.eclipse.sirius.components.forms.elements.LabelWidgetElementProps;
import org.eclipse.sirius.components.forms.elements.LabelWidgetElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the label widget.
 *
 * @author fbarbin
 */
public class LabelWidgetComponent implements IComponent {

    private LabelWidgetComponentProps props;

    public LabelWidgetComponent(LabelWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        LabelDescription labelDescription = this.props.getLabelDescription();

        String label = labelDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, labelDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, labelDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = labelDescription.getIdProvider().apply(idVariableManager);

        String value = labelDescription.getValueProvider().apply(variableManager);
        var style = labelDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(labelDescription, variableManager)));

        // @formatter:off
        Builder labelElementPropsBuilder = LabelWidgetElementProps.newLabelWidgetElementProps(id)
                .label(label)
                .value(value)
                .children(children);

        if (style != null) {
            labelElementPropsBuilder.style(style);
        }
        if (labelDescription.getHelpTextProvider() != null) {
            labelElementPropsBuilder.helpTextProvider(() -> labelDescription.getHelpTextProvider().apply(variableManager));
        }

        LabelWidgetElementProps labelElementProps = labelElementPropsBuilder.build();

        return new Element(LabelWidgetElementProps.TYPE, labelElementProps);
        // @formatter:on
    }
}
