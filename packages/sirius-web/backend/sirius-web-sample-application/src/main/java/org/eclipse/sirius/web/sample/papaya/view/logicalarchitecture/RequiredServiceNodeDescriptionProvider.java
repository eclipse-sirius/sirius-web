/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Description of the required service.
 *
 * @author sbegaudeau
 */
public class RequiredServiceNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public RequiredServiceNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createImageNodeStyleDescription();
        nodeStyle.setShape("67e2ae35-e8e7-3aaa-9ab0-c74df3410888");
        nodeStyle.setColor(this.colorProvider.getColor("color_white"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_empty"));
        nodeStyle.setBorderSize(0);
        nodeStyle.setPositionDependentRotation(true);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("RequiredService");
        nodeDescription.setSemanticCandidatesExpression("aql:self.requiredServices");
        nodeDescription.getOutsideLabels().add(new PapayaViewBuilder().createOutsideLabelDescription("aql:if self.contract = null then 'undefined' else self.contract.name endif",
                this.colorProvider.getColor("label_black")));
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

}
