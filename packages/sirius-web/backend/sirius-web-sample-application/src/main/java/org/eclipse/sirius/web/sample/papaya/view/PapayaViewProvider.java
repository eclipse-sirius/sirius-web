/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import java.util.List;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ComponentNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.DataTypeNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.DependsOnEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.EnumNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ExtendsClassEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ExtendsInterfaceEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.FulfillsContractEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ImplementsInterfaceEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.PackageNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ProvidedServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.RealizedByEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ReferencesClassEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.RequiredServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.InteractionEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalActivityNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalActorNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalEntityNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalPerimeterNodeDescriptionProvider;

/**
 * Used to create the test view.
 *
 * @author sbegaudeau
 */
public class PapayaViewProvider {

    private DiagramDescription diagramDescription;

    public View getView() {
        View view = ViewFactory.eINSTANCE.createView();

        this.diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        this.diagramDescription.setDomainType("papaya_core::Root");
        this.diagramDescription.setName("Diagram");
        this.diagramDescription.setTitleExpression("Papaya Diagram");
        this.diagramDescription.setAutoLayout(false);

        var dropTool = ViewFactory.eINSTANCE.createDropTool();
        dropTool.setName("Drop Tool");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self.drop(selectedNode, diagramContext, convertedNodes)");
        dropTool.getBody().add(changeContext);
        this.diagramDescription.setOnDrop(dropTool);

        view.getDescriptions().add(this.diagramDescription);

        var cache = new PapayaViewCache();
        // @formatter:off
        var diagramElementDescriptionProviders = List.of(
                new OperationalEntityNodeDescriptionProvider(),
                new OperationalPerimeterNodeDescriptionProvider(),
                new OperationalActorNodeDescriptionProvider(),
                new OperationalActivityNodeDescriptionProvider(),
                new InteractionEdgeDescriptionProvider(),
                new RealizedByEdgeDescriptionProvider(),
                new ComponentNodeDescriptionProvider(),
                new ProvidedServiceNodeDescriptionProvider(),
                new RequiredServiceNodeDescriptionProvider(),
                new PackageNodeDescriptionProvider(),
                new ClassNodeDescriptionProvider(),
                new InterfaceNodeDescriptionProvider(),
                new EnumNodeDescriptionProvider(),
                new DataTypeNodeDescriptionProvider(),
                new DependsOnEdgeDescriptionProvider(),
                new FulfillsContractEdgeDescriptionProvider(),
                new ExtendsClassEdgeDescriptionProvider(),
                new ReferencesClassEdgeDescriptionProvider(),
                new ImplementsInterfaceEdgeDescriptionProvider(),
                new ExtendsInterfaceEdgeDescriptionProvider(),
                new ImplementsInterfaceEdgeDescriptionProvider()
        );
        // @formatter:on

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> {
            var nodeDescription = diagramElementDescriptionProvider.create();
            cache.put(nodeDescription);
        });
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(this.diagramDescription, cache));

        return view;
    }
}
