/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.tables.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.SelectCellOption;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;

/**
 * The component used to render Multi-Select-based cells.
 *
 * @author arichard
 */
public class MultiSelectCellComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate"; //$NON-NLS-1$

    private final MultiSelectCellComponentProps props;

    public MultiSelectCellComponent(MultiSelectCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        String featureName = this.props.getFeatureName();
        Object value = this.props.getCellValuesProvider().apply(variableManager, featureName);
        List<String> values = new ArrayList<>();
        if (value instanceof Collection<?>) {
            values.addAll(((Collection<?>) value).stream().map(item -> item.toString()).collect(Collectors.toList()));
        }

        List<Object> optionCandidates = this.props.getCellOptionsProvider().apply(variableManager, featureName);

        List<SelectCellOption> options = new ArrayList<>();
        for (Object candidate : optionCandidates) {
            VariableManager optionVariableManager = variableManager.createChild();
            optionVariableManager.put(CANDIDATE_VARIABLE, candidate);

            String optionId = this.props.getCellOptionIdProvider().apply(optionVariableManager);
            String optionLabel = this.props.getCellOptionLabelProvider().apply(optionVariableManager);

            SelectCellOption option = SelectCellOption.newSelectCellOption(optionId)
                    .label(optionLabel)
                    .build();

            options.add(option);
        }
        MultiSelectCellElementProps cellElementProps = MultiSelectCellElementProps.newMultiSelectCellElementProps(this.props.getCellId())
                .parentLineId(this.props.getParentLineId())
                .columnId(this.props.getColumnId())
                .options(options)
                .values(values)
                .build();
        return new Element(MultiSelectCellElementProps.TYPE, cellElementProps);

    }

}
