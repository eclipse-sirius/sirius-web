/**
 * Copyright (c) 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.web.domain.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.domain.Feature;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Feature</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.impl.FeatureImpl#isOptional <em>Optional</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.impl.FeatureImpl#isMany <em>Many</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FeatureImpl extends NamedElementImpl implements Feature {
    /**
     * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isOptional()
     * @generated
     * @ordered
     */
    protected static final boolean OPTIONAL_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isOptional() <em>Optional</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isOptional()
     * @generated
     * @ordered
     */
    protected boolean optional = OPTIONAL_EDEFAULT;

    /**
     * The default value of the '{@link #isMany() <em>Many</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isMany()
     * @generated
     * @ordered
     */
    protected static final boolean MANY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isMany() <em>Many</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isMany()
     * @generated
     * @ordered
     */
    protected boolean many = MANY_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FeatureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DomainPackage.Literals.FEATURE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isOptional() {
        return this.optional;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOptional(boolean newOptional) {
        boolean oldOptional = this.optional;
        this.optional = newOptional;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.FEATURE__OPTIONAL, oldOptional, this.optional));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isMany() {
        return this.many;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMany(boolean newMany) {
        boolean oldMany = this.many;
        this.many = newMany;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.FEATURE__MANY, oldMany, this.many));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case DomainPackage.FEATURE__OPTIONAL:
            return this.isOptional();
        case DomainPackage.FEATURE__MANY:
            return this.isMany();
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
        case DomainPackage.FEATURE__OPTIONAL:
            this.setOptional((Boolean) newValue);
            return;
        case DomainPackage.FEATURE__MANY:
            this.setMany((Boolean) newValue);
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
        case DomainPackage.FEATURE__OPTIONAL:
            this.setOptional(OPTIONAL_EDEFAULT);
            return;
        case DomainPackage.FEATURE__MANY:
            this.setMany(MANY_EDEFAULT);
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
        case DomainPackage.FEATURE__OPTIONAL:
            return this.optional != OPTIONAL_EDEFAULT;
        case DomainPackage.FEATURE__MANY:
            return this.many != MANY_EDEFAULT;
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
        result.append(" (optional: "); //$NON-NLS-1$
        result.append(this.optional);
        result.append(", many: "); //$NON-NLS-1$
        result.append(this.many);
        result.append(')');
        return result.toString();
    }

} // FeatureImpl
