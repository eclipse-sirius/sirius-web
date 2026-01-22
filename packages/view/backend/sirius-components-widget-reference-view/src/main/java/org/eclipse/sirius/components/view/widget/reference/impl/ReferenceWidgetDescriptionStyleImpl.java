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
package org.eclipse.sirius.components.view.widget.reference.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionStyleImpl;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl#isItalic <em>Italic</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl#isBold <em>Bold</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl#isUnderline <em>Underline</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl#isStrikeThrough <em>Strike Through</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceWidgetDescriptionStyleImpl extends WidgetDescriptionStyleImpl implements ReferenceWidgetDescriptionStyle {

    /**
	 * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
    protected static final int FONT_SIZE_EDEFAULT = 14;

    /**
	 * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
    protected int fontSize = FONT_SIZE_EDEFAULT;

    /**
	 * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isItalic()
	 * @generated
	 * @ordered
	 */
    protected static final boolean ITALIC_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isItalic()
	 * @generated
	 * @ordered
	 */
    protected boolean italic = ITALIC_EDEFAULT;

    /**
     * The default value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #isBold()
     */
    protected static final boolean BOLD_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #isBold()
     */
    protected boolean bold = BOLD_EDEFAULT;

    /**
	 * The default value of the '{@link #isUnderline() <em>Underline</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isUnderline()
	 * @generated
	 * @ordered
	 */
    protected static final boolean UNDERLINE_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isUnderline() <em>Underline</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isUnderline()
	 * @generated
	 * @ordered
	 */
    protected boolean underline = UNDERLINE_EDEFAULT;

    /**
	 * The default value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isStrikeThrough()
	 * @generated
	 * @ordered
	 */
    protected static final boolean STRIKE_THROUGH_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isStrikeThrough()
	 * @generated
	 * @ordered
	 */
    protected boolean strikeThrough = STRIKE_THROUGH_EDEFAULT;

    /**
     * The cached value of the '{@link #getColor() <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getColor()
     */
    protected UserColor color;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ReferenceWidgetDescriptionStyleImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION_STYLE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getFontSize() {
		return fontSize;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setFontSize(int newFontSize) {
		int oldFontSize = fontSize;
		fontSize = newFontSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE, oldFontSize, fontSize));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isItalic() {
		return italic;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setItalic(boolean newItalic) {
		boolean oldItalic = italic;
		italic = newItalic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC, oldItalic, italic));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isBold() {
		return bold;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBold(boolean newBold) {
		boolean oldBold = bold;
		bold = newBold;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD, oldBold, bold));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isUnderline() {
		return underline;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setUnderline(boolean newUnderline) {
		boolean oldUnderline = underline;
		underline = newUnderline;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE, oldUnderline, underline));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isStrikeThrough() {
		return strikeThrough;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStrikeThrough(boolean newStrikeThrough) {
		boolean oldStrikeThrough = strikeThrough;
		strikeThrough = newStrikeThrough;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH, oldStrikeThrough, strikeThrough));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getColor() {
		if (color != null && color.eIsProxy())
		{
			InternalEObject oldColor = (InternalEObject)color;
			color = (UserColor)eResolveProxy(oldColor);
			if (color != oldColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR, oldColor, color));
			}
		}
		return color;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setColor(UserColor newColor) {
		UserColor oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR, oldColor, color));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetColor() {
		return color;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE:
				return getFontSize();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC:
				return isItalic();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD:
				return isBold();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE:
				return isUnderline();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH:
				return isStrikeThrough();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR:
				if (resolve) return getColor();
				return basicGetColor();
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
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE:
				setFontSize((Integer)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC:
				setItalic((Boolean)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD:
				setBold((Boolean)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE:
				setUnderline((Boolean)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH:
				setStrikeThrough((Boolean)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR:
				setColor((UserColor)newValue);
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
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE:
				setFontSize(FONT_SIZE_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC:
				setItalic(ITALIC_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD:
				setBold(BOLD_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE:
				setUnderline(UNDERLINE_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH:
				setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR:
				setColor((UserColor)null);
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
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE:
				return fontSize != FONT_SIZE_EDEFAULT;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC:
				return italic != ITALIC_EDEFAULT;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD:
				return bold != BOLD_EDEFAULT;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE:
				return underline != UNDERLINE_EDEFAULT;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH:
				return strikeThrough != STRIKE_THROUGH_EDEFAULT;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR:
				return color != null;
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == LabelStyle.class)
		{
			switch (derivedFeatureID)
			{
				case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE: return ViewPackage.LABEL_STYLE__FONT_SIZE;
				case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC: return ViewPackage.LABEL_STYLE__ITALIC;
				case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD: return ViewPackage.LABEL_STYLE__BOLD;
				case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE: return ViewPackage.LABEL_STYLE__UNDERLINE;
				case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH: return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == LabelStyle.class)
		{
			switch (baseFeatureID)
			{
				case ViewPackage.LABEL_STYLE__FONT_SIZE: return ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE;
				case ViewPackage.LABEL_STYLE__ITALIC: return ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC;
				case ViewPackage.LABEL_STYLE__BOLD: return ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD;
				case ViewPackage.LABEL_STYLE__UNDERLINE: return ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE;
				case ViewPackage.LABEL_STYLE__STRIKE_THROUGH: return ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (fontSize: ");
		result.append(fontSize);
		result.append(", italic: ");
		result.append(italic);
		result.append(", bold: ");
		result.append(bold);
		result.append(", underline: ");
		result.append(underline);
		result.append(", strikeThrough: ");
		result.append(strikeThrough);
		result.append(')');
		return result.toString();
	}

} // ReferenceWidgetDescriptionStyleImpl
