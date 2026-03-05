/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
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
import org.eclipse.sirius.components.selection.description.SelectionDescriptionDialog;
import org.eclipse.sirius.components.selection.description.SelectionDescriptionDialogAction;
import org.eclipse.sirius.components.selection.description.SelectionDescriptionDialogConfirmButtonLabels;
import org.eclipse.sirius.components.selection.description.SelectionDescriptionDialogStatusMessages;
import org.eclipse.sirius.components.selection.description.SelectionDescriptionDialogTitles;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.components.view.emf.api.IDialogDescriptionConverter;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Service;

/**
 * An implementation of the {@link IDialogDescriptionConverter} to convert the {@link SelectionDialogDescription}.
 *
 * @author fbarbin
 */
@Service
public class SelectionDialogDescriptionConverter implements IDialogDescriptionConverter {

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    private static final String SOURCE_DIAGRAM_ELEMENT_TARGET_OBJECT = "sourceDiagramElementTargetObject";

    private static final String SOURCE_DIAGRAM_ELEMENT_TARGET_OBJECT_ID = SOURCE_DIAGRAM_ELEMENT_TARGET_OBJECT + "Id";

    private static final String TARGET_DIAGRAM_ELEMENT_TARGET_OBJECT = "targetDiagramElementTargetObject";

    private static final String TARGET_DIAGRAM_ELEMENT_TARGET_OBJECT_ID = TARGET_DIAGRAM_ELEMENT_TARGET_OBJECT + "Id";

    private static final String TREE_DESCRIPTION_ID = "treeDescriptionId";

    private static final String DIALOG_DESCRIPTION_TREE_REPRESENTATION_NAME = "Selection Dialog Tree Representation";

    private static final String SELECTION_PREFIX = "selection://";

    private static final String TREE_SELECTION = "treeSelection";

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    private final ILabelService labelService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IURLParser urlParser;

    private final IViewEMFMessageService messageService;

    public SelectionDialogDescriptionConverter(IIdentityService identityService, IObjectSearchService objectSearchService, ILabelService labelService, IDiagramIdProvider diagramIdProvider, IURLParser urlParser,
            IViewEMFMessageService messageService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.labelService = Objects.requireNonNull(labelService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public List<IRepresentationDescription> convert(DialogDescription dialogDescription, AQLInterpreter interpreter) {
        List<IRepresentationDescription> representationDescriptions = new ArrayList<>();
        if (dialogDescription instanceof SelectionDialogDescription selectionDialogDescription) {
            if (this.isValid(selectionDialogDescription)) {
                SelectionDescription selectionDescription = this.convertSelectionDialog(selectionDialogDescription, interpreter);
                representationDescriptions.add(selectionDescription);
                representationDescriptions.add(selectionDescription.getTreeDescription());
            }
        }
        return representationDescriptions;
    }

    private boolean isValid(SelectionDialogDescription selectionDialogDescription) {
        return selectionDialogDescription.getSelectionDialogTreeDescription() != null;
    }

    @Override
    public boolean canConvert(DialogDescription dialogDescription) {
        return dialogDescription instanceof SelectionDialogDescription;
    }

    private SelectionDescription convertSelectionDialog(org.eclipse.sirius.components.view.diagram.SelectionDialogDescription selectionDescription, AQLInterpreter interpreter) {
        String selectionDescriptionId = this.diagramIdProvider.getId(selectionDescription);
        TreeDescription treeDescription = this.createTreeDescription(selectionDescription, interpreter);
        return SelectionDescription.newSelectionDescription(selectionDescriptionId)
                .dialogProvider(variableManager -> new SelectionDescriptionDialog(
                        this.getTitles(selectionDescription, variableManager, interpreter),
                        this.getDescription(selectionDescription, variableManager, interpreter),
                        this.getNoSelectionAction(selectionDescription, variableManager, interpreter),
                        this.getWithSelectionAction(selectionDescription, variableManager, interpreter),
                        this.getStatusMessages(selectionDescription, variableManager, interpreter),
                        this.getConfirmButtonLabels(selectionDescription, variableManager, interpreter)
                ))
                .dialogSelectionRequiredWithSelectionStatusMessageProvider(variableManager -> this.getSelectionRequiredWithSelectionStatusMessage(selectionDescription, variableManager, interpreter))
                .idProvider(variableManager -> SELECTION_PREFIX)
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                        .map(this.labelService::getStyledLabel)
                        .map(StyledString::toString)
                        .orElse(null))
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                        .map(this.identityService::getId)
                        .orElse(null))
                .label("Selection Description")
                .canCreatePredicate(variableManager -> false)
                .treeDescription(treeDescription)
                .multiple(selectionDescription.isMultiple())
                .optional(selectionDescription.isOptional())
                .iconURLsProvider(variableManager -> List.of())
                .build();
    }

