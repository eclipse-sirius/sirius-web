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
package org.eclipse.sirius.components.view.emf.tree;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.trees.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.components.collaborative.trees.api.IRenameTreeItemHandler;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.trees.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based tree description into an equivalent {@link TreeDescription}.
 *
 * @author Jerome Gout
 */
@Service
public class ViewTreeDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String DEFAULT_TREE_DESCRIPTION_LABEL = "Tree Description";

    private final ITreeIdProvider treeIdProvider;

    private final List<IRenameTreeItemHandler> renameTreeItemHandlers;

    private final List<IDeleteTreeItemHandler> deleteTreeItemHandlers;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    public ViewTreeDescriptionConverter(ITreeIdProvider treeIdProvider, List<IDeleteTreeItemHandler> deleteTreeItemHandlers, List<IRenameTreeItemHandler> renameTreeItemHandlers, IEditService editService, IFeedbackMessageService feedbackMessageService) {
        this.treeIdProvider = Objects.requireNonNull(treeIdProvider);
        this.renameTreeItemHandlers = Objects.requireNonNull(renameTreeItemHandlers);
        this.deleteTreeItemHandlers = Objects.requireNonNull(deleteTreeItemHandlers);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.tree.TreeDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription representationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        var viewTreeDescription = (org.eclipse.sirius.components.view.tree.TreeDescription) representationDescription;
        var descriptionId = this.treeIdProvider.getId(viewTreeDescription);
        var label = Optional.ofNullable(viewTreeDescription.getName()).orElse(DEFAULT_TREE_DESCRIPTION_LABEL);

        var builder = TreeDescription.newTreeDescription(descriptionId)
                .label(label)
                .idProvider(variableManager -> this.getTreeId(variableManager, descriptionId))
                .canCreatePredicate(variableManager -> this.canCreate(viewTreeDescription.getDomainType(), viewTreeDescription.getPreconditionExpression(), variableManager, interpreter))
                .labelProvider(variableManager -> StyledString.of(this.evaluateString(interpreter, variableManager, viewTreeDescription.getTitleExpression())))
                .kindProvider(variableManager -> this.evaluateString(interpreter, variableManager, viewTreeDescription.getKindExpression()))
                .targetObjectIdProvider(variableManager -> variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId).orElse(null))
                .treeItemIdProvider(variableManager -> this.evaluateString(interpreter, variableManager, viewTreeDescription.getTreeItemIdExpression()))
                .treeItemObjectProvider(variableManager -> this.evaluateObject(interpreter, variableManager, viewTreeDescription.getTreeItemObjectExpression()))
                .treeItemLabelProvider(variableManager -> this.getTreeItemLabel(interpreter, variableManager, viewTreeDescription))
                .iconURLProvider(variableManager -> this.evaluateStringList(interpreter, variableManager, viewTreeDescription.getIconURLExpression()))
                .editableProvider(variableManager -> this.evaluateBoolean(interpreter, variableManager, viewTreeDescription.getEditableExpression()))
                .deletableProvider(variableManager -> this.evaluateBoolean(interpreter, variableManager, viewTreeDescription.getDeletableExpression()))
                .selectableProvider(variableManager -> this.evaluateBoolean(interpreter, variableManager, viewTreeDescription.getSelectableExpression()))
                .elementsProvider(variableManager -> this.evaluateObjectList(interpreter, variableManager, viewTreeDescription.getElementsExpression()))
                .hasChildrenProvider(variableManager -> this.evaluateBoolean(interpreter, variableManager, viewTreeDescription.getHasChildrenExpression()))
                .childrenProvider(variableManager -> this.evaluateObjectList(interpreter, variableManager, viewTreeDescription.getChildrenExpression()))
                .parentObjectProvider(variableManager -> this.evaluateObject(interpreter, variableManager, viewTreeDescription.getParentExpression()))
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .contextMenuEntries(this.getContextMenuActions(interpreter, viewTreeDescription))
                ;

        return builder.build();
    }

    private String getTreeId(VariableManager variableManager, String descriptionId) {
        List<?> expandedObjects = variableManager.get(TreeRenderer.EXPANDED, List.class).orElse(List.of());
        List<?> activatedFilters = variableManager.get(TreeRenderer.ACTIVE_FILTER_IDS, List.class).orElse(List.of());
        return this.getExplorerTreeId(descriptionId, expandedObjects, activatedFilters);
    }

    private String getExplorerTreeId(String descriptionId, List<?> expandedObjects, List<?> activatedFilters) {
        List<String> expandedObjectIds = expandedObjects.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        List<String> activatedFilterIds = activatedFilters.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return "explorer://?treeDescriptionId=" + URLEncoder.encode(descriptionId, StandardCharsets.UTF_8) + "&expandedIds=[" + String.join(",", expandedObjectIds) + "]&activeFilterIds=[" + String.join(",", activatedFilterIds) + "]";
    }

    private boolean canCreate(String domainType, String preconditionExpression, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        Optional<EClass> optionalEClass = variableManager.get(VariableManager.SELF, EObject.class)
                .map(EObject::eClass)
                .filter(new DomainClassPredicate(domainType));
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
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asString()
                .orElse("");
    }

    private List<String> evaluateStringList(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        List<String> values = new ArrayList<>();
        if (expression != null && !expression.isBlank()) {
            Optional<List<Object>> optionalResult = interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects();
            if (optionalResult.isPresent()) {
                values = optionalResult.get().stream().filter(String.class::isInstance).map(String.class::cast).toList();
            }
        }
        return values;
    }

    private Boolean evaluateBoolean(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asBoolean()
                .orElse(true);
    }

    private Object evaluateObject(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asObject()
                .orElse(null);
    }

    private List<Object> evaluateObjectList(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asObjects()
                .orElse(List.of());
    }

    private IStatus executeOperations(AQLInterpreter interpreter, VariableManager variableManager, List<Operation> operations) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
        if (optionalVariableManager.isEmpty()) {
            return this.buildFailureWithFeedbackMessages("Something went wrong while handling the context menu action");
        } else {
            return this.buildSuccessWithSemanticChangeAndFeedbackMessages();
        }
    }

    private IStatus getDeleteHandler(VariableManager variableManager) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalTreeItem = variableManager.get(TreeItem.SELECTED_TREE_ITEM, TreeItem.class);
        var optionalTree = variableManager.get(TreeDescription.TREE, Tree.class);

        if (optionalEditingContext.isPresent() && optionalTreeItem.isPresent() && optionalTree.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            TreeItem treeItem = optionalTreeItem.get();

            var optionalHandler = this.deleteTreeItemHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, treeItem))
                    .findFirst();

            if (optionalHandler.isPresent()) {
                IDeleteTreeItemHandler deleteTreeItemHandler = optionalHandler.get();
                return deleteTreeItemHandler.handle(editingContext, treeItem, optionalTree.get());
            }
        }
        return new Failure("");
    }

    private IStatus getRenameHandler(VariableManager variableManager, String newLabel) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalTreeItem = variableManager.get(TreeItem.SELECTED_TREE_ITEM, TreeItem.class);
        var optionalTree = variableManager.get(TreeDescription.TREE, Tree.class);

        if (optionalEditingContext.isPresent() && optionalTreeItem.isPresent() && optionalTree.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            TreeItem treeItem = optionalTreeItem.get();

            var optionalHandler = this.renameTreeItemHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, treeItem, newLabel))
                    .findFirst();

            if (optionalHandler.isPresent()) {
                IRenameTreeItemHandler renameTreeItemHandler = optionalHandler.get();
                return renameTreeItemHandler.handle(editingContext, treeItem, newLabel, optionalTree.get());
            }
        }
        return new Failure("");
    }

    private StyledString getTreeItemLabel(AQLInterpreter interpreter, VariableManager variableManager, org.eclipse.sirius.components.view.tree.TreeDescription viewTreeDescription) {
        TreeItemStyleConverter styleConverter = new TreeItemStyleConverter(interpreter, variableManager.getVariables());
        StyledString result = StyledString.of("");
        var optionalTreeItemLabelDescription = viewTreeDescription.getTreeItemLabelDescriptions().stream()
                .filter(description -> this.evaluateBoolean(interpreter, variableManager, description.getPreconditionExpression()))
                .findFirst();
        if (optionalTreeItemLabelDescription.isPresent()) {
            result = styleConverter.convert(optionalTreeItemLabelDescription.get());
        }
        return result;
    }

    private java.util.List<ITreeItemContextMenuEntry> getContextMenuActions(AQLInterpreter interpreter, org.eclipse.sirius.components.view.tree.TreeDescription viewTreeDescription) {
        return viewTreeDescription.getContextMenuEntries().stream()
                .map(action -> this.convertContextMenuEntry(interpreter, action))
                .toList();
    }

    private ITreeItemContextMenuEntry convertContextMenuEntry(AQLInterpreter interpreter, TreeItemContextMenuEntry viewTreeItemContextAction) {
        ITreeItemContextMenuEntry result = null;
        if (viewTreeItemContextAction instanceof SingleClickTreeItemContextMenuEntry viewSimpleAction) {
            result = org.eclipse.sirius.components.trees.SingleClickTreeItemContextMenuEntry.newSingleClickTreeItemContextMenuEntry(UUID.randomUUID().toString())
                    .label(variableMananger -> this.evaluateString(interpreter, variableMananger, viewSimpleAction.getLabelExpression()))
                    .iconURL(variableMananger -> this.evaluateStringList(interpreter, variableMananger, viewSimpleAction.getIconURLExpression()))
                    .precondition(variableMananger -> this.evaluateBoolean(interpreter, variableMananger, viewSimpleAction.getPreconditionExpression()))
                    .handler(variableMananger -> this.executeOperations(interpreter, variableMananger, viewSimpleAction.getBody()))
                    .build();
        } else if (viewTreeItemContextAction instanceof FetchTreeItemContextMenuEntry viewFetchAction) {
            result = org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntry.newFetchTreeItemContextMenuEntry(UUID.randomUUID().toString())
                    .label(variableMananger -> this.evaluateString(interpreter, variableMananger, viewFetchAction.getLabelExpression()))
                    .iconURL(variableMananger -> this.evaluateStringList(interpreter, variableMananger, viewFetchAction.getIconURLExpression()))
                    .precondition(variableMananger -> this.evaluateBoolean(interpreter, variableMananger, viewFetchAction.getPreconditionExpression()))
                    .urlToFetch(variableMananger -> this.evaluateString(interpreter, variableMananger, viewFetchAction.getUrlExression()))
                    .fetchKind(variableMananger -> this.convertFetchKind(viewFetchAction.getKind()))
                    .build();
        }
        return result;
    }

    private Failure buildFailureWithFeedbackMessages(String technicalMessage) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(technicalMessage, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private Success buildSuccessWithSemanticChangeAndFeedbackMessages() {
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private FetchTreeItemContextMenuEntryKind convertFetchKind(org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind fetchActionKind) {
        return switch (fetchActionKind) {
            case DOWNLOAD -> FetchTreeItemContextMenuEntryKind.DOWNLOAD;
            case OPEN -> FetchTreeItemContextMenuEntryKind.OPEN;
        };
    }
}
