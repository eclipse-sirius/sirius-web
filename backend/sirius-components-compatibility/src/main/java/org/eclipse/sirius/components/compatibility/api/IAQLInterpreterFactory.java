/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.compatibility.api;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.properties.ViewExtensionDescription;

/**
 * Factory used to create instances of the AQL interpreter parameterized for a given representation. In Sirius RCP, a
 * representation may define the list of metamodels available, in this case only the specified metamodels should be
 * available in the AQL interpreter.
 *
 * @author sbegaudeau
 */
public interface IAQLInterpreterFactory {
    AQLInterpreter create(DiagramDescription diagramDescription);

    AQLInterpreter create(ViewExtensionDescription viewExtensionDescription);
}
