/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.export.api;

import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * Interface of the service used to export a diagram to SVG.
 *
 * @author rpage
 */
public interface ISVGDiagramExportService {
    String export(Diagram diagram);
}
