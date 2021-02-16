/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import React, { useCallback, useEffect, useRef } from 'react';
import styles from './ContextMenu.module.css';

const entryPropTypes = {
  icon: PropTypes.node,
  label: PropTypes.node.isRequired,
  disabled: PropTypes.bool,
  onClick: PropTypes.func,
  'data-testid': PropTypes.string.isRequired,
};
export const Entry = ({ icon, label, disabled, onClick, 'data-testid': dataTestId }) => {
  let textClassName = styles.label;
  if (disabled) {
    textClassName = `${textClassName} ${styles.disabled}`;
  }

  const potentiallyDisabledOnClick = (event) => {
    if (!disabled && onClick) {
      onClick(event);
    }
  };

  return (
    <li className={styles.entry} onClick={potentiallyDisabledOnClick} data-testid={dataTestId}>
      <div className={styles.icon}>{icon}</div>
      <Text className={textClassName}>{label}</Text>
    </li>
  );
};
Entry.propTypes = entryPropTypes;

export const Separator = () => {
  return <li className={styles.separator} />;
};

export const LEFT_START = 'leftstart';
export const TOP_START = 'topstart';
export const TOP_END = 'topend';

const contextMenuPropTypes = {
  x: PropTypes.number.isRequired,
  y: PropTypes.number.isRequired,
  caretPosition: PropTypes.oneOf([LEFT_START, TOP_START, TOP_END]),
  children: PropTypes.node.isRequired,
  onClose: PropTypes.func.isRequired,
  'data-testid': PropTypes.string.isRequired,
};
export const ContextMenu = ({ x, y, caretPosition, children, onClose, 'data-testid': dataTestId }) => {
  const domNode = useRef(null);

  const onClickOutsideElement = useCallback(
    (event) => {
      if (domNode.current && !domNode.current.contains(event.target)) {
        onClose();
      }
    },
    [onClose]
  );

  useEffect(() => {
    document.addEventListener('click', onClickOutsideElement, false);
    return () => {
      document.removeEventListener('click', onClickOutsideElement, false);
    };
  }, [onClickOutsideElement]);

  const onKeyDown = useCallback(
    (event) => {
      if (event.keyCode === 27) {
        onClose();
      }
    },
    [onClose]
  );

  useEffect(() => {
    document.addEventListener('keydown', onKeyDown, false);
    return () => {
      document.removeEventListener('keydown', onKeyDown, false);
    };
  }, [onKeyDown]);

  const style = {
    left: x,
    top: y,
  };
  let caret;
  if (caretPosition === LEFT_START) {
    caret = (
      <div className={styles.leftstartcaretarea}>
        <div className={styles.leftstartcaret} />
      </div>
    );
  } else if (caretPosition === TOP_START) {
    caret = (
      <div className={styles.topstartcaretarea}>
        <div className={styles.topstartcaret} />
      </div>
    );
  } else if (caretPosition === TOP_END) {
    caret = (
      <div className={styles.topendcaretarea}>
        <div className={styles.topendcaret} />
      </div>
    );
  }
  return (
    <div className={styles.contextmenu} style={style}>
      {caret}
      <ul className={styles.menucontents} ref={domNode} data-testid={dataTestId}>
        {children}
      </ul>
      <div />
    </div>
  );
};
ContextMenu.propTypes = contextMenuPropTypes;
