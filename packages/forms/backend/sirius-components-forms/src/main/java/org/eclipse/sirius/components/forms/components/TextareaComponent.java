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
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.elements.TextareaElementProps;
import org.eclipse.sirius.components.forms.elements.TextareaElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the textarea widget.
 *
 * @author sbegaudeau
 */
public class TextareaComponent implements IComponent {

    private final TextareaComponentProps props;

    public TextareaComponent(TextareaComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        TextareaDescription textareaDescription = this.props.getTextareaDescription();

        String label = textareaDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, textareaDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, textareaDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = textareaDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = textareaDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = textareaDescription.getIsReadOnlyProvider().apply(variableManager);
        String value = textareaDescription.getValueProvider().apply(variableManager);
        Function<String, IStatus> specializedHandler = newValue -> textareaDescription.getNewValueHandler().apply(variableManager, newValue);
        var textareaStyle = textareaDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(textareaDescription, variableManager)));

        Builder textareaElementPropsBuilder = TextareaElementProps.newTextareaElementProps(id)
                .label(label)
                .value(value)
                .newValueHandler(specializedHandler)
                .children(children);

        if (textareaDescription.getCompletionProposalsProvider() != null) {
            Function<CompletionRequest, List<CompletionProposal>> proposalsProvider = request -> {
                VariableManager completionVariables = variableManager.createChild();
                completionVariables.put(CompletionRequest.CURRENT_TEXT, request.getCurrentText());
                completionVariables.put(CompletionRequest.CURSOR_POSITION, request.getCursorPosition());
                return textareaDescription.getCompletionProposalsProvider().apply(completionVariables);
            };
            textareaElementPropsBuilder.completionProposalsProvider(proposalsProvider);
        }
        if (iconURL != null) {
            textareaElementPropsBuilder.iconURL(iconURL);
        }
        if (textareaStyle != null) {
            textareaElementPropsBuilder.style(textareaStyle);
        }
        if (textareaDescription.getHelpTextProvider() != null) {
            textareaElementPropsBuilder.helpTextProvider(() -> textareaDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            textareaElementPropsBuilder.readOnly(readOnly);
        }

        TextareaElementProps textareaElementProps = textareaElementPropsBuilder.build();

        return new Element(TextareaElementProps.TYPE, textareaElementProps);
    }
}
