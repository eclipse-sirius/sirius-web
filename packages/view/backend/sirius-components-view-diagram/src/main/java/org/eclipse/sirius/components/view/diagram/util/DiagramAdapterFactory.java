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
package org.eclipse.sirius.components.view.diagram.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
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
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.LabelDescription;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeLabelStyle;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDescription;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Style;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.diagram.ToolSection;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage
 */
public class DiagramAdapterFactory extends AdapterFactoryImpl {

    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static DiagramPackage modelPackage;
    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramSwitch<Adapter> modelSwitch = new DiagramSwitch<>() {
        @Override
        public Adapter caseDiagramDescription(DiagramDescription object) {
            return DiagramAdapterFactory.this.createDiagramDescriptionAdapter();
        }

        @Override
        public Adapter caseDiagramElementDescription(DiagramElementDescription object) {
            return DiagramAdapterFactory.this.createDiagramElementDescriptionAdapter();
        }

        @Override
        public Adapter caseNodeDescription(NodeDescription object) {
            return DiagramAdapterFactory.this.createNodeDescriptionAdapter();
        }

        @Override
        public Adapter caseEdgeDescription(EdgeDescription object) {
            return DiagramAdapterFactory.this.createEdgeDescriptionAdapter();
        }

        @Override
        public Adapter caseLayoutStrategyDescription(LayoutStrategyDescription object) {
            return DiagramAdapterFactory.this.createLayoutStrategyDescriptionAdapter();
        }

        @Override
        public Adapter caseListLayoutStrategyDescription(ListLayoutStrategyDescription object) {
            return DiagramAdapterFactory.this.createListLayoutStrategyDescriptionAdapter();
        }

        @Override
        public Adapter caseFreeFormLayoutStrategyDescription(FreeFormLayoutStrategyDescription object) {
            return DiagramAdapterFactory.this.createFreeFormLayoutStrategyDescriptionAdapter();
        }

        @Override
        public Adapter caseLabelDescription(LabelDescription object) {
            return DiagramAdapterFactory.this.createLabelDescriptionAdapter();
        }

        @Override
        public Adapter caseInsideLabelDescription(InsideLabelDescription object) {
            return DiagramAdapterFactory.this.createInsideLabelDescriptionAdapter();
        }

        @Override
        public Adapter caseOutsideLabelDescription(OutsideLabelDescription object) {
            return DiagramAdapterFactory.this.createOutsideLabelDescriptionAdapter();
        }

        @Override
        public Adapter caseStyle(Style object) {
            return DiagramAdapterFactory.this.createStyleAdapter();
        }

        @Override
        public Adapter caseBorderStyle(BorderStyle object) {
            return DiagramAdapterFactory.this.createBorderStyleAdapter();
        }

        @Override
        public Adapter caseInsideLabelStyle(InsideLabelStyle object) {
            return DiagramAdapterFactory.this.createInsideLabelStyleAdapter();
        }

        @Override
        public Adapter caseOutsideLabelStyle(OutsideLabelStyle object) {
            return DiagramAdapterFactory.this.createOutsideLabelStyleAdapter();
        }

        @Override
        public Adapter caseNodeLabelStyle(NodeLabelStyle object) {
            return DiagramAdapterFactory.this.createNodeLabelStyleAdapter();
        }

        @Override
        public Adapter caseNodeStyleDescription(NodeStyleDescription object) {
            return DiagramAdapterFactory.this.createNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseConditionalNodeStyle(ConditionalNodeStyle object) {
            return DiagramAdapterFactory.this.createConditionalNodeStyleAdapter();
        }

        @Override
        public Adapter caseConditionalInsideLabelStyle(ConditionalInsideLabelStyle object) {
            return DiagramAdapterFactory.this.createConditionalInsideLabelStyleAdapter();
        }

        @Override
        public Adapter caseConditionalOutsideLabelStyle(ConditionalOutsideLabelStyle object) {
            return DiagramAdapterFactory.this.createConditionalOutsideLabelStyleAdapter();
        }

        @Override
        public Adapter caseRectangularNodeStyleDescription(RectangularNodeStyleDescription object) {
            return DiagramAdapterFactory.this.createRectangularNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseImageNodeStyleDescription(ImageNodeStyleDescription object) {
            return DiagramAdapterFactory.this.createImageNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseIconLabelNodeStyleDescription(IconLabelNodeStyleDescription object) {
            return DiagramAdapterFactory.this.createIconLabelNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseEdgeStyle(EdgeStyle object) {
            return DiagramAdapterFactory.this.createEdgeStyleAdapter();
        }

        @Override
        public Adapter caseConditionalEdgeStyle(ConditionalEdgeStyle object) {
            return DiagramAdapterFactory.this.createConditionalEdgeStyleAdapter();
        }

        @Override
        public Adapter caseDiagramPalette(DiagramPalette object) {
            return DiagramAdapterFactory.this.createDiagramPaletteAdapter();
        }

        @Override
        public Adapter caseNodePalette(NodePalette object) {
            return DiagramAdapterFactory.this.createNodePaletteAdapter();
        }

        @Override
        public Adapter caseEdgePalette(EdgePalette object) {
            return DiagramAdapterFactory.this.createEdgePaletteAdapter();
        }

        @Override
        public Adapter caseTool(Tool object) {
            return DiagramAdapterFactory.this.createToolAdapter();
        }

        @Override
        public Adapter caseDeleteTool(DeleteTool object) {
            return DiagramAdapterFactory.this.createDeleteToolAdapter();
        }

        @Override
        public Adapter caseDropTool(DropTool object) {
            return DiagramAdapterFactory.this.createDropToolAdapter();
        }

        @Override
        public Adapter caseEdgeTool(EdgeTool object) {
            return DiagramAdapterFactory.this.createEdgeToolAdapter();
        }

        @Override
        public Adapter caseEdgeReconnectionTool(EdgeReconnectionTool object) {
            return DiagramAdapterFactory.this.createEdgeReconnectionToolAdapter();
        }

        @Override
        public Adapter caseLabelEditTool(LabelEditTool object) {
            return DiagramAdapterFactory.this.createLabelEditToolAdapter();
        }

        @Override
        public Adapter caseNodeTool(NodeTool object) {
            return DiagramAdapterFactory.this.createNodeToolAdapter();
        }

        @Override
        public Adapter caseSourceEdgeEndReconnectionTool(SourceEdgeEndReconnectionTool object) {
            return DiagramAdapterFactory.this.createSourceEdgeEndReconnectionToolAdapter();
        }

        @Override
        public Adapter caseTargetEdgeEndReconnectionTool(TargetEdgeEndReconnectionTool object) {
            return DiagramAdapterFactory.this.createTargetEdgeEndReconnectionToolAdapter();
        }

        @Override
        public Adapter caseCreateView(CreateView object) {
            return DiagramAdapterFactory.this.createCreateViewAdapter();
        }

        @Override
        public Adapter caseDeleteView(DeleteView object) {
            return DiagramAdapterFactory.this.createDeleteViewAdapter();
        }

        @Override
        public Adapter caseSelectionDescription(SelectionDescription object) {
            return DiagramAdapterFactory.this.createSelectionDescriptionAdapter();
        }

        @Override
        public Adapter caseToolSection(ToolSection object) {
            return DiagramAdapterFactory.this.createToolSectionAdapter();
        }

        @Override
        public Adapter caseDiagramToolSection(DiagramToolSection object) {
            return DiagramAdapterFactory.this.createDiagramToolSectionAdapter();
        }

        @Override
        public Adapter caseNodeToolSection(NodeToolSection object) {
            return DiagramAdapterFactory.this.createNodeToolSectionAdapter();
        }

        @Override
        public Adapter caseEdgeToolSection(EdgeToolSection object) {
            return DiagramAdapterFactory.this.createEdgeToolSectionAdapter();
        }

        @Override
        public Adapter caseDropNodeTool(DropNodeTool object) {
            return DiagramAdapterFactory.this.createDropNodeToolAdapter();
        }

        @Override
        public Adapter caseRepresentationDescription(RepresentationDescription object) {
            return DiagramAdapterFactory.this.createRepresentationDescriptionAdapter();
        }

        @Override
        public Adapter caseLabelStyle(LabelStyle object) {
            return DiagramAdapterFactory.this.createLabelStyleAdapter();
        }

        @Override
        public Adapter caseConditional(Conditional object) {
            return DiagramAdapterFactory.this.createConditionalAdapter();
        }

        @Override
        public Adapter caseOperation(Operation object) {
            return DiagramAdapterFactory.this.createOperationAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return DiagramAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DiagramAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = DiagramPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
     * the model. <!-- end-user-doc -->
     *
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param target
     *         the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription <em>Description</em>}'. <!-- begin-user-doc
     * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore a case
     * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription
     */
    public Adapter createDiagramDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription <em>Element Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription
     */
    public Adapter createDiagramElementDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.NodeDescription
     * <em>Node Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription
     */
    public Adapter createNodeDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription
     * <em>Edge Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription
     */
    public Adapter createEdgeDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription <em>Layout Strategy
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     */
    public Adapter createLayoutStrategyDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription <em>List Layout Strategy
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription
     */
    public Adapter createListLayoutStrategyDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription <em>Free Form Layout
     * Strategy Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription
     */
    public Adapter createFreeFormLayoutStrategyDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.LabelDescription
     * <em>Label Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelDescription
     */
    public Adapter createLabelDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription <em>Inside Label Description</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelDescription
     */
    public Adapter createInsideLabelDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription <em>Outside Label Description</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelDescription
     */
    public Adapter createOutsideLabelDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.Style
     * <em>Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Style
     */
    public Adapter createStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.BorderStyle
     * <em>Border Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle
     */
    public Adapter createBorderStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle
     * <em>Inside Label Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelStyle
     */
    public Adapter createInsideLabelStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelStyle
     * <em>Outside Label Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelStyle
     */
    public Adapter createOutsideLabelStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle
     * <em>Node Label Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle
     */
    public Adapter createNodeLabelStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription <em>Node Style Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription
     */
    public Adapter createNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle <em>Conditional Node Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle
     */
    public Adapter createConditionalNodeStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle <em>Conditional Inside Label
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle
     */
    public Adapter createConditionalInsideLabelStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle <em>Conditional Outside Label
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle
     */
    public Adapter createConditionalOutsideLabelStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription <em>Rectangular Node Style
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription
     */
    public Adapter createRectangularNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription <em>Image Node Style
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription
     */
    public Adapter createImageNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription <em>Icon Label Node Style
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription
     */
    public Adapter createIconLabelNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle
     * <em>Edge Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle
     */
    public Adapter createEdgeStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle <em>Conditional Edge Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle
     */
    public Adapter createConditionalEdgeStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette
     * <em>Palette</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette
     */
    public Adapter createDiagramPaletteAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.NodePalette
     * <em>Node Palette</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette
     */
    public Adapter createNodePaletteAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.EdgePalette
     * <em>Edge Palette</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette
     */
    public Adapter createEdgePaletteAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.Tool
     * <em>Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Tool
     */
    public Adapter createToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.DeleteTool
     * <em>Delete Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DeleteTool
     */
    public Adapter createDeleteToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.DropTool <em>Drop
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DropTool
     */
    public Adapter createDropToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.EdgeTool <em>Edge
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeTool
     */
    public Adapter createEdgeToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool <em>Edge Reconnection Tool</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool
     */
    public Adapter createEdgeReconnectionToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.LabelEditTool
     * <em>Label Edit Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelEditTool
     */
    public Adapter createLabelEditToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.NodeTool <em>Node
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeTool
     */
    public Adapter createNodeToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool <em>Source Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool
     */
    public Adapter createSourceEdgeEndReconnectionToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool <em>Target Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool
     */
    public Adapter createTargetEdgeEndReconnectionToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.CreateView
     * <em>Create View</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.CreateView
     */
    public Adapter createCreateViewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.DeleteView
     * <em>Delete View</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DeleteView
     */
    public Adapter createDeleteViewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription <em>Selection Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SelectionDescription
     */
    public Adapter createSelectionDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.ToolSection
     * <em>Tool Section</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ToolSection
     */
    public Adapter createToolSectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramToolSection <em>Tool Section</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramToolSection
     */
    public Adapter createDiagramToolSectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.NodeToolSection
     * <em>Node Tool Section</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeToolSection
     */
    public Adapter createNodeToolSectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.EdgeToolSection
     * <em>Edge Tool Section</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeToolSection
     */
    public Adapter createEdgeToolSectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.diagram.DropNodeTool
     * <em>Drop Node Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DropNodeTool
     */
    public Adapter createDropNodeToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RepresentationDescription
     * <em>Representation Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.RepresentationDescription
     */
    public Adapter createRepresentationDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LabelStyle <em>Label
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.LabelStyle
     */
    public Adapter createLabelStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Conditional
     * <em>Conditional</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.Conditional
     */
    public Adapter createConditionalAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Operation
     * <em>Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.Operation
     */
    public Adapter createOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // DiagramAdapterFactory
