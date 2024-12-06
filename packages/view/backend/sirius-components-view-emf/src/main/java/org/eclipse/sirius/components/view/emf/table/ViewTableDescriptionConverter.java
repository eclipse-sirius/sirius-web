/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.IconLabelCellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.ViewIconURLsProvider;
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based table description into an equivalent TableDescription.
 *
 * @author frouene
 */
@Service
public class ViewTableDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String DEFAULT_TABLE_LABEL = "Table";

    private final ITableIdProvider tableIdProvider;

    private final IObjectService objectService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    public ViewTableDescriptionConverter(ITableIdProvider tableIdProvider, IObjectService objectService) {
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.objectService = Objects.requireNonNull(objectService);

        this.semanticTargetIdProvider = variableManager -> this.self(variableManager).map(this.objectService::getId).orElse("");
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.objectService::getKind).orElse("");
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.table.TableDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription representationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.table.TableDescription viewTableDescription = (org.eclipse.sirius.components.view.table.TableDescription) representationDescription;

        Predicate<VariableManager> isStripeRowPredicate = variableManager -> {
            if (viewTableDescription.getUseStripedRowsExpression() != null) {
                return interpreter.evaluateExpression(variableManager.getVariables(), viewTableDescription.getUseStripedRowsExpression()).asBoolean().orElse(false);
            }
            return false;
        };
        return TableDescription.newTableDescription(this.tableIdProvider.getId(viewTableDescription))
                .label(Optional.ofNullable(viewTableDescription.getName()).orElse(DEFAULT_TABLE_LABEL))
                .labelProvider(variableManager -> this.computeTableLabel(viewTableDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreate(viewTableDescription.getDomainType(), viewTableDescription.getPreconditionExpression(), variableManager, interpreter))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .columnDescriptions(this.getColumnDescriptions(viewTableDescription, interpreter))
                .lineDescription(this.getRowDescription(viewTableDescription, interpreter))
                .cellDescriptions(this.getCellDescriptions(viewTableDescription, interpreter))
                .isStripeRowPredicate(isStripeRowPredicate)
                .iconURLsProvider(new ViewIconURLsProvider(interpreter, viewTableDescription.getIconExpression()))
                .build();
    }


    private List<ColumnDescription> getColumnDescriptions(org.eclipse.sirius.components.view.table.TableDescription viewTableDescription, AQLInterpreter interpreter) {
        return viewTableDescription.getColumnDescriptions().stream().map(columnDescription -> this.convertColumnDescription(columnDescription, interpreter)).toList();
    }

    private ColumnDescription convertColumnDescription(org.eclipse.sirius.components.view.table.ColumnDescription columnDescription, AQLInterpreter interpreter) {
        return ColumnDescription.newColumnDescription(this.tableIdProvider.getId(columnDescription))
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .headerLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, columnDescription.getHeaderLabelExpression()))
                .headerIconURLsProvider(new ViewIconURLsProvider(interpreter, columnDescription.getHeaderIconExpression()))
                .headerIndexLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, columnDescription.getHeaderIndexLabelExpression()))
                .semanticElementsProvider(this.getColumnSemanticElementsProvider(columnDescription, interpreter))
                .shouldRenderPredicate(this.getShouldRenderPredicate(columnDescription.getPreconditionExpression(), interpreter))
                .isResizablePredicate(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), columnDescription.getIsResizableExpression()).asBoolean().orElse(false))
                .initialWidthProvider(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), columnDescription.getInitialWidthExpression()).asInt().orElse(-1))
                .build();
    }

    private LineDescription getRowDescription(org.eclipse.sirius.components.view.table.TableDescription viewTableDescription, AQLInterpreter interpreter) {
        return this.convertRowDescription(viewTableDescription.getRowDescription(), interpreter);
    }

    private LineDescription convertRowDescription(org.eclipse.sirius.components.view.table.RowDescription rowDescription, AQLInterpreter interpreter) {
        return LineDescription.newLineDescription(this.tableIdProvider.getId(rowDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .semanticElementsProvider(this.getRowSemanticElementsProvider(rowDescription, interpreter))
                .shouldRenderPredicate(this.getShouldRenderPredicate(rowDescription.getPreconditionExpression(), interpreter))
                .headerLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, rowDescription.getHeaderLabelExpression()))
                .headerIconURLsProvider(new ViewIconURLsProvider(interpreter, rowDescription.getHeaderIconExpression()))
                .headerIndexLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, rowDescription.getHeaderIndexLabelExpression()))
                .build();
    }

    private List<ICellDescription> getCellDescriptions(org.eclipse.sirius.components.view.table.TableDescription viewTableDescription, AQLInterpreter interpreter) {
        return viewTableDescription.getCellDescriptions().stream()
                .map(cellDescription -> this.convertCellDescription(cellDescription, interpreter))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private Optional<ICellDescription> convertCellDescription(org.eclipse.sirius.components.view.table.CellDescription viewCellDescription, AQLInterpreter interpreter) {
        Optional<ICellDescription> optionalICellDescription = Optional.empty();

        if (viewCellDescription.getCellWidgetDescription() instanceof CellTextfieldWidgetDescription cellTextfieldWidgetDescription) {
            optionalICellDescription = Optional.of(TextfieldCellDescription.newTextfieldCellDescription(this.tableIdProvider.getId(viewCellDescription))
                    .targetObjectIdProvider(this.semanticTargetIdProvider)
                    .targetObjectKindProvider(this.semanticTargetKindProvider)
                    .canCreatePredicate(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getPreconditionExpression()).asBoolean().orElse(false))
                    .cellValueProvider((variableManager, columnTargetObject) -> {
                        var child = variableManager.createChild();
                        child.put("columnTargetObject", columnTargetObject);
                        return this.evaluateString(interpreter, child, viewCellDescription.getValueExpression());
                    })
                    .build());
        } else if (viewCellDescription.getCellWidgetDescription() instanceof CellLabelWidgetDescription cellLabelWidgetDescription) {
            optionalICellDescription = Optional.of(IconLabelCellDescription.newIconLabelCellDescription(this.tableIdProvider.getId(viewCellDescription))
                    .targetObjectIdProvider(this.semanticTargetIdProvider)
                    .targetObjectKindProvider(this.semanticTargetKindProvider)
                    .canCreatePredicate(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getPreconditionExpression()).asBoolean().orElse(false))
                    .cellValueProvider((variableManager, columnTargetObject) -> {
                        var child = variableManager.createChild();
                        child.put("columnTargetObject", columnTargetObject);
                        return this.evaluateString(interpreter, child, viewCellDescription.getValueExpression());
                    })
                    .cellIconURLsProvider((variableManager, columnTargetObject) -> {
                        var child = variableManager.createChild();
                        child.put("columnTargetObject", columnTargetObject);
                        return new ViewIconURLsProvider(interpreter, cellLabelWidgetDescription.getIconExpression()).apply(child);
                    })
                    .build());
        }
        return optionalICellDescription;
    }

    private String computeTableLabel(org.eclipse.sirius.components.view.table.TableDescription viewTableDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = variableManager.get(TableDescription.LABEL, String.class)
                .orElseGet(() -> this.evaluateString(interpreter, variableManager, viewTableDescription.getTitleExpression()));
        if (title.isBlank()) {
            return DEFAULT_TABLE_LABEL;
        } else {
            return title;
        }
    }

    private boolean canCreate(String domainType, String preconditionExpression, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        Optional<EClass> optionalEClass = variableManager.get(VariableManager.SELF, EObject.class).map(EObject::eClass).filter(new DomainClassPredicate(domainType));
        if (optionalEClass.isPresent()) {
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private Function<VariableManager, List<Object>> getColumnSemanticElementsProvider(org.eclipse.sirius.components.view.table.TableElementDescription elementDescription, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), elementDescription.getSemanticCandidatesExpression());
            List<Object> candidates = result.asObjects().orElse(List.of());
            if (elementDescription.getDomainType() == null || elementDescription.getDomainType().isBlank()) {
                return candidates;
            }
            return candidates.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .filter(candidate -> new DomainClassPredicate(Optional.ofNullable(elementDescription.getDomainType()).orElse("")).test(candidate.eClass()))
                    .map(Object.class::cast)
                    .toList();
        };
    }

    private Function<VariableManager, PaginatedData> getRowSemanticElementsProvider(org.eclipse.sirius.components.view.table.TableElementDescription elementDescription, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), elementDescription.getSemanticCandidatesExpression());
            List<Object> candidates = result.asObjects().orElse(List.of());
            if (elementDescription.getDomainType() == null || elementDescription.getDomainType().isBlank()) {
                return new PaginatedData(candidates, false, false, candidates.size());
            }
            var list = candidates.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .filter(candidate -> new DomainClassPredicate(Optional.ofNullable(elementDescription.getDomainType()).orElse("")).test(candidate.eClass()))
                    .map(Object.class::cast)
                    .toList();
            return new PaginatedData(list, false, false, list.size());
        };
    }

    private Predicate<VariableManager> getShouldRenderPredicate(String preconditionExpression, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression);
            return result.asBoolean().orElse(true);
        };
    }


    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

}
