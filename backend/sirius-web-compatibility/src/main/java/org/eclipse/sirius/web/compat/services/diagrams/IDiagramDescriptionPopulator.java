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
package org.eclipse.sirius.web.compat.services.diagrams;

import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;

/**
 * Used to populate a part of the diagram description builder.
 *
 * @author sbegaudeau
 */
public interface IDiagramDescriptionPopulator {
    DiagramDescription.Builder populate(DiagramDescription.Builder builder, org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription, AQLInterpreter interpreter);
}
