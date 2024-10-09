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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.components.view.emf.api.IDialogDescriptionConverter;
import org.springframework.stereotype.Service;

/**
 * An implementation of the {@link IDialogDescriptionConverter} to convert the {@link SelectionDialogDescription}.
 *
 * @author fbarbin
 */
@Service
public class SelectionDialogDescriptionConverter implements IDialogDescriptionConverter {

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    private static final String TREE_DESCRIPTION_ID = "treeDescriptionId";

    private static final String DIALOG_DESCRIPTION_TREE_REPRESENTATION_NAME = "Selection Dialog Tree Representation";

    private static final String SELECTION_PREFIX = "selection://";

    private final IObjectService objectService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IURLParser urlParser;

    public SelectionDialogDescriptionConverter(IObjectService objectService, IDiagramIdProvider diagramIdProvider, IURLParser urlParser) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public List<IRepresentationDescription> convert(DialogDescription dialogDescription, AQLInterpreter interpreter) {
        List<IRepresentationDescription> representationDescriptions = new ArrayList<>();
        if (dialogDescription instanceof SelectionDialogDescription selectionDialogDescription) {
            SelectionDescription selectionDescription = this.convertSelectionDialog(selectionDialogDescription, interpreter);
            representationDescriptions.add(selectionDescription);
            representationDescriptions.add(selectionDescription.getTreeDescription());
        }
        return representationDescriptions;
    }

    @Override
    public boolean canConvert(DialogDescription dialogDescription) {
        return dialogDescription instanceof SelectionDialogDescription;
    }

