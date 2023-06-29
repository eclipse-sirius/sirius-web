/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Create Instance</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.CreateInstanceImpl#getTypeName <em>Type Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.CreateInstanceImpl#getReferenceName <em>Reference Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.CreateInstanceImpl#getVariableName <em>Variable Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreateInstanceImpl extends OperationImpl implements CreateInstance {
    /**
     * The default value of the '{@link #getTypeName() <em>Type Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getTypeName()
     * @generated
     * @ordered
     */
    protected static final String TYPE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTypeName() <em>Type Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getTypeName()
     * @generated
     * @ordered
     */
    protected String typeName = TYPE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getReferenceName() <em>Reference Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getReferenceName()
     * @generated
     * @ordered
     */
    protected static final String REFERENCE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceName() <em>Reference Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getReferenceName()
     * @generated
     * @ordered
     */
    protected String referenceName = REFERENCE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getVariableName()
     * @generated
     * @ordered
     */
    protected static final String VARIABLE_NAME_EDEFAULT = "newInstance";

    /**
     * The cached value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getVariableName()
     * @generated
     * @ordered
     */
    protected String variableName = VARIABLE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CreateInstanceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.CREATE_INSTANCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTypeName(String newTypeName) {
        String oldTypeName = this.typeName;
        this.typeName = newTypeName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.CREATE_INSTANCE__TYPE_NAME, oldTypeName, this.typeName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getReferenceName() {
        return this.referenceName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setReferenceName(String newReferenceName) {
        String oldReferenceName = this.referenceName;
        this.referenceName = newReferenceName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.CREATE_INSTANCE__REFERENCE_NAME, oldReferenceName, this.referenceName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getVariableName() {
        return this.variableName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setVariableName(String newVariableName) {
        String oldVariableName = this.variableName;
        this.variableName = newVariableName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.CREATE_INSTANCE__VARIABLE_NAME, oldVariableName, this.variableName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ViewPackage.CREATE_INSTANCE__TYPE_NAME:
                return this.getTypeName();
            case ViewPackage.CREATE_INSTANCE__REFERENCE_NAME:
                return this.getReferenceName();
            case ViewPackage.CREATE_INSTANCE__VARIABLE_NAME:
                return this.getVariableName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ViewPackage.CREATE_INSTANCE__TYPE_NAME:
                this.setTypeName((String) newValue);
                return;
            case ViewPackage.CREATE_INSTANCE__REFERENCE_NAME:
                this.setReferenceName((String) newValue);
                return;
            case ViewPackage.CREATE_INSTANCE__VARIABLE_NAME:
                this.setVariableName((String) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ViewPackage.CREATE_INSTANCE__TYPE_NAME:
                this.setTypeName(TYPE_NAME_EDEFAULT);
                return;
            case ViewPackage.CREATE_INSTANCE__REFERENCE_NAME:
                this.setReferenceName(REFERENCE_NAME_EDEFAULT);
                return;
            case ViewPackage.CREATE_INSTANCE__VARIABLE_NAME:
                this.setVariableName(VARIABLE_NAME_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ViewPackage.CREATE_INSTANCE__TYPE_NAME:
                return TYPE_NAME_EDEFAULT == null ? this.typeName != null : !TYPE_NAME_EDEFAULT.equals(this.typeName);
            case ViewPackage.CREATE_INSTANCE__REFERENCE_NAME:
                return REFERENCE_NAME_EDEFAULT == null ? this.referenceName != null : !REFERENCE_NAME_EDEFAULT.equals(this.referenceName);
            case ViewPackage.CREATE_INSTANCE__VARIABLE_NAME:
                return VARIABLE_NAME_EDEFAULT == null ? this.variableName != null : !VARIABLE_NAME_EDEFAULT.equals(this.variableName);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (typeName: ");
        result.append(this.typeName);
        result.append(", referenceName: ");
        result.append(this.referenceName);
        result.append(", variableName: ");
        result.append(this.variableName);
        result.append(')');
        return result.toString();
    }

} // CreateInstanceImpl
