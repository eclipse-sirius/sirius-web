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
import React from 'react';
import styles from './ErrorModal.module.css';
import { Modal } from './Modal';

const propTypes = {
  title: PropTypes.string.isRequired,
  message: PropTypes.string.isRequired,
  additionalMessages: PropTypes.arrayOf(PropTypes.string),
  onClose: PropTypes.func.isRequired,
};

export const ErrorModal = ({ title, message, additionalMessages, onClose }) => {
  let additional = null;
  if (additionalMessages && additionalMessages.length > 0) {
    additional = (
      <ul className={styles.ul}>
        {additionalMessages.map((additionalMessage, index) => (
          <li key={index} className={styles.li}>
            <Text className={styles.additionalMessages}>{additionalMessage}</Text>
          </li>
        ))}
      </ul>
    );
  }
  return (
    <Modal title={title} onClose={onClose}>
      <div className={styles.container}>
        <div className={styles.messages}>
          <Text className={styles.message}>{message}</Text>
          {additional}
        </div>
      </div>
    </Modal>
  );
};
ErrorModal.propTypes = propTypes;
