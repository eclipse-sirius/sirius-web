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
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapyaViewBuilder;

/**
 * Description of the data type.
 *
 * @author sbegaudeau
 */
public class DataTypeNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#0097a7");
        nodeStyle.setBorderColor("#006064");
        nodeStyle.setLabelColor("white");

        var nodeDescription = new PapyaViewBuilder().createNodeDescription("DataType");
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);

        var newDataTypeNodeTool = new PapayaToolsFactory().createNamedElement("papaya::DataType", "types", "DataType");
        newDataTypeNodeTool.setName("New DataType");
        nodeDescription.getNodeTools().add(newDataTypeNodeTool);
        nodeDescription.setLabelEditTool(new PapayaToolsFactory().editName());
        nodeDescription.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

}
