/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.BorderStyle;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.CreateView;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DeleteView;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.DropTool;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.LinkDescription;
import org.eclipse.sirius.components.view.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.ListDescription;
import org.eclipse.sirius.components.view.ListDescriptionStyle;
import org.eclipse.sirius.components.view.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.Style;
import org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.Tool;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.WidgetDescription;
import org.eclipse.sirius.components.view.WidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage
 * @generated
 */
public class ViewAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static ViewPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ViewAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ViewPackage.eINSTANCE;
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
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ViewSwitch<Adapter> modelSwitch = new ViewSwitch<>() {
        @Override
        public Adapter caseView(View object) {
            return ViewAdapterFactory.this.createViewAdapter();
        }

        @Override
        public Adapter caseRepresentationDescription(RepresentationDescription object) {
            return ViewAdapterFactory.this.createRepresentationDescriptionAdapter();
        }

        @Override
        public Adapter caseDiagramDescription(DiagramDescription object) {
            return ViewAdapterFactory.this.createDiagramDescriptionAdapter();
        }

        @Override
        public Adapter caseDiagramElementDescription(DiagramElementDescription object) {
            return ViewAdapterFactory.this.createDiagramElementDescriptionAdapter();
        }

        @Override
        public Adapter caseNodeDescription(NodeDescription object) {
            return ViewAdapterFactory.this.createNodeDescriptionAdapter();
        }

        @Override
        public Adapter caseEdgeDescription(EdgeDescription object) {
            return ViewAdapterFactory.this.createEdgeDescriptionAdapter();
        }

        @Override
        public Adapter caseLabelStyle(LabelStyle object) {
            return ViewAdapterFactory.this.createLabelStyleAdapter();
        }

        @Override
        public Adapter caseBorderStyle(BorderStyle object) {
            return ViewAdapterFactory.this.createBorderStyleAdapter();
        }

        @Override
        public Adapter caseStyle(Style object) {
            return ViewAdapterFactory.this.createStyleAdapter();
        }

        @Override
        public Adapter caseNodeStyleDescription(NodeStyleDescription object) {
            return ViewAdapterFactory.this.createNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseRectangularNodeStyleDescription(RectangularNodeStyleDescription object) {
            return ViewAdapterFactory.this.createRectangularNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseImageNodeStyleDescription(ImageNodeStyleDescription object) {
            return ViewAdapterFactory.this.createImageNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseIconLabelNodeStyleDescription(IconLabelNodeStyleDescription object) {
            return ViewAdapterFactory.this.createIconLabelNodeStyleDescriptionAdapter();
        }

        @Override
        public Adapter caseLayoutStrategyDescription(LayoutStrategyDescription object) {
            return ViewAdapterFactory.this.createLayoutStrategyDescriptionAdapter();
        }

        @Override
        public Adapter caseFreeFormLayoutStrategyDescription(FreeFormLayoutStrategyDescription object) {
            return ViewAdapterFactory.this.createFreeFormLayoutStrategyDescriptionAdapter();
        }

        @Override
        public Adapter caseListLayoutStrategyDescription(ListLayoutStrategyDescription object) {
            return ViewAdapterFactory.this.createListLayoutStrategyDescriptionAdapter();
        }

        @Override
        public Adapter caseEdgeStyle(EdgeStyle object) {
            return ViewAdapterFactory.this.createEdgeStyleAdapter();
        }

        @Override
        public Adapter caseTool(Tool object) {
            return ViewAdapterFactory.this.createToolAdapter();
        }

        @Override
        public Adapter caseLabelEditTool(LabelEditTool object) {
            return ViewAdapterFactory.this.createLabelEditToolAdapter();
        }

        @Override
        public Adapter caseDeleteTool(DeleteTool object) {
            return ViewAdapterFactory.this.createDeleteToolAdapter();
        }

        @Override
        public Adapter caseNodeTool(NodeTool object) {
            return ViewAdapterFactory.this.createNodeToolAdapter();
        }

        @Override
        public Adapter caseEdgeTool(EdgeTool object) {
            return ViewAdapterFactory.this.createEdgeToolAdapter();
        }

        @Override
        public Adapter caseEdgeReconnectionTool(EdgeReconnectionTool object) {
            return ViewAdapterFactory.this.createEdgeReconnectionToolAdapter();
        }

        @Override
        public Adapter caseSourceEdgeEndReconnectionTool(SourceEdgeEndReconnectionTool object) {
            return ViewAdapterFactory.this.createSourceEdgeEndReconnectionToolAdapter();
        }

        @Override
        public Adapter caseTargetEdgeEndReconnectionTool(TargetEdgeEndReconnectionTool object) {
            return ViewAdapterFactory.this.createTargetEdgeEndReconnectionToolAdapter();
        }

        @Override
        public Adapter caseDropTool(DropTool object) {
            return ViewAdapterFactory.this.createDropToolAdapter();
        }

        @Override
        public Adapter caseOperation(Operation object) {
            return ViewAdapterFactory.this.createOperationAdapter();
        }

        @Override
        public Adapter caseChangeContext(ChangeContext object) {
            return ViewAdapterFactory.this.createChangeContextAdapter();
        }

        @Override
        public Adapter caseCreateInstance(CreateInstance object) {
            return ViewAdapterFactory.this.createCreateInstanceAdapter();
        }

        @Override
        public Adapter caseSetValue(SetValue object) {
            return ViewAdapterFactory.this.createSetValueAdapter();
        }

        @Override
        public Adapter caseUnsetValue(UnsetValue object) {
            return ViewAdapterFactory.this.createUnsetValueAdapter();
        }

        @Override
        public Adapter caseDeleteElement(DeleteElement object) {
            return ViewAdapterFactory.this.createDeleteElementAdapter();
        }

        @Override
        public Adapter caseCreateView(CreateView object) {
            return ViewAdapterFactory.this.createCreateViewAdapter();
        }

        @Override
        public Adapter caseDeleteView(DeleteView object) {
            return ViewAdapterFactory.this.createDeleteViewAdapter();
        }

        @Override
        public Adapter caseConditional(Conditional object) {
            return ViewAdapterFactory.this.createConditionalAdapter();
        }

        @Override
        public Adapter caseConditionalNodeStyle(ConditionalNodeStyle object) {
            return ViewAdapterFactory.this.createConditionalNodeStyleAdapter();
        }

        @Override
        public Adapter caseConditionalEdgeStyle(ConditionalEdgeStyle object) {
            return ViewAdapterFactory.this.createConditionalEdgeStyleAdapter();
        }

        @Override
        public Adapter caseFormDescription(FormDescription object) {
            return ViewAdapterFactory.this.createFormDescriptionAdapter();
        }

        @Override
        public Adapter caseWidgetDescription(WidgetDescription object) {
            return ViewAdapterFactory.this.createWidgetDescriptionAdapter();
        }

        @Override
        public Adapter caseTextfieldDescription(TextfieldDescription object) {
            return ViewAdapterFactory.this.createTextfieldDescriptionAdapter();
        }

        @Override
        public Adapter caseCheckboxDescription(CheckboxDescription object) {
            return ViewAdapterFactory.this.createCheckboxDescriptionAdapter();
        }

        @Override
        public Adapter caseSelectDescription(SelectDescription object) {
            return ViewAdapterFactory.this.createSelectDescriptionAdapter();
        }

        @Override
        public Adapter caseMultiSelectDescription(MultiSelectDescription object) {
            return ViewAdapterFactory.this.createMultiSelectDescriptionAdapter();
        }

        @Override
        public Adapter caseTextAreaDescription(TextAreaDescription object) {
            return ViewAdapterFactory.this.createTextAreaDescriptionAdapter();
        }

        @Override
        public Adapter caseRadioDescription(RadioDescription object) {
            return ViewAdapterFactory.this.createRadioDescriptionAdapter();
        }

        @Override
        public Adapter caseBarChartDescription(BarChartDescription object) {
            return ViewAdapterFactory.this.createBarChartDescriptionAdapter();
        }

        @Override
        public Adapter casePieChartDescription(PieChartDescription object) {
            return ViewAdapterFactory.this.createPieChartDescriptionAdapter();
        }

        @Override
        public Adapter caseFlexboxContainerDescription(FlexboxContainerDescription object) {
            return ViewAdapterFactory.this.createFlexboxContainerDescriptionAdapter();
        }

        @Override
        public Adapter caseButtonDescription(ButtonDescription object) {
            return ViewAdapterFactory.this.createButtonDescriptionAdapter();
        }

        @Override
        public Adapter caseWidgetDescriptionStyle(WidgetDescriptionStyle object) {
            return ViewAdapterFactory.this.createWidgetDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseTextfieldDescriptionStyle(TextfieldDescriptionStyle object) {
            return ViewAdapterFactory.this.createTextfieldDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalTextfieldDescriptionStyle(ConditionalTextfieldDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalTextfieldDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseCheckboxDescriptionStyle(CheckboxDescriptionStyle object) {
            return ViewAdapterFactory.this.createCheckboxDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalCheckboxDescriptionStyle(ConditionalCheckboxDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalCheckboxDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseSelectDescriptionStyle(SelectDescriptionStyle object) {
            return ViewAdapterFactory.this.createSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalSelectDescriptionStyle(ConditionalSelectDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseMultiSelectDescriptionStyle(MultiSelectDescriptionStyle object) {
            return ViewAdapterFactory.this.createMultiSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalMultiSelectDescriptionStyle(ConditionalMultiSelectDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalMultiSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseTextareaDescriptionStyle(TextareaDescriptionStyle object) {
            return ViewAdapterFactory.this.createTextareaDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalTextareaDescriptionStyle(ConditionalTextareaDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalTextareaDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseRadioDescriptionStyle(RadioDescriptionStyle object) {
            return ViewAdapterFactory.this.createRadioDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalRadioDescriptionStyle(ConditionalRadioDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalRadioDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseButtonDescriptionStyle(ButtonDescriptionStyle object) {
            return ViewAdapterFactory.this.createButtonDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalButtonDescriptionStyle(ConditionalButtonDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalButtonDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseBarChartDescriptionStyle(BarChartDescriptionStyle object) {
            return ViewAdapterFactory.this.createBarChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalBarChartDescriptionStyle(ConditionalBarChartDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalBarChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter casePieChartDescriptionStyle(PieChartDescriptionStyle object) {
            return ViewAdapterFactory.this.createPieChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalPieChartDescriptionStyle(ConditionalPieChartDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalPieChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseLabelDescription(LabelDescription object) {
            return ViewAdapterFactory.this.createLabelDescriptionAdapter();
        }

        @Override
        public Adapter caseLabelDescriptionStyle(LabelDescriptionStyle object) {
            return ViewAdapterFactory.this.createLabelDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalLabelDescriptionStyle(ConditionalLabelDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalLabelDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseLinkDescription(LinkDescription object) {
            return ViewAdapterFactory.this.createLinkDescriptionAdapter();
        }

        @Override
        public Adapter caseLinkDescriptionStyle(LinkDescriptionStyle object) {
            return ViewAdapterFactory.this.createLinkDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalLinkDescriptionStyle(ConditionalLinkDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalLinkDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseListDescription(ListDescription object) {
            return ViewAdapterFactory.this.createListDescriptionAdapter();
        }

        @Override
        public Adapter caseListDescriptionStyle(ListDescriptionStyle object) {
            return ViewAdapterFactory.this.createListDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalListDescriptionStyle(ConditionalListDescriptionStyle object) {
            return ViewAdapterFactory.this.createConditionalListDescriptionStyleAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return ViewAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.View <em>View</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.View
     * @generated
     */
    public Adapter createViewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RepresentationDescription
     * <em>Representation Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.RepresentationDescription
     * @generated
     */
    public Adapter createRepresentationDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.DiagramDescription
     * <em>Diagram Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.DiagramDescription
     * @generated
     */
    public Adapter createDiagramDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.DiagramElementDescription
     * <em>Diagram Element Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.DiagramElementDescription
     * @generated
     */
    public Adapter createDiagramElementDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.NodeDescription <em>Node
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.NodeDescription
     * @generated
     */
    public Adapter createNodeDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.EdgeDescription <em>Edge
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.EdgeDescription
     * @generated
     */
    public Adapter createEdgeDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LabelStyle <em>Label
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.LabelStyle
     * @generated
     */
    public Adapter createLabelStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.BorderStyle <em>Border
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.BorderStyle
     * @generated
     */
    public Adapter createBorderStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Style <em>Style</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.Style
     * @generated
     */
    public Adapter createStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.NodeStyleDescription
     * <em>Node Style Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.NodeStyleDescription
     * @generated
     */
    public Adapter createNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.RectangularNodeStyleDescription <em>Rectangular Node Style
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.RectangularNodeStyleDescription
     * @generated
     */
    public Adapter createRectangularNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ImageNodeStyleDescription
     * <em>Image Node Style Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ImageNodeStyleDescription
     * @generated
     */
    public Adapter createImageNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.IconLabelNodeStyleDescription <em>Icon Label Node Style
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.IconLabelNodeStyleDescription
     * @generated
     */
    public Adapter createIconLabelNodeStyleDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LayoutStrategyDescription
     * <em>Layout Strategy Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.LayoutStrategyDescription
     * @generated
     */
    public Adapter createLayoutStrategyDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription <em>Free Form Layout Strategy
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription
     * @generated
     */
    public Adapter createFreeFormLayoutStrategyDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ListLayoutStrategyDescription <em>List Layout Strategy
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ListLayoutStrategyDescription
     * @generated
     */
    public Adapter createListLayoutStrategyDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.EdgeStyle <em>Edge
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.EdgeStyle
     * @generated
     */
    public Adapter createEdgeStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Tool <em>Tool</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.Tool
     * @generated
     */
    public Adapter createToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LabelEditTool <em>Label
     * Edit Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.LabelEditTool
     * @generated
     */
    public Adapter createLabelEditToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.DeleteTool <em>Delete
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.DeleteTool
     * @generated
     */
    public Adapter createDeleteToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.NodeTool <em>Node
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.NodeTool
     * @generated
     */
    public Adapter createNodeToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.EdgeTool <em>Edge
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.EdgeTool
     * @generated
     */
    public Adapter createEdgeToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.EdgeReconnectionTool
     * <em>Edge Reconnection Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.EdgeReconnectionTool
     * @generated
     */
    public Adapter createEdgeReconnectionToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool <em>Source Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool
     * @generated
     */
    public Adapter createSourceEdgeEndReconnectionToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool <em>Target Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool
     * @generated
     */
    public Adapter createTargetEdgeEndReconnectionToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.DropTool <em>Drop
     * Tool</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.DropTool
     * @generated
     */
    public Adapter createDropToolAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Operation
     * <em>Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.Operation
     * @generated
     */
    public Adapter createOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ChangeContext <em>Change
     * Context</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ChangeContext
     * @generated
     */
    public Adapter createChangeContextAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.CreateInstance <em>Create
     * Instance</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.CreateInstance
     * @generated
     */
    public Adapter createCreateInstanceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.SetValue <em>Set
     * Value</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.SetValue
     * @generated
     */
    public Adapter createSetValueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.UnsetValue <em>Unset
     * Value</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.UnsetValue
     * @generated
     */
    public Adapter createUnsetValueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.DeleteElement <em>Delete
     * Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.DeleteElement
     * @generated
     */
    public Adapter createDeleteElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.CreateView <em>Create
     * View</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.CreateView
     * @generated
     */
    public Adapter createCreateViewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.DeleteView <em>Delete
     * View</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.DeleteView
     * @generated
     */
    public Adapter createDeleteViewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Conditional
     * <em>Conditional</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.Conditional
     * @generated
     */
    public Adapter createConditionalAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ConditionalNodeStyle
     * <em>Conditional Node Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalNodeStyle
     * @generated
     */
    public Adapter createConditionalNodeStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ConditionalEdgeStyle
     * <em>Conditional Edge Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalEdgeStyle
     * @generated
     */
    public Adapter createConditionalEdgeStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.FormDescription <em>Form
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.FormDescription
     * @generated
     */
    public Adapter createFormDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.WidgetDescription
     * <em>Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.WidgetDescription
     * @generated
     */
    public Adapter createWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.TextfieldDescription
     * <em>Textfield Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.TextfieldDescription
     * @generated
     */
    public Adapter createTextfieldDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.CheckboxDescription
     * <em>Checkbox Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.CheckboxDescription
     * @generated
     */
    public Adapter createCheckboxDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.SelectDescription
     * <em>Select Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.SelectDescription
     * @generated
     */
    public Adapter createSelectDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.MultiSelectDescription
     * <em>Multi Select Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.MultiSelectDescription
     * @generated
     */
    public Adapter createMultiSelectDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.TextAreaDescription
     * <em>Text Area Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.TextAreaDescription
     * @generated
     */
    public Adapter createTextAreaDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RadioDescription
     * <em>Radio Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.RadioDescription
     * @generated
     */
    public Adapter createRadioDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.WidgetDescriptionStyle
     * <em>Widget Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.WidgetDescriptionStyle
     * @generated
     */
    public Adapter createWidgetDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.TextfieldDescriptionStyle
     * <em>Textfield Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.TextfieldDescriptionStyle
     * @generated
     */
    public Adapter createTextfieldDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle <em>Conditional Textfield
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle
     * @generated
     */
    public Adapter createConditionalTextfieldDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.CheckboxDescriptionStyle
     * <em>Checkbox Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.CheckboxDescriptionStyle
     * @generated
     */
    public Adapter createCheckboxDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle <em>Conditional Checkbox
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle
     * @generated
     */
    public Adapter createConditionalCheckboxDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.SelectDescriptionStyle
     * <em>Select Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.SelectDescriptionStyle
     * @generated
     */
    public Adapter createSelectDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle <em>Conditional Select Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle
     * @generated
     */
    public Adapter createConditionalSelectDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.MultiSelectDescriptionStyle <em>Multi Select Description Style</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.MultiSelectDescriptionStyle
     * @generated
     */
    public Adapter createMultiSelectDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle <em>Conditional Multi Select
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle
     * @generated
     */
    public Adapter createConditionalMultiSelectDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle
     * <em>Textarea Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.TextareaDescriptionStyle
     * @generated
     */
    public Adapter createTextareaDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle <em>Conditional Textarea
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle
     * @generated
     */
    public Adapter createConditionalTextareaDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RadioDescriptionStyle
     * <em>Radio Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.RadioDescriptionStyle
     * @generated
     */
    public Adapter createRadioDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle <em>Conditional Radio Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle
     * @generated
     */
    public Adapter createConditionalRadioDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ButtonDescriptionStyle
     * <em>Button Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ButtonDescriptionStyle
     * @generated
     */
    public Adapter createButtonDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle <em>Conditional Button Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle
     * @generated
     */
    public Adapter createConditionalButtonDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.BarChartDescriptionStyle
     * <em>Bar Chart Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.BarChartDescriptionStyle
     * @generated
     */
    public Adapter createBarChartDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle <em>Conditional Bar Chart
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle
     * @generated
     */
    public Adapter createConditionalBarChartDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle
     * <em>Pie Chart Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.PieChartDescriptionStyle
     * @generated
     */
    public Adapter createPieChartDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle <em>Conditional Pie Chart
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle
     * @generated
     */
    public Adapter createConditionalPieChartDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LabelDescription
     * <em>Label Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.LabelDescription
     * @generated
     */
    public Adapter createLabelDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LabelDescriptionStyle
     * <em>Label Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.LabelDescriptionStyle
     * @generated
     */
    public Adapter createLabelDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle <em>Conditional Label Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle
     * @generated
     */
    public Adapter createConditionalLabelDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LinkDescription <em>Link
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.LinkDescription
     * @generated
     */
    public Adapter createLinkDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LinkDescriptionStyle
     * <em>Link Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.LinkDescriptionStyle
     * @generated
     */
    public Adapter createLinkDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle <em>Conditional Link Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle
     * @generated
     */
    public Adapter createConditionalLinkDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ListDescription <em>List
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ListDescription
     * @generated
     */
    public Adapter createListDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ListDescriptionStyle
     * <em>List Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ListDescriptionStyle
     * @generated
     */
    public Adapter createListDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.ConditionalListDescriptionStyle <em>Conditional List Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ConditionalListDescriptionStyle
     * @generated
     */
    public Adapter createConditionalListDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.BarChartDescription
     * <em>Bar Chart Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.BarChartDescription
     * @generated
     */
    public Adapter createBarChartDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.PieChartDescription
     * <em>Pie Chart Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.PieChartDescription
     * @generated
     */
    public Adapter createPieChartDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.FlexboxContainerDescription <em>Flexbox Container Description</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.FlexboxContainerDescription
     * @generated
     */
    public Adapter createFlexboxContainerDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ButtonDescription
     * <em>Button Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ButtonDescription
     * @generated
     */
    public Adapter createButtonDescriptionAdapter() {
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

} // ViewAdapterFactory
