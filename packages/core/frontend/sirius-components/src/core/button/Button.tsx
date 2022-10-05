/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { Text } from 'core/text/Text';
import PropTypes from 'prop-types';
import React from 'react';
import { Link } from 'react-router-dom';
import styles from './Button.module.css';

const BUTTON = 'button';
const SUBMIT = 'submit';

const propTypes = {
  className: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
  onClick: PropTypes.func,
  type: PropTypes.oneOf([BUTTON, SUBMIT]).isRequired,
  disabled: PropTypes.bool,
  to: PropTypes.string,
  'data-testid': PropTypes.string.isRequired,
};
const defaultProps = {
  type: BUTTON,
};
const Button = ({ className, children, onClick, type, disabled, to, 'data-testid': dataTestid }) => {
  if (to) {
    if (disabled) {
      return (
        <div className={`${className} ${styles.disabled}`} data-testid={dataTestid}>
          {children}
        </div>
      );
    } else {
      return (
        <Link to={to} className={className} data-testid={dataTestid}>
          {children}
        </Link>
      );
    }
  }

  return (
    <button className={className} type={type} onClick={onClick} disabled={disabled} data-testid={dataTestid}>
      {children}
    </button>
  );
};
Button.propTypes = propTypes;
Button.defaultProps = defaultProps;

export const ActionButton = ({ label, ...props }) => {
  return (
    <Button {...props} className={`${styles.rectangularButton} ${styles.action}`}>
      <Text className={styles.label}>{label}</Text>
    </Button>
  );
};

export const SecondaryButton = ({ label, ...props }) => {
  return (
    <Button {...props} className={`${styles.rectangularButton} ${styles.secondary}`}>
      <Text className={styles.label}>{label}</Text>
    </Button>
  );
};

export const DangerButton = ({ label, ...props }) => {
  return (
    <Button {...props} className={`${styles.rectangularButton} ${styles.danger}`}>
      <Text className={styles.label}>{label}</Text>
    </Button>
  );
};

export const DangerSecondaryButton = ({ label, ...props }) => {
  return (
    <Button {...props} className={`${styles.rectangularButton} ${styles.dangerSecondary}`}>
      <Text className={styles.label}>{label}</Text>
    </Button>
  );
};

export const IconButton = ({ className = undefined, ...props }) => {
  let iconClassName = styles.iconButton;
  if (className) {
    iconClassName = `${iconClassName} ${className}`;
  }
  return <Button {...props} className={iconClassName} />;
};

export const Buttons = ({ children }) => {
  return <div className={styles.buttons}>{children}</div>;
};
