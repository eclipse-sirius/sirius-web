/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Provides a custom layout configuration for specific diagram(s).
 *
 * @author pcdavid
 */
public interface IDiagramLayoutConfiguratorProvider {
    Optional<ISiriusWebLayoutConfigurator> getLayoutConfigurator(Diagram diagram, DiagramDescription diagramDescription);
}
