/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.MessageEmitter;
import org.eclipse.sirius.components.papaya.MessageListener;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Publication;
import org.eclipse.sirius.components.papaya.Service;
import org.eclipse.sirius.components.papaya.Subscription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Service</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl#getSubscriptions <em>Subscriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl#getPublications <em>Publications</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl#getCalls <em>Calls</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceImpl extends NamedElementImpl implements Service {
    /**
     * The cached value of the '{@link #getSubscriptions() <em>Subscriptions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSubscriptions()
     * @generated
     * @ordered
     */
    protected EList<Subscription> subscriptions;

    /**
     * The cached value of the '{@link #getPublications() <em>Publications</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPublications()
     * @generated
     * @ordered
     */
    protected EList<Publication> publications;

    /**
     * The cached value of the '{@link #getCalls() <em>Calls</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getCalls()
     * @generated
     * @ordered
     */
    protected EList<Service> calls;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ServiceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.SERVICE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Publication> getPublications() {
        if (this.publications == null) {
            this.publications = new EObjectContainmentEList<>(Publication.class, this, PapayaPackage.SERVICE__PUBLICATIONS);
        }
        return this.publications;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Subscription> getSubscriptions() {
        if (this.subscriptions == null) {
            this.subscriptions = new EObjectContainmentEList<>(Subscription.class, this, PapayaPackage.SERVICE__SUBSCRIPTIONS);
        }
        return this.subscriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Service> getCalls() {
        if (this.calls == null) {
            this.calls = new EObjectResolvingEList<>(Service.class, this, PapayaPackage.SERVICE__CALLS);
        }
        return this.calls;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.SERVICE__SUBSCRIPTIONS:
                return ((InternalEList<?>) this.getSubscriptions()).basicRemove(otherEnd, msgs);
            case PapayaPackage.SERVICE__PUBLICATIONS:
                return ((InternalEList<?>) this.getPublications()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.SERVICE__SUBSCRIPTIONS:
                return this.getSubscriptions();
            case PapayaPackage.SERVICE__PUBLICATIONS:
                return this.getPublications();
            case PapayaPackage.SERVICE__CALLS:
                return this.getCalls();
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
            case PapayaPackage.SERVICE__SUBSCRIPTIONS:
                this.getSubscriptions().clear();
                this.getSubscriptions().addAll((Collection<? extends Subscription>) newValue);
                return;
            case PapayaPackage.SERVICE__PUBLICATIONS:
                this.getPublications().clear();
                this.getPublications().addAll((Collection<? extends Publication>) newValue);
                return;
            case PapayaPackage.SERVICE__CALLS:
                this.getCalls().clear();
                this.getCalls().addAll((Collection<? extends Service>) newValue);
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
            case PapayaPackage.SERVICE__SUBSCRIPTIONS:
                this.getSubscriptions().clear();
                return;
            case PapayaPackage.SERVICE__PUBLICATIONS:
                this.getPublications().clear();
                return;
            case PapayaPackage.SERVICE__CALLS:
                this.getCalls().clear();
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
            case PapayaPackage.SERVICE__SUBSCRIPTIONS:
                return this.subscriptions != null && !this.subscriptions.isEmpty();
            case PapayaPackage.SERVICE__PUBLICATIONS:
                return this.publications != null && !this.publications.isEmpty();
            case PapayaPackage.SERVICE__CALLS:
                return this.calls != null && !this.calls.isEmpty();
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
        if (baseClass == MessageListener.class) {
            switch (derivedFeatureID) {
                case PapayaPackage.SERVICE__SUBSCRIPTIONS:
                    return PapayaPackage.MESSAGE_LISTENER__SUBSCRIPTIONS;
                default:
                    return -1;
            }
        }
        if (baseClass == MessageEmitter.class) {
            switch (derivedFeatureID) {
                case PapayaPackage.SERVICE__PUBLICATIONS:
                    return PapayaPackage.MESSAGE_EMITTER__PUBLICATIONS;
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
        if (baseClass == MessageListener.class) {
            switch (baseFeatureID) {
                case PapayaPackage.MESSAGE_LISTENER__SUBSCRIPTIONS:
                    return PapayaPackage.SERVICE__SUBSCRIPTIONS;
                default:
                    return -1;
            }
        }
        if (baseClass == MessageEmitter.class) {
            switch (baseFeatureID) {
                case PapayaPackage.MESSAGE_EMITTER__PUBLICATIONS:
                    return PapayaPackage.SERVICE__PUBLICATIONS;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // ServiceImpl
