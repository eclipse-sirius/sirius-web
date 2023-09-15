/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.ContainerLayout;
import org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription;
import org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.junit.jupiter.api.Test;

/**
 * Unit tests used to validate the proper retrieval of the conditional styles.
 *
 * @author arichard
 */
public class MappingConverterTests {

    private static final String EXPRESSION_FALSE = "aql:false";

    private static final String EXPRESSION_TRUE = "aql:true";

    private static final String PLUGIN_ID = "my.sirius.plugin";

    private static final String ICON_PATH = "/my/icon/path/MyIcon.gif";

    @Test
    public void testContainerStylePropertiesFromConditionalStyle() {
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        String mappingName = "TestMapping";
        containerMapping.setName(mappingName);


        ContainerStyleDescription defaultStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(defaultStyle)
                .labelExpression("aql: defaultStyle")
                .labelSize(10)
                .labelColor(this.getColor(1, 1, 1));

        ContainerStyleDescription firstConditionalStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(firstConditionalStyle)
                .labelExpression("aql:'firstConditionalStyle'")
                .labelSize(4)
                .labelColor(this.getColor(3, 3, 3));

        ContainerStyleDescription secondConditionalStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(secondConditionalStyle)
                .labelExpression("aql:'secondConditionalStyle'")
                .labelSize(6)
                .bold()
                .italic()
                .underline()
                .strikeThrough()
                .labelColor(this.getColor(2, 2, 2)).iconPath(PLUGIN_ID + ICON_PATH);

        ContainerStyleDescription thirdConditionalStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(thirdConditionalStyle)
                .labelExpression("aql:'thirdConditionalStyle'")
                .labelSize(8)
                .labelColor(this.getColor(4, 4, 4));

        containerMapping.setStyle(defaultStyle);
        containerMapping.getConditionnalStyles().add(this.createConditionalContainerStyle(EXPRESSION_FALSE, firstConditionalStyle));
        containerMapping.getConditionnalStyles().add(this.createConditionalContainerStyle(EXPRESSION_TRUE, secondConditionalStyle));
        containerMapping.getConditionnalStyles().add(this.createConditionalContainerStyle(EXPRESSION_TRUE, thirdConditionalStyle));

        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return UUID.randomUUID().toString();
            }
        };
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = (interpreter, domainClass, semanticCandidatesExpression, preconditionExpression) -> variableManager -> List.of();
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpreter -> modelOperation -> Optional.empty();

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        var converter = new AbstractNodeMappingConverter(new IObjectService.NoOp(), new IEditService.NoOp(), identifierProvider, semanticCandidatesProviderFactory, modelOperationHandlerSwitchProvider,
                new ImageSizeProvider());

        NodeDescription convertedNodeDescription = converter.convert(containerMapping, interpreter, new HashMap<String, NodeDescription>());
        LabelDescription labelDescription = convertedNodeDescription.getLabelDescription();

        String text = labelDescription.getTextProvider().apply(variableManager);
        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescriptionProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean isBold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean isItalic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean isUnderline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        Boolean isStrikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        List<String> iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);

        assertThat(text).isEqualTo("secondConditionalStyle");
        assertThat(fontSize).isEqualTo(6);
        assertThat(isBold).isTrue();
        assertThat(isItalic).isTrue();
        assertThat(isUnderline).isTrue();
        assertThat(isStrikeThrough).isTrue();
        assertThat(color).isEqualTo("#020202");
        assertThat(iconURL).hasSize(1);
        assertThat(iconURL.get(0)).isEqualTo(ICON_PATH);
    }

    /**
     * Test that nodes inside a list are always typed as list items with a matching style, regardless of their "native"
     * style.
     */
    @Test
    public void testContainerWithImageSubNode() {
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        containerMapping.setName("EClass");
        containerMapping.setDomainClass("ecore::EClass");
        containerMapping.setSemanticCandidatesExpression("aql:self.eClassifiers");
        containerMapping.setChildrenPresentation(ContainerLayout.LIST);
        ContainerStyleDescription containerStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        containerStyle.setLabelExpression("aql:self.name");
        containerMapping.setStyle(containerStyle);

        NodeMapping itemMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        itemMapping.setDomainClass("ecore::EAttribute");
        itemMapping.setSemanticCandidatesExpression("aql:self.eStructuralFeatures");
        WorkspaceImageDescription imageStyle = StyleFactory.eINSTANCE.createWorkspaceImageDescription();
        imageStyle.setLabelExpression("aql:self.name");
        imageStyle.setWorkspacePath("path/to/image.svg");
        itemMapping.setStyle(imageStyle);
        containerMapping.getSubNodeMappings().add(itemMapping);

        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return UUID.randomUUID().toString();
            }
        };
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = (interpreter, domainClass, semanticCandidatesExpression, preconditionExpression) -> variableManager -> List.of();
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpreter -> modelOperation -> Optional.empty();

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        var converter = new AbstractNodeMappingConverter(new IObjectService.NoOp(), new IEditService.NoOp(), identifierProvider, semanticCandidatesProviderFactory, modelOperationHandlerSwitchProvider,
                new ImageSizeProvider());
        NodeDescription convertedNodeDescription = converter.convert(containerMapping, interpreter, new HashMap<String, NodeDescription>());

        VariableManager variableManager = new VariableManager();

        variableManager.put(VariableManager.SELF, EcorePackage.Literals.ECLASS);
        assertThat(convertedNodeDescription.getTypeProvider().apply(variableManager)).isEqualTo(NodeType.NODE_RECTANGLE);
        assertThat(convertedNodeDescription.getStyleProvider().apply(variableManager)).isInstanceOf(RectangularNodeStyle.class);
        assertThat(convertedNodeDescription.getStyleProvider().apply(variableManager)).asInstanceOf(InstanceOfAssertFactories.type(RectangularNodeStyle.class))
                .matches(RectangularNodeStyle::isWithHeader);

        assertThat(convertedNodeDescription.getChildNodeDescriptions()).hasSize(1);
        NodeDescription subNodeDescription = convertedNodeDescription.getChildNodeDescriptions().get(0);
        variableManager.put(VariableManager.SELF, EcorePackage.Literals.ECLASS__ABSTRACT);
        assertThat(subNodeDescription.getTypeProvider().apply(variableManager)).isEqualTo(NodeType.NODE_ICON_LABEL);
        assertThat(subNodeDescription.getStyleProvider().apply(variableManager)).isInstanceOf(IconLabelNodeStyle.class);
    }

    @Test
    public void testNodeStylePropertiesFromConditionalStyle() {
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        String mappingName = "TestMapping";
        nodeMapping.setName(mappingName);

        NodeStyleDescription defaultStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(defaultStyle)
                .labelExpression("aql:'defaultStyle'")
                .labelSize(10).labelColor(this.getColor(1, 1, 1));

        NodeStyleDescription firstConditionalStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(firstConditionalStyle)
                .labelExpression("aql:'firstConditionalStyle'")
                .labelSize(4)
                .labelColor(this.getColor(3, 3, 3));

        NodeStyleDescription secondConditionalStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(secondConditionalStyle)
                .labelExpression("aql:'secondConditionalStyle'")
                .labelSize(6)
                .bold()
                .italic()
                .underline()
                .strikeThrough()
                .labelColor(this.getColor(2, 2, 2)).iconPath(PLUGIN_ID + ICON_PATH);

        NodeStyleDescription thirdConditionalStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(thirdConditionalStyle)
                .labelExpression("aql:'thirdConditionalStyle'")
                .labelSize(8)
                .labelColor(this.getColor(4, 4, 4));

        nodeMapping.setStyle(defaultStyle);
        nodeMapping.getConditionnalStyles().add(this.createConditionalNodeStyle(EXPRESSION_FALSE, firstConditionalStyle));
        nodeMapping.getConditionnalStyles().add(this.createConditionalNodeStyle(EXPRESSION_TRUE, secondConditionalStyle));
        nodeMapping.getConditionnalStyles().add(this.createConditionalNodeStyle(EXPRESSION_TRUE, thirdConditionalStyle));

        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return UUID.randomUUID().toString();
            }
        };
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = (interpreter, domainClass, semanticCandidatesExpression, preconditionExpression) -> variableManager -> List.of();
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpreter -> modelOperation -> Optional.empty();

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        var converter = new AbstractNodeMappingConverter(new IObjectService.NoOp(), new IEditService.NoOp(), identifierProvider, semanticCandidatesProviderFactory, modelOperationHandlerSwitchProvider,
                new ImageSizeProvider());

        NodeDescription convertedNodeDescription = converter.convert(nodeMapping, interpreter, new HashMap<String, NodeDescription>());
        LabelDescription labelDescription = convertedNodeDescription.getLabelDescription();

        String text = labelDescription.getTextProvider().apply(variableManager);
        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescriptionProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean isBold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean isItalic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean isUnderline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        Boolean isStrikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        List<String> iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);

        assertThat(text).isEqualTo("secondConditionalStyle");
        assertThat(fontSize).isEqualTo(6);
        assertThat(isBold).isTrue();
        assertThat(isItalic).isTrue();
        assertThat(isUnderline).isTrue();
        assertThat(isStrikeThrough).isTrue();
        assertThat(color).isEqualTo("#020202");
        assertThat(iconURL).hasSize(1);
        assertThat(iconURL.get(0)).isEqualTo(ICON_PATH);
    }

    private FixedColor getColor(int red, int green, int blue) {
        FixedColor fixedColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createFixedColor();
        fixedColor.setRed(red);
        fixedColor.setGreen(green);
        fixedColor.setBlue(blue);
        return fixedColor;
    }

    private ConditionalContainerStyleDescription createConditionalContainerStyle(String predicateExpression, ContainerStyleDescription containerStyleDescription) {
        ConditionalContainerStyleDescription conditionalContainerStyle = DescriptionFactory.eINSTANCE.createConditionalContainerStyleDescription();
        conditionalContainerStyle.setPredicateExpression(predicateExpression);
        conditionalContainerStyle.setStyle(containerStyleDescription);
        return conditionalContainerStyle;
    }

    private ConditionalNodeStyleDescription createConditionalNodeStyle(String predicateExpression, NodeStyleDescription nodeStyleDescription) {
        ConditionalNodeStyleDescription conditionalNodeStyle = DescriptionFactory.eINSTANCE.createConditionalNodeStyleDescription();
        conditionalNodeStyle.setPredicateExpression(predicateExpression);
        conditionalNodeStyle.setStyle(nodeStyleDescription);
        return conditionalNodeStyle;
    }
}
