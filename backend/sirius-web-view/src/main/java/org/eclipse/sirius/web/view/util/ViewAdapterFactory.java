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
package org.eclipse.sirius.web.view.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.view.DiagramDescription;
import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.RepresentationDescription;
import org.eclipse.sirius.web.view.Style;
import org.eclipse.sirius.web.view.View;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.web.view.ViewPackage
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
        public Adapter caseStyle(Style object) {
            return ViewAdapterFactory.this.createStyleAdapter();
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.web.view.View <em>View</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.web.view.View
     * @generated
     */
    public Adapter createViewAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.web.view.RepresentationDescription
     * <em>Representation Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.web.view.RepresentationDescription
     * @generated
     */
    public Adapter createRepresentationDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.web.view.DiagramDescription <em>Diagram
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.web.view.DiagramDescription
     * @generated
     */
    public Adapter createDiagramDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.web.view.DiagramElementDescription
     * <em>Diagram Element Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.web.view.DiagramElementDescription
     * @generated
     */
    public Adapter createDiagramElementDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.web.view.NodeDescription <em>Node
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.web.view.NodeDescription
     * @generated
     */
    public Adapter createNodeDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.web.view.EdgeDescription <em>Edge
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.web.view.EdgeDescription
     * @generated
     */
    public Adapter createEdgeDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.web.view.Style <em>Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.web.view.Style
     * @generated
     */
    public Adapter createStyleAdapter() {
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
