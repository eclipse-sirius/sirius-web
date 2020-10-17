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
/*******************************************************************************
 * Copyright (c) 2007, 2018 THALES GLOBAL SERVICES.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.compat.services.diagrams.tools;

import java.util.List;

import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.sirius.diagram.business.internal.query.AbstractNodeMappingApplicabilityTester;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.NodeMapping;

/**
 * Class dedicated to tools applicability test.
 * <p>
 * This class uses {@link AbstractNodeMappingApplicabilityTester} as delegate.
 * </p>
 *
 * @author hmarchadour
 */
public class CanCreateMappingTester {

    private AbstractNodeMappingApplicabilityTester delegate;

    /**
     * Create a new tester.
     *
     * @param mappingsToCreate
     *            mappings of the elements to test the creation.
     */
    public CanCreateMappingTester(List<? extends AbstractNodeMapping> mappingsToCreate) {
        this.delegate = new AbstractNodeMappingApplicabilityTester(mappingsToCreate);
    }

    public boolean canCreateIn(AbstractNodeMapping abstractNodeMapping) {
        boolean canCreateIn = false;
        if (abstractNodeMapping instanceof ContainerMapping) {
            ContainerMapping containerMapping = (ContainerMapping) abstractNodeMapping;
            DNodeContainer dNodeContainer = DiagramFactory.eINSTANCE.createDNodeContainer();
            dNodeContainer.setActualMapping(containerMapping);
            canCreateIn = this.delegate.canCreateIn(dNodeContainer);
        } else if (abstractNodeMapping instanceof NodeMapping) {
            NodeMapping nodeMapping = (NodeMapping) abstractNodeMapping;
            DNode dNode = DiagramFactory.eINSTANCE.createDNode();
            dNode.setActualMapping(nodeMapping);
            canCreateIn = this.delegate.canCreateIn(dNode);
        }
        return canCreateIn;
    }

    public boolean canCreateIn(DiagramDescription description) {
        DDiagram dDiagram = DiagramFactory.eINSTANCE.createDDiagram();
        dDiagram.setDescription(description);
        return this.delegate.canCreateIn(dDiagram);
    }

}
