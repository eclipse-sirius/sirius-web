/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widgets.reference.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @generated
 * @see org.eclipse.sirius.components.widgets.reference.ReferencePackage
 */
public class ReferenceAdapterFactory extends AdapterFactoryImpl {

    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static ReferencePackage modelPackage;

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ReferenceSwitch<Adapter> modelSwitch = new ReferenceSwitch<>() {
        @Override
        public Adapter caseReferenceWidgetDescription(ReferenceWidgetDescription object) {
            return ReferenceAdapterFactory.this.createReferenceWidgetDescriptionAdapter();
        }

        @Override
        public Adapter caseReferenceWidgetDescriptionStyle(ReferenceWidgetDescriptionStyle object) {
            return ReferenceAdapterFactory.this.createReferenceWidgetDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalReferenceWidgetDescriptionStyle(ConditionalReferenceWidgetDescriptionStyle object) {
            return ReferenceAdapterFactory.this.createConditionalReferenceWidgetDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseWidgetDescription(WidgetDescription object) {
            return ReferenceAdapterFactory.this.createWidgetDescriptionAdapter();
        }

        @Override
        public Adapter caseWidgetDescriptionStyle(WidgetDescriptionStyle object) {
            return ReferenceAdapterFactory.this.createWidgetDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseLabelStyle(LabelStyle object) {
            return ReferenceAdapterFactory.this.createLabelStyleAdapter();
        }

        @Override
        public Adapter caseConditional(Conditional object) {
            return ReferenceAdapterFactory.this.createConditionalAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return ReferenceAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ReferenceAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ReferencePackage.eINSTANCE;
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
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription <em>Widget Description</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription
     */
    public Adapter createReferenceWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle <em>Widget Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle
     */
    public Adapter createReferenceWidgetDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle
     * <em>Conditional Reference Widget Description Style</em>}'. <!-- begin-user-doc --> This default implementation
     * returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
     * cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle
     */
    public Adapter createConditionalReferenceWidgetDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.WidgetDescription
     * <em>Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.form.WidgetDescription
     */
    public Adapter createWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.WidgetDescriptionStyle <em>Widget Description Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.form.WidgetDescriptionStyle
     */
    public Adapter createWidgetDescriptionStyleAdapter() {
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
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // ReferenceAdapterFactory
