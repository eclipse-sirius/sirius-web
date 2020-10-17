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
package org.eclipse.sirius.web.compat;

import org.eclipse.sirius.web.compat.architecture.CodingRulesTestCases;
import org.eclipse.sirius.web.compat.architecture.ConfigurationTestCases;
import org.eclipse.sirius.web.compat.architecture.ServiceTestCases;
import org.eclipse.sirius.web.compat.architecture.SpringCodingRulesTestCases;
import org.eclipse.sirius.web.compat.diagrams.ColorDescriptionConverterTestCases;
import org.eclipse.sirius.web.compat.diagrams.ContainerMappingStyleProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.DiagramLabelProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.DomainBasedSourceNodesProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.EdgeMappingConverterTestCases;
import org.eclipse.sirius.web.compat.diagrams.EdgeMappingStyleProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.NodeMappingStyleProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.RelationBasedSourceNodesProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.SemanticCandidatesProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.ToolImageProviderTestCases;
import org.eclipse.sirius.web.compat.diagrams.WorkspaceImageDescriptionConverterTestCases;
import org.eclipse.sirius.web.compat.forms.FormRendererTestCases;
import org.eclipse.sirius.web.compat.operations.ChangeContextOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.CreateInstanceOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.ForOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.IfOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.LetOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.MoveElementOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.RemoveElementOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.SetValueOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.SwitchOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.operations.UnsetOperationHandlerTestCases;
import org.eclipse.sirius.web.compat.utils.DomainClassPredicateTestCases;
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
    EdgeMappingConverterTestCases.class,
    EdgeMappingStyleProviderTestCases.class,
    NodeMappingStyleProviderTestCases.class,
    ToolImageProviderTestCases.class,
    RelationBasedSourceNodesProviderTestCases.class,
    SemanticCandidatesProviderTestCases.class,
    WorkspaceImageDescriptionConverterTestCases.class,

    FormRendererTestCases.class,

    ChangeContextOperationHandlerTestCases.class,
    CreateInstanceOperationHandlerTestCases.class,
    ForOperationHandlerTestCases.class,
    IfOperationHandlerTestCases.class,
    LetOperationHandlerTestCases.class,
    MoveElementOperationHandlerTestCases.class,
    RemoveElementOperationHandlerTestCases.class,
    SetValueOperationHandlerTestCases.class,
    SwitchOperationHandlerTestCases.class,
    UnsetOperationHandlerTestCases.class,

    DomainClassPredicateTestCases.class,
})
// @formatter:on
public final class AllSiriusWebSiriusTests {
    private AllSiriusWebSiriusTests() {
        // Prevent instantiation
    }
}
