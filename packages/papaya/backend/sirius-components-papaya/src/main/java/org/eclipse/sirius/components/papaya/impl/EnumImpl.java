/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.papaya.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.EnumLiteral;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Tag;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Enum</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.EnumImpl#getKey <em>Key</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.EnumImpl#getValue <em>Value</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.EnumImpl#getLiterals <em>Literals</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumImpl extends TypeImpl implements org.eclipse.sirius.components.papaya.Enum {
    /**
     * The default value of the '{@link #getKey() <em>Key</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getKey()
     * @generated
     * @ordered
     */
    protected static final String KEY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKey() <em>Key</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getKey()
     * @generated
     * @ordered
     */
    protected String key = KEY_EDEFAULT;

    /**
     * The default value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected String value = VALUE_EDEFAULT;

    /**
     * The cached value of the '{@link #getLiterals() <em>Literals</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLiterals()
     * @generated
     * @ordered
     */
    protected EList<EnumLiteral> literals;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EnumImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.ENUM;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getKey() {
        return this.key;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setKey(String newKey) {
        String oldKey = this.key;
        this.key = newKey;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.ENUM__KEY, oldKey, this.key));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValue(String newValue) {
        String oldValue = this.value;
        this.value = newValue;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.ENUM__VALUE, oldValue, this.value));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EnumLiteral> getLiterals() {
        if (this.literals == null) {
            this.literals = new EObjectContainmentEList<>(EnumLiteral.class, this, PapayaPackage.ENUM__LITERALS);
        }
        return this.literals;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.ENUM__LITERALS:
                return ((InternalEList<?>) this.getLiterals()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.ENUM__KEY:
                return this.getKey();
            case PapayaPackage.ENUM__VALUE:
                return this.getValue();
            case PapayaPackage.ENUM__LITERALS:
                return this.getLiterals();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PapayaPackage.ENUM__KEY:
                this.setKey((String) newValue);
                return;
            case PapayaPackage.ENUM__VALUE:
                this.setValue((String) newValue);
                return;
            case PapayaPackage.ENUM__LITERALS:
                this.getLiterals().clear();
                this.getLiterals().addAll((Collection<? extends EnumLiteral>) newValue);
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
            case PapayaPackage.ENUM__KEY:
                this.setKey(KEY_EDEFAULT);
                return;
            case PapayaPackage.ENUM__VALUE:
                this.setValue(VALUE_EDEFAULT);
                return;
            case PapayaPackage.ENUM__LITERALS:
                this.getLiterals().clear();
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
            case PapayaPackage.ENUM__KEY:
                return KEY_EDEFAULT == null ? this.key != null : !KEY_EDEFAULT.equals(this.key);
            case PapayaPackage.ENUM__VALUE:
                return VALUE_EDEFAULT == null ? this.value != null : !VALUE_EDEFAULT.equals(this.value);
            case PapayaPackage.ENUM__LITERALS:
                return this.literals != null && !this.literals.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == Tag.class) {
            switch (derivedFeatureID) {
                case PapayaPackage.ENUM__KEY:
                    return PapayaPackage.TAG__KEY;
                case PapayaPackage.ENUM__VALUE:
                    return PapayaPackage.TAG__VALUE;
                default:
                    return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == Tag.class) {
            switch (baseFeatureID) {
                case PapayaPackage.TAG__KEY:
                    return PapayaPackage.ENUM__KEY;
                case PapayaPackage.TAG__VALUE:
                    return PapayaPackage.ENUM__VALUE;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (key: ");
        result.append(this.key);
        result.append(", value: ");
        result.append(this.value);
        result.append(')');
        return result.toString();
    }

} // EnumImpl
