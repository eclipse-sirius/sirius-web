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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.datatree.DataTree;

/**
 * Report with data to display an impact analysis.
 *
 * @author frouene
 */
public record ImpactAnalysisReport(int nbElementDeleted, int nbElementModified, int nbElementCreated, List<String> additionalReports, DataTree impactTree) {

    public ImpactAnalysisReport {
        Objects.requireNonNull(additionalReports);
        Objects.requireNonNull(impactTree);
    }

}
