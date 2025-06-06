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
import org.eclipse.sirius.components.tables.descriptions.SelectCellDescription;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;

/**
 * The component used to render Select-based cells.
 *
 * @author arichard
 * @author lfasani
 */
public class SelectCellComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate";

    private final SelectCellComponentProps props;

    public SelectCellComponent(SelectCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        SelectCellDescription cellDescription = this.props.selectCellDescription();

        String targetObjectId = cellDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = cellDescription.getTargetObjectKindProvider().apply(variableManager);

        String value = cellDescription.getCellValueProvider().apply(variableManager, this.props.columnTargetObject());
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

        String tooltipValue = cellDescription.getCellTooltipValueProvider().apply(variableManager, this.props.columnTargetObject());

        SelectCellElementProps cellElementProps = new SelectCellElementProps(this.props.cellId(), this.props.selectCellDescription().getId(), targetObjectId, targetObjectKind, this.props.columnId()
                , options, value, tooltipValue);
        return new Element(SelectCellElementProps.TYPE, cellElementProps);
    }
}
