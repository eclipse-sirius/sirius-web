/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.papaya.Channel;
import org.eclipse.sirius.components.papaya.Message;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Publication;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Publication</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.PublicationImpl#getChannel <em>Channel</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.PublicationImpl#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PublicationImpl extends MinimalEObjectImpl.Container implements Publication {
    /**
     * The cached value of the '{@link #getChannel() <em>Channel</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getChannel()
     * @generated
     * @ordered
     */
    protected Channel channel;

    /**
     * The cached value of the '{@link #getMessage() <em>Message</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getMessage()
     * @generated
     * @ordered
     */
    protected Message message;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PublicationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.PUBLICATION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Channel getChannel() {
        if (this.channel != null && this.channel.eIsProxy()) {
            InternalEObject oldChannel = (InternalEObject) this.channel;
            this.channel = (Channel) this.eResolveProxy(oldChannel);
            if (this.channel != oldChannel) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapayaPackage.PUBLICATION__CHANNEL, oldChannel, this.channel));
            }
        }
        return this.channel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Channel basicGetChannel() {
        return this.channel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setChannel(Channel newChannel) {
        Channel oldChannel = this.channel;
        this.channel = newChannel;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.PUBLICATION__CHANNEL, oldChannel, this.channel));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Message getMessage() {
        if (this.message != null && this.message.eIsProxy()) {
            InternalEObject oldMessage = (InternalEObject) this.message;
            this.message = (Message) this.eResolveProxy(oldMessage);
            if (this.message != oldMessage) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, PapayaPackage.PUBLICATION__MESSAGE, oldMessage, this.message));
            }
        }
        return this.message;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Message basicGetMessage() {
        return this.message;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetMessage(Message newMessage, NotificationChain msgs) {
        Message oldMessage = this.message;
        this.message = newMessage;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PapayaPackage.PUBLICATION__MESSAGE, oldMessage, newMessage);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMessage(Message newMessage) {
        if (newMessage != this.message) {
            NotificationChain msgs = null;
            if (this.message != null)
                msgs = ((InternalEObject) this.message).eInverseRemove(this, PapayaPackage.MESSAGE__EMITTED_BY, Message.class, msgs);
            if (newMessage != null)
                msgs = ((InternalEObject) newMessage).eInverseAdd(this, PapayaPackage.MESSAGE__EMITTED_BY, Message.class, msgs);
            msgs = this.basicSetMessage(newMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.PUBLICATION__MESSAGE, newMessage, newMessage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.PUBLICATION__MESSAGE:
                if (this.message != null)
                    msgs = ((InternalEObject) this.message).eInverseRemove(this, PapayaPackage.MESSAGE__EMITTED_BY, Message.class, msgs);
                return this.basicSetMessage((Message) otherEnd, msgs);
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
            case PapayaPackage.PUBLICATION__MESSAGE:
                return this.basicSetMessage(null, msgs);
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
            case PapayaPackage.PUBLICATION__CHANNEL:
                if (resolve)
                    return this.getChannel();
                return this.basicGetChannel();
            case PapayaPackage.PUBLICATION__MESSAGE:
                if (resolve)
                    return this.getMessage();
                return this.basicGetMessage();
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
            case PapayaPackage.PUBLICATION__CHANNEL:
                this.setChannel((Channel) newValue);
                return;
            case PapayaPackage.PUBLICATION__MESSAGE:
                this.setMessage((Message) newValue);
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
            case PapayaPackage.PUBLICATION__CHANNEL:
                this.setChannel((Channel) null);
                return;
            case PapayaPackage.PUBLICATION__MESSAGE:
                this.setMessage((Message) null);
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
            case PapayaPackage.PUBLICATION__CHANNEL:
                return this.channel != null;
            case PapayaPackage.PUBLICATION__MESSAGE:
                return this.message != null;
        }
        return super.eIsSet(featureID);
    }

} // PublicationImpl
