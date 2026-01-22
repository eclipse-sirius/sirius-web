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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.Action;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.UserResizableDirection;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#isCollapsible
 * <em>Collapsible</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getChildrenLayoutStrategy <em>Children
 * Layout Strategy</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getChildrenDescriptions <em>Children
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getBorderNodesDescriptions <em>Border
 * Nodes Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getReusedChildNodeDescriptions
 * <em>Reused Child Node Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#getReusedBorderNodeDescriptions
 * <em>Reused Border Node Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl#isUserResizable <em>User
 * Resizable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeDescriptionImpl extends DiagramElementDescriptionImpl implements NodeDescription {

    /**
	 * The default value of the '{@link #isCollapsible() <em>Collapsible</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isCollapsible()
	 * @generated
	 * @ordered
	 */
    protected static final boolean COLLAPSIBLE_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isCollapsible() <em>Collapsible</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isCollapsible()
	 * @generated
	 * @ordered
	 */
    protected boolean collapsible = COLLAPSIBLE_EDEFAULT;

    /**
	 * The cached value of the '{@link #getPalette() <em>Palette</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPalette()
	 * @generated
	 * @ordered
	 */
    protected NodePalette palette;

    /**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
    protected EList<Action> actions;

    /**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
    protected NodeStyleDescription style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalNodeStyle> conditionalStyles;

    /**
	 * The cached value of the '{@link #getChildrenDescriptions() <em>Children Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getChildrenDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> childrenDescriptions;

    /**
	 * The cached value of the '{@link #getBorderNodesDescriptions() <em>Border Nodes Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBorderNodesDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> borderNodesDescriptions;

    /**
	 * The cached value of the '{@link #getReusedChildNodeDescriptions() <em>Reused Child Node Descriptions</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getReusedChildNodeDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> reusedChildNodeDescriptions;

    /**
	 * The cached value of the '{@link #getReusedBorderNodeDescriptions() <em>Reused Border Node Descriptions</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getReusedBorderNodeDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<NodeDescription> reusedBorderNodeDescriptions;

    /**
	 * The default value of the '{@link #getUserResizable() <em>User Resizable</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUserResizable()
	 * @generated
	 * @ordered
	 */
    protected static final UserResizableDirection USER_RESIZABLE_EDEFAULT = UserResizableDirection.BOTH;

    /**
	 * The cached value of the '{@link #getUserResizable() <em>User Resizable</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUserResizable()
	 * @generated
	 * @ordered
	 */
    protected UserResizableDirection userResizable = USER_RESIZABLE_EDEFAULT;

    /**
     * The default value of the '{@link #getDefaultWidthExpression() <em>Default Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDefaultWidthExpression()
     */
    protected static final String DEFAULT_WIDTH_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultWidthExpression() <em>Default Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDefaultWidthExpression()
     */
    protected String defaultWidthExpression = DEFAULT_WIDTH_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getDefaultHeightExpression() <em>Default Height Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDefaultHeightExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String DEFAULT_HEIGHT_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDefaultHeightExpression() <em>Default Height Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDefaultHeightExpression()
	 * @generated
	 * @ordered
	 */
    protected String defaultHeightExpression = DEFAULT_HEIGHT_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #isKeepAspectRatio() <em>Keep Aspect Ratio</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #isKeepAspectRatio()
	 * @generated
	 * @ordered
	 */
    protected static final boolean KEEP_ASPECT_RATIO_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isKeepAspectRatio() <em>Keep Aspect Ratio</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #isKeepAspectRatio()
	 * @generated
	 * @ordered
	 */
    protected boolean keepAspectRatio = KEEP_ASPECT_RATIO_EDEFAULT;

    /**
	 * The default value of the '{@link #getIsCollapsedByDefaultExpression() <em>Is Collapsed By Default Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsCollapsedByDefaultExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String IS_COLLAPSED_BY_DEFAULT_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getIsCollapsedByDefaultExpression() <em>Is Collapsed By Default Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsCollapsedByDefaultExpression()
	 * @generated
	 * @ordered
	 */
    protected String isCollapsedByDefaultExpression = IS_COLLAPSED_BY_DEFAULT_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getInsideLabel() <em>Inside Label</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInsideLabel()
     */
    protected InsideLabelDescription insideLabel;

    /**
     * The cached value of the '{@link #getOutsideLabels() <em>Outside Labels</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getOutsideLabels()
     */
    protected EList<OutsideLabelDescription> outsideLabels;

    /**
	 * The default value of the '{@link #getIsHiddenByDefaultExpression() <em>Is Hidden By Default Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsHiddenByDefaultExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String IS_HIDDEN_BY_DEFAULT_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getIsHiddenByDefaultExpression() <em>Is Hidden By Default Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsHiddenByDefaultExpression()
	 * @generated
	 * @ordered
	 */
    protected String isHiddenByDefaultExpression = IS_HIDDEN_BY_DEFAULT_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getIsFadedByDefaultExpression() <em>Is Faded By Default Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsFadedByDefaultExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String IS_FADED_BY_DEFAULT_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getIsFadedByDefaultExpression() <em>Is Faded By Default Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIsFadedByDefaultExpression()
	 * @generated
	 * @ordered
	 */
    protected String isFadedByDefaultExpression = IS_FADED_BY_DEFAULT_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected NodeDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.NODE_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isCollapsible() {
		return collapsible;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCollapsible(boolean newCollapsible) {
		boolean oldCollapsible = collapsible;
		collapsible = newCollapsible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE, oldCollapsible, collapsible));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NodePalette getPalette() {
		return palette;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPalette(NodePalette newPalette) {
		if (newPalette != palette)
		{
			NotificationChain msgs = null;
			if (palette != null)
				msgs = ((InternalEObject)palette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__PALETTE, null, msgs);
			if (newPalette != null)
				msgs = ((InternalEObject)newPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__PALETTE, null, msgs);
			msgs = basicSetPalette(newPalette, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__PALETTE, newPalette, newPalette));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Action> getActions() {
		if (actions == null)
		{
			actions = new EObjectContainmentEList<Action>(Action.class, this, DiagramPackage.NODE_DESCRIPTION__ACTIONS);
		}
		return actions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPalette(NodePalette newPalette, NotificationChain msgs) {
		NodePalette oldPalette = palette;
		palette = newPalette;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__PALETTE, oldPalette, newPalette);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NodeStyleDescription getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(NodeStyleDescription newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(NodeStyleDescription newStyle, NotificationChain msgs) {
		NodeStyleDescription oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalNodeStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalNodeStyle>(ConditionalNodeStyle.class, this, DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES);
		}
		return conditionalStyles;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getChildrenDescriptions() {
		if (childrenDescriptions == null)
		{
			childrenDescriptions = new EObjectContainmentEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
		}
		return childrenDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getBorderNodesDescriptions() {
		if (borderNodesDescriptions == null)
		{
			borderNodesDescriptions = new EObjectContainmentEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS);
		}
		return borderNodesDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getReusedChildNodeDescriptions() {
		if (reusedChildNodeDescriptions == null)
		{
			reusedChildNodeDescriptions = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS);
		}
		return reusedChildNodeDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<NodeDescription> getReusedBorderNodeDescriptions() {
		if (reusedBorderNodeDescriptions == null)
		{
			reusedBorderNodeDescriptions = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS);
		}
		return reusedBorderNodeDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserResizableDirection getUserResizable() {
		return userResizable;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setUserResizable(UserResizableDirection newUserResizable) {
		UserResizableDirection oldUserResizable = userResizable;
		userResizable = newUserResizable == null ? USER_RESIZABLE_EDEFAULT : newUserResizable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE, oldUserResizable, userResizable));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDefaultWidthExpression() {
		return defaultWidthExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDefaultWidthExpression(String newDefaultWidthExpression) {
		String oldDefaultWidthExpression = defaultWidthExpression;
		defaultWidthExpression = newDefaultWidthExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION, oldDefaultWidthExpression, defaultWidthExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDefaultHeightExpression() {
		return defaultHeightExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDefaultHeightExpression(String newDefaultHeightExpression) {
		String oldDefaultHeightExpression = defaultHeightExpression;
		defaultHeightExpression = newDefaultHeightExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION, oldDefaultHeightExpression, defaultHeightExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isKeepAspectRatio() {
		return keepAspectRatio;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setKeepAspectRatio(boolean newKeepAspectRatio) {
		boolean oldKeepAspectRatio = keepAspectRatio;
		keepAspectRatio = newKeepAspectRatio;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO, oldKeepAspectRatio, keepAspectRatio));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsCollapsedByDefaultExpression() {
		return isCollapsedByDefaultExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsCollapsedByDefaultExpression(String newIsCollapsedByDefaultExpression) {
		String oldIsCollapsedByDefaultExpression = isCollapsedByDefaultExpression;
		isCollapsedByDefaultExpression = newIsCollapsedByDefaultExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION, oldIsCollapsedByDefaultExpression, isCollapsedByDefaultExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public InsideLabelDescription getInsideLabel() {
		return insideLabel;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setInsideLabel(InsideLabelDescription newInsideLabel) {
		if (newInsideLabel != insideLabel)
		{
			NotificationChain msgs = null;
			if (insideLabel != null)
				msgs = ((InternalEObject)insideLabel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, null, msgs);
			if (newInsideLabel != null)
				msgs = ((InternalEObject)newInsideLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, null, msgs);
			msgs = basicSetInsideLabel(newInsideLabel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, newInsideLabel, newInsideLabel));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetInsideLabel(InsideLabelDescription newInsideLabel, NotificationChain msgs) {
		InsideLabelDescription oldInsideLabel = insideLabel;
		insideLabel = newInsideLabel;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, oldInsideLabel, newInsideLabel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<OutsideLabelDescription> getOutsideLabels() {
		if (outsideLabels == null)
		{
			outsideLabels = new EObjectContainmentEList<OutsideLabelDescription>(OutsideLabelDescription.class, this, DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS);
		}
		return outsideLabels;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsHiddenByDefaultExpression() {
		return isHiddenByDefaultExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsHiddenByDefaultExpression(String newIsHiddenByDefaultExpression) {
		String oldIsHiddenByDefaultExpression = isHiddenByDefaultExpression;
		isHiddenByDefaultExpression = newIsHiddenByDefaultExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION, oldIsHiddenByDefaultExpression, isHiddenByDefaultExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsFadedByDefaultExpression() {
		return isFadedByDefaultExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsFadedByDefaultExpression(String newIsFadedByDefaultExpression) {
		String oldIsFadedByDefaultExpression = isFadedByDefaultExpression;
		isFadedByDefaultExpression = newIsFadedByDefaultExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION, oldIsFadedByDefaultExpression, isFadedByDefaultExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.NODE_DESCRIPTION__PALETTE:
				return basicSetPalette(null, msgs);
			case DiagramPackage.NODE_DESCRIPTION__ACTIONS:
				return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
			case DiagramPackage.NODE_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
				return ((InternalEList<?>)getConditionalStyles()).basicRemove(otherEnd, msgs);
			case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
				return ((InternalEList<?>)getChildrenDescriptions()).basicRemove(otherEnd, msgs);
			case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
				return ((InternalEList<?>)getBorderNodesDescriptions()).basicRemove(otherEnd, msgs);
			case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
				return basicSetInsideLabel(null, msgs);
			case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
				return ((InternalEList<?>)getOutsideLabels()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
				return isCollapsible();
			case DiagramPackage.NODE_DESCRIPTION__PALETTE:
				return getPalette();
			case DiagramPackage.NODE_DESCRIPTION__ACTIONS:
				return getActions();
			case DiagramPackage.NODE_DESCRIPTION__STYLE:
				return getStyle();
			case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
				return getConditionalStyles();
			case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
				return getChildrenDescriptions();
			case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
				return getBorderNodesDescriptions();
			case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
				return getReusedChildNodeDescriptions();
			case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
				return getReusedBorderNodeDescriptions();
			case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
				return getUserResizable();
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
				return getDefaultWidthExpression();
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
				return getDefaultHeightExpression();
			case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
				return isKeepAspectRatio();
			case DiagramPackage.NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION:
				return getIsCollapsedByDefaultExpression();
			case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
				return getInsideLabel();
			case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
				return getOutsideLabels();
			case DiagramPackage.NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION:
				return getIsHiddenByDefaultExpression();
			case DiagramPackage.NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION:
				return getIsFadedByDefaultExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
				setCollapsible((Boolean)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__PALETTE:
				setPalette((NodePalette)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends Action>)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__STYLE:
				setStyle((NodeStyleDescription)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalNodeStyle>)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
				getChildrenDescriptions().clear();
				getChildrenDescriptions().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
				getBorderNodesDescriptions().clear();
				getBorderNodesDescriptions().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
				getReusedChildNodeDescriptions().clear();
				getReusedChildNodeDescriptions().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
				getReusedBorderNodeDescriptions().clear();
				getReusedBorderNodeDescriptions().addAll((Collection<? extends NodeDescription>)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
				setUserResizable((UserResizableDirection)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
				setDefaultWidthExpression((String)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
				setDefaultHeightExpression((String)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
				setKeepAspectRatio((Boolean)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION:
				setIsCollapsedByDefaultExpression((String)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
				setInsideLabel((InsideLabelDescription)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
				getOutsideLabels().clear();
				getOutsideLabels().addAll((Collection<? extends OutsideLabelDescription>)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION:
				setIsHiddenByDefaultExpression((String)newValue);
				return;
			case DiagramPackage.NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION:
				setIsFadedByDefaultExpression((String)newValue);
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
			case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
				setCollapsible(COLLAPSIBLE_EDEFAULT);
				return;
			case DiagramPackage.NODE_DESCRIPTION__PALETTE:
				setPalette((NodePalette)null);
				return;
			case DiagramPackage.NODE_DESCRIPTION__ACTIONS:
				getActions().clear();
				return;
			case DiagramPackage.NODE_DESCRIPTION__STYLE:
				setStyle((NodeStyleDescription)null);
				return;
			case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				return;
			case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
				getChildrenDescriptions().clear();
				return;
			case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
				getBorderNodesDescriptions().clear();
				return;
			case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
				getReusedChildNodeDescriptions().clear();
				return;
			case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
				getReusedBorderNodeDescriptions().clear();
				return;
			case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
				setUserResizable(USER_RESIZABLE_EDEFAULT);
				return;
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
				setDefaultWidthExpression(DEFAULT_WIDTH_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
				setDefaultHeightExpression(DEFAULT_HEIGHT_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
				setKeepAspectRatio(KEEP_ASPECT_RATIO_EDEFAULT);
				return;
			case DiagramPackage.NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION:
				setIsCollapsedByDefaultExpression(IS_COLLAPSED_BY_DEFAULT_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
				setInsideLabel((InsideLabelDescription)null);
				return;
			case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
				getOutsideLabels().clear();
				return;
			case DiagramPackage.NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION:
				setIsHiddenByDefaultExpression(IS_HIDDEN_BY_DEFAULT_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION:
				setIsFadedByDefaultExpression(IS_FADED_BY_DEFAULT_EXPRESSION_EDEFAULT);
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
			case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
				return collapsible != COLLAPSIBLE_EDEFAULT;
			case DiagramPackage.NODE_DESCRIPTION__PALETTE:
				return palette != null;
			case DiagramPackage.NODE_DESCRIPTION__ACTIONS:
				return actions != null && !actions.isEmpty();
			case DiagramPackage.NODE_DESCRIPTION__STYLE:
				return style != null;
			case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
			case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
				return childrenDescriptions != null && !childrenDescriptions.isEmpty();
			case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
				return borderNodesDescriptions != null && !borderNodesDescriptions.isEmpty();
			case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
				return reusedChildNodeDescriptions != null && !reusedChildNodeDescriptions.isEmpty();
			case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
				return reusedBorderNodeDescriptions != null && !reusedBorderNodeDescriptions.isEmpty();
			case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
				return userResizable != USER_RESIZABLE_EDEFAULT;
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
				return DEFAULT_WIDTH_EXPRESSION_EDEFAULT == null ? defaultWidthExpression != null : !DEFAULT_WIDTH_EXPRESSION_EDEFAULT.equals(defaultWidthExpression);
			case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
				return DEFAULT_HEIGHT_EXPRESSION_EDEFAULT == null ? defaultHeightExpression != null : !DEFAULT_HEIGHT_EXPRESSION_EDEFAULT.equals(defaultHeightExpression);
			case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
				return keepAspectRatio != KEEP_ASPECT_RATIO_EDEFAULT;
			case DiagramPackage.NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION:
				return IS_COLLAPSED_BY_DEFAULT_EXPRESSION_EDEFAULT == null ? isCollapsedByDefaultExpression != null : !IS_COLLAPSED_BY_DEFAULT_EXPRESSION_EDEFAULT.equals(isCollapsedByDefaultExpression);
			case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
				return insideLabel != null;
			case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
				return outsideLabels != null && !outsideLabels.isEmpty();
			case DiagramPackage.NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION:
				return IS_HIDDEN_BY_DEFAULT_EXPRESSION_EDEFAULT == null ? isHiddenByDefaultExpression != null : !IS_HIDDEN_BY_DEFAULT_EXPRESSION_EDEFAULT.equals(isHiddenByDefaultExpression);
			case DiagramPackage.NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION:
				return IS_FADED_BY_DEFAULT_EXPRESSION_EDEFAULT == null ? isFadedByDefaultExpression != null : !IS_FADED_BY_DEFAULT_EXPRESSION_EDEFAULT.equals(isFadedByDefaultExpression);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (collapsible: ");
		result.append(collapsible);
		result.append(", userResizable: ");
		result.append(userResizable);
		result.append(", defaultWidthExpression: ");
		result.append(defaultWidthExpression);
		result.append(", defaultHeightExpression: ");
		result.append(defaultHeightExpression);
		result.append(", keepAspectRatio: ");
		result.append(keepAspectRatio);
		result.append(", isCollapsedByDefaultExpression: ");
		result.append(isCollapsedByDefaultExpression);
		result.append(", isHiddenByDefaultExpression: ");
		result.append(isHiddenByDefaultExpression);
		result.append(", isFadedByDefaultExpression: ");
		result.append(isFadedByDefaultExpression);
		result.append(')');
		return result.toString();
	}

} // NodeDescriptionImpl
