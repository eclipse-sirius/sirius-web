/*******************************************************************************
 * Copyright (c) 2019, 2020, 2022 Obeo.
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

import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.elements.GroupElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the groups.
 *
 * @author sbegaudeau
 */
public class GroupComponent implements IComponent {

    /**
     * The variable name used to store a {@link WidgetIdCounter} in the {@link VariableManager} of the
     * {@link GroupComponent}.
     */
    public static final String WIDGET_ID_PROVIDER_COUNTER = "widgetIdProviderCounter"; //$NON-NLS-1$

    private GroupComponentProps props;

    public GroupComponent(GroupComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        GroupDescription groupDescription = this.props.getGroupDescription();
        WidgetIdCounter widgetIdCounter = new WidgetIdCounter();

        List<?> semanticElements = groupDescription.getSemanticElementsProvider().apply(variableManager);

        List<Element> children = new ArrayList<>(semanticElements.size());

        for (Object semanticElement : semanticElements) {
            VariableManager groupVariableManager = variableManager.createChild();
            groupVariableManager.put(VariableManager.SELF, semanticElement);
            groupVariableManager.put(WIDGET_ID_PROVIDER_COUNTER, widgetIdCounter);

            String id = groupDescription.getIdProvider().apply(groupVariableManager);
            String label = groupDescription.getLabelProvider().apply(groupVariableManager);
            GroupDisplayMode displayMode = groupDescription.getDisplayModeProvider().apply(groupVariableManager);

            List<Element> groupChildren = new ArrayList<>();
            for (ButtonDescription toolbarActionDescription : groupDescription.getToolbarActionDescriptions()) {
                groupChildren.add(new Element(ToolbarActionComponent.class, new ToolbarActionComponentProps(groupVariableManager, toolbarActionDescription)));
            }

            // @formatter:off
            List<AbstractControlDescription> controlDescriptions = groupDescription.getControlDescriptions();
            for (AbstractControlDescription controlDescription : controlDescriptions) {
                if (controlDescription instanceof AbstractWidgetDescription) {
                    AbstractWidgetDescription widgetDescription = (AbstractWidgetDescription) controlDescription;
                    WidgetComponentProps widgetComponentProps = new WidgetComponentProps(groupVariableManager, widgetDescription);
                    groupChildren.add(new Element(WidgetComponent.class, widgetComponentProps));
                } else if (controlDescription instanceof ForDescription) {
                    ForDescription forDescription = (ForDescription) controlDescription;
                    ForComponentProps forComponentProps = new ForComponentProps(groupVariableManager, forDescription);
                    groupChildren.add(new Element(ForComponent.class, forComponentProps));
                }
            }

            GroupElementProps groupElementProps = GroupElementProps.newGroupElementProps(id)
                    .label(label)
                    .displayMode(displayMode)
                    .children(groupChildren)
                    .build();
            Element groupElement = new Element(GroupElementProps.TYPE, groupElementProps);
            // @formatter:on

            children.add(groupElement);
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }
}
