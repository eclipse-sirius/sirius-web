/**
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.CreateView;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DeleteView;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LayoutDirection;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDescription;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class DiagramFactoryImpl extends EFactoryImpl implements DiagramFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static DiagramFactory init() {
        try {
            DiagramFactory theDiagramFactory = (DiagramFactory) EPackage.Registry.INSTANCE.getEFactory(DiagramPackage.eNS_URI);
            if (theDiagramFactory != null) {
                return theDiagramFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DiagramFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DiagramFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case DiagramPackage.DIAGRAM_DESCRIPTION:
                return this.createDiagramDescription();
            case DiagramPackage.NODE_DESCRIPTION:
                return this.createNodeDescription();
            case DiagramPackage.EDGE_DESCRIPTION:
                return this.createEdgeDescription();
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION:
                return this.createListLayoutStrategyDescription();
            case DiagramPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION:
                return this.createFreeFormLayoutStrategyDescription();
            case DiagramPackage.CONDITIONAL_NODE_STYLE:
                return this.createConditionalNodeStyle();
            case DiagramPackage.RECTANGULAR_NODE_STYLE_DESCRIPTION:
                return this.createRectangularNodeStyleDescription();
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION:
                return this.createImageNodeStyleDescription();
            case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION:
                return this.createIconLabelNodeStyleDescription();
            case DiagramPackage.EDGE_STYLE:
                return this.createEdgeStyle();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE:
                return this.createConditionalEdgeStyle();
            case DiagramPackage.DIAGRAM_PALETTE:
                return this.createDiagramPalette();
            case DiagramPackage.NODE_PALETTE:
                return this.createNodePalette();
            case DiagramPackage.EDGE_PALETTE:
                return this.createEdgePalette();
            case DiagramPackage.DELETE_TOOL:
                return this.createDeleteTool();
            case DiagramPackage.DROP_TOOL:
                return this.createDropTool();
            case DiagramPackage.EDGE_TOOL:
                return this.createEdgeTool();
            case DiagramPackage.LABEL_EDIT_TOOL:
                return this.createLabelEditTool();
            case DiagramPackage.NODE_TOOL:
                return this.createNodeTool();
            case DiagramPackage.SOURCE_EDGE_END_RECONNECTION_TOOL:
                return this.createSourceEdgeEndReconnectionTool();
            case DiagramPackage.TARGET_EDGE_END_RECONNECTION_TOOL:
                return this.createTargetEdgeEndReconnectionTool();
            case DiagramPackage.CREATE_VIEW:
                return this.createCreateView();
            case DiagramPackage.DELETE_VIEW:
                return this.createDeleteView();
            case DiagramPackage.SELECTION_DESCRIPTION:
                return this.createSelectionDescription();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case DiagramPackage.ARROW_STYLE:
                return this.createArrowStyleFromString(eDataType, initialValue);
            case DiagramPackage.LAYOUT_DIRECTION:
                return this.createLayoutDirectionFromString(eDataType, initialValue);
            case DiagramPackage.LINE_STYLE:
                return this.createLineStyleFromString(eDataType, initialValue);
            case DiagramPackage.NODE_CONTAINMENT_KIND:
                return this.createNodeContainmentKindFromString(eDataType, initialValue);
            case DiagramPackage.SYNCHRONIZATION_POLICY:
                return this.createSynchronizationPolicyFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case DiagramPackage.ARROW_STYLE:
                return this.convertArrowStyleToString(eDataType, instanceValue);
            case DiagramPackage.LAYOUT_DIRECTION:
                return this.convertLayoutDirectionToString(eDataType, instanceValue);
            case DiagramPackage.LINE_STYLE:
                return this.convertLineStyleToString(eDataType, instanceValue);
            case DiagramPackage.NODE_CONTAINMENT_KIND:
                return this.convertNodeContainmentKindToString(eDataType, instanceValue);
            case DiagramPackage.SYNCHRONIZATION_POLICY:
                return this.convertSynchronizationPolicyToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramDescription createDiagramDescription() {
        DiagramDescriptionImpl diagramDescription = new DiagramDescriptionImpl();
        return diagramDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeDescription createNodeDescription() {
        NodeDescriptionImpl nodeDescription = new NodeDescriptionImpl();
        return nodeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EdgeDescription createEdgeDescription() {
        EdgeDescriptionImpl edgeDescription = new EdgeDescriptionImpl();
        return edgeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListLayoutStrategyDescription createListLayoutStrategyDescription() {
        ListLayoutStrategyDescriptionImpl listLayoutStrategyDescription = new ListLayoutStrategyDescriptionImpl();
        return listLayoutStrategyDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FreeFormLayoutStrategyDescription createFreeFormLayoutStrategyDescription() {
        FreeFormLayoutStrategyDescriptionImpl freeFormLayoutStrategyDescription = new FreeFormLayoutStrategyDescriptionImpl();
        return freeFormLayoutStrategyDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalNodeStyle createConditionalNodeStyle() {
        ConditionalNodeStyleImpl conditionalNodeStyle = new ConditionalNodeStyleImpl();
        return conditionalNodeStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RectangularNodeStyleDescription createRectangularNodeStyleDescription() {
        RectangularNodeStyleDescriptionImpl rectangularNodeStyleDescription = new RectangularNodeStyleDescriptionImpl();
        return rectangularNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ImageNodeStyleDescription createImageNodeStyleDescription() {
        ImageNodeStyleDescriptionImpl imageNodeStyleDescription = new ImageNodeStyleDescriptionImpl();
        return imageNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public IconLabelNodeStyleDescription createIconLabelNodeStyleDescription() {
        IconLabelNodeStyleDescriptionImpl iconLabelNodeStyleDescription = new IconLabelNodeStyleDescriptionImpl();
        return iconLabelNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EdgeStyle createEdgeStyle() {
        EdgeStyleImpl edgeStyle = new EdgeStyleImpl();
        return edgeStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalEdgeStyle createConditionalEdgeStyle() {
        ConditionalEdgeStyleImpl conditionalEdgeStyle = new ConditionalEdgeStyleImpl();
        return conditionalEdgeStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramPalette createDiagramPalette() {
        DiagramPaletteImpl diagramPalette = new DiagramPaletteImpl();
        return diagramPalette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodePalette createNodePalette() {
        NodePaletteImpl nodePalette = new NodePaletteImpl();
        return nodePalette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EdgePalette createEdgePalette() {
        EdgePaletteImpl edgePalette = new EdgePaletteImpl();
        return edgePalette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteTool createDeleteTool() {
        DeleteToolImpl deleteTool = new DeleteToolImpl();
        return deleteTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DropTool createDropTool() {
        DropToolImpl dropTool = new DropToolImpl();
        return dropTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EdgeTool createEdgeTool() {
        EdgeToolImpl edgeTool = new EdgeToolImpl();
        return edgeTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelEditTool createLabelEditTool() {
        LabelEditToolImpl labelEditTool = new LabelEditToolImpl();
        return labelEditTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeTool createNodeTool() {
        NodeToolImpl nodeTool = new NodeToolImpl();
        return nodeTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SourceEdgeEndReconnectionTool createSourceEdgeEndReconnectionTool() {
        SourceEdgeEndReconnectionToolImpl sourceEdgeEndReconnectionTool = new SourceEdgeEndReconnectionToolImpl();
        return sourceEdgeEndReconnectionTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TargetEdgeEndReconnectionTool createTargetEdgeEndReconnectionTool() {
        TargetEdgeEndReconnectionToolImpl targetEdgeEndReconnectionTool = new TargetEdgeEndReconnectionToolImpl();
        return targetEdgeEndReconnectionTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateView createCreateView() {
        CreateViewImpl createView = new CreateViewImpl();
        return createView;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteView createDeleteView() {
        DeleteViewImpl deleteView = new DeleteViewImpl();
        return deleteView;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectionDescription createSelectionDescription() {
        SelectionDescriptionImpl selectionDescription = new SelectionDescriptionImpl();
        return selectionDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ArrowStyle createArrowStyleFromString(EDataType eDataType, String initialValue) {
        ArrowStyle result = ArrowStyle.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertArrowStyleToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public LayoutDirection createLayoutDirectionFromString(EDataType eDataType, String initialValue) {
        LayoutDirection result = LayoutDirection.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertLayoutDirectionToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public LineStyle createLineStyleFromString(EDataType eDataType, String initialValue) {
        LineStyle result = LineStyle.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertLineStyleToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NodeContainmentKind createNodeContainmentKindFromString(EDataType eDataType, String initialValue) {
        NodeContainmentKind result = NodeContainmentKind.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertNodeContainmentKindToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SynchronizationPolicy createSynchronizationPolicyFromString(EDataType eDataType, String initialValue) {
        SynchronizationPolicy result = SynchronizationPolicy.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertSynchronizationPolicyToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramPackage getDiagramPackage() {
        return (DiagramPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DiagramPackage getPackage() {
        return DiagramPackage.eINSTANCE;
    }

} // DiagramFactoryImpl
