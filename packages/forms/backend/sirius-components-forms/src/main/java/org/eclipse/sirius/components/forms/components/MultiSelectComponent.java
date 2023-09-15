/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.sirius.components.forms.description.MultiSelectDescription;
import org.eclipse.sirius.components.forms.elements.MultiSelectElementProps;
import org.eclipse.sirius.components.forms.elements.MultiSelectElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the select widget and its options.
 *
 * @author pcdavid
 * @author arichard
 */
public class MultiSelectComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate";

    private final MultiSelectComponentProps props;

    public MultiSelectComponent(MultiSelectComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        MultiSelectDescription multiSelectDescription = this.props.getMultiSelectDescription();

        String label = multiSelectDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, multiSelectDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, multiSelectDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = multiSelectDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = multiSelectDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = multiSelectDescription.getIsReadOnlyProvider().apply(variableManager);
        List<?> optionCandidates = multiSelectDescription.getOptionsProvider().apply(variableManager);
        List<String> values = multiSelectDescription.getValuesProvider().apply(variableManager);
        var multiSelectStyle = multiSelectDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(multiSelectDescription, variableManager)));

        List<SelectOption> options = new ArrayList<>(optionCandidates.size());
        for (Object candidate : optionCandidates) {
            VariableManager optionVariableManager = variableManager.createChild();
            optionVariableManager.put(CANDIDATE_VARIABLE, candidate);

            String optionId = multiSelectDescription.getOptionIdProvider().apply(optionVariableManager);
            String optionLabel = multiSelectDescription.getOptionLabelProvider().apply(optionVariableManager);

            var selectOptionBuilder = SelectOption.newSelectOption(optionId)
                    .label(optionLabel);
            if (multiSelectStyle != null && multiSelectStyle.isShowIcon()) {
                List<String> optionIconUrl = multiSelectDescription.getOptionIconURLProvider().apply(optionVariableManager);
                if (optionIconUrl != null && !optionIconUrl.isEmpty()) {
                    selectOptionBuilder.iconURL(optionIconUrl);
                }
            }
            SelectOption option = selectOptionBuilder.build();

            options.add(option);
        }
        Function<List<String>, IStatus> newValuesHandler = newValues -> multiSelectDescription.getNewValuesHandler().apply(variableManager, newValues);


        Builder multiSelectElementPropsBuilder = MultiSelectElementProps.newMultiSelectElementProps(id)
                .label(label)
                .options(options)
                .values(values)
                .newValuesHandler(newValuesHandler)
                .children(children);

        if (iconURL != null) {
            multiSelectElementPropsBuilder.iconURL(iconURL);
        }

        if (multiSelectStyle != null) {
            multiSelectElementPropsBuilder.style(multiSelectStyle);
        }
        if (multiSelectDescription.getHelpTextProvider() != null) {
            multiSelectElementPropsBuilder.helpTextProvider(() -> multiSelectDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            multiSelectElementPropsBuilder.readOnly(readOnly);
        }

        MultiSelectElementProps multiSelectElementProps = multiSelectElementPropsBuilder.build();

        return new Element(MultiSelectElementProps.TYPE, multiSelectElementProps);
    }
}
