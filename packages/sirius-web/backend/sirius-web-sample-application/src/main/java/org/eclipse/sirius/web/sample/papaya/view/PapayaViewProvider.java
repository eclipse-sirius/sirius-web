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

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.classdiagram.ClassDiagramDescriptionProvider;
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
import org.eclipse.sirius.web.sample.papaya.view.overviewform.OverviewFormProvider;

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
        DiagramPalette palette = ViewFactory.eINSTANCE.createDiagramPalette();
        palette.setDropTool(dropTool);
        this.diagramDescription.setPalette(palette);

        view.getDescriptions().add(this.diagramDescription);

        view.getColorPalettes().add(this.createColorPalette());

        IColorProvider colorProvider = new ColorProvider(view);

        var cache = new ViewDiagramElementFinder();
        // @formatter:off
        var diagramElementDescriptionProviders = List.of(
                new OperationalEntityNodeDescriptionProvider(colorProvider),
                new OperationalPerimeterNodeDescriptionProvider(colorProvider),
                new OperationalActorNodeDescriptionProvider(colorProvider),
                new OperationalActivityNodeDescriptionProvider(colorProvider),
                new InteractionEdgeDescriptionProvider(colorProvider),
                new RealizedByEdgeDescriptionProvider(colorProvider),
                new ComponentNodeDescriptionProvider(colorProvider),
                new ProvidedServiceNodeDescriptionProvider(colorProvider),
                new RequiredServiceNodeDescriptionProvider(colorProvider),
                new PackageNodeDescriptionProvider(colorProvider),
                new ClassNodeDescriptionProvider(colorProvider),
                new InterfaceNodeDescriptionProvider(colorProvider),
                new EnumNodeDescriptionProvider(colorProvider),
                new DataTypeNodeDescriptionProvider(colorProvider),
                new DependsOnEdgeDescriptionProvider(colorProvider),
                new FulfillsContractEdgeDescriptionProvider(colorProvider),
                new ExtendsClassEdgeDescriptionProvider(colorProvider),
                new ReferencesClassEdgeDescriptionProvider(colorProvider),
                new ImplementsInterfaceEdgeDescriptionProvider(colorProvider),
                new ExtendsInterfaceEdgeDescriptionProvider(colorProvider),
                new ImplementsInterfaceEdgeDescriptionProvider(colorProvider)
        );
        // @formatter:on

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> {
            var nodeDescription = diagramElementDescriptionProvider.create();
            cache.put(nodeDescription);
        });
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(this.diagramDescription, cache));

        var builder = new PapayaViewBuilder();
        var nodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalEntity");
        if (nodeDescription.isPresent()) {
            var newOperationalEntityNodeTool = this.createNewOperationalEntityTool(builder, nodeDescription.get());
            palette.getNodeTools().addAll(List.of(this.getInitializeNodeTool(), newOperationalEntityNodeTool));
        }

        var newComponentNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Component", "components", "Component");
        newComponentNodeTool.setName("New Component");
        palette.getNodeTools().add(newComponentNodeTool);

        var newOperationalActorNodeTool = new PapayaToolsFactory().createNamedElement("papaya_operational_analysis::OperationalActor", "operationalActors", "Operational Actor");
        newOperationalActorNodeTool.setName("New Operational Actor");
        palette.getNodeTools().add(newOperationalActorNodeTool);

        var classDiagramDescription = new ClassDiagramDescriptionProvider().create(colorProvider);
        view.getDescriptions().add(classDiagramDescription);

        var overviewFormDescription = new OverviewFormProvider().create(colorProvider);
        view.getDescriptions().add(overviewFormDescription);

        return view;
    }

    private ColorPalette createColorPalette() {
        var colorPalette = ViewFactory.eINSTANCE.createColorPalette();

        colorPalette.getColors().add(this.createFixedColor("color_empty", ""));
        colorPalette.getColors().add(this.createFixedColor("color_white", "white"));
        colorPalette.getColors().add(this.createFixedColor("color_blue", "#1976D2"));
        colorPalette.getColors().add(this.createFixedColor("color_blue_2", "#1a237e"));
        colorPalette.getColors().add(this.createFixedColor("color_blue_3", "#3f51b5"));
        colorPalette.getColors().add(this.createFixedColor("color_blue_4", "#b0bec5"));
        colorPalette.getColors().add(this.createFixedColor("color_blue_5", "#0097a7"));
        colorPalette.getColors().add(this.createFixedColor("color_blue_6", "#0d47a1"));
        colorPalette.getColors().add(this.createFixedColor("color_blue_7", "#d1c4e9"));
        colorPalette.getColors().add(this.createFixedColor("color_green", "#00796B"));
        colorPalette.getColors().add(this.createFixedColor("color_green_2", "#1b5e20"));
        colorPalette.getColors().add(this.createFixedColor("color_green_3", "#26a69a"));
        colorPalette.getColors().add(this.createFixedColor("color_black", "#212121"));
        colorPalette.getColors().add(this.createFixedColor("color_orange", "#ffcc80"));
        colorPalette.getColors().add(this.createFixedColor("color_gray", "#e0e0e0"));
        colorPalette.getColors().add(this.createFixedColor("color_gray_2", "#bdbdbd"));
        colorPalette.getColors().add(this.createFixedColor("color_red", "#fb8c00"));


        colorPalette.getColors().add(this.createFixedColor("background_green", "#004D40"));

        colorPalette.getColors().add(this.createFixedColor("border_empty", ""));
        colorPalette.getColors().add(this.createFixedColor("border_gray", "#616161"));
        colorPalette.getColors().add(this.createFixedColor("border_gray_2", "#424242"));
        colorPalette.getColors().add(this.createFixedColor("border_blue", "#0d47a1"));
        colorPalette.getColors().add(this.createFixedColor("border_blue_2", "#1a237e"));
        colorPalette.getColors().add(this.createFixedColor("border_blue_3", "#5e35b1"));
        colorPalette.getColors().add(this.createFixedColor("border_blue_4", "#455a64"));
        colorPalette.getColors().add(this.createFixedColor("border_blue_5", "#006064"));
        colorPalette.getColors().add(this.createFixedColor("border_orange", "#fb8c00"));
        colorPalette.getColors().add(this.createFixedColor("border_green", "#004D40"));
        colorPalette.getColors().add(this.createFixedColor("border_green_2", "#00695c"));

        colorPalette.getColors().add(this.createFixedColor("label_white", "white"));
        colorPalette.getColors().add(this.createFixedColor("label_black", "#212121"));

        return colorPalette;
    }

    private FixedColor createFixedColor(String name, String value) {
        var fixedColor = ViewFactory.eINSTANCE.createFixedColor();
        fixedColor.setName(name);
        fixedColor.setValue(value);

        return fixedColor;
    }

    private NodeTool createNewOperationalEntityTool(PapayaViewBuilder builder, NodeDescription nodeDescription) {
        var defaultNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        defaultNodeTool.setName("New Operational Entity");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self");

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setReferenceName("operationalEntities");
        createInstance.setTypeName(builder.domainType(builder.entity("OperationalEntity")));
        createInstance.setVariableName("self");

        var createView = ViewFactory.eINSTANCE.createCreateView();
        createView.setElementDescription(nodeDescription);
        createView.setSemanticElementExpression("aql:self");
        createView.setParentViewExpression("aql:false");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'Operational Entity'");

        createView.getChildren().add(setValue);
        createInstance.getChildren().add(createView);
        changeContext.getChildren().add(createInstance);
        defaultNodeTool.getBody().add(changeContext);
        return defaultNodeTool;
    }

    private NodeTool getInitializeNodeTool() {
        var initializeNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        initializeNodeTool.setName("Initialize Data");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self.initialize(diagramContext, convertedNodes)");

        initializeNodeTool.getBody().add(changeContext);

        return initializeNodeTool;
    }
}
