/**
 */
package org.eclipse.sirius.web.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.Mode;
import org.eclipse.sirius.web.view.Style;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram Element Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getDomainType <em>Domain Type</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getSemanticCandidatesExpression <em>Semantic Candidates Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getCreationMode <em>Creation Mode</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl#getStyle <em>Style</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DiagramElementDescriptionImpl extends MinimalEObjectImpl.Container
		implements DiagramElementDescription {
	/**
	 * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected static final String DOMAIN_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected String domainType = DOMAIN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSemanticCandidatesExpression()
	 * @generated
	 * @ordered
	 */
	protected String semanticCandidatesExpression = SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationMode() <em>Creation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationMode()
	 * @generated
	 * @ordered
	 */
	protected static final Mode CREATION_MODE_EDEFAULT = Mode.AUTO;

	/**
	 * The cached value of the '{@link #getCreationMode() <em>Creation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationMode()
	 * @generated
	 * @ordered
	 */
	protected Mode creationMode = CREATION_MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
	protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
	protected Style style;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiagramElementDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDomainType() {
		return domainType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDomainType(String newDomainType) {
		String oldDomainType = domainType;
		domainType = newDomainType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE,
					oldDomainType, domainType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSemanticCandidatesExpression() {
		return semanticCandidatesExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSemanticCandidatesExpression(String newSemanticCandidatesExpression) {
		String oldSemanticCandidatesExpression = semanticCandidatesExpression;
		semanticCandidatesExpression = newSemanticCandidatesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION,
					oldSemanticCandidatesExpression, semanticCandidatesExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Mode getCreationMode() {
		return creationMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreationMode(Mode newCreationMode) {
		Mode oldCreationMode = creationMode;
		creationMode = newCreationMode == null ? CREATION_MODE_EDEFAULT : newCreationMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE, oldCreationMode, creationMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabelExpression() {
		return labelExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Style getStyle() {
		return style;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStyle(Style newStyle, NotificationChain msgs) {
		Style oldStyle = style;
		style = newStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStyle(Style newStyle) {
		if (newStyle != style) {
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject) style).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject) newStyle).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE,
					newStyle, newStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE:
			return basicSetStyle(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
			return getDomainType();
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
			return getSemanticCandidatesExpression();
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
			return getCreationMode();
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
			return getLabelExpression();
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE:
			return getStyle();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
			setDomainType((String) newValue);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
			setSemanticCandidatesExpression((String) newValue);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
			setCreationMode((Mode) newValue);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
			setLabelExpression((String) newValue);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE:
			setStyle((Style) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
			setDomainType(DOMAIN_TYPE_EDEFAULT);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
			setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
			setCreationMode(CREATION_MODE_EDEFAULT);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
			setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
			return;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE:
			setStyle((Style) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
			return DOMAIN_TYPE_EDEFAULT == null ? domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(domainType);
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
			return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? semanticCandidatesExpression != null
					: !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(semanticCandidatesExpression);
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
			return creationMode != CREATION_MODE_EDEFAULT;
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
			return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null
					: !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
		case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__STYLE:
			return style != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (domainType: ");
		result.append(domainType);
		result.append(", semanticCandidatesExpression: ");
		result.append(semanticCandidatesExpression);
		result.append(", creationMode: ");
		result.append(creationMode);
		result.append(", labelExpression: ");
		result.append(labelExpression);
		result.append(')');
		return result.toString();
	}

} //DiagramElementDescriptionImpl
