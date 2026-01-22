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
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Image Node Style Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderRadius <em>Border Radius</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderSize <em>Border Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderLineStyle <em>Border Line Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getChildrenLayoutStrategy <em>Children Layout Strategy</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getShape <em>Shape</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#isPositionDependentRotation <em>Position Dependent Rotation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImageNodeStyleDescriptionImpl extends MinimalEObjectImpl.Container implements ImageNodeStyleDescription {

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
	 * The default value of the '{@link #getShape() <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
    protected static final String SHAPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getShape() <em>Shape</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getShape()
     */
    protected String shape = SHAPE_EDEFAULT;

    /**
	 * The default value of the '{@link #isPositionDependentRotation() <em>Position Dependent Rotation</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isPositionDependentRotation()
	 * @generated
	 * @ordered
	 */
    protected static final boolean POSITION_DEPENDENT_ROTATION_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isPositionDependentRotation() <em>Position Dependent Rotation</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isPositionDependentRotation()
	 * @generated
	 * @ordered
	 */
    protected boolean positionDependentRotation = POSITION_DEPENDENT_ROTATION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ImageNodeStyleDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, borderColor));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, borderColor));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS, oldBorderRadius, borderRadius));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE, oldBorderSize, borderSize));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE, oldBorderLineStyle, borderLineStyle));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, oldChildrenLayoutStrategy, newChildrenLayoutStrategy);
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
				msgs = ((InternalEObject)childrenLayoutStrategy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, null, msgs);
			if (newChildrenLayoutStrategy != null)
				msgs = ((InternalEObject)newChildrenLayoutStrategy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, null, msgs);
			msgs = basicSetChildrenLayoutStrategy(newChildrenLayoutStrategy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, newChildrenLayoutStrategy, newChildrenLayoutStrategy));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getShape() {
		return shape;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setShape(String newShape) {
		String oldShape = shape;
		shape = newShape;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE, oldShape, shape));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isPositionDependentRotation() {
		return positionDependentRotation;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPositionDependentRotation(boolean newPositionDependentRotation) {
		boolean oldPositionDependentRotation = positionDependentRotation;
		positionDependentRotation = newPositionDependentRotation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION, oldPositionDependentRotation, positionDependentRotation));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
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
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				if (resolve) return getBorderColor();
				return basicGetBorderColor();
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				return getBorderRadius();
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				return getBorderSize();
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				return getBorderLineStyle();
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				return getChildrenLayoutStrategy();
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
				return getShape();
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
				return isPositionDependentRotation();
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
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				setBorderColor((UserColor)newValue);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				setBorderRadius((Integer)newValue);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				setBorderSize((Integer)newValue);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				setBorderLineStyle((LineStyle)newValue);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				setChildrenLayoutStrategy((LayoutStrategyDescription)newValue);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
				setShape((String)newValue);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
				setPositionDependentRotation((Boolean)newValue);
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
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				setBorderColor((UserColor)null);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				setBorderRadius(BORDER_RADIUS_EDEFAULT);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				setBorderSize(BORDER_SIZE_EDEFAULT);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				setChildrenLayoutStrategy((LayoutStrategyDescription)null);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
				setShape(SHAPE_EDEFAULT);
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
				setPositionDependentRotation(POSITION_DEPENDENT_ROTATION_EDEFAULT);
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
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
				return borderColor != null;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
				return borderRadius != BORDER_RADIUS_EDEFAULT;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
				return borderSize != BORDER_SIZE_EDEFAULT;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
				return borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				return childrenLayoutStrategy != null;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
				return SHAPE_EDEFAULT == null ? shape != null : !SHAPE_EDEFAULT.equals(shape);
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
				return positionDependentRotation != POSITION_DEPENDENT_ROTATION_EDEFAULT;
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
		result.append(", shape: ");
		result.append(shape);
		result.append(", positionDependentRotation: ");
		result.append(positionDependentRotation);
		result.append(')');
		return result.toString();
	}

} // ImageNodeStyleDescriptionImpl
