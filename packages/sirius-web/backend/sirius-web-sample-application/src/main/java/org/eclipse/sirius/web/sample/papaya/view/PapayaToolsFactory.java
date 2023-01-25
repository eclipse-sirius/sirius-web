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
package org.eclipse.sirius.web.sample.papaya.view;

import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.ViewFactory;

/**
 * Used to create the default papaya tools.
 *
 * @author sbegaudeau
 */
public class PapayaToolsFactory {
    public NodeTool createNamedElement(String typeName, String containinedFeatureName, String initialName) {
        var nodeTool = ViewFactory.eINSTANCE.createNodeTool();

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setTypeName(typeName);
        createInstance.setVariableName("newInstance");
        createInstance.setReferenceName(containinedFeatureName);

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:newInstance");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'" + initialName + "'");

        nodeTool.getBody().add(createInstance);
        createInstance.getChildren().add(changeContext);
        changeContext.getChildren().add(setValue);

        return nodeTool;
    }

    public EdgeTool setReference(String featureName, String valueExpression) {
        var edgeTool = ViewFactory.eINSTANCE.createEdgeTool();

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName(featureName);
        setValue.setValueExpression(valueExpression);

        edgeTool.getBody().add(changeContext);
        changeContext.getChildren().add(setValue);

        return edgeTool;
    }

    public LabelEditTool editName() {
        var editLabelTool = ViewFactory.eINSTANCE.createLabelEditTool();
        editLabelTool.setName("Edit Label");
        editLabelTool.setInitialDirectEditLabelExpression("aql:self.name");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:newLabel");

        editLabelTool.getBody().add(setValue);

        return editLabelTool;
    }

    public DeleteTool deleteTool() {
        var deleteTool = ViewFactory.eINSTANCE.createDeleteTool();
        deleteTool.setName("Delete");

        var deleteElement = ViewFactory.eINSTANCE.createDeleteElement();

        deleteTool.getBody().add(deleteElement);

        return deleteTool;
    }
}
