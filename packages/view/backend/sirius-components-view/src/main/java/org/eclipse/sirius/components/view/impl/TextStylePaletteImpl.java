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
package org.eclipse.sirius.components.view.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.TextStyleDescription;
import org.eclipse.sirius.components.view.TextStylePalette;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Text Style Palette</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.TextStylePaletteImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.TextStylePaletteImpl#getStyles <em>Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TextStylePaletteImpl extends MinimalEObjectImpl.Container implements TextStylePalette {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyles() <em>Styles</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getStyles()
     * @generated
     * @ordered
     */
    protected EList<TextStyleDescription> styles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TextStylePaletteImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.TEXT_STYLE_PALETTE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXT_STYLE_PALETTE__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TextStyleDescription> getStyles() {
        if (this.styles == null) {
            this.styles = new EObjectContainmentEList<>(TextStyleDescription.class, this, ViewPackage.TEXT_STYLE_PALETTE__STYLES);
        }
        return this.styles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.TEXT_STYLE_PALETTE__STYLES:
                return ((InternalEList<?>) this.getStyles()).basicRemove(otherEnd, msgs);
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
            case ViewPackage.TEXT_STYLE_PALETTE__NAME:
                return this.getName();
            case ViewPackage.TEXT_STYLE_PALETTE__STYLES:
                return this.getStyles();
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
            case ViewPackage.TEXT_STYLE_PALETTE__NAME:
                this.setName((String) newValue);
                return;
            case ViewPackage.TEXT_STYLE_PALETTE__STYLES:
                this.getStyles().clear();
                this.getStyles().addAll((Collection<? extends TextStyleDescription>) newValue);
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
            case ViewPackage.TEXT_STYLE_PALETTE__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case ViewPackage.TEXT_STYLE_PALETTE__STYLES:
                this.getStyles().clear();
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
            case ViewPackage.TEXT_STYLE_PALETTE__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case ViewPackage.TEXT_STYLE_PALETTE__STYLES:
                return this.styles != null && !this.styles.isEmpty();
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
        result.append(" (name: ");
        result.append(this.name);
        result.append(')');
        return result.toString();
    }

} // TextStylePaletteImpl
