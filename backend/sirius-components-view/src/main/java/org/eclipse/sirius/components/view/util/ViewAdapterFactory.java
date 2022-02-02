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
package org.eclipse.sirius.components.view.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.BorderStyle;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.CreateView;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DeleteView;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.DropTool;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyle;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.Style;
import org.eclipse.sirius.components.view.Tool;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;

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
        public Adapter caseNodeStyle(NodeStyle object) {
            return ViewAdapterFactory.this.createNodeStyleAdapter();
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.NodeStyle <em>Node
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.NodeStyle
     * @generated
     */
    public Adapter createNodeStyleAdapter() {
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
