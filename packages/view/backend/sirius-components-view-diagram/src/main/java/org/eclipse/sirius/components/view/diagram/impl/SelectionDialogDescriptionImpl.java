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
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Selection Dialog Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionMessage
 * <em>Selection Message</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionDialogTreeDescription
 * <em>Selection Dialog Tree Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SelectionDialogDescriptionImpl extends DialogDescriptionImpl implements SelectionDialogDescription {
    /**
     * The default value of the '{@link #getSelectionMessage() <em>Selection Message</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionMessage()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_MESSAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectionMessage() <em>Selection Message</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionMessage()
     * @generated
     * @ordered
     */
    protected String selectionMessage = SELECTION_MESSAGE_EDEFAULT;

    /**
     * The cached value of the '{@link #getSelectionDialogTreeDescription() <em>Selection Dialog Tree Description</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionDialogTreeDescription()
     * @generated
     * @ordered
     */
    protected SelectionDialogTreeDescription selectionDialogTreeDescription;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SelectionDialogDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectionMessage() {
        return this.selectionMessage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectionMessage(String newSelectionMessage) {
        String oldSelectionMessage = this.selectionMessage;
        this.selectionMessage = newSelectionMessage;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE, oldSelectionMessage, this.selectionMessage));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectionDialogTreeDescription getSelectionDialogTreeDescription() {
        return this.selectionDialogTreeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSelectionDialogTreeDescription(SelectionDialogTreeDescription newSelectionDialogTreeDescription, NotificationChain msgs) {
        SelectionDialogTreeDescription oldSelectionDialogTreeDescription = this.selectionDialogTreeDescription;
        this.selectionDialogTreeDescription = newSelectionDialogTreeDescription;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION,
                    oldSelectionDialogTreeDescription, newSelectionDialogTreeDescription);
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
    public void setSelectionDialogTreeDescription(SelectionDialogTreeDescription newSelectionDialogTreeDescription) {
        if (newSelectionDialogTreeDescription != this.selectionDialogTreeDescription) {
            NotificationChain msgs = null;
            if (this.selectionDialogTreeDescription != null)
                msgs = ((InternalEObject) this.selectionDialogTreeDescription).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, null, msgs);
            if (newSelectionDialogTreeDescription != null)
                msgs = ((InternalEObject) newSelectionDialogTreeDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION,
                        null, msgs);
            msgs = this.basicSetSelectionDialogTreeDescription(newSelectionDialogTreeDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, newSelectionDialogTreeDescription,
                    newSelectionDialogTreeDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                return this.basicSetSelectionDialogTreeDescription(null, msgs);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                return this.getSelectionMessage();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                return this.getSelectionDialogTreeDescription();
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                this.setSelectionMessage((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                this.setSelectionDialogTreeDescription((SelectionDialogTreeDescription) newValue);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                this.setSelectionMessage(SELECTION_MESSAGE_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                this.setSelectionDialogTreeDescription((SelectionDialogTreeDescription) null);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE:
                return SELECTION_MESSAGE_EDEFAULT == null ? this.selectionMessage != null : !SELECTION_MESSAGE_EDEFAULT.equals(this.selectionMessage);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                return this.selectionDialogTreeDescription != null;
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
        result.append(" (selectionMessage: ");
        result.append(this.selectionMessage);
        result.append(')');
        return result.toString();
    }

} // SelectionDialogDescriptionImpl
