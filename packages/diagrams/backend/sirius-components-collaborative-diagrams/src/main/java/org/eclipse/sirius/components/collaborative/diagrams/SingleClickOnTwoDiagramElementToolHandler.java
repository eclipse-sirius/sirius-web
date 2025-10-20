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
package org.eclipse.sirius.components.collaborative.diagrams;

import org.eclipse.sirius.components.collaborative.diagrams.api.IToolHandler;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Tool handler for SingleClickOnTwoDiagramElementTool.
 *
 * @author mcharfadi
 */
public record SingleClickOnTwoDiagramElementToolHandler(Function<VariableManager, IStatus> handler, Optional<String> dialogDescriptionId, List<String> targetCandidatesId) implements IToolHandler {
}
