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
import React, { useEffect, useRef } from 'react';
import PropTypes from 'prop-types';
import { Modal } from 'modals/Modal';

import styles from './ShareDiagramModal.module.css';

const propTypes = {
  url: PropTypes.string.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const ShareDiagramModal = ({ url, onClose }) => {
  let message = 'Shareable link';
  if (navigator.clipboard && document.hasFocus()) {
    navigator.clipboard.writeText(url);
    message += ' (copied into the clipboard)';
  }

  const textRef = useRef();
  useEffect(() => {
    var range = document.createRange();
    range.selectNodeContents(textRef.current);
    var selection = window.getSelection();
    selection.removeAllRanges();
    selection.addRange(range);
  }, [textRef]);

  return (
    <Modal title={message} onClose={onClose}>
      <div className={styles.text} ref={textRef}>
        {url}
      </div>
    </Modal>
  );
};
ShareDiagramModal.propTypes = propTypes;
