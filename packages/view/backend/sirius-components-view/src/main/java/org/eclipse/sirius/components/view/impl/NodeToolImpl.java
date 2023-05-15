/**
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.SelectionDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Tool</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.NodeToolImpl#getSelectionDescription <em>Selection
 * Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeToolImpl extends ToolImpl implements NodeTool {
    /**
     * The cached value of the '{@link #getSelectionDescription() <em>Selection Description</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionDescription()
     * @generated
     * @ordered
     */
    protected SelectionDescription selectionDescription;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodeToolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.NODE_TOOL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectionDescription getSelectionDescription() {
        return this.selectionDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSelectionDescription(SelectionDescription newSelectionDescription, NotificationChain msgs) {
        SelectionDescription oldSelectionDescription = this.selectionDescription;
        this.selectionDescription = newSelectionDescription;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION, oldSelectionDescription, newSelectionDescription);
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
    public void setSelectionDescription(SelectionDescription newSelectionDescription) {
        if (newSelectionDescription != this.selectionDescription) {
            NotificationChain msgs = null;
            if (this.selectionDescription != null)
                msgs = ((InternalEObject) this.selectionDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION, null, msgs);
            if (newSelectionDescription != null)
                msgs = ((InternalEObject) newSelectionDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION, null, msgs);
            msgs = this.basicSetSelectionDescription(newSelectionDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION, newSelectionDescription, newSelectionDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION:
                return this.basicSetSelectionDescription(null, msgs);
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
            case ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION:
                return this.getSelectionDescription();
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
            case ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION:
                this.setSelectionDescription((SelectionDescription) newValue);
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
            case ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION:
                this.setSelectionDescription((SelectionDescription) null);
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
            case ViewPackage.NODE_TOOL__SELECTION_DESCRIPTION:
                return this.selectionDescription != null;
        }
        return super.eIsSet(featureID);
    }

} // NodeToolImpl