    private SelectionDescriptionDialogTitles getTitles(org.eclipse.sirius.components.view.diagram.SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String defaultTitle = this.messageService.defaultSelectionDialogTitle();
        var defaultTitleExpression = selectionDescription.getDefaultTitleExpression();
        var safeDefaultTitleExpression = Optional.ofNullable(defaultTitleExpression).orElse("");
        if (!safeDefaultTitleExpression.isBlank()) {
            defaultTitle = interpreter.evaluateExpression(variableManager.getVariables(), safeDefaultTitleExpression).asString().orElse(defaultTitle);
        }

        String noSelectionTitle = this.messageService.defaultSelectionDialogTitle();
        var noSelectionTitleExpression = selectionDescription.getNoSelectionTitleExpression();
        var safeNoSelectionTitleExpression = Optional.ofNullable(noSelectionTitleExpression).orElse("");
        if (!safeNoSelectionTitleExpression.isBlank()) {
            noSelectionTitle = interpreter.evaluateExpression(variableManager.getVariables(), safeNoSelectionTitleExpression).asString().orElse(noSelectionTitle);
        }

        String withSelectionTitle = this.messageService.defaultSelectionDialogTitle();
        var withSelectionTitleExpression = selectionDescription.getWithSelectionTitleExpression();
        var safeWithSelectionTitleExpression = Optional.ofNullable(withSelectionTitleExpression).orElse("");
        if (!safeNoSelectionTitleExpression.isBlank()) {
            withSelectionTitle = interpreter.evaluateExpression(variableManager.getVariables(), safeWithSelectionTitleExpression).asString().orElse(withSelectionTitle);
        }

        return new SelectionDescriptionDialogTitles(defaultTitle, noSelectionTitle, withSelectionTitle);
    }

    private String getDescription(org.eclipse.sirius.components.view.diagram.SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String dialogDescription = "";
        if (selectionDescription.isOptional()) {
            dialogDescription = this.messageService.defaultSelectionDialogWithOptionalSelectionDescription();
        } else {
            dialogDescription = this.messageService.defaultSelectionDialogWithMandatorySelectionDescription();
        }

        var descriptionExpression = selectionDescription.getDescriptionExpression();
        var safeDescriptionExpression = Optional.ofNullable(descriptionExpression).orElse("");
        if (!safeDescriptionExpression.isBlank()) {
            dialogDescription = interpreter.evaluateExpression(variableManager.getVariables(), safeDescriptionExpression).asString().orElse(dialogDescription);
        }
        return dialogDescription;
    }

    private SelectionDescriptionDialogAction getNoSelectionAction(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String noSelectionActionLabel = this.messageService.defaultSelectionDialogNoSelectionActionLabel();
        String noSelectionActionLabelExpression = selectionDescription.getNoSelectionActionLabelExpression();
        String safeNoSelectionActionLabelExpression = Optional.ofNullable(noSelectionActionLabelExpression).orElse("");
        if (!safeNoSelectionActionLabelExpression.isBlank()) {
            noSelectionActionLabel = interpreter.evaluateExpression(variableManager.getVariables(), safeNoSelectionActionLabelExpression).asString().orElse(noSelectionActionLabel);
        }

        String noSelectionActionDescription = this.messageService.defaultSelectionDialogNoSelectionActionDescription();
        String noSelectionActionDescriptionExpression = selectionDescription.getNoSelectionActionDescriptionExpression();
        String safeNoSelectionActionDescriptionExpression = Optional.ofNullable(noSelectionActionDescriptionExpression).orElse("");
        if (!safeNoSelectionActionLabelExpression.isBlank()) {
            noSelectionActionDescription = interpreter.evaluateExpression(variableManager.getVariables(), safeNoSelectionActionDescriptionExpression).asString().orElse(noSelectionActionLabel);
        }

        return new SelectionDescriptionDialogAction(noSelectionActionLabel, noSelectionActionDescription);
    }

    private SelectionDescriptionDialogAction getWithSelectionAction(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String withSelectionActionLabel = this.messageService.defaultSelectionDialogWithSelectionActionLabel();
        String withSelectionActionLabelExpression = selectionDescription.getWithSelectionActionLabelExpression();
        String safeWithSelectionActionLabelExpression = Optional.ofNullable(withSelectionActionLabelExpression).orElse("");
        if (!safeWithSelectionActionLabelExpression.isBlank()) {
            withSelectionActionLabel = interpreter.evaluateExpression(variableManager.getVariables(), safeWithSelectionActionLabelExpression).asString().orElse(withSelectionActionLabel);
        }

        String withSelectionActionDescription = this.messageService.defaultSelectionDialogWithSelectionActionDescription();
        String withSelectionActionDescriptionExpression = selectionDescription.getWithSelectionActionDescriptionExpression();
        String safeWithSelectionActionDescriptionExpression = Optional.ofNullable(withSelectionActionDescriptionExpression).orElse("");
        if (!safeWithSelectionActionLabelExpression.isBlank()) {
            withSelectionActionDescription = interpreter.evaluateExpression(variableManager.getVariables(), safeWithSelectionActionDescriptionExpression).asString().orElse(withSelectionActionLabel);
        }

        return new SelectionDescriptionDialogAction(withSelectionActionLabel, withSelectionActionDescription);
    }

