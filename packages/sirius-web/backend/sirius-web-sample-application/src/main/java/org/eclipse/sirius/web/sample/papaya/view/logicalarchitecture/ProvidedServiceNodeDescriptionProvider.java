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

import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapyaViewBuilder;

/**
 * Description of provided service.
 *
 * @author sbegaudeau
 */
public class ProvidedServiceNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        nodeStyle.setShape("f13acf89-e0bc-3b42-a0f6-c39e459e311e");
        nodeStyle.setColor("white");
        nodeStyle.setBorderColor("");
        nodeStyle.setBorderSize(0);
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = new PapyaViewBuilder().createNodeDescription("ProvidedService");
        nodeDescription.setSemanticCandidatesExpression("aql:self.providedServices");
        nodeDescription.setLabelExpression("aql:if self.contract = null then 'undefined' else self.contract.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

}
