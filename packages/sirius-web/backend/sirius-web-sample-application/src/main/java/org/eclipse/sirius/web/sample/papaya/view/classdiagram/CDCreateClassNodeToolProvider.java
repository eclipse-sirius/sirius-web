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

import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeToolProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Provide the tool to create classes.
 *
 * @author sbegaudeau
 */
public class CDCreateClassNodeToolProvider implements INodeToolProvider {
    @Override
    public NodeTool create(PapayaViewCache cache) {
        var nodeTool = ViewFactory.eINSTANCE.createNodeTool();
        nodeTool.setName("Create Class ");

        var builder = new PapayaViewBuilder();
        var domainType = builder.domainType(builder.entity("Class"));

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setTypeName(domainType);
        createInstance.setVariableName("newInstance");
        createInstance.setReferenceName("types");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:newInstance");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'Class'");

        var classNodeDescription = cache.getNodeDescription(CDClassNodeDescriptionProvider.NAME);
        var createView = ViewFactory.eINSTANCE.createCreateView();
        createView.setParentViewExpression("aql:selectedNode");
        createView.setSemanticElementExpression("aql:newInstance");
        createView.setElementDescription(classNodeDescription);

        nodeTool.getBody().add(createInstance);
        createInstance.getChildren().add(changeContext);
        changeContext.getChildren().add(setValue);
        setValue.getChildren().add(createView);

        return nodeTool;
    }
}