    private SelectionDescriptionDialogStatusMessages getStatusMessages(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String noSelectionActionStatusMessage = this.messageService.defaultSelectionDialogNoSelectionActionStatusMessage();
        String noSelectionActionStatusMessageExpression = selectionDescription.getNoSelectionActionStatusMessageExpression();
        String safeNoSelectionActionStatusMessageExpression = Optional.ofNullable(noSelectionActionStatusMessageExpression).orElse("");
        if (!safeNoSelectionActionStatusMessageExpression.isBlank()) {
            noSelectionActionStatusMessage = interpreter.evaluateExpression(variableManager.getVariables(), safeNoSelectionActionStatusMessageExpression).asString().orElse(noSelectionActionStatusMessage);
        }

        String selectionRequiredWithoutSelectionStatusMessage = this.messageService.defaultSelectionDialogSelectionRequiredWithoutSelectionStatusMessage();
        String withSelectionActionStatusMessageExpression = selectionDescription.getSelectionRequiredWithoutSelectionStatusMessageExpression();
        String safeWithSelectionActionStatusMessageExpression = Optional.ofNullable(withSelectionActionStatusMessageExpression).orElse("");
        if (!safeWithSelectionActionStatusMessageExpression.isBlank()) {
            selectionRequiredWithoutSelectionStatusMessage = interpreter.evaluateExpression(variableManager.getVariables(), safeWithSelectionActionStatusMessageExpression).asString().orElse(noSelectionActionStatusMessage);
        }
        return new SelectionDescriptionDialogStatusMessages(noSelectionActionStatusMessage, selectionRequiredWithoutSelectionStatusMessage);
    }

    private SelectionDescriptionDialogConfirmButtonLabels getConfirmButtonLabels(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        new SelectionDescriptionDialogConfirmButtonLabels(this.messageService.defaultSelectionDialogConfirmButtonLabel(), this.messageService.defaultSelectionDialogSelectionRequiredWithoutSelectionConfirmButtonLabel(), this.messageService.defaultSelectionDialogConfirmButtonLabel());

        String noSelectionConfirmButtonLabel = this.messageService.defaultSelectionDialogConfirmButtonLabel();
        var noSelectionConfirmButtonLabelExpression = selectionDescription.getNoSelectionConfirmButtonLabelExpression();
        var safeNoSelectionConfirmButtonLabelExpression = Optional.ofNullable(noSelectionConfirmButtonLabelExpression).orElse("");
        if (!safeNoSelectionConfirmButtonLabelExpression.isBlank()) {
            noSelectionConfirmButtonLabel = interpreter.evaluateExpression(variableManager.getVariables(), safeNoSelectionConfirmButtonLabelExpression).asString().orElse(noSelectionConfirmButtonLabel);
        }

        String selectionRequiredWithoutSelectionConfirmButtonLabel = this.messageService.defaultSelectionDialogSelectionRequiredWithoutSelectionConfirmButtonLabel();
        var selectionRequiredWithoutSelectionConfirmButtonLabelExpression = selectionDescription.getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression();
        var safeSelectionRequiredWithoutSelectionConfirmButtonLabelExpression = Optional.ofNullable(selectionRequiredWithoutSelectionConfirmButtonLabelExpression).orElse("");
        if (!safeSelectionRequiredWithoutSelectionConfirmButtonLabelExpression.isBlank()) {
            selectionRequiredWithoutSelectionConfirmButtonLabel = interpreter.evaluateExpression(variableManager.getVariables(), safeSelectionRequiredWithoutSelectionConfirmButtonLabelExpression).asString().orElse(selectionRequiredWithoutSelectionConfirmButtonLabel);
        }

        String selectionRequiredWithSelectionConfirmButtonLabel = this.messageService.defaultSelectionDialogConfirmButtonLabel();
        var selectionRequiredWithSelectionConfirmButtonLabelExpression = selectionDescription.getSelectionRequiredWithSelectionConfirmButtonLabelExpression();
        var safeSelectionRequiredWithSelectionConfirmButtonLabel = Optional.ofNullable(selectionRequiredWithSelectionConfirmButtonLabelExpression).orElse("");
        if (!safeSelectionRequiredWithoutSelectionConfirmButtonLabelExpression.isBlank()) {
            selectionRequiredWithSelectionConfirmButtonLabel = interpreter.evaluateExpression(variableManager.getVariables(), safeSelectionRequiredWithSelectionConfirmButtonLabel).asString().orElse(selectionRequiredWithSelectionConfirmButtonLabel);
        }

        return new SelectionDescriptionDialogConfirmButtonLabels(noSelectionConfirmButtonLabel, selectionRequiredWithoutSelectionConfirmButtonLabel, selectionRequiredWithSelectionConfirmButtonLabel);
    }

