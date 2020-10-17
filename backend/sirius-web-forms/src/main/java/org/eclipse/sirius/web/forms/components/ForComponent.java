/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.forms.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.Fragment;
import org.eclipse.sirius.web.components.FragmentProps;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.forms.description.ForDescription;
import org.eclipse.sirius.web.forms.description.IfDescription;
import org.eclipse.sirius.web.representations.VariableManager;

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

        // @formatter:off
        List<Element> children = new ArrayList<>();

        List<Object> objects = forDescription.getIterableProvider().apply(variableManager);
        for (Object object : objects) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(forDescription.getIterator(), object);

            List<Element> forChildren = new ArrayList<>();

            List<IfDescription> ifDescriptions = forDescription.getIfDescriptions();
            for (IfDescription ifDescription : ifDescriptions) {
                IfComponentProps ifComponentProps = new IfComponentProps(childVariableManager, ifDescription);
                forChildren.add(new Element(IfComponent.class, ifComponentProps));
            }

            FragmentProps fragmentProps = new FragmentProps(forChildren);
            children.add(new Fragment(fragmentProps));
        }
        // @formatter:on

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

}
