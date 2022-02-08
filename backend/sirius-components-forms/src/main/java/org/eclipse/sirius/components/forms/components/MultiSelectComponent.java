/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

    public static final String CANDIDATE_VARIABLE = "candidate"; //$NON-NLS-1$

    private MultiSelectComponentProps props;

    public MultiSelectComponent(MultiSelectComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        MultiSelectDescription multiSelectDescription = this.props.getMultiSelectDescription();

        String id = multiSelectDescription.getIdProvider().apply(variableManager);
        String label = multiSelectDescription.getLabelProvider().apply(variableManager);
        List<?> optionCandidates = multiSelectDescription.getOptionsProvider().apply(variableManager);
        List<String> values = multiSelectDescription.getValuesProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(multiSelectDescription, variableManager)));

        List<SelectOption> options = new ArrayList<>(optionCandidates.size());
        for (Object candidate : optionCandidates) {
            VariableManager optionVariableManager = variableManager.createChild();
            optionVariableManager.put(CANDIDATE_VARIABLE, candidate);

            String optionId = multiSelectDescription.getOptionIdProvider().apply(optionVariableManager);
            String optionLabel = multiSelectDescription.getOptionLabelProvider().apply(optionVariableManager);

            // @formatter:off
            SelectOption option = SelectOption.newSelectOption(optionId)
                    .label(optionLabel)
                    .build();
            // @formatter:on

            options.add(option);
        }
        Function<List<String>, IStatus> newValuesHandler = newValues -> {
            return multiSelectDescription.getNewValuesHandler().apply(variableManager, newValues);
        };

        // @formatter:off
        MultiSelectElementProps selectElementProps = MultiSelectElementProps.newMultiSelectElementProps(id)
                .label(label)
                .options(options)
                .values(values)
                .newValuesHandler(newValuesHandler)
                .children(children)
                .build();
        return new Element(MultiSelectElementProps.TYPE, selectElementProps);
        // @formatter:on
    }
}
