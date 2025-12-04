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
package org.eclipse.sirius.web.application.impactanalysis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IChangeDescriptionDataTreeProvider;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IDiagramImpactAnalysisReportProvider;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides impact analysis reports for diagram operations.
 *
 * @author gdaniel
 */
@Service
public class DiagramImpactAnalysisReportProvider implements IDiagramImpactAnalysisReportProvider {

    public static final String IMPACT_ANALYSIS_MESSAGE_PARAMETER = "impact_analysis_message_parameter_key";

    private final IChangeDescriptionDataTreeProvider changeDescriptionDataTreeProvider;

    private final IMessageService messageService;

    public DiagramImpactAnalysisReportProvider(IChangeDescriptionDataTreeProvider changeDescriptionDataTreeProvider, IMessageService messageService) {
        this.changeDescriptionDataTreeProvider = Objects.requireNonNull(changeDescriptionDataTreeProvider);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public Optional<ImpactAnalysisReport> getReport(IEditingContext editingContext, ChangeDescription changeDescription, IStatus toolExecutionStatus) {
        Optional<ImpactAnalysisReport> result = Optional.empty();
        List<String> additionalReports = new ArrayList<>();
        if (toolExecutionStatus instanceof Success success) {
            if (success.getParameters().get("viewCreationRequests") instanceof List<?> viewCreationRequestsList && !viewCreationRequestsList.isEmpty()) {
                additionalReports.add("Views added: " + viewCreationRequestsList.size());
            }
            if (success.getParameters().get("viewDeletionRequests") instanceof List<?> viewDeletionRequestsList && !viewDeletionRequestsList.isEmpty()) {
                additionalReports.add("Views deleted: " + viewDeletionRequestsList.size());
            }
            List<String> additionalMessages = (List<String>) success.getParameters().getOrDefault(IMPACT_ANALYSIS_MESSAGE_PARAMETER, List.of());
            additionalReports.addAll(additionalMessages);
        } else if (toolExecutionStatus instanceof Failure failure) {
            List<Message> failureMessages = failure.getMessages();
            if (failureMessages.isEmpty()) {
                // Add a generic error message if the status doesn't contain any information.
                additionalReports.add(this.messageService.operationExecutionFailed(this.messageService.unexpectedError()));
            } else {
                for (Message failureMessage : failureMessages) {
                    additionalReports.add(this.messageService.operationExecutionFailed(failureMessage.body()));
                }
            }
        }
        Optional<DataTree> optionalImpactTree = this.changeDescriptionDataTreeProvider.getDataTree(editingContext, changeDescription);
        if (optionalImpactTree.isPresent()) {
            result = Optional.of(new ImpactAnalysisReport(changeDescription.getObjectsToAttach().size(), changeDescription.getObjectChanges().size(), changeDescription.getObjectsToDetach().size(),
                    additionalReports, optionalImpactTree.get()));
        }

        return result;
    }

}
