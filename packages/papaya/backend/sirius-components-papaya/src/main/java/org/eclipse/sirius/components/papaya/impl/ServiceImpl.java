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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Message;
import org.eclipse.sirius.components.papaya.MessageEmitter;
import org.eclipse.sirius.components.papaya.MessageListener;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Service;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Service</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl#getListenedMessages <em>Listened Messages</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl#getEmittedMessages <em>Emitted Messages</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl#getCalls <em>Calls</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceImpl extends NamedElementImpl implements Service {
    /**
     * The cached value of the '{@link #getListenedMessages() <em>Listened Messages</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getListenedMessages()
     * @generated
     * @ordered
     */
    protected EList<Message> listenedMessages;

    /**
     * The cached value of the '{@link #getEmittedMessages() <em>Emitted Messages</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEmittedMessages()
     * @generated
     * @ordered
     */
    protected EList<Message> emittedMessages;

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
    public EList<Message> getEmittedMessages() {
        if (this.emittedMessages == null) {
            this.emittedMessages = new EObjectWithInverseResolvingEList.ManyInverse<>(Message.class, this, PapayaPackage.SERVICE__EMITTED_MESSAGES, PapayaPackage.MESSAGE__EMITTED_BY);
        }
        return this.emittedMessages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Message> getListenedMessages() {
        if (this.listenedMessages == null) {
            this.listenedMessages = new EObjectWithInverseResolvingEList.ManyInverse<>(Message.class, this, PapayaPackage.SERVICE__LISTENED_MESSAGES, PapayaPackage.MESSAGE__LISTENED_BY);
        }
        return this.listenedMessages;
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
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.SERVICE__LISTENED_MESSAGES:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getListenedMessages()).basicAdd(otherEnd, msgs);
            case PapayaPackage.SERVICE__EMITTED_MESSAGES:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getEmittedMessages()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.SERVICE__LISTENED_MESSAGES:
                return ((InternalEList<?>) this.getListenedMessages()).basicRemove(otherEnd, msgs);
            case PapayaPackage.SERVICE__EMITTED_MESSAGES:
                return ((InternalEList<?>) this.getEmittedMessages()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.SERVICE__LISTENED_MESSAGES:
                return this.getListenedMessages();
            case PapayaPackage.SERVICE__EMITTED_MESSAGES:
                return this.getEmittedMessages();
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
            case PapayaPackage.SERVICE__LISTENED_MESSAGES:
                this.getListenedMessages().clear();
                this.getListenedMessages().addAll((Collection<? extends Message>) newValue);
                return;
            case PapayaPackage.SERVICE__EMITTED_MESSAGES:
                this.getEmittedMessages().clear();
                this.getEmittedMessages().addAll((Collection<? extends Message>) newValue);
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
            case PapayaPackage.SERVICE__LISTENED_MESSAGES:
                this.getListenedMessages().clear();
                return;
            case PapayaPackage.SERVICE__EMITTED_MESSAGES:
                this.getEmittedMessages().clear();
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
            case PapayaPackage.SERVICE__LISTENED_MESSAGES:
                return this.listenedMessages != null && !this.listenedMessages.isEmpty();
            case PapayaPackage.SERVICE__EMITTED_MESSAGES:
                return this.emittedMessages != null && !this.emittedMessages.isEmpty();
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
                case PapayaPackage.SERVICE__LISTENED_MESSAGES:
                    return PapayaPackage.MESSAGE_LISTENER__LISTENED_MESSAGES;
                default:
                    return -1;
            }
        }
        if (baseClass == MessageEmitter.class) {
            switch (derivedFeatureID) {
                case PapayaPackage.SERVICE__EMITTED_MESSAGES:
                    return PapayaPackage.MESSAGE_EMITTER__EMITTED_MESSAGES;
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
                case PapayaPackage.MESSAGE_LISTENER__LISTENED_MESSAGES:
                    return PapayaPackage.SERVICE__LISTENED_MESSAGES;
                default:
                    return -1;
            }
        }
        if (baseClass == MessageEmitter.class) {
            switch (baseFeatureID) {
                case PapayaPackage.MESSAGE_EMITTER__EMITTED_MESSAGES:
                    return PapayaPackage.SERVICE__EMITTED_MESSAGES;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

} // ServiceImpl
