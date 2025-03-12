/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.components.view.emf.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.tables.api.IRowFilterProvider;
import org.eclipse.sirius.components.collaborative.tables.api.RowFilter;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.table.RowFilterDescription;
import org.springframework.stereotype.Service;

/**
 * Table row filters provider for view table model.
 *
 * @author Jerome Gout
 */
@Service
public class ViewRowFilterProvider implements IRowFilterProvider {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    public ViewRowFilterProvider(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
            IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService,
            IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TableDescription tableDescription, String representationId) {
        return this.viewRepresentationDescriptionPredicate.test(tableDescription);
    }

    @Override
    public List<RowFilter> get(IEditingContext editingContext, TableDescription tableDescription, String representationId) {
        List<RowFilter> result = new ArrayList<RowFilter>();
        var optionalTableDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, tableDescription.getId())
                .filter(org.eclipse.sirius.components.view.table.TableDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.table.TableDescription.class::cast);
        if (optionalTableDescription.isPresent()) {
            var viewTableDescription = optionalTableDescription.get();

            if (viewTableDescription.eContainer() instanceof View view) {
                AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, view);
                VariableManager variableManager = new VariableManager();
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put("descriptionId", tableDescription.getId());
                variableManager.put(EditingContextEventProcessor.REPRESENTATION_ID, representationId);
                result = viewTableDescription.getRowFilters().stream()
                        .map(filterDescription -> this.convertRowFilterDescription(filterDescription, interpreter, variableManager))
                        .toList();
            }
        }
        return result;
    }

    private RowFilter convertRowFilterDescription(RowFilterDescription rowFilterDescription, AQLInterpreter interpreter, VariableManager variableManager) {
        String label = interpreter.evaluateExpression(variableManager.getVariables(), rowFilterDescription.getLabelExpression()).asString().orElse("");
        boolean initialState = interpreter.evaluateExpression(variableManager.getVariables(), rowFilterDescription.getInitialStateExpression()).asBoolean().orElse(false);
        return new RowFilter(rowFilterDescription.getId(), label, initialState);
    }
}
