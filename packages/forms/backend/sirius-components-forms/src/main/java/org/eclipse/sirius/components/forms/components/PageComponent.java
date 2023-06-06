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
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.elements.PageElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the pages.
 *
 * @author sbegaudeau
 */
public class PageComponent implements IComponent {

    private PageComponentProps props;

    public PageComponent(PageComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        PageDescription pageDescription = this.props.getPageDescription();

        List<?> semanticElements = pageDescription.getSemanticElementsProvider().apply(variableManager);
        List<Element> children = new ArrayList<>(semanticElements.size());

        for (Object semanticElement : semanticElements) {
            VariableManager pageVariableManager = variableManager.createChild();
            pageVariableManager.put(VariableManager.SELF, semanticElement);

            String id = pageDescription.getIdProvider().apply(pageVariableManager);
            String label = pageDescription.getLabelProvider().apply(pageVariableManager);

            List<Element> pageChildren = new ArrayList<>();

            pageDescription.getToolbarActionDescriptions().stream()
                    .map(toolbarActionDescription -> new Element(ToolbarActionComponent.class, new ToolbarActionComponentProps(pageVariableManager, toolbarActionDescription)))
                    .forEach(pageChildren::add);

            pageDescription.getGroupDescriptions().stream()
                    .map(groupDescription -> new Element(GroupComponent.class, new GroupComponentProps(pageVariableManager, groupDescription, this.props.getWidgetDescriptors())))
                    .forEach(pageChildren::add);

            PageElementProps pageElementProps = PageElementProps.newPageElementProps(id)
                    .label(label)
                    .children(pageChildren)
                    .build();
            Element pageElement = new Element(PageElementProps.TYPE, pageElementProps);

            children.add(pageElement);
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }
}
