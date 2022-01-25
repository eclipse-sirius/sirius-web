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
package org.eclipse.sirius.components.forms.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        List<Element> children = new ArrayList<>();

        List<Object> semanticElements = pageDescription.getSemanticElementsProvider().apply(variableManager);
        for (Object semanticElement : semanticElements) {
            VariableManager pageVariableManager = variableManager.createChild();
            pageVariableManager.put(VariableManager.SELF, semanticElement);

            String id = pageDescription.getIdProvider().apply(variableManager);
            String label = pageDescription.getLabelProvider().apply(pageVariableManager);

            // @formatter:off
            List<Element> groupComponents = pageDescription.getGroupDescriptions().stream()
                    .map(groupDescription -> {
                        GroupComponentProps groupComponentProps = new GroupComponentProps(pageVariableManager, groupDescription);
                        return new Element(GroupComponent.class, groupComponentProps);
                    })
                    .collect(Collectors.toList());

            PageElementProps pageElementProps = PageElementProps.newPageElementProps(id)
                    .label(label)
                    .children(groupComponents)
                    .build();
            Element pageElement = new Element(PageElementProps.TYPE, pageElementProps);
            // @formatter:on

            children.add(pageElement);
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }
}
