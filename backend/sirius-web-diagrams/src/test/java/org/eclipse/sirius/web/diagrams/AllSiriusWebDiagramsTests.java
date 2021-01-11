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
package org.eclipse.sirius.web.diagrams;

import org.eclipse.sirius.web.diagrams.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.diagrams.architecture.ImmutableTestCases;
import org.eclipse.sirius.web.diagrams.components.EdgeLabelPositionProviderTestCases;
import org.eclipse.sirius.web.diagrams.components.EdgeRoutingPointsProviderTestCases;
import org.eclipse.sirius.web.diagrams.components.NodeLabelPositionProviderTestCases;
import org.eclipse.sirius.web.diagrams.components.NodePositionProviderTestCases;
import org.eclipse.sirius.web.diagrams.components.NodeSizeProviderTestCases;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRendererEdgeTestCases;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRendererNodeTestCases;
import org.eclipse.sirius.web.diagrams.renderer.UnsynchronizedDiagramTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite used to run all the unit tests of the diagrams.
 *
 * @author sbegaudeau
 */
@RunWith(Suite.class)
@SuiteClasses({ CodingRulesTestCases.class, ImmutableTestCases.class, DiagramRendererEdgeTestCases.class, DiagramRendererNodeTestCases.class, UnsynchronizedDiagramTestCases.class,
        EdgeRoutingPointsProviderTestCases.class, NodePositionProviderTestCases.class, NodeSizeProviderTestCases.class, NodeLabelPositionProviderTestCases.class,
        EdgeLabelPositionProviderTestCases.class })
public final class AllSiriusWebDiagramsTests {
    private AllSiriusWebDiagramsTests() {
        // Prevent instantiation
    }
}
