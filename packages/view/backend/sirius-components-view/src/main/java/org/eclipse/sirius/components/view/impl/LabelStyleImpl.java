/**
 * Copyright (c) 2021, 2022 Obeo.
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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Label Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl#getFontSize <em>Font Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl#isBold <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl#isUnderline <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl#isStrikeThrough <em>Strike Through</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class LabelStyleImpl extends MinimalEObjectImpl.Container implements LabelStyle {
    /**
     * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFontSize()
     * @generated
     * @ordered
     */
    protected static final int FONT_SIZE_EDEFAULT = 14;

    /**
     * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFontSize()
     * @generated
     * @ordered
     */
    protected int fontSize = FONT_SIZE_EDEFAULT;

    /**
     * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected static final boolean ITALIC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected boolean italic = ITALIC_EDEFAULT;

    /**
     * The default value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected static final boolean BOLD_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected boolean bold = BOLD_EDEFAULT;

    /**
     * The default value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isUnderline()
     * @generated
     * @ordered
     */
    protected static final boolean UNDERLINE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isUnderline()
     * @generated
     * @ordered
     */
    protected boolean underline = UNDERLINE_EDEFAULT;

    /**
     * The default value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isStrikeThrough()
     * @generated
     * @ordered
     */
    protected static final boolean STRIKE_THROUGH_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isStrikeThrough()
     * @generated
     * @ordered
     */
    protected boolean strikeThrough = STRIKE_THROUGH_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LabelStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.LABEL_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getFontSize() {
        return this.fontSize;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFontSize(int newFontSize) {
        int oldFontSize = this.fontSize;
        this.fontSize = newFontSize;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LABEL_STYLE__FONT_SIZE, oldFontSize, this.fontSize));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isItalic() {
        return this.italic;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setItalic(boolean newItalic) {
        boolean oldItalic = this.italic;
        this.italic = newItalic;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LABEL_STYLE__ITALIC, oldItalic, this.italic));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isBold() {
        return this.bold;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBold(boolean newBold) {
        boolean oldBold = this.bold;
        this.bold = newBold;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LABEL_STYLE__BOLD, oldBold, this.bold));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isUnderline() {
        return this.underline;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUnderline(boolean newUnderline) {
        boolean oldUnderline = this.underline;
        this.underline = newUnderline;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LABEL_STYLE__UNDERLINE, oldUnderline, this.underline));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isStrikeThrough() {
        return this.strikeThrough;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStrikeThrough(boolean newStrikeThrough) {
        boolean oldStrikeThrough = this.strikeThrough;
        this.strikeThrough = newStrikeThrough;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LABEL_STYLE__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.LABEL_STYLE__FONT_SIZE:
            return this.getFontSize();
        case ViewPackage.LABEL_STYLE__ITALIC:
            return this.isItalic();
        case ViewPackage.LABEL_STYLE__BOLD:
            return this.isBold();
        case ViewPackage.LABEL_STYLE__UNDERLINE:
            return this.isUnderline();
        case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
            return this.isStrikeThrough();
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
        case ViewPackage.LABEL_STYLE__FONT_SIZE:
            this.setFontSize((Integer) newValue);
            return;
        case ViewPackage.LABEL_STYLE__ITALIC:
            this.setItalic((Boolean) newValue);
            return;
        case ViewPackage.LABEL_STYLE__BOLD:
            this.setBold((Boolean) newValue);
            return;
        case ViewPackage.LABEL_STYLE__UNDERLINE:
            this.setUnderline((Boolean) newValue);
            return;
        case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
            this.setStrikeThrough((Boolean) newValue);
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
        case ViewPackage.LABEL_STYLE__FONT_SIZE:
            this.setFontSize(FONT_SIZE_EDEFAULT);
            return;
        case ViewPackage.LABEL_STYLE__ITALIC:
            this.setItalic(ITALIC_EDEFAULT);
            return;
        case ViewPackage.LABEL_STYLE__BOLD:
            this.setBold(BOLD_EDEFAULT);
            return;
        case ViewPackage.LABEL_STYLE__UNDERLINE:
            this.setUnderline(UNDERLINE_EDEFAULT);
            return;
        case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
            this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
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
        case ViewPackage.LABEL_STYLE__FONT_SIZE:
            return this.fontSize != FONT_SIZE_EDEFAULT;
        case ViewPackage.LABEL_STYLE__ITALIC:
            return this.italic != ITALIC_EDEFAULT;
        case ViewPackage.LABEL_STYLE__BOLD:
            return this.bold != BOLD_EDEFAULT;
        case ViewPackage.LABEL_STYLE__UNDERLINE:
            return this.underline != UNDERLINE_EDEFAULT;
        case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
            return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
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
        result.append(" (fontSize: ");
        result.append(this.fontSize);
        result.append(", italic: ");
        result.append(this.italic);
        result.append(", bold: ");
        result.append(this.bold);
        result.append(", underline: ");
        result.append(this.underline);
        result.append(", strikeThrough: ");
        result.append(this.strikeThrough);
        result.append(')');
        return result.toString();
    }

} // LabelStyleImpl
