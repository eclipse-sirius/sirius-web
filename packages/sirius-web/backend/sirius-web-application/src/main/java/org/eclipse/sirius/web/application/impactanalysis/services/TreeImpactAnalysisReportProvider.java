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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IChangeDescriptionDataTreeProvider;
import org.eclipse.sirius.web.application.impactanalysis.services.api.ITreeImpactAnalysisReportProvider;
import org.springframework.stereotype.Service;

/**
 * Provides impact analysis reports for diagram operations.
 *
 * @author gdaniel
 */
@Service
public class TreeImpactAnalysisReportProvider implements ITreeImpactAnalysisReportProvider {

    public static final String IMPACT_ANALYSIS_MESSAGE_PARAMETER = "impact_analysis_message_parameter_key";

    private final IChangeDescriptionDataTreeProvider changeDescriptionDataTreeProvider;

    public TreeImpactAnalysisReportProvider(IChangeDescriptionDataTreeProvider changeDescriptionDataTreeProvider) {
        this.changeDescriptionDataTreeProvider = Objects.requireNonNull(changeDescriptionDataTreeProvider);
    }

    @Override
    public Optional<ImpactAnalysisReport> getReport(IEditingContext editingContext, ChangeDescription changeDescription, IStatus toolExecutionStatus) {
        Optional<ImpactAnalysisReport> result = Optional.empty();
        if (toolExecutionStatus instanceof Success success) {
            List<String> additionalReports = (List<String>) success.getParameters().getOrDefault(IMPACT_ANALYSIS_MESSAGE_PARAMETER, List.of());
            Optional<DataTree> optionalImpactTree = this.changeDescriptionDataTreeProvider.getDataTree(editingContext, changeDescription);
            if (optionalImpactTree.isPresent()) {
                result = Optional.of(new ImpactAnalysisReport(changeDescription.getObjectsToAttach().size(), changeDescription.getObjectChanges().size(), changeDescription.getObjectsToDetach().size(),
                        additionalReports, optionalImpactTree.get()));
            }
        }

        return result;
    }

}
