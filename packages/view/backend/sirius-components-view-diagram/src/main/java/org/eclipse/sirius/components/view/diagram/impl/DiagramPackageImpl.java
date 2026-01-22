/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import org.eclipse.sirius.components.view.diagram.Action;
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
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.EdgeType;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.GroupPalette;
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
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
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Style;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.diagram.ToolSection;
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class DiagramPackageImpl extends EPackageImpl implements DiagramPackage {

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private static boolean isInited = false;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass diagramDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass diagramElementDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass nodeDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass edgeDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass layoutStrategyDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass listLayoutStrategyDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass freeFormLayoutStrategyDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass labelDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass insideLabelDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass outsideLabelDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass styleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass borderStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass insideLabelStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass outsideLabelStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass nodeLabelStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass nodeStyleDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass conditionalNodeStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass conditionalInsideLabelStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass conditionalOutsideLabelStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass rectangularNodeStyleDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass imageNodeStyleDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass iconLabelNodeStyleDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass edgeStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass conditionalEdgeStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass diagramPaletteEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass groupPaletteEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass nodePaletteEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass edgePaletteEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass toolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deleteToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass dropToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass edgeToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass edgeReconnectionToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass labelEditToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass nodeToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass sourceEdgeEndReconnectionToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass targetEdgeEndReconnectionToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass createViewEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deleteViewEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass selectionDialogDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass toolSectionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass diagramToolSectionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass nodeToolSectionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass edgeToolSectionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass dropNodeToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass dialogDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass selectionDialogTreeDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass actionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum arrowStyleEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum layoutDirectionEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum lineStyleEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum nodeContainmentKindEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum synchronizationPolicyEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum insideLabelPositionEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum outsideLabelPositionEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum labelOverflowStrategyEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum arrangeLayoutDirectionEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum labelTextAlignEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum userResizableDirectionEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum headerSeparatorDisplayModeEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum edgeTypeEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isCreated = false;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <p>This method is used to initialize {@link DiagramPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static DiagramPackage init() {
		if (isInited) return (DiagramPackage)EPackage.Registry.INSTANCE.getEPackage(DiagramPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDiagramPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DiagramPackageImpl theDiagramPackage = registeredDiagramPackage instanceof DiagramPackageImpl ? (DiagramPackageImpl)registeredDiagramPackage : new DiagramPackageImpl();

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
	 * @generated
	 */
    @Override
    public EClass getDiagramDescription() {
		return diagramDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDiagramDescription_AutoLayout() {
		return (EAttribute)diagramDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramDescription_Palette() {
		return (EReference)diagramDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramDescription_NodeDescriptions() {
		return (EReference)diagramDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramDescription_EdgeDescriptions() {
		return (EReference)diagramDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDiagramDescription_ArrangeLayoutDirection() {
		return (EAttribute)diagramDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramDescription_GroupPalette() {
		return (EReference)diagramDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDiagramElementDescription() {
		return diagramElementDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDiagramElementDescription_Name() {
		return (EAttribute)diagramElementDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDiagramElementDescription_DomainType() {
		return (EAttribute)diagramElementDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDiagramElementDescription_SemanticCandidatesExpression() {
		return (EAttribute)diagramElementDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDiagramElementDescription_PreconditionExpression() {
		return (EAttribute)diagramElementDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDiagramElementDescription_SynchronizationPolicy() {
		return (EAttribute)diagramElementDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getNodeDescription() {
		return nodeDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_Collapsible() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_Palette() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_Actions() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_Style() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_ConditionalStyles() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_ChildrenDescriptions() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_BorderNodesDescriptions() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_ReusedChildNodeDescriptions() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_ReusedBorderNodeDescriptions() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_UserResizable() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_DefaultWidthExpression() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(10);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_DefaultHeightExpression() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(11);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_KeepAspectRatio() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(12);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_IsCollapsedByDefaultExpression() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(13);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_InsideLabel() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(14);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeDescription_OutsideLabels() {
		return (EReference)nodeDescriptionEClass.getEStructuralFeatures().get(15);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_IsHiddenByDefaultExpression() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(16);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeDescription_IsFadedByDefaultExpression() {
		return (EAttribute)nodeDescriptionEClass.getEStructuralFeatures().get(17);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEdgeDescription() {
		return edgeDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeDescription_BeginLabelExpression() {
		return (EAttribute)edgeDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeDescription_CenterLabelExpression() {
		return (EAttribute)edgeDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeDescription_EndLabelExpression() {
		return (EAttribute)edgeDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeDescription_IsDomainBasedEdge() {
		return (EAttribute)edgeDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeDescription_Palette() {
		return (EReference)edgeDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeDescription_SourceDescriptions() {
		return (EReference)edgeDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeDescription_TargetDescriptions() {
		return (EReference)edgeDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeDescription_SourceExpression() {
		return (EAttribute)edgeDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeDescription_TargetExpression() {
		return (EAttribute)edgeDescriptionEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeDescription_Style() {
		return (EReference)edgeDescriptionEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeDescription_ConditionalStyles() {
		return (EReference)edgeDescriptionEClass.getEStructuralFeatures().get(10);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getLayoutStrategyDescription() {
		return layoutStrategyDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLayoutStrategyDescription_OnWestAtCreationBorderNodes() {
		return (EReference)layoutStrategyDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLayoutStrategyDescription_OnEastAtCreationBorderNodes() {
		return (EReference)layoutStrategyDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLayoutStrategyDescription_OnSouthAtCreationBorderNodes() {
		return (EReference)layoutStrategyDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLayoutStrategyDescription_OnNorthAtCreationBorderNodes() {
		return (EReference)layoutStrategyDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getListLayoutStrategyDescription() {
		return listLayoutStrategyDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getListLayoutStrategyDescription_AreChildNodesDraggableExpression() {
		return (EAttribute)listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getListLayoutStrategyDescription_TopGapExpression() {
		return (EAttribute)listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getListLayoutStrategyDescription_BottomGapExpression() {
		return (EAttribute)listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getListLayoutStrategyDescription_GrowableNodes() {
		return (EReference)listLayoutStrategyDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getFreeFormLayoutStrategyDescription() {
		return freeFormLayoutStrategyDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getLabelDescription() {
		return labelDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getLabelDescription_LabelExpression() {
		return (EAttribute)labelDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getLabelDescription_OverflowStrategy() {
		return (EAttribute)labelDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getLabelDescription_TextAlign() {
		return (EAttribute)labelDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getInsideLabelDescription() {
		return insideLabelDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getInsideLabelDescription_Position() {
		return (EAttribute)insideLabelDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getInsideLabelDescription_Style() {
		return (EReference)insideLabelDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getInsideLabelDescription_ConditionalStyles() {
		return (EReference)insideLabelDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getOutsideLabelDescription() {
		return outsideLabelDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getOutsideLabelDescription_Position() {
		return (EAttribute)outsideLabelDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getOutsideLabelDescription_Style() {
		return (EReference)outsideLabelDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getOutsideLabelDescription_ConditionalStyles() {
		return (EReference)outsideLabelDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getStyle() {
		return styleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getStyle_Color() {
		return (EReference)styleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getBorderStyle() {
		return borderStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getBorderStyle_BorderColor() {
		return (EReference)borderStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getBorderStyle_BorderRadius() {
		return (EAttribute)borderStyleEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getBorderStyle_BorderSize() {
		return (EAttribute)borderStyleEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getBorderStyle_BorderLineStyle() {
		return (EAttribute)borderStyleEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getInsideLabelStyle() {
		return insideLabelStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getInsideLabelStyle_WithHeader() {
		return (EAttribute)insideLabelStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getInsideLabelStyle_HeaderSeparatorDisplayMode() {
		return (EAttribute)insideLabelStyleEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getOutsideLabelStyle() {
		return outsideLabelStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getNodeLabelStyle() {
		return nodeLabelStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeLabelStyle_LabelColor() {
		return (EReference)nodeLabelStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeLabelStyle_LabelIcon() {
		return (EAttribute)nodeLabelStyleEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeLabelStyle_MaxWidthExpression() {
		return (EAttribute)nodeLabelStyleEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeLabelStyle_Background() {
		return (EReference)nodeLabelStyleEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeLabelStyle_ShowIconExpression() {
		return (EAttribute)nodeLabelStyleEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getNodeStyleDescription() {
		return nodeStyleDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeStyleDescription_ChildrenLayoutStrategy() {
		return (EReference)nodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getConditionalNodeStyle() {
		return conditionalNodeStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getConditionalNodeStyle_Style() {
		return (EReference)conditionalNodeStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getConditionalInsideLabelStyle() {
		return conditionalInsideLabelStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getConditionalInsideLabelStyle_Style() {
		return (EReference)conditionalInsideLabelStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getConditionalOutsideLabelStyle() {
		return conditionalOutsideLabelStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getConditionalOutsideLabelStyle_Style() {
		return (EReference)conditionalOutsideLabelStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getRectangularNodeStyleDescription() {
		return rectangularNodeStyleDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getRectangularNodeStyleDescription_Background() {
		return (EReference)rectangularNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getImageNodeStyleDescription() {
		return imageNodeStyleDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getImageNodeStyleDescription_Shape() {
		return (EAttribute)imageNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getImageNodeStyleDescription_PositionDependentRotation() {
		return (EAttribute)imageNodeStyleDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getIconLabelNodeStyleDescription() {
		return iconLabelNodeStyleDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getIconLabelNodeStyleDescription_Background() {
		return (EReference)iconLabelNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEdgeStyle() {
		return edgeStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_LineStyle() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_SourceArrowStyle() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_TargetArrowStyle() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_EdgeWidth() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_ShowIcon() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_LabelIcon() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeStyle_Background() {
		return (EReference)edgeStyleEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_MaxWidthExpression() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeStyle_EdgeType() {
		return (EAttribute)edgeStyleEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getConditionalEdgeStyle() {
		return conditionalEdgeStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDiagramPalette() {
		return diagramPaletteEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramPalette_DropTool() {
		return (EReference)diagramPaletteEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramPalette_DropNodeTool() {
		return (EReference)diagramPaletteEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramPalette_NodeTools() {
		return (EReference)diagramPaletteEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramPalette_QuickAccessTools() {
		return (EReference)diagramPaletteEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramPalette_ToolSections() {
		return (EReference)diagramPaletteEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getGroupPalette() {
		return groupPaletteEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGroupPalette_NodeTools() {
		return (EReference)groupPaletteEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGroupPalette_QuickAccessTools() {
		return (EReference)groupPaletteEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getGroupPalette_ToolSections() {
		return (EReference)groupPaletteEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getNodePalette() {
		return nodePaletteEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodePalette_DeleteTool() {
		return (EReference)nodePaletteEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodePalette_LabelEditTool() {
		return (EReference)nodePaletteEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodePalette_DropNodeTool() {
		return (EReference)nodePaletteEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodePalette_NodeTools() {
		return (EReference)nodePaletteEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodePalette_QuickAccessTools() {
		return (EReference)nodePaletteEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodePalette_EdgeTools() {
		return (EReference)nodePaletteEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodePalette_ToolSections() {
		return (EReference)nodePaletteEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEdgePalette() {
		return edgePaletteEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_DeleteTool() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_CenterLabelEditTool() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_BeginLabelEditTool() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_EndLabelEditTool() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_NodeTools() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_QuickAccessTools() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_EdgeReconnectionTools() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_EdgeTools() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgePalette_ToolSections() {
		return (EReference)edgePaletteEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTool() {
		return toolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTool_Name() {
		return (EAttribute)toolEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTool_PreconditionExpression() {
		return (EAttribute)toolEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTool_Body() {
		return (EReference)toolEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeleteTool() {
		return deleteToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDropTool() {
		return dropToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEdgeTool() {
		return edgeToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeTool_TargetElementDescriptions() {
		return (EReference)edgeToolEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeTool_IconURLsExpression() {
		return (EAttribute)edgeToolEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeTool_DialogDescription() {
		return (EReference)edgeToolEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getEdgeTool_ElementsToSelectExpression() {
		return (EAttribute)edgeToolEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEdgeReconnectionTool() {
		return edgeReconnectionToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getLabelEditTool() {
		return labelEditToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getLabelEditTool_InitialDirectEditLabelExpression() {
		return (EAttribute)labelEditToolEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getNodeTool() {
		return nodeToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeTool_DialogDescription() {
		return (EReference)nodeToolEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeTool_IconURLsExpression() {
		return (EAttribute)nodeToolEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeTool_ElementsToSelectExpression() {
		return (EAttribute)nodeToolEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getNodeTool_WithImpactAnalysis() {
		return (EAttribute)nodeToolEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getSourceEdgeEndReconnectionTool() {
		return sourceEdgeEndReconnectionToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTargetEdgeEndReconnectionTool() {
		return targetEdgeEndReconnectionToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCreateView() {
		return createViewEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCreateView_ParentViewExpression() {
		return (EAttribute)createViewEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getCreateView_ElementDescription() {
		return (EReference)createViewEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCreateView_SemanticElementExpression() {
		return (EAttribute)createViewEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCreateView_VariableName() {
		return (EAttribute)createViewEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCreateView_ContainmentKind() {
		return (EAttribute)createViewEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeleteView() {
		return deleteViewEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDeleteView_ViewExpression() {
		return (EAttribute)deleteViewEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getSelectionDialogDescription() {
		return selectionDialogDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSelectionDialogDescription_SelectionMessage() {
		return (EAttribute)selectionDialogDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getSelectionDialogDescription_SelectionDialogTreeDescription() {
		return (EReference)selectionDialogDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSelectionDialogDescription_Multiple() {
		return (EAttribute)selectionDialogDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getToolSection() {
		return toolSectionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getToolSection_Name() {
		return (EAttribute)toolSectionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDiagramToolSection() {
		return diagramToolSectionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDiagramToolSection_NodeTools() {
		return (EReference)diagramToolSectionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getNodeToolSection() {
		return nodeToolSectionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeToolSection_NodeTools() {
		return (EReference)nodeToolSectionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getNodeToolSection_EdgeTools() {
		return (EReference)nodeToolSectionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEdgeToolSection() {
		return edgeToolSectionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getEdgeToolSection_NodeTools() {
		return (EReference)edgeToolSectionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDropNodeTool() {
		return dropNodeToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDropNodeTool_AcceptedNodeTypes() {
		return (EReference)dropNodeToolEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDialogDescription() {
		return dialogDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getSelectionDialogTreeDescription() {
		return selectionDialogTreeDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSelectionDialogTreeDescription_ElementsExpression() {
		return (EAttribute)selectionDialogTreeDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSelectionDialogTreeDescription_ChildrenExpression() {
		return (EAttribute)selectionDialogTreeDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSelectionDialogTreeDescription_IsSelectableExpression() {
		return (EAttribute)selectionDialogTreeDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getAction() {
		return actionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getAction_Name() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getAction_TooltipExpression() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getAction_IconURLsExpression() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getAction_PreconditionExpression() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getAction_Body() {
		return (EReference)actionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getArrowStyle() {
		return arrowStyleEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getLayoutDirection() {
		return layoutDirectionEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getLineStyle() {
		return lineStyleEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getNodeContainmentKind() {
		return nodeContainmentKindEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getSynchronizationPolicy() {
		return synchronizationPolicyEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getInsideLabelPosition() {
		return insideLabelPositionEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getOutsideLabelPosition() {
		return outsideLabelPositionEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getLabelOverflowStrategy() {
		return labelOverflowStrategyEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getArrangeLayoutDirection() {
		return arrangeLayoutDirectionEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getLabelTextAlign() {
		return labelTextAlignEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getUserResizableDirection() {
		return userResizableDirectionEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getHeaderSeparatorDisplayMode() {
		return headerSeparatorDisplayModeEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getEdgeType() {
		return edgeTypeEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DiagramFactory getDiagramFactory() {
		return (DiagramFactory)getEFactoryInstance();
	}

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		diagramDescriptionEClass = createEClass(DIAGRAM_DESCRIPTION);
		createEAttribute(diagramDescriptionEClass, DIAGRAM_DESCRIPTION__AUTO_LAYOUT);
		createEReference(diagramDescriptionEClass, DIAGRAM_DESCRIPTION__PALETTE);
		createEReference(diagramDescriptionEClass, DIAGRAM_DESCRIPTION__GROUP_PALETTE);
		createEReference(diagramDescriptionEClass, DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
		createEReference(diagramDescriptionEClass, DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);
		createEAttribute(diagramDescriptionEClass, DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION);

		diagramElementDescriptionEClass = createEClass(DIAGRAM_ELEMENT_DESCRIPTION);
		createEAttribute(diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__NAME);
		createEAttribute(diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE);
		createEAttribute(diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
		createEAttribute(diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION);
		createEAttribute(diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY);

		nodeDescriptionEClass = createEClass(NODE_DESCRIPTION);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__COLLAPSIBLE);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__PALETTE);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__ACTIONS);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__STYLE);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__CONDITIONAL_STYLES);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__USER_RESIZABLE);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__KEEP_ASPECT_RATIO);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__INSIDE_LABEL);
		createEReference(nodeDescriptionEClass, NODE_DESCRIPTION__OUTSIDE_LABELS);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION);
		createEAttribute(nodeDescriptionEClass, NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION);

		edgeDescriptionEClass = createEClass(EDGE_DESCRIPTION);
		createEAttribute(edgeDescriptionEClass, EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION);
		createEAttribute(edgeDescriptionEClass, EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION);
		createEAttribute(edgeDescriptionEClass, EDGE_DESCRIPTION__END_LABEL_EXPRESSION);
		createEAttribute(edgeDescriptionEClass, EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE);
		createEReference(edgeDescriptionEClass, EDGE_DESCRIPTION__PALETTE);
		createEReference(edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_DESCRIPTIONS);
		createEReference(edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_DESCRIPTIONS);
		createEAttribute(edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_EXPRESSION);
		createEAttribute(edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_EXPRESSION);
		createEReference(edgeDescriptionEClass, EDGE_DESCRIPTION__STYLE);
		createEReference(edgeDescriptionEClass, EDGE_DESCRIPTION__CONDITIONAL_STYLES);

		layoutStrategyDescriptionEClass = createEClass(LAYOUT_STRATEGY_DESCRIPTION);
		createEReference(layoutStrategyDescriptionEClass, LAYOUT_STRATEGY_DESCRIPTION__ON_WEST_AT_CREATION_BORDER_NODES);
		createEReference(layoutStrategyDescriptionEClass, LAYOUT_STRATEGY_DESCRIPTION__ON_EAST_AT_CREATION_BORDER_NODES);
		createEReference(layoutStrategyDescriptionEClass, LAYOUT_STRATEGY_DESCRIPTION__ON_SOUTH_AT_CREATION_BORDER_NODES);
		createEReference(layoutStrategyDescriptionEClass, LAYOUT_STRATEGY_DESCRIPTION__ON_NORTH_AT_CREATION_BORDER_NODES);

		listLayoutStrategyDescriptionEClass = createEClass(LIST_LAYOUT_STRATEGY_DESCRIPTION);
		createEAttribute(listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION);
		createEAttribute(listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION);
		createEAttribute(listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION);
		createEReference(listLayoutStrategyDescriptionEClass, LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES);

		freeFormLayoutStrategyDescriptionEClass = createEClass(FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION);

		labelDescriptionEClass = createEClass(LABEL_DESCRIPTION);
		createEAttribute(labelDescriptionEClass, LABEL_DESCRIPTION__LABEL_EXPRESSION);
		createEAttribute(labelDescriptionEClass, LABEL_DESCRIPTION__OVERFLOW_STRATEGY);
		createEAttribute(labelDescriptionEClass, LABEL_DESCRIPTION__TEXT_ALIGN);

		insideLabelDescriptionEClass = createEClass(INSIDE_LABEL_DESCRIPTION);
		createEAttribute(insideLabelDescriptionEClass, INSIDE_LABEL_DESCRIPTION__POSITION);
		createEReference(insideLabelDescriptionEClass, INSIDE_LABEL_DESCRIPTION__STYLE);
		createEReference(insideLabelDescriptionEClass, INSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES);

		outsideLabelDescriptionEClass = createEClass(OUTSIDE_LABEL_DESCRIPTION);
		createEAttribute(outsideLabelDescriptionEClass, OUTSIDE_LABEL_DESCRIPTION__POSITION);
		createEReference(outsideLabelDescriptionEClass, OUTSIDE_LABEL_DESCRIPTION__STYLE);
		createEReference(outsideLabelDescriptionEClass, OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES);

		styleEClass = createEClass(STYLE);
		createEReference(styleEClass, STYLE__COLOR);

		borderStyleEClass = createEClass(BORDER_STYLE);
		createEReference(borderStyleEClass, BORDER_STYLE__BORDER_COLOR);
		createEAttribute(borderStyleEClass, BORDER_STYLE__BORDER_RADIUS);
		createEAttribute(borderStyleEClass, BORDER_STYLE__BORDER_SIZE);
		createEAttribute(borderStyleEClass, BORDER_STYLE__BORDER_LINE_STYLE);

		insideLabelStyleEClass = createEClass(INSIDE_LABEL_STYLE);
		createEAttribute(insideLabelStyleEClass, INSIDE_LABEL_STYLE__WITH_HEADER);
		createEAttribute(insideLabelStyleEClass, INSIDE_LABEL_STYLE__HEADER_SEPARATOR_DISPLAY_MODE);

		outsideLabelStyleEClass = createEClass(OUTSIDE_LABEL_STYLE);

		nodeLabelStyleEClass = createEClass(NODE_LABEL_STYLE);
		createEReference(nodeLabelStyleEClass, NODE_LABEL_STYLE__LABEL_COLOR);
		createEReference(nodeLabelStyleEClass, NODE_LABEL_STYLE__BACKGROUND);
		createEAttribute(nodeLabelStyleEClass, NODE_LABEL_STYLE__SHOW_ICON_EXPRESSION);
		createEAttribute(nodeLabelStyleEClass, NODE_LABEL_STYLE__LABEL_ICON);
		createEAttribute(nodeLabelStyleEClass, NODE_LABEL_STYLE__MAX_WIDTH_EXPRESSION);

		nodeStyleDescriptionEClass = createEClass(NODE_STYLE_DESCRIPTION);
		createEReference(nodeStyleDescriptionEClass, NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY);

		conditionalNodeStyleEClass = createEClass(CONDITIONAL_NODE_STYLE);
		createEReference(conditionalNodeStyleEClass, CONDITIONAL_NODE_STYLE__STYLE);

		conditionalInsideLabelStyleEClass = createEClass(CONDITIONAL_INSIDE_LABEL_STYLE);
		createEReference(conditionalInsideLabelStyleEClass, CONDITIONAL_INSIDE_LABEL_STYLE__STYLE);

		conditionalOutsideLabelStyleEClass = createEClass(CONDITIONAL_OUTSIDE_LABEL_STYLE);
		createEReference(conditionalOutsideLabelStyleEClass, CONDITIONAL_OUTSIDE_LABEL_STYLE__STYLE);

		rectangularNodeStyleDescriptionEClass = createEClass(RECTANGULAR_NODE_STYLE_DESCRIPTION);
		createEReference(rectangularNodeStyleDescriptionEClass, RECTANGULAR_NODE_STYLE_DESCRIPTION__BACKGROUND);

		imageNodeStyleDescriptionEClass = createEClass(IMAGE_NODE_STYLE_DESCRIPTION);
		createEAttribute(imageNodeStyleDescriptionEClass, IMAGE_NODE_STYLE_DESCRIPTION__SHAPE);
		createEAttribute(imageNodeStyleDescriptionEClass, IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION);

		iconLabelNodeStyleDescriptionEClass = createEClass(ICON_LABEL_NODE_STYLE_DESCRIPTION);
		createEReference(iconLabelNodeStyleDescriptionEClass, ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND);

		edgeStyleEClass = createEClass(EDGE_STYLE);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__LINE_STYLE);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__SOURCE_ARROW_STYLE);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__TARGET_ARROW_STYLE);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__EDGE_WIDTH);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__SHOW_ICON);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__LABEL_ICON);
		createEReference(edgeStyleEClass, EDGE_STYLE__BACKGROUND);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__MAX_WIDTH_EXPRESSION);
		createEAttribute(edgeStyleEClass, EDGE_STYLE__EDGE_TYPE);

		conditionalEdgeStyleEClass = createEClass(CONDITIONAL_EDGE_STYLE);

		diagramPaletteEClass = createEClass(DIAGRAM_PALETTE);
		createEReference(diagramPaletteEClass, DIAGRAM_PALETTE__DROP_TOOL);
		createEReference(diagramPaletteEClass, DIAGRAM_PALETTE__DROP_NODE_TOOL);
		createEReference(diagramPaletteEClass, DIAGRAM_PALETTE__NODE_TOOLS);
		createEReference(diagramPaletteEClass, DIAGRAM_PALETTE__QUICK_ACCESS_TOOLS);
		createEReference(diagramPaletteEClass, DIAGRAM_PALETTE__TOOL_SECTIONS);

		groupPaletteEClass = createEClass(GROUP_PALETTE);
		createEReference(groupPaletteEClass, GROUP_PALETTE__NODE_TOOLS);
		createEReference(groupPaletteEClass, GROUP_PALETTE__QUICK_ACCESS_TOOLS);
		createEReference(groupPaletteEClass, GROUP_PALETTE__TOOL_SECTIONS);

		nodePaletteEClass = createEClass(NODE_PALETTE);
		createEReference(nodePaletteEClass, NODE_PALETTE__DELETE_TOOL);
		createEReference(nodePaletteEClass, NODE_PALETTE__LABEL_EDIT_TOOL);
		createEReference(nodePaletteEClass, NODE_PALETTE__DROP_NODE_TOOL);
		createEReference(nodePaletteEClass, NODE_PALETTE__NODE_TOOLS);
		createEReference(nodePaletteEClass, NODE_PALETTE__QUICK_ACCESS_TOOLS);
		createEReference(nodePaletteEClass, NODE_PALETTE__EDGE_TOOLS);
		createEReference(nodePaletteEClass, NODE_PALETTE__TOOL_SECTIONS);

		edgePaletteEClass = createEClass(EDGE_PALETTE);
		createEReference(edgePaletteEClass, EDGE_PALETTE__DELETE_TOOL);
		createEReference(edgePaletteEClass, EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL);
		createEReference(edgePaletteEClass, EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL);
		createEReference(edgePaletteEClass, EDGE_PALETTE__END_LABEL_EDIT_TOOL);
		createEReference(edgePaletteEClass, EDGE_PALETTE__NODE_TOOLS);
		createEReference(edgePaletteEClass, EDGE_PALETTE__QUICK_ACCESS_TOOLS);
		createEReference(edgePaletteEClass, EDGE_PALETTE__EDGE_RECONNECTION_TOOLS);
		createEReference(edgePaletteEClass, EDGE_PALETTE__EDGE_TOOLS);
		createEReference(edgePaletteEClass, EDGE_PALETTE__TOOL_SECTIONS);

		toolEClass = createEClass(TOOL);
		createEAttribute(toolEClass, TOOL__NAME);
		createEAttribute(toolEClass, TOOL__PRECONDITION_EXPRESSION);
		createEReference(toolEClass, TOOL__BODY);

		deleteToolEClass = createEClass(DELETE_TOOL);

		dropToolEClass = createEClass(DROP_TOOL);

		edgeToolEClass = createEClass(EDGE_TOOL);
		createEReference(edgeToolEClass, EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS);
		createEAttribute(edgeToolEClass, EDGE_TOOL__ICON_UR_LS_EXPRESSION);
		createEReference(edgeToolEClass, EDGE_TOOL__DIALOG_DESCRIPTION);
		createEAttribute(edgeToolEClass, EDGE_TOOL__ELEMENTS_TO_SELECT_EXPRESSION);

		edgeReconnectionToolEClass = createEClass(EDGE_RECONNECTION_TOOL);

		labelEditToolEClass = createEClass(LABEL_EDIT_TOOL);
		createEAttribute(labelEditToolEClass, LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION);

		nodeToolEClass = createEClass(NODE_TOOL);
		createEReference(nodeToolEClass, NODE_TOOL__DIALOG_DESCRIPTION);
		createEAttribute(nodeToolEClass, NODE_TOOL__ICON_UR_LS_EXPRESSION);
		createEAttribute(nodeToolEClass, NODE_TOOL__ELEMENTS_TO_SELECT_EXPRESSION);
		createEAttribute(nodeToolEClass, NODE_TOOL__WITH_IMPACT_ANALYSIS);

		sourceEdgeEndReconnectionToolEClass = createEClass(SOURCE_EDGE_END_RECONNECTION_TOOL);

		targetEdgeEndReconnectionToolEClass = createEClass(TARGET_EDGE_END_RECONNECTION_TOOL);

		createViewEClass = createEClass(CREATE_VIEW);
		createEAttribute(createViewEClass, CREATE_VIEW__PARENT_VIEW_EXPRESSION);
		createEReference(createViewEClass, CREATE_VIEW__ELEMENT_DESCRIPTION);
		createEAttribute(createViewEClass, CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION);
		createEAttribute(createViewEClass, CREATE_VIEW__VARIABLE_NAME);
		createEAttribute(createViewEClass, CREATE_VIEW__CONTAINMENT_KIND);

		deleteViewEClass = createEClass(DELETE_VIEW);
		createEAttribute(deleteViewEClass, DELETE_VIEW__VIEW_EXPRESSION);

		selectionDialogDescriptionEClass = createEClass(SELECTION_DIALOG_DESCRIPTION);
		createEAttribute(selectionDialogDescriptionEClass, SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE);
		createEReference(selectionDialogDescriptionEClass, SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION);
		createEAttribute(selectionDialogDescriptionEClass, SELECTION_DIALOG_DESCRIPTION__MULTIPLE);

		toolSectionEClass = createEClass(TOOL_SECTION);
		createEAttribute(toolSectionEClass, TOOL_SECTION__NAME);

		diagramToolSectionEClass = createEClass(DIAGRAM_TOOL_SECTION);
		createEReference(diagramToolSectionEClass, DIAGRAM_TOOL_SECTION__NODE_TOOLS);

		nodeToolSectionEClass = createEClass(NODE_TOOL_SECTION);
		createEReference(nodeToolSectionEClass, NODE_TOOL_SECTION__NODE_TOOLS);
		createEReference(nodeToolSectionEClass, NODE_TOOL_SECTION__EDGE_TOOLS);

		edgeToolSectionEClass = createEClass(EDGE_TOOL_SECTION);
		createEReference(edgeToolSectionEClass, EDGE_TOOL_SECTION__NODE_TOOLS);

		dropNodeToolEClass = createEClass(DROP_NODE_TOOL);
		createEReference(dropNodeToolEClass, DROP_NODE_TOOL__ACCEPTED_NODE_TYPES);

		dialogDescriptionEClass = createEClass(DIALOG_DESCRIPTION);

		selectionDialogTreeDescriptionEClass = createEClass(SELECTION_DIALOG_TREE_DESCRIPTION);
		createEAttribute(selectionDialogTreeDescriptionEClass, SELECTION_DIALOG_TREE_DESCRIPTION__ELEMENTS_EXPRESSION);
		createEAttribute(selectionDialogTreeDescriptionEClass, SELECTION_DIALOG_TREE_DESCRIPTION__CHILDREN_EXPRESSION);
		createEAttribute(selectionDialogTreeDescriptionEClass, SELECTION_DIALOG_TREE_DESCRIPTION__IS_SELECTABLE_EXPRESSION);

		actionEClass = createEClass(ACTION);
		createEAttribute(actionEClass, ACTION__NAME);
		createEAttribute(actionEClass, ACTION__TOOLTIP_EXPRESSION);
		createEAttribute(actionEClass, ACTION__ICON_UR_LS_EXPRESSION);
		createEAttribute(actionEClass, ACTION__PRECONDITION_EXPRESSION);
		createEReference(actionEClass, ACTION__BODY);

		// Create enums
		arrowStyleEEnum = createEEnum(ARROW_STYLE);
		layoutDirectionEEnum = createEEnum(LAYOUT_DIRECTION);
		lineStyleEEnum = createEEnum(LINE_STYLE);
		nodeContainmentKindEEnum = createEEnum(NODE_CONTAINMENT_KIND);
		synchronizationPolicyEEnum = createEEnum(SYNCHRONIZATION_POLICY);
		insideLabelPositionEEnum = createEEnum(INSIDE_LABEL_POSITION);
		outsideLabelPositionEEnum = createEEnum(OUTSIDE_LABEL_POSITION);
		labelOverflowStrategyEEnum = createEEnum(LABEL_OVERFLOW_STRATEGY);
		arrangeLayoutDirectionEEnum = createEEnum(ARRANGE_LAYOUT_DIRECTION);
		labelTextAlignEEnum = createEEnum(LABEL_TEXT_ALIGN);
		userResizableDirectionEEnum = createEEnum(USER_RESIZABLE_DIRECTION);
		headerSeparatorDisplayModeEEnum = createEEnum(HEADER_SEPARATOR_DISPLAY_MODE);
		edgeTypeEEnum = createEEnum(EDGE_TYPE);
	}

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ViewPackage theViewPackage = (ViewPackage)EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		diagramDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
		nodeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
		edgeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
		listLayoutStrategyDescriptionEClass.getESuperTypes().add(this.getLayoutStrategyDescription());
		freeFormLayoutStrategyDescriptionEClass.getESuperTypes().add(this.getLayoutStrategyDescription());
		insideLabelDescriptionEClass.getESuperTypes().add(this.getLabelDescription());
		outsideLabelDescriptionEClass.getESuperTypes().add(this.getLabelDescription());
		insideLabelStyleEClass.getESuperTypes().add(this.getNodeLabelStyle());
		outsideLabelStyleEClass.getESuperTypes().add(this.getNodeLabelStyle());
		nodeLabelStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
		nodeLabelStyleEClass.getESuperTypes().add(this.getBorderStyle());
		nodeStyleDescriptionEClass.getESuperTypes().add(this.getBorderStyle());
		conditionalNodeStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
		conditionalInsideLabelStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
		conditionalOutsideLabelStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
		rectangularNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
		imageNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
		iconLabelNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
		edgeStyleEClass.getESuperTypes().add(this.getStyle());
		edgeStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
		edgeStyleEClass.getESuperTypes().add(this.getBorderStyle());
		conditionalEdgeStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
		conditionalEdgeStyleEClass.getESuperTypes().add(this.getEdgeStyle());
		deleteToolEClass.getESuperTypes().add(this.getTool());
		dropToolEClass.getESuperTypes().add(this.getTool());
		edgeToolEClass.getESuperTypes().add(this.getTool());
		edgeReconnectionToolEClass.getESuperTypes().add(this.getTool());
		labelEditToolEClass.getESuperTypes().add(this.getTool());
		nodeToolEClass.getESuperTypes().add(this.getTool());
		sourceEdgeEndReconnectionToolEClass.getESuperTypes().add(this.getEdgeReconnectionTool());
		targetEdgeEndReconnectionToolEClass.getESuperTypes().add(this.getEdgeReconnectionTool());
		createViewEClass.getESuperTypes().add(theViewPackage.getOperation());
		deleteViewEClass.getESuperTypes().add(theViewPackage.getOperation());
		selectionDialogDescriptionEClass.getESuperTypes().add(this.getDialogDescription());
		diagramToolSectionEClass.getESuperTypes().add(this.getToolSection());
		nodeToolSectionEClass.getESuperTypes().add(this.getToolSection());
		edgeToolSectionEClass.getESuperTypes().add(this.getToolSection());
		dropNodeToolEClass.getESuperTypes().add(this.getTool());

		// Initialize classes, features, and operations; add parameters
		initEClass(diagramDescriptionEClass, DiagramDescription.class, "DiagramDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiagramDescription_AutoLayout(), ecorePackage.getEBoolean(), "autoLayout", null, 1, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiagramDescription_Palette(), this.getDiagramPalette(), null, "palette", null, 0, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiagramDescription_GroupPalette(), this.getGroupPalette(), null, "groupPalette", null, 0, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiagramDescription_NodeDescriptions(), this.getNodeDescription(), null, "nodeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getDiagramDescription_NodeDescriptions().getEKeys().add(this.getDiagramElementDescription_Name());
		initEReference(getDiagramDescription_EdgeDescriptions(), this.getEdgeDescription(), null, "edgeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getDiagramDescription_EdgeDescriptions().getEKeys().add(this.getDiagramElementDescription_Name());
		initEAttribute(getDiagramDescription_ArrangeLayoutDirection(), this.getArrangeLayoutDirection(), "arrangeLayoutDirection", "UNDEFINED", 1, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(diagramElementDescriptionEClass, DiagramElementDescription.class, "DiagramElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiagramElementDescription_Name(), theViewPackage.getIdentifier(), "name", "NewRepresentationDescription", 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiagramElementDescription_DomainType(), theViewPackage.getDomainType(), "domainType", null, 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiagramElementDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self.eContents()", 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiagramElementDescription_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiagramElementDescription_SynchronizationPolicy(), this.getSynchronizationPolicy(), "synchronizationPolicy", null, 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeDescriptionEClass, NodeDescription.class, "NodeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNodeDescription_Collapsible(), ecorePackage.getEBoolean(), "collapsible", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_Palette(), this.getNodePalette(), null, "palette", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_Actions(), this.getAction(), null, "actions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_Style(), this.getNodeStyleDescription(), null, "style", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_ConditionalStyles(), this.getConditionalNodeStyle(), null, "conditionalStyles", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_ChildrenDescriptions(), this.getNodeDescription(), null, "childrenDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodeDescription_ChildrenDescriptions().getEKeys().add(this.getDiagramElementDescription_Name());
		initEReference(getNodeDescription_BorderNodesDescriptions(), this.getNodeDescription(), null, "borderNodesDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodeDescription_BorderNodesDescriptions().getEKeys().add(this.getDiagramElementDescription_Name());
		initEReference(getNodeDescription_ReusedChildNodeDescriptions(), this.getNodeDescription(), null, "reusedChildNodeDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_ReusedBorderNodeDescriptions(), this.getNodeDescription(), null, "reusedBorderNodeDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeDescription_UserResizable(), this.getUserResizableDirection(), "userResizable", "BOTH", 1, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeDescription_DefaultWidthExpression(), theViewPackage.getInterpretedExpression(), "defaultWidthExpression", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeDescription_DefaultHeightExpression(), theViewPackage.getInterpretedExpression(), "defaultHeightExpression", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeDescription_KeepAspectRatio(), ecorePackage.getEBoolean(), "keepAspectRatio", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeDescription_IsCollapsedByDefaultExpression(), theViewPackage.getInterpretedExpression(), "isCollapsedByDefaultExpression", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_InsideLabel(), this.getInsideLabelDescription(), null, "insideLabel", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeDescription_OutsideLabels(), this.getOutsideLabelDescription(), null, "outsideLabels", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeDescription_IsHiddenByDefaultExpression(), theViewPackage.getInterpretedExpression(), "isHiddenByDefaultExpression", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeDescription_IsFadedByDefaultExpression(), theViewPackage.getInterpretedExpression(), "isFadedByDefaultExpression", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(edgeDescriptionEClass, EdgeDescription.class, "EdgeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEdgeDescription_BeginLabelExpression(), theViewPackage.getInterpretedExpression(), "beginLabelExpression", "", 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeDescription_CenterLabelExpression(), theViewPackage.getInterpretedExpression(), "centerLabelExpression", "aql:self.name", 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeDescription_EndLabelExpression(), theViewPackage.getInterpretedExpression(), "endLabelExpression", "", 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeDescription_IsDomainBasedEdge(), ecorePackage.getEBoolean(), "isDomainBasedEdge", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeDescription_Palette(), this.getEdgePalette(), null, "palette", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeDescription_SourceDescriptions(), this.getDiagramElementDescription(), null, "sourceDescriptions", null, 1, -1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeDescription_TargetDescriptions(), this.getDiagramElementDescription(), null, "targetDescriptions", null, 1, -1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeDescription_SourceExpression(), theViewPackage.getInterpretedExpression(), "sourceExpression", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeDescription_TargetExpression(), theViewPackage.getInterpretedExpression(), "targetExpression", "aql:self.eCrossReferences()", 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeDescription_Style(), this.getEdgeStyle(), null, "style", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeDescription_ConditionalStyles(), this.getConditionalEdgeStyle(), null, "conditionalStyles", null, 0, -1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(layoutStrategyDescriptionEClass, LayoutStrategyDescription.class, "LayoutStrategyDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLayoutStrategyDescription_OnWestAtCreationBorderNodes(), this.getNodeDescription(), null, "onWestAtCreationBorderNodes", null, 0, -1, LayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLayoutStrategyDescription_OnEastAtCreationBorderNodes(), this.getNodeDescription(), null, "onEastAtCreationBorderNodes", null, 0, -1, LayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLayoutStrategyDescription_OnSouthAtCreationBorderNodes(), this.getNodeDescription(), null, "onSouthAtCreationBorderNodes", null, 0, -1, LayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLayoutStrategyDescription_OnNorthAtCreationBorderNodes(), this.getNodeDescription(), null, "onNorthAtCreationBorderNodes", null, 0, -1, LayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(listLayoutStrategyDescriptionEClass, ListLayoutStrategyDescription.class, "ListLayoutStrategyDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getListLayoutStrategyDescription_AreChildNodesDraggableExpression(), theViewPackage.getInterpretedExpression(), "areChildNodesDraggableExpression", "aql:true", 1, 1, ListLayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getListLayoutStrategyDescription_TopGapExpression(), theViewPackage.getInterpretedExpression(), "topGapExpression", "", 0, 1, ListLayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getListLayoutStrategyDescription_BottomGapExpression(), theViewPackage.getInterpretedExpression(), "bottomGapExpression", "", 0, 1, ListLayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getListLayoutStrategyDescription_GrowableNodes(), this.getNodeDescription(), null, "growableNodes", null, 0, -1, ListLayoutStrategyDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(freeFormLayoutStrategyDescriptionEClass, FreeFormLayoutStrategyDescription.class, "FreeFormLayoutStrategyDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(labelDescriptionEClass, LabelDescription.class, "LabelDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabelDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", "aql:self.name", 0, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLabelDescription_OverflowStrategy(), this.getLabelOverflowStrategy(), "overflowStrategy", null, 1, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLabelDescription_TextAlign(), this.getLabelTextAlign(), "textAlign", null, 1, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(insideLabelDescriptionEClass, InsideLabelDescription.class, "InsideLabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInsideLabelDescription_Position(), this.getInsideLabelPosition(), "position", null, 1, 1, InsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInsideLabelDescription_Style(), this.getInsideLabelStyle(), null, "style", null, 0, 1, InsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInsideLabelDescription_ConditionalStyles(), this.getConditionalInsideLabelStyle(), null, "conditionalStyles", null, 0, -1, InsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(outsideLabelDescriptionEClass, OutsideLabelDescription.class, "OutsideLabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOutsideLabelDescription_Position(), this.getOutsideLabelPosition(), "position", null, 1, 1, OutsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOutsideLabelDescription_Style(), this.getOutsideLabelStyle(), null, "style", null, 0, 1, OutsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOutsideLabelDescription_ConditionalStyles(), this.getConditionalOutsideLabelStyle(), null, "conditionalStyles", null, 0, -1, OutsideLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(styleEClass, Style.class, "Style", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, Style.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(borderStyleEClass, BorderStyle.class, "BorderStyle", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBorderStyle_BorderColor(), theViewPackage.getUserColor(), null, "borderColor", null, 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBorderStyle_BorderRadius(), theViewPackage.getLength(), "borderRadius", "3", 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBorderStyle_BorderSize(), theViewPackage.getLength(), "borderSize", "1", 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBorderStyle_BorderLineStyle(), this.getLineStyle(), "borderLineStyle", null, 0, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(insideLabelStyleEClass, InsideLabelStyle.class, "InsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInsideLabelStyle_WithHeader(), ecorePackage.getEBoolean(), "withHeader", null, 0, 1, InsideLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInsideLabelStyle_HeaderSeparatorDisplayMode(), this.getHeaderSeparatorDisplayMode(), "headerSeparatorDisplayMode", null, 1, 1, InsideLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(outsideLabelStyleEClass, OutsideLabelStyle.class, "OutsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nodeLabelStyleEClass, NodeLabelStyle.class, "NodeLabelStyle", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeLabelStyle_LabelColor(), theViewPackage.getUserColor(), null, "labelColor", null, 1, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeLabelStyle_Background(), theViewPackage.getUserColor(), null, "background", null, 0, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLabelStyle_ShowIconExpression(), theViewPackage.getInterpretedExpression(), "showIconExpression", null, 0, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLabelStyle_LabelIcon(), ecorePackage.getEString(), "labelIcon", null, 0, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLabelStyle_MaxWidthExpression(), theViewPackage.getInterpretedExpression(), "maxWidthExpression", null, 0, 1, NodeLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeStyleDescriptionEClass, NodeStyleDescription.class, "NodeStyleDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeStyleDescription_ChildrenLayoutStrategy(), this.getLayoutStrategyDescription(), null, "childrenLayoutStrategy", null, 0, 1, NodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalNodeStyleEClass, ConditionalNodeStyle.class, "ConditionalNodeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalNodeStyle_Style(), this.getNodeStyleDescription(), null, "style", null, 0, 1, ConditionalNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalInsideLabelStyleEClass, ConditionalInsideLabelStyle.class, "ConditionalInsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalInsideLabelStyle_Style(), this.getInsideLabelStyle(), null, "style", null, 0, 1, ConditionalInsideLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalOutsideLabelStyleEClass, ConditionalOutsideLabelStyle.class, "ConditionalOutsideLabelStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalOutsideLabelStyle_Style(), this.getOutsideLabelStyle(), null, "style", null, 0, 1, ConditionalOutsideLabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rectangularNodeStyleDescriptionEClass, RectangularNodeStyleDescription.class, "RectangularNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRectangularNodeStyleDescription_Background(), theViewPackage.getUserColor(), null, "background", null, 0, 1, RectangularNodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(imageNodeStyleDescriptionEClass, ImageNodeStyleDescription.class, "ImageNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImageNodeStyleDescription_Shape(), ecorePackage.getEString(), "shape", null, 0, 1, ImageNodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImageNodeStyleDescription_PositionDependentRotation(), ecorePackage.getEBoolean(), "positionDependentRotation", null, 0, 1, ImageNodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iconLabelNodeStyleDescriptionEClass, IconLabelNodeStyleDescription.class, "IconLabelNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIconLabelNodeStyleDescription_Background(), theViewPackage.getUserColor(), null, "background", null, 0, 1, IconLabelNodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(edgeStyleEClass, EdgeStyle.class, "EdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEdgeStyle_LineStyle(), this.getLineStyle(), "lineStyle", "Solid", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeStyle_SourceArrowStyle(), this.getArrowStyle(), "sourceArrowStyle", "None", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeStyle_TargetArrowStyle(), this.getArrowStyle(), "targetArrowStyle", "InputArrow", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeStyle_EdgeWidth(), theViewPackage.getLength(), "edgeWidth", "1", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeStyle_ShowIcon(), ecorePackage.getEBoolean(), "showIcon", "false", 0, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeStyle_LabelIcon(), ecorePackage.getEString(), "labelIcon", null, 0, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeStyle_Background(), theViewPackage.getUserColor(), null, "background", null, 0, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeStyle_MaxWidthExpression(), theViewPackage.getInterpretedExpression(), "maxWidthExpression", null, 0, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeStyle_EdgeType(), this.getEdgeType(), "edgeType", "Manhattan", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalEdgeStyleEClass, ConditionalEdgeStyle.class, "ConditionalEdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(diagramPaletteEClass, DiagramPalette.class, "DiagramPalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDiagramPalette_DropTool(), this.getDropTool(), null, "dropTool", null, 0, 1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiagramPalette_DropNodeTool(), this.getDropNodeTool(), null, "dropNodeTool", null, 0, 1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiagramPalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getDiagramPalette_NodeTools().getEKeys().add(this.getTool_Name());
		initEReference(getDiagramPalette_QuickAccessTools(), this.getNodeTool(), null, "quickAccessTools", null, 0, -1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getDiagramPalette_QuickAccessTools().getEKeys().add(this.getTool_Name());
		initEReference(getDiagramPalette_ToolSections(), this.getDiagramToolSection(), null, "toolSections", null, 0, -1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getDiagramPalette_ToolSections().getEKeys().add(this.getToolSection_Name());

		initEClass(groupPaletteEClass, GroupPalette.class, "GroupPalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGroupPalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, GroupPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getGroupPalette_NodeTools().getEKeys().add(this.getTool_Name());
		initEReference(getGroupPalette_QuickAccessTools(), this.getNodeTool(), null, "quickAccessTools", null, 0, -1, GroupPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getGroupPalette_QuickAccessTools().getEKeys().add(this.getTool_Name());
		initEReference(getGroupPalette_ToolSections(), this.getToolSection(), null, "toolSections", null, 0, -1, GroupPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getGroupPalette_ToolSections().getEKeys().add(this.getToolSection_Name());

		initEClass(nodePaletteEClass, NodePalette.class, "NodePalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodePalette_DeleteTool(), this.getDeleteTool(), null, "deleteTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodePalette_LabelEditTool(), this.getLabelEditTool(), null, "labelEditTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodePalette_DropNodeTool(), this.getDropNodeTool(), null, "dropNodeTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodePalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodePalette_NodeTools().getEKeys().add(this.getTool_Name());
		initEReference(getNodePalette_QuickAccessTools(), this.getNodeTool(), null, "quickAccessTools", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodePalette_QuickAccessTools().getEKeys().add(this.getTool_Name());
		initEReference(getNodePalette_EdgeTools(), this.getEdgeTool(), null, "edgeTools", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodePalette_EdgeTools().getEKeys().add(this.getTool_Name());
		initEReference(getNodePalette_ToolSections(), this.getNodeToolSection(), null, "toolSections", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodePalette_ToolSections().getEKeys().add(this.getToolSection_Name());

		initEClass(edgePaletteEClass, EdgePalette.class, "EdgePalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEdgePalette_DeleteTool(), this.getDeleteTool(), null, "deleteTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgePalette_CenterLabelEditTool(), this.getLabelEditTool(), null, "centerLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgePalette_BeginLabelEditTool(), this.getLabelEditTool(), null, "beginLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgePalette_EndLabelEditTool(), this.getLabelEditTool(), null, "endLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgePalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getEdgePalette_NodeTools().getEKeys().add(this.getTool_Name());
		initEReference(getEdgePalette_QuickAccessTools(), this.getNodeTool(), null, "quickAccessTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getEdgePalette_QuickAccessTools().getEKeys().add(this.getTool_Name());
		initEReference(getEdgePalette_EdgeReconnectionTools(), this.getEdgeReconnectionTool(), null, "edgeReconnectionTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getEdgePalette_EdgeReconnectionTools().getEKeys().add(this.getTool_Name());
		initEReference(getEdgePalette_EdgeTools(), this.getEdgeTool(), null, "edgeTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgePalette_ToolSections(), this.getEdgeToolSection(), null, "toolSections", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getEdgePalette_ToolSections().getEKeys().add(this.getToolSection_Name());

		initEClass(toolEClass, Tool.class, "Tool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTool_Name(), theViewPackage.getIdentifier(), "name", "Tool", 1, 1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTool_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTool_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deleteToolEClass, DeleteTool.class, "DeleteTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dropToolEClass, DropTool.class, "DropTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(edgeToolEClass, EdgeTool.class, "EdgeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEdgeTool_TargetElementDescriptions(), this.getDiagramElementDescription(), null, "targetElementDescriptions", null, 0, -1, EdgeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeTool_IconURLsExpression(), theViewPackage.getInterpretedExpression(), "iconURLsExpression", null, 0, 1, EdgeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeTool_DialogDescription(), this.getDialogDescription(), null, "dialogDescription", null, 0, 1, EdgeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEdgeTool_ElementsToSelectExpression(), theViewPackage.getInterpretedExpression(), "elementsToSelectExpression", null, 0, 1, EdgeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(edgeReconnectionToolEClass, EdgeReconnectionTool.class, "EdgeReconnectionTool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(labelEditToolEClass, LabelEditTool.class, "LabelEditTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabelEditTool_InitialDirectEditLabelExpression(), theViewPackage.getInterpretedExpression(), "initialDirectEditLabelExpression", null, 0, 1, LabelEditTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeToolEClass, NodeTool.class, "NodeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeTool_DialogDescription(), this.getDialogDescription(), null, "dialogDescription", null, 0, 1, NodeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeTool_IconURLsExpression(), theViewPackage.getInterpretedExpression(), "iconURLsExpression", null, 0, 1, NodeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeTool_ElementsToSelectExpression(), theViewPackage.getInterpretedExpression(), "elementsToSelectExpression", null, 0, 1, NodeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeTool_WithImpactAnalysis(), ecorePackage.getEBoolean(), "withImpactAnalysis", null, 0, 1, NodeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sourceEdgeEndReconnectionToolEClass, SourceEdgeEndReconnectionTool.class, "SourceEdgeEndReconnectionTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(targetEdgeEndReconnectionToolEClass, TargetEdgeEndReconnectionTool.class, "TargetEdgeEndReconnectionTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(createViewEClass, CreateView.class, "CreateView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCreateView_ParentViewExpression(), theViewPackage.getInterpretedExpression(), "parentViewExpression", "aql:selectedNode", 1, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCreateView_ElementDescription(), this.getDiagramElementDescription(), null, "elementDescription", null, 0, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreateView_SemanticElementExpression(), theViewPackage.getInterpretedExpression(), "semanticElementExpression", "aql:self", 1, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreateView_VariableName(), ecorePackage.getEString(), "variableName", null, 0, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCreateView_ContainmentKind(), this.getNodeContainmentKind(), "containmentKind", "CHILD_NODE", 1, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deleteViewEClass, DeleteView.class, "DeleteView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeleteView_ViewExpression(), theViewPackage.getInterpretedExpression(), "viewExpression", "aql:selectedNode", 1, 1, DeleteView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(selectionDialogDescriptionEClass, SelectionDialogDescription.class, "SelectionDialogDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSelectionDialogDescription_SelectionMessage(), ecorePackage.getEString(), "selectionMessage", null, 0, 1, SelectionDialogDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSelectionDialogDescription_SelectionDialogTreeDescription(), this.getSelectionDialogTreeDescription(), null, "selectionDialogTreeDescription", null, 0, 1, SelectionDialogDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectionDialogDescription_Multiple(), ecorePackage.getEBoolean(), "multiple", null, 0, 1, SelectionDialogDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(toolSectionEClass, ToolSection.class, "ToolSection", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getToolSection_Name(), theViewPackage.getIdentifier(), "name", "Tool Section", 1, 1, ToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(diagramToolSectionEClass, DiagramToolSection.class, "DiagramToolSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDiagramToolSection_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, DiagramToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getDiagramToolSection_NodeTools().getEKeys().add(this.getTool_Name());

		initEClass(nodeToolSectionEClass, NodeToolSection.class, "NodeToolSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeToolSection_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, NodeToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodeToolSection_NodeTools().getEKeys().add(this.getTool_Name());
		initEReference(getNodeToolSection_EdgeTools(), this.getEdgeTool(), null, "edgeTools", null, 0, -1, NodeToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getNodeToolSection_EdgeTools().getEKeys().add(this.getTool_Name());

		initEClass(edgeToolSectionEClass, EdgeToolSection.class, "EdgeToolSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEdgeToolSection_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, EdgeToolSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getEdgeToolSection_NodeTools().getEKeys().add(this.getTool_Name());

		initEClass(dropNodeToolEClass, DropNodeTool.class, "DropNodeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDropNodeTool_AcceptedNodeTypes(), this.getNodeDescription(), null, "acceptedNodeTypes", null, 0, -1, DropNodeTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dialogDescriptionEClass, DialogDescription.class, "DialogDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(selectionDialogTreeDescriptionEClass, SelectionDialogTreeDescription.class, "SelectionDialogTreeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSelectionDialogTreeDescription_ElementsExpression(), theViewPackage.getInterpretedExpression(), "elementsExpression", "", 1, 1, SelectionDialogTreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectionDialogTreeDescription_ChildrenExpression(), theViewPackage.getInterpretedExpression(), "childrenExpression", null, 0, 1, SelectionDialogTreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectionDialogTreeDescription_IsSelectableExpression(), theViewPackage.getInterpretedExpression(), "isSelectableExpression", null, 0, 1, SelectionDialogTreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionEClass, Action.class, "Action", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAction_Name(), theViewPackage.getIdentifier(), "name", "Action", 1, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_TooltipExpression(), theViewPackage.getInterpretedExpression(), "tooltipExpression", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_IconURLsExpression(), theViewPackage.getInterpretedExpression(), "iconURLsExpression", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAction_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAction_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(arrowStyleEEnum, ArrowStyle.class, "ArrowStyle");
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.NONE);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.OUTPUT_ARROW);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.INPUT_ARROW);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.OUTPUT_CLOSED_ARROW);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.INPUT_CLOSED_ARROW);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.OUTPUT_FILL_CLOSED_ARROW);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.INPUT_FILL_CLOSED_ARROW);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.DIAMOND);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.FILL_DIAMOND);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.CIRCLE);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.FILL_CIRCLE);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.CROSSED_CIRCLE);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.CLOSED_ARROW_WITH_VERTICAL_BAR);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.CLOSED_ARROW_WITH_DOTS);
		addEEnumLiteral(arrowStyleEEnum, ArrowStyle.CLOSED_ARROW_WITH4_DOTS);

		initEEnum(layoutDirectionEEnum, LayoutDirection.class, "LayoutDirection");
		addEEnumLiteral(layoutDirectionEEnum, LayoutDirection.COLUMN);

		initEEnum(lineStyleEEnum, LineStyle.class, "LineStyle");
		addEEnumLiteral(lineStyleEEnum, LineStyle.SOLID);
		addEEnumLiteral(lineStyleEEnum, LineStyle.DASH);
		addEEnumLiteral(lineStyleEEnum, LineStyle.DOT);
		addEEnumLiteral(lineStyleEEnum, LineStyle.DASH_DOT);

		initEEnum(nodeContainmentKindEEnum, NodeContainmentKind.class, "NodeContainmentKind");
		addEEnumLiteral(nodeContainmentKindEEnum, NodeContainmentKind.CHILD_NODE);
		addEEnumLiteral(nodeContainmentKindEEnum, NodeContainmentKind.BORDER_NODE);

		initEEnum(synchronizationPolicyEEnum, SynchronizationPolicy.class, "SynchronizationPolicy");
		addEEnumLiteral(synchronizationPolicyEEnum, SynchronizationPolicy.SYNCHRONIZED);
		addEEnumLiteral(synchronizationPolicyEEnum, SynchronizationPolicy.UNSYNCHRONIZED);

		initEEnum(insideLabelPositionEEnum, InsideLabelPosition.class, "InsideLabelPosition");
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.TOP_CENTER);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.TOP_LEFT);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.TOP_RIGHT);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.MIDDLE_LEFT);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.MIDDLE_CENTER);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.MIDDLE_RIGHT);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.BOTTOM_LEFT);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.BOTTOM_CENTER);
		addEEnumLiteral(insideLabelPositionEEnum, InsideLabelPosition.BOTTOM_RIGHT);

		initEEnum(outsideLabelPositionEEnum, OutsideLabelPosition.class, "OutsideLabelPosition");
		addEEnumLiteral(outsideLabelPositionEEnum, OutsideLabelPosition.BOTTOM_CENTER);

		initEEnum(labelOverflowStrategyEEnum, LabelOverflowStrategy.class, "LabelOverflowStrategy");
		addEEnumLiteral(labelOverflowStrategyEEnum, LabelOverflowStrategy.NONE);
		addEEnumLiteral(labelOverflowStrategyEEnum, LabelOverflowStrategy.WRAP);
		addEEnumLiteral(labelOverflowStrategyEEnum, LabelOverflowStrategy.ELLIPSIS);

		initEEnum(arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.class, "ArrangeLayoutDirection");
		addEEnumLiteral(arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.UNDEFINED);
		addEEnumLiteral(arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.RIGHT);
		addEEnumLiteral(arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.DOWN);
		addEEnumLiteral(arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.LEFT);
		addEEnumLiteral(arrangeLayoutDirectionEEnum, ArrangeLayoutDirection.UP);

		initEEnum(labelTextAlignEEnum, LabelTextAlign.class, "LabelTextAlign");
		addEEnumLiteral(labelTextAlignEEnum, LabelTextAlign.LEFT);
		addEEnumLiteral(labelTextAlignEEnum, LabelTextAlign.RIGHT);
		addEEnumLiteral(labelTextAlignEEnum, LabelTextAlign.CENTER);
		addEEnumLiteral(labelTextAlignEEnum, LabelTextAlign.JUSTIFY);

		initEEnum(userResizableDirectionEEnum, UserResizableDirection.class, "UserResizableDirection");
		addEEnumLiteral(userResizableDirectionEEnum, UserResizableDirection.BOTH);
		addEEnumLiteral(userResizableDirectionEEnum, UserResizableDirection.HORIZONTAL);
		addEEnumLiteral(userResizableDirectionEEnum, UserResizableDirection.VERTICAL);
		addEEnumLiteral(userResizableDirectionEEnum, UserResizableDirection.NONE);

		initEEnum(headerSeparatorDisplayModeEEnum, HeaderSeparatorDisplayMode.class, "HeaderSeparatorDisplayMode");
		addEEnumLiteral(headerSeparatorDisplayModeEEnum, HeaderSeparatorDisplayMode.NEVER);
		addEEnumLiteral(headerSeparatorDisplayModeEEnum, HeaderSeparatorDisplayMode.ALWAYS);
		addEEnumLiteral(headerSeparatorDisplayModeEEnum, HeaderSeparatorDisplayMode.IF_CHILDREN);

		initEEnum(edgeTypeEEnum, EdgeType.class, "EdgeType");
		addEEnumLiteral(edgeTypeEEnum, EdgeType.MANHATTAN);
		addEEnumLiteral(edgeTypeEEnum, EdgeType.SMART_MANHATTAN);
		addEEnumLiteral(edgeTypeEEnum, EdgeType.OBLIQUE);

		// Create resource
		createResource(eNS_URI);
	}

} // DiagramPackageImpl
