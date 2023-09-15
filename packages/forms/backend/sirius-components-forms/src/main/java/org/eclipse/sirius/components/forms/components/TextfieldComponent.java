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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.CompletionRequest;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.forms.elements.TextfieldElementProps;
import org.eclipse.sirius.components.forms.elements.TextfieldElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the textfield.
 *
 * @author sbegaudeau
 */
public class TextfieldComponent implements IComponent {

    private final TextfieldComponentProps props;

    public TextfieldComponent(TextfieldComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        TextfieldDescription textfieldDescription = this.props.getTextfieldDescription();

        String label = textfieldDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, textfieldDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, textfieldDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = textfieldDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = textfieldDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = textfieldDescription.getIsReadOnlyProvider().apply(variableManager);
        String value = textfieldDescription.getValueProvider().apply(variableManager);
        Function<String, IStatus> specializedHandler = newValue -> textfieldDescription.getNewValueHandler().apply(variableManager, newValue);
        var textfieldStyle = textfieldDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(textfieldDescription, variableManager)));

        Builder textfieldElementPropsBuilder = TextfieldElementProps.newTextfieldElementProps(id)
                .label(label)
                .value(value)
                .newValueHandler(specializedHandler)
                .children(children);

        if (textfieldDescription.getCompletionProposalsProvider() != null) {
            Function<CompletionRequest, List<CompletionProposal>> proposalsProvider = request -> {
                VariableManager completionVariables = variableManager.createChild();
                completionVariables.put(CompletionRequest.CURRENT_TEXT, request.getCurrentText());
                completionVariables.put(CompletionRequest.CURSOR_POSITION, request.getCursorPosition());
                return textfieldDescription.getCompletionProposalsProvider().apply(completionVariables);
            };
            textfieldElementPropsBuilder.completionProposalsProvider(proposalsProvider);
        }
        if (iconURL != null) {
            textfieldElementPropsBuilder.iconURL(iconURL);
        }
        if (textfieldStyle != null) {
            textfieldElementPropsBuilder.style(textfieldStyle);
        }
        if (textfieldDescription.getHelpTextProvider() != null) {
            textfieldElementPropsBuilder.helpTextProvider(() -> textfieldDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            textfieldElementPropsBuilder.readOnly(readOnly);
        }

        TextfieldElementProps textfieldElementProps = textfieldElementPropsBuilder.build();

        return new Element(TextfieldElementProps.TYPE, textfieldElementProps);
    }
}
