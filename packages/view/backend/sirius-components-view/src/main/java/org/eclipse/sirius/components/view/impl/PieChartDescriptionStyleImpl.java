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
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Pie Chart Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#getFontSize <em>Font Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#isBold <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#isUnderline <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#isStrikeThrough <em>Strike
 * Through</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#getColors <em>Colors</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#getStrokeWidth <em>Stroke
 * Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.PieChartDescriptionStyleImpl#getStrokeColor <em>Stroke
 * Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PieChartDescriptionStyleImpl extends WidgetDescriptionStyleImpl implements PieChartDescriptionStyle {
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
     * The default value of the '{@link #getColors() <em>Colors</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getColors()
     * @generated
     * @ordered
     */
    protected static final String COLORS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getColors() <em>Colors</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getColors()
     * @generated
     * @ordered
     */
    protected String colors = COLORS_EDEFAULT;

    /**
     * The default value of the '{@link #getStrokeWidth() <em>Stroke Width</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getStrokeWidth()
     * @generated
     * @ordered
     */
    protected static final String STROKE_WIDTH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStrokeWidth() <em>Stroke Width</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStrokeWidth()
     * @generated
     * @ordered
     */
    protected String strokeWidth = STROKE_WIDTH_EDEFAULT;

    /**
     * The default value of the '{@link #getStrokeColor() <em>Stroke Color</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getStrokeColor()
     * @generated
     * @ordered
     */
    protected static final String STROKE_COLOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStrokeColor() <em>Stroke Color</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStrokeColor()
     * @generated
     * @ordered
     */
    protected String strokeColor = STROKE_COLOR_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PieChartDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.PIE_CHART_DESCRIPTION_STYLE;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE, oldFontSize, this.fontSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__ITALIC, oldItalic, this.italic));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__BOLD, oldBold, this.bold));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__UNDERLINE, oldUnderline, this.underline));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getColors() {
        return this.colors;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setColors(String newColors) {
        String oldColors = this.colors;
        this.colors = newColors;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__COLORS, oldColors, this.colors));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getStrokeWidth() {
        return this.strokeWidth;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStrokeWidth(String newStrokeWidth) {
        String oldStrokeWidth = this.strokeWidth;
        this.strokeWidth = newStrokeWidth;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH, oldStrokeWidth, this.strokeWidth));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getStrokeColor() {
        return this.strokeColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStrokeColor(String newStrokeColor) {
        String oldStrokeColor = this.strokeColor;
        this.strokeColor = newStrokeColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR, oldStrokeColor, this.strokeColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE:
            return this.getFontSize();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__ITALIC:
            return this.isItalic();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__BOLD:
            return this.isBold();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__UNDERLINE:
            return this.isUnderline();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
            return this.isStrikeThrough();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__COLORS:
            return this.getColors();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH:
            return this.getStrokeWidth();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR:
            return this.getStrokeColor();
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
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE:
            this.setFontSize((Integer) newValue);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__ITALIC:
            this.setItalic((Boolean) newValue);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__BOLD:
            this.setBold((Boolean) newValue);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__UNDERLINE:
            this.setUnderline((Boolean) newValue);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
            this.setStrikeThrough((Boolean) newValue);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__COLORS:
            this.setColors((String) newValue);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH:
            this.setStrokeWidth((String) newValue);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR:
            this.setStrokeColor((String) newValue);
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
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE:
            this.setFontSize(FONT_SIZE_EDEFAULT);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__ITALIC:
            this.setItalic(ITALIC_EDEFAULT);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__BOLD:
            this.setBold(BOLD_EDEFAULT);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__UNDERLINE:
            this.setUnderline(UNDERLINE_EDEFAULT);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
            this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__COLORS:
            this.setColors(COLORS_EDEFAULT);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH:
            this.setStrokeWidth(STROKE_WIDTH_EDEFAULT);
            return;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR:
            this.setStrokeColor(STROKE_COLOR_EDEFAULT);
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
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE:
            return this.fontSize != FONT_SIZE_EDEFAULT;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__ITALIC:
            return this.italic != ITALIC_EDEFAULT;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__BOLD:
            return this.bold != BOLD_EDEFAULT;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__UNDERLINE:
            return this.underline != UNDERLINE_EDEFAULT;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
            return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__COLORS:
            return COLORS_EDEFAULT == null ? this.colors != null : !COLORS_EDEFAULT.equals(this.colors);
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH:
            return STROKE_WIDTH_EDEFAULT == null ? this.strokeWidth != null : !STROKE_WIDTH_EDEFAULT.equals(this.strokeWidth);
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR:
            return STROKE_COLOR_EDEFAULT == null ? this.strokeColor != null : !STROKE_COLOR_EDEFAULT.equals(this.strokeColor);
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
            case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE:
                return ViewPackage.LABEL_STYLE__FONT_SIZE;
            case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__ITALIC:
                return ViewPackage.LABEL_STYLE__ITALIC;
            case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__BOLD:
                return ViewPackage.LABEL_STYLE__BOLD;
            case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__UNDERLINE:
                return ViewPackage.LABEL_STYLE__UNDERLINE;
            case ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH:
                return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
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
                return ViewPackage.PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE;
            case ViewPackage.LABEL_STYLE__ITALIC:
                return ViewPackage.PIE_CHART_DESCRIPTION_STYLE__ITALIC;
            case ViewPackage.LABEL_STYLE__BOLD:
                return ViewPackage.PIE_CHART_DESCRIPTION_STYLE__BOLD;
            case ViewPackage.LABEL_STYLE__UNDERLINE:
                return ViewPackage.PIE_CHART_DESCRIPTION_STYLE__UNDERLINE;
            case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
                return ViewPackage.PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH;
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
        result.append(", colors: ");
        result.append(this.colors);
        result.append(", strokeWidth: ");
        result.append(this.strokeWidth);
        result.append(", strokeColor: ");
        result.append(this.strokeColor);
        result.append(')');
        return result.toString();
    }

} // PieChartDescriptionStyleImpl
