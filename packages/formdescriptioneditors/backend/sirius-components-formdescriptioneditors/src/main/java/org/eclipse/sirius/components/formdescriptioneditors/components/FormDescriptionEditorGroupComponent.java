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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.components.ToolbarActionComponent;
import org.eclipse.sirius.components.forms.components.ToolbarActionComponentProps;
import org.eclipse.sirius.components.forms.components.WidgetComponent;
import org.eclipse.sirius.components.forms.components.WidgetComponentProps;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.elements.GroupElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.GroupDescription;

/**
 * The component used to render the form description editor group.
 *
 * @author arichard
 */
public class FormDescriptionEditorGroupComponent implements IComponent {

    private static final String AQL_PREFIX = "aql:";

    private final FormDescriptionEditorGroupComponentProps props;

    private final ViewFormDescriptionEditorConverterSwitch converter;

    public FormDescriptionEditorGroupComponent(FormDescriptionEditorGroupComponentProps props) {
        this.props = props;
        this.converter = new ViewFormDescriptionEditorConverterSwitch(props.getFormDescriptionEditorDescription(), props.getVariableManager());
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        var groupDescription = variableManager.get(VariableManager.SELF, GroupDescription.class).get();
        String id = this.props.getFormDescriptionEditorDescription().getTargetObjectIdProvider().apply(variableManager);
        String label = this.getGroupLabel(groupDescription, "Group");
        List<Element> childrenWidgets = new ArrayList<>();

        groupDescription.getToolbarActions().forEach(viewToolbarActionDescription -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, viewToolbarActionDescription);
            AbstractWidgetDescription toolbarActionDescription = this.converter.doSwitch(viewToolbarActionDescription);
            if (toolbarActionDescription instanceof ButtonDescription) {
                ToolbarActionComponentProps toolbarActionComponentProps = new ToolbarActionComponentProps(childVariableManager, (ButtonDescription) toolbarActionDescription);
                childrenWidgets.add(new Element(ToolbarActionComponent.class, toolbarActionComponentProps));
            }
        });

        groupDescription.getWidgets().forEach(viewWidgetDescription -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, viewWidgetDescription);
            AbstractWidgetDescription widgetDescription = this.converter.doSwitch(viewWidgetDescription);
            WidgetComponentProps widgetComponentProps = new WidgetComponentProps(childVariableManager, widgetDescription);
            childrenWidgets.add(new Element(WidgetComponent.class, widgetComponentProps));
        });

        // @formatter:off
        GroupElementProps groupElementProps = GroupElementProps.newGroupElementProps(id)
                .label(label)
                .displayMode(this.getGroupDisplayMode(groupDescription))
                .children(childrenWidgets)
                .build();
        // @formatter:on

        return new Element(GroupElementProps.TYPE, groupElementProps);
    }

    public String getGroupLabel(org.eclipse.sirius.components.view.GroupDescription groupDescription, String defaultLabel) {
        String widgetLabel = defaultLabel;
        String name = groupDescription.getName();
        String labelExpression = groupDescription.getLabelExpression();
        if (labelExpression != null && !labelExpression.isBlank() && !labelExpression.startsWith(AQL_PREFIX)) {
            widgetLabel = labelExpression;
        } else if (name != null && !name.isBlank()) {
            widgetLabel = name;
        }
        return widgetLabel;
    }

    private GroupDisplayMode getGroupDisplayMode(GroupDescription viewGroupDescription) {
        org.eclipse.sirius.components.view.GroupDisplayMode viewDisplayMode = viewGroupDescription.getDisplayMode();
        return GroupDisplayMode.valueOf(viewDisplayMode.getLiteral());
    }
}
