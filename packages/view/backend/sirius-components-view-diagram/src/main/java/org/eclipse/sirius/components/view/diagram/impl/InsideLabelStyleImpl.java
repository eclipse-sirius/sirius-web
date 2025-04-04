/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.impl.LabelStyleImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Inside Label Style</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getBorderColor <em>Border
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getBorderRadius <em>Border
 * Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getBorderSize <em>Border
 * Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getBorderLineStyle <em>Border Line
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getLabelColor <em>Label
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getBackground
 * <em>Background</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getShowIconExpression <em>Show Icon
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getLabelIcon <em>Label
 * Icon</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#isWithHeader <em>With
 * Header</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#isDisplayHeaderSeparator <em>Display
 * Header Separator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsideLabelStyleImpl extends LabelStyleImpl implements InsideLabelStyle {

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
    protected static final LineStyle BORDER_LINE_STYLE_EDEFAULT = LineStyle.SOLID;

    /**
     * The cached value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBorderLineStyle()
     * @generated
     * @ordered
     */
    protected LineStyle borderLineStyle = BORDER_LINE_STYLE_EDEFAULT;

    /**
     * The cached value of the '{@link #getLabelColor() <em>Label Color</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelColor()
     * @generated
     * @ordered
     */
    protected UserColor labelColor;

    /**
     * The cached value of the '{@link #getBackground() <em>Background</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBackground()
     * @generated
     * @ordered
     */
    protected UserColor background;

    /**
     * The default value of the '{@link #getShowIconExpression() <em>Show Icon Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getShowIconExpression()
     */
    protected static final String SHOW_ICON_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getShowIconExpression() <em>Show Icon Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getShowIconExpression()
     */
    protected String showIconExpression = SHOW_ICON_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelIcon()
     * @generated
     * @ordered
     */
    protected static final String LABEL_ICON_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelIcon()
     * @generated
     * @ordered
     */
    protected String labelIcon = LABEL_ICON_EDEFAULT;

    /**
     * The default value of the '{@link #getMaxWidthExpression() <em>Max Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getMaxWidthExpression()
     */
    protected static final String MAX_WIDTH_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMaxWidthExpression() <em>Max Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getMaxWidthExpression()
     */
    protected String maxWidthExpression = MAX_WIDTH_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #isWithHeader() <em>With Header</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isWithHeader()
     * @generated
     * @ordered
     */
    protected static final boolean WITH_HEADER_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isWithHeader() <em>With Header</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isWithHeader()
     * @generated
     * @ordered
     */
    protected boolean withHeader = WITH_HEADER_EDEFAULT;

    /**
     * The default value of the '{@link #getHeaderSeparatorDisplayMode() <em>Header Separator Display Mode</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getHeaderSeparatorDisplayMode()
     * @generated
     * @ordered
     */
    protected static final HeaderSeparatorDisplayMode HEADER_SEPARATOR_DISPLAY_MODE_EDEFAULT = HeaderSeparatorDisplayMode.NEVER;

    /**
     * The cached value of the '{@link #getHeaderSeparatorDisplayMode() <em>Header Separator Display Mode</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getHeaderSeparatorDisplayMode()
     * @generated
     * @ordered
     */
    protected HeaderSeparatorDisplayMode headerSeparatorDisplayMode = HEADER_SEPARATOR_DISPLAY_MODE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected InsideLabelStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.INSIDE_LABEL_STYLE;
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR, oldBorderColor, this.borderColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR, oldBorderColor, this.borderColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__BORDER_RADIUS, oldBorderRadius, this.borderRadius));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__BORDER_SIZE, oldBorderSize, this.borderSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__BORDER_LINE_STYLE, oldBorderLineStyle, this.borderLineStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getLabelColor() {
        if (this.labelColor != null && this.labelColor.eIsProxy()) {
            InternalEObject oldLabelColor = (InternalEObject) this.labelColor;
            this.labelColor = (UserColor) this.eResolveProxy(oldLabelColor);
            if (this.labelColor != oldLabelColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR, oldLabelColor, this.labelColor));
            }
        }
        return this.labelColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelColor(UserColor newLabelColor) {
        UserColor oldLabelColor = this.labelColor;
        this.labelColor = newLabelColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR, oldLabelColor, this.labelColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetLabelColor() {
        return this.labelColor;
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.INSIDE_LABEL_STYLE__BACKGROUND, oldBackground, this.background));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__BACKGROUND, oldBackground, this.background));
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
    public String getShowIconExpression() {
        return this.showIconExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setShowIconExpression(String newShowIconExpression) {
        String oldShowIconExpression = this.showIconExpression;
        this.showIconExpression = newShowIconExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON_EXPRESSION, oldShowIconExpression, this.showIconExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelIcon() {
        return this.labelIcon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelIcon(String newLabelIcon) {
        String oldLabelIcon = this.labelIcon;
        this.labelIcon = newLabelIcon;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON, oldLabelIcon, this.labelIcon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getMaxWidthExpression() {
        return this.maxWidthExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMaxWidthExpression(String newMaxWidthExpression) {
        String oldMaxWidthExpression = this.maxWidthExpression;
        this.maxWidthExpression = newMaxWidthExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__MAX_WIDTH_EXPRESSION, oldMaxWidthExpression, this.maxWidthExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isWithHeader() {
        return this.withHeader;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWithHeader(boolean newWithHeader) {
        boolean oldWithHeader = this.withHeader;
        this.withHeader = newWithHeader;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER, oldWithHeader, this.withHeader));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public HeaderSeparatorDisplayMode getHeaderSeparatorDisplayMode() {
        return this.headerSeparatorDisplayMode;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHeaderSeparatorDisplayMode(HeaderSeparatorDisplayMode newHeaderSeparatorDisplayMode) {
        HeaderSeparatorDisplayMode oldHeaderSeparatorDisplayMode = this.headerSeparatorDisplayMode;
        this.headerSeparatorDisplayMode = newHeaderSeparatorDisplayMode == null ? HEADER_SEPARATOR_DISPLAY_MODE_EDEFAULT : newHeaderSeparatorDisplayMode;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__HEADER_SEPARATOR_DISPLAY_MODE, oldHeaderSeparatorDisplayMode, this.headerSeparatorDisplayMode));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR:
                if (resolve)
                    return this.getBorderColor();
                return this.basicGetBorderColor();
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_RADIUS:
                return this.getBorderRadius();
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_SIZE:
                return this.getBorderSize();
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_LINE_STYLE:
                return this.getBorderLineStyle();
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                if (resolve)
                    return this.getLabelColor();
                return this.basicGetLabelColor();
            case DiagramPackage.INSIDE_LABEL_STYLE__BACKGROUND:
                if (resolve)
                    return this.getBackground();
                return this.basicGetBackground();
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON_EXPRESSION:
                return this.getShowIconExpression();
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                return this.getLabelIcon();
            case DiagramPackage.INSIDE_LABEL_STYLE__MAX_WIDTH_EXPRESSION:
                return this.getMaxWidthExpression();
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                return this.isWithHeader();
            case DiagramPackage.INSIDE_LABEL_STYLE__HEADER_SEPARATOR_DISPLAY_MODE:
                return this.getHeaderSeparatorDisplayMode();
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
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR:
                this.setBorderColor((UserColor) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_RADIUS:
                this.setBorderRadius((Integer) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_SIZE:
                this.setBorderSize((Integer) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_LINE_STYLE:
                this.setBorderLineStyle((LineStyle) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                this.setLabelColor((UserColor) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BACKGROUND:
                this.setBackground((UserColor) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON_EXPRESSION:
                this.setShowIconExpression((String) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                this.setLabelIcon((String) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__MAX_WIDTH_EXPRESSION:
                this.setMaxWidthExpression((String) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                this.setWithHeader((Boolean) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__HEADER_SEPARATOR_DISPLAY_MODE:
                this.setHeaderSeparatorDisplayMode((HeaderSeparatorDisplayMode) newValue);
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
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR:
                this.setBorderColor((UserColor) null);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_RADIUS:
                this.setBorderRadius(BORDER_RADIUS_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_SIZE:
                this.setBorderSize(BORDER_SIZE_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_LINE_STYLE:
                this.setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                this.setLabelColor((UserColor) null);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__BACKGROUND:
                this.setBackground((UserColor) null);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON_EXPRESSION:
                this.setShowIconExpression(SHOW_ICON_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                this.setLabelIcon(LABEL_ICON_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__MAX_WIDTH_EXPRESSION:
                this.setMaxWidthExpression(MAX_WIDTH_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                this.setWithHeader(WITH_HEADER_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__HEADER_SEPARATOR_DISPLAY_MODE:
                this.setHeaderSeparatorDisplayMode(HEADER_SEPARATOR_DISPLAY_MODE_EDEFAULT);
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
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR:
                return this.borderColor != null;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_RADIUS:
                return this.borderRadius != BORDER_RADIUS_EDEFAULT;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_SIZE:
                return this.borderSize != BORDER_SIZE_EDEFAULT;
            case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_LINE_STYLE:
                return this.borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                return this.labelColor != null;
            case DiagramPackage.INSIDE_LABEL_STYLE__BACKGROUND:
                return this.background != null;
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON_EXPRESSION:
                return SHOW_ICON_EXPRESSION_EDEFAULT == null ? this.showIconExpression != null : !SHOW_ICON_EXPRESSION_EDEFAULT.equals(this.showIconExpression);
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                return LABEL_ICON_EDEFAULT == null ? this.labelIcon != null : !LABEL_ICON_EDEFAULT.equals(this.labelIcon);
            case DiagramPackage.INSIDE_LABEL_STYLE__MAX_WIDTH_EXPRESSION:
                return MAX_WIDTH_EXPRESSION_EDEFAULT == null ? this.maxWidthExpression != null : !MAX_WIDTH_EXPRESSION_EDEFAULT.equals(this.maxWidthExpression);
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                return this.withHeader != WITH_HEADER_EDEFAULT;
            case DiagramPackage.INSIDE_LABEL_STYLE__HEADER_SEPARATOR_DISPLAY_MODE:
                return this.headerSeparatorDisplayMode != HEADER_SEPARATOR_DISPLAY_MODE_EDEFAULT;
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
                case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR:
                    return DiagramPackage.BORDER_STYLE__BORDER_COLOR;
                case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_RADIUS:
                    return DiagramPackage.BORDER_STYLE__BORDER_RADIUS;
                case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_SIZE:
                    return DiagramPackage.BORDER_STYLE__BORDER_SIZE;
                case DiagramPackage.INSIDE_LABEL_STYLE__BORDER_LINE_STYLE:
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
                    return DiagramPackage.INSIDE_LABEL_STYLE__BORDER_COLOR;
                case DiagramPackage.BORDER_STYLE__BORDER_RADIUS:
                    return DiagramPackage.INSIDE_LABEL_STYLE__BORDER_RADIUS;
                case DiagramPackage.BORDER_STYLE__BORDER_SIZE:
                    return DiagramPackage.INSIDE_LABEL_STYLE__BORDER_SIZE;
                case DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE:
                    return DiagramPackage.INSIDE_LABEL_STYLE__BORDER_LINE_STYLE;
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
        result.append(", showIconExpression: ");
        result.append(this.showIconExpression);
        result.append(", labelIcon: ");
        result.append(this.labelIcon);
        result.append(", maxWidthExpression: ");
        result.append(this.maxWidthExpression);
        result.append(", withHeader: ");
        result.append(this.withHeader);
        result.append(", headerSeparatorDisplayMode: ");
        result.append(this.headerSeparatorDisplayMode);
        result.append(')');
        return result.toString();
    }

} // InsideLabelStyleImpl
