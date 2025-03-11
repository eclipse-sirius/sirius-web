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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.Event;
import org.eclipse.sirius.components.papaya.Message;
import org.eclipse.sirius.components.papaya.MessageEmitter;
import org.eclipse.sirius.components.papaya.MessageListener;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Event</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.EventImpl#getEmittedBy <em>Emitted By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.EventImpl#getListenedBy <em>Listened By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.EventImpl#getCausedBy <em>Caused By</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventImpl extends NamedElementImpl implements Event {
    /**
     * The cached value of the '{@link #getEmittedBy() <em>Emitted By</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getEmittedBy()
     * @generated
     * @ordered
     */
    protected EList<MessageEmitter> emittedBy;

    /**
     * The cached value of the '{@link #getListenedBy() <em>Listened By</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getListenedBy()
     * @generated
     * @ordered
     */
    protected EList<MessageListener> listenedBy;

    /**
     * The cached value of the '{@link #getCausedBy() <em>Caused By</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getCausedBy()
     * @generated
     * @ordered
     */
    protected Message causedBy;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EventImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.EVENT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<MessageEmitter> getEmittedBy() {
        if (this.emittedBy == null) {
            this.emittedBy = new EObjectWithInverseResolvingEList.ManyInverse<>(MessageEmitter.class, this, PapayaPackage.EVENT__EMITTED_BY, PapayaPackage.MESSAGE_EMITTER__EMITTED_MESSAGES);
        }
        return this.emittedBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<MessageListener> getListenedBy() {
        if (this.listenedBy == null) {
            this.listenedBy = new EObjectWithInverseResolvingEList.ManyInverse<>(MessageListener.class, this, PapayaPackage.EVENT__LISTENED_BY, PapayaPackage.MESSAGE_LISTENER__LISTENED_MESSAGES);
        }
        return this.listenedBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Message getCausedBy() {
        if (this.causedBy != null && this.causedBy.eIsProxy()) {
            InternalEObject oldCausedBy = (InternalEObject) this.causedBy;
            this.causedBy = (Message) this.eResolveProxy(oldCausedBy);
            if (this.causedBy != oldCausedBy) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapayaPackage.EVENT__CAUSED_BY, oldCausedBy, this.causedBy));
            }
        }
        return this.causedBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Message basicGetCausedBy() {
        return this.causedBy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCausedBy(Message newCausedBy) {
        Message oldCausedBy = this.causedBy;
        this.causedBy = newCausedBy;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.EVENT__CAUSED_BY, oldCausedBy, this.causedBy));
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
            case PapayaPackage.EVENT__EMITTED_BY:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getEmittedBy()).basicAdd(otherEnd, msgs);
            case PapayaPackage.EVENT__LISTENED_BY:
                return ((InternalEList<InternalEObject>) (InternalEList<?>) this.getListenedBy()).basicAdd(otherEnd, msgs);
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
            case PapayaPackage.EVENT__EMITTED_BY:
                return ((InternalEList<?>) this.getEmittedBy()).basicRemove(otherEnd, msgs);
            case PapayaPackage.EVENT__LISTENED_BY:
                return ((InternalEList<?>) this.getListenedBy()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.EVENT__EMITTED_BY:
                return this.getEmittedBy();
            case PapayaPackage.EVENT__LISTENED_BY:
                return this.getListenedBy();
            case PapayaPackage.EVENT__CAUSED_BY:
                if (resolve)
                    return this.getCausedBy();
                return this.basicGetCausedBy();
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
            case PapayaPackage.EVENT__EMITTED_BY:
                this.getEmittedBy().clear();
                this.getEmittedBy().addAll((Collection<? extends MessageEmitter>) newValue);
                return;
            case PapayaPackage.EVENT__LISTENED_BY:
                this.getListenedBy().clear();
                this.getListenedBy().addAll((Collection<? extends MessageListener>) newValue);
                return;
            case PapayaPackage.EVENT__CAUSED_BY:
                this.setCausedBy((Message) newValue);
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
            case PapayaPackage.EVENT__EMITTED_BY:
                this.getEmittedBy().clear();
                return;
            case PapayaPackage.EVENT__LISTENED_BY:
                this.getListenedBy().clear();
                return;
            case PapayaPackage.EVENT__CAUSED_BY:
                this.setCausedBy((Message) null);
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
            case PapayaPackage.EVENT__EMITTED_BY:
                return this.emittedBy != null && !this.emittedBy.isEmpty();
            case PapayaPackage.EVENT__LISTENED_BY:
                return this.listenedBy != null && !this.listenedBy.isEmpty();
            case PapayaPackage.EVENT__CAUSED_BY:
                return this.causedBy != null;
        }
        return super.eIsSet(featureID);
    }

} // EventImpl
