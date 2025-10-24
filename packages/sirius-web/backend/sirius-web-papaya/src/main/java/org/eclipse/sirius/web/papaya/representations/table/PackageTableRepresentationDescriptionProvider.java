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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.tables.CursorBasedNavigationServices;
import org.eclipse.sirius.components.papaya.AnnotableElement;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Record;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.papaya.spec.PackageSpec;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.IconLabelCellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.MultiSelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.SelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.descriptions.TextareaCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.components.tables.renderer.TableRenderer;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of a table on Papaya package element. <br> This is an example to demonstrate how to use a table description.
 *
 * @author Jerome Gout
 */
@Service
public class PackageTableRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String TABLE_DESCRIPTION_ID = "papaya_package_table_description";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final ObjectMapper objectMapper;

    public PackageTableRepresentationDescriptionProvider(IIdentityService identityService, ILabelService labelService, ObjectMapper objectMapper) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        Function<VariableManager, String> headerLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getStyledLabel)
                .map(Objects::toString)
                .orElse(null);

        Function<VariableManager, List<String>> headerIconURLsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getImagePaths)
                .orElse(List.of());

        Function<VariableManager, String> headerIndexLabelProvider = variableManager -> variableManager.get("rowIndex", Integer.class)
                .map(String::valueOf)
                .orElse(null);

        var lineDescription = LineDescription.newLineDescription(UUID.nameUUIDFromBytes("Table - Line".getBytes()).toString())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .semanticElementsProvider(this::getSemanticElements)
                .headerLabelProvider(headerLabelProvider)
                .headerIconURLsProvider(headerIconURLsProvider)
                .headerIndexLabelProvider(headerIndexLabelProvider)
                .isResizablePredicate(variableManager -> true)
                .initialHeightProvider(variableManager -> 53)
                .depthLevelProvider(this::getSemanticElementDepthLevel)
                .hasChildrenProvider(this::hasChildren)
                .build();

        var tableDescription = TableDescription.newTableDescription(TABLE_DESCRIPTION_ID)
                .label("Papaya package table")
                .labelProvider(new TableLabelProvider(this.labelService))
                .canCreatePredicate(this::canCreate)
                .lineDescription(lineDescription)
                .columnDescriptions(this.getColumnDescriptions(editingContext))
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellDescriptions(this.getCellDescriptions())
                .iconURLsProvider(variableManager -> List.of("/papaya-representations/package-table.svg"))
                .isStripeRowPredicate(variableManager -> true)
                .enableSubRows(true)
                .pageSizeOptionsProvider(variableManager -> List.of(5, 10, 20, 50))
                .defaultPageSizeIndexProvider(variableManager -> 1)
                .build();

        return List.of(tableDescription);
    }


    private boolean canCreate(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .filter(PackageSpec.class::isInstance)
                .isPresent();
    }

    private PaginatedData getSemanticElements(VariableManager variableManager) {
        var self = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var cursor = variableManager.get(TableRenderer.PAGINATION_CURSOR, EObject.class).orElse(null);
        var direction = variableManager.get(TableRenderer.PAGINATION_DIRECTION, String.class).orElse(null);
        var size = variableManager.get(TableRenderer.PAGINATION_SIZE, Integer.class).orElse(0);
        var globalFilter = variableManager.get(TableRenderer.GLOBAL_FILTER_DATA, String.class).orElse(null);
        List<ColumnFilter> columnFilters = variableManager.get(TableRenderer.COLUMN_FILTERS, List.class).orElse(List.of());
        List<String> expandedIds = variableManager.get(TableRenderer.EXPANDED_IDS, List.class).orElse(List.of());
        List<String> activeRowFilterIds = variableManager.get(TableRenderer.ACTIVE_ROW_FILTER_IDS, List.class).orElse(List.of());

        Predicate<EObject> predicate = eObject -> {
            boolean isValidCandidate = this.isValidType(eObject, self) && this.hasExpandedParent(eObject, expandedIds) && this.validateFilters(eObject, activeRowFilterIds);
            if (isValidCandidate && eObject instanceof Type type) {
                if (globalFilter != null && !globalFilter.isBlank()) {
                    isValidCandidate = type.getName() != null && type.getName().contains(globalFilter);
                    isValidCandidate = isValidCandidate || type.getDescription() != null && type.getDescription().contains(globalFilter);
                    isValidCandidate = isValidCandidate || type.getVisibility() != null && type.getVisibility().getLiteral().contains(globalFilter);
                    isValidCandidate = isValidCandidate || type.getAnnotations().stream().anyMatch(annotation -> annotation.getName().contains(globalFilter));
                }
                isValidCandidate = isValidCandidate && columnFilters.stream().allMatch(new PapayaColumnFilterPredicate(this.objectMapper, type));
            }
            if (isValidCandidate && eObject instanceof Operation operation) {
                if (globalFilter != null && !globalFilter.isBlank()) {
                    isValidCandidate = operation.getName() != null && operation.getName().contains(globalFilter);
                    isValidCandidate = isValidCandidate || operation.getDescription() != null && operation.getDescription().contains(globalFilter);
                    isValidCandidate = isValidCandidate || operation.getVisibility() != null && operation.getVisibility().getLiteral().contains(globalFilter);
                }
            }
            if (isValidCandidate && eObject instanceof Parameter parameter) {
                if (globalFilter != null && !globalFilter.isBlank()) {
                    isValidCandidate = parameter.getName() != null && parameter.getName().contains(globalFilter);
                    isValidCandidate = isValidCandidate || parameter.getDescription() != null && parameter.getDescription().contains(globalFilter);
                }
            }
            return isValidCandidate;
        };

        return new CursorBasedNavigationServices().collect(self, cursor, direction, size, predicate);
    }

    private boolean validateFilters(EObject eObject, List<String> activeRowFilterIds) {
        // by default element is not filtered
        boolean isValid = true;
        if (activeRowFilterIds.contains(PackageTableRowFiltersProvider.HIDE_RECORDS_ROW_FILTER_ID)) {
            isValid = !(eObject instanceof Record);
        }
        return isValid;
    }

    private boolean isValidType(EObject eObject, EObject self) {
        // first should be inside the self element
        boolean isValid = EcoreUtil.isAncestor(self, eObject);
        boolean isValidElement = eObject instanceof Type ||
                eObject instanceof Operation ||
                // only consider parameter of regular operation (not constructor)
                (eObject instanceof Parameter && eObject.eContainer() instanceof Operation);
        return isValid && isValidElement;
    }

    private boolean hasExpandedParent(EObject eObject, List<String> expandedIds) {
        EObject parent = eObject.eContainer();
        if (parent != null && !(parent instanceof Package)) {
            var parentId = this.identityService.getId(parent);
            return expandedIds.contains(parentId) && this.hasExpandedParent(parent, expandedIds);
        }
        return true;
    }

    private boolean hasChildren(VariableManager variableManager) {
        var hasChildren = false;

        var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalEObject.isPresent()) {
            var eObject = optionalEObject.get();
            hasChildren = eObject.eContents().stream()
                    .anyMatch(childEObject -> childEObject instanceof Operation || childEObject instanceof Parameter);
        }

        return hasChildren;
    }

    private Integer getSemanticElementDepthLevel(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, EObject.class)
                .map(this::getEObjectDepthLevel)
                .orElse(0);
    }

    private Integer getEObjectDepthLevel(EObject eObject) {
        if (eObject instanceof Package) {
            return -1;
        } else {
            return 1 + this.getEObjectDepthLevel(eObject.eContainer());
        }
    }

    private List<ColumnDescription> getColumnDescriptions(IEditingContext editingContext) {
        var optionalAdapterFactory = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getAdapterFactory);

        var provider = new StructuralFeatureToDisplayNameProvider(new DisplayNameProvider(optionalAdapterFactory.get()));
        Map<EStructuralFeature, String> featureToDisplayName = provider.getColumnsStructuralFeaturesDisplayName(PapayaFactory.eINSTANCE.createClass(), PapayaPackage.eINSTANCE.getType());

        ColumnDescription iconColumnDescription = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("icon".getBytes()).toString())
                .semanticElementsProvider(variableManager -> List.of("IconColumn"))
                .headerLabelProvider(variableManager -> "Icon")
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "A")
                .targetObjectIdProvider(new ColumnTargetObjectIdProvider())
                .targetObjectKindProvider(variableManager -> "")
                .initialWidthProvider(variableManager -> 130)
                .isResizablePredicate(variableManager -> false)
                .isSortablePredicate(variableManager -> false)
                .filterVariantProvider(variableManager -> "text")
                .build();

        ColumnDescription nbAnnotationsColumn = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("nbAnnotation".getBytes()).toString())
                .semanticElementsProvider(variableManager -> List.of("NbAnnotationColumn"))
                .headerLabelProvider(variableManager -> "Annotation number")
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "F")
                .targetObjectIdProvider(variableManager -> "papaya.Type#nbAnnotation")
                .targetObjectKindProvider(variableManager -> "")
                .initialWidthProvider(variableManager -> 250)
                .isResizablePredicate(variableManager -> false)
                .isSortablePredicate(variableManager -> false)
                .filterVariantProvider(variableManager -> "range")
                .build();

        Function<VariableManager, String> headerLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, EStructuralFeature.class)
                .map(featureToDisplayName::get)
                .orElse("");

        Function<VariableManager, List<String>> headerIconURLsProvider = variableManager -> variableManager.get(VariableManager.SELF, EStructuralFeature.class)
                .map(this.labelService::getImagePaths)
                .orElse(List.of());

        Function<VariableManager, String> headerIndexLabelProvider = variableManager -> variableManager.get("columnIndex", Integer.class)
                .map(index -> String.valueOf((char) (index + 1 + 'A')))
                .orElse("");

        ColumnDescription columnDescription = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("features".getBytes()).toString())
                .semanticElementsProvider(variableManager -> featureToDisplayName.keySet().stream().map(Object.class::cast).toList())
                .headerLabelProvider(headerLabelProvider)
                .headerIconURLsProvider(headerIconURLsProvider)
                .headerIndexLabelProvider(headerIndexLabelProvider)
                .targetObjectIdProvider(new ColumnTargetObjectIdProvider())
                .targetObjectKindProvider(variableManager -> "")
                .initialWidthProvider(variableManager -> 180)
                .isResizablePredicate(variableManager -> true)
                .isSortablePredicate(variableManager -> false)
                .filterVariantProvider(variableManager -> "text")
                .build();
        return List.of(iconColumnDescription, columnDescription, nbAnnotationsColumn);
    }

    private List<ICellDescription> getCellDescriptions() {
        List<ICellDescription> cellDescriptions = new ArrayList<>();

        cellDescriptions.add(TextfieldCellDescription.newTextfieldCellDescription("textfieldCells")
                .canCreatePredicate(new CellTypePredicate().isTextfieldCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringValueProvider(this.identityService))
                .cellTooltipValueProvider(new CellStringValueProvider(this.identityService))
                .build());

        cellDescriptions.add(TextareaCellDescription.newTextareaCellDescription("textareaCells")
                .canCreatePredicate(new CellTypePredicate().isTextareaCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringValueProvider(this.identityService))
                .cellTooltipValueProvider(new CellStringValueProvider(this.identityService))
                .build());

        cellDescriptions.add(SelectCellDescription.newSelectCellDescription("selectCells")
                .canCreatePredicate(new CellTypePredicate().isSelectCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringValueProvider(this.identityService))
                .cellOptionsIdProvider(new CellOptionIdProvider(this.identityService, this.labelService))
                .cellOptionsLabelProvider(new CellOptionLabelProvider(this.labelService))
                .cellOptionsProvider(new CellOptionsProvider())
                .cellTooltipValueProvider(new CellStringValueProvider(this.identityService))
                .build());

        cellDescriptions.add(MultiSelectCellDescription.newMultiSelectCellDescription("multiselectCells")
                .canCreatePredicate(new CellTypePredicate().isMultiselectCell())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider(new CellStringListValueProvider(this.identityService))
                .cellOptionsIdProvider(new CellOptionIdProvider(this.identityService, this.labelService))
                .cellOptionsLabelProvider(new CellOptionLabelProvider(this.labelService))
                .cellOptionsProvider(new CellOptionsProvider())
                .cellTooltipValueProvider((variableManager, o) -> "")
                .build());


        Predicate<VariableManager> canCreateIconLabelPredicate = variableManager -> variableManager.get(ColumnDescription.COLUMN_TARGET_OBJECT, Object.class)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(value -> value.equals("IconColumn"))
                .isPresent();

        BiFunction<VariableManager, Object, List<String>> iconLabelCellIconURLsProvider = (variableManager, columnTargetObject) -> variableManager.get(VariableManager.SELF, EObject.class)
                .map(this.labelService::getImagePaths)
                .orElse(List.of());

        cellDescriptions.add(IconLabelCellDescription.newIconLabelCellDescription("iconLabelCells")
                .canCreatePredicate(canCreateIconLabelPredicate)
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellValueProvider((variableManager, columnTargetObject) -> "")
                .cellIconURLsProvider(iconLabelCellIconURLsProvider)
                .cellTooltipValueProvider((variableManager, o) -> "")
                .build());

        Predicate<VariableManager> nbAnnotationCellPredicate = variableManager -> variableManager.get(ColumnDescription.COLUMN_TARGET_OBJECT, Object.class)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(value -> value.equals("NbAnnotationColumn"))
                .isPresent();

        cellDescriptions.add(IconLabelCellDescription.newIconLabelCellDescription("nbAnnotationCells")
                .canCreatePredicate(nbAnnotationCellPredicate)
                .targetObjectIdProvider(variableManager -> "")
                .targetObjectKindProvider(variableManager -> "")
                .cellValueProvider((variableManager, columnTargetObject) -> variableManager.get(VariableManager.SELF, Object.class)
                        .filter(AnnotableElement.class::isInstance)
                        .map(AnnotableElement.class::cast)
                        .map(annotableElement -> annotableElement.getAnnotations().size())
                        .map(String::valueOf)
                        .orElse("NA"))
                .cellIconURLsProvider((variableManager, object) -> List.of())
                .cellTooltipValueProvider((variableManager, o) -> "")
                .build());
        return cellDescriptions;
    }
}
