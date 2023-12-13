/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.form.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.form.ContainerBorderLineStyle;
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Container Border Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderColor <em>Border
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderRadius <em>Border
 * Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderSize <em>Border
 * Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderLineStyle <em>Border Line
 * Style</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainerBorderStyleImpl extends MinimalEObjectImpl.Container implements ContainerBorderStyle {

    /**
     * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected UserColor borderColor;

    /**
     * The default value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBorderRadius()
     * @generated
     * @ordered
     */
    protected static final int BORDER_RADIUS_EDEFAULT = 3;

    /**
     * The cached value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBorderRadius()
     * @generated
     * @ordered
     */
    protected int borderRadius = BORDER_RADIUS_EDEFAULT;

    /**
     * The default value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderSize()
     * @generated
     * @ordered
     */
    protected static final int BORDER_SIZE_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderSize()
     * @generated
     * @ordered
     */
    protected int borderSize = BORDER_SIZE_EDEFAULT;

    /**
     * The default value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderLineStyle()
     */
    protected static final ContainerBorderLineStyle BORDER_LINE_STYLE_EDEFAULT = ContainerBorderLineStyle.SOLID;

    /**
     * The cached value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBorderLineStyle()
     * @generated
     * @ordered
     */
    protected ContainerBorderLineStyle borderLineStyle = BORDER_LINE_STYLE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContainerBorderStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.CONTAINER_BORDER_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getBorderColor() {
        if (this.borderColor != null && this.borderColor.eIsProxy()) {
            InternalEObject oldBorderColor = (InternalEObject) this.borderColor;
            this.borderColor = (UserColor) this.eResolveProxy(oldBorderColor);
            if (this.borderColor != oldBorderColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR, oldBorderColor, this.borderColor));
            }
        }
        return this.borderColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderColor(UserColor newBorderColor) {
        UserColor oldBorderColor = this.borderColor;
        this.borderColor = newBorderColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR, oldBorderColor, this.borderColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetBorderColor() {
        return this.borderColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getBorderRadius() {
        return this.borderRadius;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderRadius(int newBorderRadius) {
        int oldBorderRadius = this.borderRadius;
        this.borderRadius = newBorderRadius;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS, oldBorderRadius, this.borderRadius));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getBorderSize() {
        return this.borderSize;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderSize(int newBorderSize) {
        int oldBorderSize = this.borderSize;
        this.borderSize = newBorderSize;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE, oldBorderSize, this.borderSize));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ContainerBorderLineStyle getBorderLineStyle() {
        return this.borderLineStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderLineStyle(ContainerBorderLineStyle newBorderLineStyle) {
        ContainerBorderLineStyle oldBorderLineStyle = this.borderLineStyle;
        this.borderLineStyle = newBorderLineStyle == null ? BORDER_LINE_STYLE_EDEFAULT : newBorderLineStyle;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE, oldBorderLineStyle, this.borderLineStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
                if (resolve)
                    return this.getBorderColor();
                return this.basicGetBorderColor();
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
                return this.getBorderRadius();
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
                return this.getBorderSize();
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
                return this.getBorderLineStyle();
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
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
                this.setBorderColor((UserColor) newValue);
                return;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
                this.setBorderRadius((Integer) newValue);
                return;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
                this.setBorderSize((Integer) newValue);
                return;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
                this.setBorderLineStyle((ContainerBorderLineStyle) newValue);
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
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
                this.setBorderColor((UserColor) null);
                return;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
                this.setBorderRadius(BORDER_RADIUS_EDEFAULT);
                return;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
                this.setBorderSize(BORDER_SIZE_EDEFAULT);
                return;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
                this.setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
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
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
                return this.borderColor != null;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
                return this.borderRadius != BORDER_RADIUS_EDEFAULT;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
                return this.borderSize != BORDER_SIZE_EDEFAULT;
            case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
                return this.borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
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
        result.append(" (borderRadius: ");
        result.append(this.borderRadius);
        result.append(", borderSize: ");
        result.append(this.borderSize);
        result.append(", borderLineStyle: ");
        result.append(this.borderLineStyle);
        result.append(')');
        return result.toString();
    }

} // ContainerBorderStyleImpl
