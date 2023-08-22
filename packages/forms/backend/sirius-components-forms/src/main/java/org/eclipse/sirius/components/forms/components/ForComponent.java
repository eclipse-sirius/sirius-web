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

import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to render multiple occurrences of the same element.
 *
 * @author sbegaudeau
 */
public class ForComponent implements IComponent {

    private ForComponentProps props;

    public ForComponent(ForComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ForDescription forDescription = this.props.getForDescription();

        List<?> objects = forDescription.getIterableProvider().apply(variableManager);
        List<Element> children = new ArrayList<>(objects.size());

        for (Object object : objects) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(forDescription.getIterator(), object);
            childVariableManager.put(FormComponent.PARENT_ELEMENT_ID, forDescription.getId());
            List<Element> forChildren = new ArrayList<>();

            List<AbstractControlDescription> controlDescriptions = forDescription.getControlDescriptions();
            for (AbstractControlDescription controlDescription : controlDescriptions) {
                if (controlDescription instanceof AbstractWidgetDescription widgetDescription) {
                    WidgetComponentProps widgetComponentProps = new WidgetComponentProps(childVariableManager, widgetDescription, this.props.getWidgetDescriptors());
                    forChildren.add(new Element(WidgetComponent.class, widgetComponentProps));
                } else if (controlDescription instanceof ForDescription subForDescription) {
                    ForComponentProps forComponentProps = new ForComponentProps(childVariableManager, subForDescription, this.props.getWidgetDescriptors());
                    forChildren.add(new Element(ForComponent.class, forComponentProps));
                } else if (controlDescription instanceof IfDescription ifDescription) {
                    IfComponentProps ifComponentProps = new IfComponentProps(childVariableManager, ifDescription, this.props.getWidgetDescriptors());
                    forChildren.add(new Element(IfComponent.class, ifComponentProps));
                }
            }

            FragmentProps fragmentProps = new FragmentProps(forChildren);
            children.add(new Fragment(fragmentProps));
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

}
