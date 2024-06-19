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
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramStyleDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Style Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramStyleDescriptionImpl#getBackground
 * <em>Background</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramStyleDescriptionImpl extends MinimalEObjectImpl.Container implements DiagramStyleDescription {

    /**
     * The cached value of the '{@link #getBackground() <em>Background</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBackground()
     */
    protected UserColor background;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramStyleDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.DIAGRAM_STYLE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getBackground() {
        if (this.background != null && this.background.eIsProxy()) {
            InternalEObject oldBackground = (InternalEObject) this.background;
            this.background = (UserColor) this.eResolveProxy(oldBackground);
            if (this.background != oldBackground) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.DIAGRAM_STYLE_DESCRIPTION__BACKGROUND, oldBackground, this.background));
            }
        }
        return this.background;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBackground(UserColor newBackground) {
        UserColor oldBackground = this.background;
        this.background = newBackground;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_STYLE_DESCRIPTION__BACKGROUND, oldBackground, this.background));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetBackground() {
        return this.background;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.DIAGRAM_STYLE_DESCRIPTION__BACKGROUND:
                if (resolve)
                    return this.getBackground();
                return this.basicGetBackground();
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
            case DiagramPackage.DIAGRAM_STYLE_DESCRIPTION__BACKGROUND:
                this.setBackground((UserColor) newValue);
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
            case DiagramPackage.DIAGRAM_STYLE_DESCRIPTION__BACKGROUND:
                this.setBackground(null);
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
            case DiagramPackage.DIAGRAM_STYLE_DESCRIPTION__BACKGROUND:
                return this.background != null;
        }
        return super.eIsSet(featureID);
    }

} // DiagramStyleDescriptionImpl
