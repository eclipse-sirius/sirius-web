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
package org.eclipse.sirius.web.sample.papaya.view.classdiagram;

import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.INodeToolProvider;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Provide the tool to create packages.
 *
 * @author sbegaudeau
 */
public class CDCreatePackageNodeToolProvider implements INodeToolProvider {
    @Override
    public NodeTool create(IViewDiagramElementFinder cache) {
        var createPackageNodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        createPackageNodeTool.setName("Create Package");

        var builder = new PapayaViewBuilder();
        var domainType = builder.domainType(builder.entity("Package"));

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setTypeName(domainType);
        createInstance.setVariableName("newInstance");
        createInstance.setReferenceName("packages");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:newInstance");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'Package'");

        var optionalPackageNodeDescription = cache.getNodeDescription(CDPackageNodeDescriptionProvider.NAME);
        if (optionalPackageNodeDescription.isPresent()) {
            var createView = DiagramFactory.eINSTANCE.createCreateView();
            createView.setParentViewExpression("aql:selectedNode");
            createView.setSemanticElementExpression("aql:newInstance");
            createView.setElementDescription(optionalPackageNodeDescription.get());

            createPackageNodeTool.getBody().add(createInstance);
            createInstance.getChildren().add(changeContext);
            changeContext.getChildren().add(setValue);
            setValue.getChildren().add(createView);
        }

        return createPackageNodeTool;
    }
}
