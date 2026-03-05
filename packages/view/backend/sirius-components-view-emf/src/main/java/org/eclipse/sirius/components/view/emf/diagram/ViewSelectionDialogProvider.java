/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.selection.services.api.ISelectionDialogProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.selection.description.SelectionDialog;
import org.eclipse.sirius.components.selection.description.SelectionDialogAction;
import org.eclipse.sirius.components.selection.description.SelectionDialogConfirmButtonLabels;
import org.eclipse.sirius.components.selection.description.SelectionDialogStatusMessages;
import org.eclipse.sirius.components.selection.description.SelectionDialogTitles;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to compute the {@link SelectionDialog}.
 *
 * @author sbegaudeau
 */
@Service
public class ViewSelectionDialogProvider implements ISelectionDialogProvider {

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewEMFMessageService messageService;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    public ViewSelectionDialogProvider(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewEMFMessageService messageService,
            IViewAQLInterpreterFactory viewAQLInterpreterFactory) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, SelectionDescription selectionDescription) {
        return this.viewDiagramDescriptionSearchService.findViewSelectionDialogDescription(editingContext, selectionDescription.getId())
                .map(EcoreUtil::getRootContainer)
                .filter(View.class::isInstance)
                .isPresent();
    }

    @Override
    public SelectionDialog handle(IEditingContext editingContext, SelectionDescription selectionDescription, VariableManager variableManager) {
        SelectionDialogDescription viewSelectionDescription = this.viewDiagramDescriptionSearchService.findViewSelectionDialogDescription(editingContext, selectionDescription.getId()).orElse(null);
        return Optional.ofNullable(EcoreUtil.getRootContainer(viewSelectionDescription))
                .filter(View.class::isInstance)
                .map(View.class::cast)
                .map(view -> this.viewAQLInterpreterFactory.createInterpreter(editingContext, view))
                .map(aqlInterpreter -> this.getSelectionDialog(viewSelectionDescription, variableManager, aqlInterpreter))
                .orElse(null);
    }

    private SelectionDialog getSelectionDialog(SelectionDialogDescription viewSelectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        return new SelectionDialog(
                this.getSelectionDialogTitles(viewSelectionDescription, variableManager, interpreter),
                this.getSelectionDialogDescription(viewSelectionDescription, variableManager, interpreter),
                this.getSelectionDialogNoSelectionAction(viewSelectionDescription, variableManager, interpreter),
                this.getSelectionDialogWithSelectionAction(viewSelectionDescription, variableManager, interpreter),
                this.getSelectionDialogStatusMessages(viewSelectionDescription, variableManager, interpreter),
                this.getSelectionDialogConfirmButtonLabels(viewSelectionDescription, variableManager, interpreter)
        );
    }

    private SelectionDialogTitles getSelectionDialogTitles(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
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

        return new SelectionDialogTitles(defaultTitle, noSelectionTitle, withSelectionTitle);
    }

    private String getSelectionDialogDescription(SelectionDialogDescription viewSelectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String selectionDialogDescription = this.messageService.defaultSelectionDialogWithMandatorySelectionDescription();
        if (viewSelectionDescription.isOptional()) {
            selectionDialogDescription = this.messageService.defaultSelectionDialogWithOptionalSelectionDescription();
        }

        var descriptionExpression = viewSelectionDescription.getDescriptionExpression();
        var safeDescriptionExpression = Optional.ofNullable(descriptionExpression).orElse("");
        if (!safeDescriptionExpression.isBlank()) {
            selectionDialogDescription = interpreter.evaluateExpression(variableManager.getVariables(), safeDescriptionExpression).asString().orElse(selectionDialogDescription);
        }
        return selectionDialogDescription;
    }

    private SelectionDialogAction getSelectionDialogNoSelectionAction(SelectionDialogDescription viewSelectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String noSelectionActionLabel = this.messageService.defaultSelectionDialogNoSelectionActionLabel();
        String noSelectionActionLabelExpression = viewSelectionDescription.getNoSelectionActionLabelExpression();
        String safeNoSelectionActionLabelExpression = Optional.ofNullable(noSelectionActionLabelExpression).orElse("");
        if (!safeNoSelectionActionLabelExpression.isBlank()) {
            noSelectionActionLabel = interpreter.evaluateExpression(variableManager.getVariables(), safeNoSelectionActionLabelExpression).asString().orElse(noSelectionActionLabel);
        }

        String noSelectionActionDescription = this.messageService.defaultSelectionDialogNoSelectionActionDescription();
        String noSelectionActionDescriptionExpression = viewSelectionDescription.getNoSelectionActionDescriptionExpression();
        String safeNoSelectionActionDescriptionExpression = Optional.ofNullable(noSelectionActionDescriptionExpression).orElse("");
        if (!safeNoSelectionActionLabelExpression.isBlank()) {
            noSelectionActionDescription = interpreter.evaluateExpression(variableManager.getVariables(), safeNoSelectionActionDescriptionExpression).asString().orElse(noSelectionActionLabel);
        }

        return new SelectionDialogAction(noSelectionActionLabel, noSelectionActionDescription);
    }

    private SelectionDialogAction getSelectionDialogWithSelectionAction(SelectionDialogDescription viewSelectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String withSelectionActionLabel = this.messageService.defaultSelectionDialogWithSelectionActionLabel();
        String withSelectionActionLabelExpression = viewSelectionDescription.getWithSelectionActionLabelExpression();
        String safeWithSelectionActionLabelExpression = Optional.ofNullable(withSelectionActionLabelExpression).orElse("");
        if (!safeWithSelectionActionLabelExpression.isBlank()) {
            withSelectionActionLabel = interpreter.evaluateExpression(variableManager.getVariables(), safeWithSelectionActionLabelExpression).asString().orElse(withSelectionActionLabel);
        }

        String withSelectionActionDescription = this.messageService.defaultSelectionDialogWithSelectionActionDescription();
        String withSelectionActionDescriptionExpression = viewSelectionDescription.getWithSelectionActionDescriptionExpression();
        String safeWithSelectionActionDescriptionExpression = Optional.ofNullable(withSelectionActionDescriptionExpression).orElse("");
        if (!safeWithSelectionActionLabelExpression.isBlank()) {
            withSelectionActionDescription = interpreter.evaluateExpression(variableManager.getVariables(), safeWithSelectionActionDescriptionExpression).asString().orElse(withSelectionActionLabel);
        }

        return new SelectionDialogAction(withSelectionActionLabel, withSelectionActionDescription);
    }

    private SelectionDialogStatusMessages getSelectionDialogStatusMessages(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
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
        return new SelectionDialogStatusMessages(noSelectionActionStatusMessage, selectionRequiredWithoutSelectionStatusMessage);
    }

    private SelectionDialogConfirmButtonLabels getSelectionDialogConfirmButtonLabels(SelectionDialogDescription selectionDescription, VariableManager variableManager, AQLInterpreter interpreter) {
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

        return new SelectionDialogConfirmButtonLabels(noSelectionConfirmButtonLabel, selectionRequiredWithoutSelectionConfirmButtonLabel, selectionRequiredWithSelectionConfirmButtonLabel);
    }
}