    private String getSelectionRequiredWithSelectionStatusMessage(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String statusMessage = "";
        var selectionRequiredWithSelectionStatusMessageExpression = selectionDescription.getSelectionRequiredWithSelectionStatusMessageExpression();
        var safeSelectionRequiredWithSelectionStatusMessageExpression = Optional.ofNullable(selectionRequiredWithSelectionStatusMessageExpression).orElse("");
        if (safeSelectionRequiredWithSelectionStatusMessageExpression.isBlank()) {
            statusMessage = this.getDefaultSelectionRequiredWithSelectionStatusMessage(variableManager);
        } else {
            interpreter.evaluateExpression(variableManager.getVariables(), safeSelectionRequiredWithSelectionStatusMessageExpression).asString().orElseGet(() -> this.getDefaultSelectionRequiredWithSelectionStatusMessage(variableManager));
        }
        return statusMessage;
    }

    private String getDefaultSelectionRequiredWithSelectionStatusMessage(VariableManager variableManager) {
        String statusMessage = "";
        Object objects = variableManager.getVariables().get(TREE_SELECTION);
        if (objects instanceof List<?> list) {
            var selectionCount = list.stream().filter(String.class::isInstance).count();
            if (selectionCount == 1) {
                var elementLabel = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                        .flatMap(editingContext -> this.objectSearchService.getObject(editingContext, list.get(0).toString()))
                        .map(this.labelService::getStyledLabel)
                        .map(StyledString::toString)
                        .orElse("");
                statusMessage = this.messageService.defaultSelectionDialogSelectionRequiredWithOneSelectedElementStatusMessage(elementLabel);
            } else if (selectionCount > 1) {
                statusMessage = this.messageService.defaultSelectionDialogSelectionRequiredWithManySelectedElementsStatusMessage(selectionCount);
            }
        }
        return statusMessage;
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
                    .map(this.identityService::getId)
                    .orElse("");
        };

        Function<VariableManager, String> kindProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.identityService::getKind)
                    .orElse("");
        };

        Function<VariableManager, StyledString> labelProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.labelService::getStyledLabel)
                    .orElse(StyledString.of(""));
        };

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            return this.getTargetObjectId(variableManager).orElse("");
        };

        Function<VariableManager, List<String>> imageURLProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.labelService::getImagePaths)
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
                .treeItemIconURLsProvider(imageURLProvider)
                .selectableProvider(isSelectableProvider)
                .treeItemObjectProvider(this::getTreeItemObject)
                .parentObjectProvider(this::getParentObject)
                .treeItemLabelProvider(labelProvider)
                .iconURLsProvider(variableManager -> List.of())
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
            var optionalObject = this.objectSearchService.getObject(optionalEditingContext.get(), optionalId.get());
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
                this.convertTreeIdParametersToVariables(variableManager, optionalEditingContext.get());

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
            id = this.identityService.getId(self);
        }
        return id;
    }

    private void convertTreeIdParametersToVariables(VariableManager variableManager, IEditingContext editingContext) {
        Map<String, List<String>> parameters = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class)
                .map(this.urlParser::getParameterValues)
                .orElse(Map.of());
        this.safeAddVariable(parameters, variableManager, editingContext, TARGET_OBJECT_ID, VariableManager.SELF);
        this.safeAddVariable(parameters, variableManager, editingContext, SOURCE_DIAGRAM_ELEMENT_TARGET_OBJECT_ID, SOURCE_DIAGRAM_ELEMENT_TARGET_OBJECT);
        this.safeAddVariable(parameters, variableManager, editingContext, TARGET_DIAGRAM_ELEMENT_TARGET_OBJECT_ID, TARGET_DIAGRAM_ELEMENT_TARGET_OBJECT);
    }

    private void safeAddVariable(Map<String, List<String>> parameters, VariableManager variableManager, IEditingContext editingContext, String parameterName, String variableName) {
        Optional.ofNullable(parameters.get(parameterName))
            .filter(list -> !list.isEmpty())
            .map(list -> list.get(0))
            .flatMap(objectId -> this.objectSearchService.getObject(editingContext, objectId))
            .ifPresent(value -> {
                variableManager.put(variableName, value);
            });
    }
}
