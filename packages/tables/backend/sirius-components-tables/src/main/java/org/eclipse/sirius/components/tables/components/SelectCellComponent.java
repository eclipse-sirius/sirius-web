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
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.SelectCellOption;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;

/**
 * The component used to render Select-based cells.
 *
 * @author arichard
 */
public class SelectCellComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate"; //$NON-NLS-1$

    private final SelectCellComponentProps props;

    public SelectCellComponent(SelectCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        String featureName = this.props.getFeatureName();
        Object value = this.props.getCellValueProvider().apply(variableManager, featureName);
        String stringValue = ""; //$NON-NLS-1$
        if (value instanceof String) {
            stringValue = (String) value;
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
        SelectCellElementProps cellElementProps = SelectCellElementProps.newSelectCellElementProps(this.props.getCellId())
                .parentLineId(this.props.getParentLineId())
                .columnId(this.props.getColumnId())
                .options(options)
                .value(stringValue)
                .build();
        return new Element(SelectCellElementProps.TYPE, cellElementProps);

    }

}
