/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.For;
import org.eclipse.sirius.components.view.If;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.UserColor;
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
        public Adapter caseColorPalette(ColorPalette object) {
            return ViewAdapterFactory.this.createColorPaletteAdapter();
        }

        @Override
        public Adapter caseFixedColor(FixedColor object) {
            return ViewAdapterFactory.this.createFixedColorAdapter();
        }

        @Override
        public Adapter caseUserColor(UserColor object) {
            return ViewAdapterFactory.this.createUserColorAdapter();
        }

        @Override
        public Adapter caseRepresentationDescription(RepresentationDescription object) {
            return ViewAdapterFactory.this.createRepresentationDescriptionAdapter();
        }

        @Override
        public Adapter caseLabelStyle(LabelStyle object) {
            return ViewAdapterFactory.this.createLabelStyleAdapter();
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
        public Adapter caseLet(Let object) {
            return ViewAdapterFactory.this.createLetAdapter();
        }

        @Override
        public Adapter caseIf(If object) {
            return ViewAdapterFactory.this.createIfAdapter();
        }

        @Override
        public Adapter caseConditional(Conditional object) {
            return ViewAdapterFactory.this.createConditionalAdapter();
        }

        @Override
        public Adapter caseFor(For object) {
            return ViewAdapterFactory.this.createForAdapter();
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.ColorPalette <em>Color
     * Palette</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.ColorPalette
     * @generated
     */
    public Adapter createColorPaletteAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.FixedColor <em>Fixed
     * Color</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.FixedColor
     * @generated
     */
    public Adapter createFixedColorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.UserColor <em>User
     * Color</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.UserColor
     * @generated
     */
    public Adapter createUserColorAdapter() {
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Let <em>Let</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.Let
     * @generated
     */
    public Adapter createLetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.If <em>If</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.If
     * @generated
     */
    public Adapter createIfAdapter() {
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.For <em>For</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.For
     * @generated
     */
    public Adapter createForAdapter() {
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
