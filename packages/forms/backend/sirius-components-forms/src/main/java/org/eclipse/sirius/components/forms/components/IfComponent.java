/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to conditionally render children elements.
 *
 * @author sbegaudeau
 */
public class IfComponent implements IComponent {

    private IfComponentProps props;

    public IfComponent(IfComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        IfDescription ifDescription = this.props.getIfDescription();
        Boolean testResult = ifDescription.getPredicate().apply(variableManager);

        List<Element> children = new ArrayList<>();
        if (testResult.booleanValue()) {
            VariableManager childrenVariableManager = variableManager.createChild();
            childrenVariableManager.put(FormComponent.PARENT_ELEMENT_ID, ifDescription.getId());
            for (var controlDescription : ifDescription.getControlDescriptions()) {
                if (controlDescription instanceof AbstractWidgetDescription widgetDescription) {
                    WidgetComponentProps widgetComponentProps = new WidgetComponentProps(childrenVariableManager, widgetDescription, this.props.getWidgetDescriptors());
                    children.add(new Element(WidgetComponent.class, widgetComponentProps));
                } else if (controlDescription instanceof ForDescription forDescription) {
                    ForComponentProps forComponentProps = new ForComponentProps(childrenVariableManager, forDescription, this.props.getWidgetDescriptors());
                    children.add(new Element(ForComponent.class, forComponentProps));
                } else if (controlDescription instanceof IfDescription subIfDescription) {
                    IfComponentProps ifComponentProps = new IfComponentProps(childrenVariableManager, subIfDescription, this.props.getWidgetDescriptors());
                    children.add(new Element(IfComponent.class, ifComponentProps));
                }
            }
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

}
