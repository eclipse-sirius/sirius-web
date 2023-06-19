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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.FlexDirection;
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
 * @author arichard
 */
public class FlexboxContainerComponent implements IComponent {

    private final FlexboxContainerComponentProps props;

    public FlexboxContainerComponent(FlexboxContainerComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        FlexboxContainerDescription flexboxContainerDescription = this.props.getFlexboxContainerDescription();

        String id = flexboxContainerDescription.getIdProvider().apply(variableManager);
        String label = flexboxContainerDescription.getLabelProvider().apply(variableManager);
        Boolean readOnly = flexboxContainerDescription.getIsReadOnlyProvider().apply(variableManager);
        FlexDirection flexdirection = flexboxContainerDescription.getFlexDirection();
        var borderStyle = flexboxContainerDescription.getBorderStyleProvider().apply(variableManager);

        List<Element> children = new ArrayList<>();

        List<Element> childrenWidgets = new ArrayList<>();

        flexboxContainerDescription.getChildren().forEach(widget -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);

            if (optionalSelf.isPresent()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, optionalSelf.get());
                childrenWidgets.add(new Element(WidgetComponent.class, new WidgetComponentProps(childVariableManager, widget, this.props.getWidgetDescriptors())));
            }
        });


        var flexboxContainerElementPropsBuilder = FlexboxContainerElementProps.newFlexboxContainerElementProps(id)
                .label(label)
                .flexDirection(flexdirection)
                .children(childrenWidgets);
        if (readOnly != null) {
            flexboxContainerElementPropsBuilder.readOnly(readOnly);
        }

        if (borderStyle != null) {
            flexboxContainerElementPropsBuilder.borderStyle(borderStyle);
        }

        if (flexboxContainerDescription.getHelpTextProvider() != null) {
            flexboxContainerElementPropsBuilder.helpTextProvider(() -> flexboxContainerDescription.getHelpTextProvider().apply(variableManager));
        }

        Element flexboxContainerElement = new Element(FlexboxContainerElementProps.TYPE, flexboxContainerElementPropsBuilder.build());
        children.add(flexboxContainerElement);

        children.add(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(flexboxContainerDescription, variableManager)));

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }
}
