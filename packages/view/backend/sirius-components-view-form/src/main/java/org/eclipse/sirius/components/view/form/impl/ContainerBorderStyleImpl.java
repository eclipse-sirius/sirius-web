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
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderRadius <em>Border Radius</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderSize <em>Border Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ContainerBorderStyleImpl#getBorderLineStyle <em>Border Line Style</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainerBorderStyleImpl extends MinimalEObjectImpl.Container implements ContainerBorderStyle {

    /**
	 * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}' reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBorderColor()
	 * @generated
	 * @ordered
	 */
    protected UserColor borderColor;

    /**
	 * The default value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBorderRadius()
	 * @generated
	 * @ordered
	 */
    protected static final int BORDER_RADIUS_EDEFAULT = 3;

    /**
	 * The cached value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBorderRadius()
	 * @generated
	 * @ordered
	 */
    protected int borderRadius = BORDER_RADIUS_EDEFAULT;

    /**
	 * The default value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBorderSize()
	 * @generated
	 * @ordered
	 */
    protected static final int BORDER_SIZE_EDEFAULT = 1;

    /**
	 * The cached value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
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
	 * The cached value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getBorderLineStyle()
	 * @generated
	 * @ordered
	 */
    protected ContainerBorderLineStyle borderLineStyle = BORDER_LINE_STYLE_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ContainerBorderStyleImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.CONTAINER_BORDER_STYLE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getBorderColor() {
		if (borderColor != null && borderColor.eIsProxy())
		{
			InternalEObject oldBorderColor = (InternalEObject)borderColor;
			borderColor = (UserColor)eResolveProxy(oldBorderColor);
			if (borderColor != oldBorderColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR, oldBorderColor, borderColor));
			}
		}
		return borderColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetBorderColor() {
		return borderColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderColor(UserColor newBorderColor) {
		UserColor oldBorderColor = borderColor;
		borderColor = newBorderColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR, oldBorderColor, borderColor));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getBorderRadius() {
		return borderRadius;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderRadius(int newBorderRadius) {
		int oldBorderRadius = borderRadius;
		borderRadius = newBorderRadius;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS, oldBorderRadius, borderRadius));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getBorderSize() {
		return borderSize;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderSize(int newBorderSize) {
		int oldBorderSize = borderSize;
		borderSize = newBorderSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE, oldBorderSize, borderSize));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ContainerBorderLineStyle getBorderLineStyle() {
		return borderLineStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderLineStyle(ContainerBorderLineStyle newBorderLineStyle) {
		ContainerBorderLineStyle oldBorderLineStyle = borderLineStyle;
		borderLineStyle = newBorderLineStyle == null ? BORDER_LINE_STYLE_EDEFAULT : newBorderLineStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE, oldBorderLineStyle, borderLineStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
				if (resolve) return getBorderColor();
				return basicGetBorderColor();
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
				return getBorderRadius();
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
				return getBorderSize();
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
				return getBorderLineStyle();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
				setBorderColor((UserColor)newValue);
				return;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
				setBorderRadius((Integer)newValue);
				return;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
				setBorderSize((Integer)newValue);
				return;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
				setBorderLineStyle((ContainerBorderLineStyle)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
				setBorderColor((UserColor)null);
				return;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
				setBorderRadius(BORDER_RADIUS_EDEFAULT);
				return;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
				setBorderSize(BORDER_SIZE_EDEFAULT);
				return;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
				setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_COLOR:
				return borderColor != null;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_RADIUS:
				return borderRadius != BORDER_RADIUS_EDEFAULT;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_SIZE:
				return borderSize != BORDER_SIZE_EDEFAULT;
			case FormPackage.CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE:
				return borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (borderRadius: ");
		result.append(borderRadius);
		result.append(", borderSize: ");
		result.append(borderSize);
		result.append(", borderLineStyle: ");
		result.append(borderLineStyle);
		result.append(')');
		return result.toString();
	}

} // ContainerBorderStyleImpl
