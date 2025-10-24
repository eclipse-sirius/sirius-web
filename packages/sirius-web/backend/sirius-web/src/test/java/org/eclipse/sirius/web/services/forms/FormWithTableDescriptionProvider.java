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
package org.eclipse.sirius.web.services.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.SelectCellComponent;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.MultiSelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.SelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.descriptions.TextareaCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.components.widget.table.TableWidgetDescription;
import org.eclipse.sirius.web.application.table.customcells.CheckboxCellDescription;
import org.eclipse.sirius.web.papaya.representations.table.CellTypePredicate;
import org.eclipse.sirius.web.papaya.representations.table.ColumnTargetObjectIdProvider;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description for the project management concepts.
 *
 * @author lfasani
 */
@Service
public class FormWithTableDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String TASK_FORM_ID = "taskFormDescription";

    public static final String FORM_WITH_TABLE_ID = "tasksTableId";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    public FormWithTableDescriptionProvider(IIdentityService identityService, ILabelService labelService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        TableWidgetDescription tableWidgetDescription = this.getTableWidgetDescription(editingContext);

        GroupDescription taskGroup = GroupDescription.newGroupDescription("iterationGroupId")
                .idProvider(variableManager -> "iterationGroupId")
                .labelProvider(variableManager -> "Iteration Group")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(List.of(tableWidgetDescription))
                .build();

        PageDescription pageDescription = PageDescription.newPageDescription("iterationPageId")
                .idProvider(variableManager -> "iterationPageId")
                .labelProvider(variableManager -> "Iteration Page")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(List.of(taskGroup))
                .canCreatePredicate(variableManager -> true)
                .build();

        FormDescription formDescription = FormDescription.newFormDescription(TASK_FORM_ID)
                .label("Iteration form description")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> "Iteration Form")
                .targetObjectIdProvider(this::getTargetObjectId)
                .canCreatePredicate(this::canCreate)
                .pageDescriptions(List.of(pageDescription))
                .iconURLsProvider(variableManager -> List.of())
                .build();
        return List.of(formDescription);
    }

    private TableWidgetDescription getTableWidgetDescription(IEditingContext editingContext) {
        Function<VariableManager, PaginatedData> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Iteration.class)
                .map(eObject -> {
                    List<Object> objects = new ArrayList<>();
                    objects.addAll(eObject.getTasks());
                    return new PaginatedData(objects, false, false, objects.size());
                })
                .orElse(new PaginatedData(List.of(), false, false, 0));

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getStyledLabel)
                .map(Object::toString)
                .orElse(null);

        LineDescription lineDescription = LineDescription.newLineDescription(UUID.nameUUIDFromBytes("Table - Line".getBytes()).toString())
                .targetObjectIdProvider(this::getTargetObjectId)
                .targetObjectKindProvider(this::getTargetObjectKind)
                .semanticElementsProvider(semanticElementsProvider)
                .headerLabelProvider(variableManager -> "")
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .isResizablePredicate(variableManager -> false)
                .initialHeightProvider(variableManager -> 0)
                .depthLevelProvider(variableManager -> 0)
                .hasChildrenProvider(variableManager -> false)
                .build();

        TableDescription tableDescription = TableDescription.newTableDescription(FORM_WITH_TABLE_ID)
                .label("tasksTableLabel")
                .targetObjectIdProvider(this::getTargetObjectId)
                .targetObjectKindProvider(this::getTargetObjectKind)
                .labelProvider(labelProvider)
                .lineDescription(lineDescription)
                .columnDescriptions(this.getColumnDescriptions(editingContext))
                .cellDescriptions(this.getCellDescriptions())
                .iconURLsProvider(variableManager -> List.of())
                .isStripeRowPredicate(variableManager -> false)
                .enableSubRows(false)
                .pageSizeOptionsProvider(variableManager -> List.of(5, 10, 20, 50))
                .defaultPageSizeIndexProvider(variableManager -> 1)
                .build();

        return TableWidgetDescription.newTableWidgetDescription("tasksTableWidgetId")
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> "Tasks")
                .targetObjectIdProvider(this::getTargetObjectId)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .tableDescription(tableDescription)
                .build();
    }

    private List<ICellDescription> getCellDescriptions() {
        List<ICellDescription> cellDescriptions = new ArrayList<>();

        cellDescriptions.add(TextfieldCellDescription.newTextfieldCellDescription("textfieldCells")
                .canCreatePredicate(new CellTypePredicate().isTextfieldCell())
                .targetObjectIdProvider(variableManager -> "")
                .targetObjectKindProvider(variableManager -> "")
                .cellValueProvider(this.getCellStringValueProvider())
                .cellTooltipValueProvider(this.getCellStringValueProvider())
                .build());

        cellDescriptions.add(TextareaCellDescription.newTextareaCellDescription("textareaCells")
                .canCreatePredicate(new CellTypePredicate().isTextareaCell())
                .targetObjectIdProvider(variableManager -> "")
                .targetObjectKindProvider(variableManager -> "")
                .cellValueProvider(this.getCellStringValueProvider())
                .cellTooltipValueProvider(this.getCellStringValueProvider())
                .build());

        cellDescriptions.add(CheckboxCellDescription.newCheckboxCellDescription("checkboxCells")
                .canCreatePredicate(new CellTypePredicate().isCheckboxCell())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .cellValueProvider(this.getCellBooleanValueProvider())
                .cellTooltipValueProvider((vm, o) -> "")
                .build());

        cellDescriptions.add(SelectCellDescription.newSelectCellDescription("selectCells")
                .canCreatePredicate(new CellTypePredicate().isSelectCell())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .cellValueProvider(this.getCellStringValueProvider())
                .cellOptionsIdProvider(this.getCellOptionsIdProvider())
                .cellOptionsLabelProvider(this.getCellOptionsLabelProvider())
                .cellOptionsProvider(this.getCellOptionsProvider())
                .cellTooltipValueProvider((vm, o) -> "")
                .build());

        cellDescriptions.add(MultiSelectCellDescription.newMultiSelectCellDescription("multiselectCells")
                .canCreatePredicate(new CellTypePredicate().isMultiselectCell())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .cellValueProvider(this.getCellStringListValueProvider())
                .cellOptionsIdProvider(this.getCellOptionsIdProvider())
                .cellOptionsLabelProvider(this.getCellOptionsLabelProvider())
                .cellOptionsProvider(this.getCellOptionsProvider())
                .cellTooltipValueProvider((vm, o) -> "")
                .build());

        return cellDescriptions;
    }

    private String getTargetObjectId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);
    }

    private String getTargetObjectKind(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getKind)
                .orElse(null);
    }

    private boolean canCreate(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, EObject.class)
                .filter(Iteration.class::isInstance)
                .isPresent();
    }

    private List<ColumnDescription> getColumnDescriptions(IEditingContext editingContext) {
        Map<EStructuralFeature, String> featureToDisplayName = this.getColumnsStructuralFeaturesDisplayName(editingContext, PapayaFactory.eINSTANCE.createTask(), PapayaPackage.eINSTANCE.getTask());

        Function<VariableManager, String> headerLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, EStructuralFeature.class)
                .map(featureToDisplayName::get)
                .orElse("");

        ColumnDescription columnDescription = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("features".getBytes()).toString())
                .semanticElementsProvider(variableManager -> featureToDisplayName.keySet().stream().map(Object.class::cast).toList())
                .targetObjectIdProvider(new ColumnTargetObjectIdProvider())
                .targetObjectKindProvider(variableManager -> "")
                .headerLabelProvider(headerLabelProvider)
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .initialWidthProvider(variableManager -> -1)
                .isResizablePredicate(variableManager -> false)
                .filterVariantProvider(variableManager -> "text")
                .isSortablePredicate(variableManager -> false)
                .build();
        return List.of(columnDescription);
    }

    private BiFunction<VariableManager, Object, String> getCellStringValueProvider() {
        return (variableManager, columnTargetObject) -> {
            String value = "";
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EObject eObject = optionalEObject.get();
                Object objectValue = eObject.eGet(eStructuralFeature);
                if (eStructuralFeature instanceof EReference eReference) {
                    if (!eReference.isMany() && !eReference.isContainment()) {
                        value = this.identityService.getId(objectValue);
                    }
                } else if (objectValue != null) {
                    value = objectValue.toString();
                }
            }
            return value;
        };
    }

    private BiFunction<VariableManager, Object, List<String>> getCellStringListValueProvider() {
        return (variableManager, columnTargetObject) -> {
            List<String> value = List.of();
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EObject eObject = optionalEObject.get();
                Object objectValue = eObject.eGet(eStructuralFeature);
                if (eStructuralFeature instanceof EReference eReference) {
                    if (eReference.isMany() && !eReference.isContainment() && objectValue instanceof EList<?>) {
                        value = ((EList<?>) objectValue).stream()
                                .map(this.identityService::getId)
                                .toList();
                    }
                }
            }
            return value;
        };
    }

    private BiFunction<VariableManager, Object, Boolean> getCellBooleanValueProvider() {
        return (variableManager, columnTargetObject) -> {
            boolean value = false;
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EObject eObject = optionalEObject.get();
                Object objectValue = eObject.eGet(eStructuralFeature);
                value = Boolean.parseBoolean(objectValue.toString());
            }
            return value;
        };
    }

    private Function<VariableManager, String> getCellOptionsIdProvider() {
        return variableManager -> {
            Object candidate = variableManager.getVariables().get(SelectCellComponent.CANDIDATE_VARIABLE);
            if (candidate instanceof EEnumLiteral) {
                return this.labelService.getStyledLabel(candidate).toString();
            }
            return this.identityService.getId(candidate);
        };
    }

    private Function<VariableManager, String> getCellOptionsLabelProvider() {
        return variableManager -> {
            Object candidate = variableManager.getVariables().get(SelectCellComponent.CANDIDATE_VARIABLE);
            return this.labelService.getStyledLabel(candidate).toString();
        };
    }

    private BiFunction<VariableManager, Object, List<Object>> getCellOptionsProvider() {
        return (variableManager, columnTargetObject) -> {
            List<Object> options = new ArrayList<>();
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var editingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);

            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature && editingContext.isPresent()) {
                EObject eObject = optionalEObject.get();
                EClassifier eType = eStructuralFeature.getEType();
                if (eType instanceof EEnum eEnum) {
                    options.addAll(eEnum.getELiterals());
                } else {
                    var adapterFactory = editingContext.get().getDomain().getAdapterFactory();
                    var adapter = adapterFactory.adapt(eObject, IItemPropertySource.class);
                    if (adapter instanceof IItemPropertySource itemPropertySource) {
                        IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
                        if (descriptor != null) {
                            return descriptor.getChoiceOfValues(eObject).stream()
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
                        }
                    }
                }
            }
            return options;
        };
    }

    private Map<EStructuralFeature, String> getColumnsStructuralFeaturesDisplayName(IEditingContext editingContext, EObject eObject, EClass eClass) {
        Map<EStructuralFeature, String> featureToDisplayName = new LinkedHashMap<>();
        var optionalAdapterFactory = Optional.of(editingContext)
                    .filter(IEMFEditingContext.class::isInstance)
                    .map(IEMFEditingContext.class::cast)
                    .map(IEMFEditingContext::getDomain)
                    .map(AdapterFactoryEditingDomain::getAdapterFactory);
        if (optionalAdapterFactory.isPresent()) {
            var adapterFactory = optionalAdapterFactory.get();

            EList<EStructuralFeature> eAllStructuralFeatures = eClass.getEAllStructuralFeatures();
            for (EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {
                if (eStructuralFeature instanceof EAttribute && !eStructuralFeature.isMany() && !eStructuralFeature.isDerived()) {
                    featureToDisplayName.put(eStructuralFeature, this.getDisplayName(adapterFactory, eObject, eStructuralFeature));
                } else if (eStructuralFeature instanceof EReference ref && !eStructuralFeature.isDerived() && !ref.isContainment()) {
                    featureToDisplayName.put(eStructuralFeature, this.getDisplayName(adapterFactory, eObject, eStructuralFeature));
                }
            }
        }
        return featureToDisplayName;
    }

    private String getDisplayName(AdapterFactory adapterFactory, EObject eObject, EStructuralFeature eStructuralFeature) {
        Adapter adapter = adapterFactory.adapt(eObject, IItemPropertySource.class);
        if (adapter instanceof IItemPropertySource itemPropertySource) {
            IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
            if (descriptor != null) {
                return descriptor.getDisplayName(eStructuralFeature);
            }
        }
        return eStructuralFeature.getName();
    }

}
