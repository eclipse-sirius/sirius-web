/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.Style;
import org.eclipse.sirius.components.view.impl.ConditionalImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional Edge Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#getColor <em>Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#getFontSize <em>Font
 * Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#isBold <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#isUnderline
 * <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#isStrikeThrough <em>Strike
 * Through</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#getLineStyle <em>Line
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#getSourceArrowStyle <em>Source
 * Arrow Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#getTargetArrowStyle <em>Target
 * Arrow Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#getEdgeWidth <em>Edge
 * Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#isShowIcon <em>Show
 * Icon</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl#getLabelIcon <em>Label
 * Icon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalEdgeStyleImpl extends ConditionalImpl implements ConditionalEdgeStyle {
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
     * The default value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLineStyle()
     * @generated
     * @ordered
     */
    protected static final LineStyle LINE_STYLE_EDEFAULT = LineStyle.SOLID;

    /**
     * The cached value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLineStyle()
     * @generated
     * @ordered
     */
    protected LineStyle lineStyle = LINE_STYLE_EDEFAULT;

    /**
     * The default value of the '{@link #getSourceArrowStyle() <em>Source Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceArrowStyle()
     * @generated
     * @ordered
     */
    protected static final ArrowStyle SOURCE_ARROW_STYLE_EDEFAULT = ArrowStyle.NONE;

    /**
     * The cached value of the '{@link #getSourceArrowStyle() <em>Source Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceArrowStyle()
     * @generated
     * @ordered
     */
    protected ArrowStyle sourceArrowStyle = SOURCE_ARROW_STYLE_EDEFAULT;

    /**
     * The default value of the '{@link #getTargetArrowStyle() <em>Target Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetArrowStyle()
     * @generated
     * @ordered
     */
    protected static final ArrowStyle TARGET_ARROW_STYLE_EDEFAULT = ArrowStyle.INPUT_ARROW;

    /**
     * The cached value of the '{@link #getTargetArrowStyle() <em>Target Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetArrowStyle()
     * @generated
     * @ordered
     */
    protected ArrowStyle targetArrowStyle = TARGET_ARROW_STYLE_EDEFAULT;

    /**
     * The default value of the '{@link #getEdgeWidth() <em>Edge Width</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getEdgeWidth()
     * @generated
     * @ordered
     */
    protected static final int EDGE_WIDTH_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getEdgeWidth() <em>Edge Width</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getEdgeWidth()
     * @generated
     * @ordered
     */
    protected int edgeWidth = EDGE_WIDTH_EDEFAULT;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalEdgeStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.CONDITIONAL_EDGE_STYLE;
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR, oldColor, this.color));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR, oldColor, this.color));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__FONT_SIZE, oldFontSize, this.fontSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__ITALIC, oldItalic, this.italic));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__BOLD, oldBold, this.bold));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__UNDERLINE, oldUnderline, this.underline));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LineStyle getLineStyle() {
        return this.lineStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLineStyle(LineStyle newLineStyle) {
        LineStyle oldLineStyle = this.lineStyle;
        this.lineStyle = newLineStyle == null ? LINE_STYLE_EDEFAULT : newLineStyle;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__LINE_STYLE, oldLineStyle, this.lineStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ArrowStyle getSourceArrowStyle() {
        return this.sourceArrowStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSourceArrowStyle(ArrowStyle newSourceArrowStyle) {
        ArrowStyle oldSourceArrowStyle = this.sourceArrowStyle;
        this.sourceArrowStyle = newSourceArrowStyle == null ? SOURCE_ARROW_STYLE_EDEFAULT : newSourceArrowStyle;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE, oldSourceArrowStyle, this.sourceArrowStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ArrowStyle getTargetArrowStyle() {
        return this.targetArrowStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTargetArrowStyle(ArrowStyle newTargetArrowStyle) {
        ArrowStyle oldTargetArrowStyle = this.targetArrowStyle;
        this.targetArrowStyle = newTargetArrowStyle == null ? TARGET_ARROW_STYLE_EDEFAULT : newTargetArrowStyle;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE, oldTargetArrowStyle, this.targetArrowStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getEdgeWidth() {
        return this.edgeWidth;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEdgeWidth(int newEdgeWidth) {
        int oldEdgeWidth = this.edgeWidth;
        this.edgeWidth = newEdgeWidth;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__EDGE_WIDTH, oldEdgeWidth, this.edgeWidth));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__SHOW_ICON, oldShowIcon, this.showIcon));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.CONDITIONAL_EDGE_STYLE__LABEL_ICON, oldLabelIcon, this.labelIcon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR:
                if (resolve)
                    return this.getColor();
                return this.basicGetColor();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__FONT_SIZE:
                return this.getFontSize();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__ITALIC:
                return this.isItalic();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__BOLD:
                return this.isBold();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__UNDERLINE:
                return this.isUnderline();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH:
                return this.isStrikeThrough();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LINE_STYLE:
                return this.getLineStyle();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE:
                return this.getSourceArrowStyle();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE:
                return this.getTargetArrowStyle();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__EDGE_WIDTH:
                return this.getEdgeWidth();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SHOW_ICON:
                return this.isShowIcon();
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LABEL_ICON:
                return this.getLabelIcon();
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
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR:
                this.setColor((UserColor) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__FONT_SIZE:
                this.setFontSize((Integer) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__BOLD:
                this.setBold((Boolean) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__UNDERLINE:
                this.setUnderline((Boolean) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough((Boolean) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LINE_STYLE:
                this.setLineStyle((LineStyle) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE:
                this.setSourceArrowStyle((ArrowStyle) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE:
                this.setTargetArrowStyle((ArrowStyle) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__EDGE_WIDTH:
                this.setEdgeWidth((Integer) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SHOW_ICON:
                this.setShowIcon((Boolean) newValue);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LABEL_ICON:
                this.setLabelIcon((String) newValue);
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
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR:
                this.setColor((UserColor) null);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__FONT_SIZE:
                this.setFontSize(FONT_SIZE_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__BOLD:
                this.setBold(BOLD_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__UNDERLINE:
                this.setUnderline(UNDERLINE_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LINE_STYLE:
                this.setLineStyle(LINE_STYLE_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE:
                this.setSourceArrowStyle(SOURCE_ARROW_STYLE_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE:
                this.setTargetArrowStyle(TARGET_ARROW_STYLE_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__EDGE_WIDTH:
                this.setEdgeWidth(EDGE_WIDTH_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SHOW_ICON:
                this.setShowIcon(SHOW_ICON_EDEFAULT);
                return;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LABEL_ICON:
                this.setLabelIcon(LABEL_ICON_EDEFAULT);
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
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR:
                return this.color != null;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__FONT_SIZE:
                return this.fontSize != FONT_SIZE_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__BOLD:
                return this.bold != BOLD_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__UNDERLINE:
                return this.underline != UNDERLINE_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH:
                return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LINE_STYLE:
                return this.lineStyle != LINE_STYLE_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE:
                return this.sourceArrowStyle != SOURCE_ARROW_STYLE_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE:
                return this.targetArrowStyle != TARGET_ARROW_STYLE_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__EDGE_WIDTH:
                return this.edgeWidth != EDGE_WIDTH_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__SHOW_ICON:
                return this.showIcon != SHOW_ICON_EDEFAULT;
            case DiagramPackage.CONDITIONAL_EDGE_STYLE__LABEL_ICON:
                return LABEL_ICON_EDEFAULT == null ? this.labelIcon != null : !LABEL_ICON_EDEFAULT.equals(this.labelIcon);
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
        if (baseClass == Style.class) {
            switch (derivedFeatureID) {
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR:
                    return DiagramPackage.STYLE__COLOR;
                default:
                    return -1;
            }
        }
        if (baseClass == LabelStyle.class) {
            switch (derivedFeatureID) {
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__FONT_SIZE:
                    return ViewPackage.LABEL_STYLE__FONT_SIZE;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__ITALIC:
                    return ViewPackage.LABEL_STYLE__ITALIC;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__BOLD:
                    return ViewPackage.LABEL_STYLE__BOLD;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__UNDERLINE:
                    return ViewPackage.LABEL_STYLE__UNDERLINE;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH:
                    return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == EdgeStyle.class) {
            switch (derivedFeatureID) {
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__LINE_STYLE:
                    return DiagramPackage.EDGE_STYLE__LINE_STYLE;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE:
                    return DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE:
                    return DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__EDGE_WIDTH:
                    return DiagramPackage.EDGE_STYLE__EDGE_WIDTH;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__SHOW_ICON:
                    return DiagramPackage.EDGE_STYLE__SHOW_ICON;
                case DiagramPackage.CONDITIONAL_EDGE_STYLE__LABEL_ICON:
                    return DiagramPackage.EDGE_STYLE__LABEL_ICON;
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
        if (baseClass == Style.class) {
            switch (baseFeatureID) {
                case DiagramPackage.STYLE__COLOR:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__COLOR;
                default:
                    return -1;
            }
        }
        if (baseClass == LabelStyle.class) {
            switch (baseFeatureID) {
                case ViewPackage.LABEL_STYLE__FONT_SIZE:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__FONT_SIZE;
                case ViewPackage.LABEL_STYLE__ITALIC:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__ITALIC;
                case ViewPackage.LABEL_STYLE__BOLD:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__BOLD;
                case ViewPackage.LABEL_STYLE__UNDERLINE:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__UNDERLINE;
                case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == EdgeStyle.class) {
            switch (baseFeatureID) {
                case DiagramPackage.EDGE_STYLE__LINE_STYLE:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__LINE_STYLE;
                case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE;
                case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE;
                case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__EDGE_WIDTH;
                case DiagramPackage.EDGE_STYLE__SHOW_ICON:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__SHOW_ICON;
                case DiagramPackage.EDGE_STYLE__LABEL_ICON:
                    return DiagramPackage.CONDITIONAL_EDGE_STYLE__LABEL_ICON;
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
        result.append(", lineStyle: ");
        result.append(this.lineStyle);
        result.append(", sourceArrowStyle: ");
        result.append(this.sourceArrowStyle);
        result.append(", targetArrowStyle: ");
        result.append(this.targetArrowStyle);
        result.append(", edgeWidth: ");
        result.append(this.edgeWidth);
        result.append(", showIcon: ");
        result.append(this.showIcon);
        result.append(", labelIcon: ");
        result.append(this.labelIcon);
        result.append(')');
        return result.toString();
    }

} // ConditionalEdgeStyleImpl
