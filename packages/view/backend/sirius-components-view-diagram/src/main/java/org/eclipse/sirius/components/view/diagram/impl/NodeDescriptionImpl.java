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
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;

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
     * The default value of the '{@link #isCollapsible() <em>Collapsible</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isCollapsible()
     */
    protected static final boolean COLLAPSIBLE_EDEFAULT = false;
    /**
     * The default value of the '{@link #isUserResizable() <em>User Resizable</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isUserResizable()
     */
    protected static final boolean USER_RESIZABLE_EDEFAULT = true;
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
     * The default value of the '{@link #getDefaultHeightExpression() <em>Default Height Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDefaultHeightExpression()
     */
    protected static final String DEFAULT_HEIGHT_EXPRESSION_EDEFAULT = null;
    /**
     * The default value of the '{@link #isKeepAspectRatio() <em>Keep Aspect Ratio</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isKeepAspectRatio()
     */
    protected static final boolean KEEP_ASPECT_RATIO_EDEFAULT = false;
    /**
     * The cached value of the '{@link #isCollapsible() <em>Collapsible</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isCollapsible()
     */
    protected boolean collapsible = COLLAPSIBLE_EDEFAULT;
    /**
     * The cached value of the '{@link #getPalette() <em>Palette</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPalette()
     */
    protected NodePalette palette;
    /**
     * The cached value of the '{@link #getChildrenLayoutStrategy() <em>Children Layout Strategy</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getChildrenLayoutStrategy()
     */
    protected LayoutStrategyDescription childrenLayoutStrategy;
    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getStyle()
     */
    protected NodeStyleDescription style;
    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getConditionalStyles()
     */
    protected EList<ConditionalNodeStyle> conditionalStyles;
    /**
     * The cached value of the '{@link #getChildrenDescriptions() <em>Children Descriptions</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getChildrenDescriptions()
     */
    protected EList<NodeDescription> childrenDescriptions;
    /**
     * The cached value of the '{@link #getBorderNodesDescriptions() <em>Border Nodes Descriptions</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBorderNodesDescriptions()
     */
    protected EList<NodeDescription> borderNodesDescriptions;
    /**
     * The cached value of the '{@link #getReusedChildNodeDescriptions() <em>Reused Child Node Descriptions</em>}'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getReusedChildNodeDescriptions()
     */
    protected EList<NodeDescription> reusedChildNodeDescriptions;
    /**
     * The cached value of the '{@link #getReusedBorderNodeDescriptions() <em>Reused Border Node Descriptions</em>}'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getReusedBorderNodeDescriptions()
     */
    protected EList<NodeDescription> reusedBorderNodeDescriptions;
    /**
     * The cached value of the '{@link #isUserResizable() <em>User Resizable</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isUserResizable()
     */
    protected boolean userResizable = USER_RESIZABLE_EDEFAULT;
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
     * The cached value of the '{@link #getDefaultHeightExpression() <em>Default Height Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDefaultHeightExpression()
     */
    protected String defaultHeightExpression = DEFAULT_HEIGHT_EXPRESSION_EDEFAULT;
    /**
     * The cached value of the '{@link #isKeepAspectRatio() <em>Keep Aspect Ratio</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isKeepAspectRatio()
     */
    protected boolean keepAspectRatio = KEEP_ASPECT_RATIO_EDEFAULT;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodeDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.NODE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isCollapsible() {
        return this.collapsible;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCollapsible(boolean newCollapsible) {
        boolean oldCollapsible = this.collapsible;
        this.collapsible = newCollapsible;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE, oldCollapsible, this.collapsible));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodePalette getPalette() {
        return this.palette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPalette(NodePalette newPalette) {
        if (newPalette != this.palette) {
            NotificationChain msgs = null;
            if (this.palette != null)
                msgs = ((InternalEObject) this.palette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__PALETTE, null, msgs);
            if (newPalette != null)
                msgs = ((InternalEObject) newPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__PALETTE, null, msgs);
            msgs = this.basicSetPalette(newPalette, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__PALETTE, newPalette, newPalette));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetPalette(NodePalette newPalette, NotificationChain msgs) {
        NodePalette oldPalette = this.palette;
        this.palette = newPalette;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__PALETTE, oldPalette, newPalette);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LayoutStrategyDescription getChildrenLayoutStrategy() {
        return this.childrenLayoutStrategy;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setChildrenLayoutStrategy(LayoutStrategyDescription newChildrenLayoutStrategy) {
        if (newChildrenLayoutStrategy != this.childrenLayoutStrategy) {
            NotificationChain msgs = null;
            if (this.childrenLayoutStrategy != null)
                msgs = ((InternalEObject) this.childrenLayoutStrategy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, null, msgs);
            if (newChildrenLayoutStrategy != null)
                msgs = ((InternalEObject) newChildrenLayoutStrategy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, null, msgs);
            msgs = this.basicSetChildrenLayoutStrategy(newChildrenLayoutStrategy, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, newChildrenLayoutStrategy, newChildrenLayoutStrategy));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetChildrenLayoutStrategy(LayoutStrategyDescription newChildrenLayoutStrategy, NotificationChain msgs) {
        LayoutStrategyDescription oldChildrenLayoutStrategy = this.childrenLayoutStrategy;
        this.childrenLayoutStrategy = newChildrenLayoutStrategy;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, oldChildrenLayoutStrategy,
                    newChildrenLayoutStrategy);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeStyleDescription getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStyle(NodeStyleDescription newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(NodeStyleDescription newStyle, NotificationChain msgs) {
        NodeStyleDescription oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__STYLE, oldStyle, newStyle);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalNodeStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalNodeStyle.class, this, DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getChildrenDescriptions() {
        if (this.childrenDescriptions == null) {
            this.childrenDescriptions = new EObjectContainmentEList<>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
        }
        return this.childrenDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getBorderNodesDescriptions() {
        if (this.borderNodesDescriptions == null) {
            this.borderNodesDescriptions = new EObjectContainmentEList<>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS);
        }
        return this.borderNodesDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getReusedChildNodeDescriptions() {
        if (this.reusedChildNodeDescriptions == null) {
            this.reusedChildNodeDescriptions = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS);
        }
        return this.reusedChildNodeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getReusedBorderNodeDescriptions() {
        if (this.reusedBorderNodeDescriptions == null) {
            this.reusedBorderNodeDescriptions = new EObjectResolvingEList<>(NodeDescription.class, this, DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS);
        }
        return this.reusedBorderNodeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isUserResizable() {
        return this.userResizable;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUserResizable(boolean newUserResizable) {
        boolean oldUserResizable = this.userResizable;
        this.userResizable = newUserResizable;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE, oldUserResizable, this.userResizable));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDefaultWidthExpression() {
        return this.defaultWidthExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDefaultWidthExpression(String newDefaultWidthExpression) {
        String oldDefaultWidthExpression = this.defaultWidthExpression;
        this.defaultWidthExpression = newDefaultWidthExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION, oldDefaultWidthExpression, this.defaultWidthExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDefaultHeightExpression() {
        return this.defaultHeightExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDefaultHeightExpression(String newDefaultHeightExpression) {
        String oldDefaultHeightExpression = this.defaultHeightExpression;
        this.defaultHeightExpression = newDefaultHeightExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION, oldDefaultHeightExpression, this.defaultHeightExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isKeepAspectRatio() {
        return this.keepAspectRatio;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setKeepAspectRatio(boolean newKeepAspectRatio) {
        boolean oldKeepAspectRatio = this.keepAspectRatio;
        this.keepAspectRatio = newKeepAspectRatio;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO, oldKeepAspectRatio, this.keepAspectRatio));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public InsideLabelDescription getInsideLabel() {
        return this.insideLabel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setInsideLabel(InsideLabelDescription newInsideLabel) {
        if (newInsideLabel != this.insideLabel) {
            NotificationChain msgs = null;
            if (this.insideLabel != null)
                msgs = ((InternalEObject) this.insideLabel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, null, msgs);
            if (newInsideLabel != null)
                msgs = ((InternalEObject) newInsideLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, null, msgs);
            msgs = this.basicSetInsideLabel(newInsideLabel, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, newInsideLabel, newInsideLabel));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetInsideLabel(InsideLabelDescription newInsideLabel, NotificationChain msgs) {
        InsideLabelDescription oldInsideLabel = this.insideLabel;
        this.insideLabel = newInsideLabel;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL, oldInsideLabel, newInsideLabel);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<OutsideLabelDescription> getOutsideLabels() {
        if (this.outsideLabels == null) {
            this.outsideLabels = new EObjectContainmentEList<>(OutsideLabelDescription.class, this, DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS);
        }
        return this.outsideLabels;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.NODE_DESCRIPTION__PALETTE:
                return this.basicSetPalette(null, msgs);
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
                return this.basicSetChildrenLayoutStrategy(null, msgs);
            case DiagramPackage.NODE_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
                return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
                return ((InternalEList<?>) this.getChildrenDescriptions()).basicRemove(otherEnd, msgs);
            case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
                return ((InternalEList<?>) this.getBorderNodesDescriptions()).basicRemove(otherEnd, msgs);
            case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
                return this.basicSetInsideLabel(null, msgs);
            case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
                return ((InternalEList<?>) this.getOutsideLabels()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
                return this.isCollapsible();
            case DiagramPackage.NODE_DESCRIPTION__PALETTE:
                return this.getPalette();
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
                return this.getChildrenLayoutStrategy();
            case DiagramPackage.NODE_DESCRIPTION__STYLE:
                return this.getStyle();
            case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
                return this.getChildrenDescriptions();
            case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
                return this.getBorderNodesDescriptions();
            case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
                return this.getReusedChildNodeDescriptions();
            case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
                return this.getReusedBorderNodeDescriptions();
            case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
                return this.isUserResizable();
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
                return this.getDefaultWidthExpression();
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
                return this.getDefaultHeightExpression();
            case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
                return this.isKeepAspectRatio();
            case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
                return this.getInsideLabel();
            case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
                return this.getOutsideLabels();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
                this.setCollapsible((Boolean) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__PALETTE:
                this.setPalette((NodePalette) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
                this.setChildrenLayoutStrategy((LayoutStrategyDescription) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__STYLE:
                this.setStyle((NodeStyleDescription) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalNodeStyle>) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
                this.getChildrenDescriptions().clear();
                this.getChildrenDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
                this.getBorderNodesDescriptions().clear();
                this.getBorderNodesDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
                this.getReusedChildNodeDescriptions().clear();
                this.getReusedChildNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
                this.getReusedBorderNodeDescriptions().clear();
                this.getReusedBorderNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
                this.setUserResizable((Boolean) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
                this.setDefaultWidthExpression((String) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
                this.setDefaultHeightExpression((String) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
                this.setKeepAspectRatio((Boolean) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
                this.setInsideLabel((InsideLabelDescription) newValue);
                return;
            case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
                this.getOutsideLabels().clear();
                this.getOutsideLabels().addAll((Collection<? extends OutsideLabelDescription>) newValue);
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
            case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
                this.setCollapsible(COLLAPSIBLE_EDEFAULT);
                return;
            case DiagramPackage.NODE_DESCRIPTION__PALETTE:
                this.setPalette((NodePalette) null);
                return;
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
                this.setChildrenLayoutStrategy((LayoutStrategyDescription) null);
                return;
            case DiagramPackage.NODE_DESCRIPTION__STYLE:
                this.setStyle((NodeStyleDescription) null);
                return;
            case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
                this.getChildrenDescriptions().clear();
                return;
            case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
                this.getBorderNodesDescriptions().clear();
                return;
            case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
                this.getReusedChildNodeDescriptions().clear();
                return;
            case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
                this.getReusedBorderNodeDescriptions().clear();
                return;
            case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
                this.setUserResizable(USER_RESIZABLE_EDEFAULT);
                return;
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
                this.setDefaultWidthExpression(DEFAULT_WIDTH_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
                this.setDefaultHeightExpression(DEFAULT_HEIGHT_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
                this.setKeepAspectRatio(KEEP_ASPECT_RATIO_EDEFAULT);
                return;
            case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
                this.setInsideLabel((InsideLabelDescription) null);
                return;
            case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
                this.getOutsideLabels().clear();
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
            case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
                return this.collapsible != COLLAPSIBLE_EDEFAULT;
            case DiagramPackage.NODE_DESCRIPTION__PALETTE:
                return this.palette != null;
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
                return this.childrenLayoutStrategy != null;
            case DiagramPackage.NODE_DESCRIPTION__STYLE:
                return this.style != null;
            case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
                return this.childrenDescriptions != null && !this.childrenDescriptions.isEmpty();
            case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
                return this.borderNodesDescriptions != null && !this.borderNodesDescriptions.isEmpty();
            case DiagramPackage.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS:
                return this.reusedChildNodeDescriptions != null && !this.reusedChildNodeDescriptions.isEmpty();
            case DiagramPackage.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS:
                return this.reusedBorderNodeDescriptions != null && !this.reusedBorderNodeDescriptions.isEmpty();
            case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
                return this.userResizable != USER_RESIZABLE_EDEFAULT;
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
                return DEFAULT_WIDTH_EXPRESSION_EDEFAULT == null ? this.defaultWidthExpression != null : !DEFAULT_WIDTH_EXPRESSION_EDEFAULT.equals(this.defaultWidthExpression);
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
                return DEFAULT_HEIGHT_EXPRESSION_EDEFAULT == null ? this.defaultHeightExpression != null : !DEFAULT_HEIGHT_EXPRESSION_EDEFAULT.equals(this.defaultHeightExpression);
            case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
                return this.keepAspectRatio != KEEP_ASPECT_RATIO_EDEFAULT;
            case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
                return this.insideLabel != null;
            case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
                return this.outsideLabels != null && !this.outsideLabels.isEmpty();
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
        result.append(" (collapsible: ");
        result.append(this.collapsible);
        result.append(", userResizable: ");
        result.append(this.userResizable);
        result.append(", defaultWidthExpression: ");
        result.append(this.defaultWidthExpression);
        result.append(", defaultHeightExpression: ");
        result.append(this.defaultHeightExpression);
        result.append(", keepAspectRatio: ");
        result.append(this.keepAspectRatio);
        result.append(')');
        return result.toString();
    }

} // NodeDescriptionImpl
