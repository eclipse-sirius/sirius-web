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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.emf.tables.CursorBasedNavigationServices;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.MultiSelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.SelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.descriptions.TextareaCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.components.tables.renderer.TableRenderer;
import org.eclipse.sirius.web.application.table.customcells.CheckboxCellDescription;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of a table on Papaya project element.
 *
 * @author Jerome Gout
 */
@Service
public class ProjectTableRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String TABLE_DESCRIPTION_ID = "papaya_project_table_description";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final ComposedAdapterFactory composedAdapterFactory;

    public ProjectTableRepresentationDescriptionProvider(IIdentityService identityService, ILabelService labelService, ComposedAdapterFactory composedAdapterFactory) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        var lineDescription = LineDescription.newLineDescription(UUID.nameUUIDFromBytes("Table - Line".getBytes()).toString())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .semanticElementsProvider(this::getSemanticElements)
                .headerLabelProvider(variableManager -> "")
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .isResizablePredicate(variableManager -> false)
                .initialHeightProvider(variableManager -> -1)
                .depthLevelProvider(variableManager -> 0)
                .build();

        var tableDescription = TableDescription.newTableDescription(TABLE_DESCRIPTION_ID)
                .label("Papaya project table")
                .labelProvider(new TableLabelProvider(this.labelService))
                .canCreatePredicate(this::canCreate)
                .lineDescription(lineDescription)
                .columnDescriptions(this.getColumnDescriptions())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellDescriptions(this.getCellDescriptions())
                .iconURLsProvider(variableManager -> List.of("/papaya-representations/project-table.svg"))
                .isStripeRowPredicate(variableManager -> false)
                .enableSubRows(false)
                .pageSizeOptionsProvider(variableManager -> List.of(5, 10, 20, 50))
                .defaultPageSizeIndexProvider(variableManager -> 1)
                .build();

        return List.of(tableDescription);
    }

    private boolean canCreate(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .filter(Project.class::isInstance)
                .isPresent();
    }

    private PaginatedData getSemanticElements(VariableManager variableManager) {
        var self = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var cursor = variableManager.get(TableRenderer.PAGINATION_CURSOR, EObject.class).orElse(null);
        var direction = variableManager.get(TableRenderer.PAGINATION_DIRECTION, String.class).orElse(null);
        var size = variableManager.get(TableRenderer.PAGINATION_SIZE, Integer.class).orElse(0);

        return new CursorBasedNavigationServices().collect(self, cursor, direction, size);
    }

    private List<ColumnDescription> getColumnDescriptions() {
        Map<EStructuralFeature, String> featureToDisplayName = this.getColumnsStructuralFeaturesDisplayName();

        Function<VariableManager, String> headerLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, EStructuralFeature.class)
                .map(featureToDisplayName::get)
                .orElse("");

        ColumnDescription columnDescription = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("features".getBytes()).toString())
                .semanticElementsProvider(this.getSemanticColumnElementsProvider(featureToDisplayName))
                .targetObjectIdProvider(new ColumnTargetObjectIdProvider())
                .targetObjectKindProvider(variableManager -> "")
                .headerLabelProvider(headerLabelProvider)
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .isResizablePredicate(variableManager -> false)
                .isSortablePredicate(variableManager -> false)
                .initialWidthProvider(variableManager -> -1)
                .filterVariantProvider(variableManager -> "text")
                .build();
        return List.of(columnDescription);
    }

    private Function<VariableManager, List<Object>> getSemanticColumnElementsProvider(Map<EStructuralFeature, String> features) {
        return variableManager -> features.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int result = 0;
                    if ("Name".equals(entry1.getValue())) {
                        result = -1;
                    } else if ("Name".equals(entry2.getValue())) {
                        result = 1;
                    } else {
                        result = entry1.getValue().compareTo(entry2.getValue());
                    }
                    return result;
                })
                .map(Map.Entry::getKey)
                .map(Object.class::cast)
                .toList();
    }

    private Map<EStructuralFeature, String> getColumnsStructuralFeaturesDisplayName() {
        Map<EStructuralFeature, String> result = new HashMap<>();
        var objects = List.of(
                PapayaFactory.eINSTANCE.createAnnotation(),
                PapayaFactory.eINSTANCE.createAnnotationField(),
                PapayaFactory.eINSTANCE.createAttribute(),
                PapayaFactory.eINSTANCE.createClass(),
                PapayaFactory.eINSTANCE.createComponent(),
                PapayaFactory.eINSTANCE.createComponentExchange(),
                PapayaFactory.eINSTANCE.createComponentPort(),
                PapayaFactory.eINSTANCE.createConstructor(),
                PapayaFactory.eINSTANCE.createContribution(),
                PapayaFactory.eINSTANCE.createDataType(),
                PapayaFactory.eINSTANCE.createEnum(),
                PapayaFactory.eINSTANCE.createEnumLiteral(),
                PapayaFactory.eINSTANCE.createGenericType(),
                PapayaFactory.eINSTANCE.createInterface(),
                PapayaFactory.eINSTANCE.createIteration(),
                PapayaFactory.eINSTANCE.createOperation(),
                PapayaFactory.eINSTANCE.createPackage(),
                PapayaFactory.eINSTANCE.createParameter(),
                PapayaFactory.eINSTANCE.createProject(),
                PapayaFactory.eINSTANCE.createProvidedService(),
                PapayaFactory.eINSTANCE.createRecord(),
                PapayaFactory.eINSTANCE.createRecordComponent(),
                PapayaFactory.eINSTANCE.createRequiredService(),
                PapayaFactory.eINSTANCE.createTag(),
                PapayaFactory.eINSTANCE.createTask(),
                PapayaFactory.eINSTANCE.createTypeParameter()
        );

        var provider = new StructuralFeatureToDisplayNameProvider(new DisplayNameProvider(this.composedAdapterFactory));
        for (var eObject : objects) {
            this.addStructuralFeature(result, provider.getColumnsStructuralFeaturesDisplayName(eObject, eObject.eClass()));
        }
        return result;
    }

    private void addStructuralFeature(Map<EStructuralFeature, String> result, Map<EStructuralFeature, String> map) {
        map.forEach((key, value) -> {
            if (!result.containsKey(key)) {
                result.put(key, value);
            }
        });
    }

    private List<ICellDescription> getCellDescriptions() {
        List<ICellDescription> cellDescriptions = new ArrayList<>();
        cellDescriptions.add(TextfieldCellDescription.newTextfieldCellDescription("textfieldCells")
                .canCreatePredicate(new CellTypePredicate().isTextfieldCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringValueProvider(this.identityService))
                .build());
        cellDescriptions.add(TextareaCellDescription.newTextareaCellDescription("textareaCells")
                .canCreatePredicate(new CellTypePredicate().isTextareaCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringValueProvider(this.identityService))
                .build());
        cellDescriptions.add(CheckboxCellDescription.newCheckboxCellDescription("checkboxCells")
                .canCreatePredicate(new CellTypePredicate().isCheckboxCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellBooleanValueProvider())
                .build());
        cellDescriptions.add(SelectCellDescription.newSelectCellDescription("selectCells")
                .canCreatePredicate(new CellTypePredicate().isSelectCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringValueProvider(this.identityService))
                .cellOptionsIdProvider(new CellOptionIdProvider(this.identityService, this.labelService))
                .cellOptionsLabelProvider(new CellOptionLabelProvider(this.labelService))
                .cellOptionsProvider(new CellOptionsProvider(this.composedAdapterFactory))
                .build());
        cellDescriptions.add(MultiSelectCellDescription.newMultiSelectCellDescription("multiselectCells")
                .canCreatePredicate(new CellTypePredicate().isMultiselectCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringListValueProvider(this.identityService))
                .cellOptionsIdProvider(new CellOptionIdProvider(this.identityService, this.labelService))
                .cellOptionsLabelProvider(new CellOptionLabelProvider(this.labelService))
                .cellOptionsProvider(new CellOptionsProvider(this.composedAdapterFactory))
                .build());
        return cellDescriptions;
    }
}
