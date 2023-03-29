/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.DiagramLayoutConfiguration;

/**
 * Used to provide the diagram layout configuration.
 *
 * @author sbegaudeau
 */
public interface IDiagramLayoutConfigurationProvider {
    Optional<DiagramLayoutConfiguration> getDiagramLayoutConfiguration(IEditingContext editingContext, Diagram diagram);
}
