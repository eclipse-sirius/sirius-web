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
package org.eclipse.sirius.components.view.deck.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.impl.ConditionalImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional Deck Element Description
 * Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.ConditionalDeckElementDescriptionStyleImpl#getFontSize
 * <em>Font Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.ConditionalDeckElementDescriptionStyleImpl#isItalic
 * <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.ConditionalDeckElementDescriptionStyleImpl#isBold
 * <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.ConditionalDeckElementDescriptionStyleImpl#isUnderline
 * <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.ConditionalDeckElementDescriptionStyleImpl#isStrikeThrough
 * <em>Strike Through</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.ConditionalDeckElementDescriptionStyleImpl#getBackgroundColor
 * <em>Background Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.impl.ConditionalDeckElementDescriptionStyleImpl#getColor
 * <em>Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalDeckElementDescriptionStyleImpl extends ConditionalImpl implements ConditionalDeckElementDescriptionStyle {
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
     * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColor()
     * @generated
     * @ordered
     */
    protected UserColor backgroundColor;

    /**
     * The cached value of the '{@link #getColor() <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getColor()
     * @generated
     * @ordered
     */
    protected UserColor color;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalDeckElementDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DeckPackage.Literals.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__FONT_SIZE, oldFontSize, this.fontSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__ITALIC, oldItalic, this.italic));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BOLD, oldBold, this.bold));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__UNDERLINE, oldUnderline, this.underline));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getBackgroundColor() {
        if (this.backgroundColor != null && this.backgroundColor.eIsProxy()) {
            InternalEObject oldBackgroundColor = (InternalEObject) this.backgroundColor;
            this.backgroundColor = (UserColor) this.eResolveProxy(oldBackgroundColor);
            if (this.backgroundColor != oldBackgroundColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
            }
        }
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBackgroundColor(UserColor newBackgroundColor) {
        UserColor oldBackgroundColor = this.backgroundColor;
        this.backgroundColor = newBackgroundColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getColor() {
        if (this.color != null && this.color.eIsProxy()) {
            InternalEObject oldColor = (InternalEObject) this.color;
            this.color = (UserColor) this.eResolveProxy(oldColor);
            if (this.color != oldColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR, oldColor, this.color));
            }
        }
        return this.color;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetColor() {
        return this.color;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setColor(UserColor newColor) {
        UserColor oldColor = this.color;
        this.color = newColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR, oldColor, this.color));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__FONT_SIZE:
                return this.getFontSize();
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__ITALIC:
                return this.isItalic();
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BOLD:
                return this.isBold();
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__UNDERLINE:
                return this.isUnderline();
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                return this.isStrikeThrough();
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                if (resolve)
                    return this.getBackgroundColor();
                return this.basicGetBackgroundColor();
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
                if (resolve)
                    return this.getColor();
                return this.basicGetColor();
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
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__FONT_SIZE:
                this.setFontSize((Integer) newValue);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BOLD:
                this.setBold((Boolean) newValue);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__UNDERLINE:
                this.setUnderline((Boolean) newValue);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough((Boolean) newValue);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) newValue);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
                this.setColor((UserColor) newValue);
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
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__FONT_SIZE:
                this.setFontSize(FONT_SIZE_EDEFAULT);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BOLD:
                this.setBold(BOLD_EDEFAULT);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__UNDERLINE:
                this.setUnderline(UNDERLINE_EDEFAULT);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) null);
                return;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
                this.setColor((UserColor) null);
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
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__FONT_SIZE:
                return this.fontSize != FONT_SIZE_EDEFAULT;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BOLD:
                return this.bold != BOLD_EDEFAULT;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__UNDERLINE:
                return this.underline != UNDERLINE_EDEFAULT;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                return this.backgroundColor != null;
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
                return this.color != null;
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
        if (baseClass == LabelStyle.class) {
            switch (derivedFeatureID) {
                case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__FONT_SIZE:
                    return ViewPackage.LABEL_STYLE__FONT_SIZE;
                case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__ITALIC:
                    return ViewPackage.LABEL_STYLE__ITALIC;
                case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BOLD:
                    return ViewPackage.LABEL_STYLE__BOLD;
                case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__UNDERLINE:
                    return ViewPackage.LABEL_STYLE__UNDERLINE;
                case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                    return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == DeckElementDescriptionStyle.class) {
            switch (derivedFeatureID) {
                case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                    return DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR;
                case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
                    return DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR;
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
        if (baseClass == LabelStyle.class) {
            switch (baseFeatureID) {
                case ViewPackage.LABEL_STYLE__FONT_SIZE:
                    return DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__FONT_SIZE;
                case ViewPackage.LABEL_STYLE__ITALIC:
                    return DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__ITALIC;
                case ViewPackage.LABEL_STYLE__BOLD:
                    return DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BOLD;
                case ViewPackage.LABEL_STYLE__UNDERLINE:
                    return DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__UNDERLINE;
                case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
                    return DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == DeckElementDescriptionStyle.class) {
            switch (baseFeatureID) {
                case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                    return DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR;
                case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE__COLOR:
                    return DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE__COLOR;
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

} // ConditionalDeckElementDescriptionStyleImpl
