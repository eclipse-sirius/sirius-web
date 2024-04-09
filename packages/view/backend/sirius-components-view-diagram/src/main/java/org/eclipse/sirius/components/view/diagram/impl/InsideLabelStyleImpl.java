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
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.impl.LabelStyleImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Inside Label Style</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl#getLabelColor <em>Label
 * Color</em>}</li>
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
     * The cached value of the '{@link #getLabelColor() <em>Label Color</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelColor()
     * @generated
     * @ordered
     */
    protected UserColor labelColor;

    /**
     * The default value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isShowIcon()
     * @generated
     * @ordered
     */
    protected static final boolean SHOW_ICON_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isShowIcon()
     * @generated
     * @ordered
     */
    protected boolean showIcon = SHOW_ICON_EDEFAULT;

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
     * The default value of the '{@link #isDisplayHeaderSeparator() <em>Display Header Separator</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isDisplayHeaderSeparator()
     */
    protected static final boolean DISPLAY_HEADER_SEPARATOR_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDisplayHeaderSeparator() <em>Display Header Separator</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isDisplayHeaderSeparator()
     */
    protected boolean displayHeaderSeparator = DISPLAY_HEADER_SEPARATOR_EDEFAULT;

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
    public boolean isShowIcon() {
        return this.showIcon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setShowIcon(boolean newShowIcon) {
        boolean oldShowIcon = this.showIcon;
        this.showIcon = newShowIcon;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON, oldShowIcon, this.showIcon));
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
    public boolean isDisplayHeaderSeparator() {
        return this.displayHeaderSeparator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDisplayHeaderSeparator(boolean newDisplayHeaderSeparator) {
        boolean oldDisplayHeaderSeparator = this.displayHeaderSeparator;
        this.displayHeaderSeparator = newDisplayHeaderSeparator;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR, oldDisplayHeaderSeparator, this.displayHeaderSeparator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                if (resolve)
                    return this.getLabelColor();
                return this.basicGetLabelColor();
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON:
                return this.isShowIcon();
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                return this.getLabelIcon();
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                return this.isWithHeader();
            case DiagramPackage.INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR:
                return this.isDisplayHeaderSeparator();
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
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                this.setLabelColor((UserColor) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON:
                this.setShowIcon((Boolean) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                this.setLabelIcon((String) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                this.setWithHeader((Boolean) newValue);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR:
                this.setDisplayHeaderSeparator((Boolean) newValue);
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
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                this.setLabelColor((UserColor) null);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON:
                this.setShowIcon(SHOW_ICON_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                this.setLabelIcon(LABEL_ICON_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                this.setWithHeader(WITH_HEADER_EDEFAULT);
                return;
            case DiagramPackage.INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR:
                this.setDisplayHeaderSeparator(DISPLAY_HEADER_SEPARATOR_EDEFAULT);
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
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_COLOR:
                return this.labelColor != null;
            case DiagramPackage.INSIDE_LABEL_STYLE__SHOW_ICON:
                return this.showIcon != SHOW_ICON_EDEFAULT;
            case DiagramPackage.INSIDE_LABEL_STYLE__LABEL_ICON:
                return LABEL_ICON_EDEFAULT == null ? this.labelIcon != null : !LABEL_ICON_EDEFAULT.equals(this.labelIcon);
            case DiagramPackage.INSIDE_LABEL_STYLE__WITH_HEADER:
                return this.withHeader != WITH_HEADER_EDEFAULT;
            case DiagramPackage.INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR:
                return this.displayHeaderSeparator != DISPLAY_HEADER_SEPARATOR_EDEFAULT;
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
        result.append(" (showIcon: ");
        result.append(this.showIcon);
        result.append(", labelIcon: ");
        result.append(this.labelIcon);
        result.append(", withHeader: ");
        result.append(this.withHeader);
        result.append(", displayHeaderSeparator: ");
        result.append(this.displayHeaderSeparator);
        result.append(')');
        return result.toString();
    }

} // InsideLabelStyleImpl
