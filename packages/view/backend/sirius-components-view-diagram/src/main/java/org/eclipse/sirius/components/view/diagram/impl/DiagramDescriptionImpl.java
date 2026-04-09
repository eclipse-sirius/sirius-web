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
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.ConditionalDiagramStyle;
import org.eclipse.sirius.components.view.diagram.DecoratorDescription;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DiagramStyleDescription;
import org.eclipse.sirius.components.view.diagram.DiagramToolbar;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.GroupPalette;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#isAutoLayout <em>Auto
 * Layout</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getToolbar <em>Toolbar</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getGroupPalette <em>Group
 * Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getNodeDescriptions <em>Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getEdgeDescriptions <em>Edge
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getArrangeLayoutDirection
 * <em>Arrange Layout Direction</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getConditionalStyles
 * <em>Conditional Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl#getDecoratorDescriptions
 * <em>Decorator Descriptions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramDescriptionImpl extends RepresentationDescriptionImpl implements DiagramDescription {

    /**
     * The default value of the '{@link #isAutoLayout() <em>Auto Layout</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isAutoLayout()
     * @generated
     * @ordered
     */
    protected static final boolean AUTO_LAYOUT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAutoLayout() <em>Auto Layout</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isAutoLayout()
     * @generated
     * @ordered
     */
    protected boolean autoLayout = AUTO_LAYOUT_EDEFAULT;

    /**
     * The cached value of the '{@link #getToolbar() <em>Toolbar</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getToolbar()
     * @generated
     * @ordered
     */
    protected DiagramToolbar toolbar;

    /**
     * The cached value of the '{@link #getPalette() <em>Palette</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPalette()
     * @generated
     * @ordered
     */
    protected DiagramPalette palette;

    /**
     * The cached value of the '{@link #getGroupPalette() <em>Group Palette</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGroupPalette()
     * @generated
     * @ordered
     */
    protected GroupPalette groupPalette;

    /**
     * The cached value of the '{@link #getNodeDescriptions() <em>Node Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNodeDescriptions()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> nodeDescriptions;

    /**
     * The cached value of the '{@link #getEdgeDescriptions() <em>Edge Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEdgeDescriptions()
     * @generated
     * @ordered
     */
    protected EList<EdgeDescription> edgeDescriptions;

    /**
     * The default value of the '{@link #getArrangeLayoutDirection() <em>Arrange Layout Direction</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getArrangeLayoutDirection()
     */
    protected static final ArrangeLayoutDirection ARRANGE_LAYOUT_DIRECTION_EDEFAULT = ArrangeLayoutDirection.UNDEFINED;

    /**
     * The cached value of the '{@link #getArrangeLayoutDirection() <em>Arrange Layout Direction</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getArrangeLayoutDirection()
     */
    protected ArrangeLayoutDirection arrangeLayoutDirection = ARRANGE_LAYOUT_DIRECTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected DiagramStyleDescription style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalDiagramStyle> conditionalStyles;

    /**
     * The cached value of the '{@link #getDecoratorDescriptions() <em>Decorator Descriptions</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDecoratorDescriptions()
     * @generated
     * @ordered
     */
    protected EList<DecoratorDescription> decoratorDescriptions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.DIAGRAM_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isAutoLayout() {
        return this.autoLayout;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setAutoLayout(boolean newAutoLayout) {
        boolean oldAutoLayout = this.autoLayout;
        this.autoLayout = newAutoLayout;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT, oldAutoLayout, this.autoLayout));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramToolbar getToolbar() {
        return this.toolbar;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetToolbar(DiagramToolbar newToolbar, NotificationChain msgs) {
        DiagramToolbar oldToolbar = this.toolbar;
        this.toolbar = newToolbar;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR, oldToolbar, newToolbar);
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
    public void setToolbar(DiagramToolbar newToolbar) {
        if (newToolbar != this.toolbar) {
            NotificationChain msgs = null;
            if (this.toolbar != null)
                msgs = ((InternalEObject) this.toolbar).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR, null, msgs);
            if (newToolbar != null)
                msgs = ((InternalEObject) newToolbar).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR, null, msgs);
            msgs = this.basicSetToolbar(newToolbar, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR, newToolbar, newToolbar));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramPalette getPalette() {
        return this.palette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPalette(DiagramPalette newPalette) {
        if (newPalette != this.palette) {
            NotificationChain msgs = null;
            if (this.palette != null)
                msgs = ((InternalEObject) this.palette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, null, msgs);
            if (newPalette != null)
                msgs = ((InternalEObject) newPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, null, msgs);
            msgs = this.basicSetPalette(newPalette, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, newPalette, newPalette));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetPalette(DiagramPalette newPalette, NotificationChain msgs) {
        DiagramPalette oldPalette = this.palette;
        this.palette = newPalette;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE, oldPalette, newPalette);
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
    public EList<NodeDescription> getNodeDescriptions() {
        if (this.nodeDescriptions == null) {
            this.nodeDescriptions = new EObjectContainmentEList<>(NodeDescription.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
        }
        return this.nodeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeDescription> getEdgeDescriptions() {
        if (this.edgeDescriptions == null) {
            this.edgeDescriptions = new EObjectContainmentEList<>(EdgeDescription.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);
        }
        return this.edgeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ArrangeLayoutDirection getArrangeLayoutDirection() {
        return this.arrangeLayoutDirection;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setArrangeLayoutDirection(ArrangeLayoutDirection newArrangeLayoutDirection) {
        ArrangeLayoutDirection oldArrangeLayoutDirection = this.arrangeLayoutDirection;
        this.arrangeLayoutDirection = newArrangeLayoutDirection == null ? ARRANGE_LAYOUT_DIRECTION_EDEFAULT : newArrangeLayoutDirection;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION, oldArrangeLayoutDirection, this.arrangeLayoutDirection));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DiagramStyleDescription getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(DiagramStyleDescription newStyle, NotificationChain msgs) {
        DiagramStyleDescription oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(DiagramStyleDescription newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalDiagramStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalDiagramStyle.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DecoratorDescription> getDecoratorDescriptions() {
        if (this.decoratorDescriptions == null) {
            this.decoratorDescriptions = new EObjectContainmentEList<>(DecoratorDescription.class, this, DiagramPackage.DIAGRAM_DESCRIPTION__DECORATOR_DESCRIPTIONS);
        }
        return this.decoratorDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public GroupPalette getGroupPalette() {
        return this.groupPalette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetGroupPalette(GroupPalette newGroupPalette, NotificationChain msgs) {
        GroupPalette oldGroupPalette = this.groupPalette;
        this.groupPalette = newGroupPalette;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, oldGroupPalette, newGroupPalette);
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
    public void setGroupPalette(GroupPalette newGroupPalette) {
        if (newGroupPalette != this.groupPalette) {
            NotificationChain msgs = null;
            if (this.groupPalette != null)
                msgs = ((InternalEObject) this.groupPalette).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, null, msgs);
            if (newGroupPalette != null)
                msgs = ((InternalEObject) newGroupPalette).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, null, msgs);
            msgs = this.basicSetGroupPalette(newGroupPalette, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE, newGroupPalette, newGroupPalette));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR:
                return this.basicSetToolbar(null, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                return this.basicSetPalette(null, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
                return this.basicSetGroupPalette(null, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                return ((InternalEList<?>) this.getNodeDescriptions()).basicRemove(otherEnd, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                return ((InternalEList<?>) this.getEdgeDescriptions()).basicRemove(otherEnd, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__CONDITIONAL_STYLES:
                return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
            case DiagramPackage.DIAGRAM_DESCRIPTION__DECORATOR_DESCRIPTIONS:
                return ((InternalEList<?>) this.getDecoratorDescriptions()).basicRemove(otherEnd, msgs);
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                return this.isAutoLayout();
            case DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR:
                return this.getToolbar();
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                return this.getPalette();
            case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
                return this.getGroupPalette();
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                return this.getNodeDescriptions();
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                return this.getEdgeDescriptions();
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                return this.getArrangeLayoutDirection();
            case DiagramPackage.DIAGRAM_DESCRIPTION__STYLE:
                return this.getStyle();
            case DiagramPackage.DIAGRAM_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
            case DiagramPackage.DIAGRAM_DESCRIPTION__DECORATOR_DESCRIPTIONS:
                return this.getDecoratorDescriptions();
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                this.setAutoLayout((Boolean) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR:
                this.setToolbar((DiagramToolbar) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                this.setPalette((DiagramPalette) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
                this.setGroupPalette((GroupPalette) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                this.getNodeDescriptions().clear();
                this.getNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                this.getEdgeDescriptions().clear();
                this.getEdgeDescriptions().addAll((Collection<? extends EdgeDescription>) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                this.setArrangeLayoutDirection((ArrangeLayoutDirection) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__STYLE:
                this.setStyle((DiagramStyleDescription) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalDiagramStyle>) newValue);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__DECORATOR_DESCRIPTIONS:
                this.getDecoratorDescriptions().clear();
                this.getDecoratorDescriptions().addAll((Collection<? extends DecoratorDescription>) newValue);
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                this.setAutoLayout(AUTO_LAYOUT_EDEFAULT);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR:
                this.setToolbar((DiagramToolbar) null);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                this.setPalette((DiagramPalette) null);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
                this.setGroupPalette((GroupPalette) null);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                this.getNodeDescriptions().clear();
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                this.getEdgeDescriptions().clear();
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                this.setArrangeLayoutDirection(ARRANGE_LAYOUT_DIRECTION_EDEFAULT);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__STYLE:
                this.setStyle((DiagramStyleDescription) null);
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
            case DiagramPackage.DIAGRAM_DESCRIPTION__DECORATOR_DESCRIPTIONS:
                this.getDecoratorDescriptions().clear();
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
            case DiagramPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                return this.autoLayout != AUTO_LAYOUT_EDEFAULT;
            case DiagramPackage.DIAGRAM_DESCRIPTION__TOOLBAR:
                return this.toolbar != null;
            case DiagramPackage.DIAGRAM_DESCRIPTION__PALETTE:
                return this.palette != null;
            case DiagramPackage.DIAGRAM_DESCRIPTION__GROUP_PALETTE:
                return this.groupPalette != null;
            case DiagramPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
                return this.nodeDescriptions != null && !this.nodeDescriptions.isEmpty();
            case DiagramPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
                return this.edgeDescriptions != null && !this.edgeDescriptions.isEmpty();
            case DiagramPackage.DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION:
                return this.arrangeLayoutDirection != ARRANGE_LAYOUT_DIRECTION_EDEFAULT;
            case DiagramPackage.DIAGRAM_DESCRIPTION__STYLE:
                return this.style != null;
            case DiagramPackage.DIAGRAM_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
            case DiagramPackage.DIAGRAM_DESCRIPTION__DECORATOR_DESCRIPTIONS:
                return this.decoratorDescriptions != null && !this.decoratorDescriptions.isEmpty();
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
        result.append(" (autoLayout: ");
        result.append(this.autoLayout);
        result.append(", arrangeLayoutDirection: ");
        result.append(this.arrangeLayoutDirection);
        result.append(')');
        return result.toString();
    }

} // DiagramDescriptionImpl
