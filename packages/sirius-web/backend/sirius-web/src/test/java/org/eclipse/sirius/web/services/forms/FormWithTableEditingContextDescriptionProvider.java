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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TableWidgetDescription;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.SelectCellComponent;
import org.eclipse.sirius.components.tables.descriptions.CheckboxCellDescription;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.MultiSelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.SelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.web.papaya.representations.table.CellTypePredicate;
import org.eclipse.sirius.web.papaya.representations.table.ColumnTargetObjectIdProvider;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description for the project management concepts.
 *
 * @author lfasani
 */
@Service
public class FormWithTableEditingContextDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String TASK_FORM_ID = "taskFormDescription";
    public static final String FORM_WITH_TABLE_ID = "tasksTableId";

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IIdentityService identityService;

    private final IObjectService objectService;

    public FormWithTableEditingContextDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IIdentityService identityService, IObjectService objectService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectService = Objects.requireNonNull(objectService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        TableWidgetDescription tableWidgetDescription = this.getTableWidgetDescription();

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

    private TableWidgetDescription getTableWidgetDescription() {
        Function<VariableManager, PaginatedData> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Iteration.class)
                .map(eObject -> {
                    List<Object> objects = new ArrayList<>();
                    objects.addAll(eObject.getTasks());
                    return new PaginatedData(objects, false, false, objects.size());
                })
                .orElse(new PaginatedData(List.of(), false, false, 0));

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getLabel)
                .orElse(null);

        LineDescription lineDescription = LineDescription.newLineDescription(UUID.nameUUIDFromBytes("Table - Line".getBytes()))
                .targetObjectIdProvider(this::getTargetObjectId)
                .targetObjectKindProvider(this::getTargetObjectKind)
                .semanticElementsProvider(semanticElementsProvider)
                .headerLabelProvider(variableManager -> "")
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .build();

        TableDescription tableDescription = TableDescription.newTableDescription(FORM_WITH_TABLE_ID)
                .label("tasksTableLabel")
                .targetObjectIdProvider(this::getTargetObjectId)
                .targetObjectKindProvider(this::getTargetObjectKind)
                .labelProvider(labelProvider)
                .lineDescription(lineDescription)
                .columnDescriptions(this.getColumnDescriptions())
                .cellDescriptions(this.getCellDescriptions())
                .iconURLsProvider(variableManager -> List.of())
                .isStripeRowPredicate(variableManager -> false)
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
                .build());
        cellDescriptions.add(CheckboxCellDescription.newCheckboxCellDescription("checkboxCells")
                .canCreatePredicate(new CellTypePredicate().isCheckboxCell())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .cellValueProvider(this.getCellBooleanValueProvider())
                .build());
        cellDescriptions.add(SelectCellDescription.newSelectCellDescription("selectCells")
                .canCreatePredicate(new CellTypePredicate().isSelectCell())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .cellValueProvider(this.getCellStringValueProvider())
                .cellOptionsIdProvider(this.getCellOptionsIdProvider())
                .cellOptionsLabelProvider(this.getCellOptionsLabelProvider())
                .cellOptionsProvider(this.getCellOptionsProvider())
                .build());
        cellDescriptions.add(MultiSelectCellDescription.newMultiSelectCellDescription("multiselectCells")
                .canCreatePredicate(new CellTypePredicate().isMultiselectCell())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .cellValueProvider(this.getCellStringListValueProvider())
                .cellOptionsIdProvider(this.getCellOptionsIdProvider())
                .cellOptionsLabelProvider(this.getCellOptionsLabelProvider())
                .cellOptionsProvider(this.getCellOptionsProvider())
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

    private List<ColumnDescription> getColumnDescriptions() {
        Map<EStructuralFeature, String> featureToDisplayName = this.getColumnsStructuralFeaturesDisplayName(PapayaFactory.eINSTANCE.createTask(), PapayaPackage.eINSTANCE.getTask());

        Function<VariableManager, String> headerLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, EStructuralFeature.class)
                .map(featureToDisplayName::get)
                .orElse("");

        ColumnDescription columnDescription = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("features".getBytes()))
                .semanticElementsProvider(variableManager -> featureToDisplayName.keySet().stream().map(Object.class::cast).toList())
                .targetObjectIdProvider(new ColumnTargetObjectIdProvider())
                .targetObjectKindProvider(variableManager -> "")
                .headerLabelProvider(headerLabelProvider)
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .initialWidthProvider(variableManager -> -1)
                .isResizablePredicate(variableManager -> false)
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
                        value = this.objectService.getId(objectValue);
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
                        value = ((EList<?>) objectValue).stream().map(this.objectService::getId).collect(Collectors.toList());
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
                return this.objectService.getLabel(candidate);
            }
            return this.objectService.getId(candidate);
        };
    }

    private Function<VariableManager, String> getCellOptionsLabelProvider() {
        return variableManager -> {
            Object candidate = variableManager.getVariables().get(SelectCellComponent.CANDIDATE_VARIABLE);
            if (candidate instanceof EEnumLiteral) {
                return this.objectService.getLabel(candidate);
            }
            return this.objectService.getFullLabel(candidate);
        };
    }

    private BiFunction<VariableManager, Object, List<Object>> getCellOptionsProvider() {
        return (variableManager, columnTargetObject) -> {
            List<Object> options = new ArrayList<>();
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EObject eObject = optionalEObject.get();
                EClassifier eType = eStructuralFeature.getEType();
                if (eType instanceof EEnum) {
                    options.addAll(((EEnum) eType).getELiterals());
                } else {
                    Object adapter = this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class);
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

    private Map<EStructuralFeature, String> getColumnsStructuralFeaturesDisplayName(EObject eObject, EClass eClass) {
        Map<EStructuralFeature, String> featureToDisplayName = new LinkedHashMap<>();
        EList<EStructuralFeature> eAllStructuralFeatures = eClass.getEAllStructuralFeatures();
        for (EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {
            if (eStructuralFeature instanceof EAttribute && !eStructuralFeature.isMany() && !eStructuralFeature.isDerived()) {
                featureToDisplayName.put(eStructuralFeature, this.getDisplayName(eObject, eStructuralFeature));
            } else if (eStructuralFeature instanceof EReference ref && !eStructuralFeature.isDerived() && !ref.isContainment()) {
                featureToDisplayName.put(eStructuralFeature, this.getDisplayName(eObject, eStructuralFeature));
            }
        }
        return featureToDisplayName;
    }

    private String getDisplayName(EObject eObject, EStructuralFeature eStructuralFeature) {
        Adapter adapter = this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class);
        if (adapter instanceof IItemPropertySource itemPropertySource) {
            IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
            if (descriptor != null) {
                return descriptor.getDisplayName(eStructuralFeature);
            }
        }
        return eStructuralFeature.getName();
    }

}
