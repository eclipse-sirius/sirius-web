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
import org.eclipse.sirius.components.forms.elements.ButtonElementProps;
import org.eclipse.sirius.components.forms.elements.ButtonElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the button.
 *
 * @author arichard
 */
public class ButtonComponent implements IComponent {

    private ButtonComponentProps props;

    public ButtonComponent(ButtonComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ButtonDescription buttonDescription = this.props.getButtonDescription();

        String id = buttonDescription.getIdProvider().apply(variableManager);
        String label = buttonDescription.getLabelProvider().apply(variableManager);
        String iconURL = buttonDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = buttonDescription.getIsReadOnlyProvider().apply(variableManager);
        String buttonLabel = buttonDescription.getButtonLabelProvider().apply(variableManager);
        String imageURL = buttonDescription.getImageURLProvider().apply(variableManager);
        Function<VariableManager, IStatus> pushButtonHandler = buttonDescription.getPushButtonHandler();
        Supplier<IStatus> specializedHandler = () -> pushButtonHandler.apply(variableManager);

        var buttonStyle = buttonDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(buttonDescription, variableManager)));

        Builder buttonElementPropsBuilder = ButtonElementProps.newButtonElementProps(id)
                .label(label)
                .pushButtonHandler(specializedHandler)
                .children(children);

        if (iconURL != null) {
            buttonElementPropsBuilder.iconURL(iconURL);
        }

        if (buttonLabel != null) {
            buttonElementPropsBuilder.buttonLabel(buttonLabel);
        }
        if (imageURL != null) {
            buttonElementPropsBuilder.imageURL(imageURL);
        }
        if (buttonStyle != null) {
            buttonElementPropsBuilder.style(buttonStyle);
        }
        if (buttonDescription.getHelpTextProvider() != null) {
            buttonElementPropsBuilder.helpTextProvider(() -> buttonDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            buttonElementPropsBuilder.readOnly(readOnly);
        }

        ButtonElementProps buttonElementProps = buttonElementPropsBuilder.build();

        return new Element(ButtonElementProps.TYPE, buttonElementProps);
    }
}
