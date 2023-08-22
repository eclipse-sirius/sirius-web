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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.emf.ecore.util.Switch;
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
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * The component used to render the form description editor group.
 *
 * @author arichard
 */
public class FormDescriptionEditorGroupComponent implements IComponent {

    private static final String AQL_PREFIX = "aql:";

    private final FormDescriptionEditorGroupComponentProps props;

    private final Switch<AbstractWidgetDescription> converter;

    public FormDescriptionEditorGroupComponent(FormDescriptionEditorGroupComponentProps props) {
        this.props = props;
        List<Switch<AbstractWidgetDescription>> widgetConverters = this.props.getCustomWidgetConverterProviders().stream()
                .map(provider -> provider.getWidgetConverter(props.getFormDescriptionEditorDescription(), props.getVariableManager()))
                .toList();
        Collection<Switch<AbstractWidgetDescription>> switches = new ArrayList<>();
        switches.add(new ViewFormDescriptionEditorConverterSwitch(props.getFormDescriptionEditorDescription(), props.getVariableManager(), new ComposedSwitch<>(widgetConverters)));
        switches.addAll(widgetConverters);
        this.converter = new ComposedSwitch<>(switches);
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
            if (toolbarActionDescription instanceof ButtonDescription buttonDescription) {
                ToolbarActionComponentProps toolbarActionComponentProps = new ToolbarActionComponentProps(childVariableManager, buttonDescription);
                childrenWidgets.add(new Element(ToolbarActionComponent.class, toolbarActionComponentProps));
            }
        });

        groupDescription.getChildren().stream()
            .flatMap(controlDescription -> {
                Stream<WidgetDescription> widgets = Stream.empty();
                if (controlDescription instanceof WidgetDescription viewWidgetDescription) {
                    widgets = Stream.of(viewWidgetDescription);
                } else if (controlDescription instanceof FormElementFor formElementFor) {
                    widgets = formElementFor.getChildren().stream()
                            .filter(FormElementIf.class::isInstance).map(FormElementIf.class::cast)
                            .flatMap(formElementIf -> formElementIf.getChildren().stream())
                            .filter(WidgetDescription.class::isInstance)
                            .map(WidgetDescription.class::cast);
                }
                return widgets;
            })
            .forEach(viewWidgetDescription -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, viewWidgetDescription);
                AbstractWidgetDescription widgetDescription = this.converter.doSwitch(viewWidgetDescription);
                WidgetComponentProps widgetComponentProps = new WidgetComponentProps(childVariableManager, widgetDescription, this.props.getWidgetDescriptors());
                childrenWidgets.add(new Element(WidgetComponent.class, widgetComponentProps));
            });

        GroupElementProps.Builder groupElementPropsBuilder = GroupElementProps.newGroupElementProps(id)
                .label(label)
                .displayMode(this.getGroupDisplayMode(groupDescription))
                .children(childrenWidgets);
        if (groupDescription.getBorderStyle() != null) {
            groupElementPropsBuilder.borderStyle(new ContainerBorderStyleProvider(groupDescription.getBorderStyle()).build());
        }

        return new Element(GroupElementProps.TYPE, groupElementPropsBuilder.build());
    }

    public String getGroupLabel(org.eclipse.sirius.components.view.form.GroupDescription groupDescription, String defaultLabel) {
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
        org.eclipse.sirius.components.view.form.GroupDisplayMode viewDisplayMode = viewGroupDescription.getDisplayMode();
        return GroupDisplayMode.valueOf(viewDisplayMode.getLiteral());
    }

}
