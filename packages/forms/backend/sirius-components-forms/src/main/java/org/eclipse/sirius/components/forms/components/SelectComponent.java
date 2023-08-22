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
import java.util.function.Function;

import org.eclipse.sirius.components.forms.SelectOption;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.elements.SelectElementProps;
import org.eclipse.sirius.components.forms.elements.SelectElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the select widget and its options.
 *
 * @author sbegaudeau
 */
public class SelectComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate";

    private SelectComponentProps props;

    public SelectComponent(SelectComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        SelectDescription selectDescription = this.props.getSelectDescription();

        String label = selectDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, selectDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, selectDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = selectDescription.getIdProvider().apply(idVariableManager);

        String iconURL = selectDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = selectDescription.getIsReadOnlyProvider().apply(variableManager);
        List<?> optionCandidates = selectDescription.getOptionsProvider().apply(variableManager);
        String value = selectDescription.getValueProvider().apply(variableManager);
        var selectStyle = selectDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(selectDescription, variableManager)));

        List<SelectOption> options = new ArrayList<>(optionCandidates.size());
        for (Object candidate : optionCandidates) {
            VariableManager optionVariableManager = variableManager.createChild();
            optionVariableManager.put(CANDIDATE_VARIABLE, candidate);

            String optionId = selectDescription.getOptionIdProvider().apply(optionVariableManager);
            String optionLabel = selectDescription.getOptionLabelProvider().apply(optionVariableManager);

            var selectOptionBuilder = SelectOption.newSelectOption(optionId)
                    .label(optionLabel);
            if (selectStyle != null && selectStyle.isShowIcon()) {
                String optionIconUrl = selectDescription.getOptionIconURLProvider().apply(optionVariableManager);
                if (optionIconUrl != null && !optionIconUrl.isBlank()) {
                    selectOptionBuilder.iconURL(optionIconUrl);
                }
            }
            SelectOption option = selectOptionBuilder.build();

            options.add(option);
        }
        Function<String, IStatus> specializedHandler = newValue -> selectDescription.getNewValueHandler().apply(variableManager, newValue);

        Builder selectElementPropsBuilder = SelectElementProps.newSelectElementProps(id)
                .label(label)
                .options(options)
                .value(value)
                .newValueHandler(specializedHandler)
                .children(children);

        if (iconURL != null) {
            selectElementPropsBuilder.iconURL(iconURL);
        }
        if (selectStyle != null) {
            selectElementPropsBuilder.style(selectStyle);
        }
        if (selectDescription.getHelpTextProvider() != null) {
            selectElementPropsBuilder.helpTextProvider(() -> selectDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            selectElementPropsBuilder.readOnly(readOnly);
        }

        SelectElementProps selectElementProps = selectElementPropsBuilder.build();

        return new Element(SelectElementProps.TYPE, selectElementProps);
    }
}
