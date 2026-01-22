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
package org.eclipse.sirius.components.view.diagram.impl;

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
import org.eclipse.sirius.components.view.diagram.EdgeType;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isItalic <em>Italic</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isBold <em>Bold</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isUnderline <em>Underline</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isStrikeThrough <em>Strike Through</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderRadius <em>Border Radius</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderSize <em>Border Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBorderLineStyle <em>Border Line Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getLineStyle <em>Line Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getSourceArrowStyle <em>Source Arrow Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getTargetArrowStyle <em>Target Arrow Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getEdgeWidth <em>Edge Width</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#isShowIcon <em>Show Icon</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getLabelIcon <em>Label Icon</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getBackground <em>Background</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getMaxWidthExpression <em>Max Width Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl#getEdgeType <em>Edge Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeStyleImpl extends StyleImpl implements EdgeStyle {

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
	 * The cached value of the '{@link #getBorderColor() <em>Border Color</em>}' reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBorderColor()
	 * @generated
	 * @ordered
	 */
    protected UserColor borderColor;

    /**
	 * The default value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBorderRadius()
	 * @generated
	 * @ordered
	 */
    protected static final int BORDER_RADIUS_EDEFAULT = 3;

    /**
	 * The cached value of the '{@link #getBorderRadius() <em>Border Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBorderRadius()
	 * @generated
	 * @ordered
	 */
    protected int borderRadius = BORDER_RADIUS_EDEFAULT;

    /**
	 * The default value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBorderSize()
	 * @generated
	 * @ordered
	 */
    protected static final int BORDER_SIZE_EDEFAULT = 1;

    /**
	 * The cached value of the '{@link #getBorderSize() <em>Border Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
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
	 * The cached value of the '{@link #getBorderLineStyle() <em>Border Line Style</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getBorderLineStyle()
	 * @generated
	 * @ordered
	 */
    protected LineStyle borderLineStyle = BORDER_LINE_STYLE_EDEFAULT;

    /**
	 * The default value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getLineStyle()
	 * @generated
	 * @ordered
	 */
    protected static final LineStyle LINE_STYLE_EDEFAULT = LineStyle.SOLID;

    /**
	 * The cached value of the '{@link #getLineStyle() <em>Line Style</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getLineStyle()
	 * @generated
	 * @ordered
	 */
    protected LineStyle lineStyle = LINE_STYLE_EDEFAULT;

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
     * The cached value of the '{@link #getSourceArrowStyle() <em>Source Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSourceArrowStyle()
     */
    protected ArrowStyle sourceArrowStyle = SOURCE_ARROW_STYLE_EDEFAULT;

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
     * The cached value of the '{@link #getTargetArrowStyle() <em>Target Arrow Style</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTargetArrowStyle()
     */
    protected ArrowStyle targetArrowStyle = TARGET_ARROW_STYLE_EDEFAULT;

    /**
	 * The default value of the '{@link #getEdgeWidth() <em>Edge Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getEdgeWidth()
	 * @generated
	 * @ordered
	 */
    protected static final int EDGE_WIDTH_EDEFAULT = 1;

    /**
	 * The cached value of the '{@link #getEdgeWidth() <em>Edge Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getEdgeWidth()
	 * @generated
	 * @ordered
	 */
    protected int edgeWidth = EDGE_WIDTH_EDEFAULT;

    /**
	 * The default value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isShowIcon()
	 * @generated
	 * @ordered
	 */
    protected static final boolean SHOW_ICON_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isShowIcon()
	 * @generated
	 * @ordered
	 */
    protected boolean showIcon = SHOW_ICON_EDEFAULT;

    /**
	 * The default value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getLabelIcon()
	 * @generated
	 * @ordered
	 */
    protected static final String LABEL_ICON_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getLabelIcon() <em>Label Icon</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getLabelIcon()
	 * @generated
	 * @ordered
	 */
    protected String labelIcon = LABEL_ICON_EDEFAULT;

    /**
	 * The cached value of the '{@link #getBackground() <em>Background</em>}' reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
    protected UserColor background;

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
	 * The default value of the '{@link #getEdgeType() <em>Edge Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getEdgeType()
	 * @generated
	 * @ordered
	 */
    protected static final EdgeType EDGE_TYPE_EDEFAULT = EdgeType.MANHATTAN;

    /**
	 * The cached value of the '{@link #getEdgeType() <em>Edge Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getEdgeType()
	 * @generated
	 * @ordered
	 */
    protected EdgeType edgeType = EDGE_TYPE_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected EdgeStyleImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.EDGE_STYLE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__FONT_SIZE, oldFontSize, fontSize));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__ITALIC, oldItalic, italic));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BOLD, oldBold, bold));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__UNDERLINE, oldUnderline, underline));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__STRIKE_THROUGH, oldStrikeThrough, strikeThrough));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getBorderColor() {
		if (borderColor != null && borderColor.eIsProxy())
		{
			InternalEObject oldBorderColor = (InternalEObject)borderColor;
			borderColor = (UserColor)eResolveProxy(oldBorderColor);
			if (borderColor != oldBorderColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.EDGE_STYLE__BORDER_COLOR, oldBorderColor, borderColor));
			}
		}
		return borderColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderColor(UserColor newBorderColor) {
		UserColor oldBorderColor = borderColor;
		borderColor = newBorderColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_COLOR, oldBorderColor, borderColor));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetBorderColor() {
		return borderColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getBorderRadius() {
		return borderRadius;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderRadius(int newBorderRadius) {
		int oldBorderRadius = borderRadius;
		borderRadius = newBorderRadius;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_RADIUS, oldBorderRadius, borderRadius));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getBorderSize() {
		return borderSize;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderSize(int newBorderSize) {
		int oldBorderSize = borderSize;
		borderSize = newBorderSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_SIZE, oldBorderSize, borderSize));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LineStyle getBorderLineStyle() {
		return borderLineStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBorderLineStyle(LineStyle newBorderLineStyle) {
		LineStyle oldBorderLineStyle = borderLineStyle;
		borderLineStyle = newBorderLineStyle == null ? BORDER_LINE_STYLE_EDEFAULT : newBorderLineStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE, oldBorderLineStyle, borderLineStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public LineStyle getLineStyle() {
		return lineStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLineStyle(LineStyle newLineStyle) {
		LineStyle oldLineStyle = lineStyle;
		lineStyle = newLineStyle == null ? LINE_STYLE_EDEFAULT : newLineStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__LINE_STYLE, oldLineStyle, lineStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ArrowStyle getSourceArrowStyle() {
		return sourceArrowStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSourceArrowStyle(ArrowStyle newSourceArrowStyle) {
		ArrowStyle oldSourceArrowStyle = sourceArrowStyle;
		sourceArrowStyle = newSourceArrowStyle == null ? SOURCE_ARROW_STYLE_EDEFAULT : newSourceArrowStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE, oldSourceArrowStyle, sourceArrowStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ArrowStyle getTargetArrowStyle() {
		return targetArrowStyle;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setTargetArrowStyle(ArrowStyle newTargetArrowStyle) {
		ArrowStyle oldTargetArrowStyle = targetArrowStyle;
		targetArrowStyle = newTargetArrowStyle == null ? TARGET_ARROW_STYLE_EDEFAULT : newTargetArrowStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE, oldTargetArrowStyle, targetArrowStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getEdgeWidth() {
		return edgeWidth;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEdgeWidth(int newEdgeWidth) {
		int oldEdgeWidth = edgeWidth;
		edgeWidth = newEdgeWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__EDGE_WIDTH, oldEdgeWidth, edgeWidth));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isShowIcon() {
		return showIcon;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setShowIcon(boolean newShowIcon) {
		boolean oldShowIcon = showIcon;
		showIcon = newShowIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__SHOW_ICON, oldShowIcon, showIcon));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLabelIcon() {
		return labelIcon;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLabelIcon(String newLabelIcon) {
		String oldLabelIcon = labelIcon;
		labelIcon = newLabelIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__LABEL_ICON, oldLabelIcon, labelIcon));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getBackground() {
		if (background != null && background.eIsProxy())
		{
			InternalEObject oldBackground = (InternalEObject)background;
			background = (UserColor)eResolveProxy(oldBackground);
			if (background != oldBackground)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagramPackage.EDGE_STYLE__BACKGROUND, oldBackground, background));
			}
		}
		return background;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBackground(UserColor newBackground) {
		UserColor oldBackground = background;
		background = newBackground;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__BACKGROUND, oldBackground, background));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getMaxWidthExpression() {
		return maxWidthExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setMaxWidthExpression(String newMaxWidthExpression) {
		String oldMaxWidthExpression = maxWidthExpression;
		maxWidthExpression = newMaxWidthExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__MAX_WIDTH_EXPRESSION, oldMaxWidthExpression, maxWidthExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EdgeType getEdgeType() {
		return edgeType;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEdgeType(EdgeType newEdgeType) {
		EdgeType oldEdgeType = edgeType;
		edgeType = newEdgeType == null ? EDGE_TYPE_EDEFAULT : newEdgeType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.EDGE_STYLE__EDGE_TYPE, oldEdgeType, edgeType));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetBackground() {
		return background;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.EDGE_STYLE__FONT_SIZE:
				return getFontSize();
			case DiagramPackage.EDGE_STYLE__ITALIC:
				return isItalic();
			case DiagramPackage.EDGE_STYLE__BOLD:
				return isBold();
			case DiagramPackage.EDGE_STYLE__UNDERLINE:
				return isUnderline();
			case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
				return isStrikeThrough();
			case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
				if (resolve) return getBorderColor();
				return basicGetBorderColor();
			case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
				return getBorderRadius();
			case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
				return getBorderSize();
			case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
				return getBorderLineStyle();
			case DiagramPackage.EDGE_STYLE__LINE_STYLE:
				return getLineStyle();
			case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
				return getSourceArrowStyle();
			case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
				return getTargetArrowStyle();
			case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
				return getEdgeWidth();
			case DiagramPackage.EDGE_STYLE__SHOW_ICON:
				return isShowIcon();
			case DiagramPackage.EDGE_STYLE__LABEL_ICON:
				return getLabelIcon();
			case DiagramPackage.EDGE_STYLE__BACKGROUND:
				if (resolve) return getBackground();
				return basicGetBackground();
			case DiagramPackage.EDGE_STYLE__MAX_WIDTH_EXPRESSION:
				return getMaxWidthExpression();
			case DiagramPackage.EDGE_STYLE__EDGE_TYPE:
				return getEdgeType();
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
			case DiagramPackage.EDGE_STYLE__FONT_SIZE:
				setFontSize((Integer)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__ITALIC:
				setItalic((Boolean)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__BOLD:
				setBold((Boolean)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__UNDERLINE:
				setUnderline((Boolean)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
				setStrikeThrough((Boolean)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
				setBorderColor((UserColor)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
				setBorderRadius((Integer)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
				setBorderSize((Integer)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
				setBorderLineStyle((LineStyle)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__LINE_STYLE:
				setLineStyle((LineStyle)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
				setSourceArrowStyle((ArrowStyle)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
				setTargetArrowStyle((ArrowStyle)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
				setEdgeWidth((Integer)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__SHOW_ICON:
				setShowIcon((Boolean)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__LABEL_ICON:
				setLabelIcon((String)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__BACKGROUND:
				setBackground((UserColor)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__MAX_WIDTH_EXPRESSION:
				setMaxWidthExpression((String)newValue);
				return;
			case DiagramPackage.EDGE_STYLE__EDGE_TYPE:
				setEdgeType((EdgeType)newValue);
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
			case DiagramPackage.EDGE_STYLE__FONT_SIZE:
				setFontSize(FONT_SIZE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__ITALIC:
				setItalic(ITALIC_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__BOLD:
				setBold(BOLD_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__UNDERLINE:
				setUnderline(UNDERLINE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
				setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
				setBorderColor((UserColor)null);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
				setBorderRadius(BORDER_RADIUS_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
				setBorderSize(BORDER_SIZE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
				setBorderLineStyle(BORDER_LINE_STYLE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__LINE_STYLE:
				setLineStyle(LINE_STYLE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
				setSourceArrowStyle(SOURCE_ARROW_STYLE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
				setTargetArrowStyle(TARGET_ARROW_STYLE_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
				setEdgeWidth(EDGE_WIDTH_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__SHOW_ICON:
				setShowIcon(SHOW_ICON_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__LABEL_ICON:
				setLabelIcon(LABEL_ICON_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__BACKGROUND:
				setBackground((UserColor)null);
				return;
			case DiagramPackage.EDGE_STYLE__MAX_WIDTH_EXPRESSION:
				setMaxWidthExpression(MAX_WIDTH_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.EDGE_STYLE__EDGE_TYPE:
				setEdgeType(EDGE_TYPE_EDEFAULT);
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
			case DiagramPackage.EDGE_STYLE__FONT_SIZE:
				return fontSize != FONT_SIZE_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__ITALIC:
				return italic != ITALIC_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__BOLD:
				return bold != BOLD_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__UNDERLINE:
				return underline != UNDERLINE_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH:
				return strikeThrough != STRIKE_THROUGH_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__BORDER_COLOR:
				return borderColor != null;
			case DiagramPackage.EDGE_STYLE__BORDER_RADIUS:
				return borderRadius != BORDER_RADIUS_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__BORDER_SIZE:
				return borderSize != BORDER_SIZE_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE:
				return borderLineStyle != BORDER_LINE_STYLE_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__LINE_STYLE:
				return lineStyle != LINE_STYLE_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__SOURCE_ARROW_STYLE:
				return sourceArrowStyle != SOURCE_ARROW_STYLE_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__TARGET_ARROW_STYLE:
				return targetArrowStyle != TARGET_ARROW_STYLE_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__EDGE_WIDTH:
				return edgeWidth != EDGE_WIDTH_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__SHOW_ICON:
				return showIcon != SHOW_ICON_EDEFAULT;
			case DiagramPackage.EDGE_STYLE__LABEL_ICON:
				return LABEL_ICON_EDEFAULT == null ? labelIcon != null : !LABEL_ICON_EDEFAULT.equals(labelIcon);
			case DiagramPackage.EDGE_STYLE__BACKGROUND:
				return background != null;
			case DiagramPackage.EDGE_STYLE__MAX_WIDTH_EXPRESSION:
				return MAX_WIDTH_EXPRESSION_EDEFAULT == null ? maxWidthExpression != null : !MAX_WIDTH_EXPRESSION_EDEFAULT.equals(maxWidthExpression);
			case DiagramPackage.EDGE_STYLE__EDGE_TYPE:
				return edgeType != EDGE_TYPE_EDEFAULT;
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
				case DiagramPackage.EDGE_STYLE__FONT_SIZE: return ViewPackage.LABEL_STYLE__FONT_SIZE;
				case DiagramPackage.EDGE_STYLE__ITALIC: return ViewPackage.LABEL_STYLE__ITALIC;
				case DiagramPackage.EDGE_STYLE__BOLD: return ViewPackage.LABEL_STYLE__BOLD;
				case DiagramPackage.EDGE_STYLE__UNDERLINE: return ViewPackage.LABEL_STYLE__UNDERLINE;
				case DiagramPackage.EDGE_STYLE__STRIKE_THROUGH: return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		if (baseClass == BorderStyle.class)
		{
			switch (derivedFeatureID)
			{
				case DiagramPackage.EDGE_STYLE__BORDER_COLOR: return DiagramPackage.BORDER_STYLE__BORDER_COLOR;
				case DiagramPackage.EDGE_STYLE__BORDER_RADIUS: return DiagramPackage.BORDER_STYLE__BORDER_RADIUS;
				case DiagramPackage.EDGE_STYLE__BORDER_SIZE: return DiagramPackage.BORDER_STYLE__BORDER_SIZE;
				case DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE: return DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE;
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
				case ViewPackage.LABEL_STYLE__FONT_SIZE: return DiagramPackage.EDGE_STYLE__FONT_SIZE;
				case ViewPackage.LABEL_STYLE__ITALIC: return DiagramPackage.EDGE_STYLE__ITALIC;
				case ViewPackage.LABEL_STYLE__BOLD: return DiagramPackage.EDGE_STYLE__BOLD;
				case ViewPackage.LABEL_STYLE__UNDERLINE: return DiagramPackage.EDGE_STYLE__UNDERLINE;
				case ViewPackage.LABEL_STYLE__STRIKE_THROUGH: return DiagramPackage.EDGE_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		if (baseClass == BorderStyle.class)
		{
			switch (baseFeatureID)
			{
				case DiagramPackage.BORDER_STYLE__BORDER_COLOR: return DiagramPackage.EDGE_STYLE__BORDER_COLOR;
				case DiagramPackage.BORDER_STYLE__BORDER_RADIUS: return DiagramPackage.EDGE_STYLE__BORDER_RADIUS;
				case DiagramPackage.BORDER_STYLE__BORDER_SIZE: return DiagramPackage.EDGE_STYLE__BORDER_SIZE;
				case DiagramPackage.BORDER_STYLE__BORDER_LINE_STYLE: return DiagramPackage.EDGE_STYLE__BORDER_LINE_STYLE;
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
		result.append(", borderRadius: ");
		result.append(borderRadius);
		result.append(", borderSize: ");
		result.append(borderSize);
		result.append(", borderLineStyle: ");
		result.append(borderLineStyle);
		result.append(", lineStyle: ");
		result.append(lineStyle);
		result.append(", sourceArrowStyle: ");
		result.append(sourceArrowStyle);
		result.append(", targetArrowStyle: ");
		result.append(targetArrowStyle);
		result.append(", edgeWidth: ");
		result.append(edgeWidth);
		result.append(", showIcon: ");
		result.append(showIcon);
		result.append(", labelIcon: ");
		result.append(labelIcon);
		result.append(", maxWidthExpression: ");
		result.append(maxWidthExpression);
		result.append(", edgeType: ");
		result.append(edgeType);
		result.append(')');
		return result.toString();
	}

} // EdgeStyleImpl
