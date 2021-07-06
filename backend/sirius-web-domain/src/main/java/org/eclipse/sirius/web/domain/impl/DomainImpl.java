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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.web.domain.Domain;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.domain.Entity;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Domain</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.impl.DomainImpl#getUri <em>Uri</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.impl.DomainImpl#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DomainImpl extends NamedElementImpl implements Domain {
    /**
     * The default value of the '{@link #getUri() <em>Uri</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getUri()
     * @generated
     * @ordered
     */
    protected static final String URI_EDEFAULT = "domain://sample"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getUri()
     * @generated
     * @ordered
     */
    protected String uri = URI_EDEFAULT;

    /**
     * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getTypes()
     * @generated
     * @ordered
     */
    protected EList<Entity> types;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DomainImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DomainPackage.Literals.DOMAIN;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getUri() {
        return this.uri;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUri(String newUri) {
        String oldUri = this.uri;
        this.uri = newUri;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN__URI, oldUri, this.uri));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Entity> getTypes() {
        if (this.types == null) {
            this.types = new EObjectContainmentEList<>(Entity.class, this, DomainPackage.DOMAIN__TYPES);
        }
        return this.types;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DomainPackage.DOMAIN__TYPES:
            return ((InternalEList<?>) this.getTypes()).basicRemove(otherEnd, msgs);
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
        case DomainPackage.DOMAIN__URI:
            return this.getUri();
        case DomainPackage.DOMAIN__TYPES:
            return this.getTypes();
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
        case DomainPackage.DOMAIN__URI:
            this.setUri((String) newValue);
            return;
        case DomainPackage.DOMAIN__TYPES:
            this.getTypes().clear();
            this.getTypes().addAll((Collection<? extends Entity>) newValue);
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
        case DomainPackage.DOMAIN__URI:
            this.setUri(URI_EDEFAULT);
            return;
        case DomainPackage.DOMAIN__TYPES:
            this.getTypes().clear();
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
        case DomainPackage.DOMAIN__URI:
            return URI_EDEFAULT == null ? this.uri != null : !URI_EDEFAULT.equals(this.uri);
        case DomainPackage.DOMAIN__TYPES:
            return this.types != null && !this.types.isEmpty();
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
        result.append(" (uri: "); //$NON-NLS-1$
        result.append(this.uri);
        result.append(')');
        return result.toString();
    }

} // DomainImpl
