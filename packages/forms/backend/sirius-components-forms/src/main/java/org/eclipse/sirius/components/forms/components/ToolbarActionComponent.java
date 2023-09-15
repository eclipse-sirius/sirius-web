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
package org.eclipse.sirius.components.forms.components;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.elements.ToolbarActionElementProps;
import org.eclipse.sirius.components.forms.elements.ToolbarActionElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the toolbar action.
 *
 * @author arichard
 */
public class ToolbarActionComponent implements IComponent {

    private final ToolbarActionComponentProps props;

    public ToolbarActionComponent(ToolbarActionComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ButtonDescription buttonDescription = this.props.getButtonDescription();

        String label = buttonDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, buttonDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, buttonDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = buttonDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = buttonDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = buttonDescription.getIsReadOnlyProvider().apply(variableManager);
        String toolbarActionLabel = buttonDescription.getButtonLabelProvider().apply(variableManager);
        String imageURL = buttonDescription.getImageURLProvider().apply(variableManager);
        Function<VariableManager, IStatus> pushButtonHandler = buttonDescription.getPushButtonHandler();
        Supplier<IStatus> specializedHandler = () -> pushButtonHandler.apply(variableManager);

        var toolbarActionStyle = buttonDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(buttonDescription, variableManager)));

        Builder toolbarActionElementPropsBuilder = ToolbarActionElementProps.newToolbarActionElementProps(id)
                .label(label)
                .pushButtonHandler(specializedHandler)
                .children(children);
        if (iconURL != null) {
            toolbarActionElementPropsBuilder.iconURL(iconURL);
        }
        if (toolbarActionLabel != null) {
            toolbarActionElementPropsBuilder.toolbarActionLabel(toolbarActionLabel);
        }
        if (imageURL != null) {
            toolbarActionElementPropsBuilder.imageURL(imageURL);
        }
        if (toolbarActionStyle != null) {
            toolbarActionElementPropsBuilder.style(toolbarActionStyle);
        }
        if (buttonDescription.getHelpTextProvider() != null) {
            toolbarActionElementPropsBuilder.helpTextProvider(() -> buttonDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            toolbarActionElementPropsBuilder.readOnly(readOnly);
        }

        ToolbarActionElementProps toolbarActionElementProps = toolbarActionElementPropsBuilder.build();

        return new Element(ToolbarActionElementProps.TYPE, toolbarActionElementProps);
    }
}
