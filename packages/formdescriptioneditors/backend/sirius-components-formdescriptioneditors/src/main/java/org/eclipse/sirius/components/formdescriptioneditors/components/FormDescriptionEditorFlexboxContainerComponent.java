/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.forms.elements.FlexboxContainerElementProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the flexbox container widget.
 *
 * @author pcdavid
 */
public class FormDescriptionEditorFlexboxContainerComponent implements IComponent {

    private final FormDescriptionEditorFlexboxContainerComponentProps props;

    public FormDescriptionEditorFlexboxContainerComponent(FormDescriptionEditorFlexboxContainerComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        FlexboxContainerDescription flexboxContainerDescription = this.props.flexboxContainerDescription();

        String label = flexboxContainerDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, flexboxContainerDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, flexboxContainerDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = flexboxContainerDescription.getIdProvider().apply(idVariableManager);

        Boolean readOnly = flexboxContainerDescription.getIsReadOnlyProvider().apply(variableManager);
        FlexDirection flexdirection = flexboxContainerDescription.getFlexDirection();
        var borderStyle = flexboxContainerDescription.getBorderStyleProvider().apply(variableManager);

        List<Element> children = new ArrayList<>();

        List<Element> childrenWidgets = new ArrayList<>();
        VariableManager childrenVariableManager = variableManager.createChild();
        childrenVariableManager.put(FormComponent.PARENT_ELEMENT_ID, id);
        flexboxContainerDescription.getChildren().forEach(controlDescription -> {
            if (controlDescription instanceof AbstractWidgetDescription widgetDescription) {
                var widgetComponentProps = new FormDescriptionEditorWidgetComponentProps(childrenVariableManager, widgetDescription, this.props.widgetDescriptors());
                childrenWidgets.add(new Element(FormDescriptionEditorWidgetComponent.class, widgetComponentProps));
            }
        });

        var flexboxContainerElementPropsBuilder = FlexboxContainerElementProps.newFlexboxContainerElementProps(id)
                .label(label)
                .flexDirection(flexdirection)
                .children(childrenWidgets)
                .alignItems(flexboxContainerDescription.getAlignItems())
                .justifyContent(flexboxContainerDescription.getJustifyContent());
        if (readOnly != null) {
            flexboxContainerElementPropsBuilder.readOnly(readOnly);
        }

        if (borderStyle != null) {
            flexboxContainerElementPropsBuilder.borderStyle(borderStyle);
        }

        if (flexboxContainerDescription.getHelpTextProvider() != null) {
            flexboxContainerElementPropsBuilder.helpTextProvider(() -> flexboxContainerDescription.getHelpTextProvider().apply(variableManager));
        }
        if (flexboxContainerDescription.getGap() != null) {
            flexboxContainerElementPropsBuilder.gap(flexboxContainerDescription.getGap());
        }
        if (flexboxContainerDescription.getMargin() != null) {
            flexboxContainerElementPropsBuilder.margin(flexboxContainerDescription.getMargin());
        }
        if (flexboxContainerDescription.getPadding() != null) {
            flexboxContainerElementPropsBuilder.padding(flexboxContainerDescription.getPadding());
        }

        Element flexboxContainerElement = new Element(FlexboxContainerElementProps.TYPE, flexboxContainerElementPropsBuilder.build());
        children.add(flexboxContainerElement);

        children.add(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(flexboxContainerDescription, variableManager)));

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }
}
