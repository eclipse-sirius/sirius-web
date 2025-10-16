/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.ViewConverterResult;
import org.eclipse.sirius.components.view.emf.ViewIconURLsProvider;
import org.eclipse.sirius.components.view.emf.table.api.ICustomCellConverter;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based table description into an equivalent TableDescription.
 *
 * @author frouene
 */
@Service
public class ViewTableRepresentationDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String DEFAULT_TABLE_LABEL = "Table";

    private final IIdentityService identityService;

    private final ITableIdProvider tableIdProvider;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final List<ICustomCellConverter> customCellConverters;

    public ViewTableRepresentationDescriptionConverter(IIdentityService identityService, ITableIdProvider tableIdProvider, List<ICustomCellConverter> customCellConverters) {
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.identityService = Objects.requireNonNull(identityService);
        this.customCellConverters = Objects.requireNonNull(customCellConverters);

        this.semanticTargetIdProvider = variableManager -> {
            Optional<Object> optionalSelf = this.self(variableManager);
            return optionalSelf.map(this.identityService::getId)
                    .orElseGet(() -> optionalSelf.map(Object::toString).orElse(""));
        };
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.identityService::getKind).orElse("");
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.table.TableDescription;
    }

    @Override
    public ViewConverterResult convert(RepresentationDescription representationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.table.TableDescription viewTableDescription = (org.eclipse.sirius.components.view.table.TableDescription) representationDescription;

        Predicate<VariableManager> isStripeRowPredicate = variableManager -> {
            if (viewTableDescription.getUseStripedRowsExpression() != null) {
                return interpreter.evaluateExpression(variableManager.getVariables(), viewTableDescription.getUseStripedRowsExpression()).asBoolean().orElse(false);
            }
            return false;
        };

        Function<VariableManager, List<Integer>> pageSizeOptionsProvider = variableManager -> {
            if (viewTableDescription.getPageSizeOptionsExpression() != null && !viewTableDescription.getPageSizeOptionsExpression().isBlank()) {
                return interpreter.evaluateExpression(variableManager.getVariables(), viewTableDescription.getPageSizeOptionsExpression())
                        .asObjects()
                        .orElse(List.of(5, 10, 20, 50))
                        .stream()
                        .filter(Integer.class::isInstance)
                        .map(Integer.class::cast)
                        .toList();
            }
            return List.of(5, 10, 20, 50);
        };

        Function<VariableManager, Integer> defaultPageSizeIndexProvider = variableManager -> {
            if (viewTableDescription.getDefaultPageSizeIndexExpression() != null) {
                return interpreter.evaluateExpression(variableManager.getVariables(), viewTableDescription.getDefaultPageSizeIndexExpression()).asInt().orElse(0);
            }
            return 0;
        };

        var tableDescription = TableDescription.newTableDescription(this.tableIdProvider.getId(viewTableDescription))
                .label(Optional.ofNullable(viewTableDescription.getName()).orElse(DEFAULT_TABLE_LABEL))
                .labelProvider(variableManager -> this.computeTableLabel(viewTableDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreate(viewTableDescription.getDomainType(), viewTableDescription.getPreconditionExpression(), variableManager, interpreter))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .columnDescriptions(
                        new ColumnDescriptionConverter(this.tableIdProvider, this.semanticTargetIdProvider, this.semanticTargetKindProvider).convert(viewTableDescription.getColumnDescriptions(),
                                interpreter))
                .lineDescription(new RowDescriptionConverter(this.tableIdProvider, this.semanticTargetIdProvider, this.semanticTargetKindProvider).convert(viewTableDescription.getRowDescription(),
                        interpreter))
                .cellDescriptions(new CellDescriptionConverter(this.identityService, this.tableIdProvider, this.customCellConverters).convert(viewTableDescription.getCellDescriptions(), interpreter))
                .isStripeRowPredicate(isStripeRowPredicate)
                .iconURLsProvider(new ViewIconURLsProvider(interpreter, viewTableDescription.getIconExpression()))
                .enableSubRows(viewTableDescription.isEnableSubRows())
                .pageSizeOptionsProvider(pageSizeOptionsProvider)
                .defaultPageSizeIndexProvider(defaultPageSizeIndexProvider)
                .build();

        return new ViewConverterResult(tableDescription, null);
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


    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

}
