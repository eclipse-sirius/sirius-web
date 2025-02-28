/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.tables.descriptions.MultiSelectCellDescription;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;

/**
 * The component used to render Multi-Select-based cells.
 *
 * @author arichard
 * @author lfasani
 */
public class MultiSelectCellComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate";

    private final MultiSelectCellComponentProps props;

    public MultiSelectCellComponent(MultiSelectCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        MultiSelectCellDescription cellDescription = this.props.multiSelectCellDescription();

        String targetObjectId = cellDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = cellDescription.getTargetObjectKindProvider().apply(variableManager);

        List<String> values = cellDescription.getCellValueProvider().apply(variableManager, this.props.columnTargetObject());

        List<Object> optionCandidates = cellDescription.getCellOptionsProvider().apply(variableManager, this.props.columnTargetObject());

        List<SelectCellOption> options = new ArrayList<>();
        for (Object candidate : optionCandidates) {
            VariableManager optionVariableManager = variableManager.createChild();
            optionVariableManager.put(CANDIDATE_VARIABLE, candidate);

            String optionId = cellDescription.getCellOptionsIdProvider().apply(optionVariableManager);
            String optionLabel = cellDescription.getCellOptionsLabelProvider().apply(optionVariableManager);

            SelectCellOption option = SelectCellOption.newSelectCellOption(optionId)
                    .label(optionLabel)
                    .build();

            options.add(option);
        }
        MultiSelectCellElementProps cellElementProps = new MultiSelectCellElementProps(this.props.cellId(), this.props.multiSelectCellDescription().getId(), targetObjectId, targetObjectKind, this.props.columnId(), options, values);
        return new Element(MultiSelectCellElementProps.TYPE, cellElementProps);
    }
}
