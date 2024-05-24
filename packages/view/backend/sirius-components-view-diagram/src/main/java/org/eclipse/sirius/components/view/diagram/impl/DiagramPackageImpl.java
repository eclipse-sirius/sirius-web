/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.CreateView;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DeleteView;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.LabelDescription;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy;
import org.eclipse.sirius.components.view.diagram.LabelTextAlign;
import org.eclipse.sirius.components.view.diagram.LayoutDirection;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeLabelStyle;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.OutsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDescription;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Style;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.diagram.ToolSection;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class DiagramPackageImpl extends EPackageImpl implements DiagramPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramElementDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass layoutStrategyDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass listLayoutStrategyDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass freeFormLayoutStrategyDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass insideLabelDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass outsideLabelDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass styleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass borderStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass insideLabelStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass outsideLabelStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeLabelStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalNodeStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalInsideLabelStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalOutsideLabelStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass rectangularNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass imageNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass iconLabelNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalEdgeStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramPaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodePaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgePaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass toolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dropToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeReconnectionToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelEditToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass sourceEdgeEndReconnectionToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass targetEdgeEndReconnectionToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass createViewEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteViewEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass selectionDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass toolSectionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramToolSectionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeToolSectionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeToolSectionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dropNodeToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum arrowStyleEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum layoutDirectionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum lineStyleEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum nodeContainmentKindEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum synchronizationPolicyEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum insideLabelPositionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum outsideLabelPositionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum labelOverflowStrategyEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum arrangeLayoutDirectionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum labelTextAlignEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#eNS_URI
     * @see #init()
     */
    private DiagramPackageImpl() {
        super(eNS_URI, DiagramFactory.eINSTANCE);
    }

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link DiagramPackage#eINSTANCE} when that field is accessed. Clients should
     * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     */
    public static DiagramPackage init() {
        if (isInited)
            return (DiagramPackage) EPackage.Registry.INSTANCE.getEPackage(DiagramPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredDiagramPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        DiagramPackageImpl theDiagramPackage = registeredDiagramPackage instanceof DiagramPackageImpl ? (DiagramPackageImpl) registeredDiagramPackage : new DiagramPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        ViewPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theDiagramPackage.createPackageContents();

        // Initialize created meta-data
        theDiagramPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theDiagramPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(DiagramPackage.eNS_URI, theDiagramPackage);
        return theDiagramPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramDescription() {
        return this.diagramDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramDescription_AutoLayout() {
        return (EAttribute) this.diagramDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_Palette() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_NodeDescriptions() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_EdgeDescriptions() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramDescription_ArrangeLayoutDirection() {
        return (EAttribute) this.diagramDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramElementDescription() {
        return this.diagramElementDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_Name() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_DomainType() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_PreconditionExpression() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_SynchronizationPolicy() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeDescription() {
        return this.nodeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_Collapsible() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_Palette() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ChildrenLayoutStrategy() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_Style() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ConditionalStyles() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ChildrenDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_BorderNodesDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ReusedChildNodeDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ReusedBorderNodeDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_UserResizable() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_DefaultWidthExpression() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_DefaultHeightExpression() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_KeepAspectRatio() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_IsCollapsedByDefaultExpression() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_InsideLabel() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_OutsideLabels() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(15);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_IsHiddenByDefaultExpression() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(16);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_IsFadedByDefaultExpression() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(17);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeDescription() {
        return this.edgeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_BeginLabelExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_CenterLabelExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_EndLabelExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_IsDomainBasedEdge() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_Palette() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_SourceNodeDescriptions() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_TargetNodeDescriptions() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_SourceNodesExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_TargetNodesExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_Style() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_ConditionalStyles() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLayoutStrategyDescription() {
        return this.layoutStrategyDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getListLayoutStrategyDescription() {
        return this.listLayoutStrategyDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListLayoutStrategyDescription_AreChildNodesDraggableExpression() {
        return (EAttribute) this.listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListLayoutStrategyDescription_TopGapExpression() {
        return (EAttribute) this.listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListLayoutStrategyDescription_BottomGapExpression() {
        return (EAttribute) this.listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListLayoutStrategyDescription_GrowableNodes() {
        return (EReference) this.listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFreeFormLayoutStrategyDescription() {
        return this.freeFormLayoutStrategyDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelDescription() {
        return this.labelDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelDescription_LabelExpression() {
        return (EAttribute) this.labelDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelDescription_OverflowStrategy() {
        return (EAttribute) this.labelDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelDescription_TextAlign() {
        return (EAttribute) this.labelDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInsideLabelDescription() {
        return this.insideLabelDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getInsideLabelDescription_Position() {
        return (EAttribute) this.insideLabelDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInsideLabelDescription_Style() {
        return (EReference) this.insideLabelDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInsideLabelDescription_ConditionalStyles() {
        return (EReference) this.insideLabelDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOutsideLabelDescription() {
        return this.outsideLabelDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOutsideLabelDescription_Position() {
        return (EAttribute) this.outsideLabelDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOutsideLabelDescription_Style() {
        return (EReference) this.outsideLabelDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOutsideLabelDescription_ConditionalStyles() {
        return (EReference) this.outsideLabelDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getStyle() {
        return this.styleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getStyle_Color() {
        return (EReference) this.styleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getBorderStyle() {
        return this.borderStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBorderStyle_BorderColor() {
        return (EReference) this.borderStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBorderStyle_BorderRadius() {
        return (EAttribute) this.borderStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBorderStyle_BorderSize() {
        return (EAttribute) this.borderStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBorderStyle_BorderLineStyle() {
        return (EAttribute) this.borderStyleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInsideLabelStyle() {
        return this.insideLabelStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getInsideLabelStyle_WithHeader() {
        return (EAttribute) this.insideLabelStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getInsideLabelStyle_DisplayHeaderSeparator() {
        return (EAttribute) this.insideLabelStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOutsideLabelStyle() {
        return this.outsideLabelStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeLabelStyle() {
        return this.nodeLabelStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeLabelStyle_LabelColor() {
        return (EReference) this.nodeLabelStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeLabelStyle_ShowIcon() {
        return (EAttribute) this.nodeLabelStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeLabelStyle_LabelIcon() {
        return (EAttribute) this.nodeLabelStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeStyleDescription() {
        return this.nodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalNodeStyle() {
        return this.conditionalNodeStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getConditionalNodeStyle_Style() {
        return (EReference) this.conditionalNodeStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalInsideLabelStyle() {
        return this.conditionalInsideLabelStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getConditionalInsideLabelStyle_Style() {
        return (EReference) this.conditionalInsideLabelStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalOutsideLabelStyle() {
        return this.conditionalOutsideLabelStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getConditionalOutsideLabelStyle_Style() {
        return (EReference) this.conditionalOutsideLabelStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRectangularNodeStyleDescription() {
        return this.rectangularNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRectangularNodeStyleDescription_Background() {
        return (EReference) this.rectangularNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getImageNodeStyleDescription() {
        return this.imageNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImageNodeStyleDescription_Shape() {
        return (EAttribute) this.imageNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImageNodeStyleDescription_PositionDependentRotation() {
        return (EAttribute) this.imageNodeStyleDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getIconLabelNodeStyleDescription() {
        return this.iconLabelNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getIconLabelNodeStyleDescription_Background() {
        return (EReference) this.iconLabelNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeStyle() {
        return this.edgeStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_LineStyle() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_SourceArrowStyle() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_TargetArrowStyle() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_EdgeWidth() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_ShowIcon() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_LabelIcon() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalEdgeStyle() {
        return this.conditionalEdgeStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramPalette() {
        return this.diagramPaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramPalette_DropTool() {
        return (EReference) this.diagramPaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramPalette_DropNodeTool() {
        return (EReference) this.diagramPaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramPalette_NodeTools() {
        return (EReference) this.diagramPaletteEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramPalette_ToolSections() {
        return (EReference) this.diagramPaletteEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodePalette() {
        return this.nodePaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_DeleteTool() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_LabelEditTool() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_DropNodeTool() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_NodeTools() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_EdgeTools() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_ToolSections() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgePalette() {
        return this.edgePaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_DeleteTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_CenterLabelEditTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_BeginLabelEditTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_EndLabelEditTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_NodeTools() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_EdgeReconnectionTools() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_ToolSections() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTool() {
        return this.toolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTool_Name() {
        return (EAttribute) this.toolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTool_PreconditionExpression() {
        return (EAttribute) this.toolEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTool_Body() {
        return (EReference) this.toolEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteTool() {
        return this.deleteToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDropTool() {
        return this.dropToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeTool() {
        return this.edgeToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeTool_TargetElementDescriptions() {
        return (EReference) this.edgeToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeTool_IconURLsExpression() {
        return (EAttribute) this.edgeToolEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeReconnectionTool() {
        return this.edgeReconnectionToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelEditTool() {
        return this.labelEditToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelEditTool_InitialDirectEditLabelExpression() {
        return (EAttribute) this.labelEditToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeTool() {
        return this.nodeToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeTool_SelectionDescription() {
        return (EReference) this.nodeToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeTool_IconURLsExpression() {
        return (EAttribute) this.nodeToolEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSourceEdgeEndReconnectionTool() {
        return this.sourceEdgeEndReconnectionToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTargetEdgeEndReconnectionTool() {
        return this.targetEdgeEndReconnectionToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCreateView() {
        return this.createViewEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_ParentViewExpression() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCreateView_ElementDescription() {
        return (EReference) this.createViewEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_SemanticElementExpression() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_VariableName() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_ContainmentKind() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteView() {
        return this.deleteViewEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDeleteView_ViewExpression() {
        return (EAttribute) this.deleteViewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSelectionDescription() {
        return this.selectionDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectionDescription_SelectionCandidatesExpression() {
        return (EAttribute) this.selectionDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectionDescription_SelectionMessage() {
        return (EAttribute) this.selectionDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getToolSection() {
        return this.toolSectionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getToolSection_Name() {
        return (EAttribute) this.toolSectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramToolSection() {
        return this.diagramToolSectionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramToolSection_NodeTools() {
        return (EReference) this.diagramToolSectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeToolSection() {
        return this.nodeToolSectionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeToolSection_NodeTools() {
        return (EReference) this.nodeToolSectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeToolSection_EdgeTools() {
        return (EReference) this.nodeToolSectionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeToolSection() {
        return this.edgeToolSectionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeToolSection_NodeTools() {
        return (EReference) this.edgeToolSectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDropNodeTool() {
        return this.dropNodeToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDropNodeTool_AcceptedNodeTypes() {
        return (EReference) this.dropNodeToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getArrowStyle() {
        return this.arrowStyleEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getLayoutDirection() {
        return this.layoutDirectionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getLineStyle() {
        return this.lineStyleEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getNodeContainmentKind() {
        return this.nodeContainmentKindEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getSynchronizationPolicy() {
        return this.synchronizationPolicyEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getInsideLabelPosition() {
        return this.insideLabelPositionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getOutsideLabelPosition() {
        return this.outsideLabelPositionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getLabelOverflowStrategy() {
        return this.labelOverflowStrategyEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getArrangeLayoutDirection() {
        return this.arrangeLayoutDirectionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getLabelTextAlign() {
        return this.labelTextAlignEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramFactory getDiagramFactory() {
        return (DiagramFactory) this.getEFactoryInstance();
    }

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.diagramDescriptionEClass = this.createEClass(DIAGRAM_DESCRIPTION);
        this.createEAttribute(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__AUTO_LAYOUT);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__PALETTE);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);
        this.createEAttribute(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION);

        this.diagramElementDescriptionEClass = this.createEClass(DIAGRAM_ELEMENT_DESCRIPTION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__NAME);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY);

        this.nodeDescriptionEClass = this.createEClass(NODE_DESCRIPTION);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__COLLAPSIBLE);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__PALETTE);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__STYLE);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__USER_RESIZABLE);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__KEEP_ASPECT_RATIO);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__INSIDE_LABEL);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__OUTSIDE_LABELS);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION);

        this.edgeDescriptionEClass = this.createEClass(EDGE_DESCRIPTION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__END_LABEL_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__PALETTE);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__STYLE);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__CONDITIONAL_STYLES);

        this.layoutStrategyDescriptionEClass = this.createEClass(LAYOUT_STRATEGY_DESCRIPTION);

        this.listLayoutStrategyDescriptionEClass = this.createEClass(LIST_LAYOUT_STRATEGY_DESCRIPTION);
        this.createEAttribute(this.listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION);
        this.createEAttribute(this.listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION);
        this.createEAttribute(this.listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION);
        this.createEReference(this.listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES);

        this.freeFormLayoutStrategyDescriptionEClass = this.createEClass(FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION);

        this.labelDescriptionEClass = this.createEClass(LABEL_DESCRIPTION);
        this.createEAttribute(this.labelDescriptionEClass, LABEL_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.labelDescriptionEClass, LABEL_DESCRIPTION__OVERFLOW_STRATEGY);
        this.createEAttribute(this.labelDescriptionEClass, LABEL_DESCRIPTION__TEXT_ALIGN);

        this.insideLabelDescriptionEClass = this.createEClass(INSIDE_LABEL_DESCRIPTION);
        this.createEAttribute(this.insideLabelDescriptionEClass, INSIDE_LABEL_DESCRIPTION__POSITION);
        this.createEReference(this.insideLabelDescriptionEClass, INSIDE_LABEL_DESCRIPTION__STYLE);
        this.createEReference(this.insideLabelDescriptionEClass, INSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES);

        this.outsideLabelDescriptionEClass = this.createEClass(OUTSIDE_LABEL_DESCRIPTION);
        this.createEAttribute(this.outsideLabelDescriptionEClass, OUTSIDE_LABEL_DESCRIPTION__POSITION);
        this.createEReference(this.outsideLabelDescriptionEClass, OUTSIDE_LABEL_DESCRIPTION__STYLE);
        this.createEReference(this.outsideLabelDescriptionEClass, OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES);

        this.styleEClass = this.createEClass(STYLE);
        this.createEReference(this.styleEClass, STYLE__COLOR);

        this.borderStyleEClass = this.createEClass(BORDER_STYLE);
        this.createEReference(this.borderStyleEClass, BORDER_STYLE__BORDER_COLOR);
        this.createEAttribute(this.borderStyleEClass, BORDER_STYLE__BORDER_RADIUS);
        this.createEAttribute(this.borderStyleEClass, BORDER_STYLE__BORDER_SIZE);
        this.createEAttribute(this.borderStyleEClass, BORDER_STYLE__BORDER_LINE_STYLE);

        this.insideLabelStyleEClass = this.createEClass(INSIDE_LABEL_STYLE);
        this.createEAttribute(this.insideLabelStyleEClass, INSIDE_LABEL_STYLE__WITH_HEADER);
        this.createEAttribute(this.insideLabelStyleEClass, INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR);

        this.outsideLabelStyleEClass = this.createEClass(OUTSIDE_LABEL_STYLE);

        this.nodeLabelStyleEClass = this.createEClass(NODE_LABEL_STYLE);
        this.createEReference(this.nodeLabelStyleEClass, NODE_LABEL_STYLE__LABEL_COLOR);
        this.createEAttribute(this.nodeLabelStyleEClass, NODE_LABEL_STYLE__SHOW_ICON);
        this.createEAttribute(this.nodeLabelStyleEClass, NODE_LABEL_STYLE__LABEL_ICON);

        this.nodeStyleDescriptionEClass = this.createEClass(NODE_STYLE_DESCRIPTION);

        this.conditionalNodeStyleEClass = this.createEClass(CONDITIONAL_NODE_STYLE);
        this.createEReference(this.conditionalNodeStyleEClass, CONDITIONAL_NODE_STYLE__STYLE);

        this.conditionalInsideLabelStyleEClass = this.createEClass(CONDITIONAL_INSIDE_LABEL_STYLE);
        this.createEReference(this.conditionalInsideLabelStyleEClass, CONDITIONAL_INSIDE_LABEL_STYLE__STYLE);

        this.conditionalOutsideLabelStyleEClass = this.createEClass(CONDITIONAL_OUTSIDE_LABEL_STYLE);
        this.createEReference(this.conditionalOutsideLabelStyleEClass, CONDITIONAL_OUTSIDE_LABEL_STYLE__STYLE);

        this.rectangularNodeStyleDescriptionEClass = this.createEClass(RECTANGULAR_NODE_STYLE_DESCRIPTION);
        this.createEReference(this.rectangularNodeStyleDescriptionEClass, RECTANGULAR_NODE_STYLE_DESCRIPTION__BACKGROUND);

        this.imageNodeStyleDescriptionEClass = this.createEClass(IMAGE_NODE_STYLE_DESCRIPTION);
        this.createEAttribute(this.imageNodeStyleDescriptionEClass, IMAGE_NODE_STYLE_DESCRIPTION__SHAPE);
        this.createEAttribute(this.imageNodeStyleDescriptionEClass, IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION);

        this.iconLabelNodeStyleDescriptionEClass = this.createEClass(ICON_LABEL_NODE_STYLE_DESCRIPTION);
        this.createEReference(this.iconLabelNodeStyleDescriptionEClass, ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND);

        this.edgeStyleEClass = this.createEClass(EDGE_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__LINE_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__SOURCE_ARROW_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__TARGET_ARROW_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__EDGE_WIDTH);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__SHOW_ICON);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__LABEL_ICON);

        this.conditionalEdgeStyleEClass = this.createEClass(CONDITIONAL_EDGE_STYLE);

        this.diagramPaletteEClass = this.createEClass(DIAGRAM_PALETTE);
        this.createEReference(this.diagramPaletteEClass, DIAGRAM_PALETTE__DROP_TOOL);
        this.createEReference(this.diagramPaletteEClass, DIAGRAM_PALETTE__DROP_NODE_TOOL);
        this.createEReference(this.diagramPaletteEClass, DIAGRAM_PALETTE__NODE_TOOLS);
        this.createEReference(this.diagramPaletteEClass, DIAGRAM_PALETTE__TOOL_SECTIONS);

        this.nodePaletteEClass = this.createEClass(NODE_PALETTE);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__DELETE_TOOL);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__LABEL_EDIT_TOOL);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__DROP_NODE_TOOL);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__NODE_TOOLS);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__EDGE_TOOLS);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__TOOL_SECTIONS);

        this.edgePaletteEClass = this.createEClass(EDGE_PALETTE);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__DELETE_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__END_LABEL_EDIT_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__NODE_TOOLS);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__EDGE_RECONNECTION_TOOLS);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__TOOL_SECTIONS);

        this.toolEClass = this.createEClass(TOOL);
        this.createEAttribute(this.toolEClass, TOOL__NAME);
        this.createEAttribute(this.toolEClass, TOOL__PRECONDITION_EXPRESSION);
        this.createEReference(this.toolEClass, TOOL__BODY);

        this.deleteToolEClass = this.createEClass(DELETE_TOOL);

        this.dropToolEClass = this.createEClass(DROP_TOOL);

        this.edgeToolEClass = this.createEClass(EDGE_TOOL);
        this.createEReference(this.edgeToolEClass, EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS);
        this.createEAttribute(this.edgeToolEClass, EDGE_TOOL__ICON_UR_LS_EXPRESSION);

        this.edgeReconnectionToolEClass = this.createEClass(EDGE_RECONNECTION_TOOL);

        this.labelEditToolEClass = this.createEClass(LABEL_EDIT_TOOL);
        this.createEAttribute(this.labelEditToolEClass, LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION);

        this.nodeToolEClass = this.createEClass(NODE_TOOL);
        this.createEReference(this.nodeToolEClass, NODE_TOOL__SELECTION_DESCRIPTION);
        this.createEAttribute(this.nodeToolEClass, NODE_TOOL__ICON_UR_LS_EXPRESSION);

        this.sourceEdgeEndReconnectionToolEClass = this.createEClass(SOURCE_EDGE_END_RECONNECTION_TOOL);

        this.targetEdgeEndReconnectionToolEClass = this.createEClass(TARGET_EDGE_END_RECONNECTION_TOOL);

        this.createViewEClass = this.createEClass(CREATE_VIEW);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__PARENT_VIEW_EXPRESSION);
        this.createEReference(this.createViewEClass, CREATE_VIEW__ELEMENT_DESCRIPTION);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__VARIABLE_NAME);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__CONTAINMENT_KIND);

        this.deleteViewEClass = this.createEClass(DELETE_VIEW);
        this.createEAttribute(this.deleteViewEClass, DELETE_VIEW__VIEW_EXPRESSION);

        this.selectionDescriptionEClass = this.createEClass(SELECTION_DESCRIPTION);
        this.createEAttribute(this.selectionDescriptionEClass, SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.selectionDescriptionEClass, SELECTION_DESCRIPTION__SELECTION_MESSAGE);

        this.toolSectionEClass = this.createEClass(TOOL_SECTION);
        this.createEAttribute(this.toolSectionEClass, TOOL_SECTION__NAME);

        this.diagramToolSectionEClass = this.createEClass(DIAGRAM_TOOL_SECTION);
        this.createEReference(this.diagramToolSectionEClass, DIAGRAM_TOOL_SECTION__NODE_TOOLS);

        this.nodeToolSectionEClass = this.createEClass(NODE_TOOL_SECTION);
        this.createEReference(this.nodeToolSectionEClass, NODE_TOOL_SECTION__NODE_TOOLS);
        this.createEReference(this.nodeToolSectionEClass, NODE_TOOL_SECTION__EDGE_TOOLS);

        this.edgeToolSectionEClass = this.createEClass(EDGE_TOOL_SECTION);
        this.createEReference(this.edgeToolSectionEClass, EDGE_TOOL_SECTION__NODE_TOOLS);

        this.dropNodeToolEClass = this.createEClass(DROP_NODE_TOOL);
        this.createEReference(this.dropNodeToolEClass, DROP_NODE_TOOL__ACCEPTED_NODE_TYPES);

        // Create enums
        this.arrowStyleEEnum = this.createEEnum(ARROW_STYLE);
        this.layoutDirectionEEnum = this.createEEnum(LAYOUT_DIRECTION);
        this.lineStyleEEnum = this.createEEnum(LINE_STYLE);
        this.nodeContainmentKindEEnum = this.createEEnum(NODE_CONTAINMENT_KIND);
        this.synchronizationPolicyEEnum = this.createEEnum(SYNCHRONIZATION_POLICY);
        this.insideLabelPositionEEnum = this.createEEnum(INSIDE_LABEL_POSITION);
        this.outsideLabelPositionEEnum = this.createEEnum(OUTSIDE_LABEL_POSITION);
        this.labelOverflowStrategyEEnum = this.createEEnum(LABEL_OVERFLOW_STRATEGY);
        this.arrangeLayoutDirectionEEnum = this.createEEnum(ARRANGE_LAYOUT_DIRECTION);
        this.labelTextAlignEEnum = this.createEEnum(LABEL_TEXT_ALIGN);
    }

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        ViewPackage theViewPackage = (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.diagramDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
        this.nodeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
        this.edgeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
        this.listLayoutStrategyDescriptionEClass.getESuperTypes().add(this.getLayoutStrategyDescription());
        this.freeFormLayoutStrategyDescriptionEClass.getESuperTypes().add(this.getLayoutStrategyDescription());
        this.insideLabelDescriptionEClass.getESuperTypes().add(this.getLabelDescription());
        this.outsideLabelDescriptionEClass.getESuperTypes().add(this.getLabelDescription());
        this.insideLabelStyleEClass.getESuperTypes().add(this.getNodeLabelStyle());
        this.outsideLabelStyleEClass.getESuperTypes().add(this.getNodeLabelStyle());
        this.nodeLabelStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.nodeStyleDescriptionEClass.getESuperTypes().add(this.getBorderStyle());
        this.conditionalNodeStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalInsideLabelStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalOutsideLabelStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.rectangularNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
        this.imageNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
        this.iconLabelNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
        this.edgeStyleEClass.getESuperTypes().add(this.getStyle());
        this.edgeStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalEdgeStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalEdgeStyleEClass.getESuperTypes().add(this.getEdgeStyle());
        this.deleteToolEClass.getESuperTypes().add(this.getTool());
        this.dropToolEClass.getESuperTypes().add(this.getTool());
        this.edgeToolEClass.getESuperTypes().add(this.getTool());
        this.edgeReconnectionToolEClass.getESuperTypes().add(this.getTool());
        this.labelEditToolEClass.getESuperTypes().add(this.getTool());
        this.nodeToolEClass.getESuperTypes().add(this.getTool());
        this.sourceEdgeEndReconnectionToolEClass.getESuperTypes().add(this.getEdgeReconnectionTool());
        this.targetEdgeEndReconnectionToolEClass.getESuperTypes().add(this.getEdgeReconnectionTool());
        this.createViewEClass.getESuperTypes().add(theViewPackage.getOperation());
        this.deleteViewEClass.getESuperTypes().add(theViewPackage.getOperation());
        this.diagramToolSectionEClass.getESuperTypes().add(this.getToolSection());
        this.nodeToolSectionEClass.getESuperTypes().add(this.getToolSection());
        this.edgeToolSectionEClass.getESuperTypes().add(this.getToolSection());
        this.dropNodeToolEClass.getESuperTypes().add(this.getTool());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.diagramDescriptionEClass, DiagramDescription.class, "DiagramDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDiagramDescription_AutoLayout(), this.ecorePackage.getEBoolean(), "autoLayout", null, 1, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramDescription_Palette(), this.getDiagramPalette(), null, "palette", null, 0, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramDescription_NodeDescriptions(), this.getNodeDescription(), null, "nodeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramDescription_EdgeDescriptions(), this.getEdgeDescription(), null, "edgeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramDescription_ArrangeLayoutDirection(), this.getArrangeLayoutDirection(), "arrangeLayoutDirection", "UNDEFINED", 1, 1, DiagramDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.diagramElementDescriptionEClass, DiagramElementDescription.class, "DiagramElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDiagramElementDescription_Name(), theViewPackage.getIdentifier(), "name", "NewRepresentationDescription", 0, 1, DiagramElementDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_DomainType(), theViewPackage.getDomainType(), "domainType", null, 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self.eContents()", 0, 1,
                DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1,
                DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_SynchronizationPolicy(), this.getSynchronizationPolicy(), "synchronizationPolicy", null, 0, 1, DiagramElementDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeDescriptionEClass, NodeDescription.class, "NodeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getNodeDescription_Collapsible(), this.ecorePackage.getEBoolean(), "collapsible", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_Palette(), this.getNodePalette(), null, "palette", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ChildrenLayoutStrategy(), this.getLayoutStrategyDescription(), null, "childrenLayoutStrategy", null, 0, 1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_Style(), this.getNodeStyleDescription(), null, "style", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ConditionalStyles(), this.getConditionalNodeStyle(), null, "conditionalStyles", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ChildrenDescriptions(), this.getNodeDescription(), null, "childrenDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_BorderNodesDescriptions(), this.getNodeDescription(), null, "borderNodesDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ReusedChildNodeDescriptions(), this.getNodeDescription(), null, "reusedChildNodeDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ReusedBorderNodeDescriptions(), this.getNodeDescription(), null, "reusedBorderNodeDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_UserResizable(), this.ecorePackage.getEBoolean(), "userResizable", "true", 1, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_DefaultWidthExpression(), theViewPackage.getInterpretedExpression(), "defaultWidthExpression", null, 0, 1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_DefaultHeightExpression(), theViewPackage.getInterpretedExpression(), "defaultHeightExpression", null, 0, 1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_KeepAspectRatio(), this.ecorePackage.getEBoolean(), "keepAspectRatio", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_IsCollapsedByDefaultExpression(), theViewPackage.getInterpretedExpression(), "isCollapsedByDefaultExpression", null, 0, 1, NodeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_InsideLabel(), this.getInsideLabelDescription(), null, "insideLabel", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_OutsideLabels(), this.getOutsideLabelDescription(), null, "outsideLabels", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_IsHiddenByDefaultExpression(), theViewPackage.getInterpretedExpression(), "isHiddenByDefaultExpression", null, 0, 1, NodeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_IsFadedByDefaultExpression(), theViewPackage.getInterpretedExpression(), "isFadedByDefaultExpression", null, 0, 1, NodeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeDescriptionEClass, EdgeDescription.class, "EdgeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getEdgeDescription_BeginLabelExpression(), theViewPackage.getInterpretedExpression(), "beginLabelExpression", "", 0, 1, EdgeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_CenterLabelExpression(), theViewPackage.getInterpretedExpression(), "centerLabelExpression", "aql:self.name", 0, 1, EdgeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_EndLabelExpression(), theViewPackage.getInterpretedExpression(), "endLabelExpression", "", 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_IsDomainBasedEdge(), this.ecorePackage.getEBoolean(), "isDomainBasedEdge", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_Palette(), this.getEdgePalette(), null, "palette", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_SourceNodeDescriptions(), this.getNodeDescription(), null, "sourceNodeDescriptions", null, 1, -1, EdgeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_TargetNodeDescriptions(), this.getNodeDescription(), null, "targetNodeDescriptions", null, 1, -1, EdgeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_SourceNodesExpression(), theViewPackage.getInterpretedExpression(), "sourceNodesExpression", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_TargetNodesExpression(), theViewPackage.getInterpretedExpression(), "targetNodesExpression", "aql:self.eCrossReferences()", 0, 1,
                EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_Style(), this.getEdgeStyle(), null, "style", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_ConditionalStyles(), this.getConditionalEdgeStyle(), null, "conditionalStyles", null, 0, -1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.layoutStrategyDescriptionEClass, LayoutStrategyDescription.class, "LayoutStrategyDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.listLayoutStrategyDescriptionEClass, ListLayoutStrategyDescription.class, "ListLayoutStrategyDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getListLayoutStrategyDescription_AreChildNodesDraggableExpression(), theViewPackage.getInterpretedExpression(), "areChildNodesDraggableExpression", "aql:true", 1, 1,
                ListLayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getListLayoutStrategyDescription_TopGapExpression(), theViewPackage.getInterpretedExpression(), "topGapExpression", "", 0, 1, ListLayoutStrategyDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getListLayoutStrategyDescription_BottomGapExpression(), theViewPackage.getInterpretedExpression(), "bottomGapExpression", "", 0, 1,
                ListLayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getListLayoutStrategyDescription_GrowableNodes(), this.getNodeDescription(), null, "growableNodes", null, 0, -1, ListLayoutStrategyDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.freeFormLayoutStrategyDescriptionEClass, FreeFormLayoutStrategyDescription.class, "FreeFormLayoutStrategyDescription", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.labelDescriptionEClass, LabelDescription.class, "LabelDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLabelDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", "aql:self.name", 0, 1, LabelDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelDescription_OverflowStrategy(), this.getLabelOverflowStrategy(), "overflowStrategy", null, 1, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelDescription_TextAlign(), this.getLabelTextAlign(), "textAlign", null, 1, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.insideLabelDescriptionEClass, InsideLabelDescription.class, "InsideLabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getInsideLabelDescription_Position(), this.getInsideLabelPosition(), "position", null, 1, 1, InsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getInsideLabelDescription_Style(), this.getInsideLabelStyle(), null, "style", null, 0, 1, InsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getInsideLabelDescription_ConditionalStyles(), this.getConditionalInsideLabelStyle(), null, "conditionalStyles", null, 0, -1, InsideLabelDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.outsideLabelDescriptionEClass, OutsideLabelDescription.class, "OutsideLabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getOutsideLabelDescription_Position(), this.getOutsideLabelPosition(), "position", null, 1, 1, OutsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getOutsideLabelDescription_Style(), this.getOutsideLabelStyle(), null, "style", null, 0, 1, OutsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getOutsideLabelDescription_ConditionalStyles(), this.getConditionalOutsideLabelStyle(), null, "conditionalStyles", null, 0, -1, OutsideLabelDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.styleEClass, Style.class, "Style", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, Style.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.borderStyleEClass, BorderStyle.class, "BorderStyle", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getBorderStyle_BorderColor(), theViewPackage.getUserColor(), null, "borderColor", null, 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBorderStyle_BorderRadius(), theViewPackage.getLength(), "borderRadius", "3", 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBorderStyle_BorderSize(), theViewPackage.getLength(), "borderSize", "1", 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBorderStyle_BorderLineStyle(), this.getLineStyle(), "borderLineStyle", null, 0, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.insideLabelStyleEClass, InsideLabelStyle.class, "InsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getInsideLabelStyle_WithHeader(), this.ecorePackage.getEBoolean(), "withHeader", null, 0, 1, InsideLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getInsideLabelStyle_DisplayHeaderSeparator(), this.ecorePackage.getEBoolean(), "displayHeaderSeparator", null, 0, 1, InsideLabelStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.outsideLabelStyleEClass, OutsideLabelStyle.class, "OutsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.nodeLabelStyleEClass, NodeLabelStyle.class, "NodeLabelStyle", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getNodeLabelStyle_LabelColor(), theViewPackage.getUserColor(), null, "labelColor", null, 1, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeLabelStyle_ShowIcon(), this.ecorePackage.getEBoolean(), "showIcon", "false", 0, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeLabelStyle_LabelIcon(), this.ecorePackage.getEString(), "labelIcon", null, 0, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeStyleDescriptionEClass, NodeStyleDescription.class, "NodeStyleDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.conditionalNodeStyleEClass, ConditionalNodeStyle.class, "ConditionalNodeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getConditionalNodeStyle_Style(), this.getNodeStyleDescription(), null, "style", null, 0, 1, ConditionalNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalInsideLabelStyleEClass, ConditionalInsideLabelStyle.class, "ConditionalInsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getConditionalInsideLabelStyle_Style(), this.getInsideLabelStyle(), null, "style", null, 0, 1, ConditionalInsideLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalOutsideLabelStyleEClass, ConditionalOutsideLabelStyle.class, "ConditionalOutsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getConditionalOutsideLabelStyle_Style(), this.getOutsideLabelStyle(), null, "style", null, 0, 1, ConditionalOutsideLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.rectangularNodeStyleDescriptionEClass, RectangularNodeStyleDescription.class, "RectangularNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRectangularNodeStyleDescription_Background(), theViewPackage.getUserColor(), null, "background", null, 0, 1, RectangularNodeStyleDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.imageNodeStyleDescriptionEClass, ImageNodeStyleDescription.class, "ImageNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getImageNodeStyleDescription_Shape(), this.ecorePackage.getEString(), "shape", null, 0, 1, ImageNodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getImageNodeStyleDescription_PositionDependentRotation(), this.ecorePackage.getEBoolean(), "positionDependentRotation", null, 0, 1, ImageNodeStyleDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.iconLabelNodeStyleDescriptionEClass, IconLabelNodeStyleDescription.class, "IconLabelNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getIconLabelNodeStyleDescription_Background(), theViewPackage.getUserColor(), null, "background", null, 0, 1, IconLabelNodeStyleDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeStyleEClass, EdgeStyle.class, "EdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getEdgeStyle_LineStyle(), this.getLineStyle(), "lineStyle", "Solid", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_SourceArrowStyle(), this.getArrowStyle(), "sourceArrowStyle", "None", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_TargetArrowStyle(), this.getArrowStyle(), "targetArrowStyle", "InputArrow", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_EdgeWidth(), theViewPackage.getLength(), "edgeWidth", "1", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_ShowIcon(), this.ecorePackage.getEBoolean(), "showIcon", "false", 0, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_LabelIcon(), this.ecorePackage.getEString(), "labelIcon", null, 0, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalEdgeStyleEClass, ConditionalEdgeStyle.class, "ConditionalEdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.diagramPaletteEClass, DiagramPalette.class, "DiagramPalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDiagramPalette_DropTool(), this.getDropTool(), null, "dropTool", null, 0, 1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramPalette_DropNodeTool(), this.getDropNodeTool(), null, "dropNodeTool", null, 0, 1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramPalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramPalette_ToolSections(), this.getDiagramToolSection(), null, "toolSections", null, 0, -1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodePaletteEClass, NodePalette.class, "NodePalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getNodePalette_DeleteTool(), this.getDeleteTool(), null, "deleteTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_LabelEditTool(), this.getLabelEditTool(), null, "labelEditTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_DropNodeTool(), this.getDropNodeTool(), null, "dropNodeTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_EdgeTools(), this.getEdgeTool(), null, "edgeTools", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_ToolSections(), this.getNodeToolSection(), null, "toolSections", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgePaletteEClass, EdgePalette.class, "EdgePalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEdgePalette_DeleteTool(), this.getDeleteTool(), null, "deleteTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_CenterLabelEditTool(), this.getLabelEditTool(), null, "centerLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_BeginLabelEditTool(), this.getLabelEditTool(), null, "beginLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_EndLabelEditTool(), this.getLabelEditTool(), null, "endLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_EdgeReconnectionTools(), this.getEdgeReconnectionTool(), null, "edgeReconnectionTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_ToolSections(), this.getEdgeToolSection(), null, "toolSections", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.toolEClass, Tool.class, "Tool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTool_Name(), theViewPackage.getIdentifier(), "name", "Tool", 1, 1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTool_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTool_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.deleteToolEClass, DeleteTool.class, "DeleteTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.dropToolEClass, DropTool.class, "DropTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.edgeToolEClass, EdgeTool.class, "EdgeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEdgeTool_TargetElementDescriptions(), this.getDiagramElementDescription(), null, "targetElementDescriptions", null, 0, -1, EdgeTool.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeTool_IconURLsExpression(), theViewPackage.getInterpretedExpression(), "iconURLsExpression", null, 0, 1, EdgeTool.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeReconnectionToolEClass, EdgeReconnectionTool.class, "EdgeReconnectionTool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.labelEditToolEClass, LabelEditTool.class, "LabelEditTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLabelEditTool_InitialDirectEditLabelExpression(), theViewPackage.getInterpretedExpression(), "initialDirectEditLabelExpression", null, 0, 1, LabelEditTool.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeToolEClass, NodeTool.class, "NodeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getNodeTool_SelectionDescription(), this.getSelectionDescription(), null, "selectionDescription", null, 0, 1, NodeTool.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeTool_IconURLsExpression(), theViewPackage.getInterpretedExpression(), "iconURLsExpression", null, 0, 1, NodeTool.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.sourceEdgeEndReconnectionToolEClass, SourceEdgeEndReconnectionTool.class, "SourceEdgeEndReconnectionTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.targetEdgeEndReconnectionToolEClass, TargetEdgeEndReconnectionTool.class, "TargetEdgeEndReconnectionTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.createViewEClass, CreateView.class, "CreateView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getCreateView_ParentViewExpression(), theViewPackage.getInterpretedExpression(), "parentViewExpression", "aql:selectedNode", 1, 1, CreateView.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCreateView_ElementDescription(), this.getDiagramElementDescription(), null, "elementDescription", null, 0, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateView_SemanticElementExpression(), theViewPackage.getInterpretedExpression(), "semanticElementExpression", "aql:self", 1, 1, CreateView.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateView_VariableName(), this.ecorePackage.getEString(), "variableName", null, 0, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateView_ContainmentKind(), this.getNodeContainmentKind(), "containmentKind", "CHILD_NODE", 1, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.deleteViewEClass, DeleteView.class, "DeleteView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDeleteView_ViewExpression(), theViewPackage.getInterpretedExpression(), "viewExpression", "aql:selectedNode", 1, 1, DeleteView.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.selectionDescriptionEClass, SelectionDescription.class, "SelectionDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getSelectionDescription_SelectionCandidatesExpression(), theViewPackage.getInterpretedExpression(), "selectionCandidatesExpression", "aql:self", 0, 1,
                SelectionDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectionDescription_SelectionMessage(), this.ecorePackage.getEString(), "selectionMessage", null, 0, 1, SelectionDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.toolSectionEClass, ToolSection.class, "ToolSection", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getToolSection_Name(), theViewPackage.getIdentifier(), "name", "Tool Section", 1, 1, ToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.diagramToolSectionEClass, DiagramToolSection.class, "DiagramToolSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDiagramToolSection_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, DiagramToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeToolSectionEClass, NodeToolSection.class, "NodeToolSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getNodeToolSection_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, NodeToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeToolSection_EdgeTools(), this.getEdgeTool(), null, "edgeTools", null, 0, -1, NodeToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeToolSectionEClass, EdgeToolSection.class, "EdgeToolSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEdgeToolSection_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, EdgeToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dropNodeToolEClass, DropNodeTool.class, "DropNodeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDropNodeTool_AcceptedNodeTypes(), this.getNodeDescription(), null, "acceptedNodeTypes", null, 0, -1, DropNodeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        this.initEEnum(this.arrowStyleEEnum, ArrowStyle.class, "ArrowStyle");
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.NONE);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.OUTPUT_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.OUTPUT_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.OUTPUT_FILL_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_FILL_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.FILL_DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.CIRCLE);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.FILL_CIRCLE);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.CROSSED_CIRCLE);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.CLOSED_ARROW_WITH_VERTICAL_BAR);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.CLOSED_ARROW_WITH_DOTS);

        this.initEEnum(this.layoutDirectionEEnum, LayoutDirection.class, "LayoutDirection");
        this.addEEnumLiteral(this.layoutDirectionEEnum, LayoutDirection.COLUMN);

        this.initEEnum(this.lineStyleEEnum, LineStyle.class, "LineStyle");
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.SOLID);
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.DASH);
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.DOT);
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.DASH_DOT);

        this.initEEnum(this.nodeContainmentKindEEnum, NodeContainmentKind.class, "NodeContainmentKind");
        this.addEEnumLiteral(this.nodeContainmentKindEEnum, NodeContainmentKind.CHILD_NODE);
        this.addEEnumLiteral(this.nodeContainmentKindEEnum, NodeContainmentKind.BORDER_NODE);

        this.initEEnum(this.synchronizationPolicyEEnum, SynchronizationPolicy.class, "SynchronizationPolicy");
        this.addEEnumLiteral(this.synchronizationPolicyEEnum, SynchronizationPolicy.SYNCHRONIZED);
        this.addEEnumLiteral(this.synchronizationPolicyEEnum, SynchronizationPolicy.UNSYNCHRONIZED);

        this.initEEnum(this.insideLabelPositionEEnum, InsideLabelPosition.class, "InsideLabelPosition");
        this.addEEnumLiteral(this.insideLabelPositionEEnum, InsideLabelPosition.TOP_CENTER);

        this.initEEnum(this.outsideLabelPositionEEnum, OutsideLabelPosition.class, "OutsideLabelPosition");
        this.addEEnumLiteral(this.outsideLabelPositionEEnum, OutsideLabelPosition.BOTTOM_CENTER);

        this.initEEnum(this.labelOverflowStrategyEEnum, LabelOverflowStrategy.class, "LabelOverflowStrategy");
        this.addEEnumLiteral(this.labelOverflowStrategyEEnum, LabelOverflowStrategy.NONE);
        this.addEEnumLiteral(this.labelOverflowStrategyEEnum, LabelOverflowStrategy.WRAP);
        this.addEEnumLiteral(this.labelOverflowStrategyEEnum, LabelOverflowStrategy.ELLIPSIS);

        this.initEEnum(this.arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.class, "ArrangeLayoutDirection");
        this.addEEnumLiteral(this.arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.UNDEFINED);
        this.addEEnumLiteral(this.arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.RIGHT);
        this.addEEnumLiteral(this.arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.DOWN);
        this.addEEnumLiteral(this.arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.LEFT);
        this.addEEnumLiteral(this.arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.UP);

        this.initEEnum(this.labelTextAlignEEnum, LabelTextAlign.class, "LabelTextAlign");
        this.addEEnumLiteral(this.labelTextAlignEEnum, LabelTextAlign.LEFT);
        this.addEEnumLiteral(this.labelTextAlignEEnum, LabelTextAlign.RIGHT);
        this.addEEnumLiteral(this.labelTextAlignEEnum, LabelTextAlign.CENTER);
        this.addEEnumLiteral(this.labelTextAlignEEnum, LabelTextAlign.JUSTIFY);

        // Create resource
        this.createResource(eNS_URI);
    }

} // DiagramPackageImpl
