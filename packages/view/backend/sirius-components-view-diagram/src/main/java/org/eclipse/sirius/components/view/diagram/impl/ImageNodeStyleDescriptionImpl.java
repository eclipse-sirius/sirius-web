/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Image Node Style Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderColor <em>Border
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderRadius <em>Border
 * Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderSize <em>Border
 * Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getBorderLineStyle
 * <em>Border Line Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#getShape
 * <em>Shape</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl#isPositionDependentRotation
 * <em>Position Dependent Rotation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImageNodeStyleDescriptionImpl extends StyleImpl implements ImageNodeStyleDescription {

    /**
     * The default value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderRadius()
     */
    protected static final int BORDER_RADIUS_EDEFAULT = 3;
    /**
     * The default value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderSize()
     */
    protected static final int BORDER_SIZE_EDEFAULT = 1;
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
     * The default value of the '{@link #getShape() <em>Shape</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getShape()
     */
    protected static final String SHAPE_EDEFAULT = null;
    /**
     * The default value of the '{@link #isPositionDependentRotation() <em>Position Dependent Rotation</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isPositionDependentRotation()
     */
    protected static final boolean POSITION_DEPENDENT_ROTATION_EDEFAULT = false;
    /**
     * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderColor()
     */
    protected UserColor borderColor;
    /**
     * The cached value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderRadius()
     */
    protected int borderRadius = BORDER_RADIUS_EDEFAULT;
    /**
     * The cached value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderSize()
     */
    protected int borderSize = BORDER_SIZE_EDEFAULT;
    /**
     * The cached value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderLineStyle()
     */
    protected LineStyle borderLineStyle = BORDER_LINE_STYLE_EDEFAULT;
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
     * The cached value of the '{@link #isPositionDependentRotation() <em>Position Dependent Rotation</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isPositionDependentRotation()
     */
    protected boolean positionDependentRotation = POSITION_DEPENDENT_ROTATION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ImageNodeStyleDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION;
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, this.borderColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, this.borderColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS, oldBorderRadius, this.borderRadius));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE, oldBorderSize, this.borderSize));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LineStyle getBorderLineStyle() {
        return this.borderLineStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderLineStyle(LineStyle newBorderLineStyle) {
        LineStyle oldBorderLineStyle = this.borderLineStyle;
        this.borderLineStyle = newBorderLineStyle == null ? BORDER_LINE_STYLE_EDEFAULT : newBorderLineStyle;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE, oldBorderLineStyle, this.borderLineStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getShape() {
        return this.shape;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setShape(String newShape) {
        String oldShape = this.shape;
        this.shape = newShape;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE, oldShape, this.shape));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isPositionDependentRotation() {
        return this.positionDependentRotation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPositionDependentRotation(boolean newPositionDependentRotation) {
        boolean oldPositionDependentRotation = this.positionDependentRotation;
        this.positionDependentRotation = newPositionDependentRotation;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION, oldPositionDependentRotation,
                    this.positionDependentRotation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                if (resolve)
                    return this.getBorderColor();
                return this.basicGetBorderColor();
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                return this.getBorderRadius();
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                return this.getBorderSize();
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                return this.getBorderLineStyle();
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
                return this.getShape();
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
                return this.isPositionDependentRotation();
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
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                this.setBorderColor((UserColor) newValue);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                this.setBorderRadius((Integer) newValue);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                this.setBorderSize((Integer) newValue);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                this.setBorderLineStyle((LineStyle) newValue);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
                this.setShape((String) newValue);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
                this.setPositionDependentRotation((Boolean) newValue);
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
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                this.setBorderColor((UserColor) null);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                this.setBorderRadius(BORDER_RADIUS_EDEFAULT);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                this.setBorderSize(BORDER_SIZE_EDEFAULT);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                this.setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
                this.setShape(SHAPE_EDEFAULT);
                return;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
                this.setPositionDependentRotation(POSITION_DEPENDENT_ROTATION_EDEFAULT);
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
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                return this.borderColor != null;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                return this.borderRadius != BORDER_RADIUS_EDEFAULT;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                return this.borderSize != BORDER_SIZE_EDEFAULT;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                return this.borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
                return SHAPE_EDEFAULT == null ? this.shape != null : !SHAPE_EDEFAULT.equals(this.shape);
            case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
                return this.positionDependentRotation != POSITION_DEPENDENT_ROTATION_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == BorderStyle.class) {
            switch (derivedFeatureID) {
                case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                    return DiagramPackage.BORDER_STYLE__BORDER_COLOR;
                case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                    return DiagramPackage.BORDER_STYLE__BORDER_RADIUS;
                case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                    return DiagramPackage.BORDER_STYLE__BORDER_SIZE;
                case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                    return DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE;
                default:
                    return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == BorderStyle.class) {
            switch (baseFeatureID) {
                case DiagramPackage.BORDER_STYLE__BORDER_COLOR:
                    return DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR;
                case DiagramPackage.BORDER_STYLE__BORDER_RADIUS:
                    return DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS;
                case DiagramPackage.BORDER_STYLE__BORDER_SIZE:
                    return DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE;
                case DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE:
                    return DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(", shape: ");
        result.append(this.shape);
        result.append(", positionDependentRotation: ");
        result.append(this.positionDependentRotation);
        result.append(')');
        return result.toString();
    }

} // ImageNodeStyleDescriptionImpl
