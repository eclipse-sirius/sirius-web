/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.compat.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription;
import org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.compat.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Unit tests used to validate the proper retrieval of the conditional styles.
 *
 * @author arichard
 */
public class MappingConverterTests {

    private static final String EXPRESSION_FALSE = "aql:false"; //$NON-NLS-1$

    private static final String EXPRESSION_TRUE = "aql:true"; //$NON-NLS-1$

    private static final String PLUGIN_ID = "my.sirius.plugin"; //$NON-NLS-1$

    private static final String ICON_PATH = "/my/icon/path/MyIcon.gif"; //$NON-NLS-1$

    @Test
    public void testContainerStylePropertiesFromConditionalStyle() {
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        String mappingName = "TestMapping"; //$NON-NLS-1$
        containerMapping.setName(mappingName);

        // @formatter:off
        ContainerStyleDescription defaultStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(defaultStyle)
                .labelExpression("aql: defaultStyle") //$NON-NLS-1$
                .labelSize(10)
                .labelColor(this.getColor(1, 1, 1));

        ContainerStyleDescription firstConditionalStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(firstConditionalStyle)
                .labelExpression("aql:'firstConditionalStyle'") //$NON-NLS-1$
                .labelSize(4)
                .labelColor(this.getColor(3, 3, 3));

        ContainerStyleDescription secondConditionalStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(secondConditionalStyle)
                .labelExpression("aql:'secondConditionalStyle'") //$NON-NLS-1$
                .labelSize(6)
                .bold()
                .italic()
                .underline()
                .strikeThrough()
                .labelColor(this.getColor(2, 2, 2))
                .iconPath(PLUGIN_ID + ICON_PATH);

        ContainerStyleDescription thirdConditionalStyle = StyleFactory.eINSTANCE.createFlatContainerStyleDescription();
        new BasicLabelStyleDescriptionPopulator(thirdConditionalStyle)
                .labelExpression("aql:'thirdConditionalStyle'") //$NON-NLS-1$
                .labelSize(8)
                .labelColor(this.getColor(4, 4, 4));
        // @formatter:on

        containerMapping.setStyle(defaultStyle);
        containerMapping.getConditionnalStyles().add(this.createConditionalContainerStyle(EXPRESSION_FALSE, firstConditionalStyle));
        containerMapping.getConditionnalStyles().add(this.createConditionalContainerStyle(EXPRESSION_TRUE, secondConditionalStyle));
        containerMapping.getConditionnalStyles().add(this.createConditionalContainerStyle(EXPRESSION_TRUE, thirdConditionalStyle));

        IIdentifierProvider identifierProvider = element -> UUID.randomUUID().toString();
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = (interpreter, domainClass, semanticCandidatesExpression, preconditionExpression) -> variableManager -> List.of();
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpreter -> modelOperation -> Optional.empty();

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        var converter = new AbstractNodeMappingConverter(new NoOpObjectService(), new NoOpEditService(), identifierProvider, semanticCandidatesProviderFactory, modelOperationHandlerSwitchProvider);

        NodeDescription convertedNodeDescription = converter.convert(containerMapping, interpreter, new HashMap<UUID, NodeDescription>());
        LabelDescription labelDescription = convertedNodeDescription.getLabelDescription();

        String text = labelDescription.getTextProvider().apply(variableManager);
        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescriptionProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean isBold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean isItalic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean isUnderline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        Boolean isStrikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        String iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);

