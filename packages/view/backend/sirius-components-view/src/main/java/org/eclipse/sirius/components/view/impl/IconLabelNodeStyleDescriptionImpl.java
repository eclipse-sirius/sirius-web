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
import org.eclipse.sirius.components.view.BorderStyle;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Icon Label Node Style Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#getFontSize <em>Font
 * Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#isBold <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#isUnderline
 * <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#isStrikeThrough <em>Strike
 * Through</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#getBorderColor <em>Border
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#getBorderRadius <em>Border
 * Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#getBorderSize <em>Border
 * Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#getBorderLineStyle <em>Border
 * Line Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#getLabelColor <em>Label
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#getSizeComputationExpression
 * <em>Size Computation Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.IconLabelNodeStyleDescriptionImpl#isShowIcon <em>Show
 * Icon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IconLabelNodeStyleDescriptionImpl extends StyleImpl implements IconLabelNodeStyleDescription {
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
     * The default value of the '{@link #getBorderColor() <em>Border Color</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected static final String BORDER_COLOR_EDEFAULT = "#33B0C3"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getBorderColor()
     * @generated
     * @ordered
     */
    protected String borderColor = BORDER_COLOR_EDEFAULT;

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
     * @see #getBorderLineStyle()
     * @generated
     * @ordered
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
     * The default value of the '{@link #getLabelColor() <em>Label Color</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelColor()
     * @generated
     * @ordered
     */
    protected static final String LABEL_COLOR_EDEFAULT = "black"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getLabelColor() <em>Label Color</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelColor()
     * @generated
     * @ordered
     */
    protected String labelColor = LABEL_COLOR_EDEFAULT;

    /**
     * The default value of the '{@link #getSizeComputationExpression() <em>Size Computation Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSizeComputationExpression()
     * @generated
     * @ordered
     */
    protected static final String SIZE_COMPUTATION_EXPRESSION_EDEFAULT = "1"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getSizeComputationExpression() <em>Size Computation Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSizeComputationExpression()
     * @generated
     * @ordered
     */
    protected String sizeComputationExpression = SIZE_COMPUTATION_EXPRESSION_EDEFAULT;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected IconLabelNodeStyleDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.ICON_LABEL_NODE_STYLE_DESCRIPTION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE, oldFontSize, this.fontSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC, oldItalic, this.italic));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD, oldBold, this.bold));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE, oldUnderline, this.underline));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getBorderColor() {
        return this.borderColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBorderColor(String newBorderColor) {
        String oldBorderColor = this.borderColor;
        this.borderColor = newBorderColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR, oldBorderColor, this.borderColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS, oldBorderRadius, this.borderRadius));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE, oldBorderSize, this.borderSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE, oldBorderLineStyle, this.borderLineStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelColor() {
        return this.labelColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelColor(String newLabelColor) {
        String oldLabelColor = this.labelColor;
        this.labelColor = newLabelColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR, oldLabelColor, this.labelColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSizeComputationExpression() {
        return this.sizeComputationExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSizeComputationExpression(String newSizeComputationExpression) {
        String oldSizeComputationExpression = this.sizeComputationExpression;
        this.sizeComputationExpression = newSizeComputationExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SIZE_COMPUTATION_EXPRESSION, oldSizeComputationExpression,
                    this.sizeComputationExpression));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON, oldShowIcon, this.showIcon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE:
            return this.getFontSize();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC:
            return this.isItalic();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD:
            return this.isBold();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE:
            return this.isUnderline();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
            return this.isStrikeThrough();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
            return this.getBorderColor();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
            return this.getBorderRadius();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
            return this.getBorderSize();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
            return this.getBorderLineStyle();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
            return this.getLabelColor();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SIZE_COMPUTATION_EXPRESSION:
            return this.getSizeComputationExpression();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON:
            return this.isShowIcon();
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
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE:
            this.setFontSize((Integer) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC:
            this.setItalic((Boolean) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD:
            this.setBold((Boolean) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE:
            this.setUnderline((Boolean) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
            this.setStrikeThrough((Boolean) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
            this.setBorderColor((String) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
            this.setBorderRadius((Integer) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
            this.setBorderSize((Integer) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
            this.setBorderLineStyle((LineStyle) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
            this.setLabelColor((String) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SIZE_COMPUTATION_EXPRESSION:
            this.setSizeComputationExpression((String) newValue);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON:
            this.setShowIcon((Boolean) newValue);
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
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE:
            this.setFontSize(FONT_SIZE_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC:
            this.setItalic(ITALIC_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD:
            this.setBold(BOLD_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE:
            this.setUnderline(UNDERLINE_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
            this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
            this.setBorderColor(BORDER_COLOR_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
            this.setBorderRadius(BORDER_RADIUS_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
            this.setBorderSize(BORDER_SIZE_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
            this.setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
            this.setLabelColor(LABEL_COLOR_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SIZE_COMPUTATION_EXPRESSION:
            this.setSizeComputationExpression(SIZE_COMPUTATION_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON:
            this.setShowIcon(SHOW_ICON_EDEFAULT);
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
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE:
            return this.fontSize != FONT_SIZE_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC:
            return this.italic != ITALIC_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD:
            return this.bold != BOLD_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE:
            return this.underline != UNDERLINE_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
            return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
            return BORDER_COLOR_EDEFAULT == null ? this.borderColor != null : !BORDER_COLOR_EDEFAULT.equals(this.borderColor);
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
            return this.borderRadius != BORDER_RADIUS_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
            return this.borderSize != BORDER_SIZE_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
            return this.borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR:
            return LABEL_COLOR_EDEFAULT == null ? this.labelColor != null : !LABEL_COLOR_EDEFAULT.equals(this.labelColor);
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SIZE_COMPUTATION_EXPRESSION:
            return SIZE_COMPUTATION_EXPRESSION_EDEFAULT == null ? this.sizeComputationExpression != null : !SIZE_COMPUTATION_EXPRESSION_EDEFAULT.equals(this.sizeComputationExpression);
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON:
            return this.showIcon != SHOW_ICON_EDEFAULT;
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
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE:
                return ViewPackage.LABEL_STYLE__FONT_SIZE;
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC:
                return ViewPackage.LABEL_STYLE__ITALIC;
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD:
                return ViewPackage.LABEL_STYLE__BOLD;
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE:
                return ViewPackage.LABEL_STYLE__UNDERLINE;
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH:
                return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
            default:
                return -1;
            }
        }
        if (baseClass == BorderStyle.class) {
            switch (derivedFeatureID) {
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
                return ViewPackage.BORDER_STYLE__BORDER_COLOR;
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
                return ViewPackage.BORDER_STYLE__BORDER_RADIUS;
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
                return ViewPackage.BORDER_STYLE__BORDER_SIZE;
            case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
                return ViewPackage.BORDER_STYLE__BORDER_LINE_STYLE;
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
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE;
            case ViewPackage.LABEL_STYLE__ITALIC:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC;
            case ViewPackage.LABEL_STYLE__BOLD:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD;
            case ViewPackage.LABEL_STYLE__UNDERLINE:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE;
            case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;
            default:
                return -1;
            }
        }
        if (baseClass == BorderStyle.class) {
            switch (baseFeatureID) {
            case ViewPackage.BORDER_STYLE__BORDER_COLOR:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR;
            case ViewPackage.BORDER_STYLE__BORDER_RADIUS:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS;
            case ViewPackage.BORDER_STYLE__BORDER_SIZE:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE;
            case ViewPackage.BORDER_STYLE__BORDER_LINE_STYLE:
                return ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;
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
        result.append(" (fontSize: "); //$NON-NLS-1$
        result.append(this.fontSize);
        result.append(", italic: "); //$NON-NLS-1$
        result.append(this.italic);
        result.append(", bold: "); //$NON-NLS-1$
        result.append(this.bold);
        result.append(", underline: "); //$NON-NLS-1$
        result.append(this.underline);
        result.append(", strikeThrough: "); //$NON-NLS-1$
        result.append(this.strikeThrough);
        result.append(", borderColor: "); //$NON-NLS-1$
        result.append(this.borderColor);
        result.append(", borderRadius: "); //$NON-NLS-1$
        result.append(this.borderRadius);
        result.append(", borderSize: "); //$NON-NLS-1$
        result.append(this.borderSize);
        result.append(", borderLineStyle: "); //$NON-NLS-1$
        result.append(this.borderLineStyle);
        result.append(", labelColor: "); //$NON-NLS-1$
        result.append(this.labelColor);
        result.append(", sizeComputationExpression: "); //$NON-NLS-1$
        result.append(this.sizeComputationExpression);
        result.append(", showIcon: "); //$NON-NLS-1$
        result.append(this.showIcon);
        result.append(')');
        return result.toString();
    }

} // IconLabelNodeStyleDescriptionImpl
