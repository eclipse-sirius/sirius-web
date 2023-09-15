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
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.RadioOption;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.forms.elements.RadioElementProps;
import org.eclipse.sirius.components.forms.elements.RadioElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the radio widget and its options.
 *
 * @author sbegaudeau
 */
public class RadioComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate";

    private final RadioComponentProps props;

    public RadioComponent(RadioComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        RadioDescription radioDescription = this.props.getRadioDescription();

        String label = radioDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, radioDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, radioDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = radioDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = radioDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = radioDescription.getIsReadOnlyProvider().apply(variableManager);
        List<?> optionCandidates = radioDescription.getOptionsProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(radioDescription, variableManager)));

        List<RadioOption> options = new ArrayList<>(optionCandidates.size());
        for (Object candidate : optionCandidates) {
            VariableManager optionVariableManager = variableManager.createChild();
            optionVariableManager.put(CANDIDATE_VARIABLE, candidate);

            String optionId = radioDescription.getOptionIdProvider().apply(optionVariableManager);
            String optionLabel = radioDescription.getOptionLabelProvider().apply(optionVariableManager);
            Boolean optionSelected = radioDescription.getOptionSelectedProvider().apply(optionVariableManager);

            RadioOption option = RadioOption.newRadioOption(optionId)
                    .label(optionLabel)
                    .selected(optionSelected)
                    .build();

            options.add(option);
        }

        BiFunction<VariableManager, String, IStatus> genericHandler = radioDescription.getNewValueHandler();
        Function<String, IStatus> specializedHandler = newValue -> genericHandler.apply(variableManager, newValue);
        var radioStyle = radioDescription.getStyleProvider().apply(variableManager);

        Builder radioElementPropsBuilder = RadioElementProps.newRadioElementProps(id)
                .label(label)
                .options(options)
                .newValueHandler(specializedHandler)
                .children(children);

        if (iconURL != null) {
            radioElementPropsBuilder.iconURL(iconURL);
        }
        if (radioStyle != null) {
            radioElementPropsBuilder.style(radioStyle);
        }
        if (radioDescription.getHelpTextProvider() != null) {
            radioElementPropsBuilder.helpTextProvider(() -> radioDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            radioElementPropsBuilder.readOnly(readOnly);
        }

        RadioElementProps radioElementProps = radioElementPropsBuilder.build();

        return new Element(RadioElementProps.TYPE, radioElementProps);
    }
}
