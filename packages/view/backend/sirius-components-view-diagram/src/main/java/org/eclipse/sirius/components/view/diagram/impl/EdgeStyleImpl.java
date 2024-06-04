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

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getFontSize <em>Font Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isBold <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isUnderline <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isStrikeThrough <em>Strike
 * Through</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderColor <em>Border Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderRadius <em>Border Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderSize <em>Border Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderLineStyle <em>Border Line
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getLineStyle <em>Line Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getSourceArrowStyle <em>Source Arrow
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getTargetArrowStyle <em>Target Arrow
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getEdgeWidth <em>Edge Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isShowIcon <em>Show Icon</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getLabelIcon <em>Label Icon</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBackground <em>Background</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeStyleImpl extends StyleImpl implements EdgeStyle {

    /**
     * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getFontSize()
     */
    protected static final int FONT_SIZE_EDEFAULT = 14;
    /**
     * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isItalic()
     */
    protected static final boolean ITALIC_EDEFAULT = false;
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
     * The default value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isUnderline()
     */
    protected static final boolean UNDERLINE_EDEFAULT = false;
    /**
     * The default value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isStrikeThrough()
     */
    protected static final boolean STRIKE_THROUGH_EDEFAULT = false;
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
     * The default value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLineStyle()
     */
    protected static final LineStyle LINE_STYLE_EDEFAULT = LineStyle.SOLID;
    /**
     * The default value of the '{@link #getSourceArrowStyle() <em>Source Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSourceArrowStyle()
     */
    protected static final ArrowStyle SOURCE_ARROW_STYLE_EDEFAULT = ArrowStyle.NONE;
    /**
     * The default value of the '{@link #getTargetArrowStyle() <em>Target Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTargetArrowStyle()
     */
    protected static final ArrowStyle TARGET_ARROW_STYLE_EDEFAULT = ArrowStyle.INPUT_ARROW;
    /**
     * The default value of the '{@link #getEdgeWidth() <em>Edge Width</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEdgeWidth()
     */
    protected static final int EDGE_WIDTH_EDEFAULT = 1;
    /**
     * The default value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isShowIcon()
     */
    protected static final boolean SHOW_ICON_EDEFAULT = false;
    /**
     * The default value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelIcon()
     */
    protected static final String LABEL_ICON_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getFontSize()
     */
    protected int fontSize = FONT_SIZE_EDEFAULT;
    /**
     * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isItalic()
     */
    protected boolean italic = ITALIC_EDEFAULT;
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
     * The cached value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isUnderline()
     */
    protected boolean underline = UNDERLINE_EDEFAULT;
    /**
     * The cached value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isStrikeThrough()
     */
    protected boolean strikeThrough = STRIKE_THROUGH_EDEFAULT;
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
     * The cached value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLineStyle()
     */
    protected LineStyle lineStyle = LINE_STYLE_EDEFAULT;
    /**
     * The cached value of the '{@link #getSourceArrowStyle() <em>Source Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSourceArrowStyle()
     */
    protected ArrowStyle sourceArrowStyle = SOURCE_ARROW_STYLE_EDEFAULT;
    /**
     * The cached value of the '{@link #getTargetArrowStyle() <em>Target Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTargetArrowStyle()
     */
    protected ArrowStyle targetArrowStyle = TARGET_ARROW_STYLE_EDEFAULT;
    /**
     * The cached value of the '{@link #getEdgeWidth() <em>Edge Width</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEdgeWidth()
     */
    protected int edgeWidth = EDGE_WIDTH_EDEFAULT;
    /**
     * The cached value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isShowIcon()
     */
    protected boolean showIcon = SHOW_ICON_EDEFAULT;
    /**
     * The cached value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelIcon()
     */
    protected String labelIcon = LABEL_ICON_EDEFAULT;

    /**
     * The cached value of the '{@link #getBackground() <em>Background</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBackground()
     */
    protected UserColor background;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EdgeStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.EDGE_STYLE;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__FONT_SIZE, oldFontSize, this.fontSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__ITALIC, oldItalic, this.italic));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BOLD, oldBold, this.bold));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__UNDERLINE, oldUnderline, this.underline));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.EDGE_STYLE__BORDER_COLOR, oldBorderColor, this.borderColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_COLOR, oldBorderColor, this.borderColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_RADIUS, oldBorderRadius, this.borderRadius));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_SIZE, oldBorderSize, this.borderSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE, oldBorderLineStyle, this.borderLineStyle));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__LINE_STYLE, oldLineStyle, this.lineStyle));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE, oldSourceArrowStyle, this.sourceArrowStyle));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE, oldTargetArrowStyle, this.targetArrowStyle));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__EDGE_WIDTH, oldEdgeWidth, this.edgeWidth));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__SHOW_ICON, oldShowIcon, this.showIcon));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__LABEL_ICON, oldLabelIcon, this.labelIcon));
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.EDGE_STYLE__BACKGROUND, oldBackground, this.background));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BACKGROUND, oldBackground, this.background));
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
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.EDGE_STYLE__FONT_SIZE:
                return this.getFontSize();
            case DiagramPackage.EDGE_STYLE__ITALIC:
                return this.isItalic();
            case DiagramPackage.EDGE_STYLE__BOLD:
                return this.isBold();
            case DiagramPackage.EDGE_STYLE__UNDERLINE:
                return this.isUnderline();
            case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
                return this.isStrikeThrough();
            case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
                if (resolve)
                    return this.getBorderColor();
                return this.basicGetBorderColor();
            case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
                return this.getBorderRadius();
            case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
                return this.getBorderSize();
            case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
                return this.getBorderLineStyle();
            case DiagramPackage.EDGE_STYLE__LINE_STYLE:
                return this.getLineStyle();
            case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
                return this.getSourceArrowStyle();
            case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
                return this.getTargetArrowStyle();
            case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
                return this.getEdgeWidth();
            case DiagramPackage.EDGE_STYLE__SHOW_ICON:
                return this.isShowIcon();
            case DiagramPackage.EDGE_STYLE__LABEL_ICON:
                return this.getLabelIcon();
            case DiagramPackage.EDGE_STYLE__BACKGROUND:
                if (resolve)
                    return this.getBackground();
                return this.basicGetBackground();
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
            case DiagramPackage.EDGE_STYLE__FONT_SIZE:
                this.setFontSize((Integer) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__BOLD:
                this.setBold((Boolean) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__UNDERLINE:
                this.setUnderline((Boolean) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough((Boolean) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
                this.setBorderColor((UserColor) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
                this.setBorderRadius((Integer) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
                this.setBorderSize((Integer) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
                this.setBorderLineStyle((LineStyle) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__LINE_STYLE:
                this.setLineStyle((LineStyle) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
                this.setSourceArrowStyle((ArrowStyle) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
                this.setTargetArrowStyle((ArrowStyle) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
                this.setEdgeWidth((Integer) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__SHOW_ICON:
                this.setShowIcon((Boolean) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__LABEL_ICON:
                this.setLabelIcon((String) newValue);
                return;
            case DiagramPackage.EDGE_STYLE__BACKGROUND:
                this.setBackground((UserColor) newValue);
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
            case DiagramPackage.EDGE_STYLE__FONT_SIZE:
                this.setFontSize(FONT_SIZE_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__BOLD:
                this.setBold(BOLD_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__UNDERLINE:
                this.setUnderline(UNDERLINE_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
                this.setBorderColor(null);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
                this.setBorderRadius(BORDER_RADIUS_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
                this.setBorderSize(BORDER_SIZE_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
                this.setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__LINE_STYLE:
                this.setLineStyle(LINE_STYLE_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
                this.setSourceArrowStyle(SOURCE_ARROW_STYLE_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
                this.setTargetArrowStyle(TARGET_ARROW_STYLE_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
                this.setEdgeWidth(EDGE_WIDTH_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__SHOW_ICON:
                this.setShowIcon(SHOW_ICON_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__LABEL_ICON:
                this.setLabelIcon(LABEL_ICON_EDEFAULT);
                return;
            case DiagramPackage.EDGE_STYLE__BACKGROUND:
                this.setBackground(null);
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
            case DiagramPackage.EDGE_STYLE__FONT_SIZE:
                return this.fontSize != FONT_SIZE_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__BOLD:
                return this.bold != BOLD_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__UNDERLINE:
                return this.underline != UNDERLINE_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
                return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
                return this.borderColor != null;
            case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
                return this.borderRadius != BORDER_RADIUS_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
                return this.borderSize != BORDER_SIZE_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
                return this.borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__LINE_STYLE:
                return this.lineStyle != LINE_STYLE_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
                return this.sourceArrowStyle != SOURCE_ARROW_STYLE_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
                return this.targetArrowStyle != TARGET_ARROW_STYLE_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
                return this.edgeWidth != EDGE_WIDTH_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__SHOW_ICON:
                return this.showIcon != SHOW_ICON_EDEFAULT;
            case DiagramPackage.EDGE_STYLE__LABEL_ICON:
                return !Objects.equals(LABEL_ICON_EDEFAULT, this.labelIcon);
            case DiagramPackage.EDGE_STYLE__BACKGROUND:
                return this.background != null;
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
                case DiagramPackage.EDGE_STYLE__FONT_SIZE:
                    return ViewPackage.LABEL_STYLE__FONT_SIZE;
                case DiagramPackage.EDGE_STYLE__ITALIC:
                    return ViewPackage.LABEL_STYLE__ITALIC;
                case DiagramPackage.EDGE_STYLE__BOLD:
                    return ViewPackage.LABEL_STYLE__BOLD;
                case DiagramPackage.EDGE_STYLE__UNDERLINE:
                    return ViewPackage.LABEL_STYLE__UNDERLINE;
                case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
                    return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == BorderStyle.class) {
            switch (derivedFeatureID) {
                case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
                    return DiagramPackage.BORDER_STYLE__BORDER_COLOR;
                case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
                    return DiagramPackage.BORDER_STYLE__BORDER_RADIUS;
                case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
                    return DiagramPackage.BORDER_STYLE__BORDER_SIZE;
                case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
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
        if (baseClass == LabelStyle.class) {
            switch (baseFeatureID) {
                case ViewPackage.LABEL_STYLE__FONT_SIZE:
                    return DiagramPackage.EDGE_STYLE__FONT_SIZE;
                case ViewPackage.LABEL_STYLE__ITALIC:
                    return DiagramPackage.EDGE_STYLE__ITALIC;
                case ViewPackage.LABEL_STYLE__BOLD:
                    return DiagramPackage.EDGE_STYLE__BOLD;
                case ViewPackage.LABEL_STYLE__UNDERLINE:
                    return DiagramPackage.EDGE_STYLE__UNDERLINE;
                case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
                    return DiagramPackage.EDGE_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == BorderStyle.class) {
            switch (baseFeatureID) {
                case DiagramPackage.BORDER_STYLE__BORDER_COLOR:
                    return DiagramPackage.EDGE_STYLE__BORDER_COLOR;
                case DiagramPackage.BORDER_STYLE__BORDER_RADIUS:
                    return DiagramPackage.EDGE_STYLE__BORDER_RADIUS;
                case DiagramPackage.BORDER_STYLE__BORDER_SIZE:
                    return DiagramPackage.EDGE_STYLE__BORDER_SIZE;
                case DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE:
                    return DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE;
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

        String result = super.toString() + " (fontSize: " +
                this.fontSize +
                ", italic: " +
                this.italic +
                ", bold: " +
                this.bold +
                ", underline: " +
                this.underline +
                ", strikeThrough: " +
                this.strikeThrough +
                ", borderRadius: " +
                this.borderRadius +
                ", borderSize: " +
                this.borderSize +
                ", borderLineStyle: " +
                this.borderLineStyle +
                ", lineStyle: " +
                this.lineStyle +
                ", sourceArrowStyle: " +
                this.sourceArrowStyle +
                ", targetArrowStyle: " +
                this.targetArrowStyle +
                ", edgeWidth: " +
                this.edgeWidth +
                ", showIcon: " +
                this.showIcon +
                ", labelIcon: " +
                this.labelIcon +
                ')';
        return result;
    }

} // EdgeStyleImpl
