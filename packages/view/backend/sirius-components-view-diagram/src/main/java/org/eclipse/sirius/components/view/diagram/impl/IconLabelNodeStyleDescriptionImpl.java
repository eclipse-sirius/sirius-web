/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Icon Label Node Style Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl#getBorderRadius <em>Border Radius</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl#getBorderSize <em>Border Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl#getBorderLineStyle <em>Border Line Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl#getChildrenLayoutStrategy <em>Children Layout Strategy</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl#getBackground <em>Background</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IconLabelNodeStyleDescriptionImpl extends MinimalEObjectImpl.Container implements IconLabelNodeStyleDescription {

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
    protected static final LineStyle BORDER_LINE_STYLE_EDEFAULT = LineStyle.SOLID;

    /**
	 * The cached value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getBorderLineStyle()
	 * @generated
	 * @ordered
	 */
    protected LineStyle borderLineStyle = BORDER_LINE_STYLE_EDEFAULT;

    /**
	 * The cached value of the '{@link #getChildrenLayoutStrategy() <em>Children Layout Strategy</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getChildrenLayoutStrategy()
	 * @generated
	 * @ordered
	 */
    protected LayoutStrategyDescription childrenLayoutStrategy;

    /**
	 * The cached value of the '{@link #getBackground() <em>Background</em>}' reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
    protected UserColor background;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected IconLabelNodeStyleDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.ICON_LABEL_NODE_STYLE_DESCRIPTION;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, borderColor));
			}
		}
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, borderColor));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS, oldBorderRadius, borderRadius));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE, oldBorderSize, borderSize));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LineStyle getBorderLineStyle() {
		return borderLineStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderLineStyle(LineStyle newBorderLineStyle) {
		LineStyle oldBorderLineStyle = borderLineStyle;
		borderLineStyle = newBorderLineStyle == null ? BORDER_LINE_STYLE_EDEFAULT : newBorderLineStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE, oldBorderLineStyle, borderLineStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LayoutStrategyDescription getChildrenLayoutStrategy() {
		return childrenLayoutStrategy;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetChildrenLayoutStrategy(LayoutStrategyDescription newChildrenLayoutStrategy, NotificationChain msgs) {
		LayoutStrategyDescription oldChildrenLayoutStrategy = childrenLayoutStrategy;
		childrenLayoutStrategy = newChildrenLayoutStrategy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, oldChildrenLayoutStrategy, newChildrenLayoutStrategy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setChildrenLayoutStrategy(LayoutStrategyDescription newChildrenLayoutStrategy) {
		if (newChildrenLayoutStrategy != childrenLayoutStrategy)
		{
			NotificationChain msgs = null;
			if (childrenLayoutStrategy != null)
				msgs = ((InternalEObject)childrenLayoutStrategy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, null, msgs);
			if (newChildrenLayoutStrategy != null)
				msgs = ((InternalEObject)newChildrenLayoutStrategy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, null, msgs);
			msgs = basicSetChildrenLayoutStrategy(newChildrenLayoutStrategy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, newChildrenLayoutStrategy, newChildrenLayoutStrategy));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getBackground() {
		if (background != null && background.eIsProxy())
		{
			InternalEObject oldBackground = (InternalEObject)background;
			background = (UserColor)eResolveProxy(oldBackground);
			if (background != oldBackground)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND, oldBackground, background));
			}
		}
		return background;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetBackground() {
		return background;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBackground(UserColor newBackground) {
		UserColor oldBackground = background;
		background = newBackground;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND, oldBackground, background));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				return basicSetChildrenLayoutStrategy(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				if (resolve) return getBorderColor();
				return basicGetBorderColor();
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				return getBorderRadius();
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				return getBorderSize();
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				return getBorderLineStyle();
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				return getChildrenLayoutStrategy();
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND:
				if (resolve) return getBackground();
				return basicGetBackground();
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
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				setBorderColor((UserColor)newValue);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				setBorderRadius((Integer)newValue);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				setBorderSize((Integer)newValue);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				setBorderLineStyle((LineStyle)newValue);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				setChildrenLayoutStrategy((LayoutStrategyDescription)newValue);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND:
				setBackground((UserColor)newValue);
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
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				setBorderColor((UserColor)null);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				setBorderRadius(BORDER_RADIUS_EDEFAULT);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				setBorderSize(BORDER_SIZE_EDEFAULT);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				setChildrenLayoutStrategy((LayoutStrategyDescription)null);
				return;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND:
				setBackground((UserColor)null);
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
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				return borderColor != null;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				return borderRadius != BORDER_RADIUS_EDEFAULT;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				return borderSize != BORDER_SIZE_EDEFAULT;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				return borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				return childrenLayoutStrategy != null;
			case DiagramPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND:
				return background != null;
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

} // IconLabelNodeStyleDescriptionImpl
