/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
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
        var nodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        nodeStyle.setShape("67e2ae35-e8e7-3aaa-9ab0-c74df3410888");
        nodeStyle.setColor(this.colorProvider.getColor("color_white"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_empty"));
        nodeStyle.setBorderSize(0);
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_black"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("RequiredService");
        nodeDescription.setSemanticCandidatesExpression("aql:self.requiredServices");
        nodeDescription.setLabelExpression("aql:if self.contract = null then 'undefined' else self.contract.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

}
