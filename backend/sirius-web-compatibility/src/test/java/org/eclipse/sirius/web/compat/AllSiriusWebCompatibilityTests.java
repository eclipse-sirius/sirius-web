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
package org.eclipse.sirius.web.compat;

import org.eclipse.sirius.web.compat.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.compat.architecture.ConfigurationTestCases;
import org.eclipse.sirius.web.compat.architecture.ServiceTestCases;
import org.eclipse.sirius.web.compat.architecture.SpringCodingRulesTestCases;
import org.eclipse.sirius.web.compat.diagrams.ColorDescriptionConverterTestCases;
import org.eclipse.sirius.web.compat.diagrams.ContainerMappingStyleProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.DiagramLabelProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.DomainBasedSourceNodesProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.EdgeMappingStyleProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.LabelStyleDescriptionConverterTestCases;
import org.eclipse.sirius.web.compat.diagrams.MappingConverterTestCases;
import org.eclipse.sirius.web.compat.diagrams.NodeMappingStyleProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.RelationBasedSourceNodesProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.WorkspaceImageDescriptionConverterTestCases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Tests suite of Sirius Web.
 *
 * @author sbegaudeau
 */
// @formatter:off
@RunWith(Suite.class)
@SuiteClasses({
    CodingRulesTestCases.class,
    ConfigurationTestCases.class,
    ServiceTestCases.class,
    SpringCodingRulesTestCases.class,

    ColorDescriptionConverterTestCases.class,
    ContainerMappingStyleProviderTestCases.class,
    DiagramLabelProviderTestCases.class,
    DomainBasedSourceNodesProviderTestCases.class,

    EdgeMappingStyleProviderTestCases.class,
    LabelStyleDescriptionConverterTestCases.class,
    MappingConverterTestCases.class,
    NodeMappingStyleProviderTestCases.class,
    RelationBasedSourceNodesProviderTestCases.class,
    WorkspaceImageDescriptionConverterTestCases.class,
})
// @formatter:on
public final class AllSiriusWebCompatibilityTests {
    private AllSiriusWebCompatibilityTests() {
        // Prevent instantiation
    }
}