    private SelectionDescription convertSelectionDialog(org.eclipse.sirius.components.view.diagram.SelectionDialogDescription selectionDescription, AQLInterpreter interpreter) {
        String selectionDescriptionId = this.diagramIdProvider.getId(selectionDescription);
        TreeDescription treeDescription = this.createTreeDescription(selectionDescription, interpreter);
        return SelectionDescription.newSelectionDescription(selectionDescriptionId)
                .messageProvider(variableManager -> {
                    String message = selectionDescription.getSelectionMessage();
                    if (message == null) {
                        message = "";
                    }
                    return message;
                })
                .idProvider(variableManager -> SELECTION_PREFIX)
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null))
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .label("Selection Description")
                .canCreatePredicate(variableManager -> false)
                .treeDescription(treeDescription)
                .build();
    }

    /**
     * Create the TreeDescription attached to the SelectionDescription.
     *
     * @param selectionDescription
     *         the SelectionDialogDescription.
     * @param interpreter
     *         the AQL interpreter.
     * @return The {@link TreeDescription}
     */
    private TreeDescription createTreeDescription(SelectionDialogDescription selectionDescription, AQLInterpreter interpreter) {
        SelectionDialogTreeDescription selectionDialogTreeDescription = selectionDescription.getSelectionDialogTreeDescription();
        final String treeDescriptionId = this.diagramIdProvider.getId(selectionDialogTreeDescription);

        Function<VariableManager, List<?>> elementsProvider = this.getElementProvider(interpreter, selectionDialogTreeDescription);

        Function<VariableManager, IStatus> deleteHandler = variableManager -> new Failure("Unexecutable delete handler");

        BiFunction<VariableManager, String, IStatus> renameHandler = (variableManager, newValue) -> new Failure("Unexecutable rename handler");

        Function<VariableManager, String> treeItemIdProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.objectService::getId)
                    .orElse("");
        };

        Function<VariableManager, String> kindProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.objectService::getKind)
                    .orElse("");
        };

        Function<VariableManager, StyledString> labelProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.objectService::getFullLabel)
                    .map(StyledString::of)
                    .orElse(StyledString.of(""));
        };

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            return this.getTargetObjectId(variableManager).orElse("");
        };

        Function<VariableManager, List<String>> imageURLProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.objectService::getImagePath)
                    .orElse(List.of());
        };

        Function<VariableManager, Boolean> isSelectableProvider = variableManager -> {
            String isSelectableExpression = selectionDialogTreeDescription.getIsSelectableExpression();
            String safeExpression = Optional.ofNullable(isSelectableExpression).orElse("");
            if (safeExpression.isBlank()) {
                return true;
            }
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), safeExpression);
            return result.asBoolean()
                    .orElse(false);
        };


        Function<VariableManager, List<?>> childrenProvider = variableManager -> this.getChildren(variableManager, interpreter, selectionDialogTreeDescription);

        Function<VariableManager, Boolean> hasChildrenProvider = variableManager -> this.hasChildren(interpreter, selectionDialogTreeDescription, variableManager);

        Predicate<VariableManager> canCreatePredicate = variableManager -> {
            return variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class)
                    .filter(id -> id.startsWith(SELECTION_PREFIX))
                    .map(this.urlParser::getParameterValues)
                    .map(parameters -> parameters.get(TREE_DESCRIPTION_ID).get(0))
                    .filter(treeDescriptionIdParameter -> treeDescriptionId.equals(treeDescriptionIdParameter))
                    .isPresent();
        };
        String treeId = SELECTION_PREFIX + "?" + TREE_DESCRIPTION_ID + "=" + treeDescriptionId;
        return TreeDescription.newTreeDescription(treeDescriptionId)
                .label(DIALOG_DESCRIPTION_TREE_REPRESENTATION_NAME)
                .idProvider(variableManager -> variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class).orElse(treeId))
                .canCreatePredicate(canCreatePredicate)
                .childrenProvider(childrenProvider)
                .deletableProvider(variableManager -> false)
                .deleteHandler(deleteHandler)
                .renameHandler(renameHandler)
                .editableProvider(variableManager -> false)
                .elementsProvider(elementsProvider)
                .hasChildrenProvider(hasChildrenProvider)
                .treeItemIdProvider(treeItemIdProvider)
                .kindProvider(kindProvider)
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .iconURLProvider(imageURLProvider)
                .selectableProvider(isSelectableProvider)
                .treeItemObjectProvider(this::getTreeItemObject)
                .parentObjectProvider(this::getParentObject)
                .treeItemLabelProvider(labelProvider)
                .contextMenuEntries(List.of())
                .build();
    }

    private Boolean hasChildren(AQLInterpreter interpreter, SelectionDialogTreeDescription selectionDialogTreeDescription, VariableManager variableManager) {
        return !this.computeChildrenFromExpression(variableManager, interpreter, selectionDialogTreeDescription).isEmpty();
    }

    private Object getTreeItemObject(VariableManager variableManager) {
        Object result = null;
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalId = variableManager.get(TreeDescription.ID, String.class);
        if (optionalId.isPresent() && optionalEditingContext.isPresent()) {
            var optionalObject = this.objectService.getObject(optionalEditingContext.get(), optionalId.get());
            if (optionalObject.isPresent()) {
                result = optionalObject.get();
            } else {
                var optionalEditingDomain = Optional.of(optionalEditingContext.get())
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .map(IEMFEditingContext::getDomain);

                if (optionalEditingDomain.isPresent()) {
                    var editingDomain = optionalEditingDomain.get();
                    ResourceSet resourceSet = editingDomain.getResourceSet();
                    URI uri = new JSONResourceFactory().createResourceURI(optionalId.get());

                    result = resourceSet.getResources().stream()
                            .filter(resource -> resource.getURI().equals(uri)).
                            findFirst()
                            .orElse(null);
                }
            }
        }

        return result;
    }


    private Object getParentObject(VariableManager variableManager) {
        Object result = null;
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        }
        return result;
    }

    private List<?> getChildren(VariableManager variableManager, AQLInterpreter interpreter, SelectionDialogTreeDescription selectionDialogTreeDescription) {
        List<Object> result = new ArrayList<>();

        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }

        String id = this.getTreeItemId(variableManager);
        if (expandedIds.contains(id)) {
            result.addAll(this.computeChildrenFromExpression(variableManager, interpreter, selectionDialogTreeDescription));
        }
        return result;
    }

    private List<Object> computeChildrenFromExpression(VariableManager variableManager, AQLInterpreter interpreter, SelectionDialogTreeDescription selectionDialogTreeDescription) {
        List<Object> result = new ArrayList<>();
        String childrenExpression = selectionDialogTreeDescription.getChildrenExpression();
        String safeExpression = Optional.ofNullable(childrenExpression).orElse("");
        if (!safeExpression.isBlank()) {
            Result interpreterResult = interpreter.evaluateExpression(variableManager.getVariables(), safeExpression);
            result = interpreterResult.asObjects()
                    .orElse(List.of())
                    .stream()
                    .filter(Objects::nonNull)
                    .toList();
        }
        return result;
    }

    private Function<VariableManager, List<?>> getElementProvider(AQLInterpreter interpreter, SelectionDialogTreeDescription selectionDialogTreeDescription) {
        return variableManager -> {
            Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
            if (optionalEditingContext.isPresent()) {
                //Set the targetObject as the SELF value.
                //The targetObjectId is provided by the frontend in the treeId.
                this.getTargetObjectId(variableManager)
                        .flatMap(targetObjectId -> this.objectService.getObject(optionalEditingContext.get(), targetObjectId))
                        .ifPresent(targetObject -> variableManager.put(VariableManager.SELF, targetObject));

                String elementsExpression = selectionDialogTreeDescription.getElementsExpression();
                Result result = interpreter.evaluateExpression(variableManager.getVariables(), elementsExpression);
                return result.asObjects()
                        .orElse(List.of())
                        .stream()
                        .filter(Objects::nonNull)
                        .toList();
            }
            return List.of();
        };
    }

    private Optional<String> getTargetObjectId(VariableManager variableManager) {
        return variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class)
                .map(this.urlParser::getParameterValues)
                .map(parameters -> parameters.get(TARGET_OBJECT_ID).get(0));
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String id = null;
        if (self != null) {
            id = this.objectService.getId(self);
        }
        return id;
    }

}
