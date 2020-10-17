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
import { IconButton } from 'core/button/Button';
import { Text } from 'core/text/Text';
import { Exit } from 'icons';
import PropTypes from 'prop-types';
import React, { useCallback, useEffect, useRef } from 'react';
import styles from './Modal.module.css';

const propTypes = {
  title: PropTypes.string.isRequired,
  onClose: PropTypes.func.isRequired,
  children: PropTypes.node.isRequired,
};
export const Modal = ({ title, onClose, children }) => {
  const reference = useRef(null);

  const onClick = (event) => {
    if (reference.current && reference.current === event.target) {
      onClose();
    }
  };

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

  return (
    <>
      <div className={styles.overlay}></div>
      <div
        className={styles.modal}
        role="dialog"
        aria-modal="true"
        ref={reference}
        onClick={onClick}
        data-testid="modal">
        <div className={styles.container}>
          <div className={styles.close}>
            <IconButton className={styles.exitIcon} onClick={onClose} data-testid="close">
              <Exit title="Exit" />
            </IconButton>
          </div>
          <div className={styles.content}>
            <Text className={styles.title}>{title}</Text>
            {children}
          </div>
        </div>
      </div>
    </>
  );
};
Modal.propTypes = propTypes;
