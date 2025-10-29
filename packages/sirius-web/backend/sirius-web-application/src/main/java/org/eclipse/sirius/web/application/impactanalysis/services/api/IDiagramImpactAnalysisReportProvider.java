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
package org.eclipse.sirius.web.application.impactanalysis.services.api;

import java.util.Optional;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Provides impact analysis reports for diagram operations.
 *
 * @author gdaniel
 */
public interface IDiagramImpactAnalysisReportProvider {

    Optional<ImpactAnalysisReport> getReport(IEditingContext editingContext, ChangeDescription changeDescription, IStatus toolExecutionStatus);


}
