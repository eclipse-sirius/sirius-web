/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
        FlexDirection flexdirection = flexboxContainerDescription.getFlexDirection();

        List<Element> children = new ArrayList<>();

        List<Element> childrenWidgets = new ArrayList<>();

        flexboxContainerDescription.getChildren().forEach(widget -> {
            VariableManager childVariableManager = variableManager.createChild();
            var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            var optionalIdCounter = variableManager.get(GroupComponent.WIDGET_ID_PROVIDER_COUNTER, Object.class);

            if (optionalSelf.isPresent() && optionalIdCounter.isPresent()) {
                childVariableManager.put(VariableManager.SELF, optionalSelf.get());
                childVariableManager.put(GroupComponent.WIDGET_ID_PROVIDER_COUNTER, optionalIdCounter.get());
                childrenWidgets.add(new Element(WidgetComponent.class, new WidgetComponentProps(childVariableManager, widget)));
            }
        });

        // @formatter:off
        FlexboxContainerElementProps flexboxContainerElementProps = FlexboxContainerElementProps.newFlexboxContainerElementProps(id)
                .label(label)
                .flexDirection(flexdirection)
                .children(childrenWidgets)
                .build();
        // @formatter:on

        Element flexboxContainerElement = new Element(FlexboxContainerElementProps.TYPE, flexboxContainerElementProps);
        children.add(flexboxContainerElement);

        children.add(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(flexboxContainerDescription, variableManager)));

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }
}