        assertThat(text).isEqualTo("secondConditionalStyle"); //$NON-NLS-1$
        assertThat(fontSize).isEqualTo(6);
        assertThat(isBold).isTrue();
        assertThat(isItalic).isTrue();
        assertThat(isUnderline).isTrue();
        assertThat(isStrikeThrough).isTrue();
        assertThat(color).isEqualTo("#020202"); //$NON-NLS-1$
        assertThat(iconURL).isEqualTo(ICON_PATH);
    }

    @Test
    public void testNodeStylePropertiesFromConditionalStyle() {
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        String mappingName = "TestMapping"; //$NON-NLS-1$
        nodeMapping.setName(mappingName);

        // @formatter:off
        NodeStyleDescription defaultStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(defaultStyle)
                .labelExpression("aql:'defaultStyle'") //$NON-NLS-1$
                .labelSize(10)
                .labelColor(this.getColor(1, 1, 1));

        NodeStyleDescription firstConditionalStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(firstConditionalStyle)
                .labelExpression("aql:'firstConditionalStyle'") //$NON-NLS-1$
                .labelSize(4)
                .labelColor(this.getColor(3, 3, 3));

        NodeStyleDescription secondConditionalStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(secondConditionalStyle)
                .labelExpression("aql:'secondConditionalStyle'") //$NON-NLS-1$
                .labelSize(6)
                .bold()
                .italic()
                .underline()
                .strikeThrough()
                .labelColor(this.getColor(2, 2, 2))
                .iconPath(PLUGIN_ID + ICON_PATH);

        NodeStyleDescription thirdConditionalStyle = StyleFactory.eINSTANCE.createSquareDescription();
        new BasicLabelStyleDescriptionPopulator(thirdConditionalStyle)
                .labelExpression("aql:'thirdConditionalStyle'") //$NON-NLS-1$
                .labelSize(8)
                .labelColor(this.getColor(4, 4, 4));
        // @formatter:on

        nodeMapping.setStyle(defaultStyle);
        nodeMapping.getConditionnalStyles().add(this.createConditionalNodeStyle(EXPRESSION_FALSE, firstConditionalStyle));
        nodeMapping.getConditionnalStyles().add(this.createConditionalNodeStyle(EXPRESSION_TRUE, secondConditionalStyle));
        nodeMapping.getConditionnalStyles().add(this.createConditionalNodeStyle(EXPRESSION_TRUE, thirdConditionalStyle));

        IIdentifierProvider identifierProvider = element -> UUID.randomUUID().toString();
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = (interpreter, domainClass, semanticCandidatesExpression, preconditionExpression) -> variableManager -> List.of();
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpreter -> modelOperation -> Optional.empty();

        VariableManager variableManager = new VariableManager();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        var converter = new AbstractNodeMappingConverter(new NoOpObjectService(), new NoOpEditService(), identifierProvider, semanticCandidatesProviderFactory, modelOperationHandlerSwitchProvider);

        NodeDescription convertedNodeDescription = converter.convert(nodeMapping, interpreter, new HashMap<UUID, NodeDescription>());
        LabelDescription labelDescription = convertedNodeDescription.getLabelDescription();

        String text = labelDescription.getTextProvider().apply(variableManager);
        LabelStyleDescription labelStyleDescription = labelDescription.getStyleDescriptionProvider().apply(variableManager);
        Integer fontSize = labelStyleDescription.getFontSizeProvider().apply(variableManager);
        Boolean isBold = labelStyleDescription.getBoldProvider().apply(variableManager);
        Boolean isItalic = labelStyleDescription.getItalicProvider().apply(variableManager);
        Boolean isUnderline = labelStyleDescription.getUnderlineProvider().apply(variableManager);
        Boolean isStrikeThrough = labelStyleDescription.getStrikeThroughProvider().apply(variableManager);
        String color = labelStyleDescription.getColorProvider().apply(variableManager);
        String iconURL = labelStyleDescription.getIconURLProvider().apply(variableManager);

        assertThat(text).isEqualTo("secondConditionalStyle"); //$NON-NLS-1$
        assertThat(fontSize).isEqualTo(6);
        assertThat(isBold).isTrue();
        assertThat(isItalic).isTrue();
        assertThat(isUnderline).isTrue();
        assertThat(isStrikeThrough).isTrue();
        assertThat(color).isEqualTo("#020202"); //$NON-NLS-1$
        assertThat(iconURL).isEqualTo(ICON_PATH);
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
