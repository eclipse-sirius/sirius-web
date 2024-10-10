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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.trees.api.ISingleClickTreeItemContextMenuEntryExecutor;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Fetch tree item context menu entry data provider for view tree model.
 *
 * @author Jerome Gout
 */
@Service
public class ViewSingleClickTreeItemContextMenuEntryExecutor implements ISingleClickTreeItemContextMenuEntryExecutor {

    private final IFeedbackMessageService feedbackMessageService;

    private final IEditService editService;

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    private AQLInterpreter interpreter;

    private VariableManager variableManager;

    public ViewSingleClickTreeItemContextMenuEntryExecutor(IFeedbackMessageService feedbackMessageService, IEditService editService, ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.editService = Objects.requireNonNull(editService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canExecute(TreeDescription treeDescription) {
        return viewRepresentationDescriptionPredicate.test(treeDescription);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem, String treeItemMenuContextEntryId) {
        var optionalTreeDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, treeDescription.getId())
                .filter(org.eclipse.sirius.components.view.tree.TreeDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.tree.TreeDescription.class::cast);
        if (optionalTreeDescription.isPresent()) {
            var viewTreeDescription = optionalTreeDescription.get();
            initializeAQLInterpreter(editingContext, treeDescription, tree, treeItem, viewTreeDescription);
            var singleClickEntry = viewTreeDescription.getContextMenuEntries().stream()
                    .filter(SingleClickTreeItemContextMenuEntry.class::isInstance)
                    .map(SingleClickTreeItemContextMenuEntry.class::cast)
                    .filter(entry -> Objects.equals(treeItemMenuContextEntryId, idProvider.apply(entry).toString()))
                    .findFirst();
            if (singleClickEntry.isPresent()) {
                return executeOperations(this.variableManager, singleClickEntry.get().getBody());
            }
        }
        return buildFailureWithFeedbackMessages("Something went wrong while handling the context menu action");
    }

    private FetchTreeItemContextMenuEntryKind convertFetchKind(org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind fetchActionKind) {
        return switch (fetchActionKind) {
            case DOWNLOAD -> FetchTreeItemContextMenuEntryKind.DOWNLOAD;
            case OPEN -> FetchTreeItemContextMenuEntryKind.OPEN;
        };
    }

    private String evaluateString(VariableManager varManager, String expression) {
        return this.interpreter.evaluateExpression(varManager.getVariables(), expression)
                .asString()
                .orElse("");
    }

    private IStatus executeOperations(VariableManager varManager, List<Operation> operations) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, varManager);
        if (optionalVariableManager.isEmpty()) {
            return this.buildFailureWithFeedbackMessages("Something went wrong while handling the context menu action");
        } else {
            return this.buildSuccessWithSemanticChangeAndFeedbackMessages();
        }
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

    private void initializeAQLInterpreter(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem,
                                          org.eclipse.sirius.components.view.tree.TreeDescription viewTreeDescription) {
        this.interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewTreeDescription.eContainer());
        this.variableManager = new VariableManager();
        this.variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        this.variableManager.put(TreeDescription.TREE, tree);
        this.variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);
        this.variableManager.put(TreeDescription.ID, treeItem.getId());
        var semanticTreeItemObject = treeDescription.getTreeItemObjectProvider().apply(this.variableManager);
        this.variableManager.put(VariableManager.SELF, semanticTreeItemObject);
    }
}
