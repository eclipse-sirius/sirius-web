/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * Used to convert the diagram into a graph of ELK objects.
 *
 * @author gcoutable
 */
public interface IELKDiagramConverter {

    ELKConvertedDiagram convert(Diagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IELKDiagramConverter {

        @Override
        public ELKConvertedDiagram convert(Diagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
            return null;
        }

    }

}
