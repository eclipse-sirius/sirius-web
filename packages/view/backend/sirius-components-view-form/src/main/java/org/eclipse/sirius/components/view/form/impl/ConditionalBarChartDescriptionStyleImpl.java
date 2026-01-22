/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.view.impl.ConditionalImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional Bar Chart Description
 * Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl#isItalic <em>Italic</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl#isBold <em>Bold</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl#isUnderline <em>Underline</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl#isStrikeThrough <em>Strike Through</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl#getBarsColor <em>Bars Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalBarChartDescriptionStyleImpl extends ConditionalImpl implements ConditionalBarChartDescriptionStyle {
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
	 * The default value of the '{@link #getBarsColor() <em>Bars Color</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBarsColor()
	 * @generated
	 * @ordered
	 */
    protected static final String BARS_COLOR_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getBarsColor() <em>Bars Color</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBarsColor()
	 * @generated
	 * @ordered
	 */
    protected String barsColor = BARS_COLOR_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ConditionalBarChartDescriptionStyleImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE, oldFontSize, fontSize));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC, oldItalic, italic));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD, oldBold, bold));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE, oldUnderline, underline));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH, oldStrikeThrough, strikeThrough));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getBarsColor() {
		return barsColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBarsColor(String newBarsColor) {
		String oldBarsColor = barsColor;
		barsColor = newBarsColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR, oldBarsColor, barsColor));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE:
				return getFontSize();
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC:
				return isItalic();
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD:
				return isBold();
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE:
				return isUnderline();
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
				return isStrikeThrough();
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR:
				return getBarsColor();
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
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE:
				setFontSize((Integer)newValue);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC:
				setItalic((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD:
				setBold((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE:
				setUnderline((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
				setStrikeThrough((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR:
				setBarsColor((String)newValue);
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
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE:
				setFontSize(FONT_SIZE_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC:
				setItalic(ITALIC_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD:
				setBold(BOLD_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE:
				setUnderline(UNDERLINE_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
				setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR:
				setBarsColor(BARS_COLOR_EDEFAULT);
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
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE:
				return fontSize != FONT_SIZE_EDEFAULT;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC:
				return italic != ITALIC_EDEFAULT;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD:
				return bold != BOLD_EDEFAULT;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE:
				return underline != UNDERLINE_EDEFAULT;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
				return strikeThrough != STRIKE_THROUGH_EDEFAULT;
			case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR:
				return BARS_COLOR_EDEFAULT == null ? barsColor != null : !BARS_COLOR_EDEFAULT.equals(barsColor);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == WidgetDescriptionStyle.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == LabelStyle.class)
		{
			switch (derivedFeatureID)
			{
				case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE: return ViewPackage.LABEL_STYLE__FONT_SIZE;
				case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC: return ViewPackage.LABEL_STYLE__ITALIC;
				case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD: return ViewPackage.LABEL_STYLE__BOLD;
				case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE: return ViewPackage.LABEL_STYLE__UNDERLINE;
				case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH: return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		if (baseClass == BarChartDescriptionStyle.class)
		{
			switch (derivedFeatureID)
			{
				case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR: return FormPackage.BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR;
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
		if (baseClass == WidgetDescriptionStyle.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == LabelStyle.class)
		{
			switch (baseFeatureID)
			{
				case ViewPackage.LABEL_STYLE__FONT_SIZE: return FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE;
				case ViewPackage.LABEL_STYLE__ITALIC: return FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC;
				case ViewPackage.LABEL_STYLE__BOLD: return FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD;
				case ViewPackage.LABEL_STYLE__UNDERLINE: return FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE;
				case ViewPackage.LABEL_STYLE__STRIKE_THROUGH: return FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		if (baseClass == BarChartDescriptionStyle.class)
		{
			switch (baseFeatureID)
			{
				case FormPackage.BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR: return FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR;
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
		result.append(", barsColor: ");
		result.append(barsColor);
		result.append(')');
		return result.toString();
	}

} // ConditionalBarChartDescriptionStyleImpl
