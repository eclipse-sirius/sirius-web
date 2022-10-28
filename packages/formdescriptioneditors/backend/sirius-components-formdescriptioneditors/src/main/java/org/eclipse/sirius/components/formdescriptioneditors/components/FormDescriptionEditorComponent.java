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
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.description.AbstractFormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorFlexboxContainerDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorToolbarActionDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.WidgetDescription;

/**
 * The component used to render the form description editor.
 *
 * @author arichard
 */
public class FormDescriptionEditorComponent implements IComponent {

    private final FormDescriptionEditorComponentProps props;

    public FormDescriptionEditorComponent(FormDescriptionEditorComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        var formDescription = variableManager.get(VariableManager.SELF, FormDescription.class).get();

        FormDescriptionEditorDescription formDescriptionEditorDescription = this.props.getFormDescriptionEditorDescription();
        var optionalPreviousFormDescriptionEditor = this.props.getOptionalPreviousFormDescriptionEditor();

        String id = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getId).orElseGet(() -> UUID.randomUUID().toString());
        // @formatter:off
        String label = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getLabel)
                .orElseGet(() -> variableManager.get(FormDescriptionEditor.LABEL, String.class)
                .orElse("Form Description Editor")); //$NON-NLS-1$
        // @formatter:on
        Function<VariableManager, String> targetObjectIdProvider = formDescriptionEditorDescription.getTargetObjectIdProvider();
        String targetObjectId = targetObjectIdProvider.apply(variableManager);

        List<Element> childrenWidgets = new ArrayList<>();

        formDescription.getToolbarActions().forEach(toolbarActionDescription -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, toolbarActionDescription);
            String toolbarActionId = targetObjectIdProvider.apply(childVariableManager);
            String toolbarActionKind = this.getKind(toolbarActionDescription);
            String toolbarActionLabel = toolbarActionDescription.getName();
            if (toolbarActionLabel == null) {
                toolbarActionLabel = toolbarActionKind;
            }
            // @formatter:off
            FormDescriptionEditorToolbarActionDescription fdeToolbarActionDescription = FormDescriptionEditorToolbarActionDescription.newFormDescriptionEditorToolbarActionDescription(toolbarActionId)
                    .label(toolbarActionLabel)
                    .kind(toolbarActionKind)
                    .build();
            // @formatter:on
            FormDescriptionEditorToolbarActionComponentProps fdeToolbarActionComponentProps = new FormDescriptionEditorToolbarActionComponentProps(fdeToolbarActionDescription);
            childrenWidgets.add(new Element(FormDescriptionEditorToolbarActionComponent.class, fdeToolbarActionComponentProps));
        });

        formDescription.getWidgets().forEach(widgetDescription -> {

            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, widgetDescription);
            String widgetId = targetObjectIdProvider.apply(childVariableManager);
            String widgetKind = this.getKind(widgetDescription);
            String widgetLabel = widgetDescription.getName();
            if (widgetLabel == null) {
                widgetLabel = widgetKind;
            }

            if (widgetDescription instanceof FlexboxContainerDescription) {
                List<AbstractFormDescriptionEditorWidgetDescription> childrenDescriptions = this.transformDescriptions(((FlexboxContainerDescription) widgetDescription).getChildren(),
                        childVariableManager, targetObjectIdProvider);
                // @formatter:off
                FormDescriptionEditorFlexboxContainerDescription fdeFlexboxContainerDescription = FormDescriptionEditorFlexboxContainerDescription.newFormDescriptionEditorFlexboxContainerDescription(widgetId)
                        .label(widgetLabel)
                        .kind(widgetKind)
                        .flexDirection(((FlexboxContainerDescription) widgetDescription).getFlexDirection())
                        .children(childrenDescriptions)
                        .build();
                // @formatter:on
                FormDescriptionEditorFlexboxContainerComponentProps fdeFlexboxContainerComponentProps = new FormDescriptionEditorFlexboxContainerComponentProps(fdeFlexboxContainerDescription);
                childrenWidgets.add(new Element(FormDescriptionEditorFlexboxContainerComponent.class, fdeFlexboxContainerComponentProps));
            } else if (widgetDescription instanceof WidgetDescription) {
                // @formatter:off
                FormDescriptionEditorWidgetDescription fdeWidgetDescription = FormDescriptionEditorWidgetDescription.newFormDescriptionEditorWidgetDescription(widgetId)
                        .label(widgetLabel)
                        .kind(widgetKind)
                        .build();
                // @formatter:on
                FormDescriptionEditorWidgetComponentProps fdeWidgetComponentProps = new FormDescriptionEditorWidgetComponentProps(fdeWidgetDescription);
                childrenWidgets.add(new Element(FormDescriptionEditorWidgetComponent.class, fdeWidgetComponentProps));
            }
        });

        // @formatter:off
        FormDescriptionEditorElementProps formDescriptionEditorElementProps = FormDescriptionEditorElementProps.newFormDescriptionEditorElementProps(id)
                .label(label)
                .targetObjectId(targetObjectId)
                .descriptionId(formDescriptionEditorDescription.getId())
                .children(childrenWidgets)
                .build();
        // @formatter:on

        return new Element(FormDescriptionEditorElementProps.TYPE, formDescriptionEditorElementProps);
    }

    private String getKind(WidgetDescription widget) {
        return widget.eClass().getName().replace("Description", ""); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private List<AbstractFormDescriptionEditorWidgetDescription> transformDescriptions(List<WidgetDescription> widgetDescriptions, VariableManager variableManager,
            Function<VariableManager, String> targetObjectIdProvider) {
        List<AbstractFormDescriptionEditorWidgetDescription> fdeWidgetDescriptions = new ArrayList<>();

        widgetDescriptions.forEach(widgetDescription -> {

            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, widgetDescription);
            String widgetId = targetObjectIdProvider.apply(childVariableManager);
            String widgetKind = this.getKind(widgetDescription);
            String widgetLabel = widgetDescription.getName();
            if (widgetLabel == null) {
                widgetLabel = widgetKind;
            }

            if (widgetDescription instanceof FlexboxContainerDescription) {
                List<AbstractFormDescriptionEditorWidgetDescription> childrenDescriptions = this.transformDescriptions(((FlexboxContainerDescription) widgetDescription).getChildren(),
                        childVariableManager, targetObjectIdProvider);
                // @formatter:off
                FormDescriptionEditorFlexboxContainerDescription fdeFlexboxContainerDescription = FormDescriptionEditorFlexboxContainerDescription.newFormDescriptionEditorFlexboxContainerDescription(widgetId)
                        .label(widgetLabel)
                        .kind(widgetKind)
                        .flexDirection(((FlexboxContainerDescription) widgetDescription).getFlexDirection())
                        .children(childrenDescriptions)
                        .build();
                // @formatter:on
                fdeWidgetDescriptions.add(fdeFlexboxContainerDescription);
            } else if (widgetDescription instanceof WidgetDescription) {
                // @formatter:off
                FormDescriptionEditorWidgetDescription fdeWidgetDescription = FormDescriptionEditorWidgetDescription.newFormDescriptionEditorWidgetDescription(widgetId)
                        .label(widgetLabel)
                        .kind(widgetKind)
                        .build();
                // @formatter:on
                fdeWidgetDescriptions.add(fdeWidgetDescription);
            }
        });
        return fdeWidgetDescriptions;
    }
}
