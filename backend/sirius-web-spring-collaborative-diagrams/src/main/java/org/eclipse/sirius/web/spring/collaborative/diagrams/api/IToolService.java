/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.tools.ITool;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;

/**
 * Interface used to manipulate tools.
 *
 * @author hmarchadour
 */
public interface IToolService {

    /**
     * Find a specific tool according to the given diagram and tool identifiers.
     *
     * @param diagram
     *            the diagram
     * @param toolId
     *            the tool identifier
     */
    Optional<ITool> findToolById(Diagram diagram, String toolId);

    /**
     * Get all tool sections available in a specific diagram.
     *
     * @param diagram
     *            the diagram
     */
    List<ToolSection> getToolSections(Diagram diagram);

}
